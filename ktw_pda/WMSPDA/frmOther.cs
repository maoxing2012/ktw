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
    /// 其他菜单
    /// </summary>
    public partial class frmOther : Framework.BaseForm
    {
        public frmOther()
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
        /// 刷新
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
        /// 调用各业务
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Button_Click(object sender, EventArgs e)
        {
            try
            {
                base.EnableButtons(false);
                ButtonEx btn = sender as ButtonEx;
                //导航
                using (BaseForm view = AppContext.Instance().CreateObject<BaseForm>(btn.Tag.ToString()))
                {
                    view.Init(false);
                    DialogResult ret = view.ShowDialog();
                    if (ret == DialogResult.Abort)
                    {
                        //退回到Menu
                        this.DialogResult = DialogResult.Abort;
                    }
                    else
                    {
                        //退回到本画面
                        this.Init(true);
                        this.Show(null);
                    }
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

                menuInvHisList.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                menuIbdHisList.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                menuObdHisList.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
                menuInvList.TextAlign = OpenNETCF.Drawing.ContentAlignment2.MiddleLeft;
            }

            //取服务器数据
            MobileResponse<Dictionary<string, object>> response = null;
            MobileRequest req = GetInputData("initMenu");
            //库存查询 ：menuInvList
            req.Parameters.Add("menuInvList", "menuInvList");
            //收货履历：menuIbdHisList
            req.Parameters.Add("menuIbdHisList", "menuIbdHisList");
            //发货履历：menuObdHisList
            req.Parameters.Add("menuObdHisList", "menuObdHisList");
            //库存履历：menuSkuInvList
            req.Parameters.Add("menuInvHisList", "menuInvHisList");
            ret = base.PostData("initMenu", req, out response);
            if (!(ret == 0 || ret == 2))
                //失败
                return ret;

            //显示数据
            DisplayMenu(response, menuInvList, "1.库存查询");
            DisplayMenu(response, menuIbdHisList, "2.收货履历");
            DisplayMenu(response, menuObdHisList, "3.发货履历");
            DisplayMenu(response, menuInvHisList, "4.库存履历");
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
    }
}

