namespace Framework
{
    partial class BaseForm
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
            if (disposing)
            {
                if (_hotDic != null)
                {
                    _hotDic.Clear();
                    _hotDic = null;
                }
                if (_buttons != null)
                {
                    _buttons.Clear();
                    _buttons = null;
                }
                if (_btnStatus != null)
                {
                    _btnStatus.Clear();
                    _btnStatus = null;
                }
            }
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
            this.pnlTop = new System.Windows.Forms.Panel();
            this.lblTitle = new Framework.LabelEx();
            this.btnLeftMini = new Framework.PictureEx();
            this.btnRight = new Framework.PictureEx();
            this.btnLeft = new Framework.PictureEx();
            this.pnlTop.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlTop
            // 
            this.pnlTop.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.pnlTop.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(153)))), ((int)(((byte)(204)))));
            this.pnlTop.Controls.Add(this.lblTitle);
            this.pnlTop.Controls.Add(this.btnLeftMini);
            this.pnlTop.Controls.Add(this.btnRight);
            this.pnlTop.Controls.Add(this.btnLeft);
            this.pnlTop.Location = new System.Drawing.Point(0, 0);
            this.pnlTop.Name = "pnlTop";
            this.pnlTop.Size = new System.Drawing.Size(600, 47);
            this.pnlTop.TabIndex = 1;
            // 
            // lblTitle
            // 
            this.lblTitle.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.lblTitle.Font = new System.Drawing.Font("MS UI Gothic", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(128)));
            this.lblTitle.ForeColor = System.Drawing.Color.White;
            this.lblTitle.Location = new System.Drawing.Point(57, 4);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(496, 42);
            this.lblTitle.TabIndex = 2;
            this.lblTitle.Text = "南大阪新倉庫（日本食研）";
            this.lblTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // btnLeftMini
            // 
            this.btnLeftMini.Location = new System.Drawing.Point(17, 13);
            this.btnLeftMini.Name = "btnLeftMini";
            this.btnLeftMini.Size = new System.Drawing.Size(33, 22);
            this.btnLeftMini.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.btnLeftMini.TabIndex = 1;
            this.btnLeftMini.TabStop = false;
            this.btnLeftMini.Visible = false;
            this.btnLeftMini.Click += new System.EventHandler(this.btnLeft_Click);
            // 
            // btnRight
            // 
            this.btnRight.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnRight.Location = new System.Drawing.Point(555, 0);
            this.btnRight.Name = "btnRight";
            this.btnRight.Size = new System.Drawing.Size(45, 47);
            this.btnRight.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.btnRight.TabIndex = 0;
            this.btnRight.TabStop = false;
            this.btnRight.Click += new System.EventHandler(this.btnRight_Click);
            // 
            // btnLeft
            // 
            this.btnLeft.Location = new System.Drawing.Point(0, 3);
            this.btnLeft.Name = "btnLeft";
            this.btnLeft.Size = new System.Drawing.Size(57, 44);
            this.btnLeft.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.btnLeft.TabIndex = 0;
            this.btnLeft.TabStop = false;
            this.btnLeft.Click += new System.EventHandler(this.btnLeft_Click);
            this.btnLeft.Paint += new System.Windows.Forms.PaintEventHandler(this.btnLeft_Paint);
            // 
            // BaseForm
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(600, 500);
            this.ControlBox = false;
            this.Controls.Add(this.pnlTop);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.KeyPreview = true;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "BaseForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "BaseForm";
            this.Load += new System.EventHandler(this.BaseForm_Load);
            this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.BaseForm_KeyUp);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.BaseForm_KeyDown);
            this.pnlTop.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).EndInit();
            this.ResumeLayout(false);

        }


        #endregion

        protected LabelEx lblTitle;
        protected PictureEx btnLeftMini;
        protected PictureEx btnRight;
        protected PictureEx btnLeft;
        protected System.Windows.Forms.Panel pnlTop;
    }
}