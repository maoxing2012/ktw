namespace Framework
{
    partial class frmSkuIdSearch
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
            this.pnlDetail = new Framework.AlphaPanel();
            this.bindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.grdData = new Framework.GridEx();
            this.tsMain = new System.Windows.Forms.DataGridTableStyle();
            this.dataGridTextBoxColumn3 = new System.Windows.Forms.DataGridTextBoxColumn();
            this.dataGridTextBoxColumn4 = new System.Windows.Forms.DataGridTextBoxColumn();
            this.btnExist = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.dataGridTableStyle1 = new System.Windows.Forms.DataGridTableStyle();
            this.dataGridTextBoxColumn1 = new System.Windows.Forms.DataGridTextBoxColumn();
            this.dataGridTextBoxColumn2 = new System.Windows.Forms.DataGridTextBoxColumn();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.grdData)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.grdData);
            this.pnlDetail.Controls.Add(this.btnExist);
            this.pnlDetail.Controls.Add(this.picHead);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(238, 295);
            // 
            // bindingSource
            // 
            this.bindingSource.DataSource = typeof(Framework.Sku);
            // 
            // grdData
            // 
            this.grdData.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.grdData.BackgroundColor = System.Drawing.SystemColors.Window;
            this.grdData.ColumnHeadersVisible = false;
            this.grdData.DataSource = this.bindingSource;
            this.grdData.DoSelectNext = false;
            this.grdData.Font = new System.Drawing.Font("Tahoma", 10F, System.Drawing.FontStyle.Regular);
            this.grdData.Location = new System.Drawing.Point(9, 35);
            this.grdData.Name = "grdData";
            this.grdData.RowHeadersVisible = false;
            this.grdData.RowSelectMode = true;
            this.grdData.Size = new System.Drawing.Size(221, 245);
            this.grdData.TabIndex = 1;
            this.grdData.TableStyles.Add(this.tsMain);
            this.grdData.ShowDetail += new System.EventHandler(this.grdData_ShowDetail);
            // 
            // tsMain
            // 
            this.tsMain.GridColumnStyles.Add(this.dataGridTextBoxColumn3);
            this.tsMain.GridColumnStyles.Add(this.dataGridTextBoxColumn4);
            this.tsMain.MappingName = "Sku";
            // 
            // dataGridTextBoxColumn3
            // 
            this.dataGridTextBoxColumn3.Format = "";
            this.dataGridTextBoxColumn3.FormatInfo = null;
            this.dataGridTextBoxColumn3.MappingName = "Num";
            // 
            // dataGridTextBoxColumn4
            // 
            this.dataGridTextBoxColumn4.Format = "";
            this.dataGridTextBoxColumn4.FormatInfo = null;
            this.dataGridTextBoxColumn4.MappingName = "Content";
            // 
            // btnExist
            // 
            this.btnExist.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnExist.BackgroundImage = null;
            this.btnExist.CommitKey = null;
            this.btnExist.HotKey = null;
            this.btnExist.ImageName = "WMSPDA.Images.close.png";
            this.btnExist.Location = new System.Drawing.Point(210, 6);
            this.btnExist.Name = "btnExist";
            this.btnExist.RoundButton = false;
            this.btnExist.Size = new System.Drawing.Size(21, 18);
            this.btnExist.TabIndex = 5;
            this.btnExist.TransparentColor = System.Drawing.Color.White;
            this.btnExist.Click += new System.EventHandler(this.btnExist_Click);
            // 
            // picHead
            // 
            this.picHead.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.picHead.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(51)))), ((int)(((byte)(152)))), ((int)(((byte)(203)))));
            this.picHead.ImageName = "WMSPDA.Images.header.PNG";
            this.picHead.Location = new System.Drawing.Point(0, 0);
            this.picHead.Name = "picHead";
            this.picHead.Size = new System.Drawing.Size(240, 29);
            this.picHead.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picHead.TextColor = System.Drawing.Color.White;
            this.picHead.TextFont = new System.Drawing.Font("Tahoma", 9F, System.Drawing.FontStyle.Bold);
            this.picHead.TitleText = "商品選択（同一バーコード）";
            this.picHead.TransparentColor = System.Drawing.Color.White;
            // 
            // dataGridTableStyle1
            // 
            this.dataGridTableStyle1.GridColumnStyles.Add(this.dataGridTextBoxColumn1);
            this.dataGridTableStyle1.GridColumnStyles.Add(this.dataGridTextBoxColumn2);
            this.dataGridTableStyle1.MappingName = "CountPlan";
            // 
            // dataGridTextBoxColumn1
            // 
            this.dataGridTextBoxColumn1.Format = "";
            this.dataGridTextBoxColumn1.FormatInfo = null;
            this.dataGridTextBoxColumn1.MappingName = "Num";
            this.dataGridTextBoxColumn1.NullText = "";
            // 
            // dataGridTextBoxColumn2
            // 
            this.dataGridTextBoxColumn2.Format = "";
            this.dataGridTextBoxColumn2.FormatInfo = null;
            this.dataGridTextBoxColumn2.MappingName = "Content";
            this.dataGridTextBoxColumn2.NullText = "";
            // 
            // frmSkuIdSearch
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(238, 295);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmSkuIdSearch";
            this.Text = "";
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.grdData)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.PictureEx picHead;
        private Framework.GridEx grdData;
        private System.Windows.Forms.BindingSource bindingSource;
        private ButtonEx btnExist;
        private System.Windows.Forms.DataGridTableStyle dataGridTableStyle1;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn1;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn2;
        private System.Windows.Forms.DataGridTableStyle tsMain;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn3;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn4;

    }
}
