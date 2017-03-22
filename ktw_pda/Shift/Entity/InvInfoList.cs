using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Shift
{
    class InvInfoList
    {
        /// <summary>
        ///库存信息List
        /// </summary>
        public List<string> invInfo { get; set; }

        /// <summary>
        /// 库位ID
        /// </summary>
        public long binId { get; set; }  

    }
}
