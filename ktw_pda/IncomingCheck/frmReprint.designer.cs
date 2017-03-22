namespace IncomingCheck
{
    partial class frmReprint
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
            this.btnClear = new Framework.ButtonEx();
            this.btnOk = new Framework.ButtonEx();
            this.txtExpDate = new Framework.TextEx();
            this.lblBlIn = new System.Windows.Forms.Label();
            this.lblCsIn = new System.Windows.Forms.Label();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.txtAsnNumber = new Framework.TextEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnHome = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.txtQrCode = new Framework.TextEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.btnClear);
            this.pnlDetail.Controls.Add(this.btnOk);
            this.pnlDetail.Controls.Add(this.txtExpDate);
            this.pnlDetail.Controls.Add(this.lblBlIn);
            this.pnlDetail.Controls.Add(this.lblCsIn);
            this.pnlDetail.Controls.Add(this.lblSkuName);
            this.pnlDetail.Controls.Add(this.txtAsnNumber);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Controls.Add(this.txtQrCode);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
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
            this.btnClear.Size = new System.Drawing.Size(97, 27);
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
            this.btnOk.Location = new System.Drawing.Point(95, 268);
            this.btnOk.Name = "btnOk";
            this.btnOk.RoundButton = true;
            this.btnOk.Size = new System.Drawing.Size(143, 27);
            this.btnOk.TabIndex = 55;
            this.btnOk.Text = "Enter 確定";
            this.btnOk.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnOk.Click += new System.EventHandler(this.enter_Click);
            // 
            // txtExpDate
            // 
            this.txtExpDate.BorderColor = System.Drawing.Color.Empty;
            this.txtExpDate.CommitKey = null;
            this.txtExpDate.DoSelectNext = false;
            this.txtExpDate.Field = "";
            this.txtExpDate.Location = new System.Drawing.Point(7, 93);
            this.txtExpDate.Name = "txtExpDate";
            this.txtExpDate.Pattern = null;
            this.txtExpDate.Size = new System.Drawing.Size(224, 23);
            this.txtExpDate.TabIndex = 5;
            this.txtExpDate.Tip = "賞味期限入力";
            this.txtExpDate.ValidsCommit = null;
            this.txtExpDate.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtDescBinCode_KeyDown);
            // 
            // lblBlIn
            // 
            this.lblBlIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblBlIn.Location = new System.Drawing.Point(122, 155);
            this.lblBlIn.Name = "lblBlIn";
            this.lblBlIn.Size = new System.Drawing.Size(109, 23);
            this.lblBlIn.Visible = false;
            // 
            // lblCsIn
            // 
            this.lblCsIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblCsIn.Location = new System.Drawing.Point(8, 155);
            this.lblCsIn.Name = "lblCsIn";
            this.lblCsIn.Size = new System.Drawing.Size(109, 23);
            this.lblCsIn.Visible = false;
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuName.Location = new System.Drawing.Point(7, 124);
            this.lblSkuName.Name = "lblSkuName";
            this.lblSkuName.Size = new System.Drawing.Size(224, 23);
            // 
            // txtAsnNumber
            // 
            this.txtAsnNumber.BorderColor = System.Drawing.Color.Empty;
            this.txtAsnNumber.CommitKey = null;
            this.txtAsnNumber.Field = "";
            this.txtAsnNumber.Location = new System.Drawing.Point(7, 35);
            this.txtAsnNumber.MustInput = true;
            this.txtAsnNumber.Name = "txtAsnNumber";
            this.txtAsnNumber.Pattern = null;
            this.txtAsnNumber.Size = new System.Drawing.Size(224, 23);
            this.txtAsnNumber.TabIndex = 0;
            this.txtAsnNumber.Tip = "入荷伝票番号入力";
            this.txtAsnNumber.ValidsCommit = "searchAsn";
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
            this.picHead.TitleText = "入荷荷札再発行";
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
            this.txtQrCode.Size = new System.Drawing.Size(224, 23);
            this.txtQrCode.TabIndex = 1;
            this.txtQrCode.Tip = "商品バーコード入力";
            this.txtQrCode.ValidsCommit = null;
            this.txtQrCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtQrCode_KeyDown);
            // 
            // frmReprint
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmReprint";
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
        private Framework.TextEx txtAsnNumber;
        private System.Windows.Forms.Label lblSkuName;
        private Framework.TextEx txtQrCode;
        private Framework.TextEx txtExpDate;
        private System.Windows.Forms.Label lblBlIn;
        private System.Windows.Forms.Label lblCsIn;
        private Framework.ButtonEx btnClear;
        private Framework.ButtonEx btnOk;

    }
}
