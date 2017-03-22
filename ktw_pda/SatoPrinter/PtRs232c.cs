#define DEBUG

using System;
using System.Collections.Generic;
using System.Text;
using System.IO.Ports;
using System.Collections;
using System.Threading;
using Framework;
using DNWA.BHTCL.CommUtil;

namespace sato.pt
{
    /// <summary>
    /// RS232CでPT200e,PT400の制御を行う場合に使用するクラス
    /// </summary>
    public class PtRs232c
    {
        #region bluetooth
        // Line feed
        private readonly string CrLf = System.Environment.NewLine;
        // Bluetooth Device Index
        private int m_iBltDevIdx = -1;
        // Bluetooth MAC Address
        private string m_sBltMacAddr = "";

        private const int RESULT_CODE_SUCCESS = 0;
        private const int RESULT_CODE_FAILURE = 1;

#endregion

        #region グローバル変数
        private PtRs232cParam grvPrpParam;              // 通信パラメータ
        private SerialPort grvSrpPort = null;           // 通信ポート
        private int grvIntOutputStatus = Def.PRN_NONE;  // 印刷進行状況
        private bool grvBlnStopFlg = false;             // ストップフラグ
        private bool grvBlnTimeout = false;             // タイムアウトフラグ
        private bool grvBlnPrintingNow = false;         // 印刷中フラグ
        private System.ComponentModel.IContainer components = null; 
#endregion

#region コンストラクタ
        /// <summary>
        /// コンストラクタ
        /// </summary>
        public PtRs232c()
        {
            grvPrpParam.ComPort = "";
            grvPrpParam.Baudrate = Def.BAUDRATE_19200;
            grvPrpParam.Parity = Def.PARITY_NONE;
            grvPrpParam.Timeout = 500;
        }
#endregion

#region 公開メソッド

        /// <summary>
        /// connect with bluetooth 
        /// </summary>
        /// <param name="address"></param>
        /// <returns></returns>
        public int open(string address)
        {
            // BHTCL Class : Controls Bluetooth
            DNWA.BHTCL.Bluetooth bltCtrl = new DNWA.BHTCL.Bluetooth();
            bool bRetValue = false;

            do
            {
                // Bluetooth power on
                try
                {
                    // Sets or acquires the Bluetooth device power status.
                    // Property
                    //   Power status
                    if(bltCtrl.Power !=DNWA.BHTCL.Bluetooth.EN_POWER.ON)
                        bltCtrl.Power = DNWA.BHTCL.Bluetooth.EN_POWER.ON;
                }
                catch (Exception ex)
                {
                    Message.Warn("Set bluetooth power status failure. " + CrLf + ex.Message + CrLf);
                    break;
                }

                // Performs the initialization process.
                // Return values
                //   DLL_RET_SUCCESS	: Successful completion
                //   DLL_RET_ERROR	    : Unsuccessful completion
                if (BthUtil.DLL_RET_ERROR == BthUtil.BTH_Initialize())
                {
                    Message.Warn("Set initialize bluetooth failure." + CrLf);
                }

                // Specifies the PIN code. Pairing is not performed if an empty character string is specified. (The PIN code request is cancelled.)
                // Parameter
                //	lpszPINCode			: PIN CODE (length must be between 0 and 16 characters.) The PIN code request is cancelled if an empty character string is specified.
                // Return values
                //   DLL_RET_SUCCESS	: Successful completion
                //   DLL_RET_ERROR		: Unsuccessful completion
                if (BthUtil.DLL_RET_ERROR == BthUtil.BTH_SetPINCode("0000"))
                {
                    Message.Warn("Set bluetooth PIN status failure." + CrLf);
                }

                // Master Mode

                // MAC address input check
                //address = AppContext.Instance().GetConfig().GetValue(@"//setting/com", "value");
                if (0 == address.Length)
                {
                    Message.Warn("Please input Mac Address." + CrLf);
                    break;
                }

                //AppendAtResultText("Wait For Create Connection..." + CrLf);

                if (grvSrpPort == null)
                {
                    components = new System.ComponentModel.Container();
                    grvSrpPort = new SerialPort(components);
                    // Serial Port Timeout
                    grvSrpPort.ReadTimeout = 10000;
                    grvSrpPort.WriteTimeout = 10000; 
                }

                // Disconnect SPP
                bRetValue = DisconnectSpp();

                // Connect SPP
                bRetValue = ConnectSpp();
            } while (false);
            return 0;
        }

        // Disconnect SPP
        private bool DisconnectSpp()
        {
            bool bFuncRet = false;
            int iRetValue = 0;
            StringBuilder sbAddrDev = new StringBuilder(m_sBltMacAddr);
            string sMessage = "";

            do
            {
                // Close COM port.
                if (grvSrpPort == null)
                {
                }
                else if ( true == grvSrpPort.IsOpen)
                {
                    try
                    {
                        grvSrpPort.Close();
                    }
                    catch (Exception ex)
                    {
                        sMessage = ex.Message;
                        //ShowResult(RESULT_CODE_FAILURE, ex.Message);
                    }
                }

                // Disconnect.
                if (0 < m_sBltMacAddr.Length)
                {
                    // Disconnects the connection. (Master)
                    // Parameters
                    //   lpszAddress			: Bluetooth device address
                    // Return values
                    //   DLL_RET_SUCCESS		: Successful completion
                    //   DLL_RET_ERROR		    : Unsuccessful completion
                    //   DLL_RET_ERR_INDEX	    : Illegal index
                    iRetValue = BthUtil.BTH_DisconnectEx(sbAddrDev);

                    if (BthUtil.DLL_RET_SUCCESS != iRetValue)
                    {
                        ShowBthResult(iRetValue);
                        break;
                    }
                }

                bFuncRet = true;
            } while (false);

            m_iBltDevIdx = -1;
            m_sBltMacAddr = "";

            return bFuncRet;
        }

