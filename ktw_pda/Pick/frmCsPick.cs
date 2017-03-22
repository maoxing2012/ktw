using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;
using System.Threading;

namespace Pick
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmCsPick : Framework.BaseForm
    {
        //1：单条   2：多条
        private int pageType = 1;
        private string _binCode;
        private string _barCodes;
        private string _qty;
        private string _caseNumbers;
        private int _scaned = 0;
        private int _scanNum = 0;
        public frmCsPick()
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
                this.picHead.TitleText = "ケースピッキング（{0}）".FormatEx(AppContext.Instance().Owner.name);
                BtnOk.Enabled = false;
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
        /// 清除
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnClear_Click(object sender, EventArgs e)
        {
            try
            {
                if (pageType == 1)
                {
                    //清空控件
                    this.ClearControl(true, txtCaseNumber3, txtBarcode, txtBinCode, txtQty, lblCaseCount, lblBinCode, lblSkuCode, lblSkuName, lblExpDate, lblQty, lblUnit, lblSpecs, lblScanQty, lblInfo);
                    txtCaseNumber3.Focus();
                    txtCaseNumber3.SelectAll();
                    BtnOk.Enabled = false;
                }
                else if (pageType == 2)
                {
                    //清空控件
                    this.ClearControl(true, txtCaseNumber1, txtCaseNumber2, txtBarcode, txtBinCode, txtQty, lblCaseCount, lblBinCode, lblSkuCode, lblSkuName, lblExpDate, lblQty, lblUnit, lblSpecs, lblInfo);
                    txtCaseNumber1.Focus();
                    txtCaseNumber1.SelectAll();
                }
                _scaned = 0;
                _scanNum = 0;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 切换
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnChange_Click(object sender, EventArgs e)
        {
            if (pageType == 1) {
                BtnChange.Text = "F2 シングル";
                txtCaseNumber3.Hide();
                txtCaseNumber1.Show();
                txtCaseNumber2.Show();
                txtCaseNumber1.SelectAll();
                txtCaseNumber1.Focus();
                //pnlInfo.Location = new Point( 3, 93);
                txtBarcode.Width = 93;
                txtQty.Visible = true;
                BtnOk.Enabled = true;
                pageType = 2;
            }
            else if (pageType == 2) {
                BtnChange.Text = "F2 マルチ";
                txtCaseNumber3.Show();
                txtCaseNumber1.Hide();
                txtCaseNumber2.Hide();
                txtCaseNumber3.SelectAll();
                txtCaseNumber3.Focus();
                txtBarcode.Width = 119;
                txtQty.Visible = false;
                //pnlInfo.Location = new Point(3, 61);
                pageType = 1;
                BtnOk.Enabled = false;
                _scaned = 0;
            }
        }

        /// <summary>
        /// 個口番号入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtCaseNumber2_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (StringUtil.IsEmpty(txtCaseNumber1.Text))
                    {
                        txtCaseNumber1.SelectAll();
                        txtCaseNumber1.Focus();
                        Message.Warn("個口番号を入力してください");
                        return;
                    }
                    if (StringUtil.IsEmpty(txtCaseNumber2.Text))
                    {
                        txtCaseNumber2.SelectAll();
                        txtCaseNumber2.Focus();
                        Message.Warn("個口番号を入力してください");
                        return;
                    }
                    MobileRequest req = GetInputData("getCsPickInfo");
                    req.Parameters.Add("caseNumberFrom", txtCaseNumber1.Text);
                    req.Parameters.Add("caseNumberTo", txtCaseNumber2.Text);
                    MobileResponse<Dictionary<string, object>> response = null;
                    //调用服务端        
                    int ret = base.PostData("getCsPickInfo", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtCaseNumber2.SelectAll();
                        txtCaseNumber2.Focus();
                        return;
                    }
                    //if ("E".Equals(response.SeverityMsgType))
                    //{
                    //    Message.Err(response.SeverityMsg);
                    //    txtCaseNumber1.SelectAll();
                    //    txtCaseNumber1.Focus();
                    //    return;
                    //}
                    lblCaseCount.Text = response.Results["caseQty"].TrimEx();
                    lblBinCode.Text = response.Results["binCode"].TrimEx();
                    lblSkuCode.Text = response.Results["skuCode"].TrimEx();
                    lblSkuName.Text = response.Results["skuName"].TrimEx();
                    lblExpDate.Text = response.Results["expDate"].TrimEx();
                    lblQty.Text = response.Results["qty"].TrimEx();
                    lblUnit.Text = response.Results["unit"].TrimEx();
                    lblSpecs.Text = response.Results["specs"].TrimEx();
                    _barCodes = response.Results["barcodes"].TrimEx();
                    _binCode = response.Results["binCode"].TrimEx();
                    _qty = response.Results["qty"].TrimEx();
                    _caseNumbers = response.Results["caseNumbers"].TrimEx();
                    txtBinCode.SelectAll();
                    txtBinCode.Focus();

                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 個口番号入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtCaseNumber3_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (StringUtil.IsEmpty(txtCaseNumber3.Text))
                    {
                        txtCaseNumber3.SelectAll();
                        txtCaseNumber3.Focus();
                        Message.Warn("個口番号を入力してください");
                        return;
                    }
                    MobileRequest req = GetInputData("getCsPickInfo");
                    req.Parameters.Add("caseNumberFrom", txtCaseNumber3.Text);
                    MobileResponse<Dictionary<string, object>> response = null;
                    //调用服务端        
                    int ret = base.PostData("getCsPickInfo", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtCaseNumber3.SelectAll();
                        txtCaseNumber3.Focus();
                        return;
                    }
                    //if ("E".Equals(response.SeverityMsgType))
                    //{
                    //    Message.Err(response.SeverityMsg);
                    //    txtCaseNumber3.SelectAll();
                    //    txtCaseNumber3.Focus();
                    //    return;
                    //}
                    lblCaseCount.Text = response.Results["caseQty"].TrimEx();
                    lblBinCode.Text = response.Results["binCode"].TrimEx();
                    lblSkuCode.Text = response.Results["skuCode"].TrimEx();
                    lblSkuName.Text = response.Results["skuName"].TrimEx();
                    lblExpDate.Text = response.Results["expDate"].TrimEx();
                    lblQty.Text = response.Results["qty"].TrimEx();
                    lblUnit.Text = response.Results["unit"].TrimEx();
                    lblSpecs.Text = response.Results["specs"].TrimEx();
                    lblInfo.Text = response.Results["warningMsg"].TrimEx();
                    lblScanQty.Text = response.Results["scanNum"].TrimEx();
                    _barCodes = response.Results["barcodes"].TrimEx();
                    _binCode = response.Results["binCode"].TrimEx();
                    _qty =response.Results["qty"].TrimEx();
                    _scanNum = response.Results["scanNum"].ToInt(); 
                    _caseNumbers = response.Results["caseNumbers"].TrimEx();
                    txtBinCode.SelectAll();
                    txtBinCode.Focus();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 個口番号入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtCaseNumber1_KeyDown(object sender, KeyEventArgs e)
        {
             try
             {
                 if (e.KeyCode == Keys.Enter)
                 {
                    if (StringUtil.IsEmpty(txtCaseNumber1.Text))
                    {
                        txtCaseNumber1.SelectAll();
                        txtCaseNumber1.Focus();
                        Message.Warn("個口番号を入力してください");
                        return;
                    }
                 }
             }
             catch (Exception ex)
             {
                 ExceptionPolicy.HandleException(ex);
             }
        }

        /// <summary>
        /// 棚番号入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBinCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (!_binCode.Equals(txtBinCode.Text))
                    {
                        txtBinCode.SelectAll();
                        txtBinCode.Focus();
                        Message.Warn("棚番が一致していません。");
                        return;
                    }
                    txtBarcode.SelectAll();
                    txtBarcode.Focus();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 商品バーコード入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBarcode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (pageType == 1)
                    {
                        if (BtnOk.Enabled)
                        {
                            BtnOk.PerformClick();
                            return;
                        }
                    }
                    if (StringUtil.IsEmpty(txtBarcode.Text))
                    {
                        txtBarcode.SelectAll();
                        txtBarcode.Focus();
                        if (_scaned == 0)
                            Message.Warn("商品コードを入力してください。");
                        else
                        {
                            Message.Warn("バーコードを2回スキャンしてください。");
                            _scaned = 0;
                        }
                        return;
                    }

                    string[] _array = _barCodes.Split(new char[1]{','});
                    int existFlg = 0;
                    foreach (string code in _array) {
                        if (code.Equals(txtBarcode.Text)) {
                            existFlg = 1;
                        } 
                    }
                    if (existFlg == 0)
                    {
                        txtBarcode.SelectAll();
                        txtBarcode.Focus();
                        Message.Warn("商品が一致していません。");
                        _scaned = 0;
                        return;
                    }
                    if (pageType == 2)
                    {
                        txtQty.SelectAll();
                        txtQty.Focus();
                    }
                    else
                    {
                        _scaned += 1;
                        if (_scaned == _scanNum)
                        {
                            txtBarcode.Focus();
                            txtBarcode.SelectAll();
                            BtnOk.Enabled = true;
                        }
                        else
                        {
                            txtBarcode.Text = string.Empty;
                            txtBarcode.Focus();
                            txtBarcode.SelectAll();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 数量入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtQty_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (!_qty.Equals(txtQty.Text))
                    {
                        txtQty.SelectAll();
                        txtQty.Focus();
                        Message.Warn("ピック数が違います");
                        return;
                    }
                    BtnOk.PerformClick();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// Enter
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnOk_Click(object sender, EventArgs e)
        {
            try
            {
                if (!_binCode.Equals(txtBinCode.Text))
                {
                    txtBinCode.SelectAll();
                    txtBinCode.Focus();
                    Message.Warn("棚番が一致していません。");
                    return;
                }
                if (StringUtil.IsEmpty(txtBarcode.Text))
                {
                    txtBarcode.SelectAll();
                    txtBarcode.Focus();
                    Message.Warn("商品が一致していません。");
                    return;
                }
                if (pageType == 2 && !lblQty.Text.Equals(txtQty.Text))
                {
                    txtQty.SelectAll();
                    txtQty.Focus();
                    Message.Warn("ピック数が違います");
                    return;
                }

                if (Message.Ask(string.Format("{0}{1}ピッキング確定します。よろしいですか？".FormatEx(lblQty.Text, lblUnit.Text))) != DialogResult.Yes)
                    return;

                MobileRequest req = GetInputData("executeCsPick");
                req.Parameters.Add("caseNumbers", _caseNumbers);
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端        
                int ret = base.PostData("executeCsPick", req, out response);
                if (!(ret == 0 || ret == 2))
                //错误返回
                {
                    txtBinCode.SelectAll();
                    txtBinCode.Focus();
                    return;
                }
                //if ("E".Equals(response.SeverityMsgType))
                //{
                //    Message.Err(response.SeverityMsg);
                //    txtBinCode.SelectAll();
                //    txtBinCode.Focus();
                //    return;
                //}
                BtnClear.PerformClick();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private void frmCsPick_Load(object sender, EventArgs e)
        {
            try
            {
                AppContext.Instance().GetScanner().GetBarCode = new GetBarCodeEventHandler(GetBarCode);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private void frmCsPick_Closed(object sender, EventArgs e)
        {
            try
            {
                AppContext.Instance().GetScanner().GetBarCode = null;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        void GetBarCode(object sender, ScanArgs e)
        {
            try
            {
                DNWA.BHTCL.Beep beepSetting = new DNWA.BHTCL.Beep();
                int iDevice = 0;
                bool flag = false;

                if (txtBarcode.Focused)
                {
                    if (_scanNum == 2 && pageType == 1)
                    {
                        flag = true;
                    }
                }
                if (flag)
                {
                    Thread.Sleep(500);
                    iDevice = (int)DNWA.BHTCL.Beep.Settings.EN_DEVICE.BUZZER + (int)DNWA.BHTCL.Beep.Settings.EN_DEVICE.VIBRATOR;
                    beepSetting[(DNWA.BHTCL.Beep.Settings.EN_DEVICE)iDevice] = DNWA.BHTCL.Beep.EN_CTRL.OFF;                         // Buzzer/vibration stop

                    // Sets or acquires the ON/OFF duration of the beeper or vibrator.
                    // Property
                    //   ON duration of the beeper or vibrator (in units of 100 msec)
                    beepSetting.OnTime = 5;

                    // Sets or acquires the OFF duration of the beeper or vibrator.
                    // Property
                    //   OFF duration of the beeper or vibrator (in units of 100 msec)
                    beepSetting.OffTime = 1;

                    // Sets or acquires the beeping frequency of the beeper
                    // Property
                    //   Beeping frequency of the beeper (Hz)
                    beepSetting.Frequency = 32767;

                    // Sets or acquires the number of beeps or vibrations of the beeper or vibrator
                    // Property
                    //   Number of beeps or vibrations of the beeper or vibrator
                    beepSetting.Count = 2;

                    // Starts or stops the beeping or vibrating of the device specified by the index
                    // Parameters
                    //   device : Beep device. As listed in EN_DEVICE (one of the values or a combination of the values)
                    // Property
                    //   Status of the beeper or vibrator. As listed in EN_CTRL
                    beepSetting[DNWA.BHTCL.Beep.Settings.EN_DEVICE.BUZZER] = DNWA.BHTCL.Beep.EN_CTRL.ON;
                    txtBarcode.Text = e.Barcode;
                    txtBarcode_KeyDown(txtBarcode, new KeyEventArgs(Keys.Enter));
                }
                else
                    Denso.Win32.SendKeys(e.Barcode, true);
                
            }
            catch
            {
                //
            }
        }
    }
}

