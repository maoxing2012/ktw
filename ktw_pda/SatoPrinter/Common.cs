using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Reflection;
using System.Diagnostics;

namespace sato.pt
{
    /// <summary>
    /// sato.pt�ɂ���������I�ȋ��ʃN���X
    /// </summary>
    class Common
    {
#region �萔
        /***************
         * ����R�[�h
         ***************/
        /// <summary> ENQ(�X�e�[�^�X�v���R�}���h)</summary>
        public const byte ENQ = 0x05;
        /// <summary> ACK</summary>
        public const byte ACK = 0x06;
        /// <summary> NAK</summary>
        public const byte NAK = 0x15;
        /// <summary> CAN(�L�����Z���R�}���h)</summary>
        public const byte CAN = 0x18;
        /// <summary> STX</summary>
        public const byte STX = 0x02;
        /// <summary> ETX</summary>
        public const byte ETX = 0x03;
        /// <summary> ESC</summary>
        public const byte ESC = 0x1B;

        /***************
         * �X�e�[�^�X���
        ***************/

        /// <summary> ���M�E�������</summary>
        public const int STR_OK = 0;
        /// <summary> �ҋ@</summary>
        public const int STR_STANDBY = -1;
        /// <summary> �����\(�I�t���C��)</summary>
        public const int STR_OFF_LINE = -21;
        ///  <summary> �����\(�w�b�h�I�[�v��)</summary>
        public const int STR_HEAD_OPEN = -22;
        /// <summary> �����\(�y�[�p�[�G���h)</summary>
        public const int STR_PAPER_END  = -23;
        /// <summary> �����\(���{���G���h)</summary>
        public const int STR_RIBON_END = -24;
        /// <summary> �����\(�Z���T�G���[)</summary>
        public const int STR_SENSOR_ERROR = -25;
        /// <summary> �����\(�J�o�[�I�[�v��)</summary>
        public const int STR_COVER_OPENING = -26;
        /// <summary> �����s��</summary>
        public const int STR_NOT_POS_RES = -99;

        /***************
         * ���̑�
        ***************/
        /// <summary> �X�e�[�^�X�ʒu</summary>
        public const int STR_INDEX = 3;
#endregion

#region ���ʊ֐�
        /// <summary>
        /// dll���u����Ă���ꏊ�Ƀ��O���o�͂���
        /// </summary>
        /// <param name="prmStrClassName">�N���X��</param>
        /// <param name="prmStrMethodName">���\�b�h��</param>
        /// <param name="prmStrlogMsg">���O���b�Z�[�W</param>
        public static void outputLog(string prmStrClassName, string prmStrMethodName, string prmStrlogMsg)
        {
            StreamWriter sw = null;
            try
            {
                // �t�@�C���I�[�v��
                sw = new System.IO.StreamWriter(getAppPath() + @"\satopt.log",
                     true,
                     System.Text.Encoding.GetEncoding(932));
                // ���O�o��
                //sw.WriteLine(DateTime.Now.ToString("G") + ":" + prmStrClassName + "\t" + prmStrMethodName + "\t" + prmStrlogMsg�@+ "\n");
            }
            finally
            {
                if (sw != null)
                    sw.Close();
            }
        }

        /// <summary>
        /// dll���u����Ă���ꏊ�Ƀ��O���o�͂���
        /// </summary>
        /// <param name="prmStrClassName">�N���X��</param>
        /// <param name="prmStrMethodName">���\�b�h��</param>
        /// <param name="prmStrlogMsg">���O���b�Z�[�W</param>
        [Conditional("DEBUG")]
        public static void outputDebugLog(string prmStrClassName, string prmStrMethodName, string prmStrlogMsg)
        {
            outputLog(prmStrClassName, prmStrMethodName, "[�f�o�b�N]\t"+prmStrlogMsg);
        }

