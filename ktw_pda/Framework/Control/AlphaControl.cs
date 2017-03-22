using System;
using System.Drawing;
using System.Windows.Forms;


namespace Framework
{
    /// <summary>
    /// Base Alpha Control.
    /// 
    /// The drawing is not managed by the framework so the base Control
    /// is made as not visible and the Visible property is replaced by a new one.
    /// 
    /// Note that this base control should be abstract but is not to allow
    /// the designer to handle it correctly (maybe there is a nice way to do that?).
    /// </summary>
    public class AlphaControl : Control, IAlphaControl
    {
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
        public AlphaControl()
        {
            if(!DesignMode)
                base.Visible = false;
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
        /// Must be overridden.
        /// </summary>
        public virtual void Draw(Graphics gx)
        {
        }

        /// <summary>
        /// 设计模式
        /// </summary>
        public bool DesignMode
        {
            get
            {
                return FormUtil.DesignMode(this);
            }
        }
    }
}
