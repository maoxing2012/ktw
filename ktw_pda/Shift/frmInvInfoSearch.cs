using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace Shift
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmInvInfoSearch : Framework.BaseForm
    {
        private long binId = -1 ;
        public frmInvInfoSearch()
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
            int ret = 0;
            if (!back)
            {
                //基类命令控件初始化
                ret = base.Init(back, paras);
                if (ret != 0)
                    return ret;
                this.picHead.TitleText = "棚単位棚移動（{0}）".FormatEx(AppContext.Instance().Owner.name);
            }

            return 0;
        }

        /// <summary>
        /// return to menu
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
        /// 移動元の棚番入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBinCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (StringUtil.IsEmpty(txtBinCode.Text))
                    {
                        txtBinCode.SelectAll();
                        txtBinCode.Focus();
                        Message.Warn("移動元の棚番を入力してください");
                        return;
                    }
                    //取服务器数据
                    MobileRequest req = GetInputData("getInvInfoByBinCode");
                    req.PageId = null;
                    req.Parameters.Add("binCode", txtBinCode.Text);
                    AppContext app = AppContext.Instance();
                    MobileResponse<InvInfoList> response = null;
                    //调用服务端
                    int ret = base.PostData("getInvInfoByBinCode", req, out response);
                    if (!(ret == 0 || ret == 2))
                        //错误返回
                        return;
                    //if ("E".Equals(response.SeverityMsgType)) {
                    //    Message.Err(response.SeverityMsg);
                    //    return;
                    //}
                    List<string> result = response.Results.invInfo;
                    binId = response.Results.binId; 
                    foreach (string s in result)
                    {
                        listBox1.Items.Add(s);
                    }
                    txtBinCode.ReadOnly = true;
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// enter
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void enter_Click(object sender, EventArgs e)
        {
            try
            {
                if (StringUtil.IsEmpty(txtDescBinCode.Text))
                {
                    txtDescBinCode.SelectAll();
                    txtDescBinCode.Focus();
                    Message.Warn("移動先の棚番を入力してください");
                    return;
                }
                if (binId == -1)
                {
                    txtBinCode.SelectAll();
                    txtBinCode.Focus();
                    return;
                }

                if (Message.Ask("棚番{0}に移動します。よろしいですか？".FormatEx(txtDescBinCode.Text)) != DialogResult.Yes)
                {
                    txtDescBinCode.SelectAll();
                    txtDescBinCode.Focus();
                    return;
                }

                //取服务器数据
                MobileRequest req = GetInputData("executeBinMove");
                req.PageId = null;
                req.Parameters.Add("binId", binId);
                req.Parameters.Add("descBinCode", txtDescBinCode.Text);
                AppContext app = AppContext.Instance();
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端
                int ret = base.PostData("executeBinMove", req, out response);
                if (!(ret == 0 || ret == 2))
                {
                    //错误返回
                    txtBinCode.SelectAll();
                    txtBinCode.Focus();
                    return;
                }
                //if ("E".Equals(response.SeverityMsgType))
                //{
                //    Message.Err(response.SeverityMsg);
                //    return;
                //}
                txtBinCode.Text = "";
                txtDescBinCode.Text = "";
                listBox1.Items.Clear();
                txtBinCode.ReadOnly = false;
                txtBinCode.Focus();
                txtBinCode.SelectAll();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
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
                this.ClearControl(true, txtBinCode, listBox1, txtDescBinCode);
                txtBinCode.Focus();
                this.listBox1.Items.Clear();
                txtBinCode.SelectAll();
                txtBinCode.ReadOnly = false;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 移動先の棚番入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtDescBinCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if ((e.KeyCode == Keys.Enter))
                {
                    // Enter
                    enter.PerformClick();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
           
        }
    }
}

