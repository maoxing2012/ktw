namespace sato.pt
{
    /// <summary>
    /// PtRs232c�Ŏg�p����ARS232C�p�p�����[�^�\����
    /// </summary>
    public struct PtRs232cParam
    {
        /// <summary> COM�|�[�g</summary>
        public string ComPort;
        /// <summary> �{�[���[�g</summary>
        public byte Baudrate;
        /// <summary> �p���e�B</summary>
        public byte Parity;
        /// <summary> �^�C���A�E�g</summary>
        public int Timeout;
    }
}
