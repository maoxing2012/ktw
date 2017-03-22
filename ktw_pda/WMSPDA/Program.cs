using System;
using System.Linq;
using System.Collections.Generic;
using System.Windows.Forms;
using Framework;
//using DNWA.BHTCL;

namespace WMSPDA
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [MTAThread]
        static void Main(string[] args)
        {
            try
            {
#if DEBUG
                Message.Info("Debug");
#endif
                //版本号
                if(args.Length > 0)
                    AppContext.Instance().Version = args[0];
                //Config cfg = AppContext.Instance().GetConfig();
                //string lastvalue = cfg.GetValue(@"//configuration/setting/lastaccess", "value");
                //string today = System.DateTime.Now.ToString("yyyyMMdd");
                //cfg.SetValue(@"//configuration/setting/lastaccess", "value", today);
                //cfg.Save();
                //if (!lastvalue.IsEmpty() && lastvalue != today)
                //{
                //    DialogResult ret= Message.Ask("システムを安定して使用いただくために、定期的(1 日1 回など)にリセットすることを推奨します。");
                //    if (ret == DialogResult.Yes)
                //    {
                //        PwrMng.ShutDown(PwrMng.EN_SHUTDOWN_MODE.WARM);
                //        return;
                //    }
                //}

                //启动程序
                Application.Run(new frmMain());
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}