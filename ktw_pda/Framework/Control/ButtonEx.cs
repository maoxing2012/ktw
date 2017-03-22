using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Drawing.Imaging;
using System.Windows.Forms;
using System.ComponentModel;
using OpenNETCF.Drawing;
using PlatformAPI;
using Microsoft.Drawing;

namespace Framework
{
    /// <summary>
    /// 按钮控件
    /// </summary>
    public class ButtonEx :System.Windows.Forms.Button, IAlphaControl
    {

        private AlphaImage _image = null;

        #region 背景透明处理

        private bool _visible = true;
        /// <summary>
        /// Show or Hide this Control.
        /// Note: Don't use the Control.Visible property...
        /// </summary>
        public new bool Visible
        {
            get { return _visible; }
            set
            {
                if (_visible == value)
                    return;
                _visible = value;
                Refresh();
            }
        }

        /// <summary>
        /// When the Text property is updated the Control is refreshed.
        /// </summary>
        public override string Text
        {
            get { return base.Text; }
            set
            {
                if (base.Text == value)
                    return;
                base.Text = value;
                Refresh();
            }
        }


        /// <summary>
        /// Default constructor.
        /// </summary>
        public ButtonEx()
        {
            try
            {
                if (!FormUtil.DesignMode(this))
                {
                    //非设计模式下
                    base.Visible = false;
                    //this.BorderStyle = BorderStyle.None;
                    this.TransparentColor = Color.FromArgb(0xff, 0xff, 0xff);
                    this.ParentChanged += new EventHandler(AlphaImageButton_ParentChanged);
                    this.EnabledChanged += new EventHandler(AlphaImageButton_EnabledChanged);
                    //if (!this.IsDesignMode())
                    //{
                    //    WndProcHooker.HookWndProc(this, new WndProcHooker.WndProcCallback(WndProcCallback), Win32.WM_PAINT);
                    //    WndProcHooker.HookWndProc(this, new WndProcHooker.WndProcCallback(WndProcCallback), Win32.WM_PAINTBACKGROUND);
                    //}
                }
            }
            catch
            {
            }

        }


        /// <summary>
        /// Overrides Control.Refresh() to forward to action to the 
        /// parent control, which is responsible to handle the drawing process.
        /// </summary>
        public override void Refresh()
        {
            if (this.Parent != null)
                this.Parent.Invalidate(this.Bounds);
        }

        /// <summary>
        /// Checks if the given coordinates are contained by this control.
        /// </summary>
        public bool HitTest(int x, int y)
        {
            return this.Bounds.Contains(x, y);
        }


        /// <summary>
        /// Overrides Control.Resize() to force a custom Refresh.
        /// </summary>
        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
            Refresh();
        }


        /// <summary>
        /// Internal Draw method, called by the container.
        /// Will call the actual Draw method if the control is visible.
        /// </summary>
        void IAlphaControl.DrawInternal(Graphics gx)
        {
            try
            {
                if (_visible && !base.Visible)
                    Draw(gx);
            }
            catch
            {
            }
        }

