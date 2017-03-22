using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.Reflection;

namespace Framework
{
    /// <summary>
    /// 网格控件封装
    /// </summary>
    public partial class GridEx : DataGrid, ISupportInitialize
    {
        public event EventHandler ShowDetail;
        public GridEx()
        {
            InitializeComponent();
        }

        private bool _selectNext = false;
        private int _borderWidth = 0;

        /// <summary>
        /// 按Enter后跳到下一个控件
        /// </summary>
        public bool DoSelectNext { 
            get
            {
                return _selectNext;
            }

            set
            {
                _selectNext = value;
            }
        }

        private bool _rowSelectMode = true;
        /// <summary>
        /// 行选择模式
        /// </summary>
        public bool RowSelectMode
        {
            get
            {
                return _rowSelectMode;
            }

            set
            {
                _rowSelectMode = value;
            }
        }

        /// <summary>
        /// 行选择
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void GridEx_CurrentCellChanged(object sender, EventArgs e)
        {
            try
            {
                if (sender != null && sender is DataGrid)
                    SelectCurrentRow(sender as DataGrid);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 选择当前行
        /// </summary>
        /// <param name="grid"></param>
        private void SelectCurrentRow(DataGrid grid)
        {
            if (RowSelectMode)
            {
                int index = grid.CurrentCell.RowNumber;
                BindingSource bs = grid.DataSource as BindingSource;
                if (bs == null)
                    return;
                if(index >=0 && index <bs.Count )
                    grid.Select(index);
            }
        }
        /// <summary>
        /// 行选择
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void GridEx_GotFocus(object sender, EventArgs e)
        {
            try
            {
                if (sender != null && sender is DataGrid)
                    SelectCurrentRow(sender as DataGrid );
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 按键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void GridEx_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    //确定
                    BindingSource bs = this.DataSource as BindingSource;
                    if (bs != null && bs.Current != null)
                    {
                        if (ShowDetail != null)
                            ShowDetail(this, EventArgs.Empty);
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        ///// <summary>
        ///// 重绘事件
        ///// </summary>
        ///// <param name="pea"></param>
        //protected override void OnPaint(PaintEventArgs pea)
        //{
        //    base.OnPaint(pea);

        //    //祛除外黑框
        //    Rectangle clientRectangle = this.ClientRectangle;
        //    clientRectangle.Width--;
        //    clientRectangle.Height--;
        //    FieldInfo field = typeof(DataGrid).GetField("v_cxyDefaultBorderWidth", System.Reflection.BindingFlags.NonPublic | BindingFlags.Static);
        //    _borderWidth = (int)field.GetValue(null);
        //    clientRectangle.Inflate(-_borderWidth / 2, -_borderWidth / 2);
        //    pea.Graphics.DrawRectangle(new Pen(this.BackColor, _borderWidth), clientRectangle);
        //}

        #region ISupportInitialize 成员

        public void BeginInit()
        {
        }

        public void EndInit()
        {
        }
        #endregion

        /// <summary>
        /// 双击处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void GridEx_DoubleClick(object sender, EventArgs e)
        {
            try
            {
                //确定
                BindingSource bs = this.DataSource as BindingSource;
                if (bs != null && bs.Current != null)
                {
                    if (ShowDetail != null)
                        ShowDetail(this, EventArgs.Empty);
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}
