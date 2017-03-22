
using Framework;

namespace Seize
{
    partial class frmUnitInfo
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            this.pnlDetail = new Framework.AlphaPanel();
            this.grdData = new System.Windows.Forms.DataGridView();
            this.btnExist = new Framework.ButtonEx();
            this.picHead = new Framework.PictureEx();
            this.numDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.unitName1DataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.in1DataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.bindingSource = new System.Windows.Forms.BindingSource(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).BeginInit();
            this.pnlTop.SuspendLayout();
            this.pnlDetail.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.grdData)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // lblTitle
            // 
            this.lblTitle.Size = new System.Drawing.Size(317, 42);
            this.lblTitle.Text = "単位選択";
            // 
            // btnRight
            // 
            this.btnRight.Location = new System.Drawing.Point(376, 8);
            this.btnRight.Size = new System.Drawing.Size(30, 30);
            // 
            // pnlTop
            // 
            this.pnlTop.Size = new System.Drawing.Size(421, 47);
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
            this.pnlDetail.Size = new System.Drawing.Size(421, 222);
            this.pnlDetail.TabIndex = 2;
            // 
            // grdData
            // 
            this.grdData.AllowUserToAddRows = false;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.grdData.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle1;
            this.grdData.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.grdData.AutoGenerateColumns = false;
            this.grdData.BackgroundColor = System.Drawing.SystemColors.Window;
            this.grdData.ColumnHeadersVisible = false;
            this.grdData.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.numDataGridViewTextBoxColumn,
            this.unitName1DataGridViewTextBoxColumn,
            this.in1DataGridViewTextBoxColumn});
            this.grdData.DataSource = this.bindingSource;
            this.grdData.Font = new System.Drawing.Font("Tahoma", 10F);
            this.grdData.Location = new System.Drawing.Point(9, 56);
            this.grdData.MultiSelect = false;
            this.grdData.Name = "grdData";
            this.grdData.ReadOnly = true;
            this.grdData.RowHeadersVisible = false;
            dataGridViewCellStyle2.Font = new System.Drawing.Font("MS UI Gothic", 20F);
            this.grdData.RowsDefaultCellStyle = dataGridViewCellStyle2;
            this.grdData.RowTemplate.Height = 50;
            this.grdData.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.grdData.Size = new System.Drawing.Size(400, 154);
            this.grdData.TabIndex = 1;
            this.grdData.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.grdData_CellClick);
            this.grdData.KeyDown += new System.Windows.Forms.KeyEventHandler(this.grdData_KeyDown);
            // 
            // btnExist
            // 
            this.btnExist.CommitKey = "";
            this.btnExist.HotKey = "";
            this.btnExist.ImageName = "";
            this.btnExist.Location = new System.Drawing.Point(0, 0);
            this.btnExist.Name = "btnExist";
            this.btnExist.Size = new System.Drawing.Size(75, 23);
            this.btnExist.TabIndex = 2;
            // 
            // picHead
            // 
            this.picHead.Location = new System.Drawing.Point(0, 0);
            this.picHead.Name = "picHead";
            this.picHead.Size = new System.Drawing.Size(100, 50);
            this.picHead.TabIndex = 3;
            this.picHead.TabStop = false;
            // 
            // numDataGridViewTextBoxColumn
            // 
            this.numDataGridViewTextBoxColumn.DataPropertyName = "Num";
            this.numDataGridViewTextBoxColumn.HeaderText = "Num";
            this.numDataGridViewTextBoxColumn.Name = "numDataGridViewTextBoxColumn";
            this.numDataGridViewTextBoxColumn.ReadOnly = true;
            this.numDataGridViewTextBoxColumn.Width = 50;
            // 
            // unitName1DataGridViewTextBoxColumn
            // 
            this.unitName1DataGridViewTextBoxColumn.DataPropertyName = "UnitName1";
            this.unitName1DataGridViewTextBoxColumn.HeaderText = "UnitName1";
            this.unitName1DataGridViewTextBoxColumn.Name = "unitName1DataGridViewTextBoxColumn";
            this.unitName1DataGridViewTextBoxColumn.ReadOnly = true;
            this.unitName1DataGridViewTextBoxColumn.Width = 173;
            // 
            // in1DataGridViewTextBoxColumn
            // 
            this.in1DataGridViewTextBoxColumn.DataPropertyName = "In1";
            this.in1DataGridViewTextBoxColumn.HeaderText = "In1";
            this.in1DataGridViewTextBoxColumn.Name = "in1DataGridViewTextBoxColumn";
            this.in1DataGridViewTextBoxColumn.ReadOnly = true;
            this.in1DataGridViewTextBoxColumn.Width = 173;
            // 
            // bindingSource
            // 
            this.bindingSource.DataSource = typeof(Seize.UnitInfo);
            // 
            // frmUnitInfo
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(421, 222);
            this.Controls.Add(this.pnlDetail);
            this.Name = "frmUnitInfo";
            this.Text = "単位選択";
            this.Controls.SetChildIndex(this.pnlDetail, 0);
            this.Controls.SetChildIndex(this.pnlTop, 0);
            ((System.ComponentModel.ISupportInitialize)(this.btnLeftMini)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnRight)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.btnLeft)).EndInit();
            this.pnlTop.ResumeLayout(false);
            this.pnlDetail.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.grdData)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picHead)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.bindingSource)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Framework.AlphaPanel pnlDetail;
        private Framework.PictureEx picHead;
        private System.Windows.Forms.DataGridView grdData;
        private System.Windows.Forms.BindingSource bindingSource;
        private ButtonEx btnExist;
        private System.Windows.Forms.DataGridViewTextBoxColumn numDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn unitName1DataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn in1DataGridViewTextBoxColumn;

    }
}
