using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;
using Entity;

namespace WMSPDA
{
    /// <summary>
    /// 切换仓库画面
    /// </summary>
    public partial class frmWareHouse :Framework.BaseForm// Form//  // 
    {
        public frmWareHouse()
        {
            InitializeComponent();
        }
        /// <summary>
        /// 确定
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                //如果一样报一个Message提示
                if (AppContext.Instance().WareHouse.WhId ==(long) cboWareHouse.SelectedValue)
                {
                    Notify("选择仓库为当前仓库，不需要切换！");
                    return;
                }
                AppContext.Instance().WareHouse = (WareHouseObj)cboWareHouse.SelectedItem;
                Notify("仓库切换成功！");

                //退回到Menu
                AppContext.Instance().GetNavigator().Back("menu", false);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 退回
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnBack_Click(object sender, EventArgs e)
        {
            try
            {
                //退回到Menu
                AppContext.Instance().GetNavigator().Back("menu", false);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 获取顶层控件
        /// </summary>
        /// <returns></returns>
        public override Control GetTopControl()
        {
            return pnlDetail;
        }

        private MobileResponse<WareHouseList> _response = null;
        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public override int Init(bool back, params object[] paras)
        {
            //从服务器取数据
            int ret;
            //获取数据
            MobileRequest req = GetInputData();
            //服务器动作
            ret = PostData("getWhs", req, out _response);
            if (ret == 0 || ret == 2)
            {
                //成功
                ret = 0;
            }
            return ret;
        }

        /// <summary>
        /// 显示画面
        /// </summary>
        /// <param name="prev"></param>
        /// <returns></returns>
        public override int Show(IView prev)
        {
            //当前仓库
            txtCurrent.Text = AppContext.Instance().WareHouse.WhName;
            //显示下拉列表
            cboWareHouse.SuspendLayout() ;
            cboWareHouse.DisplayMember = "WhName";
            cboWareHouse.ValueMember = "WhId";
            cboWareHouse.DataSource = _response.Results.WhList;
            cboWareHouse.ResumeLayout();
            if (_response.Results.WhList.Count > 0)
                cboWareHouse.SelectedIndex = 0;

            //定位焦点
            cboWareHouse.Focus();
            return 0;
        }
        /// <summary>
        /// Eneter确定
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cboWareHouse_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    //Eneter确定
                    btnOK_Click(sender, e);
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
    }
}

