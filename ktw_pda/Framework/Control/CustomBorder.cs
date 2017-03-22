using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using Microsoft.Drawing;
using System.Drawing;
using System.Runtime.InteropServices;

namespace Framework
{
    /// <summary>
    /// 自定义边框
    /// </summary>
    public class CustomBorder
    {
        #region fields

        private int offset = 1;
        private Color borderColor;
        private Size roundSize;
        private Control control;
        private bool drawShadow;

        #endregion

        #region constructors

        public CustomBorder()
        {
            this.borderColor = Color.Black;
            this.roundSize = new Size();
            this.drawShadow = true;
        }

        public CustomBorder(Control control)
            : this()
        {
            this.Attach(control);

        }

        #endregion

        #region public methods

        public void Attach(Control control)
        {
            this.control = control;
            this.SetStyle(control);
            this.HookParentPaintEvent(control);
        }

        #endregion

        #region properties

        public Size RoundSize
        {
            get
            {
                return roundSize;
            }
            set
            {
                roundSize = value;
            }
        }

        public Color BorderColor
        {
            get
            {
                return borderColor;
            }
            set
            {
                borderColor = value;
                if (this.control.Parent != null)
                {
                    Rectangle rect = new Rectangle(control.Left - offset, control.Top - offset, control.Width + offset + 1, control.Height + offset + 1);
                    control.Parent.Invalidate(rect);
                }
            }
        }

        #endregion

        #region helper methods

        private void SetStyle(Control control)
        {
            if (control is TextBox)
            {
                ((TextBox)control).BorderStyle = BorderStyle.None;
                offset = 3;
            }
            else
            {
                control.ShowBorder(false);
            }

        }

        private void HookParentPaintEvent(Control control)
        {
            if (control.Parent != null)
            {
                control.Parent.Paint += new PaintEventHandler(Parent_Paint);
            }
        }

        #endregion

        #region event handlers


        void Parent_Paint(object sender, PaintEventArgs e)
        {

            Rectangle bounds = new Rectangle(control.Left - offset, control.Top - offset, control.Width + offset, control.Height + offset);
            Rectangle clipRect = new Rectangle(e.ClipRectangle.Left, e.ClipRectangle.Top - 10, e.ClipRectangle.Width, e.ClipRectangle.Height);
            // Check if we need to draw border 
            if (bounds.IntersectsWith(clipRect))
            {

                if (this.roundSize.Height == 0 && this.roundSize.Width == 0)
                {
                    Rectangle rect = new Rectangle(control.Left - offset, control.Top - offset, control.Width + offset, control.Height + offset);
                    using (Pen pen = new Pen(this.borderColor))
                    {
                        e.Graphics.DrawRectangle(pen, rect);
                    }
                }
                else
                {
                    int rad = 3;
                    Rectangle rect = new Rectangle(control.Left - offset - rad, control.Top - offset, control.Width + offset + 4, control.Height + offset);
                    e.Graphics.DrawRoundedRectangle(borderColor, control.BackColor, rect, roundSize);
                }
            }

        }

        #endregion

    }
}
