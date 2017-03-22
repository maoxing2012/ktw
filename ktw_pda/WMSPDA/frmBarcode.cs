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
    /// 条码扫描测试
    /// </summary>
    public partial class frmBarcode :Framework.BaseForm // Form //
    {
        public frmBarcode()
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
                AppContext.Instance().GetScanner().GetBarCode -= new GetBarCodeEventHandler(GetBarCodeEventHandler);
                AppContext.Instance().GetNavigator().Back("menu", false);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 清空
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                //清空控件
                this.ClearControl(false, txtBarCode, txtCodeType, txtTime);
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
            AppContext.Instance().GetScanner().GetBarCode += new GetBarCodeEventHandler(GetBarCodeEventHandler);
            txtBarCode.Focus();
            return 0;
        }

        /// <summary>
        /// 获取条码消息
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void GetBarCodeEventHandler(object sender, ScanArgs e)
        {
            try
            {
                //清空信息
                Notify(string.Empty);
                //条码
                if (!txtBarCode.Text.IsEmpty())
                {
                    if (txtBarCode.Lines.Length > 20)
                        txtBarCode.Text = string.Empty;
                    else
                        txtBarCode.Text += Environment.NewLine;
                }
                txtBarCode.Text += e.Barcode;
                txtBarCode.SelectionStart = txtBarCode.Text.Length;
                txtBarCode.ScrollToCaret();
                //条码格式
                txtCodeType.Text = "条码格式:{0}".FormatEx(e.CodeType);
                //读取时间
                txtTime.Text = "读取时间:{0}s".FormatEx((e.Duration.TotalMilliseconds*0.001).ToString());
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}

