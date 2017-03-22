namespace WMSPDA
{
    partial class frmPersonalCenter
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.pnlDetail = new Framework.AlphaPanel();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnRefresh = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.btnPrint = new Framework.ButtonEx();
            this.btnWareHouse = new Framework.ButtonEx();
            this.btnExit = new Framework.ButtonEx();
            this.btnBarCode = new Framework.ButtonEx();
            this.btnPassword = new Framework.ButtonEx();
            this.lblVer = new Framework.LabelEx();
            this.lblUser = new Framework.LabelEx();
            this.picUser = new Framework.PictureEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picUser)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnRefresh);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Controls.Add(this.btnPrint);
            this.pnlDetail.Controls.Add(this.btnWareHouse);
            this.pnlDetail.Controls.Add(this.btnExit);
            this.pnlDetail.Controls.Add(this.btnBarCode);
            this.pnlDetail.Controls.Add(this.btnPassword);
            this.pnlDetail.Controls.Add(this.lblVer);
            this.pnlDetail.Controls.Add(this.lblUser);
            this.pnlDetail.Controls.Add(this.picUser);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Top;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(320, 243);
            // 
            // picBack
            // 
            this.picBack.ImageName = "WMSPDA.Images.back2.gif";
            this.picBack.Location = new System.Drawing.Point(11, 8);
            this.picBack.Name = "picBack";
            this.picBack.Size = new System.Drawing.Size(13, 10);
            this.picBack.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picBack.TextColor = System.Drawing.Color.Empty;
            this.picBack.TextFont = null;
            this.picBack.TitleText = null;
            this.picBack.TransparentColor = System.Drawing.Color.White;
            this.picBack.Visible = false;
            // 
            // btnBack
            // 
            this.btnBack.CommitKey = null;
            this.btnBack.HotKey = null;
            this.btnBack.ImageName = "WMSPDA.Images.back1.png";
            this.btnBack.Location = new System.Drawing.Point(4, 5);
            this.btnBack.Name = "btnBack";
            this.btnBack.RoundButton = false;
            this.btnBack.Size = new System.Drawing.Size(26, 17);
            this.btnBack.TabIndex = 18;
            this.btnBack.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnBack.Visible = false;
            this.btnBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnRefresh
            // 
            this.btnRefresh.CommitKey = null;
            this.btnRefresh.HotKey = null;
            this.btnRefresh.ImageName = "WMSPDA.Images.refresh.png";
            this.btnRefresh.Location = new System.Drawing.Point(292, 1);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.RoundButton = false;
            this.btnRefresh.Size = new System.Drawing.Size(26, 26);
            this.btnRefresh.TabIndex = 17;
            this.btnRefresh.TransparentColor = System.Drawing.Color.White;
            this.btnRefresh.Visible = false;
            // 
            // picHead
            // 
            this.picHead.Dock = System.Windows.Forms.DockStyle.Top;
            this.picHead.ImageName = "WMSPDA.Images.header.PNG";
            this.picHead.Location = new System.Drawing.Point(0, 0);
            this.picHead.Name = "picHead";
            this.picHead.Size = new System.Drawing.Size(320, 29);
            this.picHead.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picHead.TextColor = System.Drawing.Color.White;
            this.picHead.TextFont = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Bold);
            this.picHead.TitleText = "个人中心";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // btnPrint
            // 
            this.btnPrint.CommitKey = null;
            this.btnPrint.HotKey = "key4";
            this.btnPrint.ImageName = "WMSPDA.Images.menu.png";
            this.btnPrint.Location = new System.Drawing.Point(163, 160);
            this.btnPrint.Name = "btnPrint";
            this.btnPrint.RoundButton = false;
            this.btnPrint.Size = new System.Drawing.Size(150, 30);
            this.btnPrint.TabIndex = 10;
            this.btnPrint.Tag = "print";
            this.btnPrint.Text = "4.打印机设置&测试";
            this.btnPrint.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnPrint.Click += new System.EventHandler(this.Button_Click);
            // 
            // btnWareHouse
            // 
            this.btnWareHouse.CommitKey = null;
            this.btnWareHouse.HotKey = "key2";
            this.btnWareHouse.ImageName = "WMSPDA.Images.menu.png";
            this.btnWareHouse.Location = new System.Drawing.Point(163, 124);
            this.btnWareHouse.Name = "btnWareHouse";
            this.btnWareHouse.RoundButton = false;
            this.btnWareHouse.Size = new System.Drawing.Size(150, 30);
            this.btnWareHouse.TabIndex = 11;
            this.btnWareHouse.Tag = "warehouse";
            this.btnWareHouse.Text = "2.切换仓库(维信GX)";
            this.btnWareHouse.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnWareHouse.Click += new System.EventHandler(this.Button_Click);
            // 
            // btnExit
            // 
            this.btnExit.CommitKey = null;
            this.btnExit.HotKey = "key9";
            this.btnExit.ImageName = "WMSPDA.Images.button2.png";
            this.btnExit.Location = new System.Drawing.Point(47, 203);
            this.btnExit.Name = "btnExit";
            this.btnExit.RoundButton = false;
            this.btnExit.Size = new System.Drawing.Size(226, 36);
            this.btnExit.TabIndex = 9;
            this.btnExit.Text = "9.退出当前账号";
            this.btnExit.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // btnBarCode
            // 
            this.btnBarCode.CommitKey = null;
            this.btnBarCode.HotKey = "key3";
            this.btnBarCode.ImageName = "WMSPDA.Images.menu.png";
            this.btnBarCode.Location = new System.Drawing.Point(7, 160);
            this.btnBarCode.Name = "btnBarCode";
            this.btnBarCode.RoundButton = false;
            this.btnBarCode.Size = new System.Drawing.Size(150, 30);
            this.btnBarCode.TabIndex = 7;
            this.btnBarCode.Tag = "barcode";
            this.btnBarCode.Text = "3.条码扫描测试";
            this.btnBarCode.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnBarCode.Click += new System.EventHandler(this.Button_Click);
            // 
            // btnPassword
            // 
            this.btnPassword.CommitKey = null;
            this.btnPassword.HotKey = "key1";
            this.btnPassword.ImageName = "WMSPDA.Images.menu.png";
            this.btnPassword.Location = new System.Drawing.Point(7, 124);
            this.btnPassword.Name = "btnPassword";
            this.btnPassword.RoundButton = false;
            this.btnPassword.Size = new System.Drawing.Size(150, 30);
            this.btnPassword.TabIndex = 8;
            this.btnPassword.Tag = "password";
            this.btnPassword.Text = "1.修改密码";
            this.btnPassword.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnPassword.Click += new System.EventHandler(this.Button_Click);
            // 
            // lblVer
            // 
            this.lblVer.Border = false;
            this.lblVer.ForeColor = System.Drawing.Color.Navy;
            this.lblVer.Location = new System.Drawing.Point(102, 87);
            this.lblVer.Name = "lblVer";
            this.lblVer.Size = new System.Drawing.Size(212, 18);
            this.lblVer.Text = "当前版本号：V1.0";
            // 
            // lblUser
            // 
            this.lblUser.Border = false;
            this.lblUser.Location = new System.Drawing.Point(102, 47);
            this.lblUser.Name = "lblUser";
            this.lblUser.Size = new System.Drawing.Size(212, 34);
            this.lblUser.Text = "亲爱的毛幸用户，您好";
            // 
            // picUser
            // 
            this.picUser.ImageName = "";
            this.picUser.Location = new System.Drawing.Point(6, 33);
            this.picUser.Name = "picUser";
            this.picUser.Size = new System.Drawing.Size(90, 73);
            this.picUser.TextColor = System.Drawing.Color.Empty;
            this.picUser.TextFont = null;
            this.picUser.TitleText = null;
            this.picUser.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            // 
            // frmPersonalCenter
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.ClientSize = new System.Drawing.Size(320, 294);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmPersonalCenter";
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picUser)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.ButtonEx btnPrint;
        private Framework.ButtonEx btnWareHouse;
        private Framework.ButtonEx btnExit;
        private Framework.ButtonEx btnBarCode;
        private Framework.ButtonEx btnPassword;
        private Framework.LabelEx lblVer;
        private Framework.LabelEx lblUser;
        private Framework.PictureEx picUser;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.ButtonEx btnRefresh;
        private Framework.PictureEx picHead;

    }
}