        /// <summary>
        /// Must be overridden.
        /// </summary>
        public virtual void Draw(Graphics gx)
        {
            if (this.IsDesignMode())
                return;
            //画背景
            if (RoundButton && false)
            {
                //gx.FillRectangle(new SolidBrush(Color.Transparent), this.ClientRectangle);

                //if (!this.IsDesignMode())
                //{
                //    IntPtr hdc = gx.GetHdc();
                //    GraphicsEx g = GraphicsEx.FromHdc(hdc);
                //    PenEx p = new PenEx(this.BackColor);
                //    g.DrawRoundRectangle(p, this.ClientRectangle, new Size(20, 20));
                //}
                gx.DrawRoundedRectangle(this.BackColor, BackColor, this.ClientRectangle, new Size(20, 20));
                //this.BorderStyle = BorderStyle.None;
                base.Visible = true;

            }
            ////if (this._image == null)
            ////{
            else if (!string.IsNullOrEmpty(this.ImageName))
            {
                this._image = AppContext.Instance().GetImage(this.ImageName);
            }
            else
            {
                this._image = null;
            }
            //}
            if (this._image != null)
            {
                Rectangle destRect = new Rectangle(this.Left, this.Top, base.Width, base.Height);
                //Rectangle rectangle2 = new Rectangle(0, 0, base.Image.Width, base.Image.Height);
                //ImageAttributes imageAttr = new ImageAttributes();
                //imageAttr.SetColorKey(this.TransparentColor, this.TransparentColor);
                //gx.DrawImage(base.Image, destRect, rectangle2.X, rectangle2.Y, rectangle2.Width, rectangle2.Height, GraphicsUnit.Pixel, imageAttr);
                //_image.CachImage = true;
                _image.Draw(gx, destRect);
                //this.BackgroundImage = _image.LastImage;
                ////this.BorderStyle = BorderStyle.None;
                //base.Visible = true;
            }
            //else
            //{
            //    base.Visible = true;
            //}
            //else
            //{
            //    Bitmap backImg = new Bitmap(this.ClientRectangle.Width,this.ClientRectangle.Height);
            //    using (Graphics gxBuffer2 = Graphics.FromImage(backImg))
            //    {
            //        IntPtr hdcBack = gxBuffer2.GetHdc();
            //        IntPtr hdcDest = gx.GetHdc();

            //        DrawingAPI.BitBlt(hdcBack, 0, 0, (int)ClientRectangle.Width, (int)ClientRectangle.Height, hdcDest, this.Left, this.Top, DrawingAPI.SRCCOPY);

            //        gxBuffer2.ReleaseHdc(hdcBack);
            //        gx.ReleaseHdc(hdcDest);
            //    }
            //}
            else
            {
                Rectangle destRect = new Rectangle(this.Left, this.Top, base.Width, base.Height);
                gx.FillRectangle(new SolidBrush(this.BackColor), destRect);
            }

            //写文字
            DrawText(gx, this.Location);
        }

        public  Image BackgroundImage
        {
            get
            {
                return _backgroundImage;
            }
            set
            {
                _backgroundImage = value;
            }
        }
        private Image _backgroundImage;

        /// <summary>
        /// 写文字
        /// </summary>
        /// <param name="gx"></param>
        private void DrawText(Graphics gx,Point leftTop)
        {
            if (!StringUtil.IsEmpty(this.Text))
            {
                //"用 户 登 录"
                String title = Text;
                SizeF textSize = gx.MeasureString(title, this.Font);
                Rectangle rect = new Rectangle((int)(this.Width - textSize.Width) / 2 + leftTop.X, (int)(this.Height - textSize.Height) / 2 + leftTop.Y, (int)textSize.Width, (int)textSize.Height);
                if (this.TextAlign == ContentAlignment2.MiddleLeft)
                {
                    rect = new Rectangle(10 + leftTop.X , (int)(this.Height - textSize.Height) / 2 + leftTop.Y, (int)textSize.Width, (int)textSize.Height);
                }
                else if (this.TextAlign == ContentAlignment2.MiddleRight)
                {
                    rect = new Rectangle((int)(this.Width - textSize.Width) + leftTop.X, (int)(this.Height - textSize.Height) / 2 + leftTop.Y, (int)textSize.Width, (int)textSize.Height);
                }
                SolidBrush br = new SolidBrush(this.ForeColor);
                if (!this.Enabled)
                    br = new SolidBrush(Color.Gray);
                gx.DrawString(title, this.Font, br, rect);
            }
        }

        public  Color TransparentColor
        {
            get
            {
                return _transparentColor;
            }
            set
            {
                _transparentColor = value;
            }
        }
        private Color _transparentColor=Color.FromArgb(0,0,0);

        public  ContentAlignment2 TextAlign
        {
            get
            {
                return _textAlign;
            }
            set
            {
                _textAlign = value;
            }
        }
        private ContentAlignment2 _textAlign;
        

