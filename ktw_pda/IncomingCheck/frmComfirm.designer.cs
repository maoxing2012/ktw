namespace IncomingCheck
{
    partial class frmComfirm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmComfirm));
            this.pnlDetail = new Framework.AlphaPanel();
            this.txtCode = new Framework.NumericText();
            this.exit = new Framework.ButtonEx();
            this.Ok = new Framework.ButtonEx();
            this.lbl = new System.Windows.Forms.Label();
            this.pnlDetail.SuspendLayout();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.LightGray;
            this.pnlDetail.Controls.Add(this.txtCode);
            this.pnlDetail.Controls.Add(this.exit);
            this.pnlDetail.Controls.Add(this.Ok);
            this.pnlDetail.Controls.Add(this.lbl);
            resources.ApplyResources(this.pnlDetail, "pnlDetail");
            this.pnlDetail.Name = "pnlDetail";
            // 
            // txtCode
            // 
            this.txtCode.BorderColor = System.Drawing.Color.Empty;
            this.txtCode.CanNegative = false;
            this.txtCode.CommitKey = null;
            this.txtCode.Field = "caseNumber";
            resources.ApplyResources(this.txtCode, "txtCode");
            this.txtCode.MustInput = true;
            this.txtCode.Name = "txtCode";
            this.txtCode.Pattern = "";
            this.txtCode.Tip = "個口番号入力";
            this.txtCode.ValidsCommit = null;
            this.txtCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCode_KeyDown);
            // 
            // exit
            // 
            resources.ApplyResources(this.exit, "exit");
            this.exit.BackColor = System.Drawing.Color.Silver;
            this.exit.BackgroundImage = null;
            this.exit.BorderColor = System.Drawing.Color.Transparent;
            this.exit.CommitKey = null;
            this.exit.ForeColor = System.Drawing.Color.Black;
            this.exit.HotKey = "keyf1";
            this.exit.ImageName = "WMSPDA.Images.button2.png";
            this.exit.Name = "exit";
            this.exit.RoundButton = true;
            this.exit.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.exit.Click += new System.EventHandler(this.exit_Click);
            // 
            // Ok
            // 
            resources.ApplyResources(this.Ok, "Ok");
            this.Ok.BackColor = System.Drawing.Color.Silver;
            this.Ok.BackgroundImage = null;
            this.Ok.BorderColor = System.Drawing.Color.Transparent;
            this.Ok.CommitKey = null;
            this.Ok.ForeColor = System.Drawing.Color.Black;
            this.Ok.HotKey = null;
            this.Ok.ImageName = "WMSPDA.Images.button1.png";
            this.Ok.Name = "Ok";
            this.Ok.RoundButton = true;
            this.Ok.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.Ok.Click += new System.EventHandler(this.Ok_Click);
            // 
            // lbl
            // 
            resources.ApplyResources(this.lbl, "lbl");
            this.lbl.Name = "lbl";
            // 
            // frmComfirm
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            resources.ApplyResources(this, "$this");
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmComfirm";
            this.pnlDetail.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private System.Windows.Forms.Label lbl;
        private Framework.ButtonEx exit;
        private Framework.ButtonEx Ok;
        private Framework.NumericText txtCode;

    }
}
