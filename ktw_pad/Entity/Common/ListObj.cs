using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    /// <summary>
    /// 列表对象
    /// </summary>
    public  class ListObj
    {
        /// <summary>
        /// 行号
        /// </summary>
        public virtual string Num { get; set; }
        /// <summary>
        /// 内容
        /// </summary>
        public virtual string Content { get; set; }
    }
}
