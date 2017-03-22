using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Runtime.CompilerServices;

namespace Framework
{
    public class FormUtil
    {
        /// <summary>
        /// 找到控件对应窗体
        /// </summary>
        /// <param name="ctl"></param>
        /// <returns></returns>
        public static Form FindForm(Control ctl)
        {
            Control control = ctl;
            do
            {
                control = control.Parent;
            }
            while (!(control is Form));
            return (Form)control;
        }

        /// <summary>
        /// 设计模式判断
        /// </summary>
        //[Extension]
        public static bool DesignMode(Control c)
        {
            return c.Site != null && c.Site.DesignMode;
        }

    }
}
