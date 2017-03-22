using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Pick
{
    class WaveInfo
    {
        /// <summary>
        /// 波次号
        /// </summary>
        public long waveId { get; set; }
        /// <summary>
        /// 温度带区分
        /// </summary>
        public string tempDiv { get; set; }
        /// <summary>
        /// 温度带名称
        /// </summary>
        public string tempDivNm { get; set; }
        /// <summary>
        /// 总已拣货数量
        /// </summary>
        public long executeQty { get; set; }
        /// <summary>
        /// 总计划数量
        /// </summary>
        public long planQty { get; set; }
    }
}
