using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    /// <summary>
    /// 工厂接口
    /// </summary>
    public interface IFactory
    {
        /// <summary>
        /// 获取对象
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="name"></param>
        /// <returns></returns>
        T CreateObject<T>(string name);
    }
}
