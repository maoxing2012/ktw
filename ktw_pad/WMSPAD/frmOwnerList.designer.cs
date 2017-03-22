namespace WMSPAD
{
    partial class frmOwnerList
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
            this.components = new System.ComponentModel.Container();
            this.btnOK = new Framework.ButtonEx();
            this.btnClear = new Framework.ButtonEx();
            this.lblWhName = new System.Windows.Forms.Label();
            this.bindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.lblName = new System.Windows.Forms.Label();
            this.cboOwner = new Framework.ComboBoxEx();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).BeginInit();
            this.pnlTop.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // lblTitle
            // 
            this.lblTitle.Text = "荷主選択（日本食研）";
            // 
            // btnRight
            // 
            this.btnRight.Visible = false;
            // 
            // btnOK
            // 
            this.btnOK.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(42)))), ((int)(((byte)(144)))), ((int)(((byte)(198)))));
            this.btnOK.CommitKey = "";
            this.btnOK.FlatAppearance.BorderSize = 0;
            this.btnOK.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnOK.Font = new System.Drawing.Font("MS UI Gothic", 20.25F);
            this.btnOK.HotKey = "";
            this.btnOK.ImageName = "skip";
            this.btnOK.Location = new System.Drawing.Point(300, 451);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(300, 50);
            this.btnOK.TabIndex = 2;
            this.btnOK.Text = "Enter 確定";
            this.btnOK.UseVisualStyleBackColor = false;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // btnClear
            // 
            this.btnClear.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(48)))), ((int)(((byte)(0)))));
            this.btnClear.CommitKey = "";
            this.btnClear.FlatAppearance.BorderSize = 0;
            this.btnClear.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnClear.Font = new System.Drawing.Font("MS UI Gothic", 20.25F);
            this.btnClear.HotKey = "keyf1";
            this.btnClear.ImageName = "skip";
            this.btnClear.Location = new System.Drawing.Point(0, 451);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(300, 50);
            this.btnClear.TabIndex = 1;
            this.btnClear.Text = "F1 クリア";
            this.btnClear.UseVisualStyleBackColor = false;
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // lblWhName
            // 
            this.lblWhName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(203)))), ((int)(((byte)(254)))));
            this.lblWhName.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "WhName", true));
            this.lblWhName.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblWhName.Location = new System.Drawing.Point(96, 292);
            this.lblWhName.Name = "lblWhName";
            this.lblWhName.Size = new System.Drawing.Size(408, 32);
            this.lblWhName.TabIndex = 14;
            // 
            // bindingSource
            // 
            this.bindingSource.DataSource = typeof(Entity.OwnerInfo);
            // 
            // lblName
            // 
            this.lblName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(203)))), ((int)(((byte)(203)))), ((int)(((byte)(254)))));
            this.lblName.DataBindings.Add(new System.Windows.Forms.Binding("Text", this.bindingSource, "name", true));
            this.lblName.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.lblName.Location = new System.Drawing.Point(96, 219);
            this.lblName.Name = "lblName";
            this.lblName.Size = new System.Drawing.Size(408, 32);
            this.lblName.TabIndex = 15;
            // 
            // cboOwner
            // 
            this.cboOwner.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.cboOwner.DataSource = this.bindingSource;
            this.cboOwner.DisplayMember = "BindText";
            this.cboOwner.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.cboOwner.Location = new System.Drawing.Point(96, 123);
            this.cboOwner.Name = "cboOwner";
            this.cboOwner.Size = new System.Drawing.Size(408, 35);
            this.cboOwner.TabIndex = 0;
            this.cboOwner.ValueMember = "id";
            this.cboOwner.KeyDown += new System.Windows.Forms.KeyEventHandler(this.cboOwner_KeyDown);
            // 
            // frmOwnerList
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(600, 500);
            this.Controls.Add(this.lblWhName);
            this.Controls.Add(this.lblName);
            this.Controls.Add(this.cboOwner);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.btnClear);
            this.Name = "frmOwnerList";
            this.ShowInTaskbar = false;
            this.Text = "frmOwnerList";
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.frmOwnerList_KeyDown);
            this.Controls.SetChildIndex(this.pnlTop, 0);
            this.Controls.SetChildIndex(this.btnClear, 0);
            this.Controls.SetChildIndex(this.btnOK, 0);
            this.Controls.SetChildIndex(this.cboOwner, 0);
            this.Controls.SetChildIndex(this.lblName, 0);
            this.Controls.SetChildIndex(this.lblWhName, 0);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).EndInit();
            this.pnlTop.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.ButtonEx btnOK;
        private Framework.ButtonEx btnClear;
        private System.Windows.Forms.Label lblWhName;
        private System.Windows.Forms.Label lblName;
        private Framework.ComboBoxEx cboOwner;
        private System.Windows.Forms.BindingSource bindingSource;
    }
}