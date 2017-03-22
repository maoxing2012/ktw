namespace WMSPAD
{
    partial class frmSetting
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
            this.btnOK = new Framework.ButtonEx();
            this.btnClear = new Framework.ButtonEx();
            this.txtServer = new Framework.TextEx();
            this.lblWeb = new Framework.LabelEx();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).BeginInit();
            this.pnlTop.SuspendLayout();
            this.SuspendLayout();
            // 
            // lblTitle
            // 
            this.lblTitle.Text = "設定";
            // 
            // btnRight
            // 
            this.btnRight.Visible = false;
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
            this.btnOK.Location = new System.Drawing.Point(300, 450);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(300, 50);
            this.btnOK.TabIndex = 2;
            this.btnOK.Text = "Enter 確定";
            this.btnOK.UseVisualStyleBackColor = false;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // btnClear
            // 
            this.btnClear.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(48)))), ((int)(((byte)(0)))));
            this.btnClear.CommitKey = "";
            this.btnClear.FlatAppearance.BorderSize = 0;
            this.btnClear.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnClear.Font = new System.Drawing.Font("MS UI Gothic", 20.25F);
            this.btnClear.HotKey = "keyf1";
            this.btnClear.ImageName = "skip";
            this.btnClear.Location = new System.Drawing.Point(0, 450);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(300, 50);
            this.btnClear.TabIndex = 1;
            this.btnClear.Text = "F1 クリア";
            this.btnClear.UseVisualStyleBackColor = false;
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtServer
            // 
            this.txtServer.BorderColor = System.Drawing.Color.Empty;
            this.txtServer.CommitKey = null;
            this.txtServer.Field = null;
            this.txtServer.Font = new System.Drawing.Font("Tahoma", 20F);
            this.txtServer.Location = new System.Drawing.Point(260, 81);
            this.txtServer.Name = "txtServer";
            this.txtServer.Pattern = null;
            this.txtServer.Size = new System.Drawing.Size(311, 40);
            this.txtServer.TabIndex = 0;
            this.txtServer.Text = "192.168.1.1:8080";
            this.txtServer.Tip = null;
            this.txtServer.ValidsCommit = null;
            this.txtServer.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtServer_KeyDown);
            // 
            // lblWeb
            // 
            this.lblWeb.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblWeb.Location = new System.Drawing.Point(30, 81);
            this.lblWeb.Name = "lblWeb";
            this.lblWeb.Size = new System.Drawing.Size(233, 40);
            this.lblWeb.TabIndex = 8;
            this.lblWeb.Text = "サーバのアドレス ";
            // 
            // frmSetting
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(600, 500);
            this.Controls.Add(this.txtServer);
            this.Controls.Add(this.lblWeb);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.btnClear);
            this.Name = "frmSetting";
            this.ShowInTaskbar = false;
            this.Text = "frmSetting";
            this.Load += new System.EventHandler(this.frmSetting_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.frmSetting_KeyDown);
            this.Controls.SetChildIndex(this.pnlTop, 0);
            this.Controls.SetChildIndex(this.btnClear, 0);
            this.Controls.SetChildIndex(this.btnOK, 0);
            this.Controls.SetChildIndex(this.lblWeb, 0);
            this.Controls.SetChildIndex(this.txtServer, 0);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).EndInit();
            this.pnlTop.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Framework.ButtonEx btnOK;
        private Framework.ButtonEx btnClear;
        private Framework.TextEx txtServer;
        private Framework.LabelEx lblWeb;
    }
}