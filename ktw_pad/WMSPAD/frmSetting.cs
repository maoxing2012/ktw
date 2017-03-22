using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace WMSPAD
{
    /// <summary>
    /// 系统设置
    /// </summary>
    public partial class frmSetting : BaseForm
    {
        public frmSetting()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 确定
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                //清空提示栏
                Notify(string.Empty);

                //数据校验
                if (!CheckWeb())
                    return;

                //保存数据
                Config cfg = AppContext.Instance().GetConfig();
                cfg.SetValue(@"//setting/server", "value", txtServer.Text);
                cfg.Save();
                this.Close();
            }
            catch (Exception ex)
            {
                //处理异常
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 网址
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtServer_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (CheckWeb())
                        btnOK.PerformClick();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 服务端地址！
        /// </summary>
        /// <returns></returns>
        private bool CheckWeb()
        {
            if (string.IsNullOrEmpty(txtServer.Text))
            {
                Framework.Message.Warn("サーバーのアドレスを入力してください。");
                txtServer.SelectAll();
                txtServer.Focus();
                return false;
            }
            return true;
        }

        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public override int Init(bool back, params object[] paras)
        {
            txtServer.Text = AppContext.Instance().GetConfig().GetValue(@"//setting/server", "value");
            return 0;
        }

        /// <summary>
        /// 快捷键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmSetting_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                switch (e.KeyCode)
                {
                    case Keys.F1:
                        btnClear.PerformClick();
                        break;
                    case Keys.F3:
                        txtServer.Visible = !txtServer.Visible;
                        lblWeb.Visible = !lblWeb.Visible;
                        break;
                }
                if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("green").Value))
                {
                    DialogResult = DialogResult.Cancel;
                }
                if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("red").Value))
                {
                    //btnKeybord.PerformClick();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmSetting_Load(object sender, EventArgs e)
        {
            try
            {
                Init(false);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// F1 クリア
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnClear_Click(object sender, EventArgs e)
        {
            try
            {
                this.ClearControl(true, txtServer);
                txtServer.Focus();
                txtServer.SelectAll();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}
