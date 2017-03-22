using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.Windows.Forms;
using Microsoft.Drawing;

namespace Framework
{
    /// <summary>
    /// Helper class for container controls handling alpha channel.
    /// The controlled control must forward the Resize and Paint events.
    /// It uses a double buffer to avoid flickering.
    /// </summary>
    internal class AlphaContainer
    {
        /// <summary> Controlled Control. </summary>
        private Control _control;

        /// <summary> Back buffer used for double buffering. </summary>
        private Bitmap _backBuffer;


        /// <summary>
        /// Constructor, the controlled control must be supplied.
        /// </summary>
        /// <param name="control"></param>
        public AlphaContainer(Control control)
        {
            if (control == null)
                throw new ArgumentNullException("A valid control must be supplied.");

            _control = control;
            _control.Disposed += new EventHandler(_control_Disposed);

            CreateBackBuffer();
        }

        void _control_Disposed(object sender, EventArgs e)
        {
            try
            {
                if (_backBuffer != null)
                {
                    _backBuffer.Dispose();
                    _backBuffer = null;
                }
            }
            catch
            {
            }
        }

        private void CreateBackBuffer()
        {
            if (_backBuffer != null)
                _backBuffer.Dispose();
            _backBuffer = null;
            GC.Collect();
            GC.Collect();
            // The bitmap needs to be created with the 32bpp pixel format for the IImage to do the right thing.
            _backBuffer = new Bitmap(_control.ClientSize.Width, _control.ClientSize.Height, PixelFormat.Format32bppRgb);
        }

        
        /// <summary>
        /// Handles the Resize event to update the back buffer.
        /// </summary>
		public void OnResize(EventArgs e)
        {
            CreateBackBuffer();
		}

        /// <summary>
        /// Handles the Paint event, it is where the magic happens ;o)
        /// </summary>
        public void OnPaint(PaintEventArgs e)
        {
            if (_backBuffer != null)
            {
                // We need a Graphics object on the buffer to get an HDC
                using (Graphics gxBuffer = Graphics.FromImage(_backBuffer))
                {
                    // Since we nop'd OnPaintBackground, take care of it here
                    gxBuffer.Clear(_control.BackColor);

                    Region gxClipBounds = new Region(Rectangle.Ceiling(gxBuffer.ClipBounds));

                    // Iterates the child control list in reverse order
                    // to respect the Z-order
                    for (int i = _control.Controls.Count - 1; i >= 0; i--)
                    {
                        //System.Diagnostics.Debug.WriteLine(_control.Controls[i].Name );
                        // Handle controls inheriting AlphaControl only
                        Control ctrl = _control.Controls[i];
                        if (ctrl == null)
                            continue;

                        // Something to draw?
                        Rectangle clipRect = Rectangle.Intersect(e.ClipRectangle, ctrl.Bounds);
                        if (clipRect.IsEmpty)
                            continue;

                        // Clip to the control bounds
                        gxBuffer.Clip = new Region(clipRect);

                        // Perform the actual drawing
                        IAlphaControl alpha = ctrl as IAlphaControl;
                        if (alpha == null)
                        {
                            //if(ctrl is TextBox)
                            //{
                            //    //‘≤Ω«¥¶¿Ì
                            //    TextBox control = ctrl as TextBox;
                            //    int rad = 3;
                            //    int offset = 3;
                            //    if (control.BorderStyle != BorderStyle.None)
                            //    {
                            //        control.BorderStyle = BorderStyle.None;
                            //        control.Left += 6;
                            //        control.Width -= 7;
                            //    }
                            //    Rectangle rect = new Rectangle(control.Left - offset - rad, control.Top - offset, control.Width + offset + 4, control.Height + offset);
                            //    gxBuffer.Clip = new Region(e.ClipRectangle);
                            //    gxBuffer.DrawRoundedRectangle(Color.Black, control.BackColor, rect, new Size(7, 7));
                            //    gxBuffer.Clip = new Region(clipRect);
                            //}
                            continue;
                        }
                        alpha.DrawInternal(gxBuffer);
                        //System.Diagnostics.Debug.Write(_control.Controls[i].Name);
                        //System.Diagnostics.Debug.WriteLine("DrawInternal");
                    }

                    // Restore clip bounds
                    gxBuffer.Clip = gxClipBounds;
                }

                // Put the final composed image on screen.
                e.Graphics.DrawImage(_backBuffer, e.ClipRectangle, e.ClipRectangle, GraphicsUnit.Pixel);
            }
            else
            {
                // This should never happen, should it?
                e.Graphics.Clear(_control.BackColor);
            }
        }
    }
}
