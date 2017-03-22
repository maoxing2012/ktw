using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace StockTake
{
    class CountInfo
    {						

        /// <summary>
        /// 盘点记录ID
        /// </summary>
        public long countRecordId { get; set; }

        /// <summary>
        ///库位ID	
        /// </summary>
        public long binId { get; set; }

        /// <summary>
        ///商品ID	
        /// </summary>
        public long skuId { get; set; }

        /// <summary>
        ///商品编号
        /// </summary>
        public string skuCode { get; set; }

        /// <summary>
        ///商品名称
        /// </summary>
        public string skuName { get; set; }

        /// <summary>
        ///CS包装数
        /// </summary>
        public long csIn { get; set; }

        /// <summary>
        ///BL包装数
        /// </summary>
        public long blIn { get; set; }

        /// <summary>
        ///保质期
        /// </summary>
        public string expDate { get; set; }
        /// <summary>
        ///数量信息
        /// </summary>
        public string qtyInfo { get; set; }
        /// <summary>
        ///CS单位
        /// </summary>
        public string csUnit { get; set; }
        /// <summary>
        ///BL单位
        /// </summary>
        public string blUnit { get; set; }
        /// <summary>
        ///PS单位	
        /// </summary>
        public string psUnit { get; set; }
        /// <summary>
        ///库存状态
        /// </summary>
        public string invStatus { get; set; }


    }
}
