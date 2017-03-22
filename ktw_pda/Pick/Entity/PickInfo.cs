using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Pick
{
    class PickInfo
    {
        public long planQty { get; set; }
        public long exeQty { get; set; }
        public long binId { get; set; }
        public string binCode { get; set; }
        public long skuId { get; set; }
        public string skuCode { get; set; }
        public string skuName { get; set; }
        public string barcodes { get; set; }
        public string expDate { get; set; }
        public long qty { get; set; }
        public string qtyInfo { get; set; }
        public string specs { get; set; }
        public long qtyA { get; set; }
        public long qtyB { get; set; }
        public long qtyC { get; set; }
        public long qtyD { get; set; }
        public long qtyE { get; set; }
        public long qtyF { get; set; }
        public long qtyG { get; set; }
        public long qtyH { get; set; }
        public long qtyI { get; set; }
        public string qtyHyphenA { get; set; }
        public string qtyHyphenB { get; set; }
        public string qtyHyphenC { get; set; }
        public string qtyHyphenD { get; set; }
        public string qtyHyphenE { get; set; }
        public string qtyHyphenF { get; set; }
        public string qtyHyphenG { get; set; }
        public string qtyHyphenH { get; set; }
        public string qtyHyphenI { get; set; }
        public string qtyInfoA { get; set; }
        public string qtyInfoB { get; set; }
        public string qtyInfoC { get; set; }
        public string qtyInfoD { get; set; }
        public string qtyInfoE { get; set; }
        public string qtyInfoF { get; set; }
        public string qtyInfoG { get; set; }
        public string qtyInfoH { get; set; }
        public string qtyInfoI { get; set; }
        /// <summary>
        /// 散件发货时的警告消息
        /// </summary>
        public string warnMsg4Ps { get; set; }

        //Batch
        /// <summary>
        /// 拣货任务ID
        /// </summary>
        public long wtId { get; set; }
        /// <summary>
        /// 折算后的箱数
        /// </summary>
        public long csQty { get; set; }
        /// <summary>
        /// 折算后的内箱数
        /// </summary>
        public long blQty { get; set; }
        /// <summary>
        /// 折算后的散件数
        /// </summary>
        public long psQty { get; set; }
        /// <summary>
        /// CS包装单位（入数）
        /// </summary>
        public double csIn { get; set; }
        /// <summary>
        /// BL包装单位（入数）
        /// </summary>
        public double blIn { get; set; }
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
        /// 库存状态
        /// </summary>
        public string invStatus { get; set; }
        /// <summary>
        /// 库存状态名称
        /// </summary>
        public string invStatusNm { get; set; }
    }
}
