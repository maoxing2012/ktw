using System.Drawing;
using System;
using System.ComponentModel;
using System.Windows.Forms;
using System.Drawing.Imaging;
using OpenNETCF.Windows.Forms;
using System.Reflection;
using OpenNETCF.Drawing;
using System.IO;
using OpenNETCF.Reflection;


namespace Framework
{
	/// <summary>
	/// Simple PictureBox control handling alpha channel.
	/// </summary>
    public class PictureEx : System.Windows.Forms.PictureBox, ISupportInitialize, IAlphaControl
	{

        #region 背景透明处理

        private AlphaImage _image = null;
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

        ///// <summary>
        ///// When the Text property is updated the Control is refreshed.
        ///// </summary>
        //public override string Text
        //{
        //    get { return base.Text; }
        //    set
        //    {
        //        if (base.Text == value)
        //            return;
        //        base.Text = value;
        //        Refresh();
        //    }
        //}


        /// <summary>
        /// Default constructor.
        /// </summary>
        public PictureEx()
        {
            if (!FormUtil.DesignMode(this))
            {
                base.Visible = false;
                this.TransparentColor = Color.FromArgb(0xff, 0xff, 0xff);
                this.ParentChanged += new EventHandler(AlphaImageButton_ParentChanged);
                this.EnabledChanged += new EventHandler(AlphaImageButton_EnabledChanged);
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
            if (_visible && !base.Visible )
                Draw(gx);
        }

        /// <summary>
        /// Must be overridden.
        /// </summary>
        public virtual void Draw(Graphics gx)
        {
            if (this.IsDesignMode())
            {
                //base.Visible = true;
                MemoryStream stream = (MemoryStream)Assembly2.GetEntryAssembly().GetManifestResourceStream(this.ImageName);
                Bitmap img = new Bitmap(stream);
                this.Image = img;
                return;
            }
            //if (this._image == null)
            //{
            if (!string.IsNullOrEmpty(this.ImageName))
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
                //if (base.SizeMode == PictureBoxSizeMode.CenterImage)
                //{
                //    destRect = new Rectangle((base.Width / 2) - (base.Image.Width / 2)+this.Left, (base.Height / 2) - (base.Image.Height / 2)+this.Top, base.Image.Width, base.Image.Height);
                //}
                //else if (base.SizeMode == PictureBoxSizeMode.Normal)
                //{
                //    destRect = new Rectangle(this.Left, this.Top, base.Image.Width, base.Image.Height);
                //}
                //ImageAttributes imageAttr = new ImageAttributes();
                //imageAttr.SetColorKey(this.TransparentColor, this.TransparentColor);
                //gx.DrawImage(base.Image, destRect, rectangle2.X, rectangle2.Y, rectangle2.Width, rectangle2.Height, GraphicsUnit.Pixel, imageAttr);
                //gx.DrawImage(base.Image, destRect, rectangle2, GraphicsUnit.Pixel);
                if (!this.IsDesignMode())
                {
                    _image.CachImage = ShowFlag;
                    _image.Draw(gx, destRect);
                    //设置Image，显示图片
                    if (ShowFlag)
                        this.Image = _image.LastImage;
                }
                base.Visible = ShowFlag;
            }
            else
            {
                Graphics g = gx;
                Rectangle destRect = new Rectangle(this.Left, this.Top, base.Width, base.Height);
                g.FillRectangle(new SolidBrush(this.BackColor), destRect);
            }
            //写文字
            if (!StringUtil.IsEmpty(this.TitleText))
            {
                Graphics g = gx;
                //"用 户 登 录"
                String title = TitleText;
                SizeF textSize = g.MeasureString(title, this.TextFont);
                Rectangle rect = new Rectangle((int)(this.Width - textSize.Width) / 2+this.Left , (int)(this.Height - textSize.Height) / 2+this.Top, (int)textSize.Width, (int)textSize.Height);
                g.DrawString(title, this.TextFont, new SolidBrush(TextColor), rect);
            }
        }

        #endregion