        /// <summary>
        /// ������M�����m�F
        /// </summary>
        /// <param name="prmBytStatus">�`�F�b�N����X�e�[�^�X</param>
        /// <returns>
        /// STR_OK�F���M��
        /// STR_STANDBY�F�ҋ@
        /// STR_OFF_LINE�F�����\(�I�t���C��)
        /// STR_HEAD_OPEN�F�����\(�w�b�h�I�[�v��)
        /// STR_PAPER_END�F�����\(�y�[�p�[�G���h)
        /// STR_RIBON_END�F�����\(���{���G���h)
        /// STR_SENSOR_ERROR�F�����\(�Z���T�G���[)
        /// STR_COVER_OPENING�F�����\(�J�o�[�I�[�v��)
        /// STR_NOT_POS_RES�F�����s��
        /// </returns>
        public static int chkPrintTransmissionCondition(byte prmBytStatus)
        {
            int intRet = STR_NOT_POS_RES;

            switch (prmBytStatus)
            {
                /******************
                 * �I�t���C�����
                 ******************/
                case 0x30:  // �G���[�Ȃ�
                case 0x31:  // ���{���j�A�G���h
                case 0x32:  // �o�b�t�@�j���t��
                case 0x33:  // ���{���j���G���h���o�b�t�@�j���t��
                    intRet = STR_OFF_LINE;   // �����\�ȃG���[/�I�t���C��
                    break;
                /******************
                 * �I�����C�����
                 ******************/
                // ��M�҂�
                case 0x41:  // �G���[�Ȃ�
                case 0x42:  // ���{���j���G���h
                    intRet = STR_OK;   // ���M��
                    break;
                case 0x43:  // �o�b�t�@�j���t��
                case 0x44:  // ���{���j���G���h���o�b�t�@�j���t��
                case 0x21:  // �o�b�e���j�A�G���h
                case 0x22:  // �o�b�e���j�A�G���h�����{���j�A�G���h
                case 0x23:  // �o�b�e���j�A�G���h���o�b�t�@�j���t��
                case 0x24:  // �o�b�e���j�A�G���h�����{���j���G���h���o�b�t�@�j���t��
                // �󎚒�
                case 0x47:  // �G���[�Ȃ�
                case 0x48:  // ���{���j���G���h
                case 0x49:  // �o�b�t�@�j���t��
                case 0x4A:  // ���{���j���G���h���o�b�t�@�j���t��
                case 0x25:  // �o�b�e���j�A�G���h
                case 0x26:  // �o�b�e���j�A�G���h�����{���j�A�G���h
                case 0x27:  // �o�b�e���j�A�G���h���o�b�t�@�j���t��
                case 0x28:  // �o�b�e���j�A�G���h�����{���j���G���h���o�b�t�@�j���t��
                // �n�N���҂�
                case 0x4D:  // �G���[�Ȃ�
                case 0x4E:  // ���{���j���G���h
                case 0x4F:  // �o�b�t�@�j���t��
                case 0x50:  // ���{���j���G���h���o�b�t�@�j���t��
                case 0x29:  // �o�b�e���j�A�G���h
                case 0x2A:  // �o�b�e���j�A�G���h�����{���j�A�G���h
                case 0x2B:  // �o�b�e���j�A�G���h���o�b�t�@�j���t��
                case 0x2C:  // �o�b�e���j�A�G���h�����{���j���G���h���o�b�t�@�j���t��
                // ��́E�ҏW��
                case 0x53:  // �G���[�Ȃ�
                case 0x54:  // ���{���j���G���h
                case 0x55:  // �o�b�t�@�j���t��
                case 0x56:  // ���{���j���G���h���o�b�t�@�j���t��
                case 0x2D:  // �o�b�e���j�A�G���h
                case 0x2E:  // �o�b�e���j�A�G���h�����{���j�A�G���h
                case 0x2F:  // �o�b�e���j�A�G���h���o�b�t�@�j���t��
                case 0x40:  // �o�b�e���j�A�G���h�����{���j���G���h���o�b�t�@�j���t��
                    intRet = STR_STANDBY;   // �ҋ@
                    break;
                /*****************************
                 * �G���[���o(�I�t���C�����)
                 *****************************/
                case 0x61:  // ��M�o�b�t�@�t��
                    intRet = STR_NOT_POS_RES;   // �����s��
                    break;
                case 0x62:  // �w�b�h�I�[�v��
                    intRet = STR_HEAD_OPEN;
                    break;
                case 0x63:  // �y�[�p�[�G���h
                    intRet = STR_PAPER_END;     // �����\�ȃG���[
                    break;
                case 0x64:  // ���{���G���h
                    intRet = STR_RIBON_END;     // �����\�ȃG���[
                    break;
                case 0x65:  // ���f�B�A�G���[
                    intRet = STR_NOT_POS_RES;   // �����s��
                    break;
                case 0x66:  // �Z���T�G���[
                    intRet = STR_SENSOR_ERROR;  // �����\�ȃG���[
                    break;
                case 0x67:  // �w�b�h�G���[
                    intRet = STR_NOT_POS_RES;   // �����s��
                    break;
                case 0x68:  // �J�o�[�I�[�v��
                    intRet = STR_COVER_OPENING; // �����\�ȃG���[
                    break;
                case 0x69:  // �J�[�h�G���[
                case 0x6A:  // �J�b�^�G���[
                case 0x6B:  // ���̑��̃G���[
                case 0x6C:  // �J�b�^�Z���T�G���[
                case 0x6D:  // �X�^�b�J�A�����C���_�t���A�������t��
                case 0x71:  // �o�b�e���G���[
                    intRet = STR_NOT_POS_RES;   // �����s��
                    break;
            }

            return intRet;
        }

