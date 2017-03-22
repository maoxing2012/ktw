namespace WMSPDA
{
    partial class frmMain
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
            this.pnlParent = new Framework.AlphaPanel();
            this.pnlMain = new Framework.AlphaPanel();
            this.lblUser = new System.Windows.Forms.Label();
            this.lblSetting = new Framework.LabelEx();
            this.btnSetting = new Framework.ButtonEx();
            this.lblVersion = new System.Windows.Forms.Label();
            this.menuReplenishment = new Framework.ButtonEx();
            this.menuShipment = new Framework.ButtonEx();
            this.menuPackage = new Framework.ButtonEx();
            this.menuPickup = new Framework.ButtonEx();
            this.menuPutaway = new Framework.ButtonEx();
            this.menuAsn = new Framework.ButtonEx();
            this.btnRefresh = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.pnlParent.SuspendLayout();
            this.pnlMain.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlParent
            // 
            this.pnlParent.Controls.Add(this.pnlMain);
            this.pnlParent.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlParent.Location = new System.Drawing.Point(0, 0);
            this.pnlParent.Name = "pnlParent";
            this.pnlParent.Size = new System.Drawing.Size(238, 294);
            // 
            // pnlMain
            // 
            this.pnlMain.BackColor = System.Drawing.Color.White;
            this.pnlMain.Controls.Add(this.lblUser);
            this.pnlMain.Controls.Add(this.lblSetting);
            this.pnlMain.Controls.Add(this.btnSetting);
            this.pnlMain.Controls.Add(this.lblVersion);
            this.pnlMain.Controls.Add(this.menuReplenishment);
            this.pnlMain.Controls.Add(this.menuShipment);
            this.pnlMain.Controls.Add(this.menuPackage);
            this.pnlMain.Controls.Add(this.menuPickup);
            this.pnlMain.Controls.Add(this.menuPutaway);
            this.pnlMain.Controls.Add(this.menuAsn);
            this.pnlMain.Controls.Add(this.btnRefresh);
            this.pnlMain.Controls.Add(this.picHead);
            this.pnlMain.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlMain.Location = new System.Drawing.Point(0, 0);
            this.pnlMain.Name = "pnlMain";
            this.pnlMain.Size = new System.Drawing.Size(238, 294);
            // 
            // lblUser
            // 
            this.lblUser.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.lblUser.Location = new System.Drawing.Point(112, 32);
            this.lblUser.Name = "lblUser";
            this.lblUser.Size = new System.Drawing.Size(123, 18);
            this.lblUser.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // lblSetting
            // 
            this.lblSetting.Border = false;
            this.lblSetting.Location = new System.Drawing.Point(30, 32);
            this.lblSetting.Name = "lblSetting";
            this.lblSetting.Size = new System.Drawing.Size(167, 21);
            this.lblSetting.Text = "F1荷主切替";
            this.lblSetting.Transparent = false;
            this.lblSetting.ClickEx += new System.EventHandler(this.btnSetting_Click);
            // 
            // btnSetting
            // 
            this.btnSetting.BackgroundImage = null;
            this.btnSetting.CommitKey = null;
            this.btnSetting.HotKey = "keyf1";
            this.btnSetting.ImageName = "WMSPDA.Images.setting.png";
            this.btnSetting.Location = new System.Drawing.Point(4, 29);
            this.btnSetting.Name = "btnSetting";
            this.btnSetting.RoundButton = false;
            this.btnSetting.Size = new System.Drawing.Size(24, 24);
            this.btnSetting.TabIndex = 11;
            this.btnSetting.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnSetting.Click += new System.EventHandler(this.btnSetting_Click);
            // 
            // lblVersion
            // 
            this.lblVersion.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.lblVersion.Location = new System.Drawing.Point(11, 267);
            this.lblVersion.Name = "lblVersion";
            this.lblVersion.Size = new System.Drawing.Size(150, 18);
            this.lblVersion.Text = "バージョン：";
            // 
            // menuReplenishment
            // 
            this.menuReplenishment.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.menuReplenishment.BackgroundImage = null;
            this.menuReplenishment.CommitKey = null;
            this.menuReplenishment.HotKey = "key6";
            this.menuReplenishment.ImageName = "WMSPDA.Images.menu.png";
            this.menuReplenishment.Location = new System.Drawing.Point(112, 182);
            this.menuReplenishment.Name = "menuReplenishment";
            this.menuReplenishment.RoundButton = false;
            this.menuReplenishment.Size = new System.Drawing.Size(115, 50);
            this.menuReplenishment.TabIndex = 7;
            this.menuReplenishment.Tag = "menuOther";
            this.menuReplenishment.Text = "6.在庫照会・そのた";
            this.menuReplenishment.TransparentColor = System.Drawing.Color.White;
            this.menuReplenishment.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuShipment
            // 
            this.menuShipment.BackgroundImage = null;
            this.menuShipment.CommitKey = null;
            this.menuShipment.HotKey = "key5";
            this.menuShipment.ImageName = "WMSPDA.Images.menu.png";
            this.menuShipment.Location = new System.Drawing.Point(11, 182);
            this.menuShipment.Name = "menuShipment";
            this.menuShipment.RoundButton = false;
            this.menuShipment.Size = new System.Drawing.Size(96, 50);
            this.menuShipment.TabIndex = 5;
            this.menuShipment.Tag = "menuShipment";
            this.menuShipment.Text = "5.棚卸";
            this.menuShipment.TransparentColor = System.Drawing.Color.White;
            this.menuShipment.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuPackage
            // 
            this.menuPackage.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.menuPackage.BackgroundImage = null;
            this.menuPackage.CommitKey = null;
            this.menuPackage.HotKey = "key4";
            this.menuPackage.ImageName = "WMSPDA.Images.menu.png";
            this.menuPackage.Location = new System.Drawing.Point(112, 129);
            this.menuPackage.Name = "menuPackage";
            this.menuPackage.RoundButton = false;
            this.menuPackage.Size = new System.Drawing.Size(115, 50);
            this.menuPackage.TabIndex = 4;
            this.menuPackage.Tag = "menuPackageIn";
            this.menuPackage.Text = "4.出荷検品・梱包";
            this.menuPackage.TransparentColor = System.Drawing.Color.White;
            this.menuPackage.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuPickup
            // 
            this.menuPickup.BackgroundImage = null;
            this.menuPickup.CommitKey = null;
            this.menuPickup.HotKey = "key3";
            this.menuPickup.ImageName = "WMSPDA.Images.menu.png";
            this.menuPickup.Location = new System.Drawing.Point(11, 129);
            this.menuPickup.Name = "menuPickup";
            this.menuPickup.RoundButton = false;
            this.menuPickup.Size = new System.Drawing.Size(96, 50);
            this.menuPickup.TabIndex = 3;
            this.menuPickup.Tag = "menuPickup";
            this.menuPickup.Text = "3.ピッキング";
            this.menuPickup.TransparentColor = System.Drawing.Color.White;
            this.menuPickup.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuPutaway
            // 
            this.menuPutaway.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.menuPutaway.BackgroundImage = null;
            this.menuPutaway.CommitKey = null;
            this.menuPutaway.HotKey = "key2";
            this.menuPutaway.ImageName = "WMSPDA.Images.menu.png";
            this.menuPutaway.Location = new System.Drawing.Point(112, 76);
            this.menuPutaway.Name = "menuPutaway";
            this.menuPutaway.RoundButton = false;
            this.menuPutaway.Size = new System.Drawing.Size(115, 50);
            this.menuPutaway.TabIndex = 2;
            this.menuPutaway.Tag = "menuShift";
            this.menuPutaway.Text = "2.入庫・棚移動";
            this.menuPutaway.TransparentColor = System.Drawing.Color.White;
            this.menuPutaway.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuAsn
            // 
            this.menuAsn.BackgroundImage = null;
            this.menuAsn.CommitKey = null;
            this.menuAsn.HotKey = "key1";
            this.menuAsn.ImageName = "WMSPDA.Images.menu.png";
            this.menuAsn.Location = new System.Drawing.Point(11, 76);
            this.menuAsn.Name = "menuAsn";
            this.menuAsn.RoundButton = false;
            this.menuAsn.Size = new System.Drawing.Size(96, 50);
            this.menuAsn.TabIndex = 1;
            this.menuAsn.Tag = "incoming";
            this.menuAsn.Text = "1.入荷検品";
            this.menuAsn.TransparentColor = System.Drawing.Color.White;
            this.menuAsn.Click += new System.EventHandler(this.Menu_Click);
            // 
            // btnRefresh
            // 
            this.btnRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnRefresh.BackgroundImage = null;
            this.btnRefresh.CommitKey = null;
            this.btnRefresh.HotKey = null;
            this.btnRefresh.ImageName = "WMSPDA.Images.refresh.png";
            this.btnRefresh.Location = new System.Drawing.Point(209, 1);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.RoundButton = false;
            this.btnRefresh.Size = new System.Drawing.Size(26, 26);
            this.btnRefresh.TabIndex = 0;
            this.btnRefresh.TransparentColor = System.Drawing.Color.White;
            this.btnRefresh.Visible = false;
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
            this.picHead.TitleText = "メニュー";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // frmMain
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 294);
            this.Controls.Add(this.pnlParent);
            this.Name = "frmMain";
            this.Text = "メニュー";
            this.Load += new System.EventHandler(this.frmMain_Load);
            this.Closed += new System.EventHandler(this.frmMain_Closed);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.frmMain_KeyDown);
            this.pnlParent.ResumeLayout(false);
            this.pnlMain.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlParent;
        private Framework.AlphaPanel pnlMain;
        private Framework.ButtonEx menuShipment;
        private Framework.ButtonEx menuPackage;
        private Framework.ButtonEx menuPickup;
        private Framework.ButtonEx menuPutaway;
        private Framework.ButtonEx menuAsn;
        private Framework.ButtonEx btnRefresh;
        private Framework.PictureEx picHead;
        private Framework.ButtonEx menuReplenishment;
        private System.Windows.Forms.Label lblVersion;
        private Framework.LabelEx lblSetting;
        private Framework.ButtonEx btnSetting;
        private System.Windows.Forms.Label lblUser;





    }
}

