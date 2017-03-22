using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using OpenNETCF.Windows.Forms;
using System.Runtime.InteropServices;
//using com.core.scpwms.server.mobile.bean;

namespace Framework
{
    /// <summary>
    /// 窗体基类
    /// </summary>
    public partial class BaseForm : Form,IView
        //, IPaintControl
    {

        private AlphaContainer _alphaManager;
        protected Dictionary<string, Control> _hotDic = new Dictionary<string, Control>();
        protected List<Control> _buttons = new List<Control>();
        protected List<bool> _btnStatus = new List<bool>();

        protected const int PAGE_SIZE = 3;

        /// <summary>
        /// KeyDown封装
        /// </summary>
        public class KeyDownEvntArgs : KeyEventArgs
        {
            public KeyDownEvntArgs(KeyEventArgs keyData)
                : base(keyData.KeyData)
            {
                Args = keyData;
            }

            public KeyEventArgs Args { get; set; }
        }

        /// <summary>
        /// 虚构函数
        /// </summary>
        public BaseForm()
        {
            try
            {
                // Instantiate before the components to handle events triggered by InitializeComponent
                _alphaManager = new AlphaContainer(this);
                InitializeComponent();
            }
            catch
            {
            }
        }
        /// <summary>
        /// 大小变更触发事件处理
        /// </summary>
        /// <param name="e"></param>
        protected override void OnResize(EventArgs e)
        {
            try
            {
                if (!DesignMode)
                    _alphaManager.OnResize(e);
                base.OnResize(e);
            }
            catch
            {
            }
        }
        /// <summary>
        /// 全部转移到OnPaint处理
        /// </summary>
        /// <param name="e"></param>
        protected override void OnPaintBackground(PaintEventArgs e)
        {
            try
            {
                //Prevent flicker, we will take care of the background in OnPaint()
                if (DesignMode)
                    base.OnPaintBackground(e);
            }
            catch
            {
            }
        }
        /// <summary>
        /// 描绘事件，调用_alphaManager处理输出
        /// </summary>
        /// <param name="e"></param>
        protected override void OnPaint(PaintEventArgs e)
        {
            try
            {
                if (DesignMode)
                {
                    base.OnPaint(e);
                    return;
                }
                _alphaManager.OnPaint(e);
                //if (this.PreView != null)
                //{
                //    //刷新画面
                //    (this.PreView as BaseForm).Refresh();
                //}
            }
            catch
            {
            }
        }


        /// <summary>
        /// 处理按键事件
        /// </summary>
        /// <param name="e"></param>
        /// <returns></returns>
        protected virtual bool ProcessKeyUp(KeyEventArgs e)
        {
            if (e.Modifiers == Keys.None)
            {
                switch (e.KeyCode)
                {
                    //case Keys.Up:
                    //    Thread.Sleep(100);
                    //    SendKeys.Send("+{Tab}");
                    //    break;
                    //case Keys.Down:
                    //    Thread.Sleep(100);
                    //    SendKeys.Send("{Tab}");
                    //    break;
                    default:
                        int k = (int)e.KeyCode;
                        if (_hotDic.ContainsKey(k.ToString()))
                        {
                            //处理快捷键
                            Control c = _hotDic[k.ToString()];
                            if (c is ButtonEx)
                            {
                                ButtonEx btnKey = c as ButtonEx;
                                if (btnKey.Enabled && btnKey.Visible)
                                {
                                    //e.Handled = true;
                                    btnKey.PerformClick();
                                }
                            }
                        }
                        break;
                }
            }
            return true;
        }

        /// <summary>
        /// 处理按键事件
        /// </summary>
        /// <param name="e"></param>
        /// <returns></returns>
        protected virtual bool ProcessKeyDown(KeyEventArgs e)
        {
            switch (e.KeyCode)
            {
                case Keys.Escape:
                    //AppContext.Instance().GetNavigator().Back(string.Empty, false);
                    this.DialogResult = DialogResult.Abort;
                    break;
            }
            if (e.Modifiers == Keys.None)
            {
                if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("green").Value))
                {
                    this.DialogResult = DialogResult.Cancel;
                }
                if (e.KeyValue == int.Parse(AppContext.Instance().GetHotKey("red").Value))
                {
                    this.DialogResult = DialogResult.Abort;
                }
            }
            return true;
        }


        /// <summary>
        /// 设计模式判断
        /// </summary>
        public bool DesignMode
        {
            get
            {
                return this.Site != null && this.Site.DesignMode;
            }

        }
        /// <summary>
        /// 是否为返回状态
        /// </summary>
        public bool IsBack { get; set; }

        public IView PreView { get; set; }

        public bool IsLoad { get; set; }
        /// <summary>
        /// 跳转到某画面
        /// </summary>
        public string BackTo { get; set; }

        #region IView 成员

        /// <summary>
        /// 初始化数据
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="back"></param>
        /// <param name="response"></param>
        /// <param name="paras"></param>
        /// <returns></returns>
        public virtual int Init(bool back, params object[] paras)
        {
            //初始化BusinessControl
            this.IsBack = back;
            if (_fieldDic.Count == 0)
            {
                InitControl(GetTopControl());
            }
            if (!back)
            {
                InitArgs = paras;
            }
            this.IsLoad = true;
            return 0;
        }
        /// <summary>
        /// 遍历控件初始化信息
        /// </summary>
        /// <param name="parent"></param>
        private void InitControl(Control parent)
        {
            foreach (Control field in parent.Controls)
            {
                if (field is TextEx)
                {
                    TextEx text = field as TextEx;
                    ////设置业务父窗体
                    //text.BusinessForm = this;
                    //缓存对应列表
                    if (!text.Field.IsEmpty())
                    {
                        //非字段文本框不缓存
                        _fieldDic.Add(text.Field, field);
                        _fieldLst.Add(text.Field);
                        text.Validating += new CancelEventHandler(text_Validating);
                    }
                    //最后一个文本框提交事务
                    if (!text.CommitKey.IsEmpty())
                        text.KeyDown += new KeyEventHandler(text_KeyDown);

                }
                else if (field is ButtonEx)
                {
                    ButtonEx btn = field as ButtonEx;
                    //确定处理
                    if (!btn.CommitKey.IsEmpty())
                    {
                        btn.Click += new EventHandler(btn_Click);
                    }
                    //处理快捷键
                    if (!btn.HotKey.IsEmpty())
                    {
                        //快捷键只处理一次
                        HotKey info = btn.HotKey.HotKey();
                        btn.Text = btn.Text.FormatEx(info.Text);
                        if (!_hotDic.ContainsKey(info.Value))
                            _hotDic.Add(info.Value, btn);

                    }
                    _buttons.Add(btn);
                    _btnStatus.Add(btn.Enabled);
                }
                else if (field is Panel)
                {
                    InitControl(field);
                }
            }
        }


        /// <summary>
        /// 按钮事件提交
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        void btn_Click(object sender, EventArgs e)
        {
            //提交事务
            Execute((sender as ButtonEx).CommitKey);
        }
        /// <summary>
        /// 最后一个文本框按Enter
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        void text_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyCode != Keys.Enter)
                {
                    return;
                }
                //确定
                //清空提示栏
                Notify(string.Empty);
                TextEx text = sender as TextEx;
                //先做单个Check
                if (!Valid(text, text.CommitKey))
                    return;

                //做整体提交Check
                Execute(text.CommitKey);
                
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 提交某请求
        /// </summary>
        /// <param name="command"></param>
        protected virtual int Execute(string command)
        {
            try
            {
                
                EnableButtons(false);

                //清空提示栏
                Notify(string.Empty);

                //先校验相关数据
                if (!ValidAll(command))
                    return 1;

                //获取数据
                MobileRequest req = GetInputData(command);

                //执行命令
                int ret = ExecuteCommand(command,ref req);
                if (ret == 0)
                {
                    //执行成功...
                }
                return ret;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                EnableButtons(true);
            }
            return 2;
        }
        /// <summary>
        /// 提交某请求
        /// </summary>
        /// <param name="command"></param>
        protected virtual int Execute<T>(string command, out MobileResponse<T> response)
        {
            response = null;
            try
            {
                EnableButtons(false);

                Notify(string.Empty);

                //先校验相关数据
                if (!ValidAll(command))
                    return 1;

                //获取数据
                MobileRequest req = GetInputData(command);

                //执行命令
                //服务器动作
                int ret = PostData(command, req, out response);

                //画面动作
                return ReactUi(command, response, ret);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                EnableButtons(true);
            }
            return 2;
        }

        /// <summary>
        /// 获取输入
        /// </summary>
        /// <param name="command"></param>
        /// <returns></returns>
        protected virtual MobileRequest GetInputData(string command)
        {
            MobileRequest req = new MobileRequest();
            req.PageId = this.ViewName;
            req.UserId = AppContext.Instance().LoginUser.UserId;
            if (AppContext.Instance().Owner != null)
            {
                req.WhId = AppContext.Instance().Owner.WhId;
                req.OwnerId = AppContext.Instance().Owner.id;
            }
            foreach (string field in _fieldLst)
            {

                //if (_fieldDic[field] is NumericText)
                //{
                //    TextEx text = _fieldDic[field] as TextEx;
                //    req.Parameters.Add(text.Field, text.Text.ToDouble());
                //}
                //else
                if (_fieldDic[field] is TextEx)
                {
                    TextEx text = _fieldDic[field] as TextEx;
                    req.Parameters.Add(text.Field, text.Text.Trim());
                }
            }
            return req;
        }

        /// <summary>
        /// 执行命令
        /// </summary>
        /// <param name="command"></param>
        /// <returns></returns>
        protected virtual int ExecuteCommand(string command, ref MobileRequest req)
        {
            //服务器动作
            MobileResponse<Dictionary<string,object>> response;
            int ret = PostData(command, req, out response);

            //画面动作
            return ReactUi(command, response, ret);
        }

        /// <summary>
        /// 画面动作
        /// </summary>
        /// <param name="command"></param>
        /// <param name="req"></param>
        /// <param name="response"></param>
        /// <returns></returns>
        protected virtual int ReactUi<T>(string command, MobileResponse<T> response, int ret)
        {
            //if (ret == 0 || ret == 4)
            //{
            //    AppContext.Instance().GetNavigator().Forword(response.TargetPageId.TrimEx());
            //}
            return 0;
        }
        /// <summary>
        /// 服务器事件
        /// </summary>
        /// <param name="command"></param>
        /// <param name="req"></param>
        /// <returns></returns>
        protected virtual int PostData<T>(string command, MobileRequest req, out MobileResponse<T> response)
        {
            //服务器动作
            response = AppContext.Instance().Execute<T>(command, req);

            switch (StringUtil.TrimString(response.SeverityMsgType).ToUpper())
            {
                case "E":
                    Message.Err(response.SeverityMsg);
                    return 1;
                case "W":
                    Message.Warn(response.SeverityMsg);
                    return 2;
                case "C":
                    if (Message.Ask(response.SeverityMsg) != DialogResult.Yes)
                        //不选是则退出
                        return 3;
                    return 4;
                case "M":
                    //消息
                    string message = StringUtil.TrimString(response.SeverityMsg);
                    //Beep.soundBeep(Beep.SUCCESSSOUND);
                    this.Notify(message);
                    break;
                case "M1":
                    Message.Info(response.SeverityMsg);
                    break;
                default:
                    break;
            }
            return 0;
        }


        /// <summary>
        /// 文本控件数据检验
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        void text_Validating(object sender, CancelEventArgs e)
        {
            try
            {
                ////清空提示栏
                //Notify(string.Empty);

                Valid((TextEx)sender);
                e.Cancel = false;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 检验控件
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="c"></param>
        /// <returns></returns>
        private bool Valid<T>(T c)
        {
            return Valid(c,string.Empty);
        }
        /// <summary>
        /// 检验控件
        /// </summary>
        /// <param name="c"></param>
        /// <returns></returns>
        private bool Valid(TextEx c)
        {
            return Valid(c, string.Empty);
        }
        /// <summary>
        /// 校验控件
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="c"></param>
        /// <param name="commit"></param>
        /// <returns></returns>
        private bool Valid<T>(T c,string commit)
        {
            return true;
        }
        /// <summary>
        /// 单个校验
        /// </summary>
        /// <param name="c"></param>
        /// <returns></returns>
        protected virtual bool Valid(TextEx c, string commit)
        {
            //先清空信息栏
            //this.Notify(string.Empty);

            if (c.ValidsCommit.IsEmpty() || c.ValidsCommit.Split(',').Any(p => p == commit))
            {
                //如果需要输入//必须输入判断
                if (c.MustInput && c.Text.IsEmpty())
                {
                    //提示必须输入
                    string para = c.FieldCaption.TrimEx();
                    if (para.IsEmpty())
                        para = c.Tip;
                    if (commit.IsEmpty())
                    {
                        //单纯文本框按Enter时,不限制焦点，仅提示
                        return true;
                    }
                    else
                    {
                        //非单个校验，而是某个按钮按下时，焦点不能转移
                        Message.Info("field.must.input".GetMessage().FormatEx(para));
                        c.SelectAll();
                        c.Focus();
                        return false;
                    }
                }
            }
            return true;
        }
        /// <summary>
        /// 按钮按下时的校验
        /// </summary>
        /// <param name="mode"></param>
        protected virtual bool ValidAll(string mode)
        {
            foreach (string field in _fieldLst)
            {
                if (_fieldDic[field] is TextEx)
                {
                    if (!Valid((TextEx)_fieldDic[field], mode))
                        return false;
                }
            }
            return true;
        }

        /// <summary>
        /// 显示视图
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="prev"></param>
        /// <param name="response"></param>
        /// <returns></returns>
        public virtual int Show(IView prev)
        {
            this.PreView = prev;
            //显示画面时光标控制
            EnableButtons(true);
            return 0;
        }
        /// <summary>
        /// 处理提示
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="para"></param>
        /// <returns></returns>
        public virtual int Notify<T>(T para)
        {

            if (para is KeyDownEvntArgs)
            {
                KeyDownEvntArgs info = para as KeyDownEvntArgs;
                return Notify(info);
            }
            else if (para is KeyEventArgs)
            {
                KeyEventArgs info = para as KeyEventArgs;
                return Notify(info);
            }
            else if (para is string)
            {
                string info = para as string;
                return Notify(info);
            }
            //else if (para is ExitArgs)
            //{
            //    ExitArgs info = para as ExitArgs;
            //    return Notify(info);
            //}
            return 0;
        }

        /// <summary>
        /// 按钮处理
        /// </summary>
        /// <param name="e"></param>
        /// <returns></returns>
        protected virtual int Notify(KeyDownEvntArgs e)
        {
            ProcessKeyDown(e.Args);
            return 0;
        }

        /// <summary>
        /// 按钮处理
        /// </summary>
        /// <param name="e"></param>
        /// <returns></returns>
        protected virtual int Notify(KeyEventArgs e)
        {
            ProcessKeyUp(e);
            return 0;
        }
        ///// <summary>
        ///// 退出消息
        ///// </summary>
        ///// <param name="e"></param>
        ///// <returns></returns>
        //protected virtual int Notify(ExitArgs e)
        //{
        //    return 0;
        //}

        /// <summary>
        /// 获取容器控件
        /// </summary>
        /// <returns></returns>
        public virtual Control GetTopControl()
        {
            return this;
        }
        /// <summary>
        /// 视图名称
        /// </summary>
        public virtual string ViewName
        {
            get;
            set;
        }

        #endregion

        #region 业务模型
        /// <summary>
        /// 业务模型（字段）和控件对照表
        /// </summary>
        protected Dictionary<string, Control> _fieldDic = new Dictionary<string, Control>();
        /// <summary>
        /// 控件的顺序
        /// </summary>
        private List<string> _fieldLst = new List<string>();
        /// <summary>
        /// 业务模型顺序
        /// </summary>
        //private List<Control> _fieldLst = new List<Control>();

        ///// <summary>
        ///// 检验事件发生
        ///// </summary>
        ///// <param name="validObject"></param>
        ///// <returns></returns>
        //private int Notify(ValidObject validObject)
        //{
        //    return validObject.Valid(validObject.ValidControl);
        //}
        /// <summary>
        /// 显示消息提示
        /// </summary>
        /// <param name="message"></param>
        /// <returns></returns>
        protected virtual  int Notify(String message)
        {
            if (this.PreView != null)
                this.PreView.Notify<string>(message);
            return 0;
        }
        private bool _buttonControl=true;
        /// <summary>
        /// 设置按钮可用
        /// </summary>
        /// <param name="flag"></param>
        protected virtual void EnableButtons(bool flag)
        {
            Cursor.Current = flag? Cursors.Default: Cursors.WaitCursor;
            //加快速度，暂时把按钮处理屏蔽
            return;
            if (flag)
            {

                if (_buttonControl)
                    //如果已设为可用，跳过
                    return;
                _buttonControl = true;
            }
            else
            {
                if (!_buttonControl)
                    //已设为不可用，重复设置跳过
                    return;
                _buttonControl = false; 
            }

            for (int i = 0; _buttons !=null && i < _buttons.Count; i++)
            {
                if (flag)
                {
                    //恢复
                    _buttons[i].Enabled = _btnStatus[i];
                }
                else
                {
                    //记住
                    _btnStatus[i] = _buttons[i].Enabled;
                    //置为不可用
                    _buttons[i].Enabled = flag;
                }
            }
            Application.DoEvents();
        }
        #endregion

        /// <summary>
        /// 清空窗体
        /// </summary>
        protected virtual void ClearForm()
        {
            foreach (string field in _fieldLst)
            {
                if (_fieldDic[field] is TextEx)
                {
                    _fieldDic[field].Text = string.Empty;
                }
            }
        }

        /// <summary>
        /// 获取指定页数据
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="page"></param>
        /// <param name="srcLst"></param>
        /// <returns></returns>
        protected virtual BindingList<T> GetCurrentPage<T>(int page, BindingList<T> srcLst)
        {
            //BindingList<T> ret = new BindingList<T>();
            //if (page < 1 || (page - 1) * PAGE_SIZE > srcLst.Count || srcLst.Count == 0)
            //    return ret;
            //for (int i = (page - 1) * PAGE_SIZE; i < page * PAGE_SIZE && i < srcLst.Count; i++)
            //{
            //    ret.Add(srcLst[i]);
            //}
            return GetCurrentPage(page, srcLst, PAGE_SIZE);
        }

        /// <summary>
        /// 获取指定页数据
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="page"></param>
        /// <param name="srcLst"></param>
        /// <returns></returns>
        protected virtual BindingList<T> GetCurrentPage<T>(int page, BindingList<T> srcLst,int pageSize)
        {
            BindingList<T> ret = new BindingList<T>();
            if (page < 1 || (page - 1) * pageSize > srcLst.Count || srcLst.Count == 0)
                return ret;
            for (int i = (page - 1) * pageSize; i < page * pageSize && i < srcLst.Count; i++)
            {
                ret.Add(srcLst[i]);
            }
            return ret;
        }
        /// <summary>
        /// 快捷键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BaseForm_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                //处理按键事件
                ProcessKeyDown(e);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }

        }
        /// <summary>
        /// 快捷键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BaseForm_KeyUp(object sender, KeyEventArgs e)
        {
            //处理按键事件
            ProcessKeyUp(e);
        }
        /// <summary>
        /// 初始化参数
        /// </summary>
        public object[] InitArgs
        {
            get
            {
                return _initArgs;
            }
            set
            {
                _initArgs = value;
            }
        }
        private object[] _initArgs;

        ///// <summary>
        ///// 触发显示方法
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void BaseForm_Activated(object sender, EventArgs e)
        //{

        //}
        /// <summary>
        /// 窗体加载
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BaseForm_Load(object sender, EventArgs e)
        {
            try
            {
                //this.Size = Screen.PrimaryScreen.WorkingArea.Size;
                InitForm();
                Show(null);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
            finally
            {
                IsLoad = false;
            }
        }

        protected virtual bool InitForm()
        {
            this.Size = Screen.PrimaryScreen.WorkingArea.Size;
            return true;
        }
    }
}