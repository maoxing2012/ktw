using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Drawing.Imaging;
using System.Windows.Forms;
using System.ComponentModel;

namespace Framework
{
    /// <summary>
    /// 按钮控件
    /// </summary>
    public class ButtonEx :System.Windows.Forms.Button
    {
        /// <summary>
        /// 提交事务关键字,如"login"
        /// </summary>
        public string CommitKey
        {
            get
            {
                return _commitKey;
            }
            set
            {
                _commitKey = value;
            }
        }
        private string _commitKey = string.Empty;
        /// <summary>
        /// 热键处理
        /// </summary>
        public string HotKey
        {
            get
            {
                return _hotKey;
            }
            set
            {
                _hotKey = value;
            }
        }
        private string _hotKey = string.Empty;

        /// <summary>
        /// 图片名称
        /// </summary>
        public string ImageName
        {
            get
            {
                return _imageName;
            }
            set
            {
                //if (!string.IsNullOrEmpty(value) && !FormUtil.DesignMode(this))
                //{
                //    this._image = AppContext.Instance().GetImage(value);
                //}
                _imageName = value;
            }
        }
        private string _imageName = string.Empty;

    }
}
