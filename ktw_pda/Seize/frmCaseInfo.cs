using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;
using System.Collections;

namespace Seize
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmCaseInfo : Framework.BaseForm
    {
        private List<CaseInfo> _CaseInfo = null;
        private long caseCount = 0;
        private long inCaseCount = 0;
        private long _skuId = 0;
        private long _num = 0;
        private Hashtable hshTable = new Hashtable();
        private Dictionary<long, long> _stockQty = new Dictionary<long, long>();
        private Dictionary<long, long> _stockInQty = new Dictionary<long, long>();
        public frmCaseInfo()
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
                this.picHead.TitleText = "出荷検品（{0}）".FormatEx(AppContext.Instance().Owner.name);
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
        /// 商品バーコード入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBarCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (inCaseCount == caseCount)
                    {
                        enter.PerformClick();
                        return;
                    }
                    if (StringUtil.IsEmpty(txtBarCode.Text))
                    {
                        txtBarCode.SelectAll();
                        txtBarCode.Focus();
                        Message.Warn("商品バーコードを入力してください");
                        return;
                    }
                    if (txtCaseNumber.Text.Equals(txtBarCode.Text))
                    {
                        txtBarCode.SelectAll();
                        txtBarCode.Focus();
                        Message.Warn("検品数不足、検品完了できません。");
                        return;
                    }
                    StockResult ret = BizUtil.QueryStock(txtBarCode.Text);
                    if (ret.ActionResult == ActionResult.SelectOne)
                    {
                        _skuId = ret.Result.id;
                        //_num = ret.Result.coefficient;
                        int existFlg = 0;
                        for (int i = 0; i < _CaseInfo.Count; i++)
                        {
                            if (_CaseInfo[i].skuId == _skuId)
                            {
                                UnitInfo ci;
                                bool temps = GetUnit(_CaseInfo[i], txtBarCode.Text, out ci);
                                if (!temps)
                                {
                                    txtBarCode.Focus();
                                    txtBarCode.SelectAll();
                                    return;
                                }
                                _num = ci.In;
                                existFlg = 1;
                                string key = _skuId + "_" + _CaseInfo[i].expDate;

                                if (hshTable.Contains(key))
                                {
                                    string value = (string)hshTable[key].ToString();
                                    string innum = value.Substring(0, value.IndexOf("/"));
                                    string countnum = value.Substring(value.IndexOf("/") + 1);

                                    lblNum.Text = "" + _num;
                                    // lblUnit.Text = ret.Result.unit;
                                    lblUnit.Text = ci.UnitName;
                                    txtExpDate.Text = _CaseInfo[i].expDate;
                                    lblExpDate.Text = _CaseInfo[i].expDate;
                                    //lblQty.Text = value;
                                    lblQty.Text = _stockInQty[_skuId] + "/" + _stockQty[_skuId].ToString();
                                    lblSkuCode.Text = _CaseInfo[i].skuCode;
                                    lblSkuName.Text = _CaseInfo[i].skuName;
                                    lblSpecs.Text = _CaseInfo[i].specs;
                                    //lblInfo.Visible = (_CaseInfo[i].blIn > 0);
                                    if (_CaseInfo[i].blIn > 0)
                                    {
                                        lblInfo.Visible = true;
                                        if (_num == 1)
                                        {
                                            lblInfo.BackColor = Color.Red;
                                            lblInfo.ForeColor = Color.White;
                                            lblInfo.Text = "*バラ出荷です。ご注意ください!";
                                        }
                                        else
                                        {
                                            lblInfo.BackColor = Color.Yellow;
                                            lblInfo.ForeColor = Color.Black;
                                            lblInfo.Text = "*ボール入り数注意。";
                                        }
                                    }
                                    else
                                    {
                                        lblInfo.Visible = false;
                                    }
                                    if (!countnum.Equals(innum))
                                    {
                                        //lblNum.Text = "" + _num;
                                        //// lblUnit.Text = ret.Result.unit;
                                        //lblUnit.Text = ci.UnitName;
                                        //txtExpDate.Text = _CaseInfo[i].expDate;
                                        //lblExpDate.Text = _CaseInfo[i].expDate;
                                        ////lblQty.Text = value;
                                        //lblQty.Text = innum + "/" + _stockQty[_skuId].ToString();
                                        //lblSkuCode.Text = _CaseInfo[i].skuCode;
                                        //lblSkuName.Text = _CaseInfo[i].skuName;
                                        //lblSpecs.Text = _CaseInfo[i].specs;
                                        //lblInfo.Visible = (_CaseInfo[i].blIn > 0);
                                        ////existFlg = 1;
                                        break;
                                    }
                                }
                                else
                                {
                                    lblNum.Text = "" + _num;
                                    //lblUnit.Text = ret.Result.unit;
                                    lblUnit.Text = ci.UnitName;
                                    txtExpDate.Text = _CaseInfo[i].expDate;
                                    lblExpDate.Text = _CaseInfo[i].expDate;
                                    string value = "0/" + _CaseInfo[i].qty;
                                    hshTable.Add(key, value);
                                    //lblQty.Text = value;
                                    lblQty.Text = (_stockInQty.ContainsKey(_skuId)? _stockInQty[_skuId] : 0 )+ "/" + _stockQty[_skuId].ToString();
                                    lblSkuCode.Text = _CaseInfo[i].skuCode;
                                    lblSkuName.Text = _CaseInfo[i].skuName;
                                    lblSpecs.Text = _CaseInfo[i].specs;
                                    //lblInfo.Visible = (_CaseInfo[i].blIn > 0);
                                    if (_CaseInfo[i].blIn > 0)
                                    {
                                        lblInfo.Visible = true;
                                        if (_num == 1)
                                        {
                                            lblInfo.BackColor = Color.Red;
                                            lblInfo.ForeColor = Color.White;
                                            lblInfo.Text = "*バラ出荷です。ご注意ください!";
                                        }
                                        else
                                        {
                                            lblInfo.BackColor = Color.Yellow;
                                            lblInfo.ForeColor = Color.Black;
                                            lblInfo.Text = "*ボール入り数注意。";
                                        }
                                    }
                                    else
                                    {
                                        lblInfo.Visible = false;
                                    }
                                    //existFlg = 1;
                                    break;
                                }
                            }
                        }
                        if (existFlg == 0)
                        {
                            Message.Warn("個口に該当商品がありません。");
                            txtBarCode.SelectAll();
                            txtBarCode.Focus();
                        }
                        else
                        {
                            if (inCaseCount == caseCount)
                            {
                                txtBarCode.Tip = "個口番号入力";
                                txtBarCode.Text = string.Empty;
                                //this.ClearControl(true, txtBarCode, lblQty, txtExpDate, lblSkuCode, lblSkuName, lblUnit, lblNum, lblSpecs, lblExpDate);
                                txtBarCode.SelectAll();
                                txtBarCode.Focus();
                            }
                            else
                            {
                                enter.PerformClick();
                            }
                            //txtExpDate.SelectAll();
                            //txtExpDate.Focus();
                        }
                    }
                    else
                    {
                        txtBarCode.Focus();
                        txtBarCode.SelectAll();
                    }

                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }


        private bool GetUnit(CaseInfo info, string barCode,out UnitInfo sel)
        {
            sel = null;
            BindingList<UnitInfo> ret = new BindingList<UnitInfo>();
            if (("," + info.csBarCode + ",").Contains("," + barCode + ","))
            {
                UnitInfo ui = new UnitInfo();
                ui.Num =(ret.Count+1).ToString();
                ui.In = info.csIn;
                ui.UnitName = info.csUnit;
                ret.Add(ui);
            }
            if (("," + info.blBarCode + ",").Contains("," + barCode + ","))
            {
                UnitInfo ui = new UnitInfo();
                ui.Num = (ret.Count + 1).ToString();
                ui.In = info.blIn;
                ui.UnitName = info.blUnit;
                ret.Add(ui);
            }
            if (("," + info.psBarCode + ",").Contains("," + barCode + ","))
            {
                UnitInfo ui = new UnitInfo();
                ui.Num = (ret.Count + 1).ToString();
                ui.In = 1;
                ui.UnitName = info.psUnit;
                ret.Add(ui);
            }
            if (ret.Count > 1)
            {
                using (frmUnitInfo view = new frmUnitInfo())
                {
                    view.Init(false, ret);
                    DialogResult rd = view.ShowDialog();
                    if (rd == DialogResult.OK)
                    {
                        sel = view.SelectedUnit;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            else if (ret.Count > 0)
            {
                sel = ret[0];
                return true;
            }
            Message.Warn("単位不整合。");
            return false;
        }


        /// <summary>
        /// 個口番号入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtCaseNumber_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (StringUtil.IsEmpty(txtCaseNumber.Text))
                    {
                        txtCaseNumber.SelectAll();
                        txtCaseNumber.Focus();
                        Message.Warn("個口番号を入力してください");
                        return;
                    }
                    MobileRequest req = GetInputData("getCaseInfo");
                    MobileResponse<CaseInfoList> response = null;
                    //调用服务端
                    int ret = base.PostData("getCaseInfo", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtCaseNumber.SelectAll();
                        txtCaseNumber.Focus();
                        return;
                    }
                    //if ("E".Equals(response.SeverityMsgType))
                    //{
                    //    Message.Err(response.SeverityMsg);
                    //    return;
                    //}
                    _CaseInfo = response.Results.skuInfo;
                    _stockQty.Clear();
                    _stockInQty.Clear();
                    for (int i = 0; i < _CaseInfo.Count; i++)
                    {
                        caseCount += _CaseInfo[i].qty;
                        if (_stockQty.ContainsKey(_CaseInfo[i].skuId))
                            _stockQty[_CaseInfo[i].skuId] += _CaseInfo[i].qty;
                        else
                            _stockQty.Add(_CaseInfo[i].skuId, _CaseInfo[i].qty);
                    }
                    lblCaseQty.Text = inCaseCount + "/" + caseCount;
                    txtCaseNumber.ReadOnly = true;
                    txtBarCode.SelectAll();
                    txtBarCode.Focus();
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
                _CaseInfo = null;
                caseCount = 0;
                inCaseCount = 0;
                _skuId = 0;
                _num = 0;
                hshTable.Clear();
                _stockQty.Clear();
                _stockInQty.Clear();
                lblInfo.Visible = false;
                //清空控件
                this.ClearControl(true, txtCaseNumber, txtBarCode,txtExpDate,lblCaseQty,lblQty, lblSkuCode,lblSkuName, lblUnit, lblNum, lblSpecs,lblExpDate);
                txtCaseNumber.Focus();
                txtCaseNumber.SelectAll();
                txtCaseNumber.ReadOnly = false;
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

                if (inCaseCount == caseCount)
                {
                    try
                    {
                        //using (frmConfirm view = new frmConfirm())
                        //{
                        //    view.Init(false, txtCaseNumber.Text);
                        //    DialogResult ret = view.ShowDialog();
                        //    if (ret == DialogResult.OK)
                        //    {
                        //        F1.PerformClick();
                        //    }
                        //}
                        //txtBarCode.Tip = "個口番号入力";
                        if (StringUtil.IsEmpty(txtBarCode.Text))
                        {
                            txtBarCode.SelectAll();
                            txtBarCode.Focus();
                            Message.Warn("個口番号を入力してください");
                            return;
                        }
                        if (!txtBarCode.Text.Equals(txtCaseNumber.Text))
                        {
                            txtBarCode.SelectAll();
                            txtBarCode.Focus();
                            Message.Warn("個口番号は一致していいですか。");
                            return;
                        }
                        else
                        {
                            MobileRequest req = GetInputData("checkedCase");
                            MobileResponse<Dictionary<string, object>> response = null;
                            //调用服务端
                            int ret = base.PostData("checkedCase", req, out response);
                            if (!(ret == 0 || ret == 2))
                            {
                                //错误返回
                                txtBarCode.Focus();
                                txtBarCode.SelectAll();
                                return;
                            }
                            txtBarCode.Tip = "商品バーコード入力";
                            F1.PerformClick();
                        }
                    }
                    catch (Exception ex)
                    {
                        ExceptionPolicy.HandleException(ex);
                    }
                }
                else
                {
                    if (StringUtil.IsEmpty(txtCaseNumber.Text))
                    {
                        txtCaseNumber.SelectAll();
                        txtCaseNumber.Focus();
                        Message.Warn("個口番号を入力してください");
                        return;
                    }
                    if (StringUtil.IsEmpty(txtBarCode.Text))
                    {
                        txtBarCode.SelectAll();
                        txtBarCode.Focus();
                        Message.Warn("商品バーコードを入力してください");
                        return;
                    }
                    int existFlg = 0;
                    long total = 0;
                    long inqty = 0;
                    //string exp = txtExpDate.Text;
                    string exp = lblExpDate.Text;
                    for (int i = 0; i < _CaseInfo.Count; i++)
                    {
                        if (_CaseInfo[i].skuId == _skuId && _CaseInfo[i].expDate.Equals(exp))
                        {
                            string key = _skuId + "_" + _CaseInfo[i].expDate;
                            if (hshTable.Contains(key))
                            {
                                string value = (string)hshTable[key].ToString();
                                string innum = value.Substring(0, value.IndexOf("/"));
                                string countnum = value.Substring(value.IndexOf("/") + 1);
                                inqty = StringUtil.ParseLong(innum);
                                total = StringUtil.ParseLong(countnum);
                            }
                            else
                            {
                                total = _CaseInfo[i].qty;
                                inqty = 0;
                            }
                            existFlg = 1;
                            break;
                        }
                    }
                    //if (existFlg == 0)
                    //{
                    //    //txtExpDate.SelectAll();
                    //    //txtExpDate.Focus();
                    //    Message.Warn("該当する賞味期限が見つかりません。");
                    //    return;
                    //}

                    if (_num + inqty > total || _num + inCaseCount > caseCount)
                    {
                        //Message.Warn("総検品数を超えました。");
                        Message.Warn("数量オーバー。");
                        txtBarCode.Focus();
                        txtBarCode.SelectAll();
                        return;
                    }
                    inCaseCount += _num;
                    inqty += _num;
                    if (_stockInQty.ContainsKey(_skuId))
                        _stockInQty[_skuId] += _num;
                    else
                        _stockInQty.Add(_skuId, _num);
                    lblCaseQty.Text = inCaseCount + "/" + caseCount;
                    //lblQty.Text = inqty + "/" + total;
                    lblQty.Text = _stockInQty[_skuId] + "/" + _stockQty[_skuId];
                    hshTable.Remove(_skuId + "_" + exp);
                    hshTable.Add(_skuId + "_" + exp, inqty + "/" + total);

                    if (inCaseCount == caseCount)
                    {
                        //try
                        //{
                        //    using (frmConfirm view = new frmConfirm())
                        //    {
                        //        view.Init(false, txtCaseNumber.Text);
                        //        DialogResult ret = view.ShowDialog();
                        //        if (ret == DialogResult.OK)
                        //        {
                        //            F1.PerformClick();
                        //        }
                        //    }
                        //}
                        //catch (Exception ex)
                        //{
                        //    ExceptionPolicy.HandleException(ex);
                        //}
                        //enter.PerformClick();
                        txtBarCode.Tip = "個口番号入力";
                        txtBarCode.Text = string.Empty;
                        txtBarCode.SelectAll();
                        txtBarCode.Focus();
                    }
                    else
                    {
                        //this.ClearControl(true, txtBarCode, lblQty, txtExpDate, lblSkuCode, lblSkuName, lblUnit, lblNum, lblSpecs, lblExpDate);
                        txtBarCode.SelectAll();
                        txtBarCode.Focus();
                    }
                }

            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }


        /// <summary>
        /// 賞味期限入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtExpDate_KeyDown(object sender, KeyEventArgs e)
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

        private void frmCaseInfo_Closed(object sender, EventArgs e)
        {
            try
            {
                if (_CaseInfo != null)
                {
                    _CaseInfo.Clear();
                }
                _CaseInfo = null;
                if (hshTable != null)
                    hshTable.Clear();
                hshTable = null;
                if (_stockQty != null)
                    _stockQty.Clear();
                _stockQty = null;
                if (_stockInQty != null)
                    _stockInQty.Clear();
                _stockInQty = null;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

    }
}

