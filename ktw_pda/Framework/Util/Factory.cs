using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Reflection;
using System.IO;

namespace Framework
{
    /// <summary>
    /// 工厂类
    /// </summary>
    public class Factory
    {
        /// <summary>
        /// 缓存Assembly
        /// </summary>
        private Dictionary<string, Assembly> _assemblyDic = new Dictionary<string, Assembly>();

        /// <summary>
        /// 获取对象
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="name"></param>
        /// <returns></returns>
        public T CreateObject<T>(string name)
        {
            Assembly bin;
            Dictionary<string, string> dic = AppContext.Instance().GetConfig().ObjectConfig(name);
            if (!this._assemblyDic.ContainsKey(dic["assemblyName"]))
            {
                if (dic["assemblyName"].EndsWith(".dll") | dic["assemblyName"].EndsWith(".exe"))
                {
                    bin = Assembly.LoadFrom(Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName) + @"\" + dic["assemblyName"]);
                }
                else
                {
                    bin = Assembly.Load(dic["assemblyName"]);
                }
                this._assemblyDic.Add(dic["assemblyName"], bin);
            }
            bin = this._assemblyDic[dic["assemblyName"]];
            return (T)bin.CreateInstance(dic["fullname"]);
        }

    }
}
