using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace Seize
{
    /// <summary>
    /// 単位
    /// </summary>
    public partial class frmUnitInfo : BaseForm
    {
        public UnitInfo SelectedUnit { get; set; }

        public frmUnitInfo()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 显示界面
        /// </summary>
        /// <param name="prev"></param>
        /// <returns></returns>
        public override int Show(IView prev)
        {
            int ret = base.Show(prev);
            DisplayData(this.InitArgs[0] as BindingList<UnitInfo>);
            if (ret != 0)
                return ret;
            return 0;
        }
        /// <summary>
        /// 退回
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnExist_Click(object sender, EventArgs e)
        {
            try
            {
                this.DialogResult = DialogResult.Cancel;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public override int Init(bool back, params object[] paras)
        {
            int retflg = base.Init(back, paras);
            if (retflg != 0)
                return retflg;

            return 0;
        }

        protected override bool InitForm()
        {
            btnRight.Image = AppContext.Instance().GetImage("WMSPAD.Images.close.png");
            this.Location = new Point((Screen.PrimaryScreen.WorkingArea.Width - this.Width) / 2
                , (Screen.PrimaryScreen.WorkingArea.Height - this.Height) / 2);
            return true;
        }

        /// <summary>
        /// 显示数据
        /// </summary>
        /// <param name="response"></param>
        private void DisplayData(BindingList<UnitInfo> list)
        {
            //for (int i = 0; i < list.Count; i++)
            //{
            //    list[i].Num = "{0}.".FormatEx(i + 1);
            //}
            bindingSource.DataSource = list;
        }

        /// <summary>
        /// Close
        /// </summary>
        protected override void PressRightButton()
        {
            try
            {
                this.DialogResult = DialogResult.Cancel;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// Select
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grdData_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                this.SelectedUnit = bindingSource.Current as UnitInfo;
                this.DialogResult = DialogResult.OK;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// Enter
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grdData_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    this.SelectedUnit = bindingSource.Current as UnitInfo;
                    this.DialogResult = DialogResult.OK;
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}
