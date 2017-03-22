namespace WMSPDA
{
    partial class frmSetting
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmSetting));
            this.pnlDetail = new Framework.AlphaPanel();
            this.btnPrint = new Framework.ButtonEx();
            this.btnClear = new Framework.ButtonEx();
            this.txtCom = new Framework.TextEx();
            this.txtServer = new Framework.TextEx();
            this.labelEx2 = new Framework.LabelEx();
            this.lblWeb = new Framework.LabelEx();
            this.btnOK = new Framework.ButtonEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnKeybord = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.txtPortNo = new Framework.TextEx();
            this.labelEx3 = new Framework.LabelEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.Controls.Add(this.btnPrint);
            this.pnlDetail.Controls.Add(this.btnClear);
            this.pnlDetail.Controls.Add(this.txtCom);
            this.pnlDetail.Controls.Add(this.txtServer);
            this.pnlDetail.Controls.Add(this.labelEx2);
            this.pnlDetail.Controls.Add(this.lblWeb);
            this.pnlDetail.Controls.Add(this.btnOK);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnKeybord);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Controls.Add(this.txtPortNo);
            this.pnlDetail.Controls.Add(this.labelEx3);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 294);
            // 
            // btnPrint
            // 
            this.btnPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnPrint.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(152)))), ((int)(((byte)(0)))));
            this.btnPrint.BackgroundImage = null;
            this.btnPrint.BorderColor = System.Drawing.Color.Transparent;
            this.btnPrint.CommitKey = null;
            this.btnPrint.ForeColor = System.Drawing.Color.White;
            this.btnPrint.HotKey = "keyf2";
            this.btnPrint.ImageName = "";
            this.btnPrint.Location = new System.Drawing.Point(79, 267);
            this.btnPrint.Name = "btnPrint";
            this.btnPrint.RoundButton = true;
            this.btnPrint.Size = new System.Drawing.Size(81, 27);
            this.btnPrint.TabIndex = 63;
            this.btnPrint.Text = "F2 印刷";
            this.btnPrint.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnPrint.Click += new System.EventHandler(this.btnPrint_Click);
            // 
            // btnClear
            // 
            this.btnClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnClear.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(51)))), ((int)(((byte)(0)))));
            this.btnClear.BackgroundImage = null;
            this.btnClear.BorderColor = System.Drawing.Color.Transparent;
            this.btnClear.CommitKey = null;
            this.btnClear.ForeColor = System.Drawing.Color.White;
            this.btnClear.HotKey = "keyf1";
            this.btnClear.ImageName = "";
            this.btnClear.Location = new System.Drawing.Point(0, 267);
            this.btnClear.Name = "btnClear";
            this.btnClear.RoundButton = true;
            this.btnClear.Size = new System.Drawing.Size(81, 27);
            this.btnClear.TabIndex = 57;
            this.btnClear.Text = "F1 クリア";
            this.btnClear.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // txtCom
            // 
            this.txtCom.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.txtCom.BorderColor = System.Drawing.Color.Empty;
            this.txtCom.CommitKey = null;
            this.txtCom.DoSelectNext = false;
            this.txtCom.Field = null;
            this.txtCom.Location = new System.Drawing.Point(114, 43);
            this.txtCom.Name = "txtCom";
            this.txtCom.Pattern = null;
            this.txtCom.Size = new System.Drawing.Size(121, 25);
            this.txtCom.TabIndex = 0;
            this.txtCom.Text = "192.168.151.24:1024";
            this.txtCom.Tip = null;
            this.txtCom.ValidsCommit = null;
            this.txtCom.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCom_KeyDown);
            // 
            // txtServer
            // 
            this.txtServer.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.txtServer.BorderColor = System.Drawing.Color.Empty;
            this.txtServer.CommitKey = null;
            this.txtServer.Field = null;
            this.txtServer.Location = new System.Drawing.Point(114, 91);
            this.txtServer.Name = "txtServer";
            this.txtServer.Pattern = null;
            this.txtServer.Size = new System.Drawing.Size(121, 25);
            this.txtServer.TabIndex = 0;
            this.txtServer.Text = "192.168.1.1:8080";
            this.txtServer.Tip = null;
            this.txtServer.ValidsCommit = null;
            this.txtServer.Visible = false;
            this.txtServer.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtServer_KeyDown);
            // 
            // labelEx2
            // 
            this.labelEx2.Border = false;
            this.labelEx2.Location = new System.Drawing.Point(4, 43);
            this.labelEx2.Name = "labelEx2";
            this.labelEx2.Size = new System.Drawing.Size(109, 27);
            this.labelEx2.Text = "プリンタ ";
            this.labelEx2.Transparent = false;
            // 
            // lblWeb
            // 
            this.lblWeb.Border = false;
            this.lblWeb.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Regular);
            this.lblWeb.Location = new System.Drawing.Point(4, 91);
            this.lblWeb.Name = "lblWeb";
            this.lblWeb.Size = new System.Drawing.Size(109, 27);
            this.lblWeb.Text = "サーバのアドレス ";
            this.lblWeb.Transparent = false;
            this.lblWeb.Visible = false;
            // 
            // btnOK
            // 
            this.btnOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnOK.BackColor = System.Drawing.Color.RoyalBlue;
            this.btnOK.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnOK.BackgroundImage")));
            this.btnOK.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.btnOK.CommitKey = null;
            this.btnOK.ForeColor = System.Drawing.Color.White;
            this.btnOK.HotKey = "";
            this.btnOK.ImageName = "";
            this.btnOK.Location = new System.Drawing.Point(158, 267);
            this.btnOK.Name = "btnOK";
            this.btnOK.RoundButton = false;
            this.btnOK.Size = new System.Drawing.Size(81, 27);
            this.btnOK.TabIndex = 2;
            this.btnOK.Text = "Enter 確定";
            this.btnOK.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // picBack
            // 
            this.picBack.ImageName = "WMSPDA.Images.back2.gif";
            this.picBack.Location = new System.Drawing.Point(10, 8);
            this.picBack.Name = "picBack";
            this.picBack.Size = new System.Drawing.Size(13, 10);
            this.picBack.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picBack.TextColor = System.Drawing.Color.Empty;
            this.picBack.TextFont = null;
            this.picBack.TitleText = null;
            this.picBack.TransparentColor = System.Drawing.Color.White;
            this.picBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnBack
            // 
            this.btnBack.BackgroundImage = null;
            this.btnBack.CommitKey = null;
            this.btnBack.HotKey = null;
            this.btnBack.ImageName = "WMSPDA.Images.back1.png";
            this.btnBack.Location = new System.Drawing.Point(3, 5);
            this.btnBack.Name = "btnBack";
            this.btnBack.RoundButton = false;
            this.btnBack.Size = new System.Drawing.Size(26, 17);
            this.btnBack.TabIndex = 3;
            this.btnBack.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnKeybord
            // 
            this.btnKeybord.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnKeybord.BackgroundImage = null;
            this.btnKeybord.CommitKey = null;
            this.btnKeybord.HotKey = null;
            this.btnKeybord.ImageName = "WMSPDA.Images.Keyboard.png";
            this.btnKeybord.Location = new System.Drawing.Point(209, 1);
            this.btnKeybord.Name = "btnKeybord";
            this.btnKeybord.RoundButton = false;
            this.btnKeybord.Size = new System.Drawing.Size(26, 26);
            this.btnKeybord.TabIndex = 6;
            this.btnKeybord.TransparentColor = System.Drawing.Color.White;
            this.btnKeybord.Click += new System.EventHandler(this.btnKeybord_Click);
            // 
            // picHead
            // 
            this.picHead.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.picHead.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.picHead.ImageName = "";
            this.picHead.Location = new System.Drawing.Point(0, 0);
            this.picHead.Name = "picHead";
            this.picHead.Size = new System.Drawing.Size(240, 29);
            this.picHead.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picHead.TextColor = System.Drawing.Color.White;
            this.picHead.TextFont = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Bold);
            this.picHead.TitleText = "設定";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // txtPortNo
            // 
            this.txtPortNo.BorderColor = System.Drawing.Color.Empty;
            this.txtPortNo.CommitKey = null;
            this.txtPortNo.Field = null;
            this.txtPortNo.Location = new System.Drawing.Point(114, 267);
            this.txtPortNo.Name = "txtPortNo";
            this.txtPortNo.Pattern = null;
            this.txtPortNo.Size = new System.Drawing.Size(203, 25);
            this.txtPortNo.TabIndex = 1;
            this.txtPortNo.Tip = null;
            this.txtPortNo.ValidsCommit = null;
            this.txtPortNo.Visible = false;
            this.txtPortNo.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCom_KeyDown);
            // 
            // labelEx3
            // 
            this.labelEx3.Border = false;
            this.labelEx3.Location = new System.Drawing.Point(4, 267);
            this.labelEx3.Name = "labelEx3";
            this.labelEx3.Size = new System.Drawing.Size(109, 27);
            this.labelEx3.Text = "控制器端口";
            this.labelEx3.Transparent = false;
            this.labelEx3.Visible = false;
            // 
            // frmSetting
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 294);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmSetting";
            this.Text = "";
            this.Load += new System.EventHandler(this.frmSetting_Load);
            this.Closed += new System.EventHandler(this.frmSetting_Closed);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.frmSetting_KeyDown);
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.ButtonEx btnKeybord;
        private Framework.PictureEx picHead;
        private Framework.ButtonEx btnOK;
        private Framework.TextEx txtCom;
        private Framework.TextEx txtServer;
        private Framework.LabelEx labelEx2;
        private Framework.LabelEx lblWeb;
        private Framework.TextEx txtPortNo;
        private Framework.LabelEx labelEx3;
        private Framework.ButtonEx btnClear;
        private Framework.ButtonEx btnPrint;
    }
}
