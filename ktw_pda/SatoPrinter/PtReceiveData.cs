namespace sato.pt
{
    /// <summary>
    /// ��M���
    /// </summary>
    public struct PtReceiveData
    {
        /// <summary>
        /// ����
        /// PTS_END_RECEIVE�F��M����
        /// PTS_STOP�F���~
        /// PTS_TIMEOUT�F�^�C���A�E�g
        /// PTS_SYSTEMERR�F�V�X�e���G���[
        /// </summary>
        public byte Ret;
        /// <summary> ��M�f�[�^</summary>
        public byte[] ReceiveData;
        /// <summary> ��M�f�[�^��</summary>
        public int ReceiveLen;
    }

    public struct PtOutputResult
    {
        /// <summary>
        /// ����
        /// PTS_PRINTING�F�����
        /// PTS_ACK�F����I��(ACK)
        /// PTS_NAK�FNAK
        /// PTS_STOP�F���~
        /// PTS_TIMEOUT�F�^�C���A�E�g
        /// PTS_SYSTEMERR�F�V�X�e���G���[
        /// </summary>
        public byte Ret;
        /// <summary> �X�e�[�^�X</summary>
        public byte Status;
        /// <summary> �X�e�[�^�X���e(11byte)</summary>
        public byte[] StatusData;
    }
}
