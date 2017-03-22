namespace Shift
{
    partial class frmInvInfoSearch
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
            this.F1 = new Framework.ButtonEx();
            this.enter = new Framework.ButtonEx();
            this.listBox1 = new System.Windows.Forms.ListBox();
            this.txtDescBinCode = new Framework.TextEx();
            this.txtBinCode = new Framework.TextEx();
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
            this.pnlDetail.Controls.Add(this.F1);
            this.pnlDetail.Controls.Add(this.enter);
            this.pnlDetail.Controls.Add(this.listBox1);
            this.pnlDetail.Controls.Add(this.txtDescBinCode);
            this.pnlDetail.Controls.Add(this.txtBinCode);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // F1
            // 
            this.F1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.F1.BackColor = System.Drawing.Color.Brown;
            this.F1.BackgroundImage = null;
            this.F1.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(51)))), ((int)(((byte)(0)))));
            this.F1.CommitKey = null;
            this.F1.ForeColor = System.Drawing.Color.White;
            this.F1.HotKey = "keyf1";
            this.F1.ImageName = "";
            this.F1.Location = new System.Drawing.Point(0, 268);
            this.F1.Name = "F1";
            this.F1.RoundButton = true;
            this.F1.Size = new System.Drawing.Size(101, 27);
            this.F1.TabIndex = 58;
            this.F1.Text = "F1 クリア";
            this.F1.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.F1.Click += new System.EventHandler(this.F1_Click);
            // 
            // enter
            // 
            this.enter.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.enter.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.enter.BackgroundImage = null;
            this.enter.BorderColor = System.Drawing.Color.Transparent;
            this.enter.CommitKey = null;
            this.enter.ForeColor = System.Drawing.Color.White;
            this.enter.HotKey = null;
            this.enter.ImageName = "";
            this.enter.Location = new System.Drawing.Point(99, 268);
            this.enter.Name = "enter";
            this.enter.RoundButton = true;
            this.enter.Size = new System.Drawing.Size(139, 27);
            this.enter.TabIndex = 57;
            this.enter.Text = "Enter 確定";
            this.enter.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.enter.Click += new System.EventHandler(this.enter_Click);
            // 
            // listBox1
            // 
            this.listBox1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.listBox1.Location = new System.Drawing.Point(7, 66);
            this.listBox1.Name = "listBox1";
            this.listBox1.Size = new System.Drawing.Size(225, 162);
            this.listBox1.TabIndex = 14;
            // 
            // txtDescBinCode
            // 
            this.txtDescBinCode.BorderColor = System.Drawing.Color.Empty;
            this.txtDescBinCode.CommitKey = null;
            this.txtDescBinCode.Field = null;
            this.txtDescBinCode.Location = new System.Drawing.Point(7, 237);
            this.txtDescBinCode.Name = "txtDescBinCode";
            this.txtDescBinCode.Pattern = null;
            this.txtDescBinCode.Size = new System.Drawing.Size(224, 25);
            this.txtDescBinCode.TabIndex = 13;
            this.txtDescBinCode.Tip = "移動先の棚番入力";
            this.txtDescBinCode.ValidsCommit = null;
            this.txtDescBinCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtDescBinCode_KeyDown);
            // 
            // txtBinCode
            // 
            this.txtBinCode.BorderColor = System.Drawing.Color.Empty;
            this.txtBinCode.CommitKey = null;
            this.txtBinCode.Field = null;
            this.txtBinCode.Location = new System.Drawing.Point(7, 35);
            this.txtBinCode.Name = "txtBinCode";
            this.txtBinCode.Pattern = null;
            this.txtBinCode.Size = new System.Drawing.Size(224, 25);
            this.txtBinCode.TabIndex = 7;
            this.txtBinCode.Tip = "移動元の棚番入力";
            this.txtBinCode.ValidsCommit = null;
            this.txtBinCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBinCode_KeyDown);
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
            // frmInvInfoSearch
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.BackColor = System.Drawing.SystemColors.Window;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmInvInfoSearch";
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
        private Framework.TextEx txtBinCode;
        private Framework.TextEx txtDescBinCode;
        private System.Windows.Forms.ListBox listBox1;
        private Framework.ButtonEx F1;
        private Framework.ButtonEx enter;

    }
}
