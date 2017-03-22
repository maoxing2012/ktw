using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Framework;

namespace Framework
{
    /// <summary>
    /// 商品信息
    /// </summary>
    public class Sku
    {

        public long id { get; set; }//商品ID
        public string unit { get; set; }//包装单位名称
        public string code { get; set; }//商品编号
        public string name { get; set; }//商品名称
        public long coefficient { get; set; }//包装系数			

        /// <summary>
        /// 绑定内容
        /// </summary>
        public string Content
        {
            get
            {
                StringBuilder sb = new StringBuilder();
                sb.AppendFormat(@"{0}{1}{2}", code.TrimEx(), Environment.NewLine, name.TrimEx());
                return sb.ToString();
            }
        }

        /// <summary>
        /// 数字
        /// </summary>
        public string Num { get; set; }

    }
}
