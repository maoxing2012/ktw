  using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace Pick
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmNextPsPick : Framework.BaseForm
    {
        private string _binCode;
        private string _barCodes;
        private string _qty;
        private string _qtyA;
        private string _qtyB;
        private string _qtyC;
        private string _qtyD;
        private string _qtyE;
        private string _qtyF;
        private string _qtyG;
        private string _qtyH;
        private string _qtyI;
        private string _skuCode;
        private string _skuname;
        private string _expDate;
        private string _processId;
        private string _binId;
        private string _skuId;
        private PickInfo _pick;
        public frmNextPsPick()
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
            int retFlg = 0;
            if (!back)
            {
                //基类命令控件初始化
                retFlg = base.Init(back, paras);
                if (retFlg != 0)
                    return retFlg;
                _processId = paras[0].TrimEx();
            }
            MobileRequest req = GetInputData("getNextPickInfo");
            req.Parameters.Add("processId", StringUtil.ParseLong(_processId));
            MobileResponse<Dictionary<string, object>> response = null;
            //调用服务端        
            int ret = base.PostData("getNextPickInfo", req, out response);
            if (!(ret == 0 || ret == 2))
                //错误返回
                return ret;
            _pick = new PickInfo();
            lblCount.Text = response.Results["exeQty"].TrimEx() + "/" + response.Results["planQty"].TrimEx();
            lblBinCode.Text = response.Results["binCode"].TrimEx();
            lblSkuCode.Text = response.Results["skuCode"].TrimEx();
            lblSkuName.Text = response.Results["skuName"].TrimEx();
            lblExpDate.Text = response.Results["expDate"].TrimEx();
            lblQty.Text = response.Results["qtyInfo"].TrimEx();
            lblSpecs.Text = response.Results["specs"].TrimEx();
            _barCodes = response.Results["barcodes"].TrimEx();
            _binCode = response.Results["binCode"].TrimEx();
            _qty = response.Results["qty"].TrimEx();
            _qtyA = response.Results["qtyA"].TrimEx();
            _qtyB = response.Results["qtyB"].TrimEx();
            _qtyC = response.Results["qtyC"].TrimEx();
            _qtyD = response.Results["qtyD"].TrimEx();
            _qtyE = response.Results["qtyE"].TrimEx();
            _qtyF = response.Results["qtyF"].TrimEx();
            _qtyG = response.Results["qtyG"].TrimEx();
            _qtyH = response.Results["qtyH"].TrimEx();
            _qtyI = response.Results["qtyI"].TrimEx();

            _pick.warnMsg4Ps = response.Results["warnMsg4Ps"].TrimEx();
            _pick.qtyHyphenA=  response.Results["qtyHyphenA"].TrimEx();
            _pick.qtyHyphenB = response.Results["qtyHyphenB"].TrimEx();
            _pick.qtyHyphenC = response.Results["qtyHyphenC"].TrimEx();
            _pick.qtyHyphenD = response.Results["qtyHyphenD"].TrimEx();
            _pick.qtyHyphenE = response.Results["qtyHyphenE"].TrimEx();
            _pick.qtyHyphenF = response.Results["qtyHyphenF"].TrimEx();
            _pick.qtyHyphenG = response.Results["qtyHyphenG"].TrimEx();
            _pick.qtyHyphenH = response.Results["qtyHyphenH"].TrimEx();
            _pick.qtyHyphenI = response.Results["qtyHyphenI"].TrimEx();

            _pick.qtyInfoA = response.Results["qtyInfoA"].TrimEx();
            _pick.qtyInfoB = response.Results["qtyInfoB"].TrimEx();
            _pick.qtyInfoC = response.Results["qtyInfoC"].TrimEx();
            _pick.qtyInfoD = response.Results["qtyInfoD"].TrimEx();
            _pick.qtyInfoE = response.Results["qtyInfoE"].TrimEx();
            _pick.qtyInfoF = response.Results["qtyInfoF"].TrimEx();
            _pick.qtyInfoG = response.Results["qtyInfoG"].TrimEx();
            _pick.qtyInfoH = response.Results["qtyInfoH"].TrimEx();
            _pick.qtyInfoI = response.Results["qtyInfoI"].TrimEx();

            _skuCode = response.Results["skuCode"].TrimEx();
            _skuname = response.Results["skuName"].TrimEx();
            _expDate = response.Results["expDate"].TrimEx();
            _binId = response.Results["binId"].TrimEx();
            _skuId = response.Results["skuId"].TrimEx();
            lblWarnMsg4Ps.Visible =! _pick.warnMsg4Ps.IsEmpty();
            lblWarnMsg4Ps.Text = _pick.warnMsg4Ps;
            this.picHead.TitleText = "バラピッキング（{0}）".FormatEx(AppContext.Instance().Owner.name);

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
                //清空控件
                this.ClearControl(true, txtBarcode, txtBinCode, txtQty);
                txtBinCode.Focus();
                txtBinCode.SelectAll();
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

                    string[] _array = _barCodes.Split(new char[1]{','});
                    int existFlg = 0;
                    foreach (string code in _array) {
                        if (code.Equals(txtBarcode.Text)) {
                            existFlg = 1;
                        } 
                    }
                    if (StringUtil.IsEmpty(txtBarcode.Text) || existFlg == 0)
                    {
                        txtBarcode.SelectAll();
                        txtBarcode.Focus();
                        Message.Warn("商品が一致していません。");
                        return;
                    }
                    txtQty.SelectAll();
                    txtQty.Focus();
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
                    this.BtnOk.PerformClick();
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
                if (!_qty.Equals(txtQty.Text))
                {
                    txtQty.SelectAll();
                    txtQty.Focus();
                    Message.Warn("ピック数が違います");
                    return;
                }
                //单品警告
                if (lblWarnMsg4Ps.Visible)
                {
                    Message.Info(lblWarnMsg4Ps.Text);
                }
                using (frmExecutePsPick view = new frmExecutePsPick())
                {
                    view.Init(false, _processId, _binCode, _skuCode, _skuname, _expDate, _qty, _qtyA, _qtyB, _qtyC, _qtyD, _qtyE, _qtyF, _qtyG, _qtyH, _qtyI, _binId, _skuId, _pick);
                    DialogResult retFlg = view.ShowDialog();
                    if (retFlg == DialogResult.OK)
                    {
                        this.DialogResult = DialogResult.OK;
                        this.Close();
                    }
                    else if (retFlg == DialogResult.Abort)
                    {
                        this.DialogResult = DialogResult.Abort;
                        return;
                    }
                    else {
                        BtnClear.PerformClick();
                        this.Init(true, _processId);
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

    }
}

