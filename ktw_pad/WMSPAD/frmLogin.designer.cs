namespace WMSPAD
{
    partial class frmLogin
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblSetting = new Framework.LabelEx();
            this.picSetting = new Framework.PictureEx();
            this.picLogo = new Framework.PictureEx();
            this.lblUser = new Framework.LabelEx();
            this.txtUser = new Framework.TextEx();
            this.lblPassword = new Framework.LabelEx();
            this.txtPassword = new Framework.TextEx();
            this.btnCancel = new Framework.ButtonEx();
            this.btnOK = new Framework.ButtonEx();
            this.lblStatus = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).BeginInit();
            this.pnlTop.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picSetting)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picLogo)).BeginInit();
            this.SuspendLayout();
            // 
            // lblTitle
            // 
            this.lblTitle.Text = "ログイン";
            // 
            // btnRight
            // 
            this.btnRight.Visible = false;
            // 
            // btnLeft
            // 
            this.btnLeft.Visible = false;
            // 
            // lblSetting
            // 
            this.lblSetting.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblSetting.Location = new System.Drawing.Point(42, 54);
            this.lblSetting.Name = "lblSetting";
            this.lblSetting.Size = new System.Drawing.Size(66, 29);
            this.lblSetting.TabIndex = 5;
            this.lblSetting.Text = "設定";
            this.lblSetting.Click += new System.EventHandler(this.picSetting_Click);
            // 
            // picSetting
            // 
            this.picSetting.Location = new System.Drawing.Point(7, 54);
            this.picSetting.Name = "picSetting";
            this.picSetting.Size = new System.Drawing.Size(32, 32);
            this.picSetting.TabIndex = 4;
            this.picSetting.TabStop = false;
            this.picSetting.Click += new System.EventHandler(this.picSetting_Click);
            // 
            // picLogo
            // 
            this.picLogo.Location = new System.Drawing.Point(17, 92);
            this.picLogo.Name = "picLogo";
            this.picLogo.Size = new System.Drawing.Size(571, 189);
            this.picLogo.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picLogo.TabIndex = 15;
            this.picLogo.TabStop = false;
            // 
            // lblUser
            // 
            this.lblUser.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblUser.Location = new System.Drawing.Point(66, 305);
            this.lblUser.Name = "lblUser";
            this.lblUser.Size = new System.Drawing.Size(150, 33);
            this.lblUser.TabIndex = 16;
            this.lblUser.Text = "ユーザ名";
            this.lblUser.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // txtUser
            // 
            this.txtUser.BorderColor = System.Drawing.Color.Empty;
            this.txtUser.CommitKey = null;
            this.txtUser.Field = null;
            this.txtUser.Font = new System.Drawing.Font("Tahoma", 20F);
            this.txtUser.Location = new System.Drawing.Point(227, 301);
            this.txtUser.Name = "txtUser";
            this.txtUser.Pattern = "";
            this.txtUser.Size = new System.Drawing.Size(273, 40);
            this.txtUser.TabIndex = 0;
            this.txtUser.Tip = "ユーザ名";
            this.txtUser.ValidsCommit = null;
            this.txtUser.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtUser_KeyDown);
            // 
            // lblPassword
            // 
            this.lblPassword.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblPassword.Location = new System.Drawing.Point(66, 364);
            this.lblPassword.Name = "lblPassword";
            this.lblPassword.Size = new System.Drawing.Size(150, 33);
            this.lblPassword.TabIndex = 16;
            this.lblPassword.Text = "パスワード";
            this.lblPassword.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // txtPassword
            // 
            this.txtPassword.BorderColor = System.Drawing.Color.Empty;
            this.txtPassword.CommitKey = null;
            this.txtPassword.Field = null;
            this.txtPassword.Font = new System.Drawing.Font("Tahoma", 20F);
            this.txtPassword.Location = new System.Drawing.Point(227, 359);
            this.txtPassword.Name = "txtPassword";
            this.txtPassword.PasswordChar = '*';
            this.txtPassword.Pattern = "";
            this.txtPassword.Size = new System.Drawing.Size(273, 40);
            this.txtPassword.TabIndex = 1;
            this.txtPassword.Tip = "パスワード ";
            this.txtPassword.ValidsCommit = null;
            this.txtPassword.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtPassword_KeyDown);
            // 
            // btnCancel
            // 
            this.btnCancel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(48)))), ((int)(((byte)(0)))));
            this.btnCancel.CommitKey = "";
            this.btnCancel.FlatAppearance.BorderSize = 0;
            this.btnCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnCancel.Font = new System.Drawing.Font("MS UI Gothic", 20.25F);
            this.btnCancel.HotKey = "esc";
            this.btnCancel.ImageName = "skip";
            this.btnCancel.Location = new System.Drawing.Point(0, 451);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(300, 50);
            this.btnCancel.TabIndex = 2;
            this.btnCancel.Text = "Esc  閉じる";
            this.btnCancel.UseVisualStyleBackColor = false;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // btnOK
            // 
            this.btnOK.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(42)))), ((int)(((byte)(144)))), ((int)(((byte)(198)))));
            this.btnOK.CommitKey = "";
            this.btnOK.FlatAppearance.BorderSize = 0;
            this.btnOK.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnOK.Font = new System.Drawing.Font("MS UI Gothic", 20.25F);
            this.btnOK.HotKey = "";
            this.btnOK.ImageName = "skip";
            this.btnOK.Location = new System.Drawing.Point(300, 451);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(300, 50);
            this.btnOK.TabIndex = 3;
            this.btnOK.Text = "Enter ログイン";
            this.btnOK.UseVisualStyleBackColor = false;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // lblStatus
            // 
            this.lblStatus.Font = new System.Drawing.Font("MS UI Gothic", 14F);
            this.lblStatus.Location = new System.Drawing.Point(3, 427);
            this.lblStatus.Name = "lblStatus";
            this.lblStatus.Size = new System.Drawing.Size(597, 21);
            this.lblStatus.TabIndex = 18;
            this.lblStatus.Text = "バージョン：";
            this.lblStatus.Visible = false;
            // 
            // frmLogin
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(600, 500);
            this.Controls.Add(this.lblStatus);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.txtPassword);
            this.Controls.Add(this.txtUser);
            this.Controls.Add(this.lblPassword);
            this.Controls.Add(this.lblUser);
            this.Controls.Add(this.picLogo);
            this.Controls.Add(this.lblSetting);
            this.Controls.Add(this.picSetting);
            this.Name = "frmLogin";
            this.ShowInTaskbar = false;
            this.Text = "frmLogin";
            this.Load += new System.EventHandler(this.frmLogin_Load);
            this.Controls.SetChildIndex(this.pnlTop, 0);
            this.Controls.SetChildIndex(this.picSetting, 0);
            this.Controls.SetChildIndex(this.lblSetting, 0);
            this.Controls.SetChildIndex(this.picLogo, 0);
            this.Controls.SetChildIndex(this.lblUser, 0);
            this.Controls.SetChildIndex(this.lblPassword, 0);
            this.Controls.SetChildIndex(this.txtUser, 0);
            this.Controls.SetChildIndex(this.txtPassword, 0);
            this.Controls.SetChildIndex(this.btnCancel, 0);
            this.Controls.SetChildIndex(this.btnOK, 0);
            this.Controls.SetChildIndex(this.lblStatus, 0);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).EndInit();
            this.pnlTop.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picSetting)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picLogo)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Framework.LabelEx lblSetting;
        private Framework.PictureEx picSetting;
        private Framework.PictureEx picLogo;
        private Framework.LabelEx lblUser;
        private Framework.TextEx txtUser;
        private Framework.LabelEx lblPassword;
        private Framework.TextEx txtPassword;
        private Framework.ButtonEx btnCancel;
        private Framework.ButtonEx btnOK;
        private System.Windows.Forms.Label lblStatus;
    }
}