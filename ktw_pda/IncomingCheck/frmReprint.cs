using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;
using sato.pt;
using BusinessUtil;

namespace IncomingCheck
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmReprint : Framework.BaseForm
    {
        private long _skuId;

        public frmReprint()
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
                this.picHead.TitleText = "入荷荷札再発行（{0}）".FormatEx(AppContext.Instance().Owner.name);
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
                    if (txtQrCode.ReadOnly)
                    {
                        txtExpDate.Focus();
                        txtExpDate.SelectAll();
                        return;
                    }
                    if (!CheckAsnNumber())
                        return;

                    if (!CheckQrCode())
                        return;

                    StockResult result = BizUtil.QueryStock(txtQrCode.Text);
                    if (result.ActionResult != ActionResult.SelectOne)
                    {
                        txtQrCode.Focus();
                        txtQrCode.SelectAll();
                        return;
                    }
                    _skuId = result.Result.id;
                    txtQrCode.TabStop = false;
                    txtQrCode.ReadOnly = true;
                    lblSkuName.Text = result.Result.name;
                    txtExpDate.Focus();
                    txtExpDate.SelectAll();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// QRコード
        /// </summary>
        private bool CheckQrCode()
        {
            if (StringUtil.IsEmpty(txtQrCode.Text))
            {
                Message.Warn("QRコードを入力してください");
                txtQrCode.SelectAll();
                txtQrCode.Focus();
                return false;
            }
            return true;
        }

        ///// <summary>
        /////画面赋值
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void setPageData(MobileResponse<InvInfoQrList> response)
        //{
        //    if ("E".Equals(response.SeverityMsgType))
        //    {
        //        Message.Err(response.SeverityMsg);
        //        return;
        //    }
        //    this.txtPs.SelectAll();
        //    this.txtPs.Focus();
        //    if (response.Results.blIn > 0)
        //    {
        //        this.txtBl.ReadOnly = false;
        //        this.txtBl.SelectAll();
        //        this.txtBl.Focus();
        //    }
        //    if (response.Results.csIn > 0)
        //    {
        //        this.txtCs.ReadOnly = false;
        //        this.txtCs.SelectAll();
        //        this.txtCs.Focus();
        //    }

        //    this.lblReceiveInfo.Text = response.Results.skuCode.TrimEx();
        //    this.lblStockInfo.Text = response.Results.expDate.TrimEx();
        //    this.lblSkuName.Text = response.Results.skuName.TrimEx();
        //    this.lblCsIn.Text = "CS入:" + response.Results.csIn.TrimEx();
        //    this.lblBlIn.Text = "BL入:" + response.Results.blIn.TrimEx();
        //    this.lblCsUnit.Text = response.Results.csUnit.TrimEx();
        //    this.lblBlUnit.Text = response.Results.blUnit.TrimEx();
        //    this.lblPsUnit.Text = response.Results.psUnit.TrimEx();
        //    this.lblQtyInfo.Text = response.Results.qtyInfo.TrimEx();
        //    blIn = response.Results.blIn;
        //    csIn = response.Results.csIn;
        //    invId = response.Results.invId;
        //    binId = response.Results.binId;
        //    skuId = response.Results.skuId;
        //    invStatus = response.Results.invStatus;
        //    txtAsnNumber.ReadOnly = true;
        //    txtQrCode.ReadOnly = true;
        //}

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
                this.ClearControl(true, txtAsnNumber, txtQrCode, txtExpDate,  lblSkuName, lblCsIn, lblBlIn);
                txtAsnNumber.Focus();
                txtAsnNumber.SelectAll();
                txtAsnNumber.ReadOnly = false;
                txtQrCode.ReadOnly = false;
                txtAsnNumber.TabStop = true;
                txtQrCode.TabStop = true;
                _skuId = 0;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// Enter 確定
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void enter_Click(object sender, EventArgs e)
        {
            try
            {

                if (!CheckAsnNumber())
                    return;

                if (!CheckQrCode())
                    return;

                if (_skuId <= 0 || !txtQrCode.ReadOnly)
                {
                    Message.Warn("QRコードは不正です。");
                    txtQrCode.Focus();
                    txtQrCode.SelectAll();
                    return;
                }

                if (!CheckExpDate(true))
                    return;

                //取服务器数
                MobileRequest req = GetInputData("receiveAsnDetail");
                req.Parameters.Add("asnNumber", txtAsnNumber.Text);
                req.Parameters.Add("skuId", _skuId);
                req.Parameters.Add("expDate", txtExpDate.Text);
                AppContext app = AppContext.Instance();
                MobileResponse<AsnDetail> response = null;
                //调用服务端  
                EnableButtons(false);
                int ret = base.PostData("printAsnLabel", req, out response);
                if (!(ret == 0 || ret == 2))
                {
                    //错误返回
                    txtExpDate.Focus();
                    txtExpDate.SelectAll();
                    return;
                }

                //入荷荷札を印刷しますか？
                //if (Message.Ask("入荷荷札を印刷しますか？") == DialogResult.Yes)
                //{
                //    //入荷荷札を印刷します
                //    PrintBill(response.Results.label);
                //}
                EnableButtons(true);
                using (frmComfirm frmNext = new frmComfirm())
                {
                    ret = frmNext.Init(false);
                    if (frmNext.ShowDialog() == DialogResult.OK)
                    {
                        //入荷荷札を印刷します
                        EnableButtons(false);
                        PrintUtil.PrintBill(response.Results.label, frmNext.Times);
                    }
                    else
                        return;
                }
                
                btnClear.PerformClick();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                EnableButtons(true);
            }
        }

        /// <summary>
        /// 入荷伝票番号
        /// </summary>
        /// <returns></returns>
        private bool CheckAsnNumber()
        {
            if (txtAsnNumber.Text.IsEmpty())
            {
                //{0}を入力して下さい。
                Message.Warn(AppContext.Instance().GetMessage("field.must.input").FormatEx("入荷伝票番号"));
                txtAsnNumber.Focus();
                txtAsnNumber.SelectAll();
                return false;
            }
            return true;
        }

        /// <summary>
        /// 賞味期限
        /// </summary>
        /// <returns></returns>
        private bool CheckExpDate(bool commit)
        {
            if (txtExpDate.Text.IsEmpty())
            {
                //{0}を入力して下さい。
                Message.Warn(AppContext.Instance().GetMessage("field.must.input").FormatEx("賞味期限"));
                txtExpDate.Focus();
                txtExpDate.SelectAll();
                return false;
            }

            if (!txtExpDate.Text.IsDate("yyyyMMdd"))
            {
                //{0}を入力して下さい。
                Message.Warn("賞味期限(YYYYMMDD)が不正です。");
                txtExpDate.Focus();
                txtExpDate.SelectAll();
                return false;
            }
            return true;
        }

        /// <summary>
        /// 移動先棚番入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtDescBinCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (!CheckExpDate(false))
                        return;
                    btnOk.PerformClick();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}

