using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Framework;

namespace Seize
{
    public class UnitInfo
    {
        /// <summary>
        /// 数字
        /// </summary>
        public string Num { get; set; }
        public string UnitName { get; set; }
        public long In { get; set; }

        public string Content
        {
            get
            {
                return string.Format("単位：{0}{1}入り数：{2}", UnitName.TrimEx(),Environment.NewLine, In.TrimEx());
            }
        }
    }
}
