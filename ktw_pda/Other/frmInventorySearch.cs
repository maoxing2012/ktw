using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Framework;

namespace Other
{
    /// <summary>
    /// 其他菜单
    /// </summary>
    public partial class frmInventorySearch : Framework.BaseForm
    {
        private long _skuId;
        public frmInventorySearch()
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
                //基类命令控件初始化
                ret = base.Init(back, paras);
                if (ret != 0)
                    return ret;
                this.picHead.TitleText = "商品情報検索（{0}）".FormatEx(AppContext.Instance().Owner.name);
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
        /// 商品バーコード入力
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtBarCode_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                {
                    //StockResult ret= BizUtil.QueryStock(txtBarCode.Text);
                    //if (ret.ActionResult == ActionResult.SelectOne)
                    //{
                    //    _skuId = ret.Result.id;
                    //    setPageData(_skuId);
                    //}
                    //else
                    //{
                    //    txtBarCode.Focus();
                    //    txtBarCode.SelectAll();
                    //}
                    if (txtBarCode.Text.IsEmpty())
                    {
                        txtBarCode.Focus();
                        txtBarCode.SelectAll();
                        Message.Warn("QRコードをスキャン して下さい。");
                        return;
                    }
                    setPageData(0);
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        private void setPageData(long skuId) {
            //取服务器数据
            MobileRequest req = GetInputData("getSkuInfo");
            req.PageId = null;
            //req.Parameters.Add("skuId", skuId);
            AppContext app = AppContext.Instance();
            MobileResponse<Dictionary<string, object>> response = null;
            //调用服务端
            int ret = base.PostData("getSkuInfo", req, out response);
            if (!(ret == 0 || ret == 2))
                //错误返回
                return;
            this.lblSkuCode.Text = response.Results["skuCode"].TrimEx();
            this.lblSkuName.Text = response.Results["skuName"].TrimEx();
            this.lblSpecs.Text = response.Results["specs"].TrimEx();
            this.lblCsIn.Text = "アウター入数:" + response.Results["csIn"].TrimEx();
            this.lblBlIn.Text = "インナー入数:" + response.Results["blIn"].TrimEx();
            this.lblCsUnit.Text = "アウター:" + response.Results["csUnit"].TrimEx();
            this.lblBlUnit.Text = "インナー:" + response.Results["blUnit"].TrimEx();
            this.lblPsUnit.Text = "バラ:" + response.Results["psUnit"].TrimEx();
            this.lblQtyInfo.Text = response.Results["qtyInfo"].TrimEx();
            this.lblTempDiv.Text = response.Results["tempDiv"].TrimEx();
            this.lblStockDiv.Text = response.Results["stockDiv"].TrimEx();

        }
    }
}

