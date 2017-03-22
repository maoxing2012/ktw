using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Reflection;
using System.Diagnostics;

namespace sato.pt
{
    /// <summary>
    /// sato.ptにおける内部的な共通クラス
    /// </summary>
    class Common
    {
#region 定数
        /***************
         * 制御コード
         ***************/
        /// <summary> ENQ(ステータス要求コマンド)</summary>
        public const byte ENQ = 0x05;
        /// <summary> ACK</summary>
        public const byte ACK = 0x06;
        /// <summary> NAK</summary>
        public const byte NAK = 0x15;
        /// <summary> CAN(キャンセルコマンド)</summary>
        public const byte CAN = 0x18;
        /// <summary> STX</summary>
        public const byte STX = 0x02;
        /// <summary> ETX</summary>
        public const byte ETX = 0x03;
        /// <summary> ESC</summary>
        public const byte ESC = 0x1B;

        /***************
         * ステータス種別
        ***************/

        /// <summary> 送信可・印刷完了</summary>
        public const int STR_OK = 0;
        /// <summary> 待機</summary>
        public const int STR_STANDBY = -1;
        /// <summary> 復旧可能(オフライン)</summary>
        public const int STR_OFF_LINE = -21;
        ///  <summary> 復旧可能(ヘッドオープン)</summary>
        public const int STR_HEAD_OPEN = -22;
        /// <summary> 復旧可能(ペーパーエンド)</summary>
        public const int STR_PAPER_END  = -23;
        /// <summary> 復旧可能(リボンエンド)</summary>
        public const int STR_RIBON_END = -24;
        /// <summary> 復旧可能(センサエラー)</summary>
        public const int STR_SENSOR_ERROR = -25;
        /// <summary> 復旧可能(カバーオープン)</summary>
        public const int STR_COVER_OPENING = -26;
        /// <summary> 復旧不可</summary>
        public const int STR_NOT_POS_RES = -99;

        /***************
         * その他
        ***************/
        /// <summary> ステータス位置</summary>
        public const int STR_INDEX = 3;
#endregion

#region 共通関数
        /// <summary>
        /// dllが置かれている場所にログを出力する
        /// </summary>
        /// <param name="prmStrClassName">クラス名</param>
        /// <param name="prmStrMethodName">メソッド名</param>
        /// <param name="prmStrlogMsg">ログメッセージ</param>
        public static void outputLog(string prmStrClassName, string prmStrMethodName, string prmStrlogMsg)
        {
            StreamWriter sw = null;
            try
            {
                // ファイルオープン
                sw = new System.IO.StreamWriter(getAppPath() + @"\satopt.log",
                     true,
                     System.Text.Encoding.GetEncoding(932));
                // ログ出力
                //sw.WriteLine(DateTime.Now.ToString("G") + ":" + prmStrClassName + "\t" + prmStrMethodName + "\t" + prmStrlogMsg　+ "\n");
            }
            finally
            {
                if (sw != null)
                    sw.Close();
            }
        }

        /// <summary>
        /// dllが置かれている場所にログを出力する
        /// </summary>
        /// <param name="prmStrClassName">クラス名</param>
        /// <param name="prmStrMethodName">メソッド名</param>
        /// <param name="prmStrlogMsg">ログメッセージ</param>
        [Conditional("DEBUG")]
        public static void outputDebugLog(string prmStrClassName, string prmStrMethodName, string prmStrlogMsg)
        {
            outputLog(prmStrClassName, prmStrMethodName, "[デバック]\t"+prmStrlogMsg);
        }

