using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    /// <summary>
    /// 视图变化时间参数
    /// </summary>
    public class ViewChangedArgs:EventArgs 
    {
        public ViewChangedArgs(IView view,IView prevView)
        {
            this.Current = view;
        }
        public ViewChangedArgs(string pattern, IView view)
        {
            this.Pattern = pattern;
            this.Current = view;
        }
        /// <summary>
        /// 当前视图
        /// </summary>
        public IView Current { get;private set; }
        /// <summary>
        /// 切换系列时用
        /// </summary>
        public string Pattern { get; private set; }
        /// <summary>
        /// 前一视图
        /// </summary>
        public IView PrevView { get; set; }
        ///// <summary>
        ///// Init数据
        ///// </summary>
        //public MobileResponse<T>  Response { get; set; }
    }
}
