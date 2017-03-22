using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using BusinessUtil;

namespace IncomingCheck
{
    class AsnDetail
    {
        /// <summary>
        /// 订单的预定数
        /// </summary>
        public long planQty { get; set; }
        /// <summary>
        /// 订单的已收货数
        /// </summary>
        public long exeQty { get; set; }
        /// <summary>
        /// 入库单明细ID
        /// </summary>
        public long asnDetailId { get; set; }
        /// <summary>
        /// 商品编号
        /// </summary>
        public string skuCode { get; set; }
        /// <summary>
        /// 商品名称
        /// </summary>
        public string skuName { get; set; }
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
        /// PS单位
        /// </summary>
        public string psUnit { get; set; }
        /// <summary>
        /// 保质期，可能为空
        /// </summary>
        public string expDate { get; set; }
        /// <summary>
        /// 计划数
        /// </summary>
        public long skuPlanQty { get; set; }
        /// <summary>
        /// 已收货数
        /// </summary>
        public long skuExeQty { get; set; }
        /// <summary>
        /// 是否是第一次收货
        /// </summary>
        public bool isFirst { get; set; }
        /// <summary>
        /// 库存的最大保质期
        /// </summary>
        public string stockExpDate { get; set; }
        /// <summary>
        /// 标签信息
        /// </summary>
        public SkuLabel label { get; set; }
    }
}
