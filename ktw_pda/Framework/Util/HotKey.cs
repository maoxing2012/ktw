using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    /// <summary>
    /// 快捷键
    /// </summary>
    public class HotKey
    {
        /// <summary>
        /// 按钮文字
        /// </summary>
        public string Text
        {
            set;
            get;
        }
        /// <summary>
        /// 键值
        /// </summary>
        public string Value { get; set; }
    }
}
