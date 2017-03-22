using System;
using System.Collections.Generic;
using System.Text;

namespace sato.pt
{
    /// <summary>
    /// sato.pt用定数クラス
    /// </summary>
    public class Def
    {
        /// <summary> オープン成功</summary>
        public const int PT_OK = 0;
        /// <summary> オープン失敗</summary>
        public const int PT_NG = -1;
        /// <summary> パラメータエラー</summary>
        public const int PT_PARAMERR = -2;
        /// <summary> 印刷受付無し</summary>
        public const int PRN_NONE = 0;
        /// <summary> 印刷中</summary>
        public const int PRN_PRINTING = 1;
        /// <summary> ラベルデータ送信中</summary>
        public const int PRN_LABEL_SEND = 2;
        /// <summary> 完了待ち</summary>
        public const int PRN_END_WAITING = 3;
        /// <summary> オフライン</summary>
        public const int PRN_OFF_LINE = -1;
        ///  <summary> ヘッドオープン</summary>
        public const int PRN_HEAD_OPEN = -2;
        /// <summary> ペーパーエンド</summary>
        public const int PRN_PAPER_END = -3;
        /// <summary> リボンエンド</summary>
        public const int PRN_RIBON_END = -4;
        /// <summary> センサエラー</summary>
        public const int PRN_SENSOR_ERROR = -5;
        /// <summary> カバーオープン</summary>
        public const int PRN_COVER_OPENING = -6;
        /// <summary> 受信完了</summary>
        public const byte PTS_END_RECEIVE = 0x00;
        /// <summary> 印刷中</summary>
        public const byte PTS_PRINTING = 0x01;
        /// <summary> 正常終了(ACK)</summary>
        public const byte PTS_ACK = Common.ACK;
        /// <summary> NAK</summary>
        public const byte PTS_NAK = Common.NAK;
        /// <summary> 未接続</summary>
        public const byte PTS_UNCONNECTION = 0xfc;
        /// <summary> 中止</summary>
        public const byte PTS_STOP = 0xfd;
        /// <summary> タイムアウト</summary>
        public const byte PTS_TIMEOUT = 0xfe;
        /// <summary> システムエラー</summary>
        public const byte PTS_SYSTEMERR = 0xff;
        /// <summary> ボーレート9600</summary>
        public const byte BAUDRATE_9600 = 1;
        /// <summary> ボーレート19200</summary>
        public const byte BAUDRATE_19200 = 2;
        /// <summary> ボーレート38400</summary>
        public const byte BAUDRATE_38400 = 3;
        /// <summary> ボーレート57600</summary>
        public const byte BAUDRATE_57600 = 4;
        /// <summary> NONEパリティ</summary>
        public const byte PARITY_NONE = 0;
        /// <summary> 奇数パリティ</summary>
        public const byte PARITY_ODD = 1;
        /// <summary> 偶数パリティ</summary>
        public const byte PARITY_EVEN = 2;
    }
}
