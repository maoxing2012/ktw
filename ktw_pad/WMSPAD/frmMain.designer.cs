namespace WMSPAD
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmMain));
            this.picSetting = new Framework.PictureEx();
            this.lblSetting = new Framework.LabelEx();
            this.lblUser = new Framework.LabelEx();
            this.lblVersion = new System.Windows.Forms.Label();
            this.menuAsn = new Framework.ButtonEx();
            this.menuPutaway = new Framework.ButtonEx();
            this.menuPickup = new Framework.ButtonEx();
            this.menuPackage = new Framework.ButtonEx();
            this.menuShipment = new Framework.ButtonEx();
            this.menuReplenishment = new Framework.ButtonEx();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).BeginInit();
            this.pnlTop.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picSetting)).BeginInit();
            this.SuspendLayout();
            // 
            // btnRight
            // 
            this.btnRight.Visible = false;
            // 
            // picSetting
            // 
            this.picSetting.Location = new System.Drawing.Point(12, 53);
            this.picSetting.Name = "picSetting";
            this.picSetting.Size = new System.Drawing.Size(32, 32);
            this.picSetting.TabIndex = 2;
            this.picSetting.TabStop = false;
            this.picSetting.Click += new System.EventHandler(this.picSetting_Click);
            // 
            // lblSetting
            // 
            this.lblSetting.Font = new System.Drawing.Font("MS UI Gothic", 20F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(128)));
            this.lblSetting.Location = new System.Drawing.Point(47, 55);
            this.lblSetting.Name = "lblSetting";
            this.lblSetting.Size = new System.Drawing.Size(120, 28);
            this.lblSetting.TabIndex = 3;
            this.lblSetting.Text = "荷主切替";
            this.lblSetting.Click += new System.EventHandler(this.picSetting_Click);
            // 
            // lblUser
            // 
            this.lblUser.Font = new System.Drawing.Font("MS UI Gothic", 20F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(128)));
            this.lblUser.Location = new System.Drawing.Point(167, 55);
            this.lblUser.Name = "lblUser";
            this.lblUser.Size = new System.Drawing.Size(428, 28);
            this.lblUser.TabIndex = 3;
            this.lblUser.Text = "荷主切替";
            this.lblUser.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // lblVersion
            // 
            this.lblVersion.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.lblVersion.Font = new System.Drawing.Font("MS UI Gothic", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(128)));
            this.lblVersion.Location = new System.Drawing.Point(15, 473);
            this.lblVersion.Name = "lblVersion";
            this.lblVersion.Size = new System.Drawing.Size(272, 18);
            this.lblVersion.TabIndex = 13;
            this.lblVersion.Text = "バージョン：";
            this.lblVersion.Visible = false;
            // 
            // menuAsn
            // 
            this.menuAsn.CommitKey = "";
            this.menuAsn.Enabled = false;
            this.menuAsn.FlatAppearance.BorderSize = 0;
            this.menuAsn.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.menuAsn.HotKey = "key1";
            this.menuAsn.ImageName = "";
            this.menuAsn.Location = new System.Drawing.Point(67, 132);
            this.menuAsn.Name = "menuAsn";
            this.menuAsn.Size = new System.Drawing.Size(227, 62);
            this.menuAsn.TabIndex = 0;
            this.menuAsn.Text = "1.入荷検品";
            this.menuAsn.UseVisualStyleBackColor = false;
            this.menuAsn.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuPutaway
            // 
            this.menuPutaway.CommitKey = "";
            this.menuPutaway.Enabled = false;
            this.menuPutaway.FlatAppearance.BorderSize = 0;
            this.menuPutaway.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.menuPutaway.HotKey = "key2";
            this.menuPutaway.ImageName = "";
            this.menuPutaway.Location = new System.Drawing.Point(306, 132);
            this.menuPutaway.Name = "menuPutaway";
            this.menuPutaway.Size = new System.Drawing.Size(227, 62);
            this.menuPutaway.TabIndex = 1;
            this.menuPutaway.Text = "2.入庫・棚移動";
            this.menuPutaway.UseVisualStyleBackColor = false;
            this.menuPutaway.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuPickup
            // 
            this.menuPickup.CommitKey = "";
            this.menuPickup.Enabled = false;
            this.menuPickup.FlatAppearance.BorderSize = 0;
            this.menuPickup.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.menuPickup.HotKey = "key3";
            this.menuPickup.ImageName = "";
            this.menuPickup.Location = new System.Drawing.Point(67, 220);
            this.menuPickup.Name = "menuPickup";
            this.menuPickup.Size = new System.Drawing.Size(227, 62);
            this.menuPickup.TabIndex = 2;
            this.menuPickup.Text = "3.ピッキング";
            this.menuPickup.UseVisualStyleBackColor = false;
            this.menuPickup.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuPackage
            // 
            this.menuPackage.CommitKey = "";
            this.menuPackage.FlatAppearance.BorderSize = 0;
            this.menuPackage.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.menuPackage.HotKey = "key4";
            this.menuPackage.ImageName = "";
            this.menuPackage.Location = new System.Drawing.Point(306, 220);
            this.menuPackage.Name = "menuPackage";
            this.menuPackage.Size = new System.Drawing.Size(227, 62);
            this.menuPackage.TabIndex = 3;
            this.menuPackage.Tag = "menuPackageIn";
            this.menuPackage.Text = "4.出荷検品・梱包";
            this.menuPackage.UseVisualStyleBackColor = false;
            this.menuPackage.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuShipment
            // 
            this.menuShipment.CommitKey = "";
            this.menuShipment.Enabled = false;
            this.menuShipment.FlatAppearance.BorderSize = 0;
            this.menuShipment.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.menuShipment.HotKey = "key5";
            this.menuShipment.ImageName = "";
            this.menuShipment.Location = new System.Drawing.Point(67, 307);
            this.menuShipment.Name = "menuShipment";
            this.menuShipment.Size = new System.Drawing.Size(227, 62);
            this.menuShipment.TabIndex = 4;
            this.menuShipment.Text = "5.棚卸";
            this.menuShipment.UseVisualStyleBackColor = false;
            this.menuShipment.Click += new System.EventHandler(this.Menu_Click);
            // 
            // menuReplenishment
            // 
            this.menuReplenishment.CommitKey = "";
            this.menuReplenishment.Enabled = false;
            this.menuReplenishment.FlatAppearance.BorderSize = 0;
            this.menuReplenishment.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.menuReplenishment.HotKey = "key6";
            this.menuReplenishment.ImageName = "";
            this.menuReplenishment.Location = new System.Drawing.Point(306, 307);
            this.menuReplenishment.Name = "menuReplenishment";
            this.menuReplenishment.Size = new System.Drawing.Size(227, 62);
            this.menuReplenishment.TabIndex = 5;
            this.menuReplenishment.Text = "6.在庫照会・そのた";
            this.menuReplenishment.UseVisualStyleBackColor = false;
            this.menuReplenishment.Click += new System.EventHandler(this.Menu_Click);
            // 
            // frmMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(600, 500);
            this.Controls.Add(this.menuReplenishment);
            this.Controls.Add(this.menuPackage);
            this.Controls.Add(this.menuPutaway);
            this.Controls.Add(this.menuShipment);
            this.Controls.Add(this.menuPickup);
            this.Controls.Add(this.menuAsn);
            this.Controls.Add(this.lblVersion);
            this.Controls.Add(this.lblUser);
            this.Controls.Add(this.lblSetting);
            this.Controls.Add(this.picSetting);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "frmMain";
            this.Text = "frmMain";
            this.Load += new System.EventHandler(this.frmMain_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.frmMain_KeyDown);
            this.Controls.SetChildIndex(this.pnlTop, 0);
            this.Controls.SetChildIndex(this.picSetting, 0);
            this.Controls.SetChildIndex(this.lblSetting, 0);
            this.Controls.SetChildIndex(this.lblUser, 0);
            this.Controls.SetChildIndex(this.lblVersion, 0);
            this.Controls.SetChildIndex(this.menuAsn, 0);
            this.Controls.SetChildIndex(this.menuPickup, 0);
            this.Controls.SetChildIndex(this.menuShipment, 0);
            this.Controls.SetChildIndex(this.menuPutaway, 0);
            this.Controls.SetChildIndex(this.menuPackage, 0);
            this.Controls.SetChildIndex(this.menuReplenishment, 0);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).EndInit();
            this.pnlTop.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picSetting)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.PictureEx picSetting;
        private Framework.LabelEx lblSetting;
        private Framework.LabelEx lblUser;
        private System.Windows.Forms.Label lblVersion;
        private Framework.ButtonEx menuAsn;
        private Framework.ButtonEx menuPutaway;
        private Framework.ButtonEx menuPickup;
        private Framework.ButtonEx menuPackage;
        private Framework.ButtonEx menuShipment;
        private Framework.ButtonEx menuReplenishment;
    }
}

