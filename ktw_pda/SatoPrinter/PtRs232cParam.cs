namespace sato.pt
{
    /// <summary>
    /// PtRs232cで使用する、RS232C用パラメータ構造体
    /// </summary>
    public struct PtRs232cParam
    {
        /// <summary> COMポート</summary>
        public string ComPort;
        /// <summary> ボーレート</summary>
        public byte Baudrate;
        /// <summary> パリティ</summary>
        public byte Parity;
        /// <summary> タイムアウト</summary>
        public int Timeout;
    }
}
