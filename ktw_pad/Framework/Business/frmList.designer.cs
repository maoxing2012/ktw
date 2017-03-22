using Entity;
namespace Framework
{
    partial class frmList
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
            this.grdData = new System.Windows.Forms.DataGridView();
            this.bindingSource = new System.Windows.Forms.BindingSource(this.components);
            //this.dataGridTextBoxColumn3 = new System.Windows.Forms.DataGridTextBoxColumn();
            //this.dataGridTextBoxColumn4 = new System.Windows.Forms.DataGridTextBoxColumn();
            //this.dataGridTableStyle1 = new System.Windows.Forms.DataGridTableStyle();
            //this.dataGridTextBoxColumn1 = new System.Windows.Forms.DataGridTextBoxColumn();
            //this.dataGridTextBoxColumn2 = new System.Windows.Forms.DataGridTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).BeginInit();
            this.pnlTop.SuspendLayout();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grdData)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // pnlDetail
            // 
            this.pnlDetail.BackColor = System.Drawing.Color.White;
            this.pnlDetail.Controls.Add(this.grdData);
            this.pnlDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlDetail.Location = new System.Drawing.Point(0, 0);
            this.pnlDetail.Name = "pnlDetail";
            this.pnlDetail.Size = new System.Drawing.Size(600, 500);
            this.pnlDetail.TabIndex = 2;
            // 
            // grdData
            // 
            this.grdData.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.grdData.BackgroundColor = System.Drawing.SystemColors.Window;
            this.grdData.ColumnHeadersVisible = false;
            this.grdData.DataMember = "";
            this.grdData.DataSource = this.bindingSource;
            this.grdData.Font = new System.Drawing.Font("Tahoma", 10F);
            this.grdData.Location = new System.Drawing.Point(9, 12);
            this.grdData.Name = "grdData";
            this.grdData.RowHeadersVisible = false;
            this.grdData.Size = new System.Drawing.Size(583, 473);
            this.grdData.TabIndex = 1;
            // 
            // bindingSource
            // 
            this.bindingSource.DataSource = typeof(Entity.ListObj);
            //// 
            //// tsMain
            //// 
            //this.tsMain.DataGrid = null;
            //this.tsMain.GridColumnStyles.AddRange(new System.Windows.Forms.DataGridColumnStyle[] {
            //this.dataGridTextBoxColumn3,
            //this.dataGridTextBoxColumn4});
            //this.tsMain.HeaderForeColor = System.Drawing.SystemColors.ControlText;
            //this.tsMain.MappingName = "UnitInfo";
            // 
            // dataGridTextBoxColumn3
            // 
            this.dataGridTextBoxColumn3.Format = "";
            this.dataGridTextBoxColumn3.FormatInfo = null;
            this.dataGridTextBoxColumn3.MappingName = "Num";
            this.dataGridTextBoxColumn3.NullText = "";
            this.dataGridTextBoxColumn3.Width = 75;
            // 
            // dataGridTextBoxColumn4
            // 
            this.dataGridTextBoxColumn4.Format = "";
            this.dataGridTextBoxColumn4.FormatInfo = null;
            this.dataGridTextBoxColumn4.MappingName = "Content";
            this.dataGridTextBoxColumn4.Width = 75;
            // 
            // dataGridTableStyle1
            // 
            this.dataGridTableStyle1.DataGrid = null;
            this.dataGridTableStyle1.GridColumnStyles.AddRange(new System.Windows.Forms.DataGridColumnStyle[] {
            this.dataGridTextBoxColumn1,
            this.dataGridTextBoxColumn2});
            this.dataGridTableStyle1.HeaderForeColor = System.Drawing.SystemColors.ControlText;
            this.dataGridTableStyle1.MappingName = "CountPlan";
            // 
            // dataGridTextBoxColumn1
            // 
            this.dataGridTextBoxColumn1.Format = "";
            this.dataGridTextBoxColumn1.FormatInfo = null;
            this.dataGridTextBoxColumn1.MappingName = "Num";
            this.dataGridTextBoxColumn1.NullText = "";
            this.dataGridTextBoxColumn1.Width = 75;
            // 
            // dataGridTextBoxColumn2
            // 
            this.dataGridTextBoxColumn2.Format = "";
            this.dataGridTextBoxColumn2.FormatInfo = null;
            this.dataGridTextBoxColumn2.MappingName = "Content";
            this.dataGridTextBoxColumn2.NullText = "";
            this.dataGridTextBoxColumn2.Width = 75;
            // 
            // frmList
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(600, 500);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmList";
            this.Controls.SetChildIndex(this.pnlDetail, 0);
            this.Controls.SetChildIndex(this.pnlTop, 0);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).EndInit();
            this.pnlTop.ResumeLayout(false);
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grdData)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private System.Windows.Forms.DataGridView grdData;
        private System.Windows.Forms.BindingSource bindingSource;
        private System.Windows.Forms.DataGridTableStyle dataGridTableStyle1;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn1;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn2;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn3;
        private System.Windows.Forms.DataGridTextBoxColumn dataGridTextBoxColumn4;

    }
}
