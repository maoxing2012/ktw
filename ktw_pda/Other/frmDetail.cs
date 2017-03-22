using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;
using sato.pt;
using BusinessUtil;

namespace Other
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmDetail : Framework.BaseForm
    {
        private DetailInfo _detail;

        public frmDetail()
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
        /// 退回
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnBack_Click(object sender, EventArgs e)
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
            int ret = 0;
            if (!back)
            {
                this.picHead.TitleText = "在庫情報詳細（{0}）".FormatEx(AppContext.Instance().Owner.name);
                //基类命令控件初始化
                ret = base.Init(back, paras);
                if (ret != 0)
                    return ret;

                //从服务器取数据    
                MobileRequest request = GetInputData("getInvDetailInfo");
                MobileResponse<DetailInfo> response;
                request.Parameters.Add("invId", paras[0]);
                ret = PostData("getInvDetailInfo", request, out response);
                if (!(ret == 0 || ret == 2))
                {
                    return ret;
                }
                _detail = response.Results;
                bindingSource.DataSource = _detail;
            }

            return 0;
        }

        /// <summary>
        /// return to menu
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnHome_Click(object sender, EventArgs e)
        {

            try
            {
                this.DialogResult = DialogResult.Abort;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }


        /// <summary>
        /// 清除
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void F1_Click(object sender, EventArgs e)
        {
            try
            {
                using (frmComfirm frmNext = new frmComfirm())
                {
                    int ret = frmNext.Init(false);
                    if (frmNext.ShowDialog() == DialogResult.OK)
                    {
                        //入荷荷札を印刷します
                        EnableButtons(false);
                        PrintUtil.PrintBill(_detail.label, frmNext.Times);
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                EnableButtons(true);
            }
        }
    }
}

