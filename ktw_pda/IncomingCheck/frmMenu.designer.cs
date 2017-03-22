namespace IncomingCheck
{
    partial class frmMenu
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
            this.menuBatchPick = new Framework.ButtonEx();
            this.menuMulti = new Framework.ButtonEx();
            this.menuSingle = new Framework.ButtonEx();
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
            this.pnlDetail.Controls.Add(this.menuBatchPick);
            this.pnlDetail.Controls.Add(this.menuMulti);
            this.pnlDetail.Controls.Add(this.menuSingle);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // menuBatchPick
            // 
            this.menuBatchPick.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.menuBatchPick.BackgroundImage = null;
            this.menuBatchPick.CommitKey = null;
            this.menuBatchPick.HotKey = "key3";
            this.menuBatchPick.ImageName = "WMSPDA.Images.menu.png";
            this.menuBatchPick.Location = new System.Drawing.Point(20, 173);
            this.menuBatchPick.Name = "menuBatchPick";
            this.menuBatchPick.RoundButton = false;
            this.menuBatchPick.ShowFocusBorder = true;
            this.menuBatchPick.Size = new System.Drawing.Size(198, 50);
            this.menuBatchPick.TabIndex = 1;
            this.menuBatchPick.Tag = "menuBatchPick";
            this.menuBatchPick.Text = "3.大バッチピッキング";
            this.menuBatchPick.TransparentColor = System.Drawing.Color.White;
            this.menuBatchPick.Visible = false;
            this.menuBatchPick.Click += new System.EventHandler(this.Button_Click);
            // 
            // menuMulti
            // 
            this.menuMulti.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.menuMulti.BackgroundImage = null;
            this.menuMulti.CommitKey = null;
            this.menuMulti.HotKey = "key2";
            this.menuMulti.ImageName = "WMSPDA.Images.menu.png";
            this.menuMulti.Location = new System.Drawing.Point(20, 117);
            this.menuMulti.Name = "menuMulti";
            this.menuMulti.RoundButton = false;
            this.menuMulti.ShowFocusBorder = true;
            this.menuMulti.Size = new System.Drawing.Size(198, 50);
            this.menuMulti.TabIndex = 1;
            this.menuMulti.Tag = "menuMulti";
            this.menuMulti.Text = "2.マルチ";
            this.menuMulti.TransparentColor = System.Drawing.Color.White;
            this.menuMulti.Click += new System.EventHandler(this.Button_Click);
            // 
            // menuSingle
            // 
            this.menuSingle.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.menuSingle.BackgroundImage = null;
            this.menuSingle.CommitKey = null;
            this.menuSingle.HotKey = "key1";
            this.menuSingle.ImageName = "WMSPDA.Images.menu.png";
            this.menuSingle.Location = new System.Drawing.Point(20, 61);
            this.menuSingle.Name = "menuSingle";
            this.menuSingle.RoundButton = false;
            this.menuSingle.Size = new System.Drawing.Size(198, 50);
            this.menuSingle.TabIndex = 0;
            this.menuSingle.Tag = "menuSingle";
            this.menuSingle.Text = "1.シングル";
            this.menuSingle.TransparentColor = System.Drawing.Color.White;
            this.menuSingle.Click += new System.EventHandler(this.Button_Click);
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
            // frmMenu
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmMenu";
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
        private Framework.ButtonEx menuMulti;
        private Framework.ButtonEx menuSingle;
        private Framework.ButtonEx menuBatchPick;

    }
}
