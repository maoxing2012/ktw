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
using Entity;

namespace IncomingCheck
{
    /// <summary>
    /// 入荷検品マルチ
    /// </summary>
    public partial class frmCheckMulti : Framework.BaseForm
    {
        private long _asnID;
        /// <summary>
        /// 作业ID
        /// </summary>
        private long _processID;
        private long _skuId;
        private AsnDetail _asnDetail;
        private long _count = 0;

        public frmCheckMulti()
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
                _processID = paras[0].ToLong();
                this.picHead.TitleText = "入荷検品マルチ（{0}）".FormatEx(AppContext.Instance().Owner.name);
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
                    //if (!CheckAsnNumber())
                    //    return;

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

                    if (!GetAsnID(ref _asnID))
                    {
                        txtQrCode.Focus();
                        txtQrCode.SelectAll();
                        return;
                    }

                    //取服务器数据
                    MobileRequest req = GetInputData("getAsnDetailBySku");
                    req.Parameters.Add("asnId", _asnID);
                    req.Parameters.Add("skuId", _skuId);
                    AppContext app = AppContext.Instance();
                    MobileResponse<AsnDetail> response = null;
                    //调用服务端
                    int ret = base.PostData("getAsnDetailBySku", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtQrCode.Focus();
                        txtQrCode.SelectAll();
                        return;
                    }
                    txtQrCode.TabStop = false;
                    txtQrCode.ReadOnly = true;
                    lblStockInfo.Text = "{0}/{1}".FormatEx(response.Results.skuExeQty, response.Results.skuPlanQty);
                    lblSkuName.Text = response.Results.skuName.TrimEx();
                    long csIn = response.Results.csIn;
                    lblCsIn.Text = "アウター入数：{0}".FormatEx(csIn);
                    long blIn = response.Results.blIn;
                    lblBlIn.Text = "インナー入数：{0}".FormatEx(blIn);
                    if (csIn == 0)
                    {
                        txtCs.Text = "0";
                        txtCs.ReadOnly = true;
                        txtCs.TabStop = false;
                        txtCs.Visible = false;
                        if (blIn == 0)
                        {
                            txtPs.Focus();
                            txtPs.SelectAll();
                        }
                        else
                        {
                            txtBl.Focus();
                            txtBl.SelectAll();
                        }
                    }
                    else
                    {
                        txtCs.ReadOnly = false;
                        txtCs.TabStop = true;
                        txtCs.Visible = true;
                        txtCs.Focus();
                        txtCs.SelectAll();
                    }
                    if (blIn == 0)
                    {
                        txtBl.Text = "0";
                        txtBl.ReadOnly = true;
                        txtBl.TabStop = false;
                        txtBl.Visible = false;
                    }
                    else
                    {
                        txtBl.ReadOnly = false;
                        txtBl.TabStop = true;
                        txtBl.Visible = true;
                    }
                    lblCsUnit.Text = response.Results.csUnit.TrimEx();
                    lblBlUnit.Text = response.Results.blUnit.TrimEx();
                    lblPsUnit.Text = response.Results.psUnit.TrimEx();
                    //总PS数=cs数*cs入+bl数*bl入+ps数
                    lblQyCount.Text = "総数：{0}".FormatEx(0);
                    lblFirst.Visible = response.Results.isFirst;
                    if (response.Results.expDate.IsDate("yyyyMMdd"))
                    {
                        txtExpDate.Text = response.Results.expDate;
                    }
                    _asnDetail = response.Results;
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        bool GetAsnID(ref long asnID)
        {
            //取服务器数据
            MobileRequest req = GetInputData("getAsnIdBySku4Multi");
            req.Parameters.Add("processId", _processID);
            req.Parameters.Add("skuId", _skuId);
            AppContext app = AppContext.Instance();
            MobileResponse<ASNInfos> response = null;
            //调用服务端
            int ret = base.PostData("getAsnIdBySku4Multi", req, out response);
            if (!(ret == 0 || ret == 2))
            {
                //错误返回
                return false;
            }
            if (response.Results.asnList == null || response.Results.asnList.Count == 0)
            {
                lblAsnNumber.Text = response.Results.asnNumber;
                lblReceiveInfo.Text = "{0}/{1}".FormatEx(response.Results.exeQty,response.Results.planQty);
                asnID = response.Results.asnId;
            }
            else
            {
                ASNInfo result = BizUtil.QueryList(response.Results.asnList, "伝票選択") as ASNInfo;
                if (result == null)
                    return false;
                lblAsnNumber.Text = result.asnNumber;
                lblReceiveInfo.Text = "{0}/{1}".FormatEx(result.executeQty, result.planQty);
                asnID = result.asnId;
            }
            return true;
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
                this.ClearControl(true, lblAsnNumber, txtQrCode, txtBl, txtCs, txtPs, txtExpDate, lblReceiveInfo, lblStockInfo, lblSkuName, lblCsIn, lblBlIn, lblCsUnit, lblBlUnit, lblPsUnit, lblQyCount);
                txtQrCode.Focus();
                txtQrCode.SelectAll();
                //txtAsnNumber.ReadOnly = false;
                txtQrCode.ReadOnly = false;
                //txtAsnNumber.TabStop = true;
                txtQrCode.TabStop = true;
                _skuId = 0;
                _asnID = 0;
                _asnDetail = null;
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

                if (_count <= 0)
                {
                    txtCs.SelectAll();
                    txtCs.Focus();
                    Message.Warn("CS数/BL数/PS数を入力してください。");
                    return;
                }

                if (!CheckExpDate(true))
                    return;

                //取服务器数
                MobileRequest req = GetInputData("receiveAsnDetail");
                req.Parameters.Add("asnDetailId", _asnDetail.asnDetailId);
                req.Parameters.Add("qty", _count);
                req.Parameters.Add("expDate", txtExpDate.Text);
                AppContext app = AppContext.Instance();
                MobileResponse<AsnDetail> response = null;
                //调用服务端  
                EnableButtons(false);
                int ret = base.PostData("receiveAsnDetail", req, out response);
                if (!(ret == 0 || ret == 2))
                    //错误返回
                    return;

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
                }

