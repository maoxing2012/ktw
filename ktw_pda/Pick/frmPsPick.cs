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
    public partial class frmPsPick : Framework.BaseForm
    {
        private int index_count = 1;
        private string _existCode = "";
        public frmPsPick()
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
                this.picHead.TitleText = "バラピッキング（{0}）".FormatEx(AppContext.Instance().Owner.name);
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
                this.ClearControl(true, txtCaseNumber, lblCaseNumber1, lblCaseNumber2, lblCaseNumber3, lblCaseNumber4, lblCaseNumber5, lblCaseNumber6, lblCaseNumber7, lblCaseNumber8, lblCaseNumber9, lblQty1, lblQty2, lblQty3, lblQty4, lblQty5, lblQty6, lblQty7, lblQty8, lblQty9);
                foreach (Control ctol in pnlDetail.Controls)
                {
                    if (ctol is Label)
                    {
                        ctol.Hide();
                    }
                }
                _existCode = "";
                index_count = 1;
                txtCaseNumber.Focus();
                txtCaseNumber.SelectAll();
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
        private void txtCaseNumber_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (StringUtil.IsEmpty(txtCaseNumber.Text))
                    {
                        //txtCaseNumber.SelectAll();
                        //txtCaseNumber.Focus();
                        //Message.Warn("個口番号を入力してください");
                        BtnOk.PerformClick();
                        return;
                    }
                    string[] _array = _existCode.Split(new char[1] { ',' });
                    int existFlg = 0;
                    foreach (string code in _array)
                    {
                        if (code.Equals(txtCaseNumber.Text))
                        {
                            existFlg = 1;
                        }
                    }
                    if (existFlg == 1)
                    {
                        txtCaseNumber.SelectAll();
                        txtCaseNumber.Focus();
                        Message.Warn("個口番号重複");
                        return;
                    }
                    if (!StringUtil.IsEmpty(lblCaseNumber9.Text))
                    {
                        txtCaseNumber.SelectAll();
                        txtCaseNumber.Focus();
                        Message.Warn("個口数オーバー");
                        return;
                    }
                    MobileRequest req = GetInputData("getPsPickInfo");
                    req.Parameters.Add("caseNumber", txtCaseNumber.Text);
                    MobileResponse<Dictionary<string, object>> response = null;
                    //调用服务端        
                    int ret = base.PostData("getPsPickInfo", req, out response);
                    if (!(ret == 0 || ret == 2))
                    {
                        //错误返回
                        txtCaseNumber.SelectAll();
                        txtCaseNumber.Focus();
                        return;
                    }
                    foreach (Control ctol in pnlDetail.Controls)
                    {
                        if (ctol is Label && ctol.Tag.Equals("" + index_count))
                        {
                            _existCode = _existCode + "," + txtCaseNumber.Text;
                            ctol.Text = (char)('A' + index_count-1 )+ ":" + txtCaseNumber.Text;
                            ctol.Show();
                        }
                        if (ctol is Label && ctol.Tag.Equals("q" + index_count))
                        {
                            ctol.Text = response.Results["exeQty"].TrimEx() + "/" + response.Results["planQty"].TrimEx();
                            ctol.Show();
                        }
                    }
                    txtCaseNumber.Text = string.Empty;
                    txtCaseNumber.SelectAll();
                    txtCaseNumber.Focus();
                    index_count++;
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
                if ("".Equals(_existCode)) {
                    txtCaseNumber.SelectAll();
                    txtCaseNumber.Focus();
                    Message.Warn("個口番号を入力してください。");
                    return;
                }
                if (Message.Ask("バラピッキングを開始します。よろしいですか？") != DialogResult.Yes)
                    return;
                MobileRequest req = GetInputData("startPsPick");
                req.Parameters.Add("caseNumberA", lblCaseNumber1.Text);
                req.Parameters.Add("caseNumberB", lblCaseNumber2.Text);
                req.Parameters.Add("caseNumberC", lblCaseNumber3.Text);
                req.Parameters.Add("caseNumberD", lblCaseNumber4.Text);
                req.Parameters.Add("caseNumberE", lblCaseNumber5.Text);
                req.Parameters.Add("caseNumberF", lblCaseNumber6.Text);
                req.Parameters.Add("caseNumberG", lblCaseNumber7.Text);
                req.Parameters.Add("caseNumberH", lblCaseNumber8.Text);
                req.Parameters.Add("caseNumberI", lblCaseNumber9.Text);
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端        
                int ret = base.PostData("startPsPick", req, out response);
                if (!(ret == 0 || ret == 2))
                {
                    //错误返回
                    txtCaseNumber.Focus();
                    txtCaseNumber.SelectAll();
                    return;
                }
                string _processId = response.Results["processId"].TrimEx();
                using (frmNextPsPick view = new frmNextPsPick())
                {
                    ret=view.Init(false, _processId);
                    if (!(ret == 0 || ret == 2))
                    {
                        txtCaseNumber.Focus();
                        txtCaseNumber.SelectAll();
                        return;
                    }
                    DialogResult retFlg = view.ShowDialog();
                    if (retFlg == DialogResult.OK)
                    {
                        BtnClear.PerformClick();
                    }
                    else if (retFlg == DialogResult.Abort)
                    {
                        this.DialogResult = DialogResult.Abort;
                        return;
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

