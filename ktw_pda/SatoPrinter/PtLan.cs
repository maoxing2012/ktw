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
    /// ����LAN��PT200e,PT400�̐�����s���ꍇ�Ɏg�p����N���X
    /// </summary>
    public class PtLan
    {
#region �萔
        private const int IP_NUM_DIGITS = 15;   // IP����
        private const int PORT_NUM_MAX = 9999;  // �|�[�g�ő包��
        private const int PORT_NUM_MIN = 0;     // �|�[�g�ő包��

#endregion

#region �O���[�o���ϐ�
        private PtLanParam grvPlpParam;                 // �ʐM�p�����[�^
        private Socket grvSockClient = null;            // �\�P�b�g

        private int grvIntOutputStatus = Def.PRN_NONE;  // ����i�s��
        private bool grvBlnStopFlg = false;             // �X�g�b�v�t���O
        private bool grvBlnTimeout = false;             // �^�C���A�E�g�t���O
        private bool grvBlnPrintingNow = false;         // ������t���O
#endregion

#region �R���X�g���N�^
        /// <summary>
        /// �R���X�g���N�^
        /// </summary>
        public PtLan()
        {
            grvPlpParam.IPaddress = "192.168.1.1";
            grvPlpParam.Port = 1024;
            grvPlpParam.Timeout = 500;
        }
#endregion

#region ���J���\�b�h
        /// <summary>
        /// �\�P�b�g���I�[�v������
        /// </summary>
        /// <param name="plpPrm">TCP/IP�̐ݒ�</param>
        /// <returns>
        /// �I�[�v���̐���
        /// PT_OK:�I�[�v������
        /// PT_NG:�I�[�v�����s
        /// PT_PARAMERR:�p�����[�^�G���[
        /// PT_SYSTEMERR:�V�X�e���G���[
        /// </returns>
        public int open(PtLanParam plpPrm)
        {
            int intRet = Def.PT_OK;
            IPEndPoint grvSockIpe = null;   // �l�b�g���[�N�G���h�|�C���g
            bool blnPramaChack = true;
            bool blnCloseflg = true;

            Common.outputDebugLog("PtLan", "open", "====================================�J�n====================================");
            try
            {
                /***********************
                 * LAN�ڑ��̐ݒ�擾
                 ***********************/
                grvPlpParam = plpPrm;

                /***********************
                 * �p�����[�^�`�F�b�N
                ***********************/
                // IP�A�h���X�`�F�b�N
                // ���e�`�F�b�N
                // XXX.XXX.XXX.XXX���m�F
                blnPramaChack = IsIPAddress(grvPlpParam.IPaddress);

                // �����`�F�b�N
                if (grvPlpParam.IPaddress.Length > IP_NUM_DIGITS)
                {
                    blnPramaChack = false;
                }

                // �|�[�g�`�F�b�N
                // �ݒ�͈̓`�F�b�N
                if (grvPlpParam.Port > PORT_NUM_MAX || grvPlpParam.Port < PORT_NUM_MIN)
                {
                    blnPramaChack = false;
                }

                /***********************
                 * �\�P�b�g�I�[�v��
                 ***********************/
                // �\�P�b�g�̏�Ԋm�F
                if (grvSockIpe != null)
                {
                    // �\�P�b�g���J�����ςȂ��Ȃ̂ň�U�ؒf
                    blnCloseflg = close();
                }

                // �I�[�v���\�H
                if (blnCloseflg)
                {
                    // �`�F�b�NOK�Ȃ�΃\�P�b�g�I�[�v��
                    if (blnPramaChack)
                    {
                        // �ݒ�
                        // ���ڽ�ϊ�
                        grvSockIpe = new IPEndPoint(IPAddress.Parse(grvPlpParam.IPaddress), grvPlpParam.Port);
                        // ���č쐬
                        grvSockClient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

                        Common.outputDebugLog("PtLan", "open", "IP�A�h���X:" + grvPlpParam.IPaddress);
                        Common.outputDebugLog("PtLan", "open", "�|�[�g:" + grvPlpParam.Port);
                        Common.outputDebugLog("PtLan", "open", "���M�^�C���A�E�g�ݒ�F" + grvPlpParam.Timeout);

                        // �I�[�v��
                        grvSockClient.Connect(grvSockIpe);
                        Common.outputDebugLog("PtLan", "open", "�R�l�N�g");

                    }
                    else
                    {
                        // �p�����[�^�G���[��Ԃ�
                        intRet = Def.PT_PARAMERR;

                        Common.outputDebugLog("PtLan", "open", "�p�����[�^�G���[");
                    }
                }
                else
                {
                    // �ڑ����s��Ԃ�
                    intRet = Def.PT_NG;

                    Common.outputDebugLog("PtLan", "open", "����M���̂��ߐڑ����s");
                }
            }
            catch (ArgumentNullException ane)
            {   // grvSockClient.Connect():remoteEP �� null �Q�� (Visual Basic �ł� Nothing) �ł��B 
                // �G���[���O�o��
                Common.outputLog("PtLan", "open", "remoteEP �� null �Q�Ƃł�\t" + ane.Message);

                // �G���[�����̂��߁A�\�P�b�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }
            catch (SocketException se)
            {   // grvSockClient.Connect():�\�P�b�g�ւ̃A�N�Z�X�����݂Ă���Ƃ��ɃG���[���������܂����B
                // �G���[���O�o��
                Common.outputLog("PtLan", "open", "�\�P�b�g�ւ̃A�N�Z�X�����݂Ă���Ƃ��ɃG���[���������܂���\t" + se.Message);

                // �G���[�����̂��߁A�\�P�b�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }
            catch (ObjectDisposedException ode)
            {   // grvSockClient.Connect():Socket �͕����Ă��܂��B

                // �G���[���O�o��
                Common.outputLog("PtLan", "open", "Socket �͕����Ă��܂�\t" + ode.Message);

                // �G���[�����̂��߁A�\�P�b�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }
            catch (InvalidOperationException se)
            {   // grvSockClient.Connect():Socket �� Listen �����s���ł��B

                // �G���[���O�o��
                Common.outputLog("PtLan", "open", "Socket �� Listen �����s���ł�\t" + se.Message);

                // �G���[�����̂��߁A�\�P�b�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }
            catch (Exception e)
            {
                // �G���[���O�o��
                Common.outputLog("PtLan", "open", e.Message);

                // �G���[�����̂��߁A�\�P�b�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }

            Common.outputDebugLog("PtLan", "open", "====================================�I��====================================");
            return intRet;
        }

        /// <summary>
        /// LAN�̐ݒ���擾����
        /// </summary>
        /// <returns>
        /// !NULL:�ݒ���e
        /// </returns>
        public PtLanParam getParam()
        {
            Common.outputDebugLog("PtLan", "getParam", "LAN�̐ݒ�ԓ�");
            /***********************
             * LAN�̐ݒ�ԓ�
             ***********************/
            return grvPlpParam;
        }

        /// <summary>
        /// ���x��������s��
        /// �v�����^�̃X�e�[�^�X�m�F�����������܂ł��s��
        /// �i�s�󋵂́ugetOutputStatus�v�Ŋm�F�\
        /// ������́uShift_JIS�v�ő��M���s��
        /// </summary>
        /// <param name="bytData">������e</param>
        /// <returns>
        /// !NULL:����
        /// </returns>
        public PtOutputResult outputLabel(byte[] bytData)
        {
            PtOutputResult porRet;
            PtReceiveData prdResult;
            byte bytRes;

            porRet.Ret = Def.PTS_SYSTEMERR;
            porRet.Status = 0x00;
            porRet.StatusData = new byte[11];

            Common.outputDebugLog("PtLan", "outputLabel", "====================================�J�n====================================");
            try
            {
                // ������ł͂Ȃ��H
                if (grvBlnPrintingNow == false)
                {
                    // ����J�n
                    grvBlnPrintingNow = true;

                    Common.outputDebugLog("PtLan", "outputLabel", "����J�n");

                    grvIntOutputStatus = Def.PTS_PRINTING;  // �����
                    DoEvent();
                    Common.outputDebugLog("PtLan", "outputLabel", "OutputStatus�F" + grvIntOutputStatus);

                    // �v�����^�X�e�[�^�X�̊m�F(���M�O)
                    prdResult = PtChkPrintTransmissionCondition();

                    // ����\�H
                    if (prdResult.Ret == Def.PTS_END_RECEIVE)
                    {
                        if (Common.chkPrintTransmissionCondition(prdResult.ReceiveData[Common.STR_INDEX]) == Common.STR_OK)
                        {
                            grvIntOutputStatus = Def.PRN_LABEL_SEND;  // ���x���f�[�^���M��
                            DoEvent();
                            Common.outputDebugLog("PtLan", "outputLabel", "OutputStatus�F" + grvIntOutputStatus);

                            // ����f�[�^���M
                            prdResult = sendCommand(bytData);
                            if (prdResult.Ret == Def.PTS_END_RECEIVE)
                            {
                                bytRes = prdResult.ReceiveData[0];  // ACK,NAK�̑ޔ�

                                // �󎚊����҂�
                                DoEventSleep(500);

                                grvIntOutputStatus = Def.PRN_END_WAITING;  // �����҂�
                                DoEvent();
                                Common.outputDebugLog("PtLan", "outputLabel", "OutputStatus�F" + grvIntOutputStatus);

                                // �v�����^�X�e�[�^�X�̊m�F(�����҂�)
                                prdResult = PtChkPrintCompletionConditionLabel();

                                // ��������H
                                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                                {
                                    if (Common.chkPrintCompletionCondition(prdResult.ReceiveData[Common.STR_INDEX]) == Common.STR_OK)
                                    {
                                        // �߂�l�Z�b�g
                                        porRet.Ret = bytRes;
                                        porRet.Status = prdResult.ReceiveData[Common.STR_INDEX];
                                        porRet.StatusData = prdResult.ReceiveData;
                                    }
                                    else
                                    {
                                        // �G���[���e�Z�b�g
                                        porRet.Ret = Def.PTS_NAK;
                                        porRet.Status = prdResult.ReceiveData[Common.STR_INDEX];
                                        porRet.StatusData = prdResult.ReceiveData;
                                    }
                                    grvIntOutputStatus = Def.PRN_NONE;  // �����t����
                                    Common.outputDebugLog("PtLan", "outputLabel", "OutputStatus�F" + grvIntOutputStatus);
                                }
                                else
                                {
                                    // �G���[���e�Z�b�g
                                    porRet.Ret = prdResult.Ret;
                                }
                            }
                            else
                            {
                                // �G���[���e�Z�b�g
                                porRet.Ret = prdResult.Ret;
                            }
                        }
                        else
                        {
                            // �G���[���e�Z�b�g
                            porRet.Ret = Def.PTS_NAK;
                            porRet.Status = prdResult.ReceiveData[Common.STR_INDEX];
                            porRet.StatusData = prdResult.ReceiveData;
                        }
                    }
                    else
                    {
                        // �G���[���e�Z�b�g
                        porRet.Ret = prdResult.Ret;
                    }

                    // ����I��
                    grvBlnPrintingNow = false;
                    Common.outputDebugLog("PtLan", "outputLabel", "����I��");
                }
                else
                {
                    // ������ɂ��A��t�s��
                    porRet.Ret = Def.PTS_PRINTING;
                    Common.outputDebugLog("PtLan", "outputLabel", "������ɂ��A��t�s��");
                }
            }
            catch (Exception e)
            {
                // �G���[���O�o��
                Common.outputLog("PtLan", "outputLabel", e.Message);
                porRet.Ret = Def.PTS_SYSTEMERR;

            }

            Common.outputDebugLog("PtLan", "outputLabel", "�߂�l�F" + porRet.Ret);
            Common.outputDebugLog("PtLan", "outputLabel", "�߂�l(�X�e�[�^�X)�F" + (char)porRet.Status);
#if DEBUG
            String strLog = "";
            for (int ii = 0; ii < porRet.StatusData.Length; ii++)
            {
                strLog = strLog + (char)porRet.StatusData[ii];
            }

            Common.outputDebugLog("PtLan", "outputLabel", "�߂�l(�f�[�^)�F" + strLog);
#endif
            Common.outputDebugLog("PtLan", "outputLabel", "====================================�I��====================================");
            return porRet;
        }

        /// <summary>
        /// ����󋵂��擾����
        /// </summary>
        /// <returns>
        /// ����
        /// </returns>
        public int getOutputStatus()
        {
            Common.outputDebugLog("PtLan", "getOutputStatus", "����󋵕ԓ�");
            return grvIntOutputStatus;
        }

        /// <summary>
        /// ���M�A��M�����𒆎~����
        /// </summary>
        /// <returns>
        /// ����
        /// </returns>
        public void stop()
        {
            Common.outputDebugLog("PtLan", "stop", "���M�A��M�����𒆎~");
            grvBlnStopFlg = true;
        }

        /// <summary>
        /// �v�����^�̃X�e�[�^�X���擾����
        /// </summary>
        /// <returns>
        /// ����
        /// </returns>
        public PtReceiveData getPtStatus()
        {
            byte[] bytEnq = new byte[1];

            bytEnq[0] = Common.ENQ;

            Common.outputDebugLog("PtLan", "getPtStatus", "�R�}���h:" + bytEnq[0]);
            // �X�e�[�^�X���M
            return sendCommand(bytEnq);
        }

        /// <summary>
        /// �R�}���h�𑗐M����
        /// �R�}���h�𑗐M���A���肩��̉�������M���鏊�܂ł��s���B
        /// </summary>
        /// <param name="bytCommand">���M����R�}���h���i�[�����o�C�g�z��</param>
        /// <returns>
        /// ����
        /// </returns>
        public PtReceiveData sendCommand(byte[] bytCommand)
        {
            PtReceiveData pstRet;

            // ������
            pstRet.ReceiveLen = 0;
            pstRet.ReceiveData = new byte[0];
            pstRet.Ret = Def.PTS_END_RECEIVE;

            Common.outputDebugLog("PtLan", "sendCommand", "====================================�J�n====================================");
            try
            {
                // �ڑ����H
                if (grvSockClient != null)
                {
                    // ���M
                    Common.outputDebugLog("PtLan", "sendCommand", "���M");
                    pstRet.Ret = sockSend(bytCommand);
                    if (pstRet.Ret == 0)
                    {
                        // ��M
                        Common.outputDebugLog("PtLan", "sendCommand", "��M");
                        pstRet = sockReceive();
                    }
                }
                else
                {
                    Common.outputDebugLog("PtLan", "sendCommand", "���ڑ��ɂ��A��t�s��");
                    // ���ڑ��ɂ��A��t�s��
                    pstRet.Ret = Def.PTS_UNCONNECTION;
                }
            }
            catch (Exception e)
            {					// Error
                // �G���[���O�o��
                Common.outputLog("PtLan", "sendCommand", e.Message);
                pstRet.Ret = Def.PTS_SYSTEMERR;
                return pstRet;
            }

            Common.outputDebugLog("PtLan", "sendCommand", "�߂�l�F" + pstRet.Ret);
#if DEBUG
            String strLog = "";
            for (int ii = 0; ii < pstRet.ReceiveLen; ii++)
            {
                strLog = strLog + (char)pstRet.ReceiveData[ii];
            }

            Common.outputDebugLog("PtLan", "sendCommand", "�߂�l(�f�[�^)�F" + strLog);
#endif
            Common.outputDebugLog("PtLan", "sendCommand", "====================================�I��====================================");
            return pstRet;
        }

        /// <summary>
        /// �L�����Z���R�}���h�𑗐M����
        /// </summary>
        /// <returns>
        /// ����
        /// </returns>
        public PtReceiveData sendCancel()
        {
            byte[] bytCan = new byte[1];

            bytCan[0] = Common.CAN;

            Common.outputDebugLog("PtLan", "sendCancel", "�R�}���h:" + bytCan[0]);
            // �L�����Z���R�}���h�𑗐M
            return sendCommand(bytCan);
        }

        /// <summary>
        /// �\�P�b�g���N���[�Y����
        /// </summary>
        /// <returns>
        /// ����
        /// </returns>
        public bool close()
        {
            bool bolRet = true;

            Common.outputDebugLog("PtLan", "close", "====================================�J�n====================================");
            // ������ł͂Ȃ��H
            if (grvBlnPrintingNow == false)
            {
                // �\�P�b�g�N���[�Y
                if (grvSockClient != null)
                {
                    try
                    {
                        Common.outputDebugLog("PtLan", "close", "�\�P�b�g�N���[�Y");
                        grvSockClient.Shutdown(SocketShutdown.Both);
                        grvSockClient.Close();
                        grvSockClient = null;
                    }
                    catch (SocketException se)
                    {   // �\�P�b�g�ւ̃A�N�Z�X�����݂Ă���Ƃ��ɃG���[���������܂����B
                        // �G���[���O�o��
                        Common.outputLog("PtLan", "close", "�\�P�b�g�ւ̃A�N�Z�X�����݂Ă���Ƃ��ɃG���[���������܂���\t" + se.Message);

                        bolRet = false;
                    }
                    catch (ObjectDisposedException ode)
                    {   // Socket �͕����Ă��܂��B
                        // �G���[���O�o��
                        Common.outputLog("PtLan", "close", "Socket �͕����Ă��܂�\t" + ode.Message);

                        bolRet = false;
                    }
                    catch (Exception e)
                    {
                        // �G���[���O�o��
                        Common.outputLog("PtLan", "close", e.Message);

                        bolRet = false;
                    }
                }
            }
            else
            {
                Common.outputDebugLog("PtLan", "close", "������Ȃ̂ŁA�N���[�Y�s��");
                // ������Ȃ̂ŁA�N���[�Y�s��
                bolRet = false;
            }

            Common.outputDebugLog("PtLan", "close", "====================================�I��====================================");
            return bolRet;
        }

#endregion

#region ����J���\�b�h
        /// <summary>
        /// IP�A�h���X�`�F�b�N
        /// </summary>
        /// <param name="prmBytSendData">�`�F�b�N����IP�A�h���X</param>
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
        /// ���M
        /// </summary>
        /// <param name="prmBytSendData">���M����f�[�^���i�[�����o�C�g�z��</param>
        /// <returns>
        /// 0�F����I��
        /// PTS_STOP�F���~
        /// PTS_TIMEOUT�F�^�C���A�E�g
        /// PTS_SYSTEMERR�F�V�X�e���G���[
        /// </returns>
        private byte sockSend(byte[] prmBytSendData)
        {
            byte bytRet = 0;

            Common.outputDebugLog("PtLan", "sockSend", "====================================�J�n====================================");
            try
            {
                // ���~���ꂽ�H
                DoEvent();
                if (PtChkStopFlg() == true)
                {
                    Common.outputDebugLog("PtLan", "sockSend", "���M�O�Ƀ��[�U��蒆�~��t");
                    bytRet = Def.PTS_STOP;
                }
                else
                {
                    Common.outputDebugLog("PtLan", "sockSend", "���M�J�n");
                    // ��M�o�b�t�@�N���A
                    clrSockReceive();
                    // ���M
                    grvSockClient.Send(prmBytSendData, 0, prmBytSendData.Length,SocketFlags.None);
                    Common.outputDebugLog("PtLan", "sockSend", "���M����");
                }
            }
            catch (ArgumentNullException ae)
            {   //grvSockClient.Send:�n���ꂽ buffer �� null �Q�� (Visual Basic �ł� Nothing) �ł��B 
                // �G���[���O�o��
                Common.outputLog("PtLan", "sockSend", "���M�o�b�t�@��NULL�Q�Ƃł�\t" + ae.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentOutOfRangeException aoe)
            {   //grvSockClient.Send:offset �p�����[�^�܂��� count �p�����[�^���A�n����� buffer �̗L���ȗ̈�O�ł��Boffset �܂��� count �� 0 �����ł��B 
                // �G���[���O�o��
                Common.outputLog("PtLan", "sockSend", "offset�Acount�w��~�X\t" + aoe.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (SocketException se)
            {   //grvSockClient.Send:socketFlags ���A�l�̗L���ȑg�ݍ��킹�ł͂���܂���B
                //grvSockClient.Send:Socket �ɃA�N�Z�X���ɃI�y���[�e�B���O �V�X�e�� �G���[���������܂����B
                // �G���[���O�o��
                Common.outputLog("PtLan", "sockSend", "�I�y���[�e�B���O �V�X�e�� �G���[\t" + se.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (ObjectDisposedException ode)
            {   //grvSockClient.Send:Socket �͕����Ă��܂��B 
                // �G���[���O�o��
                Common.outputLog("PtLan", "sockSend", "Socket �͕����Ă��܂�\t" + ode.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (Exception e)
            {					//Error
                // �G���[���O�o��
                Common.outputLog("PtLan", "sockSend", e.Message);
                bytRet = Def.PTS_SYSTEMERR;
            }

            Common.outputDebugLog("PtLan", "sockSend", "====================================�I��====================================");
            return bytRet;
        }

        /// <summary>
        /// ��M�o�b�t�@�ǂݎ̂�
        /// </summary>
        /// <returns>
        /// ��M���
        /// </returns>
        private void clrSockReceive()
        {
            int intReadLen;                     // �ǂݍ����ް���
            byte[] bytRead;                     // ��M�f�[�^

            Common.outputDebugLog("PtLan", "clrSockReceive", "====================================�J�n====================================");
            try
            {
                // �ǎ�\���擾
                intReadLen = grvSockClient.Available;

                // �ǎ�\�ް��L��H
                if (intReadLen > 0)
                {
                    bytRead = new byte[intReadLen];
                    // �ǂݎ̂�
                    grvSockClient.Receive(bytRead, 0, intReadLen, SocketFlags.None);
                }
            }
            catch (SocketException se)
            {	// grvSockClient.Available:�\�P�b�g�ւ̃A�N�Z�X�����݂Ă���Ƃ��ɃG���[���������܂����B
                // grvSockClient.Receive():Socket �ɃA�N�Z�X���ɃI�y���[�e�B���O �V�X�e�� �G���[���������܂����B
                // �G���[���O�o��
                Common.outputLog("PtLan", "clrSockReceive", "�\�P�b�g�ւ̃A�N�Z�X�����݂Ă���Ƃ��ɃG���[���������܂���\t" + se.Message);
            }
            catch (ObjectDisposedException ode)
            {	//grvSockClient.Available:Socket �͕����Ă��܂��B
                // grvSockClient.Receive():Socket �͕����Ă��܂��B
                // �G���[���O�o��
                Common.outputLog("PtLan", "clrSockReceive", "Socket �͕����Ă��܂�\t" + ode.Message);
            }
            catch (ArgumentNullException ae)
            {	// grvSockClient.Receive():buffer �� null �Q�� (Visual Basic �ł� Nothing) �ł��B
                // �G���[���O�o��
                Common.outputLog("PtLan", "clrSockReceive", "buffer �� null �Q�Ƃł�\t" + ae.Message);
            }
            catch (ArgumentOutOfRangeException aoe)
            {	// grvSockClient.Receive():offset �p�����[�^�܂��� count �p�����[�^���A�n����� buffer �̗L���ȗ̈�O�ł��Boffset �܂��� count �� 0 �����ł��B 
                // �G���[���O�o��
                Common.outputLog("PtLan", "clrSockReceive", "offset�Acount�w��~�X\t" + aoe.Message);
            }
            catch (Exception e)
            {	// Error
                // �G���[���O�o��
                Common.outputLog("PtLan", "clrSockReceive", e.Message);
            }

            Common.outputDebugLog("PtLan", "clrSockReceive", "====================================�I��====================================");
        }

        /// <summary>
        /// ��M
        /// </summary>
        /// <returns>
        /// ��M���
        /// </returns>
        private PtReceiveData subSockReceive()
        {
            PtReceiveData rdtRet;               // ���ݺ���
            int intReadLen;                     // �ǂݍ����ް���
            Timer timTimeOut = null;            // �^�C���A�E�g�Ď��^�C�}�[
            TimerCallback tcbTimeOut = null;    // �^�C���A�E�g���ɌĂԃ��\�b�h

            // ������
            rdtRet.ReceiveLen = 0;
            rdtRet.ReceiveData = new byte[0];
            rdtRet.Ret = Def.PTS_END_RECEIVE;

            Common.outputDebugLog("PtLan", "subSockReceive", "====================================�J�n====================================");
            try
            {

                // ��ѱ�ėL��H
                if (grvPlpParam.Timeout > 0)
                {
                    // �^�C�}�[�Z�b�g
                    grvBlnTimeout = false;  // �t���O������
                    tcbTimeOut = new TimerCallback(timReceiveTimer);
                    timTimeOut = new Timer(tcbTimeOut, null, grvPlpParam.Timeout, Timeout.Infinite);
                    Common.outputDebugLog("PtLan", "subSockReceive", "�^�C�}�[�Z�b�g�F" + grvPlpParam.Timeout);
                }

                while (true)
                {
                    DoEventSleep(100);

                    // �ǎ�\���擾
                    intReadLen = grvSockClient.Available;

                    Common.outputDebugLog("PtLan", "subSockReceive", "��M�o�C�g���F" + intReadLen);

                    // �ǎ�\�ް��L��H
                    if (intReadLen > 0)
                    {
                        // ��M���Z�b�g
                        rdtRet.ReceiveLen = intReadLen;

                        // ��M
                        rdtRet.ReceiveData = new byte[intReadLen];
                        grvSockClient.Receive(rdtRet.ReceiveData, 0, intReadLen, SocketFlags.None);
#if DEBUG
                        for (int ii = 0; ii < intReadLen; ii++)
                        {
                            Common.outputDebugLog("PtLan", "subSockReceive", "��M�f�[�^�F" + (char)rdtRet.ReceiveData[ii]);
                        }
#endif
                        break;  // ��M��������
                    }
                    else
                    {
                        // ���~���ꂽ�H
                        DoEvent();
                        if (PtChkStopFlg() == true)
                        {
                            Common.outputDebugLog("PtLan", "subSockReceive", "���[�U��蒆�~��t");
                            rdtRet.Ret = Def.PTS_STOP;
                            break;
                        }

                        // ��ѱ�Ĕ��肷��H
                        if (grvPlpParam.Timeout > 0)
                        {
                            // ��ѱ�āH
                            DoEvent();
                            if (grvBlnTimeout == true)
                            {
                                Common.outputDebugLog("PtLan", "subSockReceive", "��M��ѱ�Ĕ���");
                                // �v�����^��艞�����L��܂���
                                rdtRet.Ret = Def.PTS_TIMEOUT;
                                break;
                            }
                        }
                    }
                }
            }
            catch (SocketException se)
            {	// grvSockClient.Available:�\�P�b�g�ւ̃A�N�Z�X�����݂Ă���Ƃ��ɃG���[���������܂����B
                // grvSockClient.Receive():Socket �ɃA�N�Z�X���ɃI�y���[�e�B���O �V�X�e�� �G���[���������܂����B
                // �G���[���O�o��
                Common.outputLog("PtLan", "subSockReceive", "�\�P�b�g�ւ̃A�N�Z�X�����݂Ă���Ƃ��ɃG���[���������܂���\t" + se.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (ObjectDisposedException ode)
            {	//grvSockClient.Available:Socket �͕����Ă��܂��B
                // grvSockClient.Receive():Socket �͕����Ă��܂��B
                // �G���[���O�o��
                Common.outputLog("PtLan", "subSockReceive", "Socket �͕����Ă��܂�\t" + ode.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentNullException ae)
            {	// grvSockClient.Receive():buffer �� null �Q�� (Visual Basic �ł� Nothing) �ł��B
                // �G���[���O�o��
                Common.outputLog("PtLan", "subSockReceive", "buffer �� null �Q�Ƃł�\t" + ae.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentOutOfRangeException aoe)
            {	// grvSockClient.Receive():offset �p�����[�^�܂��� count �p�����[�^���A�n����� buffer �̗L���ȗ̈�O�ł��Boffset �܂��� count �� 0 �����ł��B 
                // �G���[���O�o��
                Common.outputLog("PtLan", "subSockReceive", "offset�Acount�w��~�X\t" + aoe.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (Exception e)
            {	// Error
                // �G���[���O�o��
                Common.outputLog("PtLan", "subSockReceive", e.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            finally
            {
                // ���������
                if (timTimeOut != null)
                {
                    Common.outputDebugLog("PtLan", "subSockReceive", "�^�C�}�[�Ď��I��");
                    timTimeOut.Dispose();
                }
            }

            Common.outputDebugLog("PtLan", "subSockReceive", "====================================�I��====================================");
            return rdtRet;
        }

        /// <summary>
        /// ��M
        /// </summary>
        /// <returns>
        /// ��M���
        /// </returns>
        private PtReceiveData sockReceive()
        {
            PtReceiveData rdtRet;               // ���ݺ���
            PtReceiveData rdtsrWork;            // ��M���
            ArrayList arlReceiveData = new ArrayList();
            byte bytEndData;                    // �Ō�̎�M�f�[�^

            // ������
            rdtRet.ReceiveLen = 0;
            rdtRet.ReceiveData = new byte[0];
            rdtRet.Ret = Def.PTS_END_RECEIVE;

            Common.outputDebugLog("PtLan", "sockReceive", "====================================�J�n====================================");
            try
            {

                while (true)
                {
                    DoEventSleep(100);
                    
                    // ��M
                    rdtsrWork = subSockReceive();

                    // ��M�����H
                    if (rdtsrWork.Ret == Def.PTS_END_RECEIVE)
                    {
                        if (rdtsrWork.ReceiveData[0] == Common.ACK || rdtsrWork.ReceiveData[0] == Common.NAK)
                        {
                            // ��M���e�Z�b�g
                            rdtRet = rdtsrWork;
                            break;  // ��M��������
                        }
                        else if (rdtsrWork.ReceiveData[0] == Common.STX)
                        {
                            bytEndData = 0;
                            // ��M�f�[�^�擾
                            for (int ii = 0; ii < rdtsrWork.ReceiveLen; ii++)
                            {
                                arlReceiveData.Add(rdtsrWork.ReceiveData[ii]);
                                if (rdtsrWork.ReceiveData[ii] == Common.ETX)
                                {
                                    bytEndData = rdtsrWork.ReceiveData[ii];
                                }
                            }

                            while (bytEndData != Common.ETX)    // ETX��M�܂ŌJ��Ԃ�
                            {
                                // ��M
                                rdtsrWork = subSockReceive();

                                // ��M�����H
                                if (rdtsrWork.Ret == Def.PTS_END_RECEIVE)
                                {
                                    bytEndData = 0;
                                    // ��M�f�[�^�擾
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
                                    break;  // �I��
                                }
                            }

                            Common.outputDebugLog("PtLan", "sockReceive", "��M�f�[�^����");

                            // ����Ƀf�[�^���󂯎�ꂽ�ꍇ�͎�M�f�[�^�Z�b�g
                            if (rdtsrWork.Ret == Def.PTS_END_RECEIVE)
                            {
                                // ��M���Z�b�g
                                rdtRet.ReceiveLen = arlReceiveData.Count;
                                // ��M�f�[�^�Z�b�g
                                rdtRet.ReceiveData = new byte[rdtRet.ReceiveLen];
                                for (int ii = 0; ii < rdtRet.ReceiveLen; ii++)
                                {
                                    rdtRet.ReceiveData[ii] = (byte)arlReceiveData[ii];
                                }
                            }
                            break;  // ��M��������
                        }
                        else
                        {
                            // �G���[���O�o��
                            Common.outputDebugLog("PtLan", "sockReceive", "�K��O����:" + rdtsrWork.ReceiveData[0]);
                            // ��M�����p��
                        }
                    }
                    else
                    {
                        // �G���[���e�Z�b�g
                        rdtRet = rdtsrWork;
                        break;  // ��M��������
                    }
                }
            }
            catch (Exception e)
            {	// Error
                // �G���[���O�o��
                Common.outputLog("PtLan", "sockReceive", e.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            finally
            {
                arlReceiveData.Clear();
            }

            Common.outputDebugLog("PtLan", "sockReceive", "====================================�I��====================================");
            return rdtRet;
        }

        /// <summary>
        /// �󎚑��M�O�̃v�����^��Ԋm�F���s��
        /// </summary>
        /// <returns>
        /// !NULL:����
        /// </returns>
        private PtReceiveData PtChkPrintTransmissionCondition()
        {
            PtReceiveData prdResult;
            int intStRet = Common.STR_NOT_POS_RES;

            Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "====================================�J�n====================================");
            do
            {
                Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "�v�����^��Ԋm�F�J�n");
                // �v�����^�̃X�e�[�^�X�擾
                prdResult = getPtStatus();
                Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "�v�����^��Ԋm�F�����F" + prdResult.Ret);

                // �X�e�[�^�X�擾����
                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                {
                    intStRet = Common.chkPrintTransmissionCondition(prdResult.ReceiveData[Common.STR_INDEX]);
                    Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "�X�e�[�^�X��́F" + intStRet);
                    // �X�e�[�^�X���f�E���M�\�H
                    switch (intStRet)
                    {
                        case Common.STR_OK:             // ����\
                            break;
                        case Common.STR_STANDBY:        // �ҋ@
                            grvIntOutputStatus = Def.PRN_PRINTING;  // �����
                            DoEventSleep(500);
                            break;
                        case Common.STR_OFF_LINE:       // �����\(�I�t���C��)
                            grvIntOutputStatus = Def.PRN_OFF_LINE;  // �I�t���C��
                            DoEvent();
                            break;
                        case Common.STR_HEAD_OPEN:       // �����\(�w�b�h�I�[�v��)
                            grvIntOutputStatus = Def.PRN_HEAD_OPEN;  // �w�b�h�I�[�v��
                            DoEvent();
                            break;
                        case Common.STR_PAPER_END:      // �����\(�y�[�p�[�G���h)
                            grvIntOutputStatus = Def.PRN_PAPER_END;  // �y�[�p�[�G���h
                            DoEvent();
                            break;
                        case Common.STR_RIBON_END:       // �����\(���{���G���h)
                            grvIntOutputStatus = Def.PRN_RIBON_END;  // ���{���G���h
                            DoEvent();
                            break;
                        case Common.STR_SENSOR_ERROR:   // �����\(�Z���T�G���[)
                            grvIntOutputStatus = Def.PRN_SENSOR_ERROR;  // �Z���T�G���[
                            DoEvent();
                            break;
                        case Common.STR_COVER_OPENING:  // �����\(�J�o�[�I�[�v��)
                            grvIntOutputStatus = Def.PRN_COVER_OPENING;  // �J�o�[�I�[�v��
                            DoEvent();
                            break;
                        case Common.STR_NOT_POS_RES:    // �����s��
                        default:
                            break;
                    }
                    Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "OutputStatus�F" + grvIntOutputStatus);
                }
                else
                {
                    // �ُ팟�m�̂��ߏI��
                    break;
                }
            } while (intStRet != Common.STR_OK && intStRet != Common.STR_NOT_POS_RES);

            Common.outputDebugLog("PtLan", "PtChkPrintTransmissionCondition", "====================================�I��====================================");
            return prdResult;
        }

        /// <summary>
        /// �󎚊����̊Ď����s��(���x���p)
        /// </summary>
        /// <returns>
        /// !NULL:����
        /// </returns>
        private PtReceiveData PtChkPrintCompletionConditionLabel()
        {
            PtReceiveData prdResult;
            int intStRet = Common.STR_NOT_POS_RES;

            Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "====================================�J�n====================================");
            do
            {
                Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "�v�����^��Ԋm�F�J�n");
                // �v�����^�̃X�e�[�^�X�擾
                prdResult = getPtStatus();
                Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "�v�����^��Ԋm�F�����F" + prdResult.Ret);

                // �X�e�[�^�X�擾����
                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                {
                    intStRet = Common.chkPrintCompletionCondition(prdResult.ReceiveData[Common.STR_INDEX]);
                    Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "�X�e�[�^�X��́F" + intStRet);
                    // �X�e�[�^�X���f�E���M�\�H
                    switch (intStRet)
                    {
                        case Common.STR_OK:             // �������
                            break;
                        case Common.STR_STANDBY:        // �ҋ@
                            grvIntOutputStatus = Def.PRN_END_WAITING;  // �����҂�
                            DoEventSleep(500);
                            break;
                        case Common.STR_OFF_LINE:       // �����\(�I�t���C��)
                            grvIntOutputStatus = Def.PRN_OFF_LINE;  // �I�t���C��
                            DoEvent();
                            break;
                        case Common.STR_HEAD_OPEN:       // �����\(�w�b�h�I�[�v��)
                            grvIntOutputStatus = Def.PRN_HEAD_OPEN;  // �w�b�h�I�[�v��
                            DoEvent();
                            break;
                        case Common.STR_PAPER_END:      // �����\(�y�[�p�[�G���h)
                            grvIntOutputStatus = Def.PRN_PAPER_END;  // �y�[�p�[�G���h
                            DoEvent();
                            break;
                        case Common.STR_RIBON_END:       // �����\(���{���G���h)
                            grvIntOutputStatus = Def.PRN_RIBON_END;  // ���{���G���h
                            DoEvent();
                            break;
                        case Common.STR_SENSOR_ERROR:   // �����\(�Z���T�G���[)
                            grvIntOutputStatus = Def.PRN_SENSOR_ERROR;  // �Z���T�G���[
                            DoEvent();
                            break;
                        case Common.STR_COVER_OPENING:  // �����\(�J�o�[�I�[�v��)
                            grvIntOutputStatus = Def.PRN_COVER_OPENING;  // �J�o�[�I�[�v��
                            DoEvent();
                            break;
                        case Common.STR_NOT_POS_RES:    // �����s��
                        default:
                            break;
                    }
                    Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "OutputStatus�F" + grvIntOutputStatus);
                }
                else
                {
                    // �ُ팟�m�̂��ߏI��
                    break;
                }
            } while (intStRet != Common.STR_OK && intStRet != Common.STR_NOT_POS_RES);

            Common.outputDebugLog("PtLan", "PtChkPrintCompletionConditionLabel", "====================================�I��====================================");
            return prdResult;
        }

        /// <summary>
        /// �X�g�b�v�t���O�̏�Ԃ��`�F�b�N����
        /// </summary>
        /// <returns>
        /// true�F�X�g�b�v
        /// false�F���s
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
        /// �^�C���A�E�g�Ď�
        /// </summary>
        private void timReceiveTimer(Object stateInfo)
        {
            grvBlnTimeout = true;      // �^�C���A�E�g��ݒ�
        }

        /// <summary>
        /// DoEvent
        /// </summary>
        private void DoEvent()
        {
            System.Windows.Forms.Application.DoEvents();
        }

        /// <summary>
        /// Windows���b�Z�[�W��S�ď��������ASleep���܂��B
        /// </summary>
        /// <param name="intMs">�ҋ@����(ms)</param>
        private void DoEventSleep(int intMs)
        {
            DoEvent();
            Thread.Sleep(intMs);
        }
#endregion
    }
}
