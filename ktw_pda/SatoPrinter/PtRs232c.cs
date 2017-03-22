#define DEBUG

using System;
using System.Collections.Generic;
using System.Text;
using System.IO.Ports;
using System.Collections;
using System.Threading;
using Framework;
using DNWA.BHTCL.CommUtil;

namespace sato.pt
{
    /// <summary>
    /// RS232C��PT200e,PT400�̐�����s���ꍇ�Ɏg�p����N���X
    /// </summary>
    public class PtRs232c
    {
        #region bluetooth
        // Line feed
        private readonly string CrLf = System.Environment.NewLine;
        // Bluetooth Device Index
        private int m_iBltDevIdx = -1;
        // Bluetooth MAC Address
        private string m_sBltMacAddr = "";

        private const int RESULT_CODE_SUCCESS = 0;
        private const int RESULT_CODE_FAILURE = 1;

#endregion

        #region �O���[�o���ϐ�
        private PtRs232cParam grvPrpParam;              // �ʐM�p�����[�^
        private SerialPort grvSrpPort = null;           // �ʐM�|�[�g
        private int grvIntOutputStatus = Def.PRN_NONE;  // ����i�s��
        private bool grvBlnStopFlg = false;             // �X�g�b�v�t���O
        private bool grvBlnTimeout = false;             // �^�C���A�E�g�t���O
        private bool grvBlnPrintingNow = false;         // ������t���O
        private System.ComponentModel.IContainer components = null; 
#endregion

#region �R���X�g���N�^
        /// <summary>
        /// �R���X�g���N�^
        /// </summary>
        public PtRs232c()
        {
            grvPrpParam.ComPort = "";
            grvPrpParam.Baudrate = Def.BAUDRATE_19200;
            grvPrpParam.Parity = Def.PARITY_NONE;
            grvPrpParam.Timeout = 500;
        }
#endregion

#region ���J���\�b�h

        /// <summary>
        /// connect with bluetooth 
        /// </summary>
        /// <param name="address"></param>
        /// <returns></returns>
        public int open(string address)
        {
            // BHTCL Class : Controls Bluetooth
            DNWA.BHTCL.Bluetooth bltCtrl = new DNWA.BHTCL.Bluetooth();
            bool bRetValue = false;

            do
            {
                // Bluetooth power on
                try
                {
                    // Sets or acquires the Bluetooth device power status.
                    // Property
                    //   Power status
                    if(bltCtrl.Power !=DNWA.BHTCL.Bluetooth.EN_POWER.ON)
                        bltCtrl.Power = DNWA.BHTCL.Bluetooth.EN_POWER.ON;
                }
                catch (Exception ex)
                {
                    Message.Warn("Set bluetooth power status failure. " + CrLf + ex.Message + CrLf);
                    break;
                }

                // Performs the initialization process.
                // Return values
                //   DLL_RET_SUCCESS	: Successful completion
                //   DLL_RET_ERROR	    : Unsuccessful completion
                if (BthUtil.DLL_RET_ERROR == BthUtil.BTH_Initialize())
                {
                    Message.Warn("Set initialize bluetooth failure." + CrLf);
                }

                // Specifies the PIN code. Pairing is not performed if an empty character string is specified. (The PIN code request is cancelled.)
                // Parameter
                //	lpszPINCode			: PIN CODE (length must be between 0 and 16 characters.) The PIN code request is cancelled if an empty character string is specified.
                // Return values
                //   DLL_RET_SUCCESS	: Successful completion
                //   DLL_RET_ERROR		: Unsuccessful completion
                if (BthUtil.DLL_RET_ERROR == BthUtil.BTH_SetPINCode("0000"))
                {
                    Message.Warn("Set bluetooth PIN status failure." + CrLf);
                }

                // Master Mode

                // MAC address input check
                //address = AppContext.Instance().GetConfig().GetValue(@"//setting/com", "value");
                if (0 == address.Length)
                {
                    Message.Warn("Please input Mac Address." + CrLf);
                    break;
                }

                //AppendAtResultText("Wait For Create Connection..." + CrLf);

                if (grvSrpPort == null)
                {
                    components = new System.ComponentModel.Container();
                    grvSrpPort = new SerialPort(components);
                    // Serial Port Timeout
                    grvSrpPort.ReadTimeout = 10000;
                    grvSrpPort.WriteTimeout = 10000; 
                }

                // Disconnect SPP
                bRetValue = DisconnectSpp();

                // Connect SPP
                bRetValue = ConnectSpp();
            } while (false);
            return 0;
        }

