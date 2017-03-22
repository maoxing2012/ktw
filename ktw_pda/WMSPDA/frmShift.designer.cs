namespace WMSPDA
{
    partial class frmShift
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
            this.btnHome = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.dataGridTextBoxColumn1 = new System.Windows.Forms.DataGridTextBoxColumn();
            this.tsMain = new System.Windows.Forms.DataGridTableStyle();
            this.dataGridTextBoxColumn2 = new System.Windows.Forms.DataGridTextBoxColumn();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.Controls.Add(this.menuIbdHisList);
            this.pnlDetail.Controls.Add(this.menuInvList);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(320, 294);
            // 
            // menuIbdHisList
            // 
            this.menuIbdHisList.BackgroundImage = null;
            this.menuIbdHisList.CommitKey = null;
            this.menuIbdHisList.HotKey = "key2";
            this.menuIbdHisList.ImageName = "WMSPDA.Images.menu.png";
            this.menuIbdHisList.Location = new System.Drawing.Point(20, 84);
            this.menuIbdHisList.Name = "menuIbdHisList";
            this.menuIbdHisList.RoundButton = false;
            this.menuIbdHisList.Size = new System.Drawing.Size(280, 25);
            this.menuIbdHisList.TabIndex = 6;
            this.menuIbdHisList.Tag = "menuShelfBulkload";
            this.menuIbdHisList.Text = "2.散件移位";
            this.menuIbdHisList.TransparentColor = System.Drawing.Color.White;
            this.menuIbdHisList.Click += new System.EventHandler(this.Button_Click);
            // 
            // menuInvList
            // 
            this.menuInvList.BackgroundImage = null;
            this.menuInvList.CommitKey = null;
            this.menuInvList.HotKey = "key1";
            this.menuInvList.ImageName = "WMSPDA.Images.menu.png";
            this.menuInvList.Location = new System.Drawing.Point(20, 52);
            this.menuInvList.Name = "menuInvList";
            this.menuInvList.RoundButton = false;
            this.menuInvList.Size = new System.Drawing.Size(280, 25);
            this.menuInvList.TabIndex = 5;
            this.menuInvList.Tag = "menuShelfIn";
            this.menuInvList.Text = "1.整托移位";
            this.menuInvList.TransparentColor = System.Drawing.Color.White;
            this.menuInvList.Click += new System.EventHandler(this.Button_Click);
            // 
            // picBack
            // 
            this.picBack.ImageName = "WMSPDA.Images.back2.gif";
            this.picBack.Location = new System.Drawing.Point(11, 8);
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
            this.btnBack.Location = new System.Drawing.Point(4, 5);
            this.btnBack.Name = "btnBack";
            this.btnBack.RoundButton = false;
            this.btnBack.Size = new System.Drawing.Size(26, 17);
            this.btnBack.TabIndex = 2;
            this.btnBack.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnHome
            // 
            this.btnHome.BackgroundImage = null;
            this.btnHome.CommitKey = null;
            this.btnHome.HotKey = null;
            this.btnHome.ImageName = "WMSPDA.Images.home.png";
            this.btnHome.Location = new System.Drawing.Point(292, 1);
            this.btnHome.Name = "btnHome";
            this.btnHome.RoundButton = false;
            this.btnHome.Size = new System.Drawing.Size(26, 26);
            this.btnHome.TabIndex = 3;
            this.btnHome.TransparentColor = System.Drawing.Color.White;
            this.btnHome.Click += new System.EventHandler(this.btnHome_Click);
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
            this.picHead.TitleText = "移位类型";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // dataGridTextBoxColumn1
            // 
            this.dataGridTextBoxColumn1.Format = "";
            this.dataGridTextBoxColumn1.FormatInfo = null;
            this.dataGridTextBoxColumn1.MappingName = "Num";
            this.dataGridTextBoxColumn1.Width = 30;
            // 
            // tsMain
            // 
            this.tsMain.GridColumnStyles.Add(this.dataGridTextBoxColumn1);
            this.tsMain.GridColumnStyles.Add(this.dataGridTextBoxColumn2);
            this.tsMain.MappingName = "ASNDetail";
            // 
            // dataGridTextBoxColumn2
            // 
            this.dataGridTextBoxColumn2.Format = "";
            this.dataGridTextBoxColumn2.FormatInfo = null;
            this.dataGridTextBoxColumn2.MappingName = "Content";
            // 
            // frmShift
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(320, 294);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmShift";
            this.Text = "移位类型";
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.PictureEx picBack;
        private Framework.ButtonEx btnBack;
        private Framework.ButtonEx btnHome;
        private Framework.PictureEx picHead;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn1;
        private System.Windows.Forms.DataGridTableStyle tsMain;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn2;
        private Framework.ButtonEx menuIbdHisList;
        private Framework.ButtonEx menuInvList;
    }
}
