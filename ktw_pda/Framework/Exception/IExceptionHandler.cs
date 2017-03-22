using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Framework
{
    /// <summary>
    /// 错误处理器接口
    /// </summary>
    public interface IExceptionHandler
    {
        /// <summary>
        /// 错误处理
        /// </summary>
        /// <param name="exception"></param>
        /// <returns></returns>
        HandleResult Handle(Exception exception);
    }
}
