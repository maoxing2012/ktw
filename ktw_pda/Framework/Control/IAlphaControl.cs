using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

namespace Framework
{
    /// <summary>
    /// 透明控件接口
    /// </summary>
    interface IAlphaControl
    {
        void DrawInternal(Graphics gx);
    }
}
