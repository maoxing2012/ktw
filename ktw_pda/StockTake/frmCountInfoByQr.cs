using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace StockTake
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmCountInfoByQr : Framework.BaseForm
    {
        private long countRecordId;
        private long binId;
        private long csIn;
        private long blIn;
        private long skuId;
        private long countPlanId;
        CountPlan info;
        private long count = -1;
        public frmCountInfoByQr()
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
                this.picHead.TitleText = "実棚数登録（{0}）".FormatEx(AppContext.Instance().Owner.name);
                info = paras[0] as CountPlan;
                countPlanId = info.id;
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
        /// QRコード
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtQrCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (StringUtil.IsEmpty(txtBinCode.Text))
                    {
                        txtBinCode.SelectAll();
                        txtBinCode.Focus();
                        Message.Warn("現棚番を入力してください");
                        return;
                    }
                    if (StringUtil.IsEmpty(txtQrCode.Text))
                    {
                        txtQrCode.SelectAll();
                        txtQrCode.Focus();
                        Message.Warn("QRコードを入力してください");
                        return;
                    }
                    //取服务器数据
                    MobileRequest req = GetInputData("getCountInfoByQr");
                    req.PageId = null;
                    req.Parameters.Add("binCode", txtBinCode.Text);
                    req.Parameters.Add("qrCode", txtQrCode.Text);
                    req.Parameters.Add("countPlanId", countPlanId);
                    AppContext app = AppContext.Instance();
                    MobileResponse<CountInfo> response = null;
                    //调用服务端
                    int ret = base.PostData("getCountInfoByQr", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtQrCode.Focus();
                        txtQrCode.SelectAll();
                        return;
                    }
                    setPageData(response);              

                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }


        /// <summary>
        /// 現棚番入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBinCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    //取服务器数据
                    MobileRequest req = GetInputData("checkBin4Count");
                    req.Parameters.Add("binCode", txtBinCode.Text);
                    req.Parameters.Add("countPlanId", countPlanId);
                    AppContext app = AppContext.Instance();
                    MobileResponse<CountInfo> response = null;
                    //调用服务端
                    int ret = base.PostData("checkBin4Count", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtBinCode.Focus();
                        txtBinCode.SelectAll();
                        return;
                    }
                    txtQrCode.SelectAll();
                    txtQrCode.Focus();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private void setPageData(MobileResponse<CountInfo> response)
        {
            //if ("E".Equals(response.SeverityMsgType)) {
            //    Message.Err(response.SeverityMsg);
            //    return;
            //}
            txtPs.Focus();
            txtPs.SelectAll();
            if (response.Results.blIn > 0)
            {
                txtBl.Focus();
                txtBl.SelectAll();
                this.txtBl.ReadOnly = false;
                this.txtBl.TabStop = true;
            }
            else
            {
                this.txtBl.Text = "0";
                this.txtBl.ReadOnly = true;
                this.txtBl.TabStop = false;
            }

            if (response.Results.csIn > 0)
            {
                txtCs.Focus();
                txtCs.SelectAll();
                this.txtCs.ReadOnly = false;
                this.txtCs.TabStop = true;
            }
            else
            {
                txtCs.Text = "0";
                this.txtCs.ReadOnly = true;
                this.txtCs.TabStop = false;
            }
           
            this.lblSkuCode.Text = response.Results.skuCode.TrimEx();
            this.lblExpDate.Text = response.Results.expDate.TrimEx();
            this.lblSkuName.Text = response.Results.skuName.TrimEx();
            this.lblCsIn.Text = "アウター入数:" + response.Results.csIn.TrimEx();
            this.lblBlIn.Text = "インナー入数:" + response.Results.blIn.TrimEx();
            this.lblCsUnit.Text = response.Results.csUnit.TrimEx();
            this.lblBlUnit.Text = response.Results.blUnit.TrimEx();
            this.lblPsUnit.Text = response.Results.psUnit.TrimEx();
            this.lblQtyInfo.Text = response.Results.qtyInfo.TrimEx();
            this.lblInvStatus.Text = response.Results.invStatus.TrimEx();
            blIn = response.Results.blIn;
            csIn = response.Results.csIn;
            countRecordId = response.Results.countRecordId;
            binId = response.Results.binId;
            skuId = response.Results.skuId;
            txtBinCode.ReadOnly = true;
            txtQrCode.ReadOnly = true;
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
                this.ClearControl(true, txtBinCode, txtQrCode, txtBl, txtCs, txtPs, lblSkuCode, lblExpDate, lblSkuName, lblCsIn, lblBlIn, lblCsUnit, lblBlUnit, lblPsUnit, lblQtyInfo, lblQyCount,lblInvStatus);
                txtBinCode.Focus();
                txtBinCode.SelectAll();
                txtBinCode.ReadOnly = false;
                txtQrCode.ReadOnly = false;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private void enter_Click(object sender, EventArgs e)
        {
            try
            {
                if (StringUtil.IsEmpty(txtBinCode.Text))
                {
                    txtBinCode.SelectAll();
                    txtBinCode.Focus();
                    Message.Warn("現棚番を入力してください");
                    return;
                }
                if (StringUtil.IsEmpty(txtQrCode.Text))
                {
                    txtQrCode.SelectAll();
                    txtQrCode.Focus();
                    Message.Warn("QRコードを入力してください");
                    return;
                }
                if (count < 0)
                {
                    txtCs.SelectAll();
                    txtCs.Focus();
                    Message.Warn("総数を入力してください");
                    return;
                }
                //取服务器数据
                MobileRequest req = GetInputData("executeCount");
                req.PageId = null;
                req.Parameters.Add("countPlanId", countPlanId);
                req.Parameters.Add("binId", binId);
                req.Parameters.Add("qrCode", txtQrCode.Text);
                req.Parameters.Add("qty", count);
                AppContext app = AppContext.Instance();
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端
                int ret = base.PostData("executeCount", req, out response);
                if (!(ret == 0 || ret == 2))
                    //错误返回
                    return;
                //if ("E".Equals(response.SeverityMsgType))
                //{
                //    Message.Err(response.SeverityMsg);
                //    return;
                //}
                txtBinCode.ReadOnly = false;
                txtQrCode.ReadOnly = false;
                //清空控件
                this.ClearControl(true, txtBinCode, txtQrCode, txtBl, txtCs, txtPs, lblSkuCode, lblExpDate, lblSkuName, lblCsIn, lblBlIn, lblCsUnit, lblBlUnit, lblPsUnit, lblQtyInfo, lblQyCount,lblInvStatus);
                txtBinCode.Focus();
                txtBinCode.SelectAll();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private void excute_count(object sender, EventArgs e)
        {
            count = (StringUtil.ParseLong(txtBl.Text) * blIn + StringUtil.ParseLong(txtCs.Text) * csIn + StringUtil.ParseLong(txtPs.Text));
            lblQyCount.Text = "実棚数:" + Convert.ToString(count);
        }

        private void txtPs_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
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

