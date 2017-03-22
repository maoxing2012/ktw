using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Windows.Forms;
using System.ComponentModel;
using Microsoft.Drawing;

namespace Framework
{
    /// <summary>
    /// 透明标签
    /// </summary>
    public class LabelEx:System.Windows.Forms.Label,IAlphaControl
    {
        [EditorBrowsable(EditorBrowsableState.Always)]
        public event EventHandler ClickEx;

        #region 透明处理
        /// <summary>
        /// 标签控件
        /// </summary>
        public LabelEx():base()
        {
            if (!FormUtil.DesignMode(this))
            {
                base.Visible = false;
                //WndProcHooker.HookWndProc(this, new WndProcHooker.WndProcCallback(WndProcCallback), Win32.WM_PAINT);
                //WndProcHooker.HookWndProc(this, new WndProcHooker.WndProcCallback(WndProcCallback), Win32.WM_PAINTBACKGROUND);
            }
        }

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
            get 
            {
                return base.Text; 
            }
            set
            {
                if (base.Text == value)
                    return;
                base.Text = value;
                Refresh();
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
            if (_visible)
                Draw(gx);
        }

        /// <summary>
        /// Draws the label.
        /// </summary>
        public virtual void Draw(Graphics gx)
        {
            Rectangle rect =
             new Rectangle(this.Bounds.X, this.Bounds.Y, this.Bounds.Width - 1, this.Bounds.Height - 1);

            // Draw the border
            if (_border)
            {
                Pen pen = new Pen(this.ForeColor);
                gx.DrawRectangle(pen, rect);
                // Specify a rectangle to activate the line wrapping
                rect.Inflate(-4, -2);
            }

            if (!Transparent)
            {
                gx.FillRectangle(new SolidBrush(this.BackColor), rect);
                //gx.DrawRoundedRectangle(this.BackColor,this.BackColor,rect,new Size(7,7));
            }

            string text = this.Text;
            if (!this.Tag.IsEmpty())
            {
                text = this.Tag.ToString().FormatEx(this.Text);
            }           
            SizeF textSize = gx.MeasureString(text, this.Font);
            if (this.TextAlign == ContentAlignment.TopCenter)
                rect = new Rectangle((int)(this.Width - textSize.Width) / 2 + this.Bounds.X, this.Bounds.Y, (int)textSize.Width, (int)textSize.Height);
            else if (this.TextAlign == ContentAlignment.TopRight)
                rect = new Rectangle((int)(this.Width - textSize.Width) + this.Left, this.Bounds.Y, (int)textSize.Width, (int)textSize.Height);
            SolidBrush brush = new SolidBrush(this.ForeColor);
            //rect=new Rectangle(rect.X+7,rect.Y,rect.Width-14,rect.Height);
            gx.DrawString(text, this.Font, brush, rect);
            //base.Visible = !Transparent;
        }

        #endregion

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
        //            if (string.IsNullOrEmpty(this.Text))
        //            {
        //                break;
        //            }
        //            Win32.PAINTSTRUCT ps = new Win32.PAINTSTRUCT();
        //            IntPtr ret = Win32.BeginPaint(hwnd, ref ps);
        //            if (ret.ToInt32() >= 0)
        //            {
        //                //constrInfo = AppContext.Instance().GetObjectConstructor(typeof(Graphics), new Type[] { typeof(IntPtr), typeof(bool) }); ;
        //                // (Graphics)constrInfo.Invoke(new object[] { ps.hdc, false });
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
        //            //constrInfo = AppContext.Instance().GetObjectConstructor(typeof(Graphics), new Type[] { typeof(IntPtr), typeof(bool) }); ;
        //            //gr = (Graphics)constrInfo.Invoke(new object[] { (IntPtr)wParam, false });
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

        ///// <summary>
        ///// 描绘处理
        ///// </summary>
        ///// <param name="e"></param>
        //protected override void OnPaint(PaintEventArgs e)
        //{
        //    if (this.IsDesignMode())
        //    {
        //        return;
        //    }
        //    Size clientSize = this.ClientSize;
        //    Rectangle rect = new Rectangle(0, 0, clientSize.Width, clientSize.Height);
        //    //e.Graphics.DrawRectangle(new Pen(this.BorderColor), rect);
        //    e.Graphics.FillRectangle(new SolidBrush(this.BackColor), rect);
        //    string text = this.Text;
        //    if (!this.Tag.IsEmpty())
        //    {
        //        text = this.Tag.ToString().FormatEx(this.Text);
        //    }
        //    SizeF textSize = e.Graphics.MeasureString(text, this.Font);
        //    SolidBrush brush = new SolidBrush(this.ForeColor);
        //    e.Graphics.DrawString(text, this.Font, brush, (float)2f, (float)((clientSize.Height - textSize.Height) / 2f));
        //    (e as PaintEventArgsEx).Handled = true;
        //}

        ///// <summary>
        ///// 在OnPaint里处理
        ///// </summary>
        ///// <param name="e"></param>
        //protected override void OnPaintBackground(PaintEventArgs e)
        //{
        //    if(this.IsDesignMode())
        //        base.OnPaintBackground(e);
        //}

        private bool _border;

        /// <summary>
        /// Active or not a thin border around this label.
        /// </summary>
        public bool Border
        {
            get { return _border; }
            set
            {
                if (_border == value)
                    return;
                _border = value;
                Refresh();
            }
        }

        Control _parent = null;
        /// <summary>
        /// 父控件变更
        /// </summary>
        /// <param name="e"></param>
        protected override void OnParentChanged(EventArgs e)
        {
            try
            {
                if (_parent != null)
                    _parent.MouseDown -= new MouseEventHandler(ParentMouseDown);
              _parent = this.Parent;
              _parent.MouseDown += new MouseEventHandler(ParentMouseDown);
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 父控件单击事件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ParentMouseDown(object sender, MouseEventArgs e)
        {
            try
            {
                if (this.HitTest(e.X, e.Y))
                {
                    //触发事件
                    if(this.ClickEx != null)
                        ClickEx(this,EventArgs.Empty);
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 是否透明控件
        /// </summary>
        public bool Transparent
        {
            get
            {
                return _transparent;
            }
            set
            {
                _transparent = value;
                //base.Visible = !value;
                //this.Invalidate();
            }
        }
        private bool _transparent = false;
    }
}