        // Disconnect SPP
        private bool DisconnectSpp()
        {
            bool bFuncRet = false;
            int iRetValue = 0;
            StringBuilder sbAddrDev = new StringBuilder(m_sBltMacAddr);
            string sMessage = "";

            do
            {
                // Close COM port.
                if (grvSrpPort == null)
                {
                }
                else if ( true == grvSrpPort.IsOpen)
                {
                    try
                    {
                        grvSrpPort.Close();
                    }
                    catch (Exception ex)
                    {
                        sMessage = ex.Message;
                        //ShowResult(RESULT_CODE_FAILURE, ex.Message);
                    }
                }

                // Disconnect.
                if (0 < m_sBltMacAddr.Length)
                {
                    // Disconnects the connection. (Master)
                    // Parameters
                    //   lpszAddress			: Bluetooth device address
                    // Return values
                    //   DLL_RET_SUCCESS		: Successful completion
                    //   DLL_RET_ERROR		    : Unsuccessful completion
                    //   DLL_RET_ERR_INDEX	    : Illegal index
                    iRetValue = BthUtil.BTH_DisconnectEx(sbAddrDev);

                    if (BthUtil.DLL_RET_SUCCESS != iRetValue)
                    {
                        ShowBthResult(iRetValue);
                        break;
                    }
                }

                bFuncRet = true;
            } while (false);

            m_iBltDevIdx = -1;
            m_sBltMacAddr = "";

            return bFuncRet;
        }

