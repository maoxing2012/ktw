using System;
using System.IO;
using System.Reflection;
using System.Linq;
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
    /// 主菜单画面
    /// </summary>
    public partial class frmMain : BaseForm
    {
        private IView _view = null;
        public frmMain()
        {
            InitializeComponent();
        }
        /// <summary>
        /// 处理快捷键
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmMain_KeyUp(object sender, KeyEventArgs e)
        {
            
        }
        /// <summary>
        /// 处理快捷键
        /// </summary>
        /// <param name="e"></param>
        /// <returns></returns>
        protected override bool ProcessKeyUp(KeyEventArgs e)
        {
            if (this._view != null)
            {
                _view.Notify(e);
                return true;
            }
            return base.ProcessKeyUp(e);
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
                //条码扫描设备初始化
                AppContext.Instance().GetScanner().StartScan(this);
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
            picHead.TitleText = "{0}({1})".FormatEx(AppContext.Instance().Owner.WhName
                , AppContext.Instance().Owner.name);
            lblUser.Text = AppContext.Instance().LoginUser.UserName;
        }



        ///// <summary>
        ///// 视图变化处理
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //void frmMain_ViewChanged(object sender, ViewChangedArgs e)
        //{
        //    try
        //    {
        //        //挂起操作
        //        this.SuspendLayout();
        //        pnlParent.SuspendLayout();
        //        this.pnlParent.Visible = false;
        //        _view = e.Current;
        //        //清空原界面
        //        this.pnlParent.Controls.Clear();
        //        //显示新画面
        //        IView nextFrm = e.Current;
        //        if (nextFrm == null)
        //        {
        //            //显示Menu
        //            this.pnlParent.Controls.Add(pnlMain);
        //            pnlMain.Dock = DockStyle.Fill;
        //            this.pnlParent.Visible = true;
        //            //刷新界面
        //            Init(true);
        //        }
        //        else
        //        {
        //            Control frame = nextFrm.GetTopControl();
        //            this.pnlParent.Controls.Add(frame);
        //            frame.Dock = DockStyle.Fill;
        //            this.pnlParent.Visible = true;
        //            //初始化显示
        //            nextFrm.Show(this);
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        ExceptionPolicy.HandleException(ex);
        //    }
        //    finally
        //    {
        //        this.pnlParent.ResumeLayout();
        //        this.ResumeLayout();
        //    }
        //}
        /// <summary>
        /// 窗体退出处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmMain_Closed(object sender, EventArgs e)
        {
            try
            {
                AppContext.Instance().GetScanner().StopScan();
                //Beep.dispose();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        ///// <summary>
        ///// 切换到主界面系列
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void btnMenu_Click(object sender, EventArgs e)
        //{
        //    try
        //    {
        //        //切换到主界面系列
        //        AppContext.Instance().GetNavigator().Swith("menu");
        //    }
        //    catch (Exception ex)
        //    {
        //        ExceptionPolicy.HandleException(ex);
        //    }
        //}
        ///// <summary>
        ///// 切换到个人中心界面系列
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void btnPerson_Click(object sender, EventArgs e)
        //{
        //    try
        //    {
        //        //切换到个人中心界面系列
        //        AppContext.Instance().GetNavigator().Swith("personal");
        //    }
        //    catch (Exception ex)
        //    {
        //        ExceptionPolicy.HandleException(ex);
        //    }
        //}

        /// <summary>
        /// 刷新界面
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnRefresh_Click(object sender, EventArgs e)
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
        /// 显示消息提示
        /// </summary>
        /// <param name="message"></param>
        /// <returns></returns>
        protected override int Notify(string message)
        {
            //statusBar.Text = message;
            return 0;
        }
        ///// <summary>
        ///// 退出程序
        ///// </summary>
        ///// <param name="e"></param>
        ///// <returns></returns>
        //protected override int Notify(ExitArgs e)
        //{
        //    //退出程序
        //    this.Close();
        //    return 0;
        //}
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

                menuAsn.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                menuPutaway.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                menuPickup.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                menuPackage.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                menuShipment.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                //menuCount.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                menuReplenishment.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                //menuOther.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
            }

            ////取服务器数据
            //MobileResponse<Dictionary<string, object>> response = null;
            //MobileRequest req = GetInputData(string.Empty);
            ////收货：menuAsn
            //req.Parameters.Add("menuAsn", "menuAsn");
            ////上架：menuPutaway
            //req.Parameters.Add("menuPutaway", "menuPutaway");
            ////拣货/分拣：menuPickup
            //req.Parameters.Add("menuPickup", "menuPickup");
            ////装箱：menuPackage
            //req.Parameters.Add("menuPackage", "menuPackage");
            ////发运：menuShipment
            //req.Parameters.Add("menuShipment", "menuShipment");
            ////盘点：menuCount
            //req.Parameters.Add("menuCount", "menuCount");
            ////其他：menuOther
            //req.Parameters.Add("menuOther", "menuOther");
            //ret = base.PostData("initMenu", req, out response);
            //if (!(ret == 0 || ret == 2))
            //    //失败
            //    return ret;

            ////显示数据
            //DisplayMenu(response, menuAsn, "1.收货");
            //DisplayMenu(response, menuPutaway, "2.上架");///移位
            //DisplayMenu(response, menuPickup, "3.拣货");
            //DisplayMenu(response, menuPackage, "4.码盘");///收集SN
            //DisplayMenu(response, menuShipment, "5.发运");
            ////ToDo:显示库内补货任务数
            ////DisplayMenu(response, menuCount, "6.库内补货");
            //DisplayMenu(response, menuCount, "7.盘点");
            //DisplayMenu(response, menuOther, "9.其他");
            return 0;
        }

        /// <summary>
        /// 显示按钮文本
        /// </summary>
        /// <param name="response"></param>
        /// <param name="menu"></param>
        /// <param name="menuText"></param>
        private void DisplayMenu(MobileResponse<Dictionary<string, object>> response, ButtonEx menu, string menuText)
        {
            StringBuilder sb = new StringBuilder(menuText);
            if (response.Results.ContainsKey(menu.Name))
            {
                sb.AppendFormat(" ({0})", response.Results[menu.Name]);
            }
            menu.Text = sb.ToString();
        }
        /// <summary>
        /// 获取顶层控件
        /// </summary>
        /// <returns></returns>
        public override Control GetTopControl()
        {
            return pnlMain;
        }
        /// <summary>
        /// 打开软键盘
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnKeybord_Click(object sender, EventArgs e)
        {
            try
            {
                Sip.Visible = !Sip.Visible;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
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
                if (this._view != null)
                {
                    this._view.Notify(new KeyDownEvntArgs(e));
                    return;
                }
                switch (e.KeyCode)
                {
                    case Keys.Escape:
                        this.Close();
                        break;
                }
                if(e.Modifiers == Keys.None)
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
        /// 荷主切替
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnSetting_Click(object sender, EventArgs e)
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
    }
}