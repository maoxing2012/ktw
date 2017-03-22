using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Threading;
using System.Xml;
using System.Diagnostics;
using Ionic.Zip;

namespace AppUpdater
{
    public partial class frmMain : Form
    {
        public frmMain()
        {
            InitializeComponent();
        }

        protected override void OnPaintBackground(PaintEventArgs e)
        {
            //透明处理
            //base.OnPaintBackground(e);
        }

        private void frmMain_KeyDown(object sender, KeyEventArgs e)
        {
            if ((e.KeyCode == System.Windows.Forms.Keys.Escape))
            {
                // ESC
                this.Close();
            }
        }
        /// <summary>
        /// 居中处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmMain_Load(object sender, EventArgs e)
        {
            try
            {
                this.Top = Screen.PrimaryScreen.WorkingArea.Top + (Screen.PrimaryScreen.WorkingArea.Height - 40) / 2;
                this.Width = Screen.PrimaryScreen.WorkingArea.Width;
                //取出版本信息
                lblInfo.Text = "アップデートを確認しています...";
                Cursor.Current = Cursors.WaitCursor;
                ThreadPool.QueueUserWorkItem(new WaitCallback(CheckForNewVersion));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
            finally
            {
                Cursor.Current = Cursors.Default;
            }
        }
        /// <summary>
        /// 检查新版本
        /// </summary>
        public void CheckForNewVersion(object obj)
        {
            bool startFlag = false;
            String appPath=string.Empty;
            string exe = string.Empty;
            double currentVersion = 0;
            try
            {
                String fullAppName = this.GetType().Assembly.GetName().CodeBase;
                appPath = Path.GetDirectoryName(fullAppName);
                Config cfg = new Config();
                string url =@"http://"+ cfg.GetValue(@"//setting/server", "value") + "//wmspdaversion.xml";
                string updateFilePath = Path.Combine(appPath, "wmspdaversion.xml");
                currentVersion = double.Parse(cfg.GetValue(@"//setting/version", "value"));
                exe = cfg.GetValue(@"//setting/version", "exe");
                TransferManager tm = new TransferManager();
                Stream s;
                if (tm.downloadFile(url, out s, updateFilePath, new TransferManager.TransferProgress(TransferProgress)))
                {
                    s.Close();
                }
                else
                {
                    //下载版本文件出错 
                    MessageBox.Show("最新バージョンの取得に失敗しました。サーバー設定やネット設定を確認してください。");
                    return;
                }
                XmlDocument xDoc = new XmlDocument();
                xDoc.Load(updateFilePath);
                double newVersion = double.Parse(xDoc.SelectSingleNode(@"//ver").Attributes["value"].Value);
                if (currentVersion < newVersion)
                {
                    //有新版本
                    DisplayInfo("最新バージョンをダウンロード中...", true);
                    url = xDoc.SelectSingleNode(@"//ver").Attributes["url"].Value;
                    string updateZip = appPath + "\\" + newVersion.ToString() + ".zip";
                    if (tm.downloadFile(url, out s, updateZip, new TransferManager.TransferProgress(TransferProgress)))
                    {
                        s.Close();
                    }
                    else
                    {
                        //下载版本文件出错 
                        MessageBox.Show("最新バージョンのダウンロードに失敗しました。ネット設定を確認してください。");
                        startFlag = false;
                        return;
                    }
                    //解压缩文件
                    DisplayInfo("最新バージョンにアップデート中...", false);

                    using (ZipFile zip1 = ZipFile.Read(updateZip))
                    {
                        foreach (ZipEntry e in zip1)
                        {
                            string eStr = e.ToString();
                            string[] eArray = eStr.Split('/');
                            string isExist = appPath + "\\" + eArray[1];
                            if (File.Exists(isExist))
                            {
                                File.Delete(isExist);
                            }
                            DisplayInfo("アップデート" + eArray[1], false);
                            e.Extract(appPath, ExtractExistingFileAction.OverwriteSilently);
                        }
                    }
                    //更新本地版本
                    //保留客户端设置
                    string para1 = cfg.GetValue(@"//setting/server", "value");
                    string para2 = cfg.GetValue(@"//setting/com", "value");
                    cfg = new Config();
                    cfg.SetValue(@"//setting/version", "value", newVersion.ToString());
                    //保留客户端设置
                    cfg.SetValue(@"//setting/server", "value", para1);
                    cfg.SetValue(@"//setting/com", "value", para2);
                    currentVersion = newVersion;
                    cfg.Save();
                    startFlag = true;

                    //删除下载文件
                    File.Delete(updateZip);
                    DisplayInfo("最新バージョンのアップデートに成功しました。", false);
                }
                startFlag = true;
            }
            finally
            {
                //启动主要进程
                try
                {
                    this.Invoke(new Action<bool, string, string>(Finish), true, exe, currentVersion.ToString());
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.ToString());
                }
            }
        }
        /// <summary>
        /// 显示进度
        /// </summary>
        /// <param name="progress"></param>
        private void TransferProgress(int progress)
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new TransferManager.TransferProgress(TransferProgress),progress);
                return;
            }
            //显示进度
            if (progress > 100)
                progress = 100;
            else if (progress < 0)
                progress = 0;
            progressBar.Value = progress;
        }
        /// <summary>
        /// 显示文本信息
        /// </summary>
        /// <param name="info"></param>
        /// <param name="blnZero">清空进度条</param>
        private void DisplayInfo(string info,bool blnZero)
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new Action<string, bool>(DisplayInfo), info, blnZero);
                return;
            }
            lblInfo.Text = info;
            if (blnZero)
                progressBar.Value = 0;
        }
        /// <summary>
        /// 结束
        /// </summary>
        /// <param name="startFlag"></param>
        private void Finish(bool startFlag,string exe ,string version)
        {
            try
            {
                String fullAppName = this.GetType().Assembly.GetName().CodeBase;
                String appPath = Path.GetDirectoryName(fullAppName);

                if (startFlag)
                    Process.Start(appPath + "\\" + exe, version);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
            finally
            {
                this.Close();
            }
        }
    }
}