namespace WMSPDA
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
            this.menuInvHisList = new Framework.ButtonEx();
            this.menuObdHisList = new Framework.ButtonEx();
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
            this.pnlDetail.Controls.Add(this.menuInvHisList);
            this.pnlDetail.Controls.Add(this.menuObdHisList);
            this.pnlDetail.Controls.Add(this.menuIbdHisList);
            this.pnlDetail.Controls.Add(this.menuInvList);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnRefresh);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(320, 294);
            // 
            // menuInvHisList
            // 
            this.menuInvHisList.BackgroundImage = null;
            this.menuInvHisList.CommitKey = null;
            this.menuInvHisList.HotKey = "key4";
            this.menuInvHisList.ImageName = "WMSPDA.Images.menu.png";
            this.menuInvHisList.Location = new System.Drawing.Point(20, 157);
            this.menuInvHisList.Name = "menuInvHisList";
            this.menuInvHisList.RoundButton = false;
            this.menuInvHisList.Size = new System.Drawing.Size(280, 25);
            this.menuInvHisList.TabIndex = 3;
            this.menuInvHisList.Text = "4.库存履历";
            this.menuInvHisList.TransparentColor = System.Drawing.Color.White;
            this.menuInvHisList.Visible = false;
            this.menuInvHisList.Click += new System.EventHandler(this.Button_Click);
            // 
            // menuObdHisList
            // 
            this.menuObdHisList.BackgroundImage = null;
            this.menuObdHisList.CommitKey = null;
            this.menuObdHisList.HotKey = "key3";
            this.menuObdHisList.ImageName = "WMSPDA.Images.menu.png";
            this.menuObdHisList.Location = new System.Drawing.Point(20, 125);
            this.menuObdHisList.Name = "menuObdHisList";
            this.menuObdHisList.RoundButton = false;
            this.menuObdHisList.Size = new System.Drawing.Size(280, 25);
            this.menuObdHisList.TabIndex = 2;
            this.menuObdHisList.Text = "3.发货履历";
            this.menuObdHisList.TransparentColor = System.Drawing.Color.White;
            this.menuObdHisList.Visible = false;
            this.menuObdHisList.Click += new System.EventHandler(this.Button_Click);
            // 
            // menuIbdHisList
            // 
            this.menuIbdHisList.BackgroundImage = null;
            this.menuIbdHisList.CommitKey = null;
            this.menuIbdHisList.HotKey = "key2";
            this.menuIbdHisList.ImageName = "WMSPDA.Images.menu.png";
            this.menuIbdHisList.Location = new System.Drawing.Point(20, 93);
            this.menuIbdHisList.Name = "menuIbdHisList";
            this.menuIbdHisList.RoundButton = false;
            this.menuIbdHisList.Size = new System.Drawing.Size(280, 25);
            this.menuIbdHisList.TabIndex = 1;
            this.menuIbdHisList.Text = "2.收货履历";
            this.menuIbdHisList.TransparentColor = System.Drawing.Color.White;
            this.menuIbdHisList.Visible = false;
            this.menuIbdHisList.Click += new System.EventHandler(this.Button_Click);
            // 
            // menuInvList
            // 
            this.menuInvList.BackgroundImage = null;
            this.menuInvList.CommitKey = null;
            this.menuInvList.HotKey = "key1";
            this.menuInvList.ImageName = "WMSPDA.Images.menu.png";
            this.menuInvList.Location = new System.Drawing.Point(20, 61);
            this.menuInvList.Name = "menuInvList";
            this.menuInvList.RoundButton = false;
            this.menuInvList.Size = new System.Drawing.Size(280, 25);
            this.menuInvList.TabIndex = 0;
            this.menuInvList.Tag = "inventorySearch";
            this.menuInvList.Text = "1.库存查询";
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
            this.btnRefresh.BackgroundImage = null;
            this.btnRefresh.CommitKey = null;
            this.btnRefresh.HotKey = null;
            this.btnRefresh.ImageName = "WMSPDA.Images.refresh.png";
            this.btnRefresh.Location = new System.Drawing.Point(291, 1);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.RoundButton = false;
            this.btnRefresh.Size = new System.Drawing.Size(26, 26);
            this.btnRefresh.TabIndex = 5;
            this.btnRefresh.TransparentColor = System.Drawing.Color.White;
            this.btnRefresh.Visible = false;
            this.btnRefresh.Click += new System.EventHandler(this.btnRefresh_Click);
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
            this.picHead.TitleText = "其他";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // frmOther
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(320, 294);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmOther";
            this.Text = "其他";
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
        private Framework.ButtonEx menuInvHisList;
        private Framework.ButtonEx menuObdHisList;
        private Framework.ButtonEx menuIbdHisList;
        private Framework.ButtonEx menuInvList;

    }
}
