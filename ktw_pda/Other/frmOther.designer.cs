namespace Other
{
    partial class frmOther
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
            this.menuIbdHisList = new Framework.ButtonEx();
            this.menuInvList = new Framework.ButtonEx();
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
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.menuIbdHisList);
            this.pnlDetail.Controls.Add(this.menuInvList);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnRefresh);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // menuIbdHisList
            // 
            this.menuIbdHisList.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.menuIbdHisList.BackgroundImage = null;
            this.menuIbdHisList.CommitKey = null;
            this.menuIbdHisList.HotKey = "key2";
            this.menuIbdHisList.ImageName = "WMSPDA.Images.menu.png";
            this.menuIbdHisList.Location = new System.Drawing.Point(20, 127);
            this.menuIbdHisList.Name = "menuIbdHisList";
            this.menuIbdHisList.RoundButton = false;
            this.menuIbdHisList.ShowFocusBorder = true;
            this.menuIbdHisList.Size = new System.Drawing.Size(198, 50);
            this.menuIbdHisList.TabIndex = 1;
            this.menuIbdHisList.Tag = "menuInventorySearch";
            this.menuIbdHisList.Text = "2.商品情報検索";
            this.menuIbdHisList.TransparentColor = System.Drawing.Color.White;
            this.menuIbdHisList.Click += new System.EventHandler(this.Button_Click);
            // 
            // menuInvList
            // 
            this.menuInvList.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.menuInvList.BackgroundImage = null;
            this.menuInvList.CommitKey = null;
            this.menuInvList.HotKey = "key1";
            this.menuInvList.ImageName = "WMSPDA.Images.menu.png";
            this.menuInvList.Location = new System.Drawing.Point(20, 61);
            this.menuInvList.Name = "menuInvList";
            this.menuInvList.RoundButton = false;
            this.menuInvList.Size = new System.Drawing.Size(198, 50);
            this.menuInvList.TabIndex = 0;
            this.menuInvList.Tag = "inventorySearch";
            this.menuInvList.Text = "1.在庫照会";
            this.menuInvList.TransparentColor = System.Drawing.Color.White;
            this.menuInvList.Click += new System.EventHandler(this.Button_Click);
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
            // btnRefresh
            // 
            this.btnRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnRefresh.BackgroundImage = null;
            this.btnRefresh.CommitKey = null;
            this.btnRefresh.HotKey = null;
            this.btnRefresh.ImageName = "WMSPDA.Images.home.png";
            this.btnRefresh.Location = new System.Drawing.Point(209, 1);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.RoundButton = false;
            this.btnRefresh.Size = new System.Drawing.Size(26, 26);
            this.btnRefresh.TabIndex = 5;
            this.btnRefresh.TransparentColor = System.Drawing.Color.White;
            this.btnRefresh.Click += new System.EventHandler(this.btnRefresh_Click);
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
            // frmOther
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmOther";
            this.Text = "在庫照会・そのた";
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.ButtonEx btnRefresh;
        private Framework.PictureEx picHead;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.ButtonEx menuIbdHisList;
        private Framework.ButtonEx menuInvList;

    }
}
