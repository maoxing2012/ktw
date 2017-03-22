namespace sato.pt
{
    /// <summary>
    /// 受信情報
    /// </summary>
    public struct PtReceiveData
    {
        /// <summary>
        /// 結果
        /// PTS_END_RECEIVE：受信完了
        /// PTS_STOP：中止
        /// PTS_TIMEOUT：タイムアウト
        /// PTS_SYSTEMERR：システムエラー
        /// </summary>
        public byte Ret;
        /// <summary> 受信データ</summary>
        public byte[] ReceiveData;
        /// <summary> 受信データ長</summary>
        public int ReceiveLen;
    }

    public struct PtOutputResult
    {
        /// <summary>
        /// 結果
        /// PTS_PRINTING：印刷中
        /// PTS_ACK：正常終了(ACK)
        /// PTS_NAK：NAK
        /// PTS_STOP：中止
        /// PTS_TIMEOUT：タイムアウト
        /// PTS_SYSTEMERR：システムエラー
        /// </summary>
        public byte Ret;
        /// <summary> ステータス</summary>
        public byte Status;
        /// <summary> ステータス内容(11byte)</summary>
        public byte[] StatusData;
    }
}
