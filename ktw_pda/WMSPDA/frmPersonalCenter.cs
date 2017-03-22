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
    /// 个人中心
    /// </summary>
    public partial class frmPersonalCenter : Framework.BaseForm//Form // 
    {
        public frmPersonalCenter()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 退出
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnBack_Click(object sender, EventArgs e)
        {
            try
            {
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 菜单导航
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Button_Click(object sender, EventArgs e)
        {
            try
            {
                ButtonEx btn = sender as ButtonEx;
                AppContext.Instance().GetNavigator().Forword(btn.Tag.ToString());
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 退出登录
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnExit_Click(object sender, EventArgs e)
        {
            try
            {
                //Application.Exit();
                this.PreView.Notify<ExitArgs>(new ExitArgs());
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 获取顶层控件
        /// </summary>
        /// <returns></returns>
        public override Control GetTopControl()
        {
            return pnlDetail;
        }

        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public override int Init(bool back, params object[] paras)
        {
            int ret = base.Init(back, paras);
            if (ret != 0)
                return ret;

            //显示用户
            lblUser.Text = string.Format("亲爱的{0}用户，您好。", AppContext.Instance().LoginUser.UserName);
            //当前版本号：V1.0
            lblVer.Text = string.Format("当前版本号：V{0}", AppContext.Instance().Version.TrimEx());
            //仓库
            btnWareHouse.Text = string.Format("2.切换仓库({0})", AppContext.Instance().WareHouse.WhName.Substring(0, 2));

            return 0;
        }
    }
}

