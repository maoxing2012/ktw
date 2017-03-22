using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Framework
{
    public interface INavigator
    {
        /// <summary>
        /// 导航到目标画面
        /// </summary>
        /// <param name="targetViewName"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        int Forword(string targetViewName, params object[] paras);

        /// <param name="clear"></param>
        /// <summary>
        /// 退回到某画面
        /// </summary>
        /// <param name="targetViewName"></param>
        /// <param name="clear">是否清空之间的画面缓存</param>
        /// <param name="paras"></param>
        void Back(string targetViewName, bool clear, params object[] paras);
        /// <summary>
        /// 切换窗口系列
        /// </summary>
        /// <param name="pattern"></param>
        void Swith(string pattern);
        /// <summary>
        /// 当前窗口变化
        /// </summary>
        event ViewChangedHandler ViewChanged;
    }
    /// <summary>
    /// 切换窗口处理函数
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    public delegate void ViewChangedHandler(object sender, ViewChangedArgs e);
}
