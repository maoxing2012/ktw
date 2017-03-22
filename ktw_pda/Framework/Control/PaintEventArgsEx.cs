using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace Framework
{
    /// <summary>
    /// Paint参数扩展
    /// </summary>
    public class PaintEventArgsEx : PaintEventArgs
    {
        /// <summary>
        /// 构造函数
        /// </summary>
        /// <param name="g"></param>
        /// <param name="rc"></param>
        public PaintEventArgsEx(Graphics g, Rectangle rc)
            : base(g, rc)
        {
        }

        /// <summary>
        /// 已处理
        /// </summary>
        public bool Handled { get; set; }
    }
}
