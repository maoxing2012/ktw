using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.ComponentModel;
using System.Drawing;
using System.Text.RegularExpressions;
using System.Reflection;
using OpenNETCF.Windows.Forms;
using System.Runtime.InteropServices;

namespace Framework
{
    /// <summary>
    /// 文本框控件
    /// </summary>
    public partial class TextEx : System.Windows.Forms.TextBox
    {
        private Dictionary<string, object> _extendDic = new Dictionary<string, object>();

        private int _oldSelLength;
        private int _oldSelStart;
        private string _oldText;
        private bool _selectall = true;
        private bool _selectNext = true;
        private bool _skipChange;
        private string _tip;
        private CharacterCasing characterCasing= CharacterCasing.Normal;

        [DllImport("coredll.dll")]
        public static extern IntPtr ImmAssociateContext(IntPtr hWnd, long hIMC);
        private long m_hImc;
        public TextEx()
        {
            InitializeComponent();
            if (!this.IsDesignMode())
            {
                WndProcHooker.HookWndProc(this, new WndProcHooker.WndProcCallback(WndProcCallback), Win32.WM_PAINT);
                WndProcHooker.HookWndProc(this, new WndProcHooker.WndProcCallback(WndProcCallback), Win32.WM_PAINTBACKGROUND);
            }

        }
        /// <summary>
        /// 大小写转换
        /// </summary>
        public CharacterCasing CharacterCasing
        {
            get
            {
                return this.characterCasing;
            }
            set
            {
                this.characterCasing = value;
                if (!this.IsDesignMode() && Environment.OSVersion.Platform== PlatformID.WinCE  )
                {
                    if (!this.IsDesignMode() && (base.Handle != IntPtr.Zero))
                    {
                        switch (value)
                        {
                            case CharacterCasing.Normal:
                                Win32.UpdateWindowStyle(base.Handle, 0x18, 0);
                                return;

                            case CharacterCasing.Upper:
                                Win32.UpdateWindowStyle(base.Handle, 0x10, 8);
                                return;

                            case CharacterCasing.Lower:
                                Win32.UpdateWindowStyle(base.Handle, 8, 0x10);
                                return;
                        }
                    }
                }
            }
        }

        /// <summary>
        /// 自动全选
        /// </summary>
        [DefaultValue(true)]
        public bool DoSelectAll
        {
            get
            {
                return this._selectall;
            }
            set
            {
                this._selectall = value;
            }
        }
        /// <summary>
        /// 按Enter后跳到下一个控件
        /// </summary>
        [DefaultValue(true)]
        public bool DoSelectNext
        {
            get
            {
                return this._selectNext;
            }
            set
            {
                this._selectNext = value;
            }
        }
        /// <summary>
        /// 扩展属性
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public object this[string key]
        {
            get
            {
                if (this._extendDic.ContainsKey(key))
                {
                    return this._extendDic[key];
                }
                return null;
            }
            set
            {
                if (this._extendDic.ContainsKey(key))
                {
                    this._extendDic[key] = value;
                }
                else
                {
                    this._extendDic.Add(key, value);
                }
            }
        }
        /// <summary>
        /// 提示信息
        /// </summary>
        public string Tip
        {
            get
            {
                return this._tip;
            }
            set
            {
                this._tip = value;
            }
        }

