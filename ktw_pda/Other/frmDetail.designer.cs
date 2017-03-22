namespace Other
{
    partial class frmDetail
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
            this.components = new System.ComponentModel.Container();
            this.pnlDetail = new Framework.AlphaPanel();
            this.btnClear = new Framework.ButtonEx();
            this.lblQyCount = new System.Windows.Forms.Label();
            this.bindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.lblIbdDate = new Framework.LabelEx();
            this.lblExpDate = new Framework.LabelEx();
            this.label4 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lblStockInfo = new System.Windows.Forms.Label();
            this.lblPsUnit = new Framework.LabelEx();
            this.lblBlUnit = new Framework.LabelEx();
            this.lblCsUnit = new Framework.LabelEx();
            this.lblBlIn = new Framework.LabelEx();
            this.lblCsIn = new Framework.LabelEx();
            this.label3 = new System.Windows.Forms.Label();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.lblReceiveInfo = new System.Windows.Forms.Label();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnHome = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.btnClear);
            this.pnlDetail.Controls.Add(this.lblQyCount);
            this.pnlDetail.Controls.Add(this.lblIbdDate);
            this.pnlDetail.Controls.Add(this.lblExpDate);
            this.pnlDetail.Controls.Add(this.label4);
            this.pnlDetail.Controls.Add(this.label2);
            this.pnlDetail.Controls.Add(this.lblStockInfo);
            this.pnlDetail.Controls.Add(this.lblPsUnit);
            this.pnlDetail.Controls.Add(this.lblBlUnit);
            this.pnlDetail.Controls.Add(this.lblCsUnit);
            this.pnlDetail.Controls.Add(this.lblBlIn);
            this.pnlDetail.Controls.Add(this.lblCsIn);
            this.pnlDetail.Controls.Add(this.label3);
            this.pnlDetail.Controls.Add(this.lblSkuName);
            this.pnlDetail.Controls.Add(this.label1);
            this.pnlDetail.Controls.Add(this.lblReceiveInfo);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
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
            this.btnClear.Size = new System.Drawing.Size(240, 27);
            this.btnClear.TabIndex = 56;
            this.btnClear.Text = "F1 荷札印刷";
            this.btnClear.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnClear.Click += new System.EventHandler(this.F1_Click);
            // 
            // lblQyCount
            // 
            this.lblQyCount.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblQyCount.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "qtyInfo", true));
            this.lblQyCount.Location = new System.Drawing.Point(7, 203);
            this.lblQyCount.Name = "lblQyCount";
            this.lblQyCount.Size = new System.Drawing.Size(223, 23);
            // 
            // bindingSource
            // 
            this.bindingSource.DataSource = typeof(Other.DetailInfo);
            // 
            // lblIbdDate
            // 
            this.lblIbdDate.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblIbdDate.Border = false;
            this.lblIbdDate.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "ibdDate", true));
            this.lblIbdDate.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblIbdDate.Location = new System.Drawing.Point(121, 231);
            this.lblIbdDate.Name = "lblIbdDate";
            this.lblIbdDate.Size = new System.Drawing.Size(109, 23);
            this.lblIbdDate.Tag = "入荷日:{0}";
            this.lblIbdDate.Transparent = false;
            // 
            // lblExpDate
            // 
            this.lblExpDate.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblExpDate.Border = false;
            this.lblExpDate.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "expDate", true));
            this.lblExpDate.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblExpDate.Location = new System.Drawing.Point(121, 119);
            this.lblExpDate.Name = "lblExpDate";
            this.lblExpDate.Size = new System.Drawing.Size(109, 23);
            this.lblExpDate.Tag = "EXP:{0}";
            this.lblExpDate.Transparent = false;
            // 
            // label4
            // 
            this.label4.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.label4.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "asnNumber", true));
            this.label4.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.label4.Location = new System.Drawing.Point(7, 231);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(109, 23);
            // 
            // label2
            // 
            this.label2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.label2.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "binCode", true));
            this.label2.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.label2.Location = new System.Drawing.Point(7, 119);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(109, 23);
            // 
            // lblStockInfo
            // 
            this.lblStockInfo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblStockInfo.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "skuCode", true));
            this.lblStockInfo.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblStockInfo.Location = new System.Drawing.Point(7, 35);
            this.lblStockInfo.Name = "lblStockInfo";
            this.lblStockInfo.Size = new System.Drawing.Size(109, 23);
            // 
            // lblPsUnit
            // 
            this.lblPsUnit.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblPsUnit.Border = false;
            this.lblPsUnit.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "psUnit", true));
            this.lblPsUnit.Location = new System.Drawing.Point(158, 174);
            this.lblPsUnit.Name = "lblPsUnit";
            this.lblPsUnit.Size = new System.Drawing.Size(72, 23);
            this.lblPsUnit.Tag = "バラ：{0}";
            this.lblPsUnit.Transparent = false;
            // 
            // lblBlUnit
            // 
            this.lblBlUnit.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblBlUnit.Border = false;
            this.lblBlUnit.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "blUnit", true));
            this.lblBlUnit.Location = new System.Drawing.Point(85, 174);
            this.lblBlUnit.Name = "lblBlUnit";
            this.lblBlUnit.Size = new System.Drawing.Size(67, 23);
            this.lblBlUnit.Tag = "インナー：{0}";
            this.lblBlUnit.Transparent = false;
            // 
            // lblCsUnit
            // 
            this.lblCsUnit.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblCsUnit.Border = false;
            this.lblCsUnit.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "csUnit", true));
            this.lblCsUnit.Location = new System.Drawing.Point(7, 175);
            this.lblCsUnit.Name = "lblCsUnit";
            this.lblCsUnit.Size = new System.Drawing.Size(73, 23);
            this.lblCsUnit.Tag = "アウター：{0}";
            this.lblCsUnit.Transparent = false;
            // 
            // lblBlIn
            // 
            this.lblBlIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblBlIn.Border = false;
            this.lblBlIn.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "blIn", true));
            this.lblBlIn.Location = new System.Drawing.Point(121, 147);
            this.lblBlIn.Name = "lblBlIn";
            this.lblBlIn.Size = new System.Drawing.Size(109, 23);
            this.lblBlIn.Tag = "インナー入数：{0}";
            this.lblBlIn.Transparent = false;
            // 
            // lblCsIn
            // 
            this.lblCsIn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblCsIn.Border = false;
            this.lblCsIn.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "csIn", true));
            this.lblCsIn.Location = new System.Drawing.Point(7, 147);
            this.lblCsIn.Name = "lblCsIn";
            this.lblCsIn.Size = new System.Drawing.Size(109, 23);
            this.lblCsIn.Tag = "アウター入数：{0}";
            this.lblCsIn.Transparent = false;
            // 
            // label3
            // 
            this.label3.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.label3.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "specs", true));
            this.label3.Location = new System.Drawing.Point(7, 91);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(224, 23);
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuName.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "skuName", true));
            this.lblSkuName.Location = new System.Drawing.Point(6, 63);
            this.lblSkuName.Name = "lblSkuName";
            this.lblSkuName.Size = new System.Drawing.Size(224, 23);
            // 
            // label1
            // 
            this.label1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.label1.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "invStatus", true));
            this.label1.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.label1.Location = new System.Drawing.Point(120, 35);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(57, 23);
            // 
            // lblReceiveInfo
            // 
            this.lblReceiveInfo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblReceiveInfo.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "tempDiv", true));
            this.lblReceiveInfo.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.lblReceiveInfo.Location = new System.Drawing.Point(181, 35);
            this.lblReceiveInfo.Name = "lblReceiveInfo";
            this.lblReceiveInfo.Size = new System.Drawing.Size(49, 23);
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
            this.picHead.TitleText = "在庫情報詳細";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // frmDetail
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmDetail";
            this.Text = "";
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).EndInit();
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
        private System.Windows.Forms.Label lblSkuName;
        private System.Windows.Forms.Label lblStockInfo;
        private System.Windows.Forms.Label lblQyCount;
        private Framework.ButtonEx btnClear;
        private Framework.LabelEx lblExpDate;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private Framework.LabelEx lblIbdDate;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.BindingSource bindingSource;
        private Framework.LabelEx lblPsUnit;
        private Framework.LabelEx lblCsUnit;
        private Framework.LabelEx lblBlUnit;
        private Framework.LabelEx lblBlIn;
        private Framework.LabelEx lblCsIn;
        private System.Windows.Forms.Label label3;

    }
}
