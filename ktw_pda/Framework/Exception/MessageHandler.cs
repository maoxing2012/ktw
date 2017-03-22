using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
//using Hprose.Common;

namespace Framework
{
    /// <summary>
    /// 消息处理器
    /// </summary>
    public class MessageHandler : IExceptionHandler
    {
        //private void Execute(HproseException exception)
        //{
        //    //ExceptionPolicy.HandleException(BusinessException.GetBusinessException(exception));
        //}

        private void Execute(Exception exception)
        {
            Log.Print(exception.ToString());
            Message.Err(exception.Message);
        }

        /// <summary>
        /// 处理错误
        /// </summary>
        /// <param name="exception"></param>
        /// <returns></returns>
        public HandleResult Handle(Exception exception)
        {
            HandleResult handle = HandleResult.Excute;
            try
            {
                //if (exception is HproseException)
                //{
                //    this.Execute((HproseException)exception);
                //    return handle;
                //}
                this.Execute(exception);
            }
            catch 
            {
                //什么也不做
            }
            return handle;
        }
    }
}
