using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Framework;
using Entity;

namespace IncomingCheck
{
    class ASNInfos
    {
        /// <summary>
        /// 对应的AsnID
        /// </summary>
        public long asnId { get; set; }
        /// <summary>
        /// 对应的AsnNumber
        /// </summary>
        public string asnNumber { get; set; }
        /// <summary>
        /// 订单单位的已收货数
        /// </summary>
        public long exeQty { get; set; }
        /// <summary>
        /// 订单单位的总计划收货数
        /// </summary>
        public long planQty { get; set; }
        /// <summary>
        /// 订单列表
        /// </summary>
        public BindingList<ASNInfo> asnList { get; set; }

    }

    class ASNInfo:ListObj
    {
        /// <summary>
        /// 对应的AsnID
        /// </summary>
        public long asnId { get; set; }
        /// <summary>
        /// 对应的AsnNumber
        /// </summary>
        public string asnNumber { get; set; }
        /// <summary>
        /// 订单单位的已收货数
        /// </summary>
        public long executeQty { get; set; }
        /// <summary>
        /// 订单单位的总计划收货数
        /// </summary>
        public long planQty { get; set; }

        /// <summary>
        /// 绑定内容
        /// </summary>
        public override string Content
        {
            get
            {
                StringBuilder sb = new StringBuilder();
                sb.AppendFormat(@"{0}", asnNumber.TrimEx());
                return sb.ToString();
            }
        }

        /// <summary>
        /// 数字
        /// </summary>
        public override string Num { get; set; }

    }
}
