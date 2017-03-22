using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace Other
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmList : Framework.BaseForm
    {
        private int _currentPage = 0;
        private long _skuId;
        MobileResponse<InventoryList> _response = null;

        public frmList()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 获取顶级容器控件
        /// </summary>
        /// <returns></returns>
        public override Control GetTopControl()
        {
            return this.pnlDetail;
        }
        /// <summary>
        /// 显示界面
        /// </summary>
        /// <param name="prev"></param>
        /// <returns></returns>
        public override int Show(IView prev)
        {
            int ret = base.Show(prev);
            if (ret != 0)
                return ret;
            //定位焦点
            txtBarCode.Focus();
            txtBarCode.SelectAll();
            return 0;
        }
        /// <summary>
        /// 退回
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnBack_Click(object sender, EventArgs e)
        {
            try
            {
                this.DialogResult = DialogResult.Cancel;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 调用各业务
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Button_Click(object sender, EventArgs e)
        {
            try
            {
                base.EnableButtons(false);
                ButtonEx btn = sender as ButtonEx;
                //导航
                using (BaseForm view = AppContext.Instance().CreateObject<BaseForm>(btn.Tag.ToString()))
                {
                    view.Init(false);
                    DialogResult ret = view.ShowDialog();
                    if (ret == DialogResult.Abort)
                    {
                        //退回到Menu
                        this.DialogResult = DialogResult.Abort;
                    }
                    else
                    {
                        //退回到本画面
                        this.Init(true);
                        this.Show(null);
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                base.EnableButtons(true);
            }
        }
        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public override int Init(bool back, params object[] paras)
        {
            int ret = base.Init(back, paras);
            if (ret != 0)
                return ret;

            lblPageInfo.Text = "{0}-{1}/{2}表示中".FormatEx("0", "0", "0");
            lblPrev.Enabled = false;
            lblNext.Enabled = false;

            grdData.PreferredRowHeight =54;
            tsMain.GridColumnStyles[0].Width = 30;
            tsMain.GridColumnStyles[1].Width = grdData.Width - tsMain.GridColumnStyles[0].Width - 5;
            if (grdData.TableStyles.Count == 0)
                grdData.TableStyles.Add(tsMain);
            grdData.RowHeadersVisible = false;
            grdData.ColumnHeadersVisible = false;
            _currentPage = 1;

            return 0;
        }

        private void txtBarCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter) {
                    //StockResult ret = BizUtil.QueryStock(txtBarCode.Text);
                    //if (ret.ActionResult == ActionResult.SelectOne)
                    //{
                    //    _skuId = ret.Result.id;
                    //}
                    //else
                    //{
                    //    txtBarCode.Focus();
                    //    txtBarCode.SelectAll();
                    //}
                    if (CheckQrCode())
                    {
                        txtBinCode.Focus();
                        txtBinCode.SelectAll();
                    }
                }
                   
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 前5条
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void lblPrev_ClickEx(object sender, EventArgs e)
        {
            if (_response == null)
                return;

            if (_response.Results.invList == null)
                return;

            try
            {
                if (_currentPage > 1)
                {
                    grdData.SuspendLayout();
                    _currentPage--;
                    grdData.UnSelect(grdData.CurrentRowIndex);
                    //显示数据
                    DisplayData(_response);
                    if (bindingSource.Count > 0)
                    {
                        if (e == null)
                        {
                            grdData.CurrentRowIndex = bindingSource.Count -1;
                        }
                        else
                        {
                            grdData.CurrentRowIndex = 0;
                            grdData.Select(0);
                        }
                    }
                    grdData.ResumeLayout();
                }

            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                grdData.ResumeLayout();
            }
        }
        /// <summary>
        /// 后5条
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void lblNext_ClickEx(object sender, EventArgs e)
        {
            if (_response == null)
                return;

            if (_response.Results.invList == null)
                return;

            try
            {
                if (_currentPage * PAGE_SIZE < _response.Results.invList.Count)
                {
                    _currentPage++;
                    //显示数据
                    grdData.SuspendLayout();
                    grdData.UnSelect(grdData.CurrentRowIndex);
                    DisplayData(_response);
                    if (bindingSource.Count > 0)
                    {
                        grdData.Select(0);
                        grdData.CurrentRowIndex = 0;
                    }
                    grdData.ResumeLayout();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                grdData.ResumeLayout();
            }
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnHome_Click(object sender, EventArgs e)
        {
            try
            {
                this.DialogResult = DialogResult.Abort;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 显示数据
        /// </summary>
        /// <param name="response"></param>
        private void DisplayData(MobileResponse<InventoryList> response)
        {
            for (int i = 0; i < response.Results.invList.Count; i++)
            {
                response.Results.invList[i].Num = "{0}.".FormatEx(i + 1);
            }
            bindingSource.DataSource = GetCurrentPage(_currentPage, response.Results.invList);
            if (bindingSource.Count > 0)
            {
                lblPageInfo.Text = "{0}-{1}/{2}表示中".FormatEx((_currentPage - 1) * PAGE_SIZE + 1
                    , _currentPage * PAGE_SIZE > response.Results.invList.Count ? response.Results.invList.Count : _currentPage * PAGE_SIZE, response.Results.invList.Count);
                lblPrev.Enabled = _currentPage > 1;
                lblNext.Enabled = _currentPage * PAGE_SIZE < response.Results.invList.Count;
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBinCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                    enter.PerformClick();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// Enter 検索
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void enter_Click(object sender, EventArgs e)
        {
            try
            {
                //从服务器取数据 进度     
                if (!CheckQrCode())
                    return;
                MobileRequest request = GetInputData("searchInventory");
                request.Parameters.Add("skuId", _skuId);
                int ret = PostData("searchInventory", request, out _response);
                if (ret == 0 || ret == 2)
                {
                    _currentPage = 1;
                    DisplayData(_response);
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private bool CheckQrCode()
        {
            if (txtBarCode.Text.IsEmpty())
            {
                txtBarCode.Focus();
                txtBarCode.SelectAll();
                Message.Warn("QRコードをスキャン して下さい。");
                return false;
            }
            return true;
        }

        /// <summary>
        /// 清除
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void F1_Click(object sender, EventArgs e)
        {
            try
            {
                //清空控件
                this.ClearControl(true,  txtBinCode, txtBarCode );
                bindingSource.DataSource = new BindingList<Other.InventoryInfo>();
                txtBarCode.Focus();
                txtBarCode.SelectAll();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        bool _action = false;
        /// <summary>
        /// 翻页
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grdData_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (_action)
                {
                    if (e.KeyCode == Keys.Up && bindingSource.Position == 0)
                    {
                        lblPrev_ClickEx(lblPrev, null);
                    }
                    else if (e.KeyCode == Keys.Down && bindingSource.Position == bindingSource.Count - 1)
                    {
                        lblNext_ClickEx(lblNext, EventArgs.Empty);
                    }
                }
                if (bindingSource.Position == 0 || bindingSource.Position == bindingSource.Count - 1)
                {
                    if (_action == false)
                        _action = true;
                }
                else
                    _action = false;
                    
            }
            catch
            {
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grdData_MouseDown(object sender, MouseEventArgs e)
        {
            try
            {
                if (bindingSource.Position == 0 || bindingSource.Position == bindingSource.Count - 1)
                {
                    if (_action == false)
                        _action = true;
                }
                else
                    _action = false;
            }
            catch { }
        }

        /// <summary>
        /// 显示详细
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grdData_ShowDetail(object sender, EventArgs e)
        {
            try
            {
                using (frmDetail frmNext = new frmDetail())
                {
                    InventoryInfo current= bindingSource.Current as InventoryInfo;
                    int ret=frmNext.Init(false, current.invId);
                    if (ret != 0 && ret != 2)
                        return;
                    DialogResult result = frmNext.ShowDialog();
                    if (result == DialogResult.Abort)
                    {
                        //退回到Menu
                        this.DialogResult = DialogResult.Abort;
                    }
                    else
                    {
                        //退回到本画面
                        //this.Init(true);
                        //this.Show(null);
                    }
                }
            }
            catch
            {
            }
        }
    }
}

