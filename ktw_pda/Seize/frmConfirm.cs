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
    public partial class frmConfirm : Framework.BaseForm
    {

        private string code;
        public frmConfirm()
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
        /// 初始化
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public override int Init(bool back, params object[] paras)
        {
            int ret = 0;
            code = paras[0].TrimEx();
            if (!back)
            {
                //基类命令控件初始化
                ret = base.Init(back, paras);
                if (ret != 0)
                    return ret;
            }

            return 0;
        }

        private void exit_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void Ok_Click(object sender, EventArgs e)
        {
            if (StringUtil.IsEmpty(txtCode.Text))
            {
                txtCode.SelectAll();
                txtCode.Focus();
                Message.Warn("個口番号を入力してください");
                return;
            }
            if (!txtCode.Text.Equals(code))
            {
                Message.Warn("個口番号は一致していいですか。");
                return;
            }
            else {
                MobileRequest req = GetInputData("checkedCase");
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端
                int ret = base.PostData("checkedCase", req, out response);
                if (!(ret == 0 || ret == 2))
                    //错误返回
                    return;
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }

        protected override bool InitForm()
        {
            this.Location = new Point((Screen.PrimaryScreen.WorkingArea.Width - this.Width) / 2
                , (Screen.PrimaryScreen.WorkingArea.Height - this.Height) / 2);
            return true;
        }

        private void txtCode_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                Ok.PerformClick();
            }
        }

    }
}

