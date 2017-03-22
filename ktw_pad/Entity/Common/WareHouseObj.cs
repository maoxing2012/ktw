using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    /// <summary>
    /// 仓库信息
    /// </summary>
    [Serializable]
    public class WareHouseObj
    {
        public WareHouseObj()
        {
            this.WhId = 0 ;
            this.WhName = string.Empty;
            this.WhCode = string.Empty;
        }
        public WareHouseObj(long whID,string whName,string whCode)
        {
            this.WhId = whID;
            this.WhName = whName;
            this.WhCode = whCode;
        }

        /// <summary>
        /// 仓库ID
        /// </summary>
        public long WhId { get; set; }
        /// <summary>
        /// 仓库名称
        /// </summary>
        public string WhName { get; set; }
        /// <summary>
        /// 仓库代码
        /// </summary>
        public string WhCode { get; set; }
    }
}
