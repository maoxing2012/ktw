namespace WMSPDA
{
    partial class frmPrint
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmPrint));
            this.pnlDetail = new Framework.AlphaPanel();
            this.txtCom = new Framework.TextEx();
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
            this.pnlDetail.Controls.Add(this.txtCom);
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
            // txtCom
            // 
            this.txtCom.BorderColor = System.Drawing.Color.Empty;
            this.txtCom.BusinessForm = null;
            //this.txtCom.CharacterCasing = OpenNETCF.Windows.Forms.CharacterCasing.Normal;
            this.txtCom.CommitKey = null;
            this.txtCom.DoSelectAll = true;
            this.txtCom.DoSelectNext = true;
            this.txtCom.Field = null;
            this.txtCom.Location = new System.Drawing.Point(7, 37);
            this.txtCom.MustInput = false;
            this.txtCom.Name = "txtCom";
            this.txtCom.Pattern = null;
            this.txtCom.Size = new System.Drawing.Size(306, 25);
            this.txtCom.TabIndex = 0;
            this.txtCom.Tip = "端口号";
            this.txtCom.ValidsCommit = null;
            this.txtCom.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCom_KeyDown);
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
            this.btnOK.Location = new System.Drawing.Point(47, 86);
            this.btnOK.Name = "btnOK";
            this.btnOK.RoundButton = false;
            this.btnOK.Size = new System.Drawing.Size(226, 27);
            this.btnOK.TabIndex = 1;
            this.btnOK.Text = "Enter测试打印";
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
            this.btnBack.TabIndex = 2;
            this.btnBack.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnRefresh
            // 
            this.btnRefresh.CommitKey = null;
            this.btnRefresh.HotKey = null;
            this.btnRefresh.ImageName = "WMSPDA.Images.refresh.png";
            this.btnRefresh.Location = new System.Drawing.Point(291, 1);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.RoundButton = false;
            this.btnRefresh.Size = new System.Drawing.Size(26, 26);
            this.btnRefresh.TabIndex = 3;
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
            // frmPrint
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.ClientSize = new System.Drawing.Size(320, 294);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmPrint";
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.TextEx txtCom;
        private Framework.ButtonEx btnOK;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.ButtonEx btnRefresh;
        private Framework.PictureEx picHead;
    }
}
