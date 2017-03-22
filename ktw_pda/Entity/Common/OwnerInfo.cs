using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Entity;

namespace Entity
{
    /// <summary>
    /// 货主 信息
    /// </summary>
    public class OwnerInfo:WareHouseObj
    {
        public OwnerInfo()
        {
            this.code =string.Empty;
            this.name=string.Empty;
        }
        /// <summary>
        /// 货主ID
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 货主编号
        /// </summary>
        public string  code { get; set; }
        /// <summary>
        /// 货主名称
        /// </summary>
        public string name { get; set; }
        /// <summary>
        /// 显示在下拉框内容
        /// </summary>
        public string BindText { 
            get
            {
                if (string.IsNullOrEmpty(code) && string.IsNullOrEmpty(name))
                    return string.Empty;
                return code + ":" + name;
            }
        }
    }
}
