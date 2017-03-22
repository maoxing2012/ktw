namespace WMSPDA
{
    partial class frmBarcode
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmBarcode));
            this.pnlDetail = new Framework.AlphaPanel();
            this.txtTime = new Framework.TextEx();
            this.txtCodeType = new Framework.TextEx();
            this.txtBarCode = new Framework.TextEx();
            this.btnOK = new Framework.ButtonEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnRefresh = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.Controls.Add(this.txtTime);
            this.pnlDetail.Controls.Add(this.txtCodeType);
            this.pnlDetail.Controls.Add(this.txtBarCode);
            this.pnlDetail.Controls.Add(this.btnOK);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnRefresh);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Top;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(320, 243);
            // 
            // txtTime
            // 
            this.txtTime.BorderColor = System.Drawing.Color.Empty;
            this.txtTime.BusinessForm = null;
            this.txtTime.CharacterCasing = OpenNETCF.Windows.Forms.CharacterCasing.Normal;
            this.txtTime.CommitKey = null;
            this.txtTime.DoSelectAll = true;
            this.txtTime.DoSelectNext = true;
            this.txtTime.Field = null;
            this.txtTime.Location = new System.Drawing.Point(7, 148);
            this.txtTime.MustInput = false;
            this.txtTime.Name = "txtTime";
            this.txtTime.Pattern = null;
            this.txtTime.ReadOnly = true;
            this.txtTime.Size = new System.Drawing.Size(306, 25);
            this.txtTime.TabIndex = 2;
            this.txtTime.Tip = null;
            this.txtTime.ValidsCommit = null;
            // 
            // txtCodeType
            // 
            this.txtCodeType.BorderColor = System.Drawing.Color.Empty;
            this.txtCodeType.BusinessForm = null;
            this.txtCodeType.CharacterCasing = OpenNETCF.Windows.Forms.CharacterCasing.Normal;
            this.txtCodeType.CommitKey = null;
            this.txtCodeType.DoSelectAll = true;
            this.txtCodeType.DoSelectNext = true;
            this.txtCodeType.Field = null;
            this.txtCodeType.Location = new System.Drawing.Point(7, 112);
            this.txtCodeType.MustInput = false;
            this.txtCodeType.Name = "txtCodeType";
            this.txtCodeType.Pattern = null;
            this.txtCodeType.ReadOnly = true;
            this.txtCodeType.Size = new System.Drawing.Size(306, 25);
            this.txtCodeType.TabIndex = 1;
            this.txtCodeType.Tip = null;
            this.txtCodeType.ValidsCommit = null;
            // 
            // txtBarCode
            // 
            this.txtBarCode.BorderColor = System.Drawing.Color.Empty;
            this.txtBarCode.BusinessForm = null;
            this.txtBarCode.CharacterCasing = OpenNETCF.Windows.Forms.CharacterCasing.Normal;
            this.txtBarCode.CommitKey = null;
            this.txtBarCode.DoSelectAll = true;
            this.txtBarCode.DoSelectNext = true;
            this.txtBarCode.Field = null;
            this.txtBarCode.Location = new System.Drawing.Point(7, 43);
            this.txtBarCode.Multiline = true;
            this.txtBarCode.MustInput = false;
            this.txtBarCode.Name = "txtBarCode";
            this.txtBarCode.Pattern = null;
            this.txtBarCode.ReadOnly = true;
            this.txtBarCode.Size = new System.Drawing.Size(306, 63);
            this.txtBarCode.TabIndex = 0;
            this.txtBarCode.Tip = null;
            this.txtBarCode.ValidsCommit = null;
            // 
            // btnOK
            // 
            this.btnOK.BackColor = System.Drawing.Color.White;
            this.btnOK.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnOK.BackgroundImage")));
            this.btnOK.BorderColor = System.Drawing.Color.Transparent;
            this.btnOK.CommitKey = null;
            this.btnOK.ForeColor = System.Drawing.Color.White;
            this.btnOK.HotKey = null;
            this.btnOK.ImageName = "WMSPDA.Images.button1.png";
            this.btnOK.Location = new System.Drawing.Point(7, 197);
            this.btnOK.Name = "btnOK";
            this.btnOK.RoundButton = false;
            this.btnOK.Size = new System.Drawing.Size(306, 27);
            this.btnOK.TabIndex = 3;
            this.btnOK.Text = "Enter清 空";
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
            // btnRefresh
            // 
            this.btnRefresh.CommitKey = null;
            this.btnRefresh.HotKey = null;
            this.btnRefresh.ImageName = "WMSPDA.Images.refresh.png";
            this.btnRefresh.Location = new System.Drawing.Point(211, 1);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.RoundButton = false;
            this.btnRefresh.Size = new System.Drawing.Size(26, 26);
            this.btnRefresh.TabIndex = 6;
            this.btnRefresh.TransparentColor = System.Drawing.Color.White;
            this.btnRefresh.Visible = false;
            // 
            // picHead
            // 
            this.picHead.Dock = System.Windows.Forms.DockStyle.Top;
            this.picHead.ImageName = "WMSPDA.Images.header.PNG";
            this.picHead.Location = new System.Drawing.Point(0, 0);
            this.picHead.Name = "picHead";
            this.picHead.Size = new System.Drawing.Size(320, 29);
            this.picHead.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picHead.TextColor = System.Drawing.Color.White;
            this.picHead.TextFont = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Bold);
            this.picHead.TitleText = "条码扫描测试";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // frmBarcode
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.ClientSize = new System.Drawing.Size(320, 294);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmBarcode";
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.TextEx txtTime;
        private Framework.TextEx txtCodeType;
        private Framework.TextEx txtBarCode;
        private Framework.ButtonEx btnOK;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.ButtonEx btnRefresh;
        private Framework.PictureEx picHead;
    }
}