        /// <summary>
        /// 自动全选
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void TextEx_GotFocus(object sender, EventArgs e)
        {
            try
            {
                if (this.DoSelectAll)
                {
                    this.SelectAll();
                }
                if (string.IsNullOrEmpty(this.Text) && !string.IsNullOrEmpty(this.Tip))
                {
                    this.Invalidate();
                }
                InputMode.SetInputMode(this, IMEMode.Numbers);
            }
            catch
            {
                //do nothing
            }

        }
        /// <summary>
        /// 重绘提示信息
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void TextEx_LostFocus(object sender, EventArgs e)
        {
            try
            {
                if (!this.IsDesignMode() && (string.IsNullOrEmpty(this.Text) && !string.IsNullOrEmpty(this.Tip)))
                {
                    this.Invalidate();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 文本变更时，控制非法文字不可输入
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void TextEx_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (!this.IsDesignMode() && !this._skipChange)
                {
                    this._skipChange = true;
                    try
                    {
                        if (!this.CheckValue(this.Text))
                        {
                            this.Text = this._oldText;
                            this.SelectionStart = this._oldSelStart;
                            this.SelectionLength = this._oldSelLength;
                        }
                        else
                        {
                            this._oldText = this.Text;
                            this._oldSelStart = this.SelectionStart;
                            this._oldSelLength = this.SelectionLength;
                        }
                    }
                    finally
                    {
                        this._skipChange = false;
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 判断值
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        protected virtual bool CheckValue(string value)
        {
            if (string.IsNullOrEmpty(value))
                return true;

            if (!string.IsNullOrEmpty(Pattern))
            {
                Regex textReg = new Regex(this.Pattern);
                if (!textReg.IsMatch(value))
                    return false;
            }
            return true;
        }

        //public bool DesignMode
        //{
        //    get
        //    {
        //        return true;
        //    }

        //}


        ///// <summary>
        ///// WndProc
        ///// </summary>
        ///// <param name="m"></param>
        //protected override void WndProc(ref Microsoft.WindowsCE.Forms.Message m)
        //{
        //    CancelEventArgs e = new CancelEventArgs(false);
        //    switch (m.Msg)
        //    {
        //        case (int)Win32.WM_PAINT:
        //            this.OnPaint(e);
        //            break;
        //        //case (int)Win32.WM_PAINTBACKGROUND:
        //        //    this.OnPaintBackground(e);
        //        //    break;
        //    }
        //    if (!e.Cancel)
        //    {
        //        base.WndProc(ref m);
        //    }
        //}

        /// <summary>
        /// 处理消息
        /// </summary>
        /// <param name="hwnd"></param>
        /// <param name="msg"></param>
        /// <param name="wParam"></param>
        /// <param name="lParam"></param>
        /// <param name="handled"></param>
        /// <returns></returns>
        private int WndProcCallback(
           IntPtr hwnd, uint msg, uint wParam, int lParam, ref bool handled)
        {
            if (this.IsDesignMode())
                return 0;
            ConstructorInfo constrInfo;
            Graphics gr;
            switch (msg)
            {
                case Win32.WM_PAINT:
                    if (!string.IsNullOrEmpty(this.Text) || string.IsNullOrEmpty(this.Tip))
                    {
                        break;
                    }
                    Win32.PAINTSTRUCT ps = new Win32.PAINTSTRUCT();
                    if (Environment.OSVersion.Platform == PlatformID.WinCE)
                    {
                        //WinCE
                    }
                    IntPtr ret = Win32.BeginPaint(hwnd, ref ps);
                    if (ret.ToInt32() >= 0)
                    {
                        //constrInfo = AppContext.Instance().GetObjectConstructor(typeof(Graphics), new Type[] { typeof(IntPtr), typeof(bool) }); ;
                        // (Graphics)constrInfo.Invoke(new object[] { ps.hdc, false });
                        gr = Graphics.FromHdc(ret);
                        try
                        {
                            PaintEventArgsEx args = new PaintEventArgsEx(gr, this.ClientRectangle);
                            this.OnPaint(args);
                            handled = args.Handled;
                        }
                        finally
                        {
                            //gr.ReleaseHdc(ret);
                            gr.Dispose();
                            Win32.EndPaint(hwnd, ref ps);
                        }
                    }
                    break;
                case Win32.WM_PAINTBACKGROUND:
                    //constrInfo = AppContext.Instance().GetObjectConstructor(typeof(Graphics), new Type[] { typeof(IntPtr), typeof(bool) }); ;
                    //gr = (Graphics)constrInfo.Invoke(new object[] { (IntPtr)wParam, false });
                    IntPtr hdc = (IntPtr)(int)wParam;
                    gr = Graphics.FromHdc(hdc);
                    try
                    {
                        PaintEventArgsEx args = new PaintEventArgsEx(gr, this.ClientRectangle);
                        this.OnPaintBackground(args);
                        handled = args.Handled;
                    }
                    finally
                    {
                        //gr.ReleaseHdc(hdc);
                        gr.Dispose();
                    }
                    break;
            }            
            return 0;
        }

        /// <summary>
        /// 描绘处理
        /// </summary>
        /// <param name="e"></param>
        protected override void OnPaint(PaintEventArgs e)
        {
            if (this.IsDesignMode())
            {
                return;
            }
            if (!string.IsNullOrEmpty(this.Text) || string.IsNullOrEmpty(this.Tip))
            {
                return;
            }
            Size clientSize = this.ClientSize;
            Rectangle rect = new Rectangle(0, 0, clientSize.Width, clientSize.Height);
            e.Graphics.DrawRectangle(new Pen(this.BorderColor), rect);
            e.Graphics.FillRectangle(new SolidBrush(this.BackColor), rect);
            SizeF textSize = e.Graphics.MeasureString(this.Tip, this.Font);
            SolidBrush brush = new SolidBrush(Color.Gray);
            e.Graphics.DrawString(this.Tip, this.Font, brush, (float)2f, (float)((clientSize.Height - textSize.Height) / 2f));
            (e as PaintEventArgsEx).Handled = true;
        }

        /// <summary>
        /// 在OnPaint里处理
        /// </summary>
        /// <param name="e"></param>
        protected override void OnPaintBackground(PaintEventArgs e)
        {
            base.OnPaintBackground(e);
        }

        ///// <summary>
        /////  重绘事件
        ///// </summary>
        ///// <param name="e"></param>
        //protected virtual void OnPaint(CancelEventArgs e)
        //{
        //    if (this.IsDesignMode())
        //    {
        //        return;
        //    }
        //    if (!string.IsNullOrEmpty(this.Text) || string.IsNullOrEmpty(this.Tip))
        //    {
        //        return;
        //    }
        //    Win32.PAINTSTRUCT ps = new Win32.PAINTSTRUCT();
        //    using (Graphics gr = Graphics.FromHdc(Win32.BeginPaint(this.Handle, ref ps)))
        //    {
        //        try
        //        {
        //            Size clientSize = this.ClientSize;
        //            Rectangle rect = new Rectangle(0, 0, clientSize.Width, clientSize.Height);
        //            gr.DrawRectangle(new Pen(this.BorderColor), rect);
        //            gr.FillRectangle(new SolidBrush(this.BackColor), rect);
        //            SizeF textSize = gr.MeasureString(this.Tip, this.Font);
        //            SolidBrush brush = new SolidBrush(Color.Gray);
        //            gr.DrawString(this.Tip, this.Font, brush, (float)2f, (float)((clientSize.Height - textSize.Height) / 2f));
        //            e.Cancel = true;
        //        }
        //        finally
        //        {
        //            Win32.EndPaint(this.Handle, ref ps);
        //        }
        //    }
        //}

        ///// <summary>
        /////  重绘事件
        ///// </summary>
        ///// <param name="e"></param>
        //protected virtual void OnPaintBackground(CancelEventArgs e)
        //{
        //    if (DesignMode)
        //    {
        //        return;
        //    }
        //    if (!string.IsNullOrEmpty(this.Text) || string.IsNullOrEmpty(this.Tip))
        //    {
        //        return;
        //    }
        //    Win32.PAINTSTRUCT ps = new Win32.PAINTSTRUCT();
        //    using (Graphics gr = Graphics.FromHdc(Win32.BeginPaint(this.Handle, ref ps)))
        //    {
        //        try
        //        {
        //            Size clientSize = this.ClientSize;
        //            Rectangle rect = new Rectangle(0, 0, clientSize.Width, clientSize.Height);
        //            SolidBrush brush = new SolidBrush();

        //            rect.Inflate(1, 1);
        //            gr.FillRectangle(new SolidBrush(this.BackColor), rect);
        //            SizeF textSize = gr.MeasureString(this.Tip, this.Font);
        //            SolidBrush brush = new SolidBrush(Color.Gray);
        //            gr.DrawString(this.Tip, this.Font, brush, (float)2f, (float)((clientSize.Height - textSize.Height) / 2f));
        //            e.Cancel = true;
        //        }
        //        finally
        //        {
        //            Win32.EndPaint(this.Handle, ref ps);
        //        }
        //    }
        //}

        public Color BorderColor { get; set; }

        #region 领域模型
        /// <summary>
        /// 对应字段
        /// </summary>
        public string Field { get; set; }
        /// <summary>
        /// 按下控件执行某操作Key,如"changepassword"
        /// </summary>
        [DefaultValue("")]
        public string CommitKey { get; set; }
        /// <summary>
        /// 在某几个操作Key中需要执行校验数据，如"changepassword,login"用,号分割,如果为空则所有模式下都做数据校验
        /// </summary>
        [DefaultValue("")]
        public string ValidsCommit { get; set; }
        ///// <summary>
        ///// 业务模型（不能取Parent,因为会变化）
        ///// </summary>
        //[EditorBrowsable(EditorBrowsableState.Never)]
        //public Form BusinessForm { get; set; }
        #endregion

        /// <summary>
        /// 必须输入
        /// </summary>
        [DefaultValue(false)]
        public bool MustInput { get; set; }
        private bool _mustInput = false;

        /// <summary>
        /// 自动选择下一个控件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void TextEx_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if ((e.KeyData == Keys.Return) && (this.DoSelectNext && !this.Multiline))
                {
                    Form frm = this.FindForm();
                    if (frm != null)
                        frm.SelectNextControl(this, true, true, true, true);
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        ///// <summary>
        ///// 默认IME
        ///// </summary>
        //[DefaultValue(IMEMode.Numbers)]
        //public IMEMode IMEMode
        //{
        //    get
        //    {
        //        return _imeMode;
        //    }
        //    set
        //    {
        //        _imeMode = value;
        //    }
        //}
        //private IMEMode _imeMode = IMEMode.Numbers;
        ///// <summary>
        ///// 校验数据
        ///// </summary>
        ///// <param name="sender"></param>
        ///// <param name="e"></param>
        //private void TextEx_Validating(object sender, CancelEventArgs e)
        //{
        //    if (this.BusinessForm != null)
        //        this.BusinessForm.Notify(new ValidObject(this));
        //}

        /// <summary>
        /// 正则表达式匹配
        /// </summary>
        public string Pattern {
            get
            {
                return pattern;
            }
            set
            {
                pattern = value; 
            }
        }
        private string pattern = string.Empty;
        /// <summary>
        /// 非空错误时的字段名提示
        /// </summary>
        [DefaultValue("")]
        public string FieldCaption
        {
            get
            {
                return _filedCaption;
            }
            set
            {
                _filedCaption = value;
            }
        }
        private string _filedCaption=string.Empty;
        //[DefaultValue(OpenNETCF.Windows.Forms.CharacterCasing.Normal)]
        //public OpenNETCF.Windows.Forms.CharacterCasing CharacterCasing
        //{
        //    get
        //    {
        //        return _characterCasing;
        //    }
        //    set
        //    {
        //        _characterCasing = value;
        //    }
        //}
        //private OpenNETCF.Windows.Forms.CharacterCasing _characterCasing;

        protected override void OnHandleCreated(EventArgs e)
        {
            base.OnHandleCreated(e);
            if (m_hImc == 0)
            {
                m_hImc = (long)ImmAssociateContext(this.Handle, 0);
            }
        }
    }
}
