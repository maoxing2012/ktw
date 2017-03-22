using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;
using sato.pt;

namespace WMSPDA
{
    /// <summary>
    /// 系统设置
    /// </summary>
    public partial class frmSetting : Framework.BaseForm //Form //
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
                if (!CheckCom())
                    return;

                //保存数据
                Config cfg = AppContext.Instance().GetConfig();
                cfg.SetValue(@"//setting/server", "value", txtServer.Text);
                cfg.SetValue(@"//setting/com", "value", txtCom.Text );
                //cfg.SetValue(@"//setting/portno", "value", txtPortNo.Text);
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
        /// 退回
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnBack_Click(object sender, EventArgs e)
        {
            try
            {
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
                        btnOK_Click(this, EventArgs.Empty);
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// com
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtCom_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    if (txtServer.Visible)
                    {
                        if (CheckCom())
                        {
                            txtServer.Focus();
                            txtServer.SelectAll();
                        }
                    }
                    else
                    {
                        if (CheckCom())
                            btnOK_Click(this, EventArgs.Empty);
                    }
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
                Message.Warn("サーバーのアドレスを入力してください。");
                txtServer.SelectAll();
                txtServer.Focus();
                return false;
            }
            return true;
        }
        /// <summary>
        /// 蓝牙端口号
        /// </summary>
        /// <returns></returns>
        private bool CheckCom()
        {
            //if (string.IsNullOrEmpty(txtCom.Text))
            //{
            //    Message.Warn("蓝牙端口号！");
            //    txtServer.SelectAll();
            //    txtServer.Focus();
            //    return false;
            //}
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
            txtCom.Text = AppContext.Instance().GetConfig().GetValue(@"//setting/com", "value");
            //txtPortNo.Text = AppContext.Instance().GetConfig().GetValue(@"//setting/portno", "value");
            AppContext.Instance().GetScanner().GetBarCode = new GetBarCodeEventHandler(GetBarCode);
            return 0;
        }
        
        void GetBarCode(object sender, ScanArgs e)
        {
            try
            {
                if (txtServer.Focused)
                {
                    //模拟键盘输入
                    //Denso.Win32.SendKeys(e.Barcode, false);
                    txtServer.Text = e.Barcode;
                    txtServer.Focus();
                    txtServer.SelectAll();
                }
                else if (txtCom.Focused && !txtServer.Visible)
                {
                    txtCom.Text = e.Barcode;
                    txtCom.Focus();
                    txtCom.SelectAll();
                }
                else
                    Denso.Win32.SendKeys(e.Barcode, true);
            }
            catch 
            {
                //
            }
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
                    case Keys.Escape:
                        //后退
                        btnBack_Click(this, EventArgs.Empty);
                        break;
                    case Keys.F1:
                        btnClear.PerformClick();
                        break;
                    case Keys.F2:
                        btnPrint.PerformClick();
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
                    btnKeybord.PerformClick();
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
        /// 调出软键盘
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
        /// F1 クリア
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnClear_Click(object sender, EventArgs e)
        {
            try
            {
                this.ClearControl(true, txtServer, txtCom);
                txtServer.Focus();
                txtServer.SelectAll();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 印刷
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnPrint_Click(object sender, EventArgs e)
        {
            try
            {
                PrintBill();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }


        /// <summary>
        /// 打印小票
        /// </summary>
        /// <param name="list"></param>
        private void PrintBill()
        {
            int retOpen = 0;
            PtRs232c PtLan = new PtRs232c();
            try
            {
                StringBuilder strPrint = new StringBuilder();
                string address = txtCom.Text;
                Config cfg = AppContext.Instance().GetConfig();
                cfg.SetValue(@"//setting/com", "value", txtCom.Text);
                cfg.Save();
                retOpen = PtLan.open(address);
                if (retOpen == Def.PT_OK)
                {
                    //成功打开
                    PtOutputResult retLabel;
                    retLabel.Ret = Def.PTS_SYSTEMERR;
                    string strMsg = "";
                    strPrint.Length = 0;
                    strPrint.Append("");
                    strPrint.Append("A");
                    strPrint.Append("%3H0200V00200L0202P02K8H");//
                    strPrint.Append(GetHexValue("テスト"));
                    //<Q>1
                    strPrint.Append("Q1");
                    //<Z>
                    strPrint.Append("Z");
                    //<ETX>
                    strPrint.Append("");

                    Byte[] sendBytes = Encoding.UTF8.GetBytes(strPrint.ToString());
                    retLabel = PtLan.outputLabel(sendBytes);
                    switch (retLabel.Ret)
                    {
                        case Def.PTS_PRINTING:
                            strMsg = Convert.ToString("PTS_PRINTING");
                            break;
                        case Def.PTS_ACK:
                            strMsg = Convert.ToString("PTS_ACK(ACK)");
                            break;
                        case Def.PTS_NAK:
                            strMsg = Convert.ToString("PTS_NAK(NAK)");
                            break;
                        case Def.PTS_STOP:
                            strMsg = Convert.ToString("PTS_STOP");
                            break;
                        case Def.PTS_TIMEOUT:
                            strMsg = Convert.ToString("PTS_TIMEOUT");
                            break;
                        case Def.PTS_SYSTEMERR:
                            strMsg = Convert.ToString("PTS_SYSTEMERR");
                            break;
                        case Def.PTS_UNCONNECTION:
                            strMsg = Convert.ToString("PTS_UNCONNECTION");
                            break;
                    }
                }
                else if (retOpen == Def.PT_NG)
                {
                    Message.Info("print error!");
                }
                else if (retOpen == Def.PT_PARAMERR)
                {
                    Message.Info("プリンタのパラメータが不正です。");
                }
            }
            finally
            {
                if (retOpen == Def.PT_OK)
                    PtLan.close();
            }
        }

        private string GetHexValue(string kanji)
        {
            byte[] data = System.Text.Encoding.GetEncoding(932).GetBytes(kanji.TrimEx());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.Length; i++)
            {
                sb.Append(Convert.ToString(data[i], 16).ToUpper());
            }
            return sb.ToString();
        }

        /// <summary>
        /// 事件处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmSetting_Closed(object sender, EventArgs e)
        {
            try
            {
                AppContext.Instance().GetScanner().GetBarCode =null;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}

