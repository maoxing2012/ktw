using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using OpenNETCF.Drawing;
using Framework;
using System.IO;
using System.Reflection;
using System.Drawing.Imaging;
using Entity;
using OpenNETCF.Windows.Forms;

namespace WMSPDA
{
    /// <summary>
    /// 登录窗体
    /// </summary>
    public partial class frmLogin : BaseForm
    {
        public frmLogin()
        {
            InitializeComponent();
        }
        /// <summary>
        /// 窗体加载
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmLogin_Load(object sender, EventArgs e)
        {
            try
            {
                //显示标题
                picHead.TitleText = "ログイン";
                picHead.TextFont = this.Font;
                picHead.TextColor = Color.White;
                lblStatus.Text = "バージョン：{0}".FormatEx(AppContext.Instance().Version);
                this.Size = Screen.PrimaryScreen.WorkingArea.Size;
                //this.txtUser.CharacterCasing = CharacterCasing.Lower;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 快捷键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmLogin_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                switch (e.KeyCode)
                {
                    case Keys.Escape://退出
                        this.Close();
                        break;
                    case Keys.F1://设置
                        picSetting_Click(sender, e);
                        break;
                }
                if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("green").Value))
                {
                    this.Close();
                }
                else if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("red").Value))
                {
                    btnKeybord.PerformClick();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 登录
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                EnableButtons(false);
                //清空提示栏
                Notify(string.Empty);

                //数据校验
                if (!CheckUser(1))
                    return;
                if (!CheckPassword(1))
                    return;

                //取服务器数据
                MobileRequest req = new MobileRequest();
                req.PageId = "login";
                req.Parameters.Add("loginName", txtUser.Text);
                req.Parameters.Add("password", txtPassword.Text);
                AppContext app = AppContext.Instance();
                MobileResponse<Dictionary<string, object>> response = null;
                //调用服务端
                int ret = base.PostData("login", req, out response);
                if (!(ret == 0 || ret == 2))
                    //错误返回
                    return;
                ////测试代码
                //MobileResponse response = new MobileResponse();
                //response.SeverityMsgType = "M";
                //response.SeverityMsg = "处理成功";
                //response.Results.Add("whId", "2000");
                //response.Results.Add("whCode", "ZLZG");
                //response.Results.Add("whName", "中联重工");
                //response.Results.Add("userId", "1000");
                //response.Results.Add("loginName", "admin");
                //response.Results.Add("userName", "毛幸");

                //成功登录
                app.LoginUser = new UserObj(long.Parse(response.Results["userId"].TrimEx())
                    , response.Results["loginName"].TrimEx(), response.Results["userName"].TrimEx());
                //app.WareHouse = new WareHouseObj(long.Parse(response.Results["whId"].TrimEx())
                //    , response.Results["whName"].TrimEx(), response.Results["whCode"].TrimEx());

                //switch (StringUtil.TrimString(response.SeverityMsgType).ToUpper())
                //{
                //    case "W":
                //        switch (response.TargetPageId.ToLower())
                //        {
                //            case "changePassword.html":
                //                using (frmChangePassword frmPass = new frmChangePassword())
                //                {
                //                    //初始化
                //                    frmPass.Init(false, "login");
                //                    frmPass.Show(this);
                //                    //显示修改密码
                //                    if (frmPass.ShowDialog() != DialogResult.Yes)
                //                    {
                //                        //不变更密码不能进
                //                        this.DialogResult = DialogResult.Cancel;
                //                        this.Close();
                //                        return;
                //                    }
                //                }
                //                break;
                //        }
                //        break;
                //}
                //记录登录时间
                app.LoginUser.LoginTime = DateTime.Now;

                //荷主選択（日本食研）
                using (frmOwnerList owner = new frmOwnerList())
                {
                    ret = owner.Init(false);
                    if (ret != 0 && ret != 2)
                    {
                        return;
                    }
                    if (owner.ShowDialog() != DialogResult.OK)
                    {
                        return;
                    }
                }
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                EnableButtons(true);
            }
        }
        /// <summary>
        /// 密码
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtPassword_KeyDown(object sender, KeyEventArgs e)
        {
            if ((e.KeyCode == System.Windows.Forms.Keys.Enter))
            {
                // Enter
                btnOK.PerformClick();
            }
        }

        /// <summary>
        /// 点击设置
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmLogin_MouseDown(object sender, MouseEventArgs e)
        {
            //if (this.lblSetting.HitTest(e.X, e.Y))
            //{
            //    picSetting_Click(sender, e);
            //}
        }

        /// <summary>
        /// 设置
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void picSetting_Click(object sender, EventArgs e)
        {
            try
            {
                //显示设置画面
                using (frmSetting nextFrm = new frmSetting())
                {
                    nextFrm.ShowDialog();
                }

            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 用户
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtUser_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    CheckUser(0);
                    txtPassword.Focus();
                    txtPassword.SelectAll();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 检验用户
        /// </summary>
        /// <param name="validType"></param>
        /// <returns></returns>
        private bool CheckUser(int validType)
        {
            if (string.IsNullOrEmpty(txtUser.Text))
            {
                this.lblStatus.Text = AppContext.Instance().GetMessage("field.must.input").FormatEx(txtUser.Tip);
                //Message.Info(AppContext.Instance().GetMessage("field.must.input").FormatEx(txtUser.Tip));
                if (0 == validType)
                {
                    return true;
                }
                else
                {
                    txtUser.SelectAll();
                    txtUser.Focus();
                    return false;
                }
            }
            return true;
        }
        /// <summary>
        /// 检验密码
        /// </summary>
        /// <param name="validType"></param>
        /// <returns></returns>
        private bool CheckPassword(int validType)
        {
            if (string.IsNullOrEmpty(txtPassword.Text))
            {
                //Message.Info(AppContext.Instance().GetMessage("field.must.input").FormatEx(txtPassword.Tip));
                txtPassword.SelectAll();
                txtPassword.Focus();
                this.lblStatus.Text = AppContext.Instance().GetMessage("field.must.input").FormatEx(txtPassword.Tip);
                return false;
            }
            return true;
        }

        /// <summary>
        /// 打开软键盘
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnKeybord_Click(object sender, EventArgs e)
        {
            try
            {
                Sip.Visible = !Sip.Visible;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        ///// <summary>
        ///// WINCE PictureBox控件图片透明化 
        ///// </summary>
        ///// <param name="pic"></param>
        ///// <param name="picBack"></param>
        //private void Transparent(PictureBox pic, PictureBox picBack)
        //{
        //    // 生成一个与PictureBox一样大的缓冲区             
        //    Bitmap buffer = new Bitmap(pic.Width, pic.Height);
        //    // 创建图形对象，绘图区域为刚刚生成的缓冲区             
        //    Graphics g = Graphics.FromImage(buffer);
        //    // 背景上被PictureBox覆盖的区域区域。假定PictureBox覆盖在背景图之上             
        //    Rectangle scrRect = new Rectangle(
        //        pic.Left - picBack.Left,
        //        // 截图区域的左上角X坐标                 
        //        pic.Top - picBack.Top,
        //        // 截图区域的左上角Y坐标                
        //        pic.Width,
        //        // 截图区域的宽度              
        //        pic.Height);
        //    // 截图区域的高度              
        //    // 将背景上被PictureBox覆盖的那块区域截图下来，保存在缓冲区中 

        //    Bitmap bufferBack = new Bitmap(picBack.Width, picBack.Height);
        //    Graphics gback = Graphics.FromImage(bufferBack);
        //    Rectangle rectBack = new Rectangle(0, 0, 0, 0);
        //    rectBack.Size=picBack.ClientSize;
        //    gback.DrawImage(picBack.Image, rectBack, new Rectangle(0, 0, picBack.Image.Width, picBack.Image.Height), GraphicsUnit.Pixel);

        //    g.DrawImage(bufferBack,
        //        // 背景图              
        //        0,
        //        // 所截图片画在缓冲区中的左上角X坐标           
        //        0,
        //        // 所截图片画在缓冲区中的左上角Y坐标         
        //        scrRect,
        //        // 需要截图的区域         
        //        GraphicsUnit.Pixel);
        //    // 此参数表示本次调用传入的参数以像素为单位             
        //    // 指定被当做透明色的颜色值，这里使用纯白。    
        //    // MSDN中SetColorKey的低值和高值可不相同，但这里必须相同，否则报错。原因未知。 
        //    ImageAttributes imageAttr = new ImageAttributes();
        //    imageAttr.SetColorKey(Color.FromArgb(255, 255, 255), Color.FromArgb(255, 255, 255));
        //    // 将PictureBox的Image拷贝到Bitmap中，指定的颜色当做透明色           

        //    Bitmap bufferPic = new Bitmap(pic.Width, pic.Height);
        //    Graphics gpic = Graphics.FromImage(bufferPic);
        //    rectBack = new Rectangle(0, 0, 0, 0);
        //    rectBack.Size = pic.ClientSize;
        //    gpic.DrawImage(pic.Image, rectBack, new Rectangle(0, 0, pic.Image.Width, pic.Image.Height), GraphicsUnit.Pixel);

        //    g.DrawImage(bufferPic,
        //        // 将PictureBox的图片绘制在缓冲区中         
        //        new Rectangle(0, 0, pic.Width, pic.Height),
        //        // 绘制区域，将PictureBox的图片按比例缩放绘制在这个区域内      
        //        0,
        //        // 截图区域的左边坐标，PictureBox必须覆盖为背景图之上     
        //        0,
        //        // 截图区域的上边坐标            
        //        pic.Width,
        //        // 截图区域的宽度            
        //        pic.Height,
        //        // 截图区域的高度            
        //        GraphicsUnit.Pixel,
        //        // 此参数表示本次调用传入的参数以像素为单位       
        //        imageAttr);
        //    // 透明色            
        //    pic.Image = buffer;
        //}
    }
}