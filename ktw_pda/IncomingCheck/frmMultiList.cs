using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace IncomingCheck
{
    /// <summary>
    /// 入荷検品マルチ
    /// </summary>
    public partial class frmMultiList : Framework.BaseForm
    {
        private int index_count = 1;
        private string _existCode = "";
        private List<long> _asnIDs = new List<long>();
        public frmMultiList()
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
        /// 清除
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnClear_Click(object sender, EventArgs e)
        {
            try
            {
                //清空控件
                this.ClearControl(true, txtAsnNumber, lblCaseNumber1, lblCaseNumber2, lblCaseNumber3
                    , lblCaseNumber4, lblCaseNumber5, lblCaseNumber6, lblCaseNumber7, lblCaseNumber8
                    , lblCaseNumber9, lblQty1, lblQty2, lblQty3, lblQty4, lblQty5, lblQty6, lblQty7
                    , lblQty8, lblQty9, lblCount,lblCaseNumber10,lblQty10);
                foreach (Control ctol in pnlDetail.Controls)
                {
                    if (ctol is Label)
                    {
                        ctol.Hide();
                    }
                }
                _existCode = "";
                index_count = 1;
                _asnIDs.Clear();
                txtAsnNumber.Focus();
                txtAsnNumber.SelectAll();
            }

            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 入荷伝票番号入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtAsnNumber_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (StringUtil.IsEmpty(txtAsnNumber.Text))
                    {
                        BtnOk.PerformClick();
                        return;
                    }
                    string[] _array = _existCode.Split(new char[1] { ',' });
                    int existFlg = 0;
                    foreach (string code in _array)
                    {
                        if (code.Equals(txtAsnNumber.Text))
                        {
                            existFlg = 1;
                        }
                    }
                    if (existFlg == 1)
                    {
                        txtAsnNumber.SelectAll();
                        txtAsnNumber.Focus();
                        Message.Warn("入荷伝票番号重複");
                        return;
                    }
                    if (!StringUtil.IsEmpty(lblCaseNumber10.Text))
                    {
                        txtAsnNumber.SelectAll();
                        txtAsnNumber.Focus();
                        Message.Warn("伝票数オーバー");
                        return;
                    }
                    MobileRequest req = GetInputData("getAsnInfo4Multi");
                    MobileResponse<Dictionary<string, object>> response = null;
                    //调用服务端        
                    int ret = base.PostData("getAsnInfo4Multi", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtAsnNumber.SelectAll();
                        txtAsnNumber.Focus();
                        return;
                    }
                    foreach (Control ctol in pnlDetail.Controls)
                    {
                        if (ctol is Label && ctol.Tag.Equals("" + index_count))
                        {
                            _existCode = _existCode + "," + txtAsnNumber.Text;
                            ctol.Text =  index_count + ":" + txtAsnNumber.Text;
                            ctol.Show();
                        }
                        if (ctol is Label && ctol.Tag.Equals("q" + index_count))
                        {
                            ctol.Text = response.Results["exeQty"].TrimEx() + "/" + response.Results["planQty"].TrimEx();
                            ctol.Show();
                        }
                    }
                    txtAsnNumber.Text = string.Empty;
                    txtAsnNumber.SelectAll();
                    txtAsnNumber.Focus();
                    index_count++;
                    _asnIDs.Add(response.Results["asnId"].ToLong());
                    lblCount.Text = _asnIDs.Count.ToString();
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
                if ("".Equals(_existCode))
                {
                    txtAsnNumber.SelectAll();
                    txtAsnNumber.Focus();
                    Message.Warn("入荷伝票番号を入力してください。");
                    return;
                }
                if (Message.Ask("入荷検品を開始します。よろしいですか？") != DialogResult.Yes)
                    return;
                MobileRequest req = GetInputData("startMultiAsn");
                for (int i = 0; i < _asnIDs.Count; i++)
                {
                    req.Parameters.Add("asnId"+ i, _asnIDs[i]);
                }
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端        
                int ret = base.PostData("startMultiAsn", req, out response);
                if (!(ret == 0 || ret == 2))
                {
                    //错误返回
                    txtAsnNumber.Focus();
                    txtAsnNumber.SelectAll();
                    return;
                }
                string _processId = response.Results["processId"].TrimEx();
                using (frmCheckMulti view = new frmCheckMulti())
                {
                    ret = view.Init(false, _processId);
                    if (!(ret == 0 || ret == 2))
                    {
                        txtAsnNumber.Focus();
                        txtAsnNumber.SelectAll();
                        return;
                    }
                    DialogResult retFlg = view.ShowDialog();
                    if (retFlg == DialogResult.Abort)
                    {
                        this.DialogResult = DialogResult.Abort;
                        return;
                    }
                    else
                        BtnClear.PerformClick();
                }

            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private void frmMultiList_Closed(object sender, EventArgs e)
        {
            try
            {
                if (_asnIDs != null)
                {
                    _asnIDs.Clear();
                }
                _asnIDs = null;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

    }
}