        ///// <summary>
        ///// 描绘处理
        ///// </summary>
        ///// <param name="e"></param>
        //protected override void OnPaint(PaintEventArgs e)
        //{
        //    //base.OnPaint(e);
        //    try
        //    {
        //        if (this.IsDesignMode())
        //        {
        //            base.OnPaint(e);
        //            return;
        //        }
        //        //直接画图片
        //        //if (this.Image != null)
        //        //{
        //        //    e.Graphics.DrawImage(this.Image, e.ClipRectangle.X, e.ClipRectangle.Y);
        //        //}
        //        if (this.Focused)
        //        {
        //            //if (!this.IsDesignMode())
        //            //{
        //            //    IntPtr hdc = e.Graphics.GetHdc();
        //            //    GraphicsEx gx = GraphicsEx.FromHdc(hdc);
        //            //    PenEx p = new PenEx(Color.Red);
        //            //    gx.DrawRoundRectangle(p, this.ClientRectangle, new Size(20, 20));
        //            //}
        //            Rectangle destRec = this.ClientRectangle;
        //            destRec.Width--;
        //            destRec.Height--;
        //            destRec.Inflate(-1, -1);
        //            Pen p = new Pen(Color.Red, 2);
        //            p.DashStyle = System.Drawing.Drawing2D.DashStyle.Dash;
        //            e.Graphics.DrawRectangle(p, destRec);
        //        }
        //        //写文字
        //        DrawText(e.Graphics,new Point(0,0));      
        //    }
        //    catch
        //    {
        //    }
        //}

        ///// <summary>
        ///// 在OnPaint里处理
        ///// </summary>
        ///// <param name="e"></param>
        //protected override void OnPaintBackground(PaintEventArgs e)
        //{
        //    if (this.IsDesignMode())
        //    {
        //        base.OnPaintBackground(e);
        //        return;
        //    }

        //    //画背景
        //    if (RoundButton && false)
        //    {
        //        //Rectangle destRet = new Rectangle(this.Left, this.Top, base.Width, base.Height);
        //        //GraphicsEx g = GraphicsEx.FromGraphics(e.Graphics);
        //        //g.DrawRoundRectangle(new PenEx(this.BackColor), destRet, new Size(6, 6));

        //        //e.Graphics.FillRectangle(new SolidBrush(Color.Transparent), this.ClientRectangle);
        //        ////base.OnPaintBackground(e);
        //        //if (!this.IsDesignMode())
        //        //{
        //        //    IntPtr hdc = e.Graphics.GetHdc();
        //        //    GraphicsEx gx = GraphicsEx.FromHdc(hdc);
        //        //    PenEx p = new PenEx(this.BackColor);
        //        //    gx.DrawRoundRectangle(p, this.ClientRectangle, new Size(20, 20));
        //        //    //e.Graphics.DrawRectangle(new Pen(Color.Red), this.ClientRectangle);
        //        //}

        //        e.Graphics.DrawRoundedRectangle(this.BackColor, BackColor, this.ClientRectangle, new Size(20, 20));
        //    }
        //    else if (this.BackgroundImage != null)
        //    {
        //        e.Graphics.DrawImage(this.BackgroundImage, e.ClipRectangle.X, e.ClipRectangle.Y);
        //    }
                
        //}

        #endregion

        #region 事件处理

        private bool _pushed;
        private bool _hover;
        /// <summary>
        /// Get notified when the parent control is set to register to some mouse events.
        /// </summary>
        void AlphaImageButton_ParentChanged(object sender, EventArgs e)
        {
            this.Parent.MouseDown += new MouseEventHandler(Parent_MouseDown);
            this.Parent.MouseUp += new MouseEventHandler(Parent_MouseUp);
            this.Parent.MouseMove += new MouseEventHandler(Parent_MouseMove);
        }

        void AlphaImageButton_EnabledChanged(object sender, EventArgs e)
        {
            Refresh();
        }

        void Parent_MouseDown(object sender, MouseEventArgs e)
        {
            if (!this.Visible || !this.Enabled || !HitTest(e.X, e.Y))
                return;

            _pushed = true;
            _hover = true;

            Refresh();
        }

        void Parent_MouseUp(object sender, MouseEventArgs e)
        {
            if (this.IsDisposed)
                return;
            if (!this.Visible || !this.Enabled || !_pushed || !HitTest(e.X, e.Y))
                return;

            _pushed = false;
            _hover = false;

            Refresh();

            this.OnClick(null);
        }

