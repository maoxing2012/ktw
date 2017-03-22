namespace Pick
{
    partial class frmBatchPick
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
            this.txtBl = new Framework.NumericText();
            this.txtPs = new Framework.NumericText();
            this.txtCs = new Framework.NumericText();
            this.lblPsUnit = new System.Windows.Forms.Label();
            this.lblBlUnit = new System.Windows.Forms.Label();
            this.lblCsUnit = new System.Windows.Forms.Label();
            this.btnClear = new Framework.ButtonEx();
            this.btnOk = new Framework.ButtonEx();
            this.lblQtyInfo = new System.Windows.Forms.Label();
            this.lblTempDivNm = new System.Windows.Forms.Label();
            this.lblSkuCode = new System.Windows.Forms.Label();
            this.lblSpecs = new System.Windows.Forms.Label();
            this.lblExpDate = new System.Windows.Forms.Label();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.lblPickInfo = new System.Windows.Forms.Label();
            this.txtWaveNumber = new Framework.TextEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnHome = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.txtBinCode = new Framework.TextEx();
            this.txtQrCode = new Framework.TextEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.txtBl);
            this.pnlDetail.Controls.Add(this.txtPs);
            this.pnlDetail.Controls.Add(this.txtCs);
            this.pnlDetail.Controls.Add(this.lblPsUnit);
            this.pnlDetail.Controls.Add(this.lblBlUnit);
            this.pnlDetail.Controls.Add(this.lblCsUnit);
            this.pnlDetail.Controls.Add(this.btnClear);
            this.pnlDetail.Controls.Add(this.btnOk);
            this.pnlDetail.Controls.Add(this.lblQtyInfo);
            this.pnlDetail.Controls.Add(this.lblTempDivNm);
            this.pnlDetail.Controls.Add(this.lblSkuCode);
            this.pnlDetail.Controls.Add(this.lblSpecs);
            this.pnlDetail.Controls.Add(this.lblExpDate);
            this.pnlDetail.Controls.Add(this.lblSkuName);
            this.pnlDetail.Controls.Add(this.lblPickInfo);
            this.pnlDetail.Controls.Add(this.txtWaveNumber);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Controls.Add(this.txtBinCode);
            this.pnlDetail.Controls.Add(this.txtQrCode);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // txtBl
            // 
            this.txtBl.Location = new System.Drawing.Point(80, 210);
            this.txtBl.Name = "txtBl";
            this.txtBl.ReadOnly = true;
            this.txtBl.Size = new System.Drawing.Size(38, 23);
            this.txtBl.TabIndex = 4;
            this.txtBl.Tip = "BL数";
            // 
            // txtPs
            // 
            this.txtPs.DoSelectNext = false;
            this.txtPs.Location = new System.Drawing.Point(158, 210);
            this.txtPs.Name = "txtPs";
            this.txtPs.Size = new System.Drawing.Size(38, 23);
            this.txtPs.TabIndex = 5;
            this.txtPs.Tip = "PS数";
            this.txtPs.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtPs_KeyDown);
            // 
            // txtCs
            // 
            this.txtCs.Location = new System.Drawing.Point(7, 210);
            this.txtCs.Name = "txtCs";
            this.txtCs.ReadOnly = true;
            this.txtCs.Size = new System.Drawing.Size(38, 23);
            this.txtCs.TabIndex = 3;
            this.txtCs.Tip = "CS数";
            // 
            // lblPsUnit
            // 
            this.lblPsUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblPsUnit.Location = new System.Drawing.Point(202, 210);
            this.lblPsUnit.Name = "lblPsUnit";
            this.lblPsUnit.Size = new System.Drawing.Size(28, 23);
            // 
            // lblBlUnit
            // 
            this.lblBlUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblBlUnit.Location = new System.Drawing.Point(124, 210);
            this.lblBlUnit.Name = "lblBlUnit";
            this.lblBlUnit.Size = new System.Drawing.Size(28, 23);
            // 
            // lblCsUnit
            // 
            this.lblCsUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblCsUnit.Location = new System.Drawing.Point(46, 210);
            this.lblCsUnit.Name = "lblCsUnit";
            this.lblCsUnit.Size = new System.Drawing.Size(28, 23);
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
            this.btnClear.Location = new System.Drawing.Point(0, 268);
            this.btnClear.Name = "btnClear";
            this.btnClear.RoundButton = true;
            this.btnClear.Size = new System.Drawing.Size(98, 27);
            this.btnClear.TabIndex = 56;
            this.btnClear.Text = "F1 クリア";
            this.btnClear.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnClear.Click += new System.EventHandler(this.F1_Click);
            // 
            // btnOk
            // 
            this.btnOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnOk.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.btnOk.BackgroundImage = null;
            this.btnOk.BorderColor = System.Drawing.Color.Transparent;
            this.btnOk.CommitKey = null;
            this.btnOk.ForeColor = System.Drawing.Color.White;
            this.btnOk.HotKey = null;
            this.btnOk.ImageName = "";
            this.btnOk.Location = new System.Drawing.Point(97, 268);
            this.btnOk.Name = "btnOk";
            this.btnOk.RoundButton = true;
            this.btnOk.Size = new System.Drawing.Size(142, 27);
            this.btnOk.TabIndex = 55;
            this.btnOk.Text = "Enter 確定";
            this.btnOk.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnOk.Click += new System.EventHandler(this.enter_Click);
            // 
            // lblQtyInfo
            // 
            this.lblQtyInfo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblQtyInfo.Location = new System.Drawing.Point(7, 180);
            this.lblQtyInfo.Name = "lblQtyInfo";
            this.lblQtyInfo.Size = new System.Drawing.Size(223, 23);
            // 
            // lblTempDivNm
            // 
            this.lblTempDivNm.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblTempDivNm.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblTempDivNm.Location = new System.Drawing.Point(158, 64);
            this.lblTempDivNm.Name = "lblTempDivNm";
            this.lblTempDivNm.Size = new System.Drawing.Size(72, 23);
            // 
            // lblSkuCode
            // 
            this.lblSkuCode.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuCode.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblSkuCode.Location = new System.Drawing.Point(158, 93);
            this.lblSkuCode.Name = "lblSkuCode";
            this.lblSkuCode.Size = new System.Drawing.Size(72, 23);
            // 
            // lblSpecs
            // 
            this.lblSpecs.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSpecs.Location = new System.Drawing.Point(90, 151);
            this.lblSpecs.Name = "lblSpecs";
            this.lblSpecs.Size = new System.Drawing.Size(140, 23);
            // 
            // lblExpDate
            // 
            this.lblExpDate.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblExpDate.Location = new System.Drawing.Point(7, 151);
            this.lblExpDate.Name = "lblExpDate";
            this.lblExpDate.Size = new System.Drawing.Size(77, 23);
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuName.Location = new System.Drawing.Point(6, 122);
            this.lblSkuName.Name = "lblSkuName";
            this.lblSkuName.Size = new System.Drawing.Size(224, 23);
            // 
            // lblPickInfo
            // 
            this.lblPickInfo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblPickInfo.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblPickInfo.Location = new System.Drawing.Point(158, 35);
            this.lblPickInfo.Name = "lblPickInfo";
            this.lblPickInfo.Size = new System.Drawing.Size(72, 23);
            // 
            // txtWaveNumber
            // 
            this.txtWaveNumber.BorderColor = System.Drawing.Color.Empty;
            this.txtWaveNumber.CommitKey = null;
            this.txtWaveNumber.DoSelectNext = false;
            this.txtWaveNumber.Field = "waveNumber";
            this.txtWaveNumber.Location = new System.Drawing.Point(7, 35);
            this.txtWaveNumber.MustInput = true;
            this.txtWaveNumber.Name = "txtWaveNumber";
            this.txtWaveNumber.Pattern = null;
            this.txtWaveNumber.Size = new System.Drawing.Size(145, 23);
            this.txtWaveNumber.TabIndex = 0;
            this.txtWaveNumber.Tip = "バッチ番号入力";
            this.txtWaveNumber.ValidsCommit = "searchAsn";
            this.txtWaveNumber.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtWaveNumber_KeyDown);
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
            this.btnBack.TabIndex = 4;
            this.btnBack.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnHome
            // 
            this.btnHome.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnHome.BackgroundImage = null;
            this.btnHome.CommitKey = null;
            this.btnHome.HotKey = null;
            this.btnHome.ImageName = "WMSPDA.Images.home.png";
            this.btnHome.Location = new System.Drawing.Point(209, 1);
            this.btnHome.Name = "btnHome";
            this.btnHome.RoundButton = false;
            this.btnHome.Size = new System.Drawing.Size(26, 26);
            this.btnHome.TabIndex = 5;
            this.btnHome.TransparentColor = System.Drawing.Color.White;
            this.btnHome.Click += new System.EventHandler(this.btnHome_Click);
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
            this.picHead.TitleText = "大バッチピッキング";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // txtBinCode
            // 
            this.txtBinCode.BorderColor = System.Drawing.Color.Empty;
            this.txtBinCode.CommitKey = null;
            this.txtBinCode.DoSelectNext = false;
            this.txtBinCode.Field = "binCode";
            this.txtBinCode.Location = new System.Drawing.Point(7, 64);
            this.txtBinCode.MustInput = true;
            this.txtBinCode.Name = "txtBinCode";
            this.txtBinCode.Pattern = null;
            this.txtBinCode.Size = new System.Drawing.Size(145, 23);
            this.txtBinCode.TabIndex = 1;
            this.txtBinCode.Tip = "棚番バーコード入力";
            this.txtBinCode.ValidsCommit = null;
            this.txtBinCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBinCode_KeyDown);
            // 
            // txtQrCode
            // 
            this.txtQrCode.BorderColor = System.Drawing.Color.Empty;
            this.txtQrCode.CommitKey = null;
            this.txtQrCode.DoSelectNext = false;
            this.txtQrCode.Field = null;
            this.txtQrCode.Location = new System.Drawing.Point(7, 93);
            this.txtQrCode.MustInput = true;
            this.txtQrCode.Name = "txtQrCode";
            this.txtQrCode.Pattern = null;
            this.txtQrCode.Size = new System.Drawing.Size(145, 23);
            this.txtQrCode.TabIndex = 2;
            this.txtQrCode.Tip = "商品バーコード入力";
            this.txtQrCode.ValidsCommit = null;
            this.txtQrCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtQrCode_KeyDown);
            // 
            // frmBatchPick
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmBatchPick";
            this.Text = "";
            this.Closed += new System.EventHandler(this.frmBatchPick_Closed);
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.ButtonEx btnHome;
        private Framework.PictureEx picHead;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.TextEx txtWaveNumber;
        private System.Windows.Forms.Label lblPickInfo;
        private System.Windows.Forms.Label lblSkuName;
        private System.Windows.Forms.Label lblSkuCode;
        private Framework.TextEx txtQrCode;
        private System.Windows.Forms.Label lblQtyInfo;
        private System.Windows.Forms.Label lblSpecs;
        private System.Windows.Forms.Label lblExpDate;
        private Framework.ButtonEx btnClear;
        private Framework.ButtonEx btnOk;
        private System.Windows.Forms.Label lblTempDivNm;
        private Framework.TextEx txtBinCode;
        private Framework.NumericText txtBl;
        private Framework.NumericText txtPs;
        private Framework.NumericText txtCs;
        private System.Windows.Forms.Label lblPsUnit;
        private System.Windows.Forms.Label lblBlUnit;
        private System.Windows.Forms.Label lblCsUnit;

    }
}
