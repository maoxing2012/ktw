using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace Framework
{
    public class Log
    {
        private static object _lockObject = new object();
        public static void Print(string log)
        {
            try
            {
                lock (_lockObject)
                {
                    string fileName = @"\PDALog" + (int)DateTime.Today.DayOfWeek  + ".txt";
                    if (File.Exists(fileName))
                    {
                        if (DateTime.Now.AddDays(-6) > File.GetCreationTime(fileName))
                        {
                            File.Delete(fileName);
                        }
                    }
                    StreamWriter sw = null;
                    try
                    {
                        if (!File.Exists(fileName))
                        {
                            sw = File.CreateText(fileName);
                        }
                        else
                            sw = File.AppendText(fileName);
                        string logContent = string.Format("{0},{1}", DateTime.Now.ToString("yyyy/MM/dd HH:mm:ss"), log);
                        sw.WriteLine(logContent);
                        //sw.Flush();
                    }
                    finally
                    {
                        if (sw != null)
                        {
                            sw.Close();
                            sw = null;
                        }
                    }
                }
            }
            catch
            {
            }
        }
    }
}
