using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace WMSPDA
{
    /// <summary>
    /// 修改密码
    /// </summary>
    public partial class frmChangePassword :  Framework.BaseForm//Form //
    {
        public frmChangePassword()
        {
            InitializeComponent();
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
                //退回到Menu
                AppContext.Instance().GetNavigator().Back("menu", false);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        ///// <summary>
        ///// 确定
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void btnOK_Click(object sender, EventArgs e)
        //{
        //    try
        //    {
        //        if (this.PreView is frmLogin)
        //        {
        //            //如果是从登录界面过来
        //            this.DialogResult = DialogResult.OK;
        //            this.ControlBox = false;
        //            this.Close();
        //        }
        //        else
        //            //退回到Menu
        //            AppContext.Instance().GetNavigator().Back("menu", false);
        //    }
        //    catch (Exception ex)
        //    {
        //        ExceptionPolicy.HandleException(ex);
        //    }
        //}

        /// <summary>
        /// 获取顶层控件
        /// </summary>
        /// <returns></returns>
        public override Control GetTopControl()
        {
            return pnlDetail;
        }

        ///// <summary>
        ///// 初始化界面
        ///// </summary>
        ///// <param name="back"></param>
        ///// <param name="paras"></param>
        ///// <returns></returns>
        //public override int Init(bool back, params object[] paras)
        //{
        //    //调用父类方法
        //    base.Init(back, paras);
        //    return 0;
        //}
        /// <summary>
        /// 根据不同的前一个窗体做不同事
        /// </summary>
        /// <param name="prev"></param>
        /// <returns></returns>
        public override int Show(IView prev)
        {
            //调用父类方法
            base.Show(prev);
            if (prev is frmLogin)
            {
                //如果是从login过来的画面，显示键盘按钮
                //btnKeyBord.Visible = true;
                //隐藏退回按钮
                btnBack.Visible = false;
                picBack.Visible = false;
                this.ControlBox = false;
                this.ViewName = "password";
            }
            else
            {
                //将焦点定位到老密码
                txtOldPassword.Focus();
                txtOldPassword.SelectAll();
            }
            return 0;
        }
        ///// <summary>
        ///// 显示软键盘
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void btnKeyBord_Click(object sender, EventArgs e)
        //{
        //    try
        //    {
        //        Sip.Visible = !Sip.Visible;
        //    }
        //    catch (Exception ex)
        //    {
        //        ExceptionPolicy.HandleException(ex);
        //    }
        //}

        /// <summary>
        /// 显示提示消息
        /// </summary>
        /// <param name="message"></param>
        /// <returns></returns>
        protected override int Notify(string message)
        {
            base.Notify(message);
            statusBar.Text=message ;
            return 0;
        }

        /// <summary>
        /// 快捷键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmChangePassword_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (this.PreView is frmLogin)
                {
                    //如果从登录画面进入
                    if ((int)e.KeyCode == int.Parse("esc".HotKey().Value))
                    {
                        //ESC
                        this.DialogResult = DialogResult.Cancel;
                        this.Close();
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 画面跳转
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="command"></param>
        /// <param name="response"></param>
        /// <param name="ret"></param>
        /// <returns></returns>
        protected override int ReactUi<T>(string command, MobileResponse<T> response, int ret)
        {
            if (!(ret == 0 || ret == 2))
            {
                //正常或者警告
                return ret;
            }
            //画面操作
            if (this.PreView is frmLogin)
            {
                //如果是从登录界面过来
                this.DialogResult = DialogResult.Yes;
                this.Close();
            }
            else
            {
                //清空窗体
                ClearForm();

                //退回到Menu
                AppContext.Instance().GetNavigator().Back("menu", false);
            }
            return 0;
        }

        /// <summary>
        /// 校验文本控件
        /// </summary>
        /// <param name="c"></param>
        /// <param name="commit"></param>
        /// <returns></returns>
        protected override bool Valid(TextEx c, string commit)
        {
            if(c != this.txtConfirm)
            {
                return base.Valid(c, commit);
            }
            //重复输入密码
            if (!base.Valid(c, commit))
            {
                return false;
            }
            if (!c.Text.Equals(this.txtPassword.Text))
            {
                this.Notify("新密码和确认密码不一致！");
                txtPassword.Focus();
                txtPassword.SelectAll();
                return false;
            }
            return true;
        }
    }
}

