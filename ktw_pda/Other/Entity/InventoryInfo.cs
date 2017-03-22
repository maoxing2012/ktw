using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Framework;

namespace Other
{
    /// <summary>
    /// 库存查询信息
    /// </summary>
    class InventoryInfo
    {

        public long invId { get; set; }
        public long skuId { get; set; }//库存id
        public string skuCode { get; set; }//商品编号
        public string skuName { get; set; }//商品名称
        public string binCode { get; set; }//库位号
        public string expDate { get; set; }//容器号
        public string qtyInfo { get; set; }//包装名称
        public long qty { get; set; }//数量
        public string invStatus { get; set; }//库存状态
        public long binId { get; set; }//批次信息

        /// <summary>
        /// 绑定内容
        /// </summary>
        public string Content
        {
            get
            {
                StringBuilder sb = new StringBuilder();
                sb.AppendFormat(@"{0}/{1}{2}{3}/{4}/{5}",expDate.TrimEx(), skuName.TrimEx(),
                    Environment.NewLine,binCode.TrimEx(), qtyInfo.TrimEx(),invStatus.TrimEx());
                return sb.ToString();
            }
        }

        /// <summary>
        /// 数字
        /// </summary>
        public string Num { get; set; }

    }
}