        /// <summary>
        /// ������������m�F
        /// </summary>
        /// <param name="prmBytStatus">�`�F�b�N����X�e�[�^�X</param>
        /// <returns>
        /// STR_OK�F�������
        /// STR_STANDBY�F�ҋ@
        /// STR_OFF_LINE�F�����\(�I�t���C��)
        /// STR_HEAD_OPEN�F�����\(�w�b�h�I�[�v��)
        /// STR_PAPER_END�F�����\(�y�[�p�[�G���h)
        /// STR_RIBON_END�F�����\(���{���G���h)
        /// STR_SENSOR_ERROR�F�����\(�Z���T�G���[)
        /// STR_COVER_OPENING�F�����\(�J�o�[�I�[�v��)
        /// STR_NOT_POS_RES�F�����s��
        /// </returns>
        public static int chkPrintCompletionCondition(byte prmBytStatus)
        {
            int intRet = STR_NOT_POS_RES;

            switch (prmBytStatus)
            {
                /******************
                 * �I�t���C�����
                 ******************/
                case 0x30:  // �G���[�Ȃ�
                case 0x31:  // ���{���j���G���h
                case 0x32:  // �o�b�t�@�j���t��
                case 0x33:  // ���{���j���G���h���o�b�t�@�j���t��
                    intRet = STR_OFF_LINE;   // �����\�ȃG���[/�I�t���C��
                    break;
                /******************
                 * �I�����C�����
                 ******************/
                // ��M�҂�
                case 0x41:  // �G���[�Ȃ�
                case 0x42:  // �o�b�e���j���G���h
                case 0x43:  // �o�b�t�@�j���t��
                case 0x44:  // �o�b�e���j���G���h���o�b�t�@�j���t��
                case 0x21:  // �o�b�e���j�A�G���h
                case 0x22:  // �o�b�e���j�A�G���h�����{���j�A�G���h
                case 0x23:  // �o�b�e���j�A�G���h���o�b�t�@�j���t��
                case 0x24:  // �o�b�e���j�A�G���h�����{���j���G���h���o�b�t�@�j���t��
                    intRet = STR_OK;   // �������
                    break;
                // �󎚒�
                case 0x47:  // �G���[�Ȃ�
                case 0x48:  // �o�b�e���j���G���h
                case 0x49:  // �o�b�t�@�j���t��
                case 0x4A:  // �o�b�e���j���G���h���o�b�t�@�j���t��
                case 0x25:  // �o�b�e���j�A�G���h
                case 0x26:  // �o�b�e���j�A�G���h�����{���j�A�G���h
                case 0x27:  // �o�b�e���j�A�G���h���o�b�t�@�j���t��
                case 0x28:  // �o�b�e���j�A�G���h�����{���j���G���h���o�b�t�@�j���t��
                // �n�N���҂�
                case 0x4D:  // �G���[�Ȃ�
                case 0x4E:  // �o�b�e���j���G���h
                case 0x4F:  // �o�b�t�@�j���t��
                case 0x50:  // �o�b�e���j���G���h���o�b�t�@�j���t��
                case 0x29:  // �o�b�e���j�A�G���h
                case 0x2A:  // �o�b�e���j�A�G���h�����{���j�A�G���h
                case 0x2B:  // �o�b�e���j�A�G���h���o�b�t�@�j���t��
                case 0x2C:  // �o�b�e���j�A�G���h�����{���j���G���h���o�b�t�@�j���t��
                // ��́E�ҏW��
                case 0x53:  // �G���[�Ȃ�
                case 0x54:  // �o�b�e���j���G���h
                case 0x55:  // �o�b�t�@�j���t��
                case 0x56:  // �o�b�e���j���G���h���o�b�t�@�j���t��
                case 0x2D:  // �o�b�e���j�A�G���h
                case 0x2E:  // �o�b�e���j�A�G���h�����{���j�A�G���h
                case 0x2F:  // �o�b�e���j�A�G���h���o�b�t�@�j���t��
                case 0x40:  // �o�b�e���j�A�G���h�����{���j���G���h���o�b�t�@�j���t��
                    intRet = STR_STANDBY;   // �ҋ@
                    break;
                /*****************************
                 * �G���[���o(�I�t���C�����)
                 *****************************/
                case 0x61:  // ��M�o�b�t�@�t��
                    intRet = STR_NOT_POS_RES;   // �����s��
                    break;
                case 0x62:  // �w�b�h�I�[�v��
                    intRet = STR_HEAD_OPEN;
                    break;
                case 0x63:  // �y�[�p�[�G���h
                    intRet = STR_PAPER_END;     // �����\�ȃG���[
                    break;
                case 0x64:  // ���{���G���h
                    intRet = STR_RIBON_END;     // �����\�ȃG���[
                    break;
                case 0x65:  // ���f�B�A�G���[
                    intRet = STR_NOT_POS_RES;   // �����s��
                    break;
                case 0x66:  // �Z���T�G���[
                    intRet = STR_SENSOR_ERROR;  // �����\�ȃG���[
                    break;
                case 0x67:  // �w�b�h�G���[
                    intRet = STR_NOT_POS_RES;   // �����s��
                    break;
                case 0x68:  // �J�o�[�I�[�v��
                    intRet = STR_COVER_OPENING; // �����\�ȃG���[
                    break;
                case 0x69:  // �J�[�h�G���[
                case 0x6A:  // �J�b�^�G���[
                case 0x6B:  // ���̑��̃G���[
                case 0x6C:  // �J�b�^�Z���T�G���[
                case 0x6D:  // �X�^�b�J�A�����C���_�t���A�������t��
                case 0x71:  // �o�b�e���G���[
                    intRet = STR_NOT_POS_RES;   // �����s��
                    break;
            }

            return intRet;
        }

        /// <summary>
        /// ����������p�X��Ԃ�
        /// </summary>
        /// <returns>
        /// ����������p�X
        /// </returns>
        public static string getAppPath()
        {
            string strAppName;     //exe�����܂ރA�v���P�[�V�����̃p�X
            string strAppPath;     //exe������菜�����A�v���P�[�V�����̃p�X

            //�A�v���P�[�V�����̃t���p�X���擾����
            strAppName = Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName;
            //exe������菜��
            strAppPath = Path.GetDirectoryName(strAppName);
            //Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName)
            //�p�X��߂�l�Ƃ��ĕԂ�
            return strAppPath;
        }
#endregion
    }
}