        void Parent_MouseMove(object sender, MouseEventArgs e)
        {
            if (!this.Visible || !this.Enabled || !_pushed)
                return;

            bool hit = HitTest(e.X, e.Y);
            if (hit == _hover)
                return;

            _hover = hit;

            Refresh();
        }

        #endregion

        /// <summary>
        /// 图片名称
        /// </summary>
        public string ImageName
        {
            get
            {
                return _imageName;
            }
            set
            {
                //if (!string.IsNullOrEmpty(value) && !FormUtil.DesignMode(this))
                //{
                //    this._image = AppContext.Instance().GetImage(value);
                //}
                _imageName = value;
            }
        }
        private string _imageName = string.Empty;

        /// <summary>
        /// 提交事务关键字,如"login"
        /// </summary>
        public string CommitKey { 
            get
            {
                return _commitKey;
            }
            set
            {
                _commitKey = value;
            }
        }
        private string _commitKey = string.Empty;
        /// <summary>
        /// 热键处理
        /// </summary>
        public string HotKey
        {
            get
            {
                return _hotKey;
            }
            set
            {
                _hotKey = value;
            }
        }
        private string _hotKey = string.Empty;
        /// <summary>
        /// 圆角按钮
        /// </summary>
        public bool RoundButton { get; set; }

        private void InitializeComponent()
        {
            this.SuspendLayout();
            // 
            // ButtonEx
            // 
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.ButtonEx_KeyDown);
            this.ResumeLayout(false);

        }
        /// <summary>
        /// 按键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ButtonEx_KeyDown(object sender, KeyEventArgs e)
        {
            try
            {
                if (e.KeyData == Keys.Enter)
                {
                    if (this.Visible && this.Enabled)
                        this.PerformClick();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 模拟单击
        /// </summary>
        public virtual void PerformClick()
        {
            if (Enabled && Visible)
            {
                this.OnClick(EventArgs.Empty);
            }
        }

        #region old
        public Color BorderColor
        {
            get
            {
                return _BorderColor;
            }
            set
            {
                _BorderColor = value;
            }
        }
        private Color _BorderColor = Color.Black;
        #endregion`


        ///// <summary>
        ///// 处理消息
        ///// </summary>
        ///// <param name="hwnd"></param>
        ///// <param name="msg"></param>
        ///// <param name="wParam"></param>
        ///// <param name="lParam"></param>
        ///// <param name="handled"></param>
        ///// <returns></returns>
        //private int WndProcCallback(
        //   IntPtr hwnd, uint msg, uint wParam, int lParam, ref bool handled)
        //{
        //    if (this.IsDesignMode())
        //        return 0;
        //    Graphics gr;
        //    switch (msg)
        //    {
        //        case Win32.WM_PAINT:
        //            if (this.IsDesignMode())
        //                return 0;
        //            Win32.PAINTSTRUCT ps = new Win32.PAINTSTRUCT();
        //            IntPtr ret = Win32.BeginPaint(hwnd, ref ps);
        //            if (ret.ToInt32() >= 0)
        //            {
        //                gr = Graphics.FromHdc(ret);
        //                try
        //                {
        //                    PaintEventArgsEx args = new PaintEventArgsEx(gr, this.ClientRectangle);
        //                    this.OnPaint(args);
        //                    handled = args.Handled;
        //                }
        //                finally
        //                {
        //                    //gr.ReleaseHdc(ret);
        //                    gr.Dispose();
        //                    Win32.EndPaint(hwnd, ref ps);
        //                }
        //            }
        //            break;
        //        case Win32.WM_PAINTBACKGROUND:
        //            IntPtr hdc = (IntPtr)(int)wParam;
        //            gr = Graphics.FromHdc(hdc);
        //            try
        //            {
        //                PaintEventArgsEx args = new PaintEventArgsEx(gr, this.ClientRectangle);
        //                this.OnPaintBackground(args);
        //                handled = args.Handled;
        //            }
        //            finally
        //            {
        //                //gr.ReleaseHdc(hdc);
        //                gr.Dispose();
        //            }
        //            break;
        //    }
        //    return 0;
        //}

        [DefaultValue(false)]
        public bool TransparentImage { get; set; }
        [DefaultValue(false)]
        public bool ShowFocusBorder { get; set; }
    }
}
