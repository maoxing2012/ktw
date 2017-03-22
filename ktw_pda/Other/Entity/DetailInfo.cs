using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using BusinessUtil;

namespace Other
{
    class DetailInfo
    {
        /// <summary>
        /// 商品ID
        /// </summary>
        public long skuId { get; set; }
        /// <summary>
        /// 商品编号
        /// </summary>
        public string skuCode { get; set; }
        /// <summary>
        /// 商品名称
        /// </summary>
        public string skuName { get; set; }
        /// <summary>
        /// 库存状态
        /// </summary>
        public string invStatus { get; set; }
        /// <summary>
        /// 温度带
        /// </summary>
        public string tempDiv { get; set; }
        /// <summary>
        /// 规格
        /// </summary>
        public string specs { get; set; }
        /// <summary>
        /// 保质期
        /// </summary>
        public string expDate { get; set; }
        /// <summary>
        /// 箱包装数
        /// </summary>
        public long csIn { get; set; }
        /// <summary>
        /// 内箱包装数
        /// </summary>
        public long blIn { get; set; }
        /// <summary>
        /// 箱单位
        /// </summary>
        public string csUnit { get; set; }
        /// <summary>
        /// 内箱单位
        /// </summary>
        public string blUnit { get; set; }
        /// <summary>
        /// 单品单位
        /// </summary>
        public string psUnit { get; set; }
        /// <summary>
        /// 数量信息
        /// </summary>
        public string qtyInfo { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public long qty { get; set; }
        /// <summary>
        /// 收货单号
        /// </summary>
        public string asnNumber { get; set; }
        /// <summary>
        /// 收货日期
        /// </summary>
        public string ibdDate { get; set; }

        /// <summary>
        /// 标签信息
        /// </summary>
        public SkuLabel label { get; set; }

        /// <summary>
        /// 库位ID
        /// </summary>
        public long binId { get; set; }

        /// <summary>
        /// 库位编号
        /// </summary>
        public string binCode { get; set; }
    }
}
