using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Framework;

namespace StockTake
{
    /// <summary>
    /// 库存查询信息
    /// </summary>
    class CountPlan
    {

        public long id { get; set; }//盘点单ID
        public string countPlanNumber { get; set; }//盘点单号
        public string description { get; set; }//备注
        public string countMethod { get; set; }//盘点方式
        public string status { get; set; }//状态

        /// <summary>
        /// 绑定内容
        /// </summary>
        public string Content
        {
            get
            {
                StringBuilder sb = new StringBuilder();
                sb.AppendFormat(@"{0}/ {1}", countPlanNumber.TrimEx(), description.TrimEx());
                return sb.ToString();
            }
        }

        /// <summary>
        /// 数字
        /// </summary>
        public string Num { get; set; }

    }
}
