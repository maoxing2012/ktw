using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace BusinessUtil
{
    public class SkuLabel
    {
        /// <summary>
        /// 货主编号
        /// </summary>
        public string ownerCode { get; set; }
        /// <summary>
        /// 货主名称
        /// </summary>
        public string ownerName { get; set; }
        /// <summary>
        /// 库存状态
        /// </summary>
        public string invStatus { get; set; }
        /// <summary>
        /// CS包装数
        /// </summary>
        public long csIn { get; set; }
        /// <summary>
        /// BL包装数
        /// </summary>
        public long blIn { get; set; }
        /// <summary>
        /// 商品编号
        /// </summary>
        public string skuCode { get; set; }
        /// <summary>
        /// 打印时间
        /// </summary>
        public string dateTime { get; set; }
        /// <summary>
        /// 商品名称
        /// </summary>
        public string skuName1 { get; set; }
        public string skuName2 { get; set; }
        public string skuName3 { get; set; }
        /// <summary>
        /// JANCode
        /// </summary>
        public string janCode { get; set; }
        /// <summary>
        /// 规格
        /// </summary>
        public string specs { get; set; }
        /// <summary>
        /// 保质期
        /// </summary>
        public string expDate { get; set; }
        /// <summary>
        /// 限界出库日
        /// </summary>
        public string shipExpDate { get; set; }
        /// <summary>
        /// 入库日
        /// </summary>
        public string ibdDate { get; set; }
        /// <summary>
        /// lot号
        /// </summary>
        public string lotSeq { get; set; }
        /// <summary>
        /// 温度带
        /// </summary>
        public string tempDiv { get; set; }
        /// <summary>
        /// 二维码信息
        /// </summary>
        public string qrCode { get; set; }
    }
}
