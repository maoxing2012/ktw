//--------------------------------------------------------------------- 
//THIS CODE AND INFORMATION ARE PROVIDED AS IS WITHOUT WARRANTY OF ANY 
//KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
//IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A 
//PARTICULAR PURPOSE. 
//---------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace Microsoft.Drawing
{
    public sealed class GradientFill
    {
        // This method wraps the PInvoke to GradientFill.
        // Parmeters:
        //  gr - The Graphics object we are filling
        //  rc - The rectangle to fill
        //  startColor - The starting color for the fill
        //  endColor - The ending color for the fill
        //  fillDir - The direction to fill
        //
        // Returns true if the call to GradientFill succeeded; false
        // otherwise.
        public static bool Fill(
            Graphics gr,
            Rectangle rc,
            Color startColor, Color endColor,
            FillDirection fillDir)
        {

            // Initialize the data to be used in the call to GradientFill.
            TRIVERTEX[] tva = new TRIVERTEX[2];
            tva[0] = new TRIVERTEX(rc.X, rc.Y, startColor);
            tva[1] = new TRIVERTEX(rc.Right, rc.Bottom, endColor);
            GRADIENT_RECT[] gra = new GRADIENT_RECT[] {
                                     new GRADIENT_RECT(0, 1)};

            // Get the hDC from the Graphics object.
            IntPtr hdc = gr.GetHdc();

            // PInvoke to GradientFill.
            bool b;

            //b = Win32Helper.GradientFill(
            //        hdc,
            //        tva,
            //        (uint)tva.Length,
            //        gra,
            //        (uint)gra.Length,
            //        (uint)fillDir);
            //System.Diagnostics.Debug.Assert(b, string.Format(
            //    "GradientFill failed: {0}",
            //System.Runtime.InteropServices.Marshal.GetLastWin32Error()));
            b = true;
            // Release the hDC from the Graphics object.
            gr.ReleaseHdc(hdc);

            return b;
        }

      
    }

    // The direction to the GradientFill will follow
    public enum FillDirection
    {
        //
        // The fill goes horizontally
        //
        LeftToRight = Win32Helper.GRADIENT_FILL_RECT_H,
        //
        // The fill goes vertically
        //
        TopToBottom = Win32Helper.GRADIENT_FILL_RECT_V
    }

  
}
