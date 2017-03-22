namespace Pick
{
    partial class frmCsPick
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
            this.lblInfo = new System.Windows.Forms.Label();
            this.txtCaseNumber3 = new Framework.TextEx();
            this.lblCaseCount = new System.Windows.Forms.Label();
            this.BtnChange = new Framework.ButtonEx();
            this.BtnClear = new Framework.ButtonEx();
            this.BtnOk = new Framework.ButtonEx();
            this.txtCaseNumber1 = new Framework.TextEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnHome = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.pnlInfo = new System.Windows.Forms.Panel();
            this.txtQty = new Framework.NumericText();
            this.lblScanQty = new System.Windows.Forms.Label();
            this.txtBinCode = new Framework.TextEx();
            this.lblBinCode = new System.Windows.Forms.Label();
            this.txtBarcode = new Framework.TextEx();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.lblSpecs = new System.Windows.Forms.Label();
            this.lblExpDate = new System.Windows.Forms.Label();
            this.lblUnit = new System.Windows.Forms.Label();
            this.lblQty = new System.Windows.Forms.Label();
            this.lblSkuCode = new System.Windows.Forms.Label();
            this.txtCaseNumber2 = new Framework.TextEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.pnlInfo.SuspendLayout();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.lblInfo);
            this.pnlDetail.Controls.Add(this.txtCaseNumber3);
            this.pnlDetail.Controls.Add(this.lblCaseCount);
            this.pnlDetail.Controls.Add(this.BtnChange);
            this.pnlDetail.Controls.Add(this.BtnClear);
            this.pnlDetail.Controls.Add(this.BtnOk);
            this.pnlDetail.Controls.Add(this.txtCaseNumber1);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Controls.Add(this.pnlInfo);
            this.pnlDetail.Controls.Add(this.txtCaseNumber2);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // lblInfo
            // 
            this.lblInfo.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Bold);
            this.lblInfo.ForeColor = System.Drawing.Color.Red;
            this.lblInfo.Location = new System.Drawing.Point(3, 36);
            this.lblInfo.Name = "lblInfo";
            this.lblInfo.Size = new System.Drawing.Size(237, 20);
            // 
            // txtCaseNumber3
            // 
            this.txtCaseNumber3.BorderColor = System.Drawing.Color.Empty;
            this.txtCaseNumber3.CommitKey = null;
            this.txtCaseNumber3.Field = null;
            this.txtCaseNumber3.Location = new System.Drawing.Point(6, 63);
            this.txtCaseNumber3.Name = "txtCaseNumber3";
            this.txtCaseNumber3.Pattern = null;
            this.txtCaseNumber3.Size = new System.Drawing.Size(145, 23);
            this.txtCaseNumber3.TabIndex = 1;
            this.txtCaseNumber3.Tip = "個口番号入力";
            this.txtCaseNumber3.ValidsCommit = null;
            this.txtCaseNumber3.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCaseNumber3_KeyDown);
            // 
            // lblCaseCount
            // 
            this.lblCaseCount.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblCaseCount.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Bold);
            this.lblCaseCount.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblCaseCount.Location = new System.Drawing.Point(157, 63);
            this.lblCaseCount.Name = "lblCaseCount";
            this.lblCaseCount.Size = new System.Drawing.Size(72, 50);
            this.lblCaseCount.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // BtnChange
            // 
            this.BtnChange.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.BtnChange.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(152)))), ((int)(((byte)(0)))));
            this.BtnChange.BackgroundImage = null;
            this.BtnChange.BorderColor = System.Drawing.Color.Transparent;
            this.BtnChange.CommitKey = null;
            this.BtnChange.ForeColor = System.Drawing.Color.White;
            this.BtnChange.HotKey = "keyf2";
            this.BtnChange.ImageName = "";
            this.BtnChange.Location = new System.Drawing.Point(71, 268);
            this.BtnChange.Name = "BtnChange";
            this.BtnChange.RoundButton = true;
            this.BtnChange.Size = new System.Drawing.Size(71, 27);
            this.BtnChange.TabIndex = 69;
            this.BtnChange.Text = "F2 マルチ";
            this.BtnChange.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.BtnChange.Click += new System.EventHandler(this.BtnChange_Click);
            // 
            // BtnClear
            // 
            this.BtnClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.BtnClear.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(51)))), ((int)(((byte)(0)))));
            this.BtnClear.BackgroundImage = null;
            this.BtnClear.BorderColor = System.Drawing.Color.Transparent;
            this.BtnClear.CommitKey = null;
            this.BtnClear.ForeColor = System.Drawing.Color.White;
            this.BtnClear.HotKey = "keyf1";
            this.BtnClear.ImageName = "";
            this.BtnClear.Location = new System.Drawing.Point(0, 268);
            this.BtnClear.Name = "BtnClear";
            this.BtnClear.RoundButton = true;
            this.BtnClear.Size = new System.Drawing.Size(71, 27);
            this.BtnClear.TabIndex = 56;
            this.BtnClear.Text = "F1 クリア";
            this.BtnClear.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.BtnClear.Click += new System.EventHandler(this.BtnClear_Click);
            // 
            // BtnOk
            // 
            this.BtnOk.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.BtnOk.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.BtnOk.BackgroundImage = null;
            this.BtnOk.BorderColor = System.Drawing.Color.Transparent;
            this.BtnOk.CommitKey = null;
            this.BtnOk.ForeColor = System.Drawing.Color.White;
            this.BtnOk.HotKey = null;
            this.BtnOk.ImageName = "";
            this.BtnOk.Location = new System.Drawing.Point(142, 268);
            this.BtnOk.Name = "BtnOk";
            this.BtnOk.RoundButton = true;
            this.BtnOk.Size = new System.Drawing.Size(96, 27);
            this.BtnOk.TabIndex = 55;
            this.BtnOk.Text = "Enter 確定";
            this.BtnOk.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.BtnOk.Click += new System.EventHandler(this.BtnOk_Click);
            // 
            // txtCaseNumber1
            // 
            this.txtCaseNumber1.BorderColor = System.Drawing.Color.Empty;
            this.txtCaseNumber1.CommitKey = null;
            this.txtCaseNumber1.Field = null;
            this.txtCaseNumber1.Location = new System.Drawing.Point(6, 63);
            this.txtCaseNumber1.Name = "txtCaseNumber1";
            this.txtCaseNumber1.Pattern = null;
            this.txtCaseNumber1.Size = new System.Drawing.Size(145, 23);
            this.txtCaseNumber1.TabIndex = 2;
            this.txtCaseNumber1.Tip = "個口番号入力";
            this.txtCaseNumber1.ValidsCommit = null;
            this.txtCaseNumber1.Visible = false;
            this.txtCaseNumber1.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCaseNumber1_KeyDown);
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
            this.picHead.TitleText = "在庫照会・そのた";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // pnlInfo
            // 
            this.pnlInfo.BackColor = System.Drawing.Color.White;
            this.pnlInfo.Controls.Add(this.txtQty);
            this.pnlInfo.Controls.Add(this.lblScanQty);
            this.pnlInfo.Controls.Add(this.txtBinCode);
            this.pnlInfo.Controls.Add(this.lblBinCode);
            this.pnlInfo.Controls.Add(this.txtBarcode);
            this.pnlInfo.Controls.Add(this.lblSkuName);
            this.pnlInfo.Controls.Add(this.lblSpecs);
            this.pnlInfo.Controls.Add(this.lblExpDate);
            this.pnlInfo.Controls.Add(this.lblUnit);
            this.pnlInfo.Controls.Add(this.lblQty);
            this.pnlInfo.Controls.Add(this.lblSkuCode);
            this.pnlInfo.Location = new System.Drawing.Point(3, 117);
            this.pnlInfo.Name = "pnlInfo";
            this.pnlInfo.Size = new System.Drawing.Size(234, 145);
            // 
            // txtQty
            // 
            this.txtQty.Location = new System.Drawing.Point(177, 114);
            this.txtQty.Name = "txtQty";
            this.txtQty.Size = new System.Drawing.Size(49, 23);
            this.txtQty.TabIndex = 6;
            this.txtQty.Tip = "数量";
            this.txtQty.Visible = false;
            this.txtQty.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtQty_KeyDown);
            // 
            // lblScanQty
            // 
            this.lblScanQty.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Bold);
            this.lblScanQty.Location = new System.Drawing.Point(202, 114);
            this.lblScanQty.Name = "lblScanQty";
            this.lblScanQty.Size = new System.Drawing.Size(24, 22);
            // 
            // txtBinCode
            // 
            this.txtBinCode.BorderColor = System.Drawing.Color.Empty;
            this.txtBinCode.CommitKey = null;
            this.txtBinCode.Field = null;
            this.txtBinCode.Location = new System.Drawing.Point(2, 114);
            this.txtBinCode.Name = "txtBinCode";
            this.txtBinCode.Pattern = null;
            this.txtBinCode.Size = new System.Drawing.Size(69, 23);
            this.txtBinCode.TabIndex = 4;
            this.txtBinCode.Tip = "棚番";
            this.txtBinCode.ValidsCommit = null;
            this.txtBinCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBinCode_KeyDown);
            // 
            // lblBinCode
            // 
            this.lblBinCode.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblBinCode.Font = new System.Drawing.Font("Tahoma", 14F, System.Drawing.FontStyle.Bold);
            this.lblBinCode.ForeColor = System.Drawing.Color.Red;
            this.lblBinCode.Location = new System.Drawing.Point(3, 2);
            this.lblBinCode.Name = "lblBinCode";
            this.lblBinCode.Size = new System.Drawing.Size(146, 23);
            // 
            // txtBarcode
            // 
            this.txtBarcode.BorderColor = System.Drawing.Color.Empty;
            this.txtBarcode.CommitKey = null;
            this.txtBarcode.DoSelectNext = false;
            this.txtBarcode.Field = null;
            this.txtBarcode.Location = new System.Drawing.Point(77, 114);
            this.txtBarcode.Name = "txtBarcode";
            this.txtBarcode.Pattern = null;
            this.txtBarcode.Size = new System.Drawing.Size(119, 23);
            this.txtBarcode.TabIndex = 85;
            this.txtBarcode.Tip = "商品バーコード入力";
            this.txtBarcode.ValidsCommit = null;
            this.txtBarcode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBarcode_KeyDown);
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuName.Location = new System.Drawing.Point(2, 30);
            this.lblSkuName.Name = "lblSkuName";
            this.lblSkuName.Size = new System.Drawing.Size(224, 23);
            // 
            // lblSpecs
            // 
            this.lblSpecs.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSpecs.Location = new System.Drawing.Point(2, 86);
            this.lblSpecs.Name = "lblSpecs";
            this.lblSpecs.Size = new System.Drawing.Size(146, 23);
            // 
            // lblExpDate
            // 
            this.lblExpDate.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblExpDate.Location = new System.Drawing.Point(3, 58);
            this.lblExpDate.Name = "lblExpDate";
            this.lblExpDate.Size = new System.Drawing.Size(145, 23);
            // 
            // lblUnit
            // 
            this.lblUnit.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblUnit.Font = new System.Drawing.Font("Tahoma", 16F, System.Drawing.FontStyle.Bold);
            this.lblUnit.ForeColor = System.Drawing.Color.Red;
            this.lblUnit.Location = new System.Drawing.Point(154, 58);
            this.lblUnit.Name = "lblUnit";
            this.lblUnit.Size = new System.Drawing.Size(72, 50);
            // 
            // lblQty
            // 
            this.lblQty.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblQty.Font = new System.Drawing.Font("Tahoma", 11F, System.Drawing.FontStyle.Bold);
            this.lblQty.ForeColor = System.Drawing.Color.Red;
            this.lblQty.Location = new System.Drawing.Point(108, 58);
            this.lblQty.Name = "lblQty";
            this.lblQty.Size = new System.Drawing.Size(40, 23);
            this.lblQty.Visible = false;
            // 
            // lblSkuCode
            // 
            this.lblSkuCode.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuCode.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblSkuCode.Location = new System.Drawing.Point(154, 2);
            this.lblSkuCode.Name = "lblSkuCode";
            this.lblSkuCode.Size = new System.Drawing.Size(72, 23);
            // 
            // txtCaseNumber2
            // 
            this.txtCaseNumber2.BorderColor = System.Drawing.Color.Empty;
            this.txtCaseNumber2.CommitKey = null;
            this.txtCaseNumber2.Field = null;
            this.txtCaseNumber2.Location = new System.Drawing.Point(6, 90);
            this.txtCaseNumber2.Name = "txtCaseNumber2";
            this.txtCaseNumber2.Pattern = null;
            this.txtCaseNumber2.Size = new System.Drawing.Size(145, 23);
            this.txtCaseNumber2.TabIndex = 3;
            this.txtCaseNumber2.Tip = "個口番号入力";
            this.txtCaseNumber2.ValidsCommit = null;
            this.txtCaseNumber2.Visible = false;
            this.txtCaseNumber2.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCaseNumber2_KeyDown);
            // 
            // frmCsPick
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmCsPick";
            this.Text = "";
            this.Load += new System.EventHandler(this.frmCsPick_Load);
            this.Closed += new System.EventHandler(this.frmCsPick_Closed);
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.pnlInfo.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.ButtonEx btnHome;
        private Framework.PictureEx picHead;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.TextEx txtCaseNumber1;
        private System.Windows.Forms.Label lblSpecs;
        private Framework.TextEx txtCaseNumber2;
        private Framework.NumericText txtQty;
        private Framework.ButtonEx BtnClear;
        private Framework.ButtonEx BtnOk;
        private Framework.ButtonEx BtnChange;
        private System.Windows.Forms.Label lblCaseCount;
        private Framework.TextEx txtCaseNumber3;
        private Framework.TextEx txtBinCode;
        private Framework.TextEx txtBarcode;
        private System.Windows.Forms.Label lblUnit;
        private System.Windows.Forms.Label lblSkuCode;
        private System.Windows.Forms.Label lblQty;
        private System.Windows.Forms.Label lblExpDate;
        private System.Windows.Forms.Label lblSkuName;
        private System.Windows.Forms.Label lblBinCode;
        private System.Windows.Forms.Panel pnlInfo;
        private System.Windows.Forms.Label lblInfo;
        private System.Windows.Forms.Label lblScanQty;

    }
}
