﻿namespace Pick
{
    partial class frmPick
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
            this.menuPsPick = new Framework.ButtonEx();
            this.menuCsPick = new Framework.ButtonEx();
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
            this.pnlDetail.Controls.Add(this.menuPsPick);
            this.pnlDetail.Controls.Add(this.menuCsPick);
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
            // menuPsPick
            // 
            this.menuPsPick.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.menuPsPick.BackgroundImage = null;
            this.menuPsPick.CommitKey = null;
            this.menuPsPick.HotKey = "key2";
            this.menuPsPick.ImageName = "WMSPDA.Images.menu.png";
            this.menuPsPick.Location = new System.Drawing.Point(20, 117);
            this.menuPsPick.Name = "menuPsPick";
            this.menuPsPick.RoundButton = false;
            this.menuPsPick.ShowFocusBorder = true;
            this.menuPsPick.Size = new System.Drawing.Size(198, 50);
            this.menuPsPick.TabIndex = 1;
            this.menuPsPick.Tag = "menuPsPick";
            this.menuPsPick.Text = "2.バラピッキング";
            this.menuPsPick.TransparentColor = System.Drawing.Color.White;
            this.menuPsPick.Click += new System.EventHandler(this.Button_Click);
            // 
            // menuCsPick
            // 
            this.menuCsPick.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.menuCsPick.BackgroundImage = null;
            this.menuCsPick.CommitKey = null;
            this.menuCsPick.HotKey = "key1";
            this.menuCsPick.ImageName = "WMSPDA.Images.menu.png";
            this.menuCsPick.Location = new System.Drawing.Point(20, 61);
            this.menuCsPick.Name = "menuCsPick";
            this.menuCsPick.RoundButton = false;
            this.menuCsPick.Size = new System.Drawing.Size(198, 50);
            this.menuCsPick.TabIndex = 0;
            this.menuCsPick.Tag = "menuCsPick";
            this.menuCsPick.Text = "1.ケースピッキング";
            this.menuCsPick.TransparentColor = System.Drawing.Color.White;
            this.menuCsPick.Click += new System.EventHandler(this.Button_Click);
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
            // frmPick
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmPick";
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
        private Framework.ButtonEx menuPsPick;
        private Framework.ButtonEx menuCsPick;
        private Framework.ButtonEx menuBatchPick;

    }
}
