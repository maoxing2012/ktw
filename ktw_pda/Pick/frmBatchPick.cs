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
    public partial class frmBatchPick : Framework.BaseForm
    {
        private WaveInfo _wave;
        private PickInfo _pick;

        public frmBatchPick()
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
                this.picHead.TitleText = "大バッチピッキング（{0}）".FormatEx(AppContext.Instance().Owner.name);
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
                    if (!CheckWaveNumber())
                        return;

                    if (!Valid(txtQrCode, string.Empty))
                        return;

                    StockResult result = BizUtil.QueryStock(txtQrCode.Text);
                    if (result.ActionResult != ActionResult.SelectOne)
                    {
                        txtQrCode.Focus();
                        txtQrCode.SelectAll();
                        return;
                    }
                    //lblSkuCode.Text = result.Result.code;
                    //lblSkuName.Text = result.Result.name;

                    //取服务器数据
                    MobileRequest req = GetInputData("getPickInfo4Batch");
                    req.Parameters.Add("waveId", _wave.waveId);
                    req.Parameters.Add("tempDiv", _wave.tempDiv);
                    req.Parameters.Add("skuId", result.Result.id);
                    AppContext app = AppContext.Instance();
                    MobileResponse<PickInfo> response = null;
                    //调用服务端
                    int ret = base.PostData("getPickInfo4Batch", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtQrCode.Focus();
                        txtQrCode.SelectAll();
                        return;
                    }
                    _pick = response.Results;
                    txtBinCode.ReadOnly = true;
                    txtBinCode.TabStop = false;
                    txtQrCode.TabStop = false;
                    txtQrCode.ReadOnly = true;

                    lblQtyInfo.Text = _pick.qtyInfo.TrimEx();
                    lblSkuCode.Text = _pick.skuCode.TrimEx();
                    lblSkuName.Text = _pick.skuName.TrimEx();
                    lblExpDate.Text = _pick.expDate.TrimEx();
                    lblSpecs.Text = _pick.specs.TrimEx();
                    txtCs.Text = _pick.csQty.TrimDouble();
                    txtBl.Text = _pick.blQty.TrimDouble();
                    txtPs.Text = _pick.psQty.TrimDouble();
                    double csIn = response.Results.csIn;
                    double blIn = response.Results.blIn;
                    if (csIn == 0)
                    {
                        txtCs.Text = "0";
                        txtCs.ReadOnly = true;
                        txtCs.TabStop = false;
                        //if (blIn == 0)
                        //{
                        //    txtPs.Focus();
                        //    txtPs.SelectAll();
                        //}
                        //else
                        //{
                        //    txtBl.Focus();
                        //    txtBl.SelectAll();
                        //}
                    }
                    else
                    {
                        txtCs.ReadOnly = false;
                        txtCs.TabStop = true;
                        //txtCs.Focus();
                        //txtCs.SelectAll();
                    }
                    if (blIn == 0)
                    {
                        txtBl.Text = "0";
                        txtBl.ReadOnly = true;
                        txtBl.TabStop = false;
                    }
                    else
                    {
                        txtBl.ReadOnly = false;
                        txtBl.TabStop = true;
                    }
                    lblCsUnit.Text = response.Results.csUnit.TrimEx();
                    lblBlUnit.Text = response.Results.blUnit.TrimEx();
                    lblPsUnit.Text = response.Results.psUnit.TrimEx();
                    txtPs.Focus();
                    txtPs.SelectAll();
                }
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
                this.ClearControl(true, txtBinCode, txtQrCode, lblSkuCode, lblSkuName, lblExpDate, lblQtyInfo
                                        , lblSpecs, txtCs, lblCsUnit, txtBl, lblBlUnit, txtPs, lblPsUnit
                                        ,txtWaveNumber,lblPickInfo,lblTempDivNm);
                txtWaveNumber.ReadOnly = false;
                txtWaveNumber.TabStop = true;
                txtBinCode.ReadOnly = false;
                txtBinCode.TabStop = true;
                txtQrCode.ReadOnly = false;
                txtQrCode.TabStop = true;
                txtWaveNumber.Focus();
                txtWaveNumber.SelectAll(); 
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
                double qty = 0;
                if (!txtWaveNumber.ReadOnly)
                {
                    Message.Warn(AppContext.Instance().GetMessage("field.must.input").FormatEx(txtWaveNumber.Tip));
                    txtWaveNumber.Focus();
                    txtWaveNumber.SelectAll();
                    return;
                }
                if (!txtQrCode.ReadOnly)
                {
                    Message.Warn(AppContext.Instance().GetMessage("field.must.input").FormatEx(txtQrCode.Tip));
                    txtQrCode.Focus();
                    txtQrCode.SelectAll();
                    return;
                }
                if (!txtCs.ReadOnly)
                {
                    qty += txtCs.Text.ToLong() * _pick.csIn;
                }
                if (!txtBl.ReadOnly)
                {
                    qty += txtBl.Text.ToLong() * _pick.blIn;
                }
                qty += txtPs.Text.ToLong();
                if (qty <= 0)
                {
                    //请输入数量
                    Message.Warn("ピッキング数を入力して下さい。");
                    if (!txtCs.ReadOnly)
                    {
                        txtCs.Focus();
                        txtCs.SelectAll();
                    }
                    else if (!txtBl.ReadOnly)
                    {
                        txtBl.Focus();
                        txtBl.SelectAll();
                    }
                    else
                    {
                        txtPs.Focus();
                        txtPs.SelectAll();
                    }
                }
                if (txtCs.Text.ToLong() != _pick.csQty)
                {
                    Message.Warn("{0}が違います。".FormatEx(txtCs.Tip));
                    txtCs.Focus();
                    txtCs.SelectAll();
                    return;
                }
                if (txtBl.Text.ToLong() != _pick.blQty)
                {
                    Message.Warn("{0}が違います。".FormatEx(txtBl.Tip));
                    txtBl.Focus();
                    txtBl.SelectAll();
                    return;
                }
                if (txtPs.Text.ToLong() != _pick.psQty)
                {
                    Message.Warn("{0}が違います。".FormatEx(txtPs.Tip));
                    txtPs.Focus();
                    txtPs.SelectAll();
                    return;
                }

                MobileRequest req = GetInputData("executeBatchPick");
                req.Parameters.Add("waveId", _wave.waveId);
                req.Parameters.Add("wtId", _pick.wtId);
                //req.Parameters.Add("skuId", _pick.skuId);
                req.Parameters.Add("qty", qty);
                //req.Parameters.Add("invStatus", _pick.invStatus);
                AppContext app = AppContext.Instance();
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端        
                int ret = base.PostData("executeBatchPick", req, out response);
                if (!(ret == 0 || ret == 2))
                {
                    //错误返回
                    txtPs.Focus();
                    txtPs.SelectAll();
                    return;
                }
                if ("M1".Equals(response.SeverityMsgType))
                {
                    _wave.executeQty = _wave.planQty;
                }
                else
                {
                    _wave.executeQty = response.Results["executeQty"].ToLong();
                }
                lblPickInfo.Text = "{0}/{1}".FormatEx(_wave.executeQty, _wave.planQty);
                if (_wave.executeQty == _wave.planQty)
                {
                    btnClear.PerformClick();
                }
                else
                {
                    _pick = null;
                    this.ClearControl(true, txtBinCode, txtQrCode, lblSkuCode, lblSkuName, lblExpDate, lblQtyInfo
                        , lblSpecs, txtCs, lblCsUnit, txtBl, lblBlUnit, txtPs, lblPsUnit);
                    txtBinCode.ReadOnly = false;
                    txtBinCode.TabStop = true;
                    txtQrCode.ReadOnly = false;
                    txtQrCode.TabStop = true;
                    txtBinCode.Focus();
                    txtBinCode.SelectAll();
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
        /// バッチ番号入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtWaveNumber_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    EnableButtons(false);
                    //取服务器数
                    //バッチ番号
                    if (!CheckWaveNumber())
                        return;

                    MobileRequest req = GetInputData("searchWaveDoc");
                    AppContext app = AppContext.Instance();
                    MobileResponse<WaveInfo> response = null;
                    //调用服务端        
                    int ret = base.PostData("searchWaveDoc", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtWaveNumber.Focus();
                        txtWaveNumber.SelectAll();
                        return;
                    }
                    _wave = response.Results;

                    //Display data
                    lblPickInfo.Text = "{0}/{1}".FormatEx(_wave.executeQty.TrimDouble(), _wave.planQty.TrimDouble());
                    lblTempDivNm.Text = _wave.tempDivNm.TrimEx();
                    txtWaveNumber.ReadOnly = true;
                    txtWaveNumber.TabStop = false;
                    txtBinCode.Focus();
                    txtBinCode.SelectAll();
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
        /// バッチ番号
        /// </summary>
        /// <returns></returns>
        private bool CheckWaveNumber()
        {
            if (txtWaveNumber.Text.IsEmpty())
            {
                //{0}を入力して下さい。
                Message.Warn(AppContext.Instance().GetMessage("field.must.input").FormatEx("バッチ番号"));
                txtWaveNumber.Focus();
                txtWaveNumber.SelectAll();
                return false;
            }
            return true;
        }

        /// <summary>
        /// 棚番バーコード入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBinCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (!Valid(txtBinCode, string.Empty))
                    {
                        txtBinCode.Focus();
                        txtBinCode.SelectAll();
                        return;
                    }
                    txtQrCode.Focus();
                    txtQrCode.SelectAll();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 确定
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtPs_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                    btnOk.PerformClick();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private void frmBatchPick_Closed(object sender, EventArgs e)
        {
            try
            {
                _wave = null;
                _pick = null;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}

