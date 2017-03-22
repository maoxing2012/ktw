using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Framework;
using System.Windows.Forms;
using DNWA.BHTCL;
using System.Threading;

namespace Denso
{
    public class BHT1300CL:IScan
    {

        // Scanner Class
        Scanner m_myScanner = new Scanner();
        // Barcode Read Count
        int m_intReadCount = 0;
        private DateTime _startTime;
        /// <summary>
        /// 接收热键
        /// </summary>
        private MsgWindow msgWindow;

        #region IScan 成员

        public GetBarCodeEventHandler GetBarCode
        { get; set; }

        public void StartScan(System.Windows.Forms.Form msgForm)
        {
            try
            {
#if !DEBUG
                // Add EventHandler
                m_myScanner.OnDone += new EventHandler(this.m_myScanner_OnDone);
                // Open Port
                m_myScanner.PortOpen = true;
#endif
                msgWindow = new MsgWindow();
                //msgWindow.RegHotKey("UShifTab", (int)KeyModifiers.None, (int)(System.Windows.Forms.Keys.F7));
                //msgWindow.RegHotKey("UTab", (int)KeyModifiers.None, (int)(System.Windows.Forms.Keys.F8));
                //msgWindow.RegHotKey("UIME", (int)KeyModifiers.Control, 190);
                //msgWindow.HotKeyPress += new OpenNETCF.Windows.Forms.KeyHookEventHandler(KeyDetected);
            }
            catch (DNWA.Exception.ArgumentException ex)
            {
                MessageBox.Show(ex.Message, "Port Open Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button1);
            }
        }

        public void StopScan()
        {
            try
            {
#if !DEBUG
                if (m_myScanner != null)
                {
                    // Close Port
                    m_myScanner.PortOpen = false;
                    m_myScanner.Dispose();
                    m_myScanner = null;
                }
#endif
            }
            catch (DNWA.Exception.ArgumentException ex)
            {
                MessageBox.Show(ex.Message, "Port Close Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button1);
            }
        }

        // Scanner OnDone Event
        private void m_myScanner_OnDone(object sender, EventArgs e)
        {
            string barType= string.Empty;
            string barLength= string.Empty;
            string barText = string.Empty; 
            // Get Barcode Type
            try
            {
                barType = new String(m_myScanner.InBufferType, 1);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "BarType Get Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button1);
            }
            // Get Barcode Count
            try
            {
                barLength = m_myScanner.InBufferCount.ToString();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "BarDigit Get Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button1);
            }
            // Get Barcode Data
            try
            {
                barText = m_myScanner.Input(Scanner.ALL_BUFFER);
            }
            catch (DNWA.Exception.ObjectDisposedException ex)
            {
                MessageBox.Show(ex.Message, "BarData Input Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button1);
            }
            if (GetBarCode != null)
            {
                ScanArgs tempE = new ScanArgs();
                tempE.Barcode = barText;
                tempE.CodeType = barType;
                tempE.Duration = DateTime.Now.Subtract(_startTime);
                GetBarCode(this, tempE);
            }
            else
            {
                //模拟键盘输入
                Win32.SendKeys(barText, true);
            }
        }

        #endregion

        /// <summary>
        /// 热键处理
        /// </summary>
        /// <param name="keyMessage"></param>
        /// <param name="keyData"></param>
        void KeyDetected(OpenNETCF.Win32.WM keyMessage, OpenNETCF.Windows.Forms.KeyData keyData)
        {
            try
            {
                //switch (keyData.KeyCode)
                //{
                //    case (int)Keys.F7://向上模拟shift tab
                //        if (keyMessage == OpenNETCF.Win32.WM.KEYUP)
                //        {
                //            Thread.Sleep(100);
                //            SendKeys.Send("+{Tab}");
                //        }
                //        break;

                //    case (int)Keys.F8://向下模拟 tab
                //        if (keyMessage == OpenNETCF.Win32.WM.KEYUP)
                //        {
                //            Thread.Sleep(100);
                //            SendKeys.Send("{Tab}");
                //        }
                //        break;
                //    default:
                //        break;
                //}
                if (keyData.KeyCode == 190)
                {
                    //显示软键盘
                    if (keyMessage == OpenNETCF.Win32.WM.KEYUP)
                    {
                        Thread.Sleep(250);
                        if (Sip.Visible)
                        {
                            Sip.Visible = false;
                        }
                        else
                        {
                            Sip.Visible = true;
                        }
                        Thread.Sleep(250);
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}
