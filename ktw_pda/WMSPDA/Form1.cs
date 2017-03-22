using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using SatoPrinter;

namespace WMSPDA
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        /// <summary>
        /// コンストラクタ
        /// </summary>
        PtLan PtLan = new PtLan();

        private void button1_Click(object sender, EventArgs e)
        {
            //コンストラクタ
            PtLanParam LanPrm;
            //********** パラメータ設定 **********
            LanPrm.IPaddress = "192.168.151.24";
            //ポート
            //ポート入力チェックを付けること
            LanPrm.Port = Convert.ToInt16("1024");
            LanPrm.Timeout = 30000;

            //********** プリンタ接続 **********
            //オープン
            int retOpen = PtLan.open(LanPrm);

            //********** プリンタ接続確認(状況設定) **********
            if (retOpen == Def.PT_OK)
            {
                MessageBox.Show(Convert.ToString("接続成功"));

             
            }
            else if (retOpen == Def.PT_NG)
            {
                MessageBox.Show(Convert.ToString("接続失敗")); 

            }
            else if (retOpen == Def.PT_PARAMERR)
            {
                MessageBox.Show(Convert.ToString("パラメータエラー")); 
         
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            //********** プリンタ切断 **********
            bool retClose = false;

            //クローズ
            retClose = PtLan.close();

            //********** プリンタ切断確認(状況設定) **********
            if (retClose == true)
            {
                MessageBox.Show(Convert.ToString("切断成功"));
            }
            else if (retClose == false)
            {
                MessageBox.Show(Convert.ToString("切断失敗"));
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            //********** ラベル印刷 **********
            byte[] bytData;
            PtOutputResult retLabel;

            string strPrint;
            //strPrint = "A";
            //strPrint += "%1";
            //strPrint += "V420H30P1L0102BG02100>H" + "B1234567890";
            //strPrint += "V500H130P1L0102C1B出货单号：";
            //strPrint += "V400H140P2L0402XU" + "B1234567890";
            //strPrint += "V500H170P1L0102C1B客户城市：" + "金帝达 乌鲁木齐";
            //strPrint += "V500H210P1L0102C1B承运商：" + "敦豪深圳 乌市敦豪";
            //strPrint += "V500H250P1L0102C1B出货日期：";
            //strPrint += "V400H260P2L0402XU" + "2014-01-18";
            //strPrint += "V500H290P1L0102C1B项次：";
            //strPrint += "V400H300P2L0402XU" + "1";
            //strPrint += "V500H330P2L0102C1B" + "第              箱";
            //strPrint += "V470H340P2L0402XU" + "200";
            //strPrint += "V320H330P2L0102C1B共              箱";
            //strPrint += "V290H340P2L0402XU" + "260";
            //strPrint += "Q1";
            //strPrint += "Z";

            strPrint = "A" + "\r\n";
            strPrint += "%1" + "\r\n";
            strPrint += "V420H30P1L0102BG02100>HB1234567890\r\n";
            strPrint += "V500H130P1L0102K9B出货单号：\r\n";
            strPrint += "V400H140P2L0402XUB1234567890\r\n";
            strPrint += "V500H170P1L0102C1B客户城市：金帝达 乌鲁木齐\r\n";
            strPrint += "V500H210P1L0102C1B承运商：敦豪深圳 乌市敦豪\r\n";
            strPrint += "V500H250P1L0102C1B出货日期：" + "\r\n";
            strPrint += "V400H260P2L0402XU2014-01-18\r\n";
            strPrint += "V500H290P1L0102C1B项次：" + "\r\n";
            strPrint += "V400H300P2L0402XU1\r\n";
            //strPrint += "V500H330P2L0102C1B" + "第              箱" + "\r\n";
            strPrint += "V470H340P2L0402XU200\r\n";
            //strPrint += "V320H330P2L0102C1B共              箱" + "\r\n";
            //strPrint += "V290H340P2L0402XU" + strTotalCaseAmount.ToString() + "\r\n";
            //通道
            strPrint += "V290H340P2L0402XU260\r\n";
            strPrint += "Q1" + "\r\n";
            strPrint += "Z";

            //ラベル印刷処理実行
            bytData = System.Text.Encoding.Default.GetBytes(strPrint);
            retLabel = PtLan.outputLabel(bytData);

            //タイマー監視終了
            //tmrOutputResult.Enabled = false;

            //********** ラベル印刷確認(状況設定) **********
            switch (retLabel.Ret)
            {
                case Def.PTS_PRINTING:
                    //txtStatus.Text = Convert.ToString("印刷中");
                    break;
                case Def.PTS_ACK:
                    //txtStatus.Text = Convert.ToString("ラベル印刷完了(ACK)");
                    break;
                case Def.PTS_NAK:
                    //txtStatus.Text = Convert.ToString("ラベル印刷完了(NAK)");
                    break;
                case Def.PTS_STOP:
                    //txtStatus.Text = Convert.ToString("ラベル印刷中止");
                    break;
                case Def.PTS_TIMEOUT:
                    //txtStatus.Text = Convert.ToString("タイムアウトエラー(要切断");
                    break;
                case Def.PTS_SYSTEMERR:
                    //txtStatus.Text = Convert.ToString("システムエラー");
                    break;
                case Def.PTS_UNCONNECTION:
                    //txtStatus.Text = Convert.ToString("未接続");
                    break;
            }
        }

    }
}