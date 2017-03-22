using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace Framework
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmSkuIdSearch : Framework.BaseForm
    {
        private int _currentPage = 0;
        MobileResponse<SkuList> _response = null;

        public Sku SelectedSku { get; set; }

        public frmSkuIdSearch()
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
            DisplayData(_response);
            if (ret != 0)
                return ret;
            return 0;
        }
        /// <summary>
        /// 退回
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnExist_Click(object sender, EventArgs e)
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
            int retflg = base.Init(back, paras);
            if (retflg != 0)
                return retflg;

            //取服务器数据
            MobileRequest req = GetInputData("getSkuId");
            req.PageId = null;
            req.Parameters.Add("skuBarCode", paras[0].TrimEx());
            AppContext app = AppContext.Instance();
            //调用服务端
            int ret = base.PostData("getSkuId", req, out _response);
            if (!(ret == 0 || ret == 2))
                //错误返回
                return ret;

            if (_response.Results.skuList != null)
            {
                grdData.PreferredRowHeight = 34;
                tsMain.GridColumnStyles[0].Width = 30;
                tsMain.GridColumnStyles[1].Width = grdData.Width - tsMain.GridColumnStyles[0].Width - 5;
                if (grdData.TableStyles.Count == 0)
                    grdData.TableStyles.Add(tsMain);
                grdData.RowHeadersVisible = false;
                grdData.ColumnHeadersVisible = false;
                //_currentPage = 1;
            }
            else
            {
                SelectedSku = new Sku();
                SelectedSku.code = _response.Results.SkuCode;
                SelectedSku.coefficient = _response.Results.coefficient;
                SelectedSku.name = _response.Results.SkuName;
                SelectedSku.id = _response.Results.SkuId;
                SelectedSku.unit = _response.Results.unit;
                return 0;
            }
            return 0;
        }

        /// <summary>
        /// 显示数据
        /// </summary>
        /// <param name="response"></param>
        private void DisplayData(MobileResponse<SkuList> response)
        {
            for (int i = 0; i < response.Results.skuList.Count; i++)
            {
                response.Results.skuList[i].Num = "{0}.".FormatEx(i + 1);
            }
            bindingSource.DataSource = response.Results.skuList;// GetCurrentPage(_currentPage, response.Results.skuList);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grdData_ShowDetail(object sender, EventArgs e)
        {
            try
            {
                ////取出当前记录
                //using (frmCountInfoByQr nextFrm = new frmCountInfoByQr())
                //{
                //    int retflag = nextFrm.Init(false, bindingSource.Current);
                //    if (retflag != 0 && retflag != 2)
                //        return;
                //    DialogResult ret = nextFrm.ShowDialog();
                //    if (ret == DialogResult.Abort)
                //    {
                //        //退回到Menu
                //        this.DialogResult = DialogResult.Abort;
                //    }
                //    else
                //    {
                //        //退回到本画面
                //        this.Init(false);
                //        this.Show(null);
                //        enter.PerformClick();
                //    }
                //}
                this.SelectedSku = bindingSource.Current as Sku;
                this.DialogResult = DialogResult.OK;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}

