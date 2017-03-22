namespace WMSPDA
{
    partial class frmOwnerList
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmOwnerList));
            this.btnOK = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnClear = new Framework.ButtonEx();
            this.bindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.cboOwner = new Framework.ComboBoxEx();
            this.lblName = new System.Windows.Forms.Label();
            this.lblWhName = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // btnOK
            // 
            this.btnOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnOK.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(42)))), ((int)(((byte)(144)))), ((int)(((byte)(198)))));
            this.btnOK.BackgroundImage = null;
            this.btnOK.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.btnOK.CommitKey = null;
            this.btnOK.ForeColor = System.Drawing.Color.White;
            this.btnOK.HotKey = null;
            this.btnOK.ImageName = "";
            this.btnOK.Location = new System.Drawing.Point(99, 268);
            this.btnOK.Name = "btnOK";
            this.btnOK.RoundButton = true;
            this.btnOK.Size = new System.Drawing.Size(139, 27);
            this.btnOK.TabIndex = 4;
            this.btnOK.Text = "Enter 確定";
            this.btnOK.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // picHead
            // 
            this.picHead.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.picHead.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.picHead.Image = ((System.Drawing.Image)(resources.GetObject("picHead.Image")));
            this.picHead.ImageName = "";
            this.picHead.Location = new System.Drawing.Point(0, 0);
            this.picHead.Name = "picHead";
            this.picHead.Size = new System.Drawing.Size(240, 29);
            this.picHead.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picHead.TextColor = System.Drawing.Color.Empty;
            this.picHead.TextFont = null;
            this.picHead.TitleText = "荷主選択";
            this.picHead.TransparentColor = System.Drawing.Color.White;
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
            this.btnBack.BackgroundImage = null;
            this.btnBack.CommitKey = null;
            this.btnBack.HotKey = null;
            this.btnBack.ImageName = "WMSPDA.Images.back1.png";
            this.btnBack.Location = new System.Drawing.Point(3, 5);
            this.btnBack.Name = "btnBack";
            this.btnBack.RoundButton = false;
            this.btnBack.Size = new System.Drawing.Size(26, 17);
            this.btnBack.TabIndex = 7;
            this.btnBack.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnClear
            // 
            this.btnClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnClear.BackColor = System.Drawing.Color.Brown;
            this.btnClear.BackgroundImage = null;
            this.btnClear.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(51)))), ((int)(((byte)(0)))));
            this.btnClear.CommitKey = null;
            this.btnClear.ForeColor = System.Drawing.Color.White;
            this.btnClear.HotKey = "keyf1";
            this.btnClear.ImageName = "";
            this.btnClear.Location = new System.Drawing.Point(0, 268);
            this.btnClear.Name = "btnClear";
            this.btnClear.RoundButton = true;
            this.btnClear.Size = new System.Drawing.Size(101, 27);
            this.btnClear.TabIndex = 9;
            this.btnClear.Text = "F1 クリア";
            this.btnClear.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // bindingSource
            // 
            this.bindingSource.DataSource = typeof(Entity.OwnerInfo);
            // 
            // cboOwner
            // 
            this.cboOwner.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.cboOwner.DataSource = this.bindingSource;
            this.cboOwner.DisplayMember = "BindText";
            this.cboOwner.Location = new System.Drawing.Point(7, 51);
            this.cboOwner.Name = "cboOwner";
            this.cboOwner.Size = new System.Drawing.Size(224, 30);
            this.cboOwner.TabIndex = 13;
            this.cboOwner.Text = "comboBoxEx1";
            this.cboOwner.ValueMember = "id";
            this.cboOwner.KeyDown += new System.Windows.Forms.KeyEventHandler(this.cboOwner_KeyDown);
            // 
            // lblName
            // 
            this.lblName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(203)))), ((int)(((byte)(254)))));
            this.lblName.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "name", true));
            this.lblName.Location = new System.Drawing.Point(7, 104);
            this.lblName.Name = "lblName";
            this.lblName.Size = new System.Drawing.Size(224, 27);
            // 
            // lblWhName
            // 
            this.lblWhName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(203)))), ((int)(((byte)(254)))));
            this.lblWhName.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "WhName", true));
            this.lblWhName.Location = new System.Drawing.Point(7, 152);
            this.lblWhName.Name = "lblWhName";
            this.lblWhName.Size = new System.Drawing.Size(224, 27);
            // 
            // frmOwnerList
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.lblWhName);
            this.Controls.Add(this.lblName);
            this.Controls.Add(this.cboOwner);
            this.Controls.Add(this.btnClear);
            this.Controls.Add(this.picBack);
            this.Controls.Add(this.btnBack);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.picHead);
            this.Name = "frmOwnerList";
            this.Text = "荷主選択";
            this.Load += new System.EventHandler(this.frmLogin_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.frmLogin_KeyDown);
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.PictureEx picHead;
        private Framework.ButtonEx btnOK;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.ButtonEx btnClear;
        private Framework.ComboBoxEx cboOwner;
        private System.Windows.Forms.BindingSource bindingSource;
        private System.Windows.Forms.Label lblName;
        private System.Windows.Forms.Label lblWhName;




    }
}