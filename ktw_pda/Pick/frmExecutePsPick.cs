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
    public partial class frmExecutePsPick : Framework.BaseForm
    {
        private long _exeC = 0;
        private long _exeCt = 0;
        private long _processId;
        private long _skuId;
        private long _binId;
        private string _expDate;
        private Dictionary<string, string> _qtyInfo=new Dictionary<string,string>();
        private Dictionary<string, string> _qtyInt = new Dictionary<string, string>();
        
        public frmExecutePsPick()
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
                PickInfo pick;
                _processId = StringUtil.ParseLong(paras[0].TrimEx());
                string _binCode = paras[1].TrimEx();
                string _skuCode = paras[2].TrimEx();
                string _skuname = paras[3].TrimEx();
                _expDate = paras[4].TrimEx();
                _exeCt = StringUtil.ParseLong(paras[5].TrimEx());
                string _qtyA = paras[6].TrimEx();
                string _qtyB = paras[7].TrimEx();
                string _qtyC = paras[8].TrimEx();
                string _qtyD = paras[9].TrimEx();
                string _qtyE = paras[10].TrimEx();
                string _qtyF = paras[11].TrimEx();
                string _qtyG = paras[12].TrimEx();
                string _qtyH = paras[13].TrimEx();
                string _qtyI = paras[14].TrimEx();
                _qtyInt.Add("A", _qtyA);
                _qtyInt.Add("B", _qtyB);
                _qtyInt.Add("C", _qtyC);
                _qtyInt.Add("D", _qtyD);
                _qtyInt.Add("E", _qtyE);
                _qtyInt.Add("F", _qtyF);
                _qtyInt.Add("G", _qtyG);
                _qtyInt.Add("H", _qtyH);
                _qtyInt.Add("I", _qtyI);

                _binId = StringUtil.ParseLong(paras[15].TrimEx());
                _skuId = StringUtil.ParseLong(paras[16].TrimEx());

                pick = paras[17] as PickInfo;
                _qtyInfo.Add("A", pick.qtyInfoA);
                _qtyInfo.Add("B", pick.qtyInfoB);
                _qtyInfo.Add("C", pick.qtyInfoC);
                _qtyInfo.Add("D", pick.qtyInfoD);
                _qtyInfo.Add("E", pick.qtyInfoE);
                _qtyInfo.Add("F", pick.qtyInfoF);
                _qtyInfo.Add("G", pick.qtyInfoG);
                _qtyInfo.Add("H", pick.qtyInfoH);
                _qtyInfo.Add("I", pick.qtyInfoI);

                lblBinCode.Text = _binCode;
                lblSkuCode.Text = _skuCode;
                lblSkuName.Text = _skuname;
                lblExpDate.Text = _expDate;
                lblexe.Text = _exeC + "/" + _exeCt;
                if ("0".Equals(_qtyA))
                {
                    lblA.BackColor = Color.Gray;
                    //lblA.Text = "A:";
                    lblA.Text = string.Empty;
                }
                else {
                    //lblA.Text = "A:" + _qtyA;
                    //lblA.Text =  _qtyA;
                    lblA.Text = pick.qtyHyphenA;
                }
                if ("0".Equals(_qtyB))
                {
                    lblB.BackColor = Color.Gray;
                    //lblB.Text = "B:";
                    lblB.Text = string.Empty;
                }
                else
                {
                    //lblB.Text = "B:" + _qtyB;
                    //lblB.Text =  _qtyB;
                    lblB.Text = pick.qtyHyphenB;
                }
                if ("0".Equals(_qtyC))
                {
                    lblC.BackColor = Color.Gray;
                    //lblC.Text = "C:";
                    lblC.Text = string.Empty;
                }
                else
                {
                    //lblC.Text = "C:" + _qtyC;
                    //lblC.Text = _qtyC;
                    lblC.Text = pick.qtyHyphenC;
                }
                if ("0".Equals(_qtyD))
                {
                    lblD.BackColor = Color.Gray;
                    //lblD.Text = "D:";
                    lblD.Text = string.Empty;
                }
                else
                {
                    //lblD.Text = "D:" + _qtyD;
                    //lblD.Text =  _qtyD;
                    lblD.Text = pick.qtyHyphenD;
                }
                if ("0".Equals(_qtyE))
                {
                    lblE.BackColor = Color.Gray;
                    //lblE.Text = "E:";
                    lblE.Text = string.Empty;
                }
                else
                {
                    //lblE.Text = "E:" + _qtyE;
                    //lblE.Text = _qtyE;
                    lblE.Text = pick.qtyHyphenE;
                }
                if ("0".Equals(_qtyF))
                {
                    lblF.BackColor = Color.Gray;
                    //lblF.Text = "F:";
                    lblF.Text = string.Empty;
                }
                else
                {
                    //lblF.Text = "F:" + _qtyF;
                    //lblF.Text = _qtyF;
                    lblF.Text = pick.qtyHyphenF;
                }
                if ("0".Equals(_qtyG))
                {
                    lblG.BackColor = Color.Gray;
                    //lblG.Text = "G:";
                    lblG.Text = string.Empty;
                }
                else
                {
                    //lblG.Text = "G:" + _qtyG;
                    //lblG.Text = _qtyG;
                    lblG.Text = pick.qtyHyphenG;
                }
                if ("0".Equals(_qtyH))
                {
                    lblH.BackColor = Color.Gray;
                    //lblH.Text = "H:";
                    lblH.Text = string.Empty;
                }
                else
                {
                    //lblH.Text = "H:" + _qtyH;
                    //lblH.Text =  _qtyH;
                    lblH.Text = pick.qtyHyphenH;
                }
                if ("0".Equals(_qtyI))
                {
                    lblI.BackColor = Color.Gray;
                    //lblI.Text = "I:";
                    lblI.Text = string.Empty;
                }
                else
                {
                    //lblI.Text = "I:" + _qtyI;
                    //lblI.Text = _qtyI;
                    lblI.Text = pick.qtyHyphenI;
                }
                this.picHead.TitleText = "バラピッキング（{0}）".FormatEx(AppContext.Instance().Owner.name);
                this.txtBinCode.CharacterCasing = OpenNETCF.Windows.Forms.CharacterCasing.Upper;
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
                //清空控件
                this.ClearControl(true,txtBinCode, txtQty);
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
        /// 棚番入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBinCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (BtnOk.Enabled)
                    {
                        BtnOk.PerformClick();
                        return;
                    }
                    if (StringUtil.IsEmpty(txtBinCode.Text))
                    {
                        txtBinCode.SelectAll();
                        txtBinCode.Focus();
                        Message.Warn("棚番を入力してください");
                        return;
                    }
                    bool found=false;
                    List<Label> lstCount = new List<Label>();
                    lstCount.Add(lblA);
                    lstCount.Add(lblB);
                    lstCount.Add(lblC);
                    lstCount.Add(lblD);
                    lstCount.Add(lblE);
                    lstCount.Add(lblF);
                    lstCount.Add(lblG);
                    lstCount.Add(lblH);
                    lstCount.Add(lblI);
                    for (int i = 0; i < lstCount.Count; i++)
                    {
                        Label ctol = lstCount[i];
                        if (Color.Gray != ctol.BackColor)
                        {
                            if (txtBinCode.Text.Equals(ctol.Tag))
                            {
                                found = true;
                                //txtQty.Text = ctol.Text.Substring(2);
                                //txtQty.Text = ctol.Text;
                                txtQty.Text = _qtyInfo[txtBinCode.Text];
                            }
                            break;
                        }
                    }
                    //foreach (Control ctol in pnlDetail.Controls)
                    //{
                    //    if (ctol is Label && txtBinCode.Text.Equals(ctol.Tag) && Color.Gray != ctol.BackColor)
                    //    {
                    //        found = true;
                    //        //txtQty.Text = ctol.Text.Substring(2);
                    //        //txtQty.Text = ctol.Text;
                    //        txtQty.Text = _qtyInfo[txtBinCode.Text];
                    //        break;
                    //    }
                    //}
                    if (!found)
                    {
                        txtBinCode.Focus();
                        txtBinCode.SelectAll();
                        Message.Warn("コンテナーが違います。");
                        return;
                    }
                    txtBinCode.ReadOnly = true;
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
                    if (BtnOk.Enabled)
                    {
                        BtnOk.PerformClick();
                        return;
                    }
                    if (StringUtil.IsEmpty(txtBinCode.Text))
                    {
                        txtBinCode.SelectAll();
                        txtBinCode.Focus();
                        Message.Warn("棚番を入力してください");
                        return;
                    }
                    if (StringUtil.IsEmpty(txtQty.Text))
                    {
                        txtQty.SelectAll();
                        txtQty.Focus();
                        Message.Warn("数量を入力してください");
                        return;
                    }
                    if (!txtQty.Text.Equals(_qtyInfo[txtBinCode.Text]))
                    {
                        txtQty.SelectAll();
                        txtQty.Focus();
                        Message.Warn("ピック数が違います。");
                        return;
                    }
                    int flg = 0;
                    int beyongdFlg = 0;
                    foreach (Control ctol in pnlDetail.Controls)
                    {
                        if (ctol is Label && txtBinCode.Text.Equals(ctol.Tag) && Color.Gray != ctol.BackColor)
                        {
                            flg = 1;
                            //if (StringUtil.ParseLong(txtQty.Text) < StringUtil.ParseLong(ctol.Text.Substring(2)))
                            //if (StringUtil.ParseLong(txtQty.Text) < StringUtil.ParseLong(ctol.Text))
                            //{
                            //    //_exeC += StringUtil.ParseLong(txtQty.Text);
                            //    _exeC += StringUtil.ParseLong(_qtyInt[txtBinCode.Text]);
                            //    //ctol.Text = ctol.Text.Substring(0, 2) + (StringUtil.ParseLong(ctol.Text.Substring(2)) - StringUtil.ParseLong(txtQty.Text));
                            //    ctol.Text = ( StringUtil.ParseLong(ctol.Text) - StringUtil.ParseLong(txtQty.Text)).ToString();
                            //    lblexe.Text = _exeC + "/" + _exeCt;
                            //    //清空控件
                            //    this.ClearControl(true, txtBinCode, txtQty);
                            //    txtBinCode.Focus();
                            //    txtBinCode.SelectAll();
                            //}
                            ////else if (StringUtil.ParseLong(txtQty.Text) > StringUtil.ParseLong(ctol.Text.Substring(2)))
                            //else if (StringUtil.ParseLong(txtQty.Text) > StringUtil.ParseLong(ctol.Text))
                            //{
                            //    beyongdFlg = 1;
                            //}
                            //else
                            //{
                            //    ctol.BackColor = Color.Gray;
                            //    _exeC += StringUtil.ParseLong(txtQty.Text);
                            //    //ctol.Text = ctol.Text.Substring(0, 2);
                            //    ctol.Text = string.Empty;
                            //    lblexe.Text = _exeC + "/" + _exeCt;
                            //    //清空控件
                            //    this.ClearControl(true, txtBinCode, txtQty);
                            //    txtBinCode.Focus();
                            //    txtBinCode.SelectAll();                        
                            //}

                            ctol.BackColor = Color.Gray;
                            _exeC += StringUtil.ParseLong(_qtyInt[txtBinCode.Text]);
                            ctol.Text = string.Empty;
                            lblexe.Text = _exeC + "/" + _exeCt;
                            //清空控件
                            this.ClearControl(true, txtBinCode, txtQty);
                            txtBinCode.ReadOnly = false;
                            txtBinCode.Focus();
                            txtBinCode.SelectAll();
                            break;
                        }
                    }
                    if (flg == 0)
                    {
                        //清空控件
                        this.ClearControl(true, txtBinCode, txtQty);
                        txtBinCode.ReadOnly = false;
                        txtBinCode.Focus();
                        txtBinCode.SelectAll();
                        Message.Warn("コンテナーが違います。");
                    }
                    if (beyongdFlg == 1)
                    {
                        //清空控件
                        this.ClearControl(true, txtBinCode, txtQty);
                        txtBinCode.ReadOnly = false;
                        txtBinCode.Focus();
                        txtBinCode.SelectAll();
                        Message.Warn("ピック数オーバー");
                    }
                    if (_exeC == _exeCt)
                    {
                        //清空控件
                        this.ClearControl(true, txtBinCode, txtQty);
                        txtBinCode.ReadOnly = false;
                        txtBinCode.Focus();
                        txtBinCode.SelectAll();
                        BtnOk.Enabled = true;
                    }
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
                MobileRequest req = GetInputData("executePsPick");
                req.Parameters.Add("processId", _processId);
                req.Parameters.Add("binId", _binId);
                req.Parameters.Add("skuId", _skuId);
                req.Parameters.Add("expDate", _expDate);
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端        
                int ret = base.PostData("executePsPick", req, out response);
                if (!(ret == 0 || ret == 2))
                    //错误返回
                    return;

                if ("M1".Equals(response.SeverityMsgType)) {
                    //Message.Info(response.SeverityMsg);
                   this.DialogResult = DialogResult.OK;
                }
                this.Close();    
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

    }
}

