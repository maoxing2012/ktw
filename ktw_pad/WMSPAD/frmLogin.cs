using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Framework;
using Entity;

namespace WMSPAD
{
    /// <summary>
    /// 登录窗体
    /// </summary>
    public partial class frmLogin : BaseForm
    {
        public frmLogin()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 设置
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void picSetting_Click(object sender, EventArgs e)
        {
            try
            {
                //显示设置画面
                using (frmSetting nextFrm = new frmSetting())
                {
                    nextFrm.ShowDialog();
                }

            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 初次加载
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmLogin_Load(object sender, EventArgs e)
        {
            try
            {
                //加载图片
                picLogo.Image = AppContext.Instance().GetImage("WMSPAD.Images.logo.png");
                picSetting.Image = AppContext.Instance().GetImage("WMSPAD.Images.setting.png");
                lblStatus.Text = "バージョン：{0}".FormatEx(AppContext.Instance().Version);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 登录
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                EnableButtons(false);
                //清空提示栏
                Notify(string.Empty);

                //数据校验
                if (!CheckUser(1))
                    return;
                if (!CheckPassword(1))
                    return;

                //取服务器数据
                MobileRequest req = new MobileRequest();
                req.PageId = "login";
                req.Parameters.Add("loginName", txtUser.Text);
                req.Parameters.Add("password", txtPassword.Text);
                AppContext app = AppContext.Instance();
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端
                int ret = base.PostData("login", req, out response);
                if (!(ret == 0 || ret == 2))
                    //错误返回
                    return;

                //成功登录
                app.LoginUser = new UserObj(long.Parse(response.Results["userId"].TrimEx())
                    , response.Results["loginName"].TrimEx(), response.Results["userName"].TrimEx());
                //记录登录时间
                app.LoginUser.LoginTime = DateTime.Now;

                //荷主選択（日本食研）
                using (frmOwnerList owner = new frmOwnerList())
                {
                    ret = owner.Init(false);
                    if (ret != 0 && ret != 2)
                    {
                        return;
                    }
                    if (owner.ShowDialog() != DialogResult.OK)
                    {
                        return;
                    }
                }
                this.DialogResult = DialogResult.OK;
                this.Close();
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
        /// 密码
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtPassword_KeyDown(object sender, KeyEventArgs e)
        {
            if ((e.KeyCode == System.Windows.Forms.Keys.Enter))
            {
                // Enter
                btnOK.PerformClick();
            }
        }
        /// <summary>
        /// 用户
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtUser_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    CheckUser(0);
                    txtPassword.Focus();
                    txtPassword.SelectAll();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 检验用户
        /// </summary>
        /// <param name="validType"></param>
        /// <returns></returns>
        private bool CheckUser(int validType)
        {
            if (string.IsNullOrEmpty(txtUser.Text))
            {
                this.lblStatus.Text = AppContext.Instance().GetMessage("field.must.input").FormatEx(txtUser.Tip);
                //Message.Info(AppContext.Instance().GetMessage("field.must.input").FormatEx(txtUser.Tip));
                if (0 == validType)
                {
                    return true;
                }
                else
                {
                    txtUser.SelectAll();
                    txtUser.Focus();
                    return false;
                }
            }
            return true;
        }
        /// <summary>
        /// 检验密码
        /// </summary>
        /// <param name="validType"></param>
        /// <returns></returns>
        private bool CheckPassword(int validType)
        {
            if (string.IsNullOrEmpty(txtPassword.Text))
            {
                //Message.Info(AppContext.Instance().GetMessage("field.must.input").FormatEx(txtPassword.Tip));
                txtPassword.SelectAll();
                txtPassword.Focus();
                this.lblStatus.Text = AppContext.Instance().GetMessage("field.must.input").FormatEx(txtPassword.Tip);
                return false;
            }
            return true;
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            try
            {
                this.Close();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}
