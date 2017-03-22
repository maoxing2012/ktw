using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    /// <summary>
    /// 错误处理策略
    /// </summary>
    public class ExceptionPolicy
    {
        // Fields
        private static Dictionary<string, List<IExceptionHandler>> _policyDic = new Dictionary<string, List<IExceptionHandler>>();

        /// <summary>
        /// 处理逻辑
        /// </summary>
        /// <param name="inner"></param>
        /// <returns></returns>
        public static bool HandleException(Exception inner)
        {
            return HandleException(inner, string.Empty);
        }

        /// <summary>
        /// 处理逻辑
        /// </summary>
        /// <param name="inner"></param>
        /// <param name="policy"></param>
        /// <returns></returns>
        public static bool HandleException(Exception inner, string policy)
        {
            try
            {
                List<IExceptionHandler> list;
                if (_policyDic.ContainsKey(policy))
                {
                    list = _policyDic[policy];
                }
                else
                {
                    List<string> lst = AppContext.Instance().GetConfig().ExceptionConfig(string.IsNullOrEmpty(policy) ? "Default" : policy);
                    list = new List<IExceptionHandler>();
                    foreach (string value in lst)
                    {
                        IExceptionHandler hander = AppContext.Instance().CreateObject<IExceptionHandler>(value);
                        list.Add(hander);
                    }
                    _policyDic.Add(policy, list);
                }
                foreach (IExceptionHandler hander in list)
                {
                    if (hander.Handle(inner) == HandleResult.None)
                    {
                        return true;
                    }
                }
            }
            catch
            {
                //什么也不做
            }
            return true;
        }


    }
}
