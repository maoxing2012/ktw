namespace WMSPDA
{
    partial class frmLogin
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmLogin));
            this.btnOK = new Framework.ButtonEx();
            this.txtPassword = new Framework.TextEx();
            this.txtUser = new Framework.TextEx();
            this.picHead = new Framework.PictureEx();
            this.picLogo = new Framework.PictureEx();
            this.lblSetting = new Framework.LabelEx();
            this.btnSetting = new Framework.ButtonEx();
            this.btnKeybord = new Framework.ButtonEx();
            this.lblStatus = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picLogo)).BeginInit();
            this.SuspendLayout();
            // 
            // btnOK
            // 
            this.btnOK.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(42)))), ((int)(((byte)(144)))), ((int)(((byte)(198)))));
            this.btnOK.BackgroundImage = null;
            this.btnOK.BorderColor = System.Drawing.Color.Transparent;
            this.btnOK.CommitKey = null;
            this.btnOK.ForeColor = System.Drawing.Color.White;
            this.btnOK.HotKey = null;
            this.btnOK.ImageName = "WMSPDA.Images.button1.png";
            this.btnOK.Location = new System.Drawing.Point(17, 241);
            this.btnOK.Name = "btnOK";
            this.btnOK.RoundButton = true;
            this.btnOK.Size = new System.Drawing.Size(204, 27);
            this.btnOK.TabIndex = 4;
            this.btnOK.Text = "Enter ログイン";
            this.btnOK.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // txtPassword
            // 
            this.txtPassword.BorderColor = System.Drawing.Color.Empty;
            this.txtPassword.CommitKey = null;
            this.txtPassword.DoSelectNext = false;
            this.txtPassword.Field = null;
            this.txtPassword.Location = new System.Drawing.Point(17, 200);
            this.txtPassword.Name = "txtPassword";
            this.txtPassword.PasswordChar = '*';
            this.txtPassword.Pattern = null;
            this.txtPassword.Size = new System.Drawing.Size(205, 25);
            this.txtPassword.TabIndex = 3;
            this.txtPassword.Tip = "パスワード ";
            this.txtPassword.ValidsCommit = null;
            this.txtPassword.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtPassword_KeyDown);
            // 
            // txtUser
            // 
            this.txtUser.BorderColor = System.Drawing.Color.Empty;
            this.txtUser.CommitKey = null;
            this.txtUser.Field = null;
            this.txtUser.Location = new System.Drawing.Point(17, 167);
            this.txtUser.Name = "txtUser";
            this.txtUser.Pattern = null;
            this.txtUser.Size = new System.Drawing.Size(205, 25);
            this.txtUser.TabIndex = 2;
            this.txtUser.Tip = "ユーザ名";
            this.txtUser.ValidsCommit = null;
            this.txtUser.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtUser_KeyDown);
            // 
            // picHead
            // 
            this.picHead.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.picHead.Image = ((System.Drawing.Image)(resources.GetObject("picHead.Image")));
            this.picHead.ImageName = "";
            this.picHead.Location = new System.Drawing.Point(0, 0);
            this.picHead.Name = "picHead";
            this.picHead.Size = new System.Drawing.Size(240, 29);
            this.picHead.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picHead.TextColor = System.Drawing.Color.Empty;
            this.picHead.TextFont = null;
            this.picHead.TitleText = null;
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // picLogo
            // 
            this.picLogo.Image = ((System.Drawing.Image)(resources.GetObject("picLogo.Image")));
            this.picLogo.ImageName = "WMSPDA.Images.logo.png";
            this.picLogo.Location = new System.Drawing.Point(0, 80);
            this.picLogo.Name = "picLogo";
            this.picLogo.Size = new System.Drawing.Size(240, 60);
            this.picLogo.TextColor = System.Drawing.Color.Empty;
            this.picLogo.TextFont = null;
            this.picLogo.TitleText = null;
            this.picLogo.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            // 
            // lblSetting
            // 
            this.lblSetting.Border = false;
            this.lblSetting.Location = new System.Drawing.Point(26, 32);
            this.lblSetting.Name = "lblSetting";
            this.lblSetting.Size = new System.Drawing.Size(167, 21);
            this.lblSetting.Text = "F1設定";
            this.lblSetting.Transparent = false;
            this.lblSetting.ClickEx += new System.EventHandler(this.picSetting_Click);
            // 
            // btnSetting
            // 
            this.btnSetting.BackgroundImage = null;
            this.btnSetting.CommitKey = null;
            this.btnSetting.HotKey = "keyf1";
            this.btnSetting.ImageName = "WMSPDA.Images.setting.png";
            this.btnSetting.Location = new System.Drawing.Point(0, 29);
            this.btnSetting.Name = "btnSetting";
            this.btnSetting.RoundButton = false;
            this.btnSetting.Size = new System.Drawing.Size(24, 24);
            this.btnSetting.TabIndex = 0;
            this.btnSetting.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnSetting.Click += new System.EventHandler(this.picSetting_Click);
            // 
            // btnKeybord
            // 
            this.btnKeybord.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnKeybord.BackgroundImage = null;
            this.btnKeybord.CommitKey = null;
            this.btnKeybord.HotKey = null;
            this.btnKeybord.ImageName = "WMSPDA.Images.Keyboard.png";
            this.btnKeybord.Location = new System.Drawing.Point(211, 1);
            this.btnKeybord.Name = "btnKeybord";
            this.btnKeybord.RoundButton = false;
            this.btnKeybord.Size = new System.Drawing.Size(26, 26);
            this.btnKeybord.TabIndex = 1;
            this.btnKeybord.TransparentColor = System.Drawing.Color.White;
            this.btnKeybord.Click += new System.EventHandler(this.btnKeybord_Click);
            // 
            // lblStatus
            // 
            this.lblStatus.Location = new System.Drawing.Point(2, 273);
            this.lblStatus.Name = "lblStatus";
            this.lblStatus.Size = new System.Drawing.Size(234, 21);
            // 
            // frmLogin
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.lblStatus);
            this.Controls.Add(this.btnKeybord);
            this.Controls.Add(this.lblSetting);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.txtPassword);
            this.Controls.Add(this.txtUser);
            this.Controls.Add(this.picHead);
            this.Controls.Add(this.picLogo);
            this.Controls.Add(this.btnSetting);
            this.Name = "frmLogin";
            this.Text = "ログイン";
            this.Load += new System.EventHandler(this.frmLogin_Load);
            this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.frmLogin_MouseDown);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.frmLogin_KeyDown);
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picLogo)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.TextEx txtUser;
        private Framework.TextEx txtPassword;
        private Framework.PictureEx picHead;
        private Framework.ButtonEx btnOK;
        private Framework.PictureEx picLogo;
        private Framework.LabelEx lblSetting;
        private Framework.ButtonEx btnSetting;
        private Framework.ButtonEx btnKeybord;
        private System.Windows.Forms.Label lblStatus;




    }
}