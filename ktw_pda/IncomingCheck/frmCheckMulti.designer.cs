namespace IncomingCheck
{
    partial class frmCheckMulti
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
            this.btnReprint = new Framework.ButtonEx();
            this.lblFirst = new System.Windows.Forms.Label();
            this.btnClear = new Framework.ButtonEx();
            this.btnOk = new Framework.ButtonEx();
            this.txtBl = new Framework.NumericText();
            this.txtPs = new Framework.NumericText();
            this.txtCs = new Framework.NumericText();
            this.txtExpDate = new Framework.TextEx();
            this.lblQyCount = new System.Windows.Forms.Label();
            this.lblStockInfo = new System.Windows.Forms.Label();
            this.lblPsUnit = new System.Windows.Forms.Label();
            this.lblBlUnit = new System.Windows.Forms.Label();
            this.lblCsUnit = new System.Windows.Forms.Label();
            this.lblBlIn = new System.Windows.Forms.Label();
            this.lblCsIn = new System.Windows.Forms.Label();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.lblReceiveInfo = new System.Windows.Forms.Label();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnHome = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.txtQrCode = new Framework.TextEx();
            this.lblAsnNumber = new System.Windows.Forms.Label();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.btnReprint);
            this.pnlDetail.Controls.Add(this.lblFirst);
            this.pnlDetail.Controls.Add(this.btnClear);
            this.pnlDetail.Controls.Add(this.btnOk);
            this.pnlDetail.Controls.Add(this.txtBl);
            this.pnlDetail.Controls.Add(this.txtPs);
            this.pnlDetail.Controls.Add(this.txtCs);
            this.pnlDetail.Controls.Add(this.txtExpDate);
            this.pnlDetail.Controls.Add(this.lblQyCount);
            this.pnlDetail.Controls.Add(this.lblStockInfo);
            this.pnlDetail.Controls.Add(this.lblPsUnit);
            this.pnlDetail.Controls.Add(this.lblBlUnit);
            this.pnlDetail.Controls.Add(this.lblCsUnit);
            this.pnlDetail.Controls.Add(this.lblBlIn);
            this.pnlDetail.Controls.Add(this.lblCsIn);
            this.pnlDetail.Controls.Add(this.lblSkuName);
            this.pnlDetail.Controls.Add(this.lblReceiveInfo);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Controls.Add(this.txtQrCode);
            this.pnlDetail.Controls.Add(this.lblAsnNumber);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // btnReprint
            // 
            this.btnReprint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnReprint.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(152)))), ((int)(((byte)(0)))));
            this.btnReprint.BackgroundImage = null;
            this.btnReprint.BorderColor = System.Drawing.Color.Transparent;
            this.btnReprint.CommitKey = null;
            this.btnReprint.ForeColor = System.Drawing.Color.White;
            this.btnReprint.HotKey = "keyf2";
            this.btnReprint.ImageName = "";
            this.btnReprint.Location = new System.Drawing.Point(72, 268);
            this.btnReprint.Name = "btnReprint";
            this.btnReprint.RoundButton = true;
            this.btnReprint.Size = new System.Drawing.Size(93, 27);
            this.btnReprint.TabIndex = 68;
            this.btnReprint.Text = "F2 荷札再発行";
            this.btnReprint.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnReprint.Click += new System.EventHandler(this.btnReprint_Click);
            // 
            // lblFirst
            // 
            this.lblFirst.Font = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Regular);
            this.lblFirst.ForeColor = System.Drawing.Color.Red;
            this.lblFirst.Location = new System.Drawing.Point(15, 220);
            this.lblFirst.Name = "lblFirst";
            this.lblFirst.Size = new System.Drawing.Size(213, 33);
            this.lblFirst.Text = "＊この商品は初入荷です。ご注意ください。";
            this.lblFirst.Visible = false;
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
            this.btnClear.Size = new System.Drawing.Size(74, 27);
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
            this.btnOk.Location = new System.Drawing.Point(163, 268);
            this.btnOk.Name = "btnOk";
            this.btnOk.RoundButton = true;
            this.btnOk.Size = new System.Drawing.Size(76, 27);
            this.btnOk.TabIndex = 55;
            this.btnOk.Text = "Enter 確定";
            this.btnOk.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnOk.Click += new System.EventHandler(this.enter_Click);
            // 
            // txtBl
            // 
            this.txtBl.Location = new System.Drawing.Point(80, 156);
            this.txtBl.Name = "txtBl";
            this.txtBl.ReadOnly = true;
            this.txtBl.Size = new System.Drawing.Size(38, 23);
            this.txtBl.TabIndex = 3;
            this.txtBl.Tip = "BL数";
            this.txtBl.TextChanged += new System.EventHandler(this.excute_count);
            // 
            // txtPs
            // 
            this.txtPs.Location = new System.Drawing.Point(158, 156);
            this.txtPs.Name = "txtPs";
            this.txtPs.Size = new System.Drawing.Size(38, 23);
            this.txtPs.TabIndex = 4;
            this.txtPs.Tip = "PS数";
            this.txtPs.TextChanged += new System.EventHandler(this.excute_count);
            // 
            // txtCs
            // 
            this.txtCs.Location = new System.Drawing.Point(7, 156);
            this.txtCs.Name = "txtCs";
            this.txtCs.ReadOnly = true;
            this.txtCs.Size = new System.Drawing.Size(38, 23);
            this.txtCs.TabIndex = 2;
            this.txtCs.Tip = "CS数";
            this.txtCs.TextChanged += new System.EventHandler(this.excute_count);
            // 
            // txtExpDate
            // 
            this.txtExpDate.BorderColor = System.Drawing.Color.Empty;
            this.txtExpDate.CommitKey = null;
            this.txtExpDate.Field = null;
            this.txtExpDate.Location = new System.Drawing.Point(122, 186);
            this.txtExpDate.Name = "txtExpDate";
            this.txtExpDate.Pattern = null;
            this.txtExpDate.Size = new System.Drawing.Size(109, 23);
            this.txtExpDate.TabIndex = 5;
            this.txtExpDate.Tip = "賞味期限入力";
            this.txtExpDate.ValidsCommit = null;
            this.txtExpDate.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtDescBinCode_KeyDown);
            // 
            // lblQyCount
            // 
            this.lblQyCount.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblQyCount.Location = new System.Drawing.Point(7, 186);
            this.lblQyCount.Name = "lblQyCount";
            this.lblQyCount.Size = new System.Drawing.Size(109, 23);
            // 
            // lblStockInfo
            // 
            this.lblStockInfo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblStockInfo.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Bold);
            this.lblStockInfo.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblStockInfo.Location = new System.Drawing.Point(121, 65);
            this.lblStockInfo.Name = "lblStockInfo";
            this.lblStockInfo.Size = new System.Drawing.Size(109, 23);
            // 
            // lblPsUnit
            // 
            this.lblPsUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblPsUnit.Location = new System.Drawing.Point(202, 156);
            this.lblPsUnit.Name = "lblPsUnit";
            this.lblPsUnit.Size = new System.Drawing.Size(28, 23);
            // 
            // lblBlUnit
            // 
            this.lblBlUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblBlUnit.Location = new System.Drawing.Point(124, 156);
            this.lblBlUnit.Name = "lblBlUnit";
            this.lblBlUnit.Size = new System.Drawing.Size(28, 23);
            // 
            // lblCsUnit
            // 
            this.lblCsUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblCsUnit.Location = new System.Drawing.Point(46, 156);
            this.lblCsUnit.Name = "lblCsUnit";
            this.lblCsUnit.Size = new System.Drawing.Size(28, 23);
            // 
            // lblBlIn
            // 
            this.lblBlIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblBlIn.Location = new System.Drawing.Point(121, 126);
            this.lblBlIn.Name = "lblBlIn";
            this.lblBlIn.Size = new System.Drawing.Size(109, 23);
            // 
            // lblCsIn
            // 
            this.lblCsIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblCsIn.Location = new System.Drawing.Point(7, 126);
            this.lblCsIn.Name = "lblCsIn";
            this.lblCsIn.Size = new System.Drawing.Size(109, 23);
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuName.Location = new System.Drawing.Point(6, 95);
            this.lblSkuName.Name = "lblSkuName";
            this.lblSkuName.Size = new System.Drawing.Size(224, 23);
            // 
            // lblReceiveInfo
            // 
            this.lblReceiveInfo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblReceiveInfo.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Bold);
            this.lblReceiveInfo.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblReceiveInfo.Location = new System.Drawing.Point(121, 35);
            this.lblReceiveInfo.Name = "lblReceiveInfo";
            this.lblReceiveInfo.Size = new System.Drawing.Size(109, 23);
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
            this.picHead.TitleText = "入荷検品";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // txtQrCode
            // 
            this.txtQrCode.BorderColor = System.Drawing.Color.Empty;
            this.txtQrCode.CommitKey = null;
            this.txtQrCode.DoSelectNext = false;
            this.txtQrCode.Field = null;
            this.txtQrCode.Location = new System.Drawing.Point(7, 64);
            this.txtQrCode.Name = "txtQrCode";
            this.txtQrCode.Pattern = null;
            this.txtQrCode.Size = new System.Drawing.Size(109, 23);
            this.txtQrCode.TabIndex = 1;
            this.txtQrCode.Tip = "商品バーコード入力";
            this.txtQrCode.ValidsCommit = null;
            this.txtQrCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtQrCode_KeyDown);
            // 
            // lblAsnNumber
            // 
            this.lblAsnNumber.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblAsnNumber.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblAsnNumber.Location = new System.Drawing.Point(6, 35);
            this.lblAsnNumber.Name = "lblAsnNumber";
            this.lblAsnNumber.Size = new System.Drawing.Size(109, 23);
            // 
            // frmCheckMulti
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmCheckMulti";
            this.Text = "";
            this.TextChanged += new System.EventHandler(this.excute_count);
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
        private System.Windows.Forms.Label lblReceiveInfo;
        private System.Windows.Forms.Label lblPsUnit;
        private System.Windows.Forms.Label lblCsUnit;
        private System.Windows.Forms.Label lblSkuName;
        private System.Windows.Forms.Label lblBlUnit;
        private System.Windows.Forms.Label lblStockInfo;
        private Framework.TextEx txtQrCode;
        private Framework.TextEx txtExpDate;
        private System.Windows.Forms.Label lblQyCount;
        private System.Windows.Forms.Label lblBlIn;
        private System.Windows.Forms.Label lblCsIn;
        private Framework.NumericText txtBl;
        private Framework.NumericText txtPs;
        private Framework.NumericText txtCs;
        private Framework.ButtonEx btnClear;
        private Framework.ButtonEx btnOk;
        private System.Windows.Forms.Label lblFirst;
        private Framework.ButtonEx btnReprint;
        private System.Windows.Forms.Label lblAsnNumber;

    }
}
