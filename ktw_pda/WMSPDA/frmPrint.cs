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
    /// 打印机设置&测试
    /// </summary>
    public partial class frmPrint : Framework.BaseForm//Form //
    {
        public frmPrint()
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
        /// <summary>
        /// 确定
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOK_Click(object sender, EventArgs e)
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

        /// <summary>
        /// 获取顶层控件
        /// </summary>
        /// <returns></returns>
        public override Control GetTopControl()
        {
            return pnlDetail;
        }
        /// <summary>
        /// 初期处理
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public override int Init(bool back, params object[] paras)
        {
            //焦点设置
            txtCom.Focus();

            //切换模式时无需处理
            if (!back)
            {
                //基类控件，字段，快捷键等初始化
                int ret = base.Init(back, paras);
                if (ret != 0)
                    return ret;

                //显示端口号
                txtCom.Text = AppContext.Instance().GetConfig().GetValue(@"//setting/com", "value");
            }
            txtCom.SelectAll();
            return 0;
        }

        private IPrint _printObject = null;

        /// <summary>
        /// 测试处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtCom_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                //Todo:调用服务器取出命令行

                int ret = 0;
                //调用打印机
                if(_printObject == null)
                    _printObject=AppContext.Instance().CreateObject<IPrint>("printer");
                //Todo:ret = _printObject.Print();
                if (ret == 0)
                {
                    //成功
                    //保存设置
                    AppContext.Instance().GetConfig().SetValue(@"//setting/com", "value", txtCom.Text);
                    //提示消息
                    Notify("调用成功，已设置打印端口！");
                }
                else
                {
                    //失败
                    Notify("调用服务器打印失败，错误号{0}".FormatEx(ret.ToString()));
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}

