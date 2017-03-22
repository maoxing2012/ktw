namespace Seize
{
    partial class frmCaseInfo
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmCaseInfo));
            this.pnlDetail = new Framework.AlphaPanel();
            this.lblInfo = new System.Windows.Forms.Label();
            this.lblSkuCode = new System.Windows.Forms.Label();
            this.lblQty = new System.Windows.Forms.Label();
            this.lblCaseQty = new System.Windows.Forms.Label();
            this.F1 = new Framework.ButtonEx();
            this.enter = new Framework.ButtonEx();
            this.lblSpecs = new System.Windows.Forms.Label();
            this.txtBarCode = new Framework.TextEx();
            this.lblNum = new System.Windows.Forms.Label();
            this.lblUnit = new System.Windows.Forms.Label();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.txtCaseNumber = new Framework.TextEx();
            this.picBack = new Framework.PictureEx();
            this.btnBack = new Framework.ButtonEx();
            this.btnHome = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.lblExpDate = new System.Windows.Forms.Label();
            this.txtExpDate = new Framework.TextEx();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picBack)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.lblInfo);
            this.pnlDetail.Controls.Add(this.lblSkuCode);
            this.pnlDetail.Controls.Add(this.lblQty);
            this.pnlDetail.Controls.Add(this.lblCaseQty);
            this.pnlDetail.Controls.Add(this.F1);
            this.pnlDetail.Controls.Add(this.enter);
            this.pnlDetail.Controls.Add(this.lblSpecs);
            this.pnlDetail.Controls.Add(this.txtBarCode);
            this.pnlDetail.Controls.Add(this.lblNum);
            this.pnlDetail.Controls.Add(this.lblUnit);
            this.pnlDetail.Controls.Add(this.lblSkuName);
            this.pnlDetail.Controls.Add(this.txtCaseNumber);
            this.pnlDetail.Controls.Add(this.picBack);
            this.pnlDetail.Controls.Add(this.btnBack);
            this.pnlDetail.Controls.Add(this.btnHome);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Controls.Add(this.lblExpDate);
            this.pnlDetail.Controls.Add(this.txtExpDate);
            resources.ApplyResources(this.pnlDetail, "pnlDetail");
            this.pnlDetail.Name = "pnlDetail";
            // 
            // lblInfo
            // 
            this.lblInfo.BackColor = System.Drawing.Color.Yellow;
            resources.ApplyResources(this.lblInfo, "lblInfo");
            this.lblInfo.Name = "lblInfo";
            // 
            // lblSkuCode
            // 
            this.lblSkuCode.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            resources.ApplyResources(this.lblSkuCode, "lblSkuCode");
            this.lblSkuCode.Name = "lblSkuCode";
            // 
            // lblQty
            // 
            this.lblQty.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            resources.ApplyResources(this.lblQty, "lblQty");
            this.lblQty.Name = "lblQty";
            // 
            // lblCaseQty
            // 
            this.lblCaseQty.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            resources.ApplyResources(this.lblCaseQty, "lblCaseQty");
            this.lblCaseQty.Name = "lblCaseQty";
            // 
            // F1
            // 
            resources.ApplyResources(this.F1, "F1");
            this.F1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(51)))), ((int)(((byte)(0)))));
            this.F1.BackgroundImage = null;
            this.F1.BorderColor = System.Drawing.Color.Transparent;
            this.F1.ForeColor = System.Drawing.Color.White;
            this.F1.HotKey = "keyf1";
            this.F1.ImageName = "";
            this.F1.Name = "F1";
            this.F1.RoundButton = true;
            this.F1.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.F1.Click += new System.EventHandler(this.F1_Click);
            // 
            // enter
            // 
            resources.ApplyResources(this.enter, "enter");
            this.enter.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(42)))), ((int)(((byte)(144)))), ((int)(((byte)(198)))));
            this.enter.BackgroundImage = null;
            this.enter.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.enter.ForeColor = System.Drawing.Color.White;
            this.enter.ImageName = "";
            this.enter.Name = "enter";
            this.enter.RoundButton = true;
            this.enter.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.enter.Click += new System.EventHandler(this.enter_Click);
            // 
            // lblSpecs
            // 
            this.lblSpecs.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            resources.ApplyResources(this.lblSpecs, "lblSpecs");
            this.lblSpecs.Name = "lblSpecs";
            // 
            // txtBarCode
            // 
            this.txtBarCode.BorderColor = System.Drawing.Color.Empty;
            resources.ApplyResources(this.txtBarCode, "txtBarCode");
            this.txtBarCode.Name = "txtBarCode";
            this.txtBarCode.Tip = "商品バーコード入力";
            this.txtBarCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBarCode_KeyDown);
            // 
            // lblNum
            // 
            this.lblNum.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            resources.ApplyResources(this.lblNum, "lblNum");
            this.lblNum.Name = "lblNum";
            // 
            // lblUnit
            // 
            this.lblUnit.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            resources.ApplyResources(this.lblUnit, "lblUnit");
            this.lblUnit.Name = "lblUnit";
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            resources.ApplyResources(this.lblSkuName, "lblSkuName");
            this.lblSkuName.Name = "lblSkuName";
            // 
            // txtCaseNumber
            // 
            this.txtCaseNumber.BorderColor = System.Drawing.Color.Empty;
            this.txtCaseNumber.DoSelectNext = false;
            this.txtCaseNumber.Field = "caseNumber";
            resources.ApplyResources(this.txtCaseNumber, "txtCaseNumber");
            this.txtCaseNumber.Name = "txtCaseNumber";
            this.txtCaseNumber.Tip = "個口番号入力";
            this.txtCaseNumber.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCaseNumber_KeyDown);
            // 
            // picBack
            // 
            this.picBack.ImageName = "WMSPDA.Images.back2.gif";
            resources.ApplyResources(this.picBack, "picBack");
            this.picBack.Name = "picBack";
            this.picBack.TextColor = System.Drawing.Color.Empty;
            this.picBack.TransparentColor = System.Drawing.Color.White;
            this.picBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnBack
            // 
            this.btnBack.BackgroundImage = null;
            this.btnBack.ImageName = "WMSPDA.Images.back1.png";
            resources.ApplyResources(this.btnBack, "btnBack");
            this.btnBack.Name = "btnBack";
            this.btnBack.RoundButton = false;
            this.btnBack.TransparentColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.btnBack.Click += new System.EventHandler(this.btnBack_Click);
            // 
            // btnHome
            // 
            resources.ApplyResources(this.btnHome, "btnHome");
            this.btnHome.BackgroundImage = null;
            this.btnHome.ImageName = "WMSPDA.Images.home.png";
            this.btnHome.Name = "btnHome";
            this.btnHome.RoundButton = false;
            this.btnHome.TransparentColor = System.Drawing.Color.White;
            this.btnHome.Click += new System.EventHandler(this.btnHome_Click);
            // 
            // picHead
            // 
            resources.ApplyResources(this.picHead, "picHead");
            this.picHead.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.picHead.ImageName = "";
            this.picHead.Name = "picHead";
            this.picHead.TextColor = System.Drawing.Color.White;
            this.picHead.TextFont = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Bold);
            this.picHead.TitleText = "在庫照会・そのた";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // lblExpDate
            // 
            this.lblExpDate.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            resources.ApplyResources(this.lblExpDate, "lblExpDate");
            this.lblExpDate.Name = "lblExpDate";
            // 
            // txtExpDate
            // 
            this.txtExpDate.BorderColor = System.Drawing.Color.Empty;
            resources.ApplyResources(this.txtExpDate, "txtExpDate");
            this.txtExpDate.Name = "txtExpDate";
            this.txtExpDate.Tip = "賞味期限";
            this.txtExpDate.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtExpDate_KeyDown);
            // 
            // frmCaseInfo
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            resources.ApplyResources(this, "$this");
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmCaseInfo";
            this.Closed += new System.EventHandler(this.frmCaseInfo_Closed);
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
        private Framework.TextEx txtCaseNumber;
        private System.Windows.Forms.Label lblSkuName;
        private Framework.TextEx txtBarCode;
        private System.Windows.Forms.Label lblSpecs;
        private System.Windows.Forms.Label lblNum;
        private System.Windows.Forms.Label lblUnit;
        private Framework.ButtonEx F1;
        private Framework.ButtonEx enter;
        private System.Windows.Forms.Label lblSkuCode;
        private Framework.TextEx txtExpDate;
        private System.Windows.Forms.Label lblQty;
        private System.Windows.Forms.Label lblCaseQty;
        private System.Windows.Forms.Label lblExpDate;
        private System.Windows.Forms.Label lblInfo;

    }
}
