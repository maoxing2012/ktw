using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Other
{
    class StockInfo
    {
        /// <summary>
        /// 商品编号
        /// </summary>
        public string skuCode { get; set; }
        /// <summary>
        /// 商品名称
        /// </summary>
        public string skuName { get; set; } 
        /// <summary>
        /// 规格
        /// </summary>
        public string specs { get; set; }  
        /// <summary>
        /// CS入数
        /// </summary>
        public long csIn { get; set; }  
        /// <summary>
        /// BL入数
        /// </summary>
        public long blIn { get; set; } 
        /// <summary>
        /// CS单位
        /// </summary>
        public string csUnit { get; set; }
        /// <summary>
        /// BL单位
        /// </summary>
        public string blUnit { get; set; } 
        /// <summary>
        ///  PS单位
        /// </summary>
        public string psUnit { get; set; } 
        /// <summary>
        /// 数量信息
        /// </summary>
        public string qtyInfo { get; set; }  
        /// <summary>
        /// 温度带
        /// </summary>
        public string tempDiv { get; set; } 
        /// <summary>
        /// 库存区分
        /// </summary>
        public string stockDiv { get; set; } 

    }
}
