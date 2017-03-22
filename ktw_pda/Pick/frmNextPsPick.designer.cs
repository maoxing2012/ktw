namespace Pick
{
    partial class frmNextPsPick
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
            this.lblCount = new System.Windows.Forms.Label();
            this.BtnClear = new Framework.ButtonEx();
            this.BtnOk = new Framework.ButtonEx();
            this.txtBinCode = new Framework.TextEx();
            this.txtBarcode = new Framework.TextEx();
            this.txtQty = new Framework.NumericText();
            this.lblSkuCode = new System.Windows.Forms.Label();
            this.lblWarnMsg4Ps = new System.Windows.Forms.Label();
            this.lblQty = new System.Windows.Forms.Label();
            this.lblExpDate = new System.Windows.Forms.Label();
            this.lblSpecs = new System.Windows.Forms.Label();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.lblBinCode = new System.Windows.Forms.Label();
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
            this.pnlDetail.Controls.Add(this.lblCount);
            this.pnlDetail.Controls.Add(this.BtnClear);
            this.pnlDetail.Controls.Add(this.BtnOk);
            this.pnlDetail.Controls.Add(this.txtBinCode);
            this.pnlDetail.Controls.Add(this.txtBarcode);
            this.pnlDetail.Controls.Add(this.txtQty);
            this.pnlDetail.Controls.Add(this.lblSkuCode);
            this.pnlDetail.Controls.Add(this.lblWarnMsg4Ps);
            this.pnlDetail.Controls.Add(this.lblQty);
            this.pnlDetail.Controls.Add(this.lblExpDate);
            this.pnlDetail.Controls.Add(this.lblSpecs);
            this.pnlDetail.Controls.Add(this.lblSkuName);
            this.pnlDetail.Controls.Add(this.lblBinCode);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // lblCount
            // 
            this.lblCount.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Bold);
            this.lblCount.Location = new System.Drawing.Point(173, 32);
            this.lblCount.Name = "lblCount";
            this.lblCount.Size = new System.Drawing.Size(57, 23);
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
            this.BtnClear.Size = new System.Drawing.Size(119, 27);
            this.BtnClear.TabIndex = 97;
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
            this.BtnOk.Location = new System.Drawing.Point(117, 268);
            this.BtnOk.Name = "BtnOk";
            this.BtnOk.RoundButton = true;
            this.BtnOk.Size = new System.Drawing.Size(122, 27);
            this.BtnOk.TabIndex = 96;
            this.BtnOk.Text = "Enter 確定";
            this.BtnOk.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.BtnOk.Click += new System.EventHandler(this.BtnOk_Click);
            // 
            // txtBinCode
            // 
            this.txtBinCode.BorderColor = System.Drawing.Color.Empty;
            this.txtBinCode.CommitKey = null;
            this.txtBinCode.Field = null;
            this.txtBinCode.Location = new System.Drawing.Point(6, 195);
            this.txtBinCode.Name = "txtBinCode";
            this.txtBinCode.Pattern = null;
            this.txtBinCode.Size = new System.Drawing.Size(69, 23);
            this.txtBinCode.TabIndex = 4;
            this.txtBinCode.Tip = "棚番";
            this.txtBinCode.ValidsCommit = null;
            this.txtBinCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBinCode_KeyDown);
            // 
            // txtBarcode
            // 
            this.txtBarcode.BorderColor = System.Drawing.Color.Empty;
            this.txtBarcode.CommitKey = null;
            this.txtBarcode.Field = null;
            this.txtBarcode.Location = new System.Drawing.Point(81, 195);
            this.txtBarcode.Name = "txtBarcode";
            this.txtBarcode.Pattern = null;
            this.txtBarcode.Size = new System.Drawing.Size(94, 23);
            this.txtBarcode.TabIndex = 85;
            this.txtBarcode.Tip = "商品バーコード入力";
            this.txtBarcode.ValidsCommit = null;
            this.txtBarcode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBarcode_KeyDown);
            // 
            // txtQty
            // 
            this.txtQty.Location = new System.Drawing.Point(181, 195);
            this.txtQty.Name = "txtQty";
            this.txtQty.Size = new System.Drawing.Size(49, 23);
            this.txtQty.TabIndex = 6;
            this.txtQty.Tip = "PS数量";
            this.txtQty.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtQty_KeyDown);
            // 
            // lblSkuCode
            // 
            this.lblSkuCode.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuCode.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblSkuCode.Location = new System.Drawing.Point(136, 67);
            this.lblSkuCode.Name = "lblSkuCode";
            this.lblSkuCode.Size = new System.Drawing.Size(94, 23);
            // 
            // lblWarnMsg4Ps
            // 
            this.lblWarnMsg4Ps.BackColor = System.Drawing.Color.Red;
            this.lblWarnMsg4Ps.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Bold);
            this.lblWarnMsg4Ps.ForeColor = System.Drawing.Color.White;
            this.lblWarnMsg4Ps.Location = new System.Drawing.Point(7, 230);
            this.lblWarnMsg4Ps.Name = "lblWarnMsg4Ps";
            this.lblWarnMsg4Ps.Size = new System.Drawing.Size(224, 23);
            this.lblWarnMsg4Ps.Visible = false;
            // 
            // lblQty
            // 
            this.lblQty.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblQty.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Bold);
            this.lblQty.ForeColor = System.Drawing.Color.Red;
            this.lblQty.Location = new System.Drawing.Point(6, 163);
            this.lblQty.Name = "lblQty";
            this.lblQty.Size = new System.Drawing.Size(224, 23);
            // 
            // lblExpDate
            // 
            this.lblExpDate.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblExpDate.Location = new System.Drawing.Point(7, 131);
            this.lblExpDate.Name = "lblExpDate";
            this.lblExpDate.Size = new System.Drawing.Size(100, 23);
            // 
            // lblSpecs
            // 
            this.lblSpecs.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSpecs.Location = new System.Drawing.Point(112, 131);
            this.lblSpecs.Name = "lblSpecs";
            this.lblSpecs.Size = new System.Drawing.Size(118, 23);
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuName.Location = new System.Drawing.Point(6, 99);
            this.lblSkuName.Name = "lblSkuName";
            this.lblSkuName.Size = new System.Drawing.Size(224, 23);
            // 
            // lblBinCode
            // 
            this.lblBinCode.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblBinCode.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Bold);
            this.lblBinCode.ForeColor = System.Drawing.Color.Red;
            this.lblBinCode.Location = new System.Drawing.Point(7, 67);
            this.lblBinCode.Name = "lblBinCode";
            this.lblBinCode.Size = new System.Drawing.Size(124, 23);
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
            // frmNextPsPick
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmNextPsPick";
            this.Text = "";
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
        private System.Windows.Forms.Label lblBinCode;
        private System.Windows.Forms.Label lblSpecs;
        private System.Windows.Forms.Label lblSkuName;
        private System.Windows.Forms.Label lblSkuCode;
        private System.Windows.Forms.Label lblQty;
        private System.Windows.Forms.Label lblExpDate;
        private Framework.NumericText txtQty;
        private Framework.TextEx txtBinCode;
        private Framework.TextEx txtBarcode;
        private Framework.ButtonEx BtnClear;
        private Framework.ButtonEx BtnOk;
        private System.Windows.Forms.Label lblCount;
        private System.Windows.Forms.Label lblWarnMsg4Ps;

    }
}
