using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    /// <summary>
    /// 打印接口
    /// </summary>
    public interface IPrint
    {
        /// <summary>
        /// 打印命令集
        /// </summary>
        /// <param name="commands"></param>
        /// <returns></returns>
        int Print(List<List<string>> commands);
    }
}
