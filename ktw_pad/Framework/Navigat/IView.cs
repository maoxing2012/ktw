using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Framework
{
    public interface IView
    {
        /// <summary>
        /// 获取容器控件
        /// </summary>
        /// <returns></returns>
        Control GetTopControl();
        /// <summary>
        /// 初始化数据
        /// </summary>
        /// <param name="back"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        int Init(bool back, params object[] paras);
        /// <summary>
        /// 显示视图
        /// </summary>
        /// <param name="prev"></param>
        int Show(IView prev);
        /// <summary>
        /// 处理提示
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="para"></param>
        /// <returns></returns>
        int Notify<T>(T para);
        /// <summary>
        ///  视图名称
        /// </summary>
        string ViewName { get; set; }
    }
}