        // Show Bth Library Result Code 
        private void ShowBthResult(int iResult)
        {
            switch (iResult)
            {
                case BthUtil.DLL_RET_ERR_PORT:
                    AppendAtResultText("Use of specified port not possible." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_PROFILE:
                    AppendAtResultText("Illegal profile." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_NAME_BUFF_SIZE:
                    AppendAtResultText("Bluetooth device name output buffer size insufficient." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_ADDR_BUFF_SIZE:
                    AppendAtResultText("Device address output buffer size insufficient." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_BUFF_SIZE:
                    AppendAtResultText("Buffer size insufficient." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERR_INDEX:
                    AppendAtResultText("Illegal index." + CrLf);
                    break;
                case BthUtil.DLL_RET_ERROR:
                    AppendAtResultText("Unsuccessful completion." + CrLf);
                    break;
                case BthUtil.DLL_RET_SUCCESS:
                    AppendAtResultText("Successful completion." + CrLf);
                    break;
                case BthUtil.DLL_RET_ALREADY_START_SVC:
                    AppendAtResultText("Service start-up complete." + CrLf);
                    break;
                case BthUtil.DLL_RET_CONNECT:
                    AppendAtResultText("Connection established." + CrLf);
                    break;
                case BthUtil.DLL_RET_DISCONNECT:
                    AppendAtResultText("Connection severed." + CrLf);
                    break;
                case BthUtil.DLL_RET_CONNECT_SPP_DEV_A:
                    AppendAtResultText("Serial profile connection established." + CrLf);
                    break;
                case BthUtil.DLL_RET_CONNECT_DUN_DT:
                    AppendAtResultText("Dial-up profile connection established." + CrLf);
                    break;
                default:
                    AppendAtResultText("Unknown error code." + CrLf);
                    break;
            }
        }

        // Display Information 
        private void AppendAtResultText(string str)
        {
            Message.Warn(str);
        }

        // Connect SPP
        private bool ConnectSpp()
        {
            bool bFuncRet = false;
            int iRetValue = 0;
            int i = 0;
            string address = AppContext.Instance().GetConfig().GetValue(@"//setting/com", "value");
            StringBuilder sbAddrDev = new StringBuilder(address);
            StringBuilder sbPortNumber = new StringBuilder(256);

            do
            {
                // Connect
                for (i = 0; i < 60; i++)
                {
                    // Establishes a connection with the Bluetooth device address. (Master)
                    // Parameters
                    //   lpszAddress			: Bluetooth device address
                    //   nProfile			    : Used profile. 0: SPP Master (SPP-DevA)
                    //   nPort				    : Port no. (from 0 to 9) Set automatically if �g-1�h.
                    //   bEncryption			: Encryption flag
                    //                          True  : the communication data is encrypted.
                    //                          False : the communication data is not encrypted.
                    // Return values
                    //   DLL_RET_SUCCESS		: Successful completion
                    //   DLL_RET_ERROR		    : Unsuccessful completion
                    //   DLL_RET_ERR_PROFILE	: Illegal profile
                    //   DLL_RET_ERR_PORT	    : Use of specified port not possible
                    iRetValue = BthUtil.BTH_ConnectEx(sbAddrDev, 0, -1, true);

                    if (BthUtil.DLL_RET_SUCCESS == iRetValue)
                    {
                        break;
                    }
                    else
                    {
                        Thread.Sleep(500);
                    }
                }

                if (BthUtil.DLL_RET_SUCCESS == iRetValue)
                {
                    m_sBltMacAddr = sbAddrDev.ToString();
                }
                else
                {
                    ShowBthResult(iRetValue);
                    break;
                }

                // Returns the virtual COM port name used with an SPP Master connection.
                // Parameters
                //	lpszPortName		    : Virtual COM port name storage buffer
                //	nBuffSize			    : Virtual COM port name storage buffer size
                // Return values
                //   DLL_RET_SUCCESS		: Successful completion
                //   DLL_RET_ERROR		    : Unsuccessful completion
                //   DLL_RET_ERR_BUFF_SIZE	: Buffer size insufficient
                iRetValue = BthUtil.BTH_GetPortNameClient(sbPortNumber, sbPortNumber.Capacity);
                if (BthUtil.DLL_RET_SUCCESS == iRetValue)
                {
                    //AppendAtResultText("SPP connect success. [" + sbPortNumber.ToString() + "]" + CrLf);
                }
                else
                {
                    ShowBthResult(iRetValue);
                    break;
                }

                // Open COM port.
                try
                {
                    grvSrpPort.PortName = sbPortNumber.ToString();
                    grvSrpPort.Open();
                }
                catch (Exception ex)
                {
                    ShowResult(RESULT_CODE_FAILURE, ex.Message);
                    break;
                }

                bFuncRet = true;
            } while (false);

            return bFuncRet;
        }

        // Show Result Code    
        private void ShowResult(int iResult, string sAddMessage)
        {
            string sMessage = "";

            switch (iResult)
            {
                case RESULT_CODE_SUCCESS:
                    sMessage = "Send success.";
                    break;
                case RESULT_CODE_FAILURE:
                    sMessage = "Send failure.";
                    break;
                default:
                    sMessage = "Unknown error code.";
                    break;
            }

            if (0 < sAddMessage.Length)
            {
                sMessage += CrLf + sAddMessage;
            }
            AppendAtResultText(sMessage + CrLf);
        }


        /// <summary>
        /// COM�|�[�g���I�[�v������
        /// </summary>
        /// <param name="prpPrm">RS232C�ڑ��̐ݒ�</param>
        /// <returns>
        /// �I�[�v���̐���
        /// PT_OK:�I�[�v������
        /// PT_NG:�I�[�v�����s
        /// PT_PARAMERR:�p�����[�^�G���[
        /// PT_SYSTEMERR:�V�X�e���G���[
        /// </returns>
        public int open(PtRs232cParam prpPrm)
        {
            int intRet = Def.PT_OK;
            int intBaudrate = 0;
            Parity prtParity = Parity.None;
            bool blnPramaChack = true;
            bool blnCloseflg = true;

            Common.outputDebugLog("PtRs232c", "open", "====================================�J�n====================================");
            try
            {
                /***********************
                 * RS232C�ڑ��̐ݒ�擾
                 ***********************/
                grvPrpParam = prpPrm;

                // �{�[���[�g�擾
                switch (grvPrpParam.Baudrate)
                {
                    case Def.BAUDRATE_9600:
                        intBaudrate = 9600;
                        break;
                    case Def.BAUDRATE_19200:
                        intBaudrate = 19200;
                        break;
                    case Def.BAUDRATE_38400:
                        intBaudrate = 38400;
                        break;
                    case Def.BAUDRATE_57600:
                        intBaudrate = 57600;
                        break;
                    default:
                        blnPramaChack = false;
                        break;
                }

                // �p���e�B�擾
                switch (grvPrpParam.Parity)
                {
                    case Def.PARITY_NONE:
                        prtParity = Parity.None;
                        break;
                    case Def.PARITY_ODD:
                        prtParity = Parity.Odd;
                        break;
                    case Def.PARITY_EVEN:
                        prtParity = Parity.Even;
                        break;
                    default:
                        blnPramaChack = false;
                        break;
                }

                /***********************
                 * �|�[�g�I�[�v��
                 ***********************/
                // �|�[�g�̏�Ԋm�F
                if ((grvSrpPort != null) && (grvSrpPort.IsOpen))
                {
                    // �|�[�g���J�����ςȂ��Ȃ̂ň�U�ؒf
                    blnCloseflg = close();
                }
                // �I�[�v���\�H
                if (blnCloseflg)
                {
                    // �`�F�b�NOK�Ȃ�΃|�[�g�I�[�v��
                    if (blnPramaChack)
                    {
                        components = new System.ComponentModel.Container();
                        grvSrpPort = new SerialPort(components);

                        // �ݒ�
                        grvSrpPort.PortName = grvPrpParam.ComPort;  // COM�|�[�g�i���[�U�w��j
                        grvSrpPort.BaudRate = intBaudrate;          // �{�[���[�g�i���[�U�w��j
                        grvSrpPort.Parity = prtParity;              // �p���e�B�i���[�U�w��j
                        grvSrpPort.DataBits = 8;                    // �f�[�^�r�b�g(�Œ�)
                        grvSrpPort.StopBits = StopBits.One;         // �X�g�b�v�r�b�g(�Œ�)
                        grvSrpPort.WriteBufferSize = 20000;         // ���M�o�b�t�@�T�C�Y(�Œ�)

                        Common.outputDebugLog("PtRs232c", "open", "COM�|�[�g:" + grvSrpPort.PortName);
                        Common.outputDebugLog("PtRs232c", "open", "�{�[���[�g:" + grvSrpPort.BaudRate);
                        Common.outputDebugLog("PtRs232c", "open", "�p���e�B:" + grvPrpParam.Parity);
                        Common.outputDebugLog("PtRs232c", "open", "�f�[�^�r�b�g:" + grvSrpPort.DataBits);
                        Common.outputDebugLog("PtRs232c", "open", "�X�g�b�v�r�b�g(:" + grvSrpPort.StopBits);
                        Common.outputDebugLog("PtRs232c", "open", "���M�o�b�t�@�T�C�Y:" + grvSrpPort.WriteBufferSize);

                        // �^�C���A�E�g�ݒ肠��̏ꍇ�́A�^�C���A�E�g�ݒ�
                        if (grvPrpParam.Timeout > 0)
                        {
                            grvSrpPort.WriteTimeout = grvPrpParam.Timeout;
                            grvSrpPort.ReadTimeout = grvPrpParam.Timeout;
                            Common.outputDebugLog("PtRs232c", "open", "���M�^�C���A�E�g�ݒ�F" + grvSrpPort.WriteTimeout);
                            Common.outputDebugLog("PtRs232c", "open", "��M�^�C���A�E�g�ݒ�F" + grvSrpPort.ReadTimeout);
                        }

                        // �I�[�v��
                        grvSrpPort.Open();
                        Common.outputDebugLog("PtRs232c", "open", "�I�[�v��" );
                        
                    }
                    else
                    {
                        // �p�����[�^�G���[��Ԃ�
                        intRet = Def.PT_PARAMERR;

                        Common.outputDebugLog("PtRs232c", "open", "�p�����[�^�G���[");
                    }
                }
                else
                {
                    // �ڑ����s��Ԃ�
                    intRet = Def.PT_NG;

                    Common.outputDebugLog("PtRs232c", "open", "����M���̂��ߐڑ����s");
                }
            }
            catch (InvalidOperationException ioe)
            {   // grvSrpPort.Open():�w�肵���|�[�g�͊J���Ă��܂��B 
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "open", "�w�肵���|�[�g�͊J���Ă��܂�\t" + ioe.Message);

                // �G���[�����̂��߁A�|�[�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }
            catch (ArgumentOutOfRangeException aoe)
            {   // grvSrpPort.Open():���̃C���X�^���X�� 1 �ȏ�̃v���p�e�B�������ł��B
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "open", "Open�p�����[�^�ُ�\t" + aoe.Message);

                // �G���[�����̂��߁A�|�[�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }
            catch (ArgumentException ae)
            {   // grvSrpPort.Open():�|�[�g���� "COM" �Ŏn�܂��Ă��܂���B�܂��̓|�[�g�̂��̃t�@�C���̎�ނ̓T�|�[�g����Ă��܂���B

                // �G���[���O�o��
                Common.outputLog("PtRs232c", "open", "�|�[�g���s��\t" + ae.Message);

                // �G���[�����̂��߁A�|�[�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }
            catch (UnauthorizedAccessException uae)
            {   // grvSrpPort.Open():�|�[�g�ւ̃A�N�Z�X�����ۂ���Ă��܂��B 

                // �G���[���O�o��
                Common.outputLog("PtRs232c", "open", "�|�[�g�A�N�Z�X����\t" + uae.Message);

                // �G���[�����̂��߁A�|�[�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }
            catch (Exception e)
            {
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "open", e.Message);

                // �G���[�����̂��߁A�|�[�g�N���[�Y
                close();

                intRet = Def.PT_NG;
            }

            Common.outputDebugLog("PtRs232c", "open", "====================================�I��====================================");
            return intRet;
        }

        /// <summary>
        /// RS232C�̐ݒ���擾����
        /// </summary>
        /// <returns>
        /// !NULL:�ݒ���e
        /// </returns>
        public PtRs232cParam getParam()
        {
            Common.outputDebugLog("PtRs232c", "getParam", "RS232C�̐ݒ�ԓ�");
            /***********************
             * RS232C�̐ݒ�ԓ�
             ***********************/
            return grvPrpParam;
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

            grvSrpPort.Write(bytData, 0, bytData.Length);
            return porRet;

            Common.outputDebugLog("PtRs232c", "outputLabel", "====================================�J�n====================================");
            try
            {
                // ������ł͂Ȃ��H
                if (grvBlnPrintingNow == false)
                {
                    // ����J�n
                    grvBlnPrintingNow = true;

                    Common.outputDebugLog("PtRs232c", "outputLabel", "����J�n");

                    grvIntOutputStatus = Def.PRN_PRINTING;  // �����
                    DoEvent();
                    Common.outputDebugLog("PtRs232c", "outputLabel", "OutputStatus�F" + grvIntOutputStatus);

                    // �v�����^�X�e�[�^�X�̊m�F(���M�O)
                    prdResult = PtChkPrintTransmissionCondition();

                    // ����\�H
                    if (prdResult.Ret == Def.PTS_END_RECEIVE)
                    {
                        if (Common.chkPrintTransmissionCondition(prdResult.ReceiveData[Common.STR_INDEX]) == Common.STR_OK)
                        {
                            grvIntOutputStatus = Def.PRN_LABEL_SEND;  // ���x���f�[�^���M��
                            DoEvent();
                            Common.outputDebugLog("PtRs232c", "outputLabel", "OutputStatus�F" + grvIntOutputStatus);

                            // ����f�[�^���M
                            prdResult = sendCommand(bytData);
                            if (prdResult.Ret == Def.PTS_END_RECEIVE)
                            {
                                bytRes = prdResult.ReceiveData[0];  // ACK,NAK�̑ޔ�

                                // �󎚊����҂�
                                DoEventSleep(500);

                                grvIntOutputStatus = Def.PRN_END_WAITING;  // �����҂�
                                DoEvent();
                                Common.outputDebugLog("PtRs232c", "outputLabel", "OutputStatus�F" + grvIntOutputStatus);

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
                                    Common.outputDebugLog("PtRs232c", "outputLabel", "OutputStatus�F" + grvIntOutputStatus);
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
                    Common.outputDebugLog("PtRs232c", "outputLabel", "����I��");
                }
                else
                {
                    // ������ɂ��A��t�s��
                    porRet.Ret = Def.PRN_PRINTING;
                    Common.outputDebugLog("PtRs232c", "outputLabel", "������ɂ��A��t�s��");
                }
            }
            catch (Exception e)
            {
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "outputLabel", e.Message);
                porRet.Ret = Def.PTS_SYSTEMERR;

            }

            Common.outputDebugLog("PtRs232c", "outputLabel", "�߂�l�F" + porRet.Ret);
            Common.outputDebugLog("PtRs232c", "outputLabel", "�߂�l(�X�e�[�^�X)�F" + (char)porRet.Status);
#if DEBUG
            String strLog = "";
            for (int ii = 0; ii < porRet.StatusData.Length; ii++)
            {
                strLog = strLog + (char)porRet.StatusData[ii];
            }

            Common.outputDebugLog("PtRs232c", "outputLabel", "�߂�l(�f�[�^)�F" + strLog);
#endif
            Common.outputDebugLog("PtRs232c", "outputLabel", "====================================�I��====================================");
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
            Common.outputDebugLog("PtRs232c", "getOutputStatus", "����󋵕ԓ�");
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
            Common.outputDebugLog("PtRs232c", "stop", "���M�A��M�����𒆎~");
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

            Common.outputDebugLog("PtRs232c", "getPtStatus", "�R�}���h:" + bytEnq[0]);
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

            Common.outputDebugLog("PtRs232c", "sendCommand", "====================================�J�n====================================");
            try
            {
                // �ڑ����H
                if ((grvSrpPort != null) && (grvSrpPort.IsOpen == true))
                {
                    // ���M
                    Common.outputDebugLog("PtRs232c", "sendCommand", "���M");
                    pstRet.Ret = sockSend(bytCommand);
                    if (pstRet.Ret == 0)
                    {
                        // ��M
                        Common.outputDebugLog("PtRs232c", "sendCommand", "��M");
                        pstRet = sockReceive();
                    }
                }
                else
                {
                    Common.outputDebugLog("PtRs232c", "sendCommand", "���ڑ��ɂ��A��t�s��");
                    // ���ڑ��ɂ��A��t�s��
                    pstRet.Ret = Def.PTS_UNCONNECTION;
                }
            }
            catch (Exception e)
            {					// Error
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "sendCommand", e.Message);
                pstRet.Ret = Def.PTS_SYSTEMERR;
                return pstRet;
            }

            Common.outputDebugLog("PtRs232c", "sendCommand", "�߂�l�F" + pstRet.Ret);
#if DEBUG
            String strLog = "";
            for (int ii = 0; ii < pstRet.ReceiveLen; ii++)
            {
                strLog = strLog + (char)pstRet.ReceiveData[ii];
            }

            Common.outputDebugLog("PtRs232c", "sendCommand", "�߂�l(�f�[�^)�F" + strLog);
#endif
            Common.outputDebugLog("PtRs232c", "sendCommand", "====================================�I��====================================");
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

            Common.outputDebugLog("PtRs232c", "sendCancel", "�R�}���h:" + bytCan[0]);
            // �L�����Z���R�}���h�𑗐M
            return sendCommand(bytCan);
        }

        /// <summary>
        /// COM�|�[�g���N���[�Y����
        /// </summary>
        /// <returns>
        /// ����
        /// </returns>
        public bool close()
        {
            bool bolRet = true;

            Common.outputDebugLog("PtRs232c", "close", "====================================�J�n====================================");
            try
            {
                // ������ł͂Ȃ��H
                if (grvBlnPrintingNow == false)
                {
                    // �|�[�g�N���[�Y
                    if (grvSrpPort != null)
                    {
                        Common.outputDebugLog("PtRs232c", "close", "�|�[�g�N���[�Y");
                        grvSrpPort.Close();
                    }

                    // SerialPort���
                    if (grvSrpPort != null)
                    {
                        grvSrpPort.Dispose();
                        Common.outputDebugLog("PtRs232c", "close", "SerialPort���");
                    }

                    // System.ComponentModel.IContainer���
                    if (components != null)
                    {
                        components.Dispose();
                        Common.outputDebugLog("PtRs232c", "close", "System.ComponentModel.IContainer���");
                    }

                    if (m_sBltMacAddr.Length > 0)
                        DisconnectSpp();
                }
                else
                {
                    Common.outputDebugLog("PtRs232c", "close", "������Ȃ̂ŁA�N���[�Y�s��");
                    // ������Ȃ̂ŁA�N���[�Y�s��
                    bolRet = false;
                }
            }
            catch (InvalidOperationException ioe)
            {
                // grvSrpPort.Close():�w�肵���|�[�g���J���Ă��܂���B
                Common.outputLog("PtRs232c", "close", "�|�[�g�����Ă��܂�\t" + ioe.Message);
                // ���ɕ��Ă��邾���Ȃ̂ŁA����Ƃ��Ĉ���
            }
            catch (Exception e)
            {
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "close", e.Message);

                bolRet = false;
            }

            Common.outputDebugLog("PtRs232c", "close", "====================================�I��====================================");
            return bolRet;
        }

#endregion

#region ����J���\�b�h
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

            Timer timTimeOut = null;            // �^�C���A�E�g�Ď��^�C�}�[
            TimerCallback tcbTimeOut = null;    // �^�C���A�E�g���ɌĂԃ��\�b�h

            Common.outputDebugLog("PtRs232c", "sockSend", "====================================�J�n====================================");
            try
            {
                // ���~���ꂽ�H
                DoEvent();
                if (PtChkStopFlg() == true)
                {
                    Common.outputDebugLog("PtRs232c", "sockSend", "���M�O�Ƀ��[�U��蒆�~��t");
                    bytRet = Def.PTS_STOP;
                }
                else
                {
                    // ���M�\�ȏꍇ�A���M���s��
                    if (bytRet == 0)
                    {
                        // ���M�O�Ƀo�b�t�@�N���A
                        grvSrpPort.DiscardInBuffer();
                        grvSrpPort.DiscardOutBuffer();
                        Common.outputDebugLog("PtRs232c", "sockSend", "���M�J�n");
                        //���M
                        grvSrpPort.Write(prmBytSendData, 0, prmBytSendData.Length);
                        Common.outputDebugLog("PtRs232c", "sockSend", "���M����");
                    }
                }
            }
            catch (ArgumentNullException ae)
            {   //grvSrpPort.Write:�n���ꂽ buffer �� null �Q�� (Visual Basic �ł� Nothing) �ł��B 
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "sockSend", "���M�o�b�t�@��NULL�Q�Ƃł�\t" + ae.Message);
                grvSrpPort.DiscardOutBuffer();   // �o�b�t�@�N���A
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (InvalidOperationException ioe)
            {   //grvSrpPort.Write:�w�肵���|�[�g���J���Ă��܂���B  
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "sockSend", "�|�[�g�����Ă��܂�\t" + ioe.Message);
                // �|�[�g�����Ă���̂ŁA�o�b�t�@�N���A�͍s��Ȃ��B
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentOutOfRangeException aoe)
            {   //grvSrpPort.Write:offset �p�����[�^�܂��� count �p�����[�^���A�n����� buffer �̗L���ȗ̈�O�ł��Boffset �܂��� count �� 0 �����ł��B 
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "sockSend", "offset�Acount�w��~�X\t" + aoe.Message);
                grvSrpPort.DiscardOutBuffer();   // �o�b�t�@�N���A
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (ArgumentException ae)
            {   //grvSrpPort.Write:offset �� count �����Z�����l���Abuffer �̒����𒴂��Ă��܂��B
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "sockSend", "�o�b�t�@�I�[�o�[\t" + ae.Message);
                grvSrpPort.DiscardOutBuffer();   // �o�b�t�@�N���A
                bytRet = Def.PTS_SYSTEMERR;
            }
            catch (TimeoutException te)
            {   //grvSrpPort.Write:�^�C���A�E�g���ԓ��ɑ��삪�������܂���ł����B 
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "sockSend", "���M�^�C���A�E�g����\t" + te.Message);
                grvSrpPort.DiscardOutBuffer();   // �o�b�t�@�N���A
                bytRet = Def.PTS_TIMEOUT;
            }
            catch (Exception e)
            {					//Error
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "sockSend", e.Message);
                grvSrpPort.DiscardOutBuffer();   // �o�b�t�@�N���A
                bytRet = Def.PTS_SYSTEMERR;
            }
            finally
            {
                if (timTimeOut != null)
                {
                    Common.outputDebugLog("PtRs232c", "sockSend", "�^�C�}�[�Ď��I��");
                    timTimeOut.Dispose();
                }
            }

            Common.outputDebugLog("PtRs232c", "sockSend", "====================================�I��====================================");
            return bytRet;
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

            Common.outputDebugLog("PtRs232c", "subSockReceive", "====================================�J�n====================================");
            try
            {

                // ��ѱ�ėL��H
                if (grvPrpParam.Timeout > 0)
                {
                    // �^�C�}�[�Z�b�g
                    grvBlnTimeout = false;  // �t���O������
                    tcbTimeOut = new TimerCallback(timReceiveTimer);
                    timTimeOut = new Timer(tcbTimeOut, null, grvPrpParam.Timeout, Timeout.Infinite);
                    Common.outputDebugLog("PtRs232c", "subSockReceive", "�^�C�}�[�Z�b�g�F" + grvPrpParam.Timeout);
                }

                while (true)
                {
                    DoEventSleep(100);

                    // �ǎ�\���擾
                    intReadLen = grvSrpPort.BytesToRead;

                    Common.outputDebugLog("PtRs232c", "subSockReceive", "��M�o�C�g���F" + intReadLen);

                    // �ǎ�\�ް��L��H
                    if (intReadLen > 0)
                    {
                        // ��M���Z�b�g
                        rdtRet.ReceiveLen = intReadLen;

                        // ��M
                        rdtRet.ReceiveData = new byte[intReadLen];
                        for (int ii = 0; ii < intReadLen; ii++)
                        {
                            rdtRet.ReceiveData[ii] = (byte)grvSrpPort.ReadByte();
                            Common.outputDebugLog("PtRs232c", "subSockReceive", "��M�f�[�^�F" + (char)rdtRet.ReceiveData[ii]);
                        }
                        break;  // ��M��������
                    }
                    else
                    {
                        // ���~���ꂽ�H
                        DoEvent();
                        if (PtChkStopFlg() == true)
                        {
                            Common.outputDebugLog("PtRs232c", "subSockReceive", "���[�U��蒆�~��t");
                            rdtRet.Ret = Def.PTS_STOP;
                            break;
                        }

                        // ��ѱ�Ĕ��肷��H
                        if (grvPrpParam.Timeout > 0)
                        {
                            // ��ѱ�āH
                            DoEvent();
                            if (grvBlnTimeout == true)
                            {
                                Common.outputDebugLog("PtRs232c", "subSockReceive", "��M��ѱ�Ĕ���");
                                // �v�����^��艞�����L��܂���
                                rdtRet.Ret = Def.PTS_TIMEOUT;
                                break;
                            }
                        }
                    }
                }
            }
            catch (InvalidOperationException ioe)
            {	// grvSrpPort.ReadByte():�w�肵���|�[�g���J���Ă��܂���B
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "subSockReceive", "�|�[�g�����Ă��܂�\t" + ioe.Message);
                // �|�[�g�����Ă���̂ŁA�o�b�t�@�N���A�͍s��Ȃ��B
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            catch (TimeoutException te)
            {	// grvSrpPort.ReadByte():�^�C���A�E�g���ԓ��ɑ��삪�������܂���ł����B�܂��̓o�C�g���ǂݎ���܂���ł����B

                // �G���[���O�o��
                Common.outputLog("PtRs232c", "subSockReceive", "��M�^�C���A�E�g����\t" + te.Message);
                grvSrpPort.DiscardInBuffer();   // �o�b�t�@�N���A
                rdtRet.Ret = Def.PTS_TIMEOUT;
            }
            catch (Exception e)
            {	// Error
                // �G���[���O�o��
                Common.outputLog("PtRs232c", "subSockReceive", e.Message);
                grvSrpPort.DiscardInBuffer();   // �o�b�t�@�N���A
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            finally
            {
                // ���������
                if (timTimeOut != null)
                {
                    Common.outputDebugLog("PtRs232c", "subSockReceive", "�^�C�}�[�Ď��I��");
                    timTimeOut.Dispose();
                }
            }

            Common.outputDebugLog("PtRs232c", "subSockReceive", "====================================�I��====================================");
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

            Common.outputDebugLog("PtRs232c", "sockReceive", "====================================�J�n====================================");
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

                            Common.outputDebugLog("PtRs232c", "sockReceive", "��M�f�[�^����");

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
                            Common.outputDebugLog("PtRs232c", "sockReceive", "�K��O����:" + rdtsrWork.ReceiveData[0]);
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
                Common.outputLog("PtRs232c", "sockReceive", e.Message);
                rdtRet.Ret = Def.PTS_SYSTEMERR;
            }
            finally
            {
                arlReceiveData.Clear();
            }

            Common.outputDebugLog("PtRs232c", "sockReceive", "====================================�I��====================================");
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

            Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "====================================�J�n====================================");
            do
            {
                Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "�v�����^��Ԋm�F�J�n");
                // �v�����^�̃X�e�[�^�X�擾
                prdResult = getPtStatus();
                Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "�v�����^��Ԋm�F�����F" + prdResult.Ret);

