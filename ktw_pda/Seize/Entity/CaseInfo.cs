using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Seize
{
    class CaseInfo
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
        ///商品名称	
        /// </summary>
        public string skuName { get; set; }

        /// <summary>
        ///单品条码	
        /// </summary>
        public string psBarCode { get; set; }

        /// <summary>
        ///内箱条码
        /// </summary>
        public string blBarCode { get; set; }

        /// <summary>
        ///箱条码
        /// </summary>
        public string csBarCode { get; set; }

        /// <summary>
        ///单品单位
        /// </summary>
        public string psUnit { get; set; }

        /// <summary>
        ///内箱单位
        /// </summary>
        public string blUnit { get; set; }

        /// <summary>
        ///箱单位
        /// </summary>
        public string csUnit { get; set; }
        /// <summary>
        ///内箱包装数
        /// </summary>
        public long blIn { get; set; }
        /// <summary>
        ///箱包装数
        /// </summary>
        public long csIn { get; set; }
        /// <summary>
        ///保值期
        /// </summary>
        public string expDate { get; set; }
        /// <summary>
        ///数量	
        /// </summary>
        public long qty { get; set; }
        /// <summary>
        ///规格
        /// </summary>
        public string specs { get; set; }
        /// <summary>
        /// 
        /// </summary>
        public long totalQty { get; set; }
    }
}