        /// <summary>
        /// 印刷送信条件確認
        /// </summary>
        /// <param name="prmBytStatus">チェックするステータス</param>
        /// <returns>
        /// STR_OK：送信可
        /// STR_STANDBY：待機
        /// STR_OFF_LINE：復旧可能(オフライン)
        /// STR_HEAD_OPEN：復旧可能(ヘッドオープン)
        /// STR_PAPER_END：復旧可能(ペーパーエンド)
        /// STR_RIBON_END：復旧可能(リボンエンド)
        /// STR_SENSOR_ERROR：復旧可能(センサエラー)
        /// STR_COVER_OPENING：復旧可能(カバーオープン)
        /// STR_NOT_POS_RES：復旧不可
        /// </returns>
        public static int chkPrintTransmissionCondition(byte prmBytStatus)
        {
            int intRet = STR_NOT_POS_RES;

            switch (prmBytStatus)
            {
                /******************
                 * オフライン状態
                 ******************/
                case 0x30:  // エラーなし
                case 0x31:  // リボンニアエンド
                case 0x32:  // バッファニヤフル
                case 0x33:  // リボンニヤエンド＆バッファニヤフル
                    intRet = STR_OFF_LINE;   // 復旧可能なエラー/オフライン
                    break;
                /******************
                 * オンライン状態
                 ******************/
                // 受信待ち
                case 0x41:  // エラーなし
                case 0x42:  // リボンニヤエンド
                    intRet = STR_OK;   // 送信可
                    break;
                case 0x43:  // バッファニヤフル
                case 0x44:  // リボンニヤエンド＆バッファニヤフル
                case 0x21:  // バッテリニアエンド
                case 0x22:  // バッテリニアエンド＆リボンニアエンド
                case 0x23:  // バッテリニアエンド＆バッファニヤフル
                case 0x24:  // バッテリニアエンド＆リボンニヤエンド＆バッファニヤフル
                // 印字中
                case 0x47:  // エラーなし
                case 0x48:  // リボンニヤエンド
                case 0x49:  // バッファニヤフル
                case 0x4A:  // リボンニヤエンド＆バッファニヤフル
                case 0x25:  // バッテリニアエンド
                case 0x26:  // バッテリニアエンド＆リボンニアエンド
                case 0x27:  // バッテリニアエンド＆バッファニヤフル
                case 0x28:  // バッテリニアエンド＆リボンニヤエンド＆バッファニヤフル
                // ハクリ待ち
                case 0x4D:  // エラーなし
                case 0x4E:  // リボンニヤエンド
                case 0x4F:  // バッファニヤフル
                case 0x50:  // リボンニヤエンド＆バッファニヤフル
                case 0x29:  // バッテリニアエンド
                case 0x2A:  // バッテリニアエンド＆リボンニアエンド
                case 0x2B:  // バッテリニアエンド＆バッファニヤフル
                case 0x2C:  // バッテリニアエンド＆リボンニヤエンド＆バッファニヤフル
                // 解析・編集中
                case 0x53:  // エラーなし
                case 0x54:  // リボンニヤエンド
                case 0x55:  // バッファニヤフル
                case 0x56:  // リボンニヤエンド＆バッファニヤフル
                case 0x2D:  // バッテリニアエンド
                case 0x2E:  // バッテリニアエンド＆リボンニアエンド
                case 0x2F:  // バッテリニアエンド＆バッファニヤフル
                case 0x40:  // バッテリニアエンド＆リボンニヤエンド＆バッファニヤフル
                    intRet = STR_STANDBY;   // 待機
                    break;
                /*****************************
                 * エラー検出(オフライン状態)
                 *****************************/
                case 0x61:  // 受信バッファフル
                    intRet = STR_NOT_POS_RES;   // 復旧不可
                    break;
                case 0x62:  // ヘッドオープン
                    intRet = STR_HEAD_OPEN;
                    break;
                case 0x63:  // ペーパーエンド
                    intRet = STR_PAPER_END;     // 復旧可能なエラー
                    break;
                case 0x64:  // リボンエンド
                    intRet = STR_RIBON_END;     // 復旧可能なエラー
                    break;
                case 0x65:  // メディアエラー
                    intRet = STR_NOT_POS_RES;   // 復旧不可
                    break;
                case 0x66:  // センサエラー
                    intRet = STR_SENSOR_ERROR;  // 復旧可能なエラー
                    break;
                case 0x67:  // ヘッドエラー
                    intRet = STR_NOT_POS_RES;   // 復旧不可
                    break;
                case 0x68:  // カバーオープン
                    intRet = STR_COVER_OPENING; // 復旧可能なエラー
                    break;
                case 0x69:  // カードエラー
                case 0x6A:  // カッタエラー
                case 0x6B:  // その他のエラー
                case 0x6C:  // カッタセンサエラー
                case 0x6D:  // スタッカ、リワインダフル、巻き取りフル
                case 0x71:  // バッテリエラー
                    intRet = STR_NOT_POS_RES;   // 復旧不可
                    break;
            }

            return intRet;
        }