                this.ClearControl(true, txtExpDate, txtCs, txtBl, txtPs);
                txtQrCode.ReadOnly = false;
                txtQrCode.TabStop = true;
                lblFirst.Visible = false;
                //if (response.Results.exeQty < response.Results.planQty)
                //{
                lblReceiveInfo.Text = "{0}/{1}".FormatEx(response.Results.exeQty, response.Results.planQty);
                lblStockInfo.Text = "{0}/{1}".FormatEx(response.Results.skuExeQty, response.Results.skuPlanQty);
                txtQrCode.Focus();
                txtQrCode.SelectAll();
                //}
                //else
                //{
                //    this.ClearControl(true, lblAsnNumber, lblReceiveInfo, txtQrCode, lblStockInfo, lblSkuName, lblQyCount
                //        , lblCsIn, lblBlIn, lblCsUnit, lblBlUnit, lblPsUnit);
                //    //txtAsnNumber.ReadOnly = false;
                //    //txtAsnNumber.TabStop = true;
                //    //txtAsnNumber.Focus();
                //    //txtAsnNumber.SelectAll();
                //}
                if ("M1".Equals(response.SeverityMsgType))
                {
                    this.DialogResult = DialogResult.OK;
                }
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
        /// 计算总数
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void excute_count(object sender, EventArgs e)
        {
            try
            {
                _count = (StringUtil.ParseLong(txtBl.Text) * _asnDetail.blIn + StringUtil.ParseLong(txtCs.Text) * _asnDetail.csIn + StringUtil.ParseLong(txtPs.Text));
                lblQyCount.Text = "総数:" + Convert.ToString(_count);
            }
            catch
            {
            }
        }

        ///// <summary>
        ///// 入荷伝票番号入力
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void txtAsnNumber_KeyDown(object sender, KeyEventArgs e)
        //{
        //    try
        //    {
        //        if (e.KeyCode == Keys.Enter)
        //        {
        //            EnableButtons(false);
        //            //取服务器数
        //            //入荷伝票番号
        //            if (!CheckAsnNumber())
        //                return;

        //            MobileRequest req = GetInputData("searchAsn");
        //            AppContext app = AppContext.Instance();
        //            MobileResponse<Dictionary<string, object>> response = null;
        //            //调用服务端        
        //            int ret = base.PostData("searchAsn", req, out response);
        //            if (!(ret == 0 || ret == 2))
        //            {
        //                //错误返回
        //                txtAsnNumber.Focus();
        //                txtAsnNumber.SelectAll();
        //                return;
        //            }

        //            //Display data
        //            lblReceiveInfo.Text = "{0}/{1}".FormatEx(response.Results["exeQty"].TrimDouble(), response.Results["planQty"].TrimDouble());
        //            _asnID = response.Results["asnId"].ToLong();
        //            txtAsnNumber.ReadOnly = true;
        //            txtQrCode.Focus();
        //            txtQrCode.SelectAll();
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        ExceptionPolicy.HandleException(ex);
        //    }
        //    finally
        //    {
        //        EnableButtons(true);
        //    }
        //}

        /// <summary>
        /// 入荷伝票番号
        /// </summary>
        /// <returns></returns>
        private bool CheckAsnNumber()
        {
            if (lblAsnNumber.Text.IsEmpty())
            {
                //{0}を入力して下さい。
                Message.Warn(AppContext.Instance().GetMessage("field.must.input").FormatEx("入荷伝票番号"));
                txtQrCode.Focus();
                txtQrCode.SelectAll();
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

            //在庫商品の賞味期限より古いですが、入荷しますか？】
            if (!_asnDetail.stockExpDate.IsEmpty() && commit)
            {
                if (string.Compare(txtExpDate.Text, _asnDetail.stockExpDate) < 0)
                {
                    if (Message.Ask("在庫商品の賞味期限より古いですが、入荷しますか。") != DialogResult.Yes)
                    {
                        txtExpDate.Focus();
                        txtExpDate.SelectAll();
                        return false;
                    }
                }
            }
            return true;
        }

        /// <summary>
        /// 荷札再発行
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnReprint_Click(object sender, EventArgs e)
        {
            try
            {
                using (frmReprint frmNext = new frmReprint())
                {
                    frmNext.Init(false);
                    DialogResult ret = frmNext.ShowDialog();
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

