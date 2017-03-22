using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;

namespace Framework
{
    class SkuList
    {
        public long SkuId { get; set; }//商品ID
        public string SkuCode { get; set; }//商品编号
        public string SkuName { get; set; }//商品名称
        public long coefficient { get; set; }//包装系数	
        public string unit { get; set; }//包装单位名称
        public BindingList<Sku> skuList { get; set; }
    }
}