        /// <summary>
        /// 显示文本
        /// </summary>
        public string TitleText
        {
            get
            {
                return _titleText;
            }
            set
            {
                _titleText = value;
            }
        }
        private string _titleText = string.Empty;
        /// <summary>
        /// 文本字体
        /// </summary>
        public Font TextFont
        {
            get
            {
                return _textFont;
            }
            set
            {
                _textFont = value;
            }
        }
        private Font _textFont;
        /// <summary>
        /// 字体颜色
        /// </summary>
        public Color TextColor
        {
            get
            {
                return _textColor;
            }
            set
            {
                _textColor = value;
            }
        }
        private Color _textColor=Color.Black;
        /// <summary>
        /// 图片名称
        /// </summary>
        public string ImageName { 
            get
            {
                return _imageName;
            }
            set
            {
                //if(!string.IsNullOrEmpty(value) && !FormUtil.DesignMode(this))
                //{
                //    this._image=AppContext.Instance().GetImage(value);
                //}
                _imageName=value;
            }
        }
        private string _imageName =string.Empty;

        /// <summary>
        /// 显示属性，多数情况下不需要设，当别的控件挡住了图片控件时需要改为True
        /// </summary>
        [DefaultValue(false)]
        public bool ShowFlag
        {
            get
            {
                return _showFlag;
            }
            set
            {
                _showFlag = value;
            }
        }
        private bool _showFlag;
        #region ISupportInitialize 成员

        public void BeginInit()
        {
            //throw new NotImplementedException();
        }

        public void EndInit()
        {
            //throw new NotImplementedException();
        }

        #endregion

        #region 事件处理

        private bool _pushed;
        private bool _hover;
        /// <summary>
        /// Get notified when the parent control is set to register to some mouse events.
        /// </summary>
        void AlphaImageButton_ParentChanged(object sender, EventArgs e)
        {
            if (this.IsDesignMode())
                return;
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
            if (this.IsDisposed) return;
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

        ///// <summary>
        ///// 画画面
        ///// </summary>
        ///// <param name="e"></param>
        //protected override void OnPaint(PaintEventArgs e)
        //{
        //    try
        //    {
        //        //显示图片
        //        base.OnPaint(e);

        //        //写文字
        //        if (!StringUtil.IsEmpty(this.TitleText))
        //        {
        //            Graphics g = e.Graphics;
        //            //"用 户 登 录"
        //            String title = TitleText;
        //            SizeF textSize = g.MeasureString(title, this.TextFont);
        //            Rectangle rect = new Rectangle((int)(this.Width - textSize.Width) / 2 + this.Left, (int)(this.Height - textSize.Height) / 2 + this.Top, (int)textSize.Width, (int)textSize.Height);
        //            g.DrawString(title, this.TextFont, new SolidBrush(TextColor), rect);
        //        }
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
        //    }
        //}

        ///// <summary>
        ///// 绘制图形部分
        ///// </summary>
        //private Rectangle _ImageRectangle
        //{
        //    get
        //    {
        //        Rectangle rectangle = new Rectangle(0, 0, 0, 0);
        //        if (this.Image != null)
        //        {
        //            switch (this.SizeMode)
        //            {
        //                case PictureBoxSizeMode.Normal:
        //                    rectangle.Size = this.Image.Size;
        //                    return rectangle;

        //                case PictureBoxSizeMode.StretchImage:
        //                    rectangle.Size = base.ClientSize;
        //                    return rectangle;

        //                case ((PictureBoxSizeMode)2):
        //                    return rectangle;

        //                case PictureBoxSizeMode.CenterImage:
        //                    {
        //                        rectangle.Size = this.Image.Size;
        //                        Size clientSize = base.ClientSize;
        //                        rectangle.X = (clientSize.Width - rectangle.Width) / 2;
        //                        rectangle.Y = (clientSize.Height - rectangle.Height) / 2;
        //                        return rectangle;
        //                    }
        //            }
        //        }
        //        return rectangle;
        //    }
        //}

        private Color _transparentColor;
        public Color TransparentColor
        {
            get
            {
                return this._transparentColor;
            }
            set
            {
                this._transparentColor = value;
            }
        }

    }
}
