using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System.Text.RegularExpressions;

namespace sato.pt
{
    /// <summary>
    /// 無線LANでPT200e,PT400の制御を行う場合に使用するクラス
    /// </summary>
    public class PtLan
    {
#region 定数
        private const int IP_NUM_DIGITS = 15;   // IP桁数
        private const int PORT_NUM_MAX = 9999;  // ポート最大桁数
        private const int PORT_NUM_MIN = 0;     // ポート最大桁数

#endregion

#region グローバル変数
        private PtLanParam grvPlpParam;                 // 通信パラメータ
        private Socket grvSockClient = null;            // ソケット

        private int grvIntOutputStatus = Def.PRN_NONE;  // 印刷進行状況
        private bool grvBlnStopFlg = false;             // ストップフラグ
        private bool grvBlnTimeout = false;             // タイムアウトフラグ
        private bool grvBlnPrintingNow = false;         // 印刷中フラグ
#endregion

#region コンストラクタ
        /// <summary>
        /// コンストラクタ
        /// </summary>
        public PtLan()
        {
            grvPlpParam.IPaddress = "192.168.1.1";
            grvPlpParam.Port = 1024;
            grvPlpParam.Timeout = 500;
        }
#endregion

#region 公開メソッド
        /// <summary>
        /// ソケットをオープンする
        /// </summary>
        /// <param name="plpPrm">TCP/IPの設定</param>
        /// <returns>
        /// オープンの成否
        /// PT_OK:オープン成功
        /// PT_NG:オープン失敗
        /// PT_PARAMERR:パラメータエラー
        /// PT_SYSTEMERR:システムエラー
        /// </returns>
        public int open(PtLanParam plpPrm)
        {
            int intRet = Def.PT_OK;
            IPEndPoint grvSockIpe = null;   // ネットワークエンドポイント
            bool blnPramaChack = true;
            bool blnCloseflg = true;

            Common.outputDebugLog("PtLan", "open", "====================================開始====================================");
            try
            {
                /***********************
                 * LAN接続の設定取得
                 ***********************/
                grvPlpParam = plpPrm;

                /***********************
                 * パラメータチェック
                ***********************/
                // IPアドレスチェック
                // 内容チェック
                // XXX.XXX.XXX.XXXか確認
                blnPramaChack = IsIPAddress(grvPlpParam.IPaddress);

                // 桁数チェック
                if (grvPlpParam.IPaddress.Length > IP_NUM_DIGITS)
                {
                    blnPramaChack = false;
                }

                // ポートチェック
                // 設定範囲チェック
                if (grvPlpParam.Port > PORT_NUM_MAX || grvPlpParam.Port < PORT_NUM_MIN)
                {
                    blnPramaChack = false;
                }

                /***********************
                 * ソケットオープン
                 ***********************/
                // ソケットの状態確認
                if (grvSockIpe != null)
                {
                    // ソケットが開きっぱなしなので一旦切断
                    blnCloseflg = close();
                }

                // オープン可能？
                if (blnCloseflg)
                {
                    // チェックOKならばソケットオープン
                    if (blnPramaChack)
                    {
                        // 設定
                        // ｱﾄﾞﾚｽ変換
                        grvSockIpe = new IPEndPoint(IPAddress.Parse(grvPlpParam.IPaddress), grvPlpParam.Port);
                        // ｿｹｯﾄ作成
                        grvSockClient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

                        Common.outputDebugLog("PtLan", "open", "IPアドレス:" + grvPlpParam.IPaddress);
                        Common.outputDebugLog("PtLan", "open", "ポート:" + grvPlpParam.Port);
                        Common.outputDebugLog("PtLan", "open", "送信タイムアウト設定：" + grvPlpParam.Timeout);

                        // オープン
                        grvSockClient.Connect(grvSockIpe);
                        Common.outputDebugLog("PtLan", "open", "コネクト");

                    }
                    else
                    {
                        // パラメータエラーを返す
                        intRet = Def.PT_PARAMERR;

                        Common.outputDebugLog("PtLan", "open", "パラメータエラー");
                    }
                }
                else
                {
                    // 接続失敗を返す
                    intRet = Def.PT_NG;

                    Common.outputDebugLog("PtLan", "open", "送受信中のため接続失敗");
                }
            }
            catch (ArgumentNullException ane)
            {   // grvSockClient.Connect():remoteEP が null 参照 (Visual Basic では Nothing) です。 
                // エラーログ出力
                Common.outputLog("PtLan", "open", "remoteEP が null 参照です\t" + ane.Message);

                // エラー発生のため、ソケットクローズ
                close();

                intRet = Def.PT_NG;
            }
            catch (SocketException se)
            {   // grvSockClient.Connect():ソケットへのアクセスを試みているときにエラーが発生しました。
                // エラーログ出力
                Common.outputLog("PtLan", "open", "ソケットへのアクセスを試みているときにエラーが発生しました\t" + se.Message);

                // エラー発生のため、ソケットクローズ
                close();

                intRet = Def.PT_NG;
            }
            catch (ObjectDisposedException ode)
            {   // grvSockClient.Connect():Socket は閉じられています。

                // エラーログ出力
                Common.outputLog("PtLan", "open", "Socket は閉じられています\t" + ode.Message);

                // エラー発生のため、ソケットクローズ
                close();

                intRet = Def.PT_NG;
            }
            catch (InvalidOperationException se)
            {   // grvSockClient.Connect():Socket が Listen を実行中です。

                // エラーログ出力
                Common.outputLog("PtLan", "open", "Socket が Listen を実行中です\t" + se.Message);

                // エラー発生のため、ソケットクローズ
                close();

                intRet = Def.PT_NG;
            }
            catch (Exception e)
            {
                // エラーログ出力
                Common.outputLog("PtLan", "open", e.Message);

                // エラー発生のため、ソケットクローズ
                close();

                intRet = Def.PT_NG;
            }

            Common.outputDebugLog("PtLan", "open", "====================================終了====================================");
            return intRet;
        }

