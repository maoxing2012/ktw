using Framework;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WMSPAD
{
    /// <summary>
    /// 主菜单画面
    /// </summary>
    public partial class frmMain : Framework.BaseForm
    {
        public frmMain()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 荷主切替
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void picSetting_Click(object sender, EventArgs e)
        {
            try
            {
                //荷主選択
                using (frmOwnerList owner = new frmOwnerList())
                {
                    int ret = owner.Init(false);
                    if (ret != 0 && ret != 2)
                    {
                        return;
                    }
                    if (owner.ShowDialog() != DialogResult.OK)
                    {
                        return;
                    }
                }
                DisplayCurrentInfo();
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
        private void frmMain_Load(object sender, EventArgs e)
        {
            bool layout = false;
            try
            {
                //显示登录界面
                using (frmLogin login = new frmLogin())
                {
                    if (login.ShowDialog() != DialogResult.OK)
                    {
                        this.Close();
                        return;
                    }
                }

                DisplayCurrentInfo();
                layout = true;
                this.SuspendLayout();

                //初始化
                Init(false);
                lblVersion.Text = "バージョン：{0}".FormatEx(AppContext.Instance().Version);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                if (layout) this.ResumeLayout();
            }
        }

        /// <summary>
        /// 显示信息
        /// </summary>
        private void DisplayCurrentInfo()
        {
            lblTitle.Text = "{0}({1})".FormatEx(AppContext.Instance().Owner.WhName
            , AppContext.Instance().Owner.name);
            this.Text = lblTitle.Text;
            lblUser.Text = AppContext.Instance().LoginUser.UserName;
        }

        /// <summary>
        /// 快捷键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmLogin_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                switch (e.KeyCode)
                {
                    case Keys.Escape://退出
                        this.Close();
                        break;
                    case Keys.F1://设置
                        picSetting_Click(sender, e);
                        break;
                }
                if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("green").Value))
                {
                    this.Close();
                }
                else if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("red").Value))
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
        /// 导航
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Menu_Click(object sender, EventArgs e)
        {
            try
            {
                base.EnableButtons(false);
                ButtonEx btn = sender as ButtonEx;
                //导航
                using (BaseForm view = AppContext.Instance().CreateObject<BaseForm>(btn.Tag.ToString()))
                {
                    view.Init(false);
                    view.ShowDialog();
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
        /// 刷新界面
        /// </summary>
        protected override void PressRightButton()
        {
            try
            {
                //刷新界面
                Init(true);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 窗体加载
        /// </summary>
        /// <returns></returns>
        protected override bool InitForm()
        {
            bool ret= base.InitForm();
            btnRight.Image = AppContext.Instance().GetImage("WMSPAD.Images.refresh.png");
            picSetting.Image = AppContext.Instance().GetImage("WMSPAD.Images.setting.png");
            return ret;
        }

        /// <summary>
        /// 处理快捷键
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmMain_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                switch (e.KeyCode)
                {
                    case Keys.Escape:
                        this.Close();
                        break;
                }
                if (e.Modifiers == Keys.None)
                    if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("green").Value))
                    {
                        this.Close();
                    }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// Focus
        /// </summary>
        /// <param name="prev"></param>
        /// <returns></returns>
        public override int Show(IView prev)
        {
            int ret= base.Show(prev);
            menuPackage.Focus();
            return ret;
        }

        protected override void PressLeftButton()
        {
            try
            {
                //显示登录界面
                using (frmLogin login = new frmLogin())
                {
                    if (login.ShowDialog() != DialogResult.OK)
                    {
                        this.Close();
                        return;
                    }
                }
                DisplayCurrentInfo();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}
