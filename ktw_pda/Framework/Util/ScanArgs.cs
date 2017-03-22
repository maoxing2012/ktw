using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    /// <summary>
    /// 扫描事件参数
    /// </summary>
    public class ScanArgs : EventArgs
    {
        /// <summary>
        /// 条形码
        /// </summary>
        public string Barcode
        {
            get;
            set;
        }
        /// <summary>
        /// 处理
        /// </summary>
        public bool Handle
        {
            get;
            set;
        }
        /// <summary>
        /// 条码类型
        /// </summary>
        public string CodeType { get; set; }
        /// <summary>
        /// 扫描时间
        /// </summary>
        public TimeSpan Duration { get; set; }
    }
}