        /// <summary>
        /// 印刷完了条件確認
        /// </summary>
        /// <param name="prmBytStatus">チェックするステータス</param>
        /// <returns>
        /// STR_OK：印刷完了
        /// STR_STANDBY：待機
        /// STR_OFF_LINE：復旧可能(オフライン)
        /// STR_HEAD_OPEN：復旧可能(ヘッドオープン)
        /// STR_PAPER_END：復旧可能(ペーパーエンド)
        /// STR_RIBON_END：復旧可能(リボンエンド)
        /// STR_SENSOR_ERROR：復旧可能(センサエラー)
        /// STR_COVER_OPENING：復旧可能(カバーオープン)
        /// STR_NOT_POS_RES：復旧不可
        /// </returns>
        public static int chkPrintCompletionCondition(byte prmBytStatus)
        {
            int intRet = STR_NOT_POS_RES;

            switch (prmBytStatus)
            {
                /******************
                 * オフライン状態
                 ******************/
                case 0x30:  // エラーなし
                case 0x31:  // リボンニヤエンド
                case 0x32:  // バッファニヤフル
                case 0x33:  // リボンニヤエンド＆バッファニヤフル
                    intRet = STR_OFF_LINE;   // 復旧可能なエラー/オフライン
                    break;
                /******************
                 * オンライン状態
                 ******************/
                // 受信待ち
                case 0x41:  // エラーなし
                case 0x42:  // バッテリニヤエンド
                case 0x43:  // バッファニヤフル
                case 0x44:  // バッテリニヤエンド＆バッファニヤフル
                case 0x21:  // バッテリニアエンド
                case 0x22:  // バッテリニアエンド＆リボンニアエンド
                case 0x23:  // バッテリニアエンド＆バッファニヤフル
                case 0x24:  // バッテリニアエンド＆リボンニヤエンド＆バッファニヤフル
                    intRet = STR_OK;   // 印刷完了
                    break;
                // 印字中
                case 0x47:  // エラーなし
                case 0x48:  // バッテリニヤエンド
                case 0x49:  // バッファニヤフル
                case 0x4A:  // バッテリニヤエンド＆バッファニヤフル
                case 0x25:  // バッテリニアエンド
                case 0x26:  // バッテリニアエンド＆リボンニアエンド
                case 0x27:  // バッテリニアエンド＆バッファニヤフル
                case 0x28:  // バッテリニアエンド＆リボンニヤエンド＆バッファニヤフル
                // ハクリ待ち
                case 0x4D:  // エラーなし
                case 0x4E:  // バッテリニヤエンド
                case 0x4F:  // バッファニヤフル
                case 0x50:  // バッテリニヤエンド＆バッファニヤフル
                case 0x29:  // バッテリニアエンド
                case 0x2A:  // バッテリニアエンド＆リボンニアエンド
                case 0x2B:  // バッテリニアエンド＆バッファニヤフル
                case 0x2C:  // バッテリニアエンド＆リボンニヤエンド＆バッファニヤフル
                // 解析・編集中
                case 0x53:  // エラーなし
                case 0x54:  // バッテリニヤエンド
                case 0x55:  // バッファニヤフル
                case 0x56:  // バッテリニヤエンド＆バッファニヤフル
                case 0x2D:  // バッテリニアエンド
                case 0x2E:  // バッテリニアエンド＆リボンニアエンド
                case 0x2F:  // バッテリニアエンド＆バッファニヤフル
                case 0x40:  // バッテリニアエンド＆リボンニヤエンド＆バッファニヤフル
                    intRet = STR_STANDBY;   // 待機
                    break;
                /*****************************
                 * エラー検出(オフライン状態)
                 *****************************/
                case 0x61:  // 受信バッファフル
                    intRet = STR_NOT_POS_RES;   // 復旧不可
                    break;
                case 0x62:  // ヘッドオープン
                    intRet = STR_HEAD_OPEN;
                    break;
                case 0x63:  // ペーパーエンド
                    intRet = STR_PAPER_END;     // 復旧可能なエラー
                    break;
                case 0x64:  // リボンエンド
                    intRet = STR_RIBON_END;     // 復旧可能なエラー
                    break;
                case 0x65:  // メディアエラー
                    intRet = STR_NOT_POS_RES;   // 復旧不可
                    break;
                case 0x66:  // センサエラー
                    intRet = STR_SENSOR_ERROR;  // 復旧可能なエラー
                    break;
                case 0x67:  // ヘッドエラー
                    intRet = STR_NOT_POS_RES;   // 復旧不可
                    break;
                case 0x68:  // カバーオープン
                    intRet = STR_COVER_OPENING; // 復旧可能なエラー
                    break;
                case 0x69:  // カードエラー
                case 0x6A:  // カッタエラー
                case 0x6B:  // その他のエラー
                case 0x6C:  // カッタセンサエラー
                case 0x6D:  // スタッカ、リワインダフル、巻き取りフル
                case 0x71:  // バッテリエラー
                    intRet = STR_NOT_POS_RES;   // 復旧不可
                    break;
            }

            return intRet;
        }

        /// <summary>
        /// 自分がいるパスを返す
        /// </summary>
        /// <returns>
        /// 自分がいるパス
        /// </returns>
        public static string getAppPath()
        {
            string strAppName;     //exe名も含むアプリケーションのパス
            string strAppPath;     //exe名を取り除いたアプリケーションのパス

            //アプリケーションのフルパスを取得する
            strAppName = Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName;
            //exe名を取り除く
            strAppPath = Path.GetDirectoryName(strAppName);
            //Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName)
            //パスを戻り値として返す
            return strAppPath;
        }
#endregion
    }
}
