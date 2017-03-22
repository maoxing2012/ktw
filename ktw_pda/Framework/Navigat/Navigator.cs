using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Framework
{
    /// <summary>
    /// 画面导航类
    /// </summary>
    public class Navigator:INavigator
    {

        /// <summary>
        /// 视图缓存
        /// </summary>
        private Dictionary<string, Dictionary<string, IView>> _viewDic = new Dictionary<string, Dictionary<string, IView>>();

        /// <summary>
        /// 视图堆栈
        /// </summary>
        private Dictionary<string, Stack<string>> _viewSta = new Dictionary<string, Stack<string>>();

        private string _pattern = "menu";

        /// <summary>
        /// 虚构函数
        /// </summary>
        internal Navigator()
        {
        }

        #region INavigator 成员

        /// <summary>
        /// 导航到某界面
        /// </summary>
        /// <param name="targetViewName"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public int Forword(string targetViewName, params object[] paras)
        {
            GC.Collect();
            GC.Collect();
            IView view=null;
            IView currentView = null;
            int ret=0;
            try
            {
                string current = string.Empty ;
                if (_viewSta.ContainsKey(_pattern) && _viewSta[_pattern].Count > 0)
                {
                    //如果有堆栈则有当前页面
                    current = _viewSta[_pattern].Peek();
                    //取出当前视图
                    currentView = _viewDic[_pattern][current];
                }

                if (!_viewDic.ContainsKey(_pattern) || !_viewDic[_pattern].ContainsKey(targetViewName))
                {
                    //没有缓存视图，新建视图
                    view = AppContext.Instance().CreateObject<IView>(targetViewName);
                    view.ViewName = targetViewName;
                    //缓存
                    if (!_viewDic.ContainsKey(_pattern))
                        _viewDic.Add(_pattern, new Dictionary<string, IView>());
                    if (!_viewDic[_pattern].ContainsKey(targetViewName))
                        _viewDic[_pattern].Add(targetViewName, view);
                }
                else
                {
                    //否则从缓存中取出
                    view = _viewDic[_pattern][targetViewName];
                }
                //初始化画面
                ret = view.Init(false, paras);
                if (ret == 0)
                {
                    //成功加载，入栈
                    if(!_viewSta.ContainsKey(_pattern))
                        _viewSta.Add(_pattern,new Stack<string>());

                    _viewSta[_pattern].Push(targetViewName);
                    if (ViewChanged != null)
                        //让菜单界面做出变更
                        ViewChanged(this, new ViewChangedArgs(view, currentView));
                }
            }
            
            catch (Exception ex)
            {
                ret = 1;
                ExceptionPolicy.HandleException(ex);
            }
            return ret;
        }

        /// <summary>
        /// 退回到某界面
        /// </summary>
        /// <param name="targetViewName"></param>
        /// <param name="clear"></param>
        /// <param name="paras"></param>
        public void Back(string targetViewName, bool clear, params object[] paras)
        {
            try
            {
                IView view = null;
                string viewKey = string.Empty;
                while (_viewSta[_pattern].Count > 1)
                {
                    //取出画面
                    viewKey = _viewSta[_pattern].Pop();
                    if (clear)
                    {
                        //清空缓存
                        if (_viewDic[_pattern].ContainsKey(viewKey))
                        {
                            BaseForm form = _viewDic[_pattern][viewKey] as BaseForm;
                            form.Dispose();
                            _viewDic[_pattern].Remove(viewKey);
                        }
                    }
                    //前一个画面
                    viewKey = _viewSta[_pattern].Peek();
                    if (viewKey.Equals(targetViewName) || string.IsNullOrEmpty(targetViewName) )
                    {
                        //为空时只退一个画面
                        view = _viewDic[_pattern][viewKey];
                        //调用退回初始化
                        if (view != null)
                            //菜单画面特殊处理
                            view.Init(true, paras);

                        if (ViewChanged != null)
                        {
                            //通知菜单处理页面切换
                            ViewChanged(this, new ViewChangedArgs(view, null));
                        }
                        return;
                    }
                }
                throw new ApplicationException(string.Format("退回的画面{0}找不到，请确认！",targetViewName));
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 切换页面系列
        /// </summary>
        /// <param name="pattern"></param>
        public void Swith(string pattern)
        {
            try
            {
                //取出新系列的第一个画面
                if (!_viewSta.ContainsKey(pattern))
                {
                    //如果不存在，为首次加载Menu
                    _viewSta.Add(pattern, new Stack<string>());
                    _viewSta[pattern].Push("menu");
                    if (!_viewDic.ContainsKey(pattern))
                        _viewDic.Add(pattern, new Dictionary<string, IView>());
                    //非主Menu，加载子Menu,否则加载null
                    IView menu = null;
                    if (!pattern.Equals("menu"))
                    {
                        menu = AppContext.Instance().CreateObject<IView>(pattern);
                    }
                    if (!_viewDic[pattern].ContainsKey("menu"))
                        _viewDic[pattern].Add("menu", menu);

                    if (menu != null)
                        //菜单画面特殊处理
                        menu.Init(true);

                    if (ViewChanged != null)
                    {
                        //通知菜单处理页面切换
                        ViewChanged(this, new ViewChangedArgs(pattern, menu));
                    }
                }
                //如果没变换系列，直接退出
                if (_pattern.Equals(pattern))
                    return;
                if (ViewChanged != null)
                {
                    string key = _viewSta[pattern].Peek();
                    IView view = _viewDic[pattern][key];

                    if (view != null)
                        //菜单画面特殊处理
                        view.Init(true);

                    //通知菜单处理页面切换
                    ViewChanged(this, new ViewChangedArgs(view,null));
                }
                _pattern = pattern;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        public event ViewChangedHandler ViewChanged;

        #endregion
    }
}
