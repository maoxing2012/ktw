using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using sato.pt;
using Framework;

namespace BusinessUtil
{
    public class PrintUtil
    {

        /// <summary>
        /// 打印小票
        /// </summary>
        /// <param name="list"></param>
        public static void PrintBill(SkuLabel label, string times)
        {
            int retOpen = 0;
            PtRs232c PtLan = new PtRs232c();
            try
            {
                if (times.ToLong() <= 0) return;
                StringBuilder strPrint = new StringBuilder();
                string address = AppContext.Instance().GetConfig().GetValue(@"//setting/com", "value");
                retOpen = PtLan.open(address);
                if (retOpen == Def.PT_OK)
                {
                    //成功打开
                    PtOutputResult retLabel;
                    retLabel.Ret = Def.PTS_SYSTEMERR;
                    string strMsg = "";
                    List<SkuLabel> list = new List<SkuLabel>();
                    list.Add(label);
                    for (int i = 0; i < 1; i++)
                    {
                        strPrint.Length = 0;
                        strPrint.Append("");
                        strPrint.Append("A");
                        strPrint.Append("A3V+000H+000");
                        //<A1>V00559H0440
                        //printData.Append("A1V00559H0440");
                        //<ESC>%3
                        strPrint.Append("%0");
                        if (!label.janCode.IsEmpty())
                        {
                            strPrint.Append("%0H0100V00010BG01041>H" + label.janCode);
                        }
                        strPrint.Append("%0H0144V00052L0101P01K8H");//
                        strPrint.Append(GetHexValue(label.janCode));
                        strPrint.Append("%1");
                        //限定品
                        if (!label.invStatus.IsEmpty())
                        {
                            strPrint.Append("H0021V00387P02RG0,5,3,020,020," + label.invStatus);
                        }
                        strPrint.Append("H0058V00347L0101P02K8H");
                        strPrint.Append(GetHexValue("アウター"));
                        strPrint.Append("H0077V00347P02K8H");
                        strPrint.Append(GetHexValue("入数"));
                        strPrint.Append("H0058V00175L0101P01K8H");
                        strPrint.Append(GetHexValue("インナー"));
                        strPrint.Append("H0077V00175L010P01K8H");
                        strPrint.Append(GetHexValue("入数"));
                        strPrint.Append("H0053V00107L0102P01XM");
                        strPrint.Append(label.blIn);
                        //strPrint.Append("H0172V00269L0101P01K8H");
                        //strPrint.Append(GetHexValue("JANコード：" + label.janCode));
                        //strPrint.Append("H0046V00352L0101P02K8H");
                        //strPrint.Append(GetHexValue("CS入数"));

                        strPrint.Append("H0021V00618L0101P02K9H");
                        //"荷主"
                        strPrint.Append(GetHexValue(AppContext.Instance().Owner.name));
                        strPrint.Append("H0108V00653L0202P02K9H");
                        strPrint.Append(GetHexValue(label.skuName1));
                        if (!label.skuName2.IsEmpty())
                        {
                            strPrint.Append("H0158V00653L0202P02K9H");
                            strPrint.Append(GetHexValue(label.skuName2));
                        }
                        if (!label.skuName3.IsEmpty())
                        {
                            strPrint.Append("H0208V00653L0202P02K9H");
                            strPrint.Append(GetHexValue(label.skuName3));
                        }
                        strPrint.Append("H0053V00278L0102P01XM");
                        strPrint.Append(label.csIn);
                        strPrint.Append("H0108V00185L0404P02K9H");
                        //"常"
                        strPrint.Append(GetHexValue(label.tempDiv));
                        strPrint.Append("H0108V00185L0404(100,100");
                        strPrint.Append("H0398V00487L0101P02K9H");
                        strPrint.Append(GetHexValue("ロット番号：" + label.lotSeq));
                        strPrint.Append("H0372V00487K9H");
                        strPrint.Append(GetHexValue("入荷日：" + label.ibdDate));
                        strPrint.Append("H0345V00487K9H");
                        strPrint.Append(GetHexValue("限界出庫日：" + label.shipExpDate));
                        strPrint.Append("H0319V00488K9H");
                        strPrint.Append(GetHexValue("賞味期限：" + label.expDate));
                        strPrint.Append("H0292V00487K9H");
                        strPrint.Append(GetHexValue("規格：" + label.specs));
                        strPrint.Append("H0006V00213L0101P01K8H");
                        strPrint.Append(GetHexValue(label.dateTime));
                        strPrint.Append("H0059V00653L0202P01K9H");
                        strPrint.Append(GetHexValue(label.skuCode));
                        //if (!label.janCode.IsEmpty())
                        //{
                        //    strPrint.Append("H0111V00305BG02049>H" + label.janCode);
                        //}
                        if (!label.qrCode.IsEmpty())
                        {
                            strPrint.Append("H0285V006532D30,H,03,1,0DN");
                            strPrint.Append(label.qrCode.Length.ToString().PadLeft(4, '0'));
                            strPrint.Append(",");
                            strPrint.Append(label.qrCode);
                        }
                        //<Q>1
                        strPrint.Append("Q");
                        strPrint.Append(times);
                        //<Z>
                        strPrint.Append("Z");
                        //<ETX>
                        strPrint.Append("");

                        Byte[] sendBytes = Encoding.UTF8.GetBytes(strPrint.ToString());
                        retLabel = PtLan.outputLabel(sendBytes);
                        switch (retLabel.Ret)
                        {
                            case Def.PTS_PRINTING:
                                strMsg = Convert.ToString("PTS_PRINTING");
                                break;
                            case Def.PTS_ACK:
                                strMsg = Convert.ToString("PTS_ACK(ACK)");
                                break;
                            case Def.PTS_NAK:
                                strMsg = Convert.ToString("PTS_NAK(NAK)");
                                break;
                            case Def.PTS_STOP:
                                strMsg = Convert.ToString("PTS_STOP");
                                break;
                            case Def.PTS_TIMEOUT:
                                strMsg = Convert.ToString("PTS_TIMEOUT");
                                break;
                            case Def.PTS_SYSTEMERR:
                                strMsg = Convert.ToString("PTS_SYSTEMERR");
                                break;
                            case Def.PTS_UNCONNECTION:
                                strMsg = Convert.ToString("PTS_UNCONNECTION");
                                break;
                        }
                    }
                }
                else if (retOpen == Def.PT_NG)
                {
                    Message.Info("print error!");
                }
                else if (retOpen == Def.PT_PARAMERR)
                {
                    Message.Info("プリンタのパラメータが不正です。");
                }
            }
            finally
            {
                if (retOpen == Def.PT_OK)
                    PtLan.close();
            }
        }

        private static string GetHexValue(string kanji)
        {
            byte[] data = System.Text.Encoding.GetEncoding(932).GetBytes(kanji.TrimEx());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.Length; i++)
            {
                sb.Append(Convert.ToString(data[i], 16).ToUpper());
            }
            return sb.ToString();
        }
    }
}