        // Show Bth Library Result Code 
        private void ShowBthResult(int iResult)
        {
            switch (iResult)
            {
                case BthUtil.DLL_RET_ERR_PORT:
                    AppendAtResultText("Use of specified port not possible." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_PROFILE:
                    AppendAtResultText("Illegal profile." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_NAME_BUFF_SIZE:
                    AppendAtResultText("Bluetooth device name output buffer size insufficient." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_ADDR_BUFF_SIZE:
                    AppendAtResultText("Device address output buffer size insufficient." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_BUFF_SIZE:
                    AppendAtResultText("Buffer size insufficient." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_INDEX:
                    AppendAtResultText("Illegal index." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERROR:
                    AppendAtResultText("Unsuccessful completion." + CrLf);
                    break;
                case BthUtil.DLL_RET_SUCCESS:
                    AppendAtResultText("Successful completion." + CrLf);
                    break;
                case BthUtil.DLL_RET_ALREADY_START_SVC:
                    AppendAtResultText("Service start-up complete." + CrLf);
                    break;
                case BthUtil.DLL_RET_CONNECT:
                    AppendAtResultText("Connection established." + CrLf);
                    break;
                case BthUtil.DLL_RET_DISCONNECT:
                    AppendAtResultText("Connection severed." + CrLf);
                    break;
                case BthUtil.DLL_RET_CONNECT_SPP_DEV_A:
                    AppendAtResultText("Serial profile connection established." + CrLf);
                    break;
                case BthUtil.DLL_RET_CONNECT_DUN_DT:
                    AppendAtResultText("Dial-up profile connection established." + CrLf);
                    break;
                default:
                    AppendAtResultText("Unknown error code." + CrLf);
                    break;
            }
        }

        // Display Information 
        private void AppendAtResultText(string str)
        {
            Message.Warn(str);
        }

        // Connect SPP
        private bool ConnectSpp()
        {
            bool bFuncRet = false;
            int iRetValue = 0;
            int i = 0;
            string address = AppContext.Instance().GetConfig().GetValue(@"//setting/com", "value");
            StringBuilder sbAddrDev = new StringBuilder(address);
            StringBuilder sbPortNumber = new StringBuilder(256);

            do
            {
                // Connect
                for (i = 0; i < 60; i++)
                {
                    // Establishes a connection with the Bluetooth device address. (Master)
                    // Parameters
                    //   lpszAddress			: Bluetooth device address
                    //   nProfile			    : Used profile. 0: SPP Master (SPP-DevA)
                    //   nPort				    : Port no. (from 0 to 9) Set automatically if “-1”.
                    //   bEncryption			: Encryption flag
                    //                          True  : the communication data is encrypted.
                    //                          False : the communication data is not encrypted.
                    // Return values
                    //   DLL_RET_SUCCESS		: Successful completion
                    //   DLL_RET_ERROR		    : Unsuccessful completion
                    //   DLL_RET_ERR_PROFILE	: Illegal profile
                    //   DLL_RET_ERR_PORT	    : Use of specified port not possible
                    iRetValue = BthUtil.BTH_ConnectEx(sbAddrDev, 0, -1, true);

                    if (BthUtil.DLL_RET_SUCCESS == iRetValue)
                    {
                        break;
                    }
                    else
                    {
                        Thread.Sleep(500);
                    }
                }

                if (BthUtil.DLL_RET_SUCCESS == iRetValue)
                {
                    m_sBltMacAddr = sbAddrDev.ToString();
                }
                else
                {
                    ShowBthResult(iRetValue);
                    break;
                }

                // Returns the virtual COM port name used with an SPP Master connection.
                // Parameters
                //	lpszPortName		    : Virtual COM port name storage buffer
                //	nBuffSize			    : Virtual COM port name storage buffer size
                // Return values
                //   DLL_RET_SUCCESS		: Successful completion
                //   DLL_RET_ERROR		    : Unsuccessful completion
                //   DLL_RET_ERR_BUFF_SIZE	: Buffer size insufficient
                iRetValue = BthUtil.BTH_GetPortNameClient(sbPortNumber, sbPortNumber.Capacity);
                if (BthUtil.DLL_RET_SUCCESS == iRetValue)
                {
                    //AppendAtResultText("SPP connect success. [" + sbPortNumber.ToString() + "]" + CrLf);
                }
                else
                {
                    ShowBthResult(iRetValue);
                    break;
                }

                // Open COM port.
                try
                {
                    grvSrpPort.PortName = sbPortNumber.ToString();
                    grvSrpPort.Open();
                }
                catch (Exception ex)
                {
                    ShowResult(RESULT_CODE_FAILURE, ex.Message);
                    break;
                }

                bFuncRet = true;
            } while (false);

            return bFuncRet;
        }

        // Show Result Code    
        private void ShowResult(int iResult, string sAddMessage)
        {
            string sMessage = "";

            switch (iResult)
            {
                case RESULT_CODE_SUCCESS:
                    sMessage = "Send success.";
                    break;
                case RESULT_CODE_FAILURE:
                    sMessage = "Send failure.";
                    break;
                default:
                    sMessage = "Unknown error code.";
                    break;
            }

            if (0 < sAddMessage.Length)
            {
                sMessage += CrLf + sAddMessage;
            }
            AppendAtResultText(sMessage + CrLf);
        }


        /// <summary>
        /// COMポートをオープンする
        /// </summary>
        /// <param name="prpPrm">RS232C接続の設定</param>
        /// <returns>
        /// オープンの成否
        /// PT_OK:オープン成功
        /// PT_NG:オープン失敗
        /// PT_PARAMERR:パラメータエラー
        /// PT_SYSTEMERR:システムエラー
        /// </returns>
        public int open(PtRs232cParam prpPrm)
        {
            int intRet = Def.PT_OK;
            int intBaudrate = 0;
            Parity prtParity = Parity.None;
            bool blnPramaChack = true;
            bool blnCloseflg = true;

            Common.outputDebugLog("PtRs232c", "open", "====================================開始====================================");
            try
            {
                /***********************
                 * RS232C接続の設定取得
                 ***********************/
                grvPrpParam = prpPrm;

                // ボーレート取得
                switch (grvPrpParam.Baudrate)
                {
                    case Def.BAUDRATE_9600:
                        intBaudrate = 9600;
                        break;
                    case Def.BAUDRATE_19200:
                        intBaudrate = 19200;
                        break;
                    case Def.BAUDRATE_38400:
                        intBaudrate = 38400;
                        break;
                    case Def.BAUDRATE_57600:
                        intBaudrate = 57600;
                        break;
                    default:
                        blnPramaChack = false;
                        break;
                }

                // パリティ取得
                switch (grvPrpParam.Parity)
                {
                    case Def.PARITY_NONE:
                        prtParity = Parity.None;
                        break;
                    case Def.PARITY_ODD:
                        prtParity = Parity.Odd;
                        break;
                    case Def.PARITY_EVEN:
                        prtParity = Parity.Even;
                        break;
                    default:
                        blnPramaChack = false;
                        break;
                }

                /***********************
                 * ポートオープン
                 ***********************/
                // ポートの状態確認
                if ((grvSrpPort != null) && (grvSrpPort.IsOpen))
                {
                    // ポートが開きっぱなしなので一旦切断
                    blnCloseflg = close();
                }
                // オープン可能？
                if (blnCloseflg)
                {
                    // チェックOKならばポートオープン
                    if (blnPramaChack)
                    {
                        components = new System.ComponentModel.Container();
                        grvSrpPort = new SerialPort(components);

                        // 設定
                        grvSrpPort.PortName = grvPrpParam.ComPort;  // COMポート（ユーザ指定）
                        grvSrpPort.BaudRate = intBaudrate;          // ボーレート（ユーザ指定）
                        grvSrpPort.Parity = prtParity;              // パリティ（ユーザ指定）
                        grvSrpPort.DataBits = 8;                    // データビット(固定)
                        grvSrpPort.StopBits = StopBits.One;         // ストップビット(固定)
                        grvSrpPort.WriteBufferSize = 20000;         // 送信バッファサイズ(固定)

                        Common.outputDebugLog("PtRs232c", "open", "COMポート:" + grvSrpPort.PortName);
                        Common.outputDebugLog("PtRs232c", "open", "ボーレート:" + grvSrpPort.BaudRate);
                        Common.outputDebugLog("PtRs232c", "open", "パリティ:" + grvPrpParam.Parity);
                        Common.outputDebugLog("PtRs232c", "open", "データビット:" + grvSrpPort.DataBits);
                        Common.outputDebugLog("PtRs232c", "open", "ストップビット(:" + grvSrpPort.StopBits);
                        Common.outputDebugLog("PtRs232c", "open", "送信バッファサイズ:" + grvSrpPort.WriteBufferSize);

                        // タイムアウト設定ありの場合は、タイムアウト設定
                        if (grvPrpParam.Timeout > 0)
                        {
                            grvSrpPort.WriteTimeout = grvPrpParam.Timeout;
                            grvSrpPort.ReadTimeout = grvPrpParam.Timeout;
                            Common.outputDebugLog("PtRs232c", "open", "送信タイムアウト設定：" + grvSrpPort.WriteTimeout);
                            Common.outputDebugLog("PtRs232c", "open", "受信タイムアウト設定：" + grvSrpPort.ReadTimeout);
                        }

                        // オープン
                        grvSrpPort.Open();
                        Common.outputDebugLog("PtRs232c", "open", "オープン" );
                        
                    }
                    else
                    {
                        // パラメータエラーを返す
                        intRet = Def.PT_PARAMERR;

                        Common.outputDebugLog("PtRs232c", "open", "パラメータエラー");
                    }
                }
                else
                {
                    // 接続失敗を返す
                    intRet = Def.PT_NG;

                    Common.outputDebugLog("PtRs232c", "open", "送受信中のため接続失敗");
                }
            }
            catch (InvalidOperationException ioe)
            {   // grvSrpPort.Open():指定したポートは開いています。 
                // エラーログ出力
                Common.outputLog("PtRs232c", "open", "指定したポートは開いています\t" + ioe.Message);

                // エラー発生のため、ポートクローズ
                close();

                intRet = Def.PT_NG;
            }
            catch (ArgumentOutOfRangeException aoe)
            {   // grvSrpPort.Open():このインスタンスの 1 つ以上のプロパティが無効です。
                // エラーログ出力
                Common.outputLog("PtRs232c", "open", "Openパラメータ異常\t" + aoe.Message);

                // エラー発生のため、ポートクローズ
                close();

                intRet = Def.PT_NG;
            }
            catch (ArgumentException ae)
            {   // grvSrpPort.Open():ポート名が "COM" で始まっていません。またはポートのこのファイルの種類はサポートされていません。

                // エラーログ出力
                Common.outputLog("PtRs232c", "open", "ポート名不正\t" + ae.Message);

                // エラー発生のため、ポートクローズ
                close();

                intRet = Def.PT_NG;
            }
            catch (UnauthorizedAccessException uae)
            {   // grvSrpPort.Open():ポートへのアクセスが拒否されています。 

                // エラーログ出力
                Common.outputLog("PtRs232c", "open", "ポートアクセス拒否\t" + uae.Message);

                // エラー発生のため、ポートクローズ
                close();

                intRet = Def.PT_NG;
            }
            catch (Exception e)
            {
                // エラーログ出力
                Common.outputLog("PtRs232c", "open", e.Message);

                // エラー発生のため、ポートクローズ
                close();

                intRet = Def.PT_NG;
            }

            Common.outputDebugLog("PtRs232c", "open", "====================================終了====================================");
            return intRet;
        }

        /// <summary>
        /// RS232Cの設定を取得する
        /// </summary>
        /// <returns>
        /// !NULL:設定内容
        /// </returns>
        public PtRs232cParam getParam()
        {
            Common.outputDebugLog("PtRs232c", "getParam", "RS232Cの設定返答");
            /***********************
             * RS232Cの設定返答
             ***********************/
            return grvPrpParam;
        }

        /// <summary>
        /// ラベル印刷を行う
        /// プリンタのステータス確認から印刷完了までを行う
        /// 進行状況は「getOutputStatus」で確認可能
        /// 印刷情報は「Shift_JIS」で送信を行う
        /// </summary>
        /// <param name="bytData">印刷内容</param>
        /// <returns>
        /// !NULL:結果
        /// </returns>
        public PtOutputResult outputLabel(byte[] bytData)
        {
           

            PtOutputResult porRet;
            PtReceiveData prdResult;
            byte bytRes;

            porRet.Ret = Def.PTS_SYSTEMERR;
            porRet.Status = 0x00;
            porRet.StatusData = new byte[11];

            grvSrpPort.Write(bytData, 0, bytData.Length);
            return porRet;

            Common.outputDebugLog("PtRs232c", "outputLabel", "====================================開始====================================");
            try
            {
                // 印刷中ではない？
                if (grvBlnPrintingNow == false)
                {
                    // 印刷開始
                    grvBlnPrintingNow = true;

                    Common.outputDebugLog("PtRs232c", "outputLabel", "印刷開始");

                    grvIntOutputStatus = Def.PRN_PRINTING;  // 印刷中
                    DoEvent();
                    Common.outputDebugLog("PtRs232c", "outputLabel", "OutputStatus：" + grvIntOutputStatus);

                    // プリンタステータスの確認(送信前)
                    prdResult = PtChkPrintTransmissionCondition();

                    // 印刷可能？
                    if (prdResult.Ret == Def.PTS_END_RECEIVE)
                    {
                        if (Common.chkPrintTransmissionCondition(prdResult.ReceiveData[Common.STR_INDEX]) == Common.STR_OK)
                        {
                            grvIntOutputStatus = Def.PRN_LABEL_SEND;  // ラベルデータ送信中
                            DoEvent();
                            Common.outputDebugLog("PtRs232c", "outputLabel", "OutputStatus：" + grvIntOutputStatus);

                            // 印刷データ送信
                            prdResult = sendCommand(bytData);
                            if (prdResult.Ret == Def.PTS_END_RECEIVE)
                            {
                                bytRes = prdResult.ReceiveData[0];  // ACK,NAKの退避

                                // 印字完了待ち
                                DoEventSleep(500);

                                grvIntOutputStatus = Def.PRN_END_WAITING;  // 完了待ち
                                DoEvent();
                                Common.outputDebugLog("PtRs232c", "outputLabel", "OutputStatus：" + grvIntOutputStatus);

                                // プリンタステータスの確認(完了待ち)
                                prdResult = PtChkPrintCompletionConditionLabel();

                                // 印刷完了？
                                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                                {
                                    if (Common.chkPrintCompletionCondition(prdResult.ReceiveData[Common.STR_INDEX]) == Common.STR_OK)
                                    {
                                        // 戻り値セット
                                        porRet.Ret = bytRes;
                                        porRet.Status = prdResult.ReceiveData[Common.STR_INDEX];
                                        porRet.StatusData = prdResult.ReceiveData;
                                    }
                                    else
                                    {
                                        // エラー内容セット
                                        porRet.Ret = Def.PTS_NAK;
                                        porRet.Status = prdResult.ReceiveData[Common.STR_INDEX];
                                        porRet.StatusData = prdResult.ReceiveData;
                                    }
                                    grvIntOutputStatus = Def.PRN_NONE;  // 印刷受付無し
                                    Common.outputDebugLog("PtRs232c", "outputLabel", "OutputStatus：" + grvIntOutputStatus);
                                }
                                else
                                {
                                    // エラー内容セット
                                    porRet.Ret = prdResult.Ret;
                                }
                            }
                            else
                            {
                                // エラー内容セット
                                porRet.Ret = prdResult.Ret;
                            }
                        }
                        else
                        {
                            // エラー内容セット
                            porRet.Ret = Def.PTS_NAK;
                            porRet.Status = prdResult.ReceiveData[Common.STR_INDEX];
                            porRet.StatusData = prdResult.ReceiveData;
                        }
                    }
                    else
                    {
                        // エラー内容セット
                        porRet.Ret = prdResult.Ret;
                    }

                    // 印刷終了
                    grvBlnPrintingNow = false;
                    Common.outputDebugLog("PtRs232c", "outputLabel", "印刷終了");
                }
                else
                {
                    // 印刷中につき、受付不可
                    porRet.Ret = Def.PRN_PRINTING;
                    Common.outputDebugLog("PtRs232c", "outputLabel", "印刷中につき、受付不可");
                }
            }
            catch (Exception e)
            {
                // エラーログ出力
                Common.outputLog("PtRs232c", "outputLabel", e.Message);
                porRet.Ret = Def.PTS_SYSTEMERR;

            }

            Common.outputDebugLog("PtRs232c", "outputLabel", "戻り値：" + porRet.Ret);
            Common.outputDebugLog("PtRs232c", "outputLabel", "戻り値(ステータス)：" + (char)porRet.Status);
#if DEBUG
            String strLog = "";
            for (int ii = 0; ii < porRet.StatusData.Length; ii++)
            {
                strLog = strLog + (char)porRet.StatusData[ii];
            }

            Common.outputDebugLog("PtRs232c", "outputLabel", "戻り値(データ)：" + strLog);
#endif
            Common.outputDebugLog("PtRs232c", "outputLabel", "====================================終了====================================");
            return porRet;
        }
         
        /// <summary>
        /// 印刷状況を取得する
        /// </summary>
        /// <returns>
        /// 結果
        /// </returns>
        public int getOutputStatus()
        {
            Common.outputDebugLog("PtRs232c", "getOutputStatus", "印刷状況返答");
            return grvIntOutputStatus;
        }

        /// <summary>
        /// 送信、受信処理を中止する
        /// </summary>
        /// <returns>
        /// 結果
        /// </returns>
        public void stop()
        {
            Common.outputDebugLog("PtRs232c", "stop", "送信、受信処理を中止");
            grvBlnStopFlg = true;
        }

        /// <summary>
        /// プリンタのステータスを取得する
        /// </summary>
        /// <returns>
        /// 結果
        /// </returns>
        public PtReceiveData getPtStatus()
        {
            byte[] bytEnq = new byte[1];

            bytEnq[0] = Common.ENQ;

            Common.outputDebugLog("PtRs232c", "getPtStatus", "コマンド:" + bytEnq[0]);
            // ステータス送信
            return sendCommand(bytEnq);
        }

        /// <summary>
        /// コマンドを送信する
        /// コマンドを送信し、相手からの応答を受信する所までを行う。
        /// </summary>
        /// <param name="bytCommand">送信するコマンドを格納したバイト配列</param>
        /// <returns>
        /// 結果
        /// </returns>
        public PtReceiveData sendCommand(byte[] bytCommand)
        {
            PtReceiveData pstRet;

            // 初期化
            pstRet.ReceiveLen = 0;
            pstRet.ReceiveData = new byte[0];
            pstRet.Ret = Def.PTS_END_RECEIVE;

            Common.outputDebugLog("PtRs232c", "sendCommand", "====================================開始====================================");
            try
            {
                // 接続中？
                if ((grvSrpPort != null) && (grvSrpPort.IsOpen == true))
                {
                    // 送信
                    Common.outputDebugLog("PtRs232c", "sendCommand", "送信");
                    pstRet.Ret = sockSend(bytCommand);
                    if (pstRet.Ret == 0)
                    {
                        // 受信
                        Common.outputDebugLog("PtRs232c", "sendCommand", "受信");
                        pstRet = sockReceive();
                    }
                }
                else
                {
                    Common.outputDebugLog("PtRs232c", "sendCommand", "未接続につき、受付不可");
                    // 未接続につき、受付不可
                    pstRet.Ret = Def.PTS_UNCONNECTION;
                }
            }
            catch (Exception e)
            {					// Error
                // エラーログ出力
                Common.outputLog("PtRs232c", "sendCommand", e.Message);
                pstRet.Ret = Def.PTS_SYSTEMERR;
                return pstRet;
            }

            Common.outputDebugLog("PtRs232c", "sendCommand", "戻り値：" + pstRet.Ret);
#if DEBUG
            String strLog = "";
            for (int ii = 0; ii < pstRet.ReceiveLen; ii++)
            {
                strLog = strLog + (char)pstRet.ReceiveData[ii];
            }

            Common.outputDebugLog("PtRs232c", "sendCommand", "戻り値(データ)：" + strLog);
#endif
            Common.outputDebugLog("PtRs232c", "sendCommand", "====================================終了====================================");
            return pstRet;
        }

        /// <summary>
        /// キャンセルコマンドを送信する
        /// </summary>
        /// <returns>
        /// 結果
        /// </returns>
        public PtReceiveData sendCancel()
        {
            byte[] bytCan = new byte[1];

            bytCan[0] = Common.CAN;

            Common.outputDebugLog("PtRs232c", "sendCancel", "コマンド:" + bytCan[0]);
            // キャンセルコマンドを送信
            return sendCommand(bytCan);
        }

        /// <summary>
        /// COMポートをクローズする
        /// </summary>
        /// <returns>
        /// 結果
        /// </returns>
        public bool close()
        {
            bool bolRet = true;

            Common.outputDebugLog("PtRs232c", "close", "====================================開始====================================");
            try
            {
                // 印刷中ではない？
                if (grvBlnPrintingNow == false)
                {
                    // ポートクローズ
                    if (grvSrpPort != null)
                    {
                        Common.outputDebugLog("PtRs232c", "close", "ポートクローズ");
                        grvSrpPort.Close();
                    }

                    // SerialPort解放
                    if (grvSrpPort != null)
                    {
                        grvSrpPort.Dispose();
                        Common.outputDebugLog("PtRs232c", "close", "SerialPort解放");
                    }

                    // System.ComponentModel.IContainer解放
                    if (components != null)
                    {
                        components.Dispose();
                        Common.outputDebugLog("PtRs232c", "close", "System.ComponentModel.IContainer解放");
                    }

                    if (m_sBltMacAddr.Length > 0)
                        DisconnectSpp();
                }
                else
                {
                    Common.outputDebugLog("PtRs232c", "close", "印刷中なので、クローズ不可");
                    // 印刷中なので、クローズ不可
                    bolRet = false;
                }
            }
            catch (InvalidOperationException ioe)
            {
                // grvSrpPort.Close():指定したポートが開いていません。
                Common.outputLog("PtRs232c", "close", "ポートが閉じています\t" + ioe.Message);
                // 既に閉じているだけなので、正常として扱う
            }
            catch (Exception e)
            {
                // エラーログ出力
                Common.outputLog("PtRs232c", "close", e.Message);

                bolRet = false;
            }

            Common.outputDebugLog("PtRs232c", "close", "====================================終了====================================");
            return bolRet;
        }

#endregion

#region 非公開メソッド
        /// <summary>
        /// 送信
        /// </summary>
        /// <param name="prmBytSendData">送信するデータを格納したバイト配列</param>
        /// <returns>
        /// 0：正常終了
        /// PTS_STOP：中止
        /// PTS_TIMEOUT：タイムアウト
        /// PTS_SYSTEMERR：システムエラー
        /// </returns>
        private byte sockSend(byte[] prmBytSendData)
        {
            byte bytRet = 0;

            Timer timTimeOut = null;            // タイムアウト監視タイマー
            TimerCallback tcbTimeOut = null;    // タイムアウト時に呼ぶメソッド

            Common.outputDebugLog("PtRs232c", "sockSend", "====================================開始====================================");
            try
            {
                // 中止された？
                DoEvent();
                if (PtChkStopFlg() == true)
                {
                    Common.outputDebugLog("PtRs232c", "sockSend", "送信前にユーザより中止受付");
                    bytRet = Def.PTS_STOP;
                }
                else
                {
                    // 送信可能な場合、送信を行う
                    if (bytRet == 0)
                    {
                        // 送信前にバッファクリア
                        grvSrpPort.DiscardInBuffer();
                        grvSrpPort.DiscardOutBuffer();
                        Common.outputDebugLog("PtRs232c", "sockSend", "送信開始");
                        //送信
                        grvSrpPort.Write(prmBytSendData, 0, prmBytSendData.Length);
                        Common.outputDebugLog("PtRs232c", "sockSend", "送信完了");
                    }
                }
            }
            catch (ArgumentNullException ae)
            {   //grvSrpPort.Write:渡された buffer が null 参照 (Visual Basic では Nothing) です。 
                // エラーログ出力
                Common.outputLog("PtRs232c", "sockSend", "送信バッファがNULL参照です\t" + ae.Message);
                grvSrpPort.DiscardOutBuffer();   // バッファクリア
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (InvalidOperationException ioe)
            {   //grvSrpPort.Write:指定したポートが開いていません。  
                // エラーログ出力
                Common.outputLog("PtRs232c", "sockSend", "ポートが閉じています\t" + ioe.Message);
                // ポートが閉じているので、バッファクリアは行わない。
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentOutOfRangeException aoe)
            {   //grvSrpPort.Write:offset パラメータまたは count パラメータが、渡される buffer の有効な領域外です。offset または count が 0 未満です。 
                // エラーログ出力
                Common.outputLog("PtRs232c", "sockSend", "offset、count指定ミス\t" + aoe.Message);
                grvSrpPort.DiscardOutBuffer();   // バッファクリア
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentException ae)
            {   //grvSrpPort.Write:offset に count を加算した値が、buffer の長さを超えています。
                // エラーログ出力
                Common.outputLog("PtRs232c", "sockSend", "バッファオーバー\t" + ae.Message);
                grvSrpPort.DiscardOutBuffer();   // バッファクリア
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (TimeoutException te)
            {   //grvSrpPort.Write:タイムアウト時間内に操作が完了しませんでした。 
                // エラーログ出力
                Common.outputLog("PtRs232c", "sockSend", "送信タイムアウト発生\t" + te.Message);
                grvSrpPort.DiscardOutBuffer();   // バッファクリア
                bytRet = Def.PTS_TIMEOUT;
            }
            catch (Exception e)
            {					//Error
                // エラーログ出力
                Common.outputLog("PtRs232c", "sockSend", e.Message);
                grvSrpPort.DiscardOutBuffer();   // バッファクリア
                bytRet = Def.PTS_SYSTEMERR;
            }
            finally
            {
                if (timTimeOut != null)
                {
                    Common.outputDebugLog("PtRs232c", "sockSend", "タイマー監視終了");
                    timTimeOut.Dispose();
                }
            }

            Common.outputDebugLog("PtRs232c", "sockSend", "====================================終了====================================");
            return bytRet;
        }

        /// <summary>
        /// 受信
        /// </summary>
        /// <returns>
        /// 受信情報
        /// </returns>
        private PtReceiveData subSockReceive()
        {
            PtReceiveData rdtRet;               // ﾘﾀｰﾝｺｰﾄﾞ
            int intReadLen;                     // 読み込みﾃﾞｰﾀ数
            Timer timTimeOut = null;            // タイムアウト監視タイマー
            TimerCallback tcbTimeOut = null;    // タイムアウト時に呼ぶメソッド

            // 初期化
            rdtRet.ReceiveLen = 0;
            rdtRet.ReceiveData = new byte[0];
            rdtRet.Ret = Def.PTS_END_RECEIVE;

            Common.outputDebugLog("PtRs232c", "subSockReceive", "====================================開始====================================");
            try
            {

                // ﾀｲﾑｱｳﾄ有り？
                if (grvPrpParam.Timeout > 0)
                {
                    // タイマーセット
                    grvBlnTimeout = false;  // フラグ初期化
                    tcbTimeOut = new TimerCallback(timReceiveTimer);
                    timTimeOut = new Timer(tcbTimeOut, null, grvPrpParam.Timeout, Timeout.Infinite);
                    Common.outputDebugLog("PtRs232c", "subSockReceive", "タイマーセット：" + grvPrpParam.Timeout);
                }

                while (true)
                {
                    DoEventSleep(100);

                    // 読取可能数取得
                    intReadLen = grvSrpPort.BytesToRead;

                    Common.outputDebugLog("PtRs232c", "subSockReceive", "受信バイト数：" + intReadLen);

                    // 読取可能ﾃﾞｰﾀ有り？
                    if (intReadLen > 0)
                    {
                        // 受信長セット
                        rdtRet.ReceiveLen = intReadLen;

                        // 受信
                        rdtRet.ReceiveData = new byte[intReadLen];
                        for (int ii = 0; ii < intReadLen; ii++)
                        {
                            rdtRet.ReceiveData[ii] = (byte)grvSrpPort.ReadByte();
                            Common.outputDebugLog("PtRs232c", "subSockReceive", "受信データ：" + (char)rdtRet.ReceiveData[ii]);
                        }
                        break;  // 受信処理完了
                    }
                    else
                    {
                        // 中止された？
                        DoEvent();
                        if (PtChkStopFlg() == true)
                        {
                            Common.outputDebugLog("PtRs232c", "subSockReceive", "ユーザより中止受付");
                            rdtRet.Ret = Def.PTS_STOP;
                            break;
                        }

                        // ﾀｲﾑｱｳﾄ判定する？
                        if (grvPrpParam.Timeout > 0)
                        {
                            // ﾀｲﾑｱｳﾄ？
                            DoEvent();
                            if (grvBlnTimeout == true)
                            {
                                Common.outputDebugLog("PtRs232c", "subSockReceive", "受信ﾀｲﾑｱｳﾄ発生");
                                // プリンタより応答が有りません
                                rdtRet.Ret = Def.PTS_TIMEOUT;
                                break;
                            }
                        }
                    }
                }
            }
            catch (InvalidOperationException ioe)
            {	// grvSrpPort.ReadByte():指定したポートが開いていません。
                // エラーログ出力
                Common.outputLog("PtRs232c", "subSockReceive", "ポートが閉じています\t" + ioe.Message);
                // ポートが閉じているので、バッファクリアは行わない。
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (TimeoutException te)
            {	// grvSrpPort.ReadByte():タイムアウト時間内に操作が完了しませんでした。またはバイトが読み取られませんでした。

                // エラーログ出力
                Common.outputLog("PtRs232c", "subSockReceive", "受信タイムアウト発生\t" + te.Message);
                grvSrpPort.DiscardInBuffer();   // バッファクリア
                rdtRet.Ret = Def.PTS_TIMEOUT;
            }
            catch (Exception e)
            {	// Error
                // エラーログ出力
                Common.outputLog("PtRs232c", "subSockReceive", e.Message);
                grvSrpPort.DiscardInBuffer();   // バッファクリア
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            finally
            {
                // メモリ解放
                if (timTimeOut != null)
                {
                    Common.outputDebugLog("PtRs232c", "subSockReceive", "タイマー監視終了");
                    timTimeOut.Dispose();
                }
            }

            Common.outputDebugLog("PtRs232c", "subSockReceive", "====================================終了====================================");
            return rdtRet;
        }

        /// <summary>
        /// 受信
        /// </summary>
        /// <returns>
        /// 受信情報
        /// </returns>
        private PtReceiveData sockReceive()
        {
            PtReceiveData rdtRet;               // ﾘﾀｰﾝｺｰﾄﾞ
            PtReceiveData rdtsrWork;            // 受信情報
            ArrayList arlReceiveData = new ArrayList();
            byte bytEndData;                    // 最後の受信データ

            // 初期化
            rdtRet.ReceiveLen = 0;
            rdtRet.ReceiveData = new byte[0];
            rdtRet.Ret = Def.PTS_END_RECEIVE;

            Common.outputDebugLog("PtRs232c", "sockReceive", "====================================開始====================================");
            try
            {

                while (true)
                {
                    DoEventSleep(100);

                    // 受信
                    rdtsrWork = subSockReceive();

                    // 受信完了？
                    if (rdtsrWork.Ret == Def.PTS_END_RECEIVE)
                    {
                        if (rdtsrWork.ReceiveData[0] == Common.ACK || rdtsrWork.ReceiveData[0] == Common.NAK)
                        {
                            // 受信内容セット
                            rdtRet = rdtsrWork;
                            break;  // 受信処理完了
                        }
                        else if (rdtsrWork.ReceiveData[0] == Common.STX)
                        {
                            bytEndData = 0;
                            // 受信データ取得
                            for (int ii = 0; ii < rdtsrWork.ReceiveLen; ii++)
                            {
                                arlReceiveData.Add(rdtsrWork.ReceiveData[ii]);
                                if (rdtsrWork.ReceiveData[ii] == Common.ETX)
                                {
                                    bytEndData = rdtsrWork.ReceiveData[ii];
                                }
                            }

                            while (bytEndData != Common.ETX)    // ETX受信まで繰り返す
                            {
                                // 受信
                                rdtsrWork = subSockReceive();

                                // 受信完了？
                                if (rdtsrWork.Ret == Def.PTS_END_RECEIVE)
                                {
                                    bytEndData = 0;
                                    // 受信データ取得
                                    for (int ii = 0; ii < rdtsrWork.ReceiveLen; ii++)
                                    {
                                        arlReceiveData.Add(rdtsrWork.ReceiveData[ii]);
                                        if (rdtsrWork.ReceiveData[ii] == Common.ETX)
                                        {
                                            bytEndData = rdtsrWork.ReceiveData[ii];
                                        }
                                    }
                                }
                                else
                                {
                                    rdtRet = rdtsrWork;
                                    break;  // 終了
                                }
                            }

                            Common.outputDebugLog("PtRs232c", "sockReceive", "受信データ完了");

                            // 正常にデータを受け取れた場合は受信データセット
                            if (rdtsrWork.Ret == Def.PTS_END_RECEIVE)
                            {
                                // 受信長セット
                                rdtRet.ReceiveLen = arlReceiveData.Count;
                                // 受信データセット
                                rdtRet.ReceiveData = new byte[rdtRet.ReceiveLen];
                                for (int ii = 0; ii < rdtRet.ReceiveLen; ii++)
                                {
                                    rdtRet.ReceiveData[ii] = (byte)arlReceiveData[ii];
                                }
                            }
                            break;  // 受信処理完了
                        }
                        else
                        {
                            // エラーログ出力
                            Common.outputDebugLog("PtRs232c", "sockReceive", "規定外応答:" + rdtsrWork.ReceiveData[0]);
                            // 受信処理継続
                        }
                    }
                    else
                    {
                        // エラー内容セット
                        rdtRet = rdtsrWork;
                        break;  // 受信処理完了
                    }
                }
            }
            catch (Exception e)
            {	// Error
                // エラーログ出力
                Common.outputLog("PtRs232c", "sockReceive", e.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            finally
            {
                arlReceiveData.Clear();
            }

            Common.outputDebugLog("PtRs232c", "sockReceive", "====================================終了====================================");
            return rdtRet;
        }

        /// <summary>
        /// 印字送信前のプリンタ状態確認を行う
        /// </summary>
        /// <returns>
        /// !NULL:結果
        /// </returns>
        private PtReceiveData PtChkPrintTransmissionCondition()
        {
            PtReceiveData prdResult;
            int intStRet = Common.STR_NOT_POS_RES;

            Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "====================================開始====================================");
            do
            {
                Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "プリンタ状態確認開始");
                // プリンタのステータス取得
                prdResult = getPtStatus();
                Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "プリンタ状態確認完了：" + prdResult.Ret);

                // ステータス取得成功
                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                {
                    intStRet = Common.chkPrintTransmissionCondition(prdResult.ReceiveData[Common.STR_INDEX]);
                    Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "ステータス解析：" + intStRet);
                    // ステータス判断・送信可能？
                    switch (intStRet)
                    {
                        case Common.STR_OK:             // 印刷可能
                            break;
                        case Common.STR_STANDBY:        // 待機
                            grvIntOutputStatus = Def.PRN_PRINTING;  // 印刷中
                            DoEventSleep(500);
                            break;
                        case Common.STR_OFF_LINE:       // 復旧可能(オフライン)
                            grvIntOutputStatus = Def.PRN_OFF_LINE;  // オフライン
                            DoEvent();
                            break;
                        case Common.STR_HEAD_OPEN:       // 復旧可能(ヘッドオープン)
                            grvIntOutputStatus = Def.PRN_HEAD_OPEN;  // ヘッドオープン
                            DoEvent();
                            break;
                        case Common.STR_PAPER_END:      // 復旧可能(ペーパーエンド)
                            grvIntOutputStatus = Def.PRN_PAPER_END;  // ペーパーエンド
                            DoEvent();
                            break;
                        case Common.STR_RIBON_END:       // 復旧可能(リボンエンド)
                            grvIntOutputStatus = Def.PRN_RIBON_END;  // リボンエンド
                            DoEvent();
                            break;
                        case Common.STR_SENSOR_ERROR:   // 復旧可能(センサエラー)
                            grvIntOutputStatus = Def.PRN_SENSOR_ERROR;  // センサエラー
                            DoEvent();
                            break;
                        case Common.STR_COVER_OPENING:  // 復旧可能(カバーオープン)
                            grvIntOutputStatus = Def.PRN_COVER_OPENING;  // カバーオープン
                            DoEvent();
                            break;
                        case Common.STR_NOT_POS_RES:    // 復旧不可
                        default:
                            break;
                    }
                    Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "OutputStatus：" + grvIntOutputStatus);
                }
                else
                {
                    // 異常検知のため終了
                    break;
                }
            } while (intStRet != Common.STR_OK && intStRet != Common.STR_NOT_POS_RES);

            Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "====================================終了====================================");
            return prdResult;
        }

        /// <summary>
        /// 印字完了の監視を行う(ラベル用)
        /// </summary>
        /// <returns>
        /// !NULL:結果
        /// </returns>
        private PtReceiveData PtChkPrintCompletionConditionLabel()
        {
            PtReceiveData prdResult;
            int intStRet = Common.STR_NOT_POS_RES;

            Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "====================================開始====================================");
            do
            {
                Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "プリンタ状態確認開始");
                // プリンタのステータス取得
                prdResult = getPtStatus();
                Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "プリンタ状態確認完了：" + prdResult.Ret);

                // ステータス取得成功
                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                {
                    intStRet = Common.chkPrintCompletionCondition(prdResult.ReceiveData[Common.STR_INDEX]);
                    Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "ステータス解析：" + intStRet);
                    // ステータス判断・送信可能？
                    switch (intStRet)
                    {
                        case Common.STR_OK:             // 印刷完了
                            break;
                        case Common.STR_STANDBY:        // 待機
                            grvIntOutputStatus = Def.PRN_END_WAITING;  // 完了待ち
                            DoEventSleep(500);
                            break;
                        case Common.STR_OFF_LINE:       // 復旧可能(オフライン)
                            grvIntOutputStatus = Def.PRN_OFF_LINE;  // オフライン
                            DoEvent();
                            break;
                        case Common.STR_HEAD_OPEN:       // 復旧可能(ヘッドオープン)
                            grvIntOutputStatus = Def.PRN_HEAD_OPEN;  // ヘッドオープン
                            DoEvent();
                            break;
                        case Common.STR_PAPER_END:      // 復旧可能(ペーパーエンド)
                            grvIntOutputStatus = Def.PRN_PAPER_END;  // ペーパーエンド
                            DoEvent();
                            break;
                        case Common.STR_RIBON_END:       // 復旧可能(リボンエンド)
                            grvIntOutputStatus = Def.PRN_RIBON_END;  // リボンエンド
                            DoEvent();
                            break;
                        case Common.STR_SENSOR_ERROR:   // 復旧可能(センサエラー)
                            grvIntOutputStatus = Def.PRN_SENSOR_ERROR;  // センサエラー
                            DoEvent();
                            break;
                        case Common.STR_COVER_OPENING:  // 復旧可能(カバーオープン)
                            grvIntOutputStatus = Def.PRN_COVER_OPENING;  // カバーオープン
                            DoEvent();
                            break;
                        case Common.STR_NOT_POS_RES:    // 復旧不可
                        default:
                            break;
                    }
                    Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "OutputStatus：" + grvIntOutputStatus);
                }
                else
                {
                    // 異常検知のため終了
                    break;
                }
            } while (intStRet != Common.STR_OK && intStRet != Common.STR_NOT_POS_RES && intStRet != Common.STR_STANDBY);

            Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "====================================終了====================================");
            return prdResult;
        }
  
        /// <summary>
        /// ストップフラグの状態をチェックする
        /// </summary>
        /// <returns>
        /// true：ストップ
        /// false：続行
        /// </returns>
        private bool PtChkStopFlg()
        {
            bool bolRet = false;

            if (grvBlnStopFlg == true)
            {
                grvBlnStopFlg = false;

                bolRet = true;
            }

            return bolRet;
        }

        /// <summary>
        /// タイムアウト監視
        /// </summary>
        private void timReceiveTimer(Object stateInfo)
        {
            grvBlnTimeout = true;      // タイムアウトを設定
        }

        /// <summary>
        /// DoEvent
        /// </summary>
        private void DoEvent()
        {
            System.Windows.Forms.Application.DoEvents();
        }

        /// <summary>
        /// Windowsメッセージを全て処理をし、Sleepします。
        /// </summary>
        /// <param name="intMs">待機時間(ms)</param>
        private void DoEventSleep(int intMs)
        {
            DoEvent();
            Thread.Sleep(intMs);
        }
#endregion
    }
}
