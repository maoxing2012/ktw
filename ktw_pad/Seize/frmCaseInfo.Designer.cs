namespace Seize
{
    partial class frmCaseInfo
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblInfo = new System.Windows.Forms.Label();
            this.lblSkuCode = new System.Windows.Forms.Label();
            this.lblQty = new System.Windows.Forms.Label();
            this.lblCaseQty = new System.Windows.Forms.Label();
            this.lblSpecs = new System.Windows.Forms.Label();
            this.txtBarCode = new Framework.TextEx();
            this.lblNum = new System.Windows.Forms.Label();
            this.lblUnit = new System.Windows.Forms.Label();
            this.lblSkuName = new System.Windows.Forms.Label();
            this.txtCaseNumber = new Framework.TextEx();
            this.lblExpDate = new System.Windows.Forms.Label();
            this.btnOK = new Framework.ButtonEx();
            this.btnClear = new Framework.ButtonEx();
            this.label1 = new System.Windows.Forms.Label();
            this.lblBarcode = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).BeginInit();
            this.pnlTop.SuspendLayout();
            this.SuspendLayout();
            // 
            // lblTitle
            // 
            this.lblTitle.Text = "出荷検品（日本食研）";
            // 
            // lblInfo
            // 
            this.lblInfo.BackColor = System.Drawing.Color.Yellow;
            this.lblInfo.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblInfo.Location = new System.Drawing.Point(28, 369);
            this.lblInfo.Name = "lblInfo";
            this.lblInfo.Size = new System.Drawing.Size(544, 34);
            this.lblInfo.TabIndex = 77;
            this.lblInfo.Visible = false;
            // 
            // lblSkuCode
            // 
            this.lblSkuCode.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuCode.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblSkuCode.Location = new System.Drawing.Point(437, 177);
            this.lblSkuCode.Name = "lblSkuCode";
            this.lblSkuCode.Size = new System.Drawing.Size(135, 34);
            this.lblSkuCode.TabIndex = 78;
            // 
            // lblQty
            // 
            this.lblQty.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblQty.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblQty.Location = new System.Drawing.Point(437, 129);
            this.lblQty.Name = "lblQty";
            this.lblQty.Size = new System.Drawing.Size(135, 34);
            this.lblQty.TabIndex = 79;
            // 
            // lblCaseQty
            // 
            this.lblCaseQty.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblCaseQty.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblCaseQty.Location = new System.Drawing.Point(438, 81);
            this.lblCaseQty.Name = "lblCaseQty";
            this.lblCaseQty.Size = new System.Drawing.Size(135, 34);
            this.lblCaseQty.TabIndex = 80;
            this.lblCaseQty.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // lblSpecs
            // 
            this.lblSpecs.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSpecs.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblSpecs.Location = new System.Drawing.Point(28, 321);
            this.lblSpecs.Name = "lblSpecs";
            this.lblSpecs.Size = new System.Drawing.Size(544, 34);
            this.lblSpecs.TabIndex = 81;
            // 
            // txtBarCode
            // 
            this.txtBarCode.BorderColor = System.Drawing.Color.Empty;
            this.txtBarCode.CommitKey = null;
            this.txtBarCode.Field = null;
            this.txtBarCode.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.txtBarCode.Location = new System.Drawing.Point(193, 129);
            this.txtBarCode.Name = "txtBarCode";
            this.txtBarCode.Pattern = "";
            this.txtBarCode.Size = new System.Drawing.Size(238, 34);
            this.txtBarCode.TabIndex = 1;
            this.txtBarCode.Tip = "商品バーコード入力";
            this.txtBarCode.ValidsCommit = null;
            this.txtBarCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtBarCode_KeyDown);
            // 
            // lblNum
            // 
            this.lblNum.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblNum.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblNum.Location = new System.Drawing.Point(437, 273);
            this.lblNum.Name = "lblNum";
            this.lblNum.Size = new System.Drawing.Size(135, 34);
            this.lblNum.TabIndex = 82;
            // 
            // lblUnit
            // 
            this.lblUnit.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblUnit.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblUnit.Location = new System.Drawing.Point(28, 273);
            this.lblUnit.Name = "lblUnit";
            this.lblUnit.Size = new System.Drawing.Size(403, 34);
            this.lblUnit.TabIndex = 83;
            // 
            // lblSkuName
            // 
            this.lblSkuName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblSkuName.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblSkuName.Location = new System.Drawing.Point(28, 225);
            this.lblSkuName.Name = "lblSkuName";
            this.lblSkuName.Size = new System.Drawing.Size(544, 34);
            this.lblSkuName.TabIndex = 84;
            // 
            // txtCaseNumber
            // 
            this.txtCaseNumber.BorderColor = System.Drawing.Color.Empty;
            this.txtCaseNumber.CommitKey = null;
            this.txtCaseNumber.DoSelectNext = false;
            this.txtCaseNumber.Field = "caseNumber";
            this.txtCaseNumber.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.txtCaseNumber.Location = new System.Drawing.Point(193, 82);
            this.txtCaseNumber.Name = "txtCaseNumber";
            this.txtCaseNumber.Pattern = "";
            this.txtCaseNumber.Size = new System.Drawing.Size(238, 34);
            this.txtCaseNumber.TabIndex = 0;
            this.txtCaseNumber.Tip = "個口番号入力";
            this.txtCaseNumber.ValidsCommit = null;
            this.txtCaseNumber.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtCaseNumber_KeyDown);
            // 
            // lblExpDate
            // 
            this.lblExpDate.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(204)))), ((int)(((byte)(204)))), ((int)(((byte)(255)))));
            this.lblExpDate.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblExpDate.Location = new System.Drawing.Point(28, 177);
            this.lblExpDate.Name = "lblExpDate";
            this.lblExpDate.Size = new System.Drawing.Size(403, 34);
            this.lblExpDate.TabIndex = 85;
            // 
            // btnOK
            // 
            this.btnOK.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(42)))), ((int)(((byte)(144)))), ((int)(((byte)(198)))));
            this.btnOK.CommitKey = "";
            this.btnOK.FlatAppearance.BorderSize = 0;
            this.btnOK.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnOK.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.btnOK.HotKey = "";
            this.btnOK.ImageName = "skip";
            this.btnOK.Location = new System.Drawing.Point(300, 451);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(300, 50);
            this.btnOK.TabIndex = 3;
            this.btnOK.Text = "Enter 確定";
            this.btnOK.UseVisualStyleBackColor = false;
            this.btnOK.Click += new System.EventHandler(this.enter_Click);
            // 
            // btnClear
            // 
            this.btnClear.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(48)))), ((int)(((byte)(0)))));
            this.btnClear.CommitKey = "";
            this.btnClear.FlatAppearance.BorderSize = 0;
            this.btnClear.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnClear.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.btnClear.HotKey = "keyf1";
            this.btnClear.ImageName = "skip";
            this.btnClear.Location = new System.Drawing.Point(0, 451);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(300, 50);
            this.btnClear.TabIndex = 2;
            this.btnClear.Text = "F1 クリア";
            this.btnClear.UseVisualStyleBackColor = false;
            this.btnClear.Click += new System.EventHandler(this.F1_Click);
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.label1.Location = new System.Drawing.Point(17, 85);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(175, 29);
            this.label1.TabIndex = 89;
            this.label1.Text = "個口番号";
            this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // lblBarcode
            // 
            this.lblBarcode.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblBarcode.Location = new System.Drawing.Point(17, 132);
            this.lblBarcode.Name = "lblBarcode";
            this.lblBarcode.Size = new System.Drawing.Size(175, 29);
            this.lblBarcode.TabIndex = 89;
            this.lblBarcode.Text = "商品バーコード";
            this.lblBarcode.TextAlign = System.Drawing.ContentAlignment.TopRight;
            // 
            // frmCaseInfo
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(600, 500);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.btnClear);
            this.Controls.Add(this.lblInfo);
            this.Controls.Add(this.lblSkuCode);
            this.Controls.Add(this.lblQty);
            this.Controls.Add(this.lblCaseQty);
            this.Controls.Add(this.lblSpecs);
            this.Controls.Add(this.txtBarCode);
            this.Controls.Add(this.lblNum);
            this.Controls.Add(this.lblUnit);
            this.Controls.Add(this.lblSkuName);
            this.Controls.Add(this.txtCaseNumber);
            this.Controls.Add(this.lblExpDate);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lblBarcode);
            this.Name = "frmCaseInfo";
            this.ShowInTaskbar = false;
            this.Text = "frmCaseInfo";
            this.Controls.SetChildIndex(this.lblBarcode, 0);
            this.Controls.SetChildIndex(this.label1, 0);
            this.Controls.SetChildIndex(this.lblExpDate, 0);
            this.Controls.SetChildIndex(this.pnlTop, 0);
            this.Controls.SetChildIndex(this.txtCaseNumber, 0);
            this.Controls.SetChildIndex(this.lblSkuName, 0);
            this.Controls.SetChildIndex(this.lblUnit, 0);
            this.Controls.SetChildIndex(this.lblNum, 0);
            this.Controls.SetChildIndex(this.txtBarCode, 0);
            this.Controls.SetChildIndex(this.lblSpecs, 0);
            this.Controls.SetChildIndex(this.lblCaseQty, 0);
            this.Controls.SetChildIndex(this.lblQty, 0);
            this.Controls.SetChildIndex(this.lblSkuCode, 0);
            this.Controls.SetChildIndex(this.lblInfo, 0);
            this.Controls.SetChildIndex(this.btnClear, 0);
            this.Controls.SetChildIndex(this.btnOK, 0);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).EndInit();
            this.pnlTop.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblInfo;
        private System.Windows.Forms.Label lblSkuCode;
        private System.Windows.Forms.Label lblQty;
        private System.Windows.Forms.Label lblCaseQty;
        private System.Windows.Forms.Label lblSpecs;
        private Framework.TextEx txtBarCode;
        private System.Windows.Forms.Label lblNum;
        private System.Windows.Forms.Label lblUnit;
        private System.Windows.Forms.Label lblSkuName;
        private Framework.TextEx txtCaseNumber;
        private System.Windows.Forms.Label lblExpDate;
        private Framework.ButtonEx btnOK;
        private Framework.ButtonEx btnClear;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblBarcode;
    }
}