        /// <summary>
        /// LANの設定を取得する
        /// </summary>
        /// <returns>
        /// !NULL:設定内容
        /// </returns>
        public PtLanParam getParam()
        {
            Common.outputDebugLog("PtLan", "getParam", "LANの設定返答");
            /***********************
             * LANの設定返答
             ***********************/
            return grvPlpParam;
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

            Common.outputDebugLog("PtLan", "outputLabel", "====================================開始====================================");
            try
            {
                // 印刷中ではない？
                if (grvBlnPrintingNow == false)
                {
                    // 印刷開始
                    grvBlnPrintingNow = true;

                    Common.outputDebugLog("PtLan", "outputLabel", "印刷開始");

                    grvIntOutputStatus = Def.PTS_PRINTING;  // 印刷中
                    DoEvent();
                    Common.outputDebugLog("PtLan", "outputLabel", "OutputStatus：" + grvIntOutputStatus);

                    // プリンタステータスの確認(送信前)
                    prdResult = PtChkPrintTransmissionCondition();

                    // 印刷可能？
                    if (prdResult.Ret == Def.PTS_END_RECEIVE)
                    {
                        if (Common.chkPrintTransmissionCondition(prdResult.ReceiveData[Common.STR_INDEX]) == Common.STR_OK)
                        {
                            grvIntOutputStatus = Def.PRN_LABEL_SEND;  // ラベルデータ送信中
                            DoEvent();
                            Common.outputDebugLog("PtLan", "outputLabel", "OutputStatus：" + grvIntOutputStatus);

                            // 印刷データ送信
                            prdResult = sendCommand(bytData);
                            if (prdResult.Ret == Def.PTS_END_RECEIVE)
                            {
                                bytRes = prdResult.ReceiveData[0];  // ACK,NAKの退避

                                // 印字完了待ち
                                DoEventSleep(500);

                                grvIntOutputStatus = Def.PRN_END_WAITING;  // 完了待ち
                                DoEvent();
                                Common.outputDebugLog("PtLan", "outputLabel", "OutputStatus：" + grvIntOutputStatus);

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
                                    Common.outputDebugLog("PtLan", "outputLabel", "OutputStatus：" + grvIntOutputStatus);
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
                    Common.outputDebugLog("PtLan", "outputLabel", "印刷終了");
                }
                else
                {
                    // 印刷中につき、受付不可
                    porRet.Ret = Def.PTS_PRINTING;
                    Common.outputDebugLog("PtLan", "outputLabel", "印刷中につき、受付不可");
                }
            }
            catch (Exception e)
            {
                // エラーログ出力
                Common.outputLog("PtLan", "outputLabel", e.Message);
                porRet.Ret = Def.PTS_SYSTEMERR;

            }

            Common.outputDebugLog("PtLan", "outputLabel", "戻り値：" + porRet.Ret);
            Common.outputDebugLog("PtLan", "outputLabel", "戻り値(ステータス)：" + (char)porRet.Status);
#if DEBUG
            String strLog = "";
            for (int ii = 0; ii < porRet.StatusData.Length; ii++)
            {
                strLog = strLog + (char)porRet.StatusData[ii];
            }

            Common.outputDebugLog("PtLan", "outputLabel", "戻り値(データ)：" + strLog);
#endif
            Common.outputDebugLog("PtLan", "outputLabel", "====================================終了====================================");
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
            Common.outputDebugLog("PtLan", "getOutputStatus", "印刷状況返答");
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
            Common.outputDebugLog("PtLan", "stop", "送信、受信処理を中止");
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

            Common.outputDebugLog("PtLan", "getPtStatus", "コマンド:" + bytEnq[0]);
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

            Common.outputDebugLog("PtLan", "sendCommand", "====================================開始====================================");
            try
            {
                // 接続中？
                if (grvSockClient != null)
                {
                    // 送信
                    Common.outputDebugLog("PtLan", "sendCommand", "送信");
                    pstRet.Ret = sockSend(bytCommand);
                    if (pstRet.Ret == 0)
                    {
                        // 受信
                        Common.outputDebugLog("PtLan", "sendCommand", "受信");
                        pstRet = sockReceive();
                    }
                }
                else
                {
                    Common.outputDebugLog("PtLan", "sendCommand", "未接続につき、受付不可");
                    // 未接続につき、受付不可
                    pstRet.Ret = Def.PTS_UNCONNECTION;
                }
            }
            catch (Exception e)
            {					// Error
                // エラーログ出力
                Common.outputLog("PtLan", "sendCommand", e.Message);
                pstRet.Ret = Def.PTS_SYSTEMERR;
                return pstRet;
            }

            Common.outputDebugLog("PtLan", "sendCommand", "戻り値：" + pstRet.Ret);
#if DEBUG
            String strLog = "";
            for (int ii = 0; ii < pstRet.ReceiveLen; ii++)
            {
                strLog = strLog + (char)pstRet.ReceiveData[ii];
            }

            Common.outputDebugLog("PtLan", "sendCommand", "戻り値(データ)：" + strLog);
#endif
            Common.outputDebugLog("PtLan", "sendCommand", "====================================終了====================================");
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

            Common.outputDebugLog("PtLan", "sendCancel", "コマンド:" + bytCan[0]);
            // キャンセルコマンドを送信
            return sendCommand(bytCan);
        }

        /// <summary>
        /// ソケットをクローズする
        /// </summary>
        /// <returns>
        /// 結果
        /// </returns>
        public bool close()
        {
            bool bolRet = true;

            Common.outputDebugLog("PtLan", "close", "====================================開始====================================");
            // 印刷中ではない？
            if (grvBlnPrintingNow == false)
            {
                // ソケットクローズ
                if (grvSockClient != null)
                {
                    try
                    {
                        Common.outputDebugLog("PtLan", "close", "ソケットクローズ");
                        grvSockClient.Shutdown(SocketShutdown.Both);
                        grvSockClient.Close();
                        grvSockClient = null;
                    }
                    catch (SocketException se)
                    {   // ソケットへのアクセスを試みているときにエラーが発生しました。
                        // エラーログ出力
                        Common.outputLog("PtLan", "close", "ソケットへのアクセスを試みているときにエラーが発生しました\t" + se.Message);

                        bolRet = false;
                    }
                    catch (ObjectDisposedException ode)
                    {   // Socket は閉じられています。
                        // エラーログ出力
                        Common.outputLog("PtLan", "close", "Socket は閉じられています\t" + ode.Message);

                        bolRet = false;
                    }
                    catch (Exception e)
                    {
                        // エラーログ出力
                        Common.outputLog("PtLan", "close", e.Message);

                        bolRet = false;
                    }
                }
            }
            else
            {
                Common.outputDebugLog("PtLan", "close", "印刷中なので、クローズ不可");
                // 印刷中なので、クローズ不可
                bolRet = false;
            }

            Common.outputDebugLog("PtLan", "close", "====================================終了====================================");
            return bolRet;
        }

#endregion

#region 非公開メソッド
        /// <summary>
        /// IPアドレスチェック
        /// </summary>
        /// <param name="prmBytSendData">チェックするIPアドレス</param>
        /// <returns>
        /// TRUE:OK
        /// FALSE:NG
        /// </returns>
        private bool IsIPAddress(string prmStrIp)
        {
            if (Regex.IsMatch(prmStrIp, "^[0-2]?[0-9]?[0-9]+\\.[0-2]?[0-9]?[0-9]+\\.[0-2]?[0-9]?[0-9]+\\.[0-2]?[0-9]?[0-9]+$"))
            {
                string[] strIp = prmStrIp.Split(".".ToCharArray());

                if (strIp.Length.Equals(4)
                && int.Parse(strIp[0]) <= byte.MaxValue
                && int.Parse(strIp[1]) <= byte.MaxValue
                && int.Parse(strIp[2]) <= byte.MaxValue
                && int.Parse(strIp[3]) <= byte.MaxValue)
                {
                    return true;
                }
            }
            return false;
        }

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

            Common.outputDebugLog("PtLan", "sockSend", "====================================開始====================================");
            try
            {
                // 中止された？
                DoEvent();
                if (PtChkStopFlg() == true)
                {
                    Common.outputDebugLog("PtLan", "sockSend", "送信前にユーザより中止受付");
                    bytRet = Def.PTS_STOP;
                }
                else
                {
                    Common.outputDebugLog("PtLan", "sockSend", "送信開始");
                    // 受信バッファクリア
                    clrSockReceive();
                    // 送信
                    grvSockClient.Send(prmBytSendData, 0, prmBytSendData.Length,SocketFlags.None);
                    Common.outputDebugLog("PtLan", "sockSend", "送信完了");
                }
            }
            catch (ArgumentNullException ae)
            {   //grvSockClient.Send:渡された buffer が null 参照 (Visual Basic では Nothing) です。 
                // エラーログ出力
                Common.outputLog("PtLan", "sockSend", "送信バッファがNULL参照です\t" + ae.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentOutOfRangeException aoe)
            {   //grvSockClient.Send:offset パラメータまたは count パラメータが、渡される buffer の有効な領域外です。offset または count が 0 未満です。 
                // エラーログ出力
                Common.outputLog("PtLan", "sockSend", "offset、count指定ミス\t" + aoe.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (SocketException se)
            {   //grvSockClient.Send:socketFlags が、値の有効な組み合わせではありません。
                //grvSockClient.Send:Socket にアクセス中にオペレーティング システム エラーが発生しました。
                // エラーログ出力
                Common.outputLog("PtLan", "sockSend", "オペレーティング システム エラー\t" + se.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (ObjectDisposedException ode)
            {   //grvSockClient.Send:Socket は閉じられています。 
                // エラーログ出力
                Common.outputLog("PtLan", "sockSend", "Socket は閉じられています\t" + ode.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (Exception e)
            {					//Error
                // エラーログ出力
                Common.outputLog("PtLan", "sockSend", e.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }

            Common.outputDebugLog("PtLan", "sockSend", "====================================終了====================================");
            return bytRet;
        }

        /// <summary>
        /// 受信バッファ読み捨て
        /// </summary>
        /// <returns>
        /// 受信情報
        /// </returns>
        private void clrSockReceive()
        {
            int intReadLen;                     // 読み込みﾃﾞｰﾀ数
            byte[] bytRead;                     // 受信データ

            Common.outputDebugLog("PtLan", "clrSockReceive", "====================================開始====================================");
            try
            {
                // 読取可能数取得
                intReadLen = grvSockClient.Available;

                // 読取可能ﾃﾞｰﾀ有り？
                if (intReadLen > 0)
                {
                    bytRead = new byte[intReadLen];
                    // 読み捨て
                    grvSockClient.Receive(bytRead, 0, intReadLen, SocketFlags.None);
                }
            }
            catch (SocketException se)
            {	// grvSockClient.Available:ソケットへのアクセスを試みているときにエラーが発生しました。
                // grvSockClient.Receive():Socket にアクセス中にオペレーティング システム エラーが発生しました。
                // エラーログ出力
                Common.outputLog("PtLan", "clrSockReceive", "ソケットへのアクセスを試みているときにエラーが発生しました\t" + se.Message);
            }
            catch (ObjectDisposedException ode)
            {	//grvSockClient.Available:Socket は閉じられています。
                // grvSockClient.Receive():Socket は閉じられています。
                // エラーログ出力
                Common.outputLog("PtLan", "clrSockReceive", "Socket は閉じられています\t" + ode.Message);
            }
            catch (ArgumentNullException ae)
            {	// grvSockClient.Receive():buffer が null 参照 (Visual Basic では Nothing) です。
                // エラーログ出力
                Common.outputLog("PtLan", "clrSockReceive", "buffer が null 参照です\t" + ae.Message);
            }
            catch (ArgumentOutOfRangeException aoe)
            {	// grvSockClient.Receive():offset パラメータまたは count パラメータが、渡される buffer の有効な領域外です。offset または count が 0 未満です。 
                // エラーログ出力
                Common.outputLog("PtLan", "clrSockReceive", "offset、count指定ミス\t" + aoe.Message);
            }
            catch (Exception e)
            {	// Error
                // エラーログ出力
                Common.outputLog("PtLan", "clrSockReceive", e.Message);
            }

            Common.outputDebugLog("PtLan", "clrSockReceive", "====================================終了====================================");
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

            Common.outputDebugLog("PtLan", "subSockReceive", "====================================開始====================================");
            try
            {

                // ﾀｲﾑｱｳﾄ有り？
                if (grvPlpParam.Timeout > 0)
                {
                    // タイマーセット
                    grvBlnTimeout = false;  // フラグ初期化
                    tcbTimeOut = new TimerCallback(timReceiveTimer);
                    timTimeOut = new Timer(tcbTimeOut, null, grvPlpParam.Timeout, Timeout.Infinite);
                    Common.outputDebugLog("PtLan", "subSockReceive", "タイマーセット：" + grvPlpParam.Timeout);
                }

                while (true)
                {
                    DoEventSleep(100);

                    // 読取可能数取得
                    intReadLen = grvSockClient.Available;

                    Common.outputDebugLog("PtLan", "subSockReceive", "受信バイト数：" + intReadLen);

                    // 読取可能ﾃﾞｰﾀ有り？
                    if (intReadLen > 0)
                    {
                        // 受信長セット
                        rdtRet.ReceiveLen = intReadLen;

                        // 受信
                        rdtRet.ReceiveData = new byte[intReadLen];
                        grvSockClient.Receive(rdtRet.ReceiveData, 0, intReadLen, SocketFlags.None);
#if DEBUG
                        for (int ii = 0; ii < intReadLen; ii++)
                        {
                            Common.outputDebugLog("PtLan", "subSockReceive", "受信データ：" + (char)rdtRet.ReceiveData[ii]);
                        }
#endif
                        break;  // 受信処理完了
                    }
                    else
                    {
                        // 中止された？
                        DoEvent();
                        if (PtChkStopFlg() == true)
                        {
                            Common.outputDebugLog("PtLan", "subSockReceive", "ユーザより中止受付");
                            rdtRet.Ret = Def.PTS_STOP;
                            break;
                        }

                        // ﾀｲﾑｱｳﾄ判定する？
                        if (grvPlpParam.Timeout > 0)
                        {
                            // ﾀｲﾑｱｳﾄ？
                            DoEvent();
                            if (grvBlnTimeout == true)
                            {
                                Common.outputDebugLog("PtLan", "subSockReceive", "受信ﾀｲﾑｱｳﾄ発生");
                                // プリンタより応答が有りません
                                rdtRet.Ret = Def.PTS_TIMEOUT;
                                break;
                            }
                        }
                    }
                }
            }
            catch (SocketException se)
            {	// grvSockClient.Available:ソケットへのアクセスを試みているときにエラーが発生しました。
                // grvSockClient.Receive():Socket にアクセス中にオペレーティング システム エラーが発生しました。
                // エラーログ出力
                Common.outputLog("PtLan", "subSockReceive", "ソケットへのアクセスを試みているときにエラーが発生しました\t" + se.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (ObjectDisposedException ode)
            {	//grvSockClient.Available:Socket は閉じられています。
                // grvSockClient.Receive():Socket は閉じられています。
                // エラーログ出力
                Common.outputLog("PtLan", "subSockReceive", "Socket は閉じられています\t" + ode.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentNullException ae)
            {	// grvSockClient.Receive():buffer が null 参照 (Visual Basic では Nothing) です。
                // エラーログ出力
                Common.outputLog("PtLan", "subSockReceive", "buffer が null 参照です\t" + ae.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentOutOfRangeException aoe)
            {	// grvSockClient.Receive():offset パラメータまたは count パラメータが、渡される buffer の有効な領域外です。offset または count が 0 未満です。 
                // エラーログ出力
                Common.outputLog("PtLan", "subSockReceive", "offset、count指定ミス\t" + aoe.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (Exception e)
            {	// Error
                // エラーログ出力
                Common.outputLog("PtLan", "subSockReceive", e.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            finally
            {
                // メモリ解放
                if (timTimeOut != null)
                {
                    Common.outputDebugLog("PtLan", "subSockReceive", "タイマー監視終了");
                    timTimeOut.Dispose();
                }
            }

            Common.outputDebugLog("PtLan", "subSockReceive", "====================================終了====================================");
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

            Common.outputDebugLog("PtLan", "sockReceive", "====================================開始====================================");
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

                            Common.outputDebugLog("PtLan", "sockReceive", "受信データ完了");

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
                            Common.outputDebugLog("PtLan", "sockReceive", "規定外応答:" + rdtsrWork.ReceiveData[0]);
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
                Common.outputLog("PtLan", "sockReceive", e.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            finally
            {
                arlReceiveData.Clear();
            }

            Common.outputDebugLog("PtLan", "sockReceive", "====================================終了====================================");
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

            Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "====================================開始====================================");
            do
            {
                Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "プリンタ状態確認開始");
                // プリンタのステータス取得
                prdResult = getPtStatus();
                Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "プリンタ状態確認完了：" + prdResult.Ret);

                // ステータス取得成功
                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                {
                    intStRet = Common.chkPrintTransmissionCondition(prdResult.ReceiveData[Common.STR_INDEX]);
                    Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "ステータス解析：" + intStRet);
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
                    Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "OutputStatus：" + grvIntOutputStatus);
                }
                else
                {
                    // 異常検知のため終了
                    break;
                }
            } while (intStRet != Common.STR_OK && intStRet != Common.STR_NOT_POS_RES);

            Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "====================================終了====================================");
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

            Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "====================================開始====================================");
            do
            {
                Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "プリンタ状態確認開始");
                // プリンタのステータス取得
                prdResult = getPtStatus();
                Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "プリンタ状態確認完了：" + prdResult.Ret);

                // ステータス取得成功
                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                {
                    intStRet = Common.chkPrintCompletionCondition(prdResult.ReceiveData[Common.STR_INDEX]);
                    Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "ステータス解析：" + intStRet);
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
                    Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "OutputStatus：" + grvIntOutputStatus);
                }
                else
                {
                    // 異常検知のため終了
                    break;
                }
            } while (intStRet != Common.STR_OK && intStRet != Common.STR_NOT_POS_RES);

            Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "====================================終了====================================");
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
