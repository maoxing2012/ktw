namespace sato.pt
{
    /// <summary>
    /// PtLanで使用する、無線LAN用パラメータ構造体
    /// </summary>
    public struct PtLanParam
    {
        /// <summary> IPアドレス(15桁までのピリオドを含む数字)</summary>
        public string IPaddress;
        /// <summary> ポート番号(4桁までの数値)</summary>
        public int Port;
        /// <summary> タイムアウト時間(ms)</summary>
        public int Timeout;
    }
}
