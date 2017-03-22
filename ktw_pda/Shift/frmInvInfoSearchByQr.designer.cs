namespace Shift
{
    partial class frmInvInfoSearchByQr
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
            this.F1 = new Framework.ButtonEx();
            this.enter = new Framework.ButtonEx();
            this.txtBl = new Framework.NumericText();
            this.txtPs = new Framework.NumericText();
            this.txtCs = new Framework.NumericText();
            this.txtDescBinCode = new Framework.TextEx();
            this.lblQyCount = new System.Windows.Forms.Label();
            this.lblExpDate = new System.Windows.Forms.Label();
            this.txtQrCode = new Framework.TextEx();
            this.lblPsUnit = new System.Windows.Forms.Label();
            this.lblBlUnit = new System.Windows.Forms.Label();
            this.lblCsUnit = new System.Windows.Forms.Label();
            this.lblBlIn = new System.Windows.Forms.Label();
            this.lblCsIn = new System.Windows.Forms.Label();
            this.lblQtyInfo = new System.Windows.Forms.Label();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.lblSkuCode = new System.Windows.Forms.Label();
            this.txtBinCode = new Framework.TextEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnHome = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.F1);
            this.pnlDetail.Controls.Add(this.enter);
            this.pnlDetail.Controls.Add(this.txtBl);
            this.pnlDetail.Controls.Add(this.txtPs);
            this.pnlDetail.Controls.Add(this.txtCs);
            this.pnlDetail.Controls.Add(this.txtDescBinCode);
            this.pnlDetail.Controls.Add(this.lblQyCount);
            this.pnlDetail.Controls.Add(this.lblExpDate);
            this.pnlDetail.Controls.Add(this.txtQrCode);
            this.pnlDetail.Controls.Add(this.lblPsUnit);
            this.pnlDetail.Controls.Add(this.lblBlUnit);
            this.pnlDetail.Controls.Add(this.lblCsUnit);
            this.pnlDetail.Controls.Add(this.lblBlIn);
            this.pnlDetail.Controls.Add(this.lblCsIn);
            this.pnlDetail.Controls.Add(this.lblQtyInfo);
            this.pnlDetail.Controls.Add(this.lblSkuName);
            this.pnlDetail.Controls.Add(this.lblSkuCode);
            this.pnlDetail.Controls.Add(this.txtBinCode);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // F1
            // 
            this.F1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.F1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(51)))), ((int)(((byte)(0)))));
            this.F1.BackgroundImage = null;
            this.F1.BorderColor = System.Drawing.Color.Transparent;
            this.F1.CommitKey = null;
            this.F1.ForeColor = System.Drawing.Color.White;
            this.F1.HotKey = "keyf1";
            this.F1.ImageName = "";
            this.F1.Location = new System.Drawing.Point(0, 268);
            this.F1.Name = "F1";
            this.F1.RoundButton = true;
            this.F1.Size = new System.Drawing.Size(101, 27);
            this.F1.TabIndex = 56;
            this.F1.Text = "F1 クリア";
            this.F1.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.F1.Click += new System.EventHandler(this.F1_Click);
            // 
            // enter
            // 
            this.enter.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.enter.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(42)))), ((int)(((byte)(144)))), ((int)(((byte)(198)))));
            this.enter.BackgroundImage = null;
            this.enter.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.enter.CommitKey = null;
            this.enter.ForeColor = System.Drawing.Color.White;
            this.enter.HotKey = null;
            this.enter.ImageName = "";
            this.enter.Location = new System.Drawing.Point(99, 268);
            this.enter.Name = "enter";
            this.enter.RoundButton = true;
            this.enter.Size = new System.Drawing.Size(139, 27);
            this.enter.TabIndex = 55;
            this.enter.Text = "Enter 確定";
            this.enter.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.enter.Click += new System.EventHandler(this.enter_Click);
            // 
            // txtBl
            // 
            this.txtBl.Location = new System.Drawing.Point(80, 185);
            this.txtBl.Name = "txtBl";
            this.txtBl.ReadOnly = true;
            this.txtBl.Size = new System.Drawing.Size(38, 23);
            this.txtBl.TabIndex = 4;
            this.txtBl.Tip = "BL数";
            this.txtBl.TextChanged += new System.EventHandler(this.excute_count);
            // 
            // txtPs
            // 
            this.txtPs.Location = new System.Drawing.Point(158, 185);
            this.txtPs.Name = "txtPs";
            this.txtPs.Size = new System.Drawing.Size(38, 23);
            this.txtPs.TabIndex = 5;
            this.txtPs.Tip = "PS数";
            this.txtPs.TextChanged += new System.EventHandler(this.excute_count);
            // 
            // txtCs
            // 
            this.txtCs.Location = new System.Drawing.Point(7, 185);
            this.txtCs.Name = "txtCs";
            this.txtCs.ReadOnly = true;
            this.txtCs.Size = new System.Drawing.Size(38, 23);
            this.txtCs.TabIndex = 3;
            this.txtCs.Tip = "CS数";
            this.txtCs.TextChanged += new System.EventHandler(this.excute_count);
            // 
            // txtDescBinCode
            // 
            this.txtDescBinCode.BorderColor = System.Drawing.Color.Empty;
            this.txtDescBinCode.CommitKey = null;
            this.txtDescBinCode.Field = null;
            this.txtDescBinCode.Location = new System.Drawing.Point(122, 215);
            this.txtDescBinCode.Name = "txtDescBinCode";
            this.txtDescBinCode.Pattern = null;
            this.txtDescBinCode.Size = new System.Drawing.Size(109, 23);
            this.txtDescBinCode.TabIndex = 6;
            this.txtDescBinCode.Tip = "移動先棚番入力";
            this.txtDescBinCode.ValidsCommit = null;
            this.txtDescBinCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtDescBinCode_KeyDown);
            // 
            // lblQyCount
            // 
            this.lblQyCount.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblQyCount.Location = new System.Drawing.Point(7, 215);
            this.lblQyCount.Name = "lblQyCount";
            this.lblQyCount.Size = new System.Drawing.Size(109, 23);
            // 
            // lblExpDate
            // 
            this.lblExpDate.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblExpDate.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblExpDate.Location = new System.Drawing.Point(122, 65);
            this.lblExpDate.Name = "lblExpDate";
            this.lblExpDate.Size = new System.Drawing.Size(109, 23);
            // 
            // txtQrCode
            // 
            this.txtQrCode.BorderColor = System.Drawing.Color.Empty;
            this.txtQrCode.CommitKey = null;
            this.txtQrCode.DoSelectNext = false;
            this.txtQrCode.Field = null;
            this.txtQrCode.Location = new System.Drawing.Point(122, 35);
            this.txtQrCode.Name = "txtQrCode";
            this.txtQrCode.Pattern = null;
            this.txtQrCode.Size = new System.Drawing.Size(109, 23);
            this.txtQrCode.TabIndex = 2;
            this.txtQrCode.Tip = "QRコード";
            this.txtQrCode.ValidsCommit = null;
            this.txtQrCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtQrCode_KeyDown);
            // 
            // lblPsUnit
            // 
            this.lblPsUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblPsUnit.Location = new System.Drawing.Point(202, 185);
            this.lblPsUnit.Name = "lblPsUnit";
            this.lblPsUnit.Size = new System.Drawing.Size(28, 23);
            // 
            // lblBlUnit
            // 
            this.lblBlUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblBlUnit.Location = new System.Drawing.Point(124, 185);
            this.lblBlUnit.Name = "lblBlUnit";
            this.lblBlUnit.Size = new System.Drawing.Size(28, 23);
            // 
            // lblCsUnit
            // 
            this.lblCsUnit.BackColor = System.Drawing.SystemColors.Window;
            this.lblCsUnit.Location = new System.Drawing.Point(46, 185);
            this.lblCsUnit.Name = "lblCsUnit";
            this.lblCsUnit.Size = new System.Drawing.Size(28, 23);
            // 
            // lblBlIn
            // 
            this.lblBlIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblBlIn.Location = new System.Drawing.Point(120, 155);
            this.lblBlIn.Name = "lblBlIn";
            this.lblBlIn.Size = new System.Drawing.Size(109, 23);
            // 
            // lblCsIn
            // 
            this.lblCsIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblCsIn.Location = new System.Drawing.Point(7, 155);
            this.lblCsIn.Name = "lblCsIn";
            this.lblCsIn.Size = new System.Drawing.Size(109, 23);
            // 
            // lblQtyInfo
            // 
            this.lblQtyInfo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblQtyInfo.Location = new System.Drawing.Point(6, 125);
            this.lblQtyInfo.Name = "lblQtyInfo";
            this.lblQtyInfo.Size = new System.Drawing.Size(224, 23);
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuName.Location = new System.Drawing.Point(6, 95);
            this.lblSkuName.Name = "lblSkuName";
            this.lblSkuName.Size = new System.Drawing.Size(224, 23);
            // 
            // lblSkuCode
            // 
            this.lblSkuCode.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuCode.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblSkuCode.Location = new System.Drawing.Point(7, 65);
            this.lblSkuCode.Name = "lblSkuCode";
            this.lblSkuCode.Size = new System.Drawing.Size(109, 23);
            // 
            // txtBinCode
            // 
            this.txtBinCode.BorderColor = System.Drawing.Color.Empty;
            this.txtBinCode.CommitKey = null;
            this.txtBinCode.Field = null;
            this.txtBinCode.Location = new System.Drawing.Point(7, 35);
            this.txtBinCode.Name = "txtBinCode";
            this.txtBinCode.Pattern = null;
            this.txtBinCode.Size = new System.Drawing.Size(109, 23);
            this.txtBinCode.TabIndex = 1;
            this.txtBinCode.Tip = "現棚番入力";
            this.txtBinCode.ValidsCommit = null;
            this.txtBinCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBinCode_KeyDown);
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
            // frmInvInfoSearchByQr
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmInvInfoSearchByQr";
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
        private Framework.TextEx txtBinCode;
        private System.Windows.Forms.Label lblSkuCode;
        private System.Windows.Forms.Label lblPsUnit;
        private System.Windows.Forms.Label lblCsUnit;
        private System.Windows.Forms.Label lblQtyInfo;
        private System.Windows.Forms.Label lblSkuName;
        private System.Windows.Forms.Label lblBlUnit;
        private System.Windows.Forms.Label lblExpDate;
        private Framework.TextEx txtQrCode;
        private Framework.TextEx txtDescBinCode;
        private System.Windows.Forms.Label lblQyCount;
        private System.Windows.Forms.Label lblBlIn;
        private System.Windows.Forms.Label lblCsIn;
        private Framework.NumericText txtBl;
        private Framework.NumericText txtPs;
        private Framework.NumericText txtCs;
        private Framework.ButtonEx F1;
        private Framework.ButtonEx enter;

    }
}