                // �X�e�[�^�X�擾����
                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                {
                    intStRet = Common.chkPrintTransmissionCondition(prdResult.ReceiveData[Common.STR_INDEX]);
                    Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "�X�e�[�^�X��́F" + intStRet);
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
                    Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "OutputStatus�F" + grvIntOutputStatus);
                }
                else
                {
                    // �ُ팟�m�̂��ߏI��
                    break;
                }
            } while (intStRet != Common.STR_OK && intStRet != Common.STR_NOT_POS_RES);

            Common.outputDebugLog("PtRs232c", "PtChkPrintTransmissionCondition", "====================================�I��====================================");
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

            Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "====================================�J�n====================================");
            do
            {
                Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "�v�����^��Ԋm�F�J�n");
                // �v�����^�̃X�e�[�^�X�擾
                prdResult = getPtStatus();
                Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "�v�����^��Ԋm�F�����F" + prdResult.Ret);

                // �X�e�[�^�X�擾����
                if (prdResult.Ret == Def.PTS_END_RECEIVE)
                {
                    intStRet = Common.chkPrintCompletionCondition(prdResult.ReceiveData[Common.STR_INDEX]);
                    Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "�X�e�[�^�X��́F" + intStRet);
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
                    Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "OutputStatus�F" + grvIntOutputStatus);
                }
                else
                {
                    // �ُ팟�m�̂��ߏI��
                    break;
                }
            } while (intStRet != Common.STR_OK && intStRet != Common.STR_NOT_POS_RES && intStRet != Common.STR_STANDBY);

            Common.outputDebugLog("PtRs232c", "PtChkPrintCompletionConditionLabel", "====================================�I��====================================");
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
