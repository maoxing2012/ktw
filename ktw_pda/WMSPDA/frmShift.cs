using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace WMSPDA
{
    /// <summary>
    /// 容器选择
    /// </summary>
    public partial class frmShift : Framework.BaseForm
    {

        public frmShift()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 获取容器控件
        /// </summary>
        /// <returns></returns>
        public override Control GetTopControl()
        {
            return this.pnlDetail;
        }


        /// <summary>
        /// 回退
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnBack_Click(object sender, EventArgs e)
        {
            try
            {
                //回退
                this.DialogResult = DialogResult.Cancel;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 回到Menu
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnHome_Click(object sender, EventArgs e)
        {
            try
            {
                //回退
                this.DialogResult = DialogResult.Abort;
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
            int ret = base.Init(back, paras);
            if (ret != 0)
                return ret;
            //定位焦点
            if (!back)
            {
                //非退回
                //txtContainer.Text = string.Empty;
                //_asn = paras[0] as ASNInfo;
                //_asnDetailItem = paras[1] as ASNDetailListItem;
            }
            return 0;
        }

        /// <summary>
        /// 显示界面
        /// </summary>
        /// <param name="prev"></param>
        /// <returns></returns>
        public override int Show(IView prev)
        {
            int ret = base.Show(prev);
            if (ret != 0)
                return ret;
            return 0;
        }

        /// <summary>
        /// 快捷键处理
        /// </summary>
        /// <param name="e"></param>
        /// <returns></returns>
        protected override bool ProcessKeyDown(KeyEventArgs e)
        {
            bool ret = base.ProcessKeyDown(e);

            //switch (e.KeyCode)
            //{
            //    case Keys.Escape:
            //        //退回前一画面
            //        AppContext.Instance().GetNavigator().Back(string.Empty, false);
            //        break;
            //}
            return ret;
        }

        /// <summary>
        /// 调用各业务
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Button_Click(object sender, EventArgs e)
        {
            try
            {
                base.EnableButtons(false);
                ButtonEx btn = sender as ButtonEx;
                //导航
                using (BaseForm view = AppContext.Instance().CreateObject<BaseForm>(btn.Tag.ToString()))
                {
                    view.Init(false);
                    DialogResult ret = view.ShowDialog();
                    if (ret == DialogResult.Abort)
                    {
                        //退回到Menu
                        this.DialogResult = DialogResult.Abort;
                    }
                    else
                    {
                        //退回到本画面
                        this.Init(true);
                        this.Show(null);
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                base.EnableButtons(true);
            }
        }
    }
}

