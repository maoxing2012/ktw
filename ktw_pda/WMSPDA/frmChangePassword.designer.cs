namespace WMSPDA
{
    partial class frmChangePassword
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmChangePassword));
            this.pnlDetail = new Framework.AlphaPanel();
            this.txtConfirm = new Framework.TextEx();
            this.txtPassword = new Framework.TextEx();
            this.txtOldPassword = new Framework.TextEx();
            this.btnOK = new Framework.ButtonEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnKeyBord = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.statusBar = new System.Windows.Forms.StatusBar();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.Controls.Add(this.txtConfirm);
            this.pnlDetail.Controls.Add(this.txtPassword);
            this.pnlDetail.Controls.Add(this.txtOldPassword);
            this.pnlDetail.Controls.Add(this.btnOK);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnKeyBord);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Top;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(240, 243);
            // 
            // txtConfirm
            // 
            this.txtConfirm.CommitKey = "changePassword";
            this.txtConfirm.DoSelectNext = false;
            this.txtConfirm.Field = "confirmPassword";
            this.txtConfirm.Location = new System.Drawing.Point(6, 127);
            this.txtConfirm.MustInput = true;
            this.txtConfirm.Name = "txtConfirm";
            this.txtConfirm.PasswordChar = '*';
            this.txtConfirm.Size = new System.Drawing.Size(228, 27);
            this.txtConfirm.TabIndex = 2;
            this.txtConfirm.Tip = "再次输入新密码";
            // 
            // txtPassword
            // 
            this.txtPassword.Field = "newPassword";
            this.txtPassword.Location = new System.Drawing.Point(6, 85);
            this.txtPassword.MustInput = true;
            this.txtPassword.Name = "txtPassword";
            this.txtPassword.PasswordChar = '*';
            this.txtPassword.Size = new System.Drawing.Size(228, 27);
            this.txtPassword.TabIndex = 1;
            this.txtPassword.Tip = "新密码";
            // 
            // txtOldPassword
            // 
            this.txtOldPassword.Field = "oldPassword";
            this.txtOldPassword.Location = new System.Drawing.Point(6, 43);
            this.txtOldPassword.MustInput = true;
            this.txtOldPassword.Name = "txtOldPassword";
            this.txtOldPassword.PasswordChar = '*';
            this.txtOldPassword.Size = new System.Drawing.Size(228, 27);
            this.txtOldPassword.TabIndex = 0;
            this.txtOldPassword.Tip = "原密码";
            // 
            // btnOK
            // 
            this.btnOK.BackColor = System.Drawing.Color.White;
            this.btnOK.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnOK.BackgroundImage")));
            this.btnOK.BorderColor = System.Drawing.Color.Transparent;
            this.btnOK.CommitKey = "changePassword";
            this.btnOK.ForeColor = System.Drawing.Color.White;
            this.btnOK.ImageName = "WMSPDA.Images.button1.png";
            this.btnOK.Location = new System.Drawing.Point(6, 178);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(228, 27);
            this.btnOK.TabIndex = 3;
            this.btnOK.Text = "Enter确 定";
            this.btnOK.TransparentImage = true;
            // 
            // picBack
            // 
            this.picBack.ImageName = "WMSPDA.Images.back2.gif";
            this.picBack.Location = new System.Drawing.Point(10, 8);
            this.picBack.Name = "picBack";
            this.picBack.Size = new System.Drawing.Size(13, 10);
            this.picBack.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picBack.TransparentColor = System.Drawing.Color.White;
            // 
            // btnBack
            // 
            this.btnBack.ImageName = "WMSPDA.Images.back1.png";
            this.btnBack.Location = new System.Drawing.Point(3, 5);
            this.btnBack.Name = "btnBack";
            this.btnBack.Size = new System.Drawing.Size(26, 17);
            this.btnBack.TabIndex = 4;
            this.btnBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnKeyBord
            // 
            this.btnKeyBord.ImageName = "WMSPDA.Images.Keyboard.png";
            this.btnKeyBord.Location = new System.Drawing.Point(211, 1);
            this.btnKeyBord.Name = "btnKeyBord";
            this.btnKeyBord.ShowFocusBorder = true;
            this.btnKeyBord.Size = new System.Drawing.Size(26, 26);
            this.btnKeyBord.TabIndex = 5;
            this.btnKeyBord.TransparentColor = System.Drawing.Color.White;
            this.btnKeyBord.Visible = false;
            //this.btnKeyBord.Click += new System.EventHandler(this.btnKeyBord_Click);
            // 
            // picHead
            // 
            this.picHead.Dock = System.Windows.Forms.DockStyle.Top;
            this.picHead.ImageName = "WMSPDA.Images.header.PNG";
            this.picHead.Location = new System.Drawing.Point(0, 0);
            this.picHead.Name = "picHead";
            this.picHead.Size = new System.Drawing.Size(240, 29);
            this.picHead.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picHead.TextColor = System.Drawing.Color.White;
            this.picHead.TextFont = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Bold);
            this.picHead.TitleText = "修改密码";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // statusBar
            // 
            this.statusBar.Location = new System.Drawing.Point(0, 272);
            this.statusBar.Name = "statusBar";
            this.statusBar.Size = new System.Drawing.Size(240, 22);
            // 
            // frmChangePassword
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.ClientSize = new System.Drawing.Size(240, 294);
            this.Controls.Add(this.statusBar);
            this.Controls.Add(this.pnlDetail);
            this.KeyPreview = true;
            this.Name = "frmChangePassword";
            this.Text = "WMS PDA";
            this.ViewName = "";
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.frmChangePassword_KeyDown);
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.TextEx txtConfirm;
        private Framework.TextEx txtPassword;
        private Framework.TextEx txtOldPassword;
        private Framework.ButtonEx btnOK;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.ButtonEx btnKeyBord;
        private Framework.PictureEx picHead;
        private System.Windows.Forms.StatusBar statusBar;
    }
}
