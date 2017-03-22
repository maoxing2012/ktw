using System;
using System.Collections.Generic;
using System.Text;

namespace sato.pt
{
    /// <summary>
    /// sato.pt�p�萔�N���X
    /// </summary>
    public class Def
    {
        /// <summary> �I�[�v������</summary>
        public const int PT_OK = 0;
        /// <summary> �I�[�v�����s</summary>
        public const int PT_NG = -1;
        /// <summary> �p�����[�^�G���[</summary>
        public const int PT_PARAMERR = -2;
        /// <summary> �����t����</summary>
        public const int PRN_NONE = 0;
        /// <summary> �����</summary>
        public const int PRN_PRINTING = 1;
        /// <summary> ���x���f�[�^���M��</summary>
        public const int PRN_LABEL_SEND = 2;
        /// <summary> �����҂�</summary>
        public const int PRN_END_WAITING = 3;
        /// <summary> �I�t���C��</summary>
        public const int PRN_OFF_LINE = -1;
        ///  <summary> �w�b�h�I�[�v��</summary>
        public const int PRN_HEAD_OPEN = -2;
        /// <summary> �y�[�p�[�G���h</summary>
        public const int PRN_PAPER_END = -3;
        /// <summary> ���{���G���h</summary>
        public const int PRN_RIBON_END = -4;
        /// <summary> �Z���T�G���[</summary>
        public const int PRN_SENSOR_ERROR = -5;
        /// <summary> �J�o�[�I�[�v��</summary>
        public const int PRN_COVER_OPENING = -6;
        /// <summary> ��M����</summary>
        public const byte PTS_END_RECEIVE = 0x00;
        /// <summary> �����</summary>
        public const byte PTS_PRINTING = 0x01;
        /// <summary> ����I��(ACK)</summary>
        public const byte PTS_ACK = Common.ACK;
        /// <summary> NAK</summary>
        public const byte PTS_NAK = Common.NAK;
        /// <summary> ���ڑ�</summary>
        public const byte PTS_UNCONNECTION = 0xfc;
        /// <summary> ���~</summary>
        public const byte PTS_STOP = 0xfd;
        /// <summary> �^�C���A�E�g</summary>
        public const byte PTS_TIMEOUT = 0xfe;
        /// <summary> �V�X�e���G���[</summary>
        public const byte PTS_SYSTEMERR = 0xff;
        /// <summary> �{�[���[�g9600</summary>
        public const byte BAUDRATE_9600 = 1;
        /// <summary> �{�[���[�g19200</summary>
        public const byte BAUDRATE_19200 = 2;
        /// <summary> �{�[���[�g38400</summary>
        public const byte BAUDRATE_38400 = 3;
        /// <summary> �{�[���[�g57600</summary>
        public const byte BAUDRATE_57600 = 4;
        /// <summary> NONE�p���e�B</summary>
        public const byte PARITY_NONE = 0;
        /// <summary> ��p���e�B</summary>
        public const byte PARITY_ODD = 1;
        /// <summary> �����p���e�B</summary>
        public const byte PARITY_EVEN = 2;
    }
}
