using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;
using Entity;

namespace Framework
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmList : Framework.BaseForm
    {

        public ListObj SelectedItem { get; set; }

        public frmList()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 获取顶级容器控件
        /// </summary>
        /// <returns></returns>
        public override Control GetTopControl()
        {
            return this.pnlDetail;
        }
        /// <summary>
        /// 显示界面
        /// </summary>
        /// <param name="prev"></param>
        /// <returns></returns>
        public override int Show(IView prev)
        {
            int ret = base.Show(prev);
            DisplayData(this.InitArgs[0] as BindingList<ListObj>);
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
            if (!back)
            {
                grdData.PreferredRowHeight = 34;
                tsMain.GridColumnStyles[0].Width = 30;
                tsMain.GridColumnStyles[1].Width = grdData.Width - tsMain.GridColumnStyles[0].Width - 20;
                tsMain.MappingName = "ListObj";
                if (grdData.TableStyles.Count == 0)
                    grdData.TableStyles.Add(tsMain);
                grdData.RowHeadersVisible = false;
                grdData.ColumnHeadersVisible = false;

                if (paras.Length > 1)
                {
                    //this.picHead.TitleText = paras[1].TrimEx();
                    this.Text = paras[1].TrimEx();
                }
                this.FormBorderStyle = FormBorderStyle.FixedSingle;
                this.ControlBox = true;
            }
            return 0;
        }

        protected override bool InitForm()
        {
            this.Location = new Point((Screen.PrimaryScreen.WorkingArea.Width - this.Width) / 2
                , (Screen.PrimaryScreen.WorkingArea.Height - this.Height) / 2);
            return true;
        }

        /// <summary>
        /// 显示数据
        /// </summary>
        /// <param name="response"></param>
        private void DisplayData(BindingList<ListObj> list)
        {
            for (int i = 0; i < list.Count; i++)
            {
                list[i].Num = "{0}.".FormatEx(i + 1);
            }
            bindingSource.DataSource = list;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grdData_ShowDetail(object sender, EventArgs e)
        {
            try
            {
                this.SelectedItem = bindingSource.Current as ListObj;
                this.DialogResult = DialogResult.OK;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}

