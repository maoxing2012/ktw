using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Framework;
using Entity;

namespace WMSPAD
{
    /// <summary>
    ///   荷主選択（日本食研）
    /// </summary>
    public partial class frmOwnerList : BaseForm
    {
        private MobileResponse<OwnerList> _response;

        public frmOwnerList()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 快捷键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void frmOwnerList_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                switch (e.KeyCode)
                {
                    case Keys.Escape://退出
                        this.Close();
                        break;
                }
                if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("green").Value))
                {
                    this.Close();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// Enter 確定
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                EnableButtons(false);
                //清空提示栏
                bindingSource.EndEdit();
                //check if owner is empty
                if (cboOwner.SelectedIndex == 0)
                {
                    Framework.Message.Info(AppContext.Instance().GetMessage("field.must.input").FormatEx("荷主"));
                    cboOwner.Focus();
                    return;
                }
                AppContext.Instance().Owner = bindingSource.Current as OwnerInfo;
                this.DialogResult = DialogResult.OK;
                this.Close();
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

        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public override int Init(bool back, params object[] paras)
        {
            //从服务器取数据
            int ret = base.Init(back, paras);
            if (ret != 0)
                return ret;
            //获取数据
            MobileRequest req = GetInputData("getOwnerList");
            //服务器动作
            ret = PostData("getOwnerList", req, out _response);
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

            //显示下拉列表
            cboOwner.SuspendLayout();
            _response.Results.ownerList.Insert(0, new OwnerInfo());
            bindingSource.DataSource = _response.Results.ownerList;
            cboOwner.ResumeLayout();
            if (AppContext.Instance().Owner != null)
            {
                cboOwner.SelectedValue = AppContext.Instance().Owner.id;
                lblTitle.Text = "荷主選択(" + AppContext.Instance().Owner.name + ")";
            }
            else if (_response.Results.ownerList.Count > 0)
                cboOwner.SelectedIndex = 0;

            //定位焦点
            cboOwner.Focus();
            cboOwner.SelectAll();
            EnableButtons(true);
            return 0;
        }

        /// <summary>
        /// F1 クリア
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnClear_Click(object sender, EventArgs e)
        {
            try
            {
                bindingSource.MoveFirst();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 荷主を選択する
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void cboOwner_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode == Keys.Enter)
                    btnOK.PerformClick();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

    }
}
