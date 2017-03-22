using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;
using System.Collections;

namespace IncomingCheck
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmComfirm : Framework.BaseForm
    {

        public string Times { get; set; }
        public frmComfirm()
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
            if (!back)
            {
                //基类命令控件初始化
                ret = base.Init(back, paras);
                if (ret != 0)
                    return ret;
            }

            return 0;
        }

        public override int Show(IView prev)
        {
            int ret= base.Show(prev);
            txtCode.Focus();
            txtCode.SelectAll();
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
                Message.Warn("回数を入力してください");
                return;
            }

            long ret = txtCode.Text.ToLong();
            if (ret <=0)
            {
                if (Message.Ask("入荷荷札を印刷しませんか？") != DialogResult.Yes)
                {
                    txtCode.SelectAll();
                    txtCode.Focus();
                    return;
                }
            }
            this.Times = txtCode.Text;
            this.DialogResult = DialogResult.OK;
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

