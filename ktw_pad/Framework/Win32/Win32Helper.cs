//--------------------------------------------------------------------- 
//THIS CODE AND INFORMATION ARE PROVIDED AS IS WITHOUT WARRANTY OF ANY 
//KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
//IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A 
//PARTICULAR PURPOSE. 
//---------------------------------------------------------------------
using System;
using System.Collections.Generic;
using System.Text;
using System.Runtime.InteropServices;
using System.Drawing;

namespace Microsoft.Drawing
{    
        public struct TRIVERTEX
        {
            public int x;
            public int y;
            public ushort Red;
            public ushort Green;
            public ushort Blue;
            public ushort Alpha;
            public TRIVERTEX(int x, int y, Color color)
                : this(x, y, color.R, color.G, color.B, color.A)
            {
            }
            public TRIVERTEX(
                int x, int y,
                ushort red, ushort green, ushort blue,
                ushort alpha)
            {
                this.x = x;
                this.y = y;
                this.Red = (ushort)(red << 8);
                this.Green = (ushort)(green << 8);
                this.Blue = (ushort)(blue << 8);
                this.Alpha = (ushort)(alpha << 8);
            }
        }

        public struct GRADIENT_RECT
        {
            public uint UpperLeft;
            public uint LowerRight;
            public GRADIENT_RECT(uint ul, uint lr)
            {
                this.UpperLeft = ul;
                this.LowerRight = lr;
            }
        }

        public sealed class Win32Helper
        {
            //[DllImport("coredll.dll", SetLastError = true, EntryPoint = "GradientFill")]
            //public extern static bool GradientFill(
            //    IntPtr hdc,
            //    TRIVERTEX[] pVertex,
            //    uint dwNumVertex,
            //    GRADIENT_RECT[] pMesh,
            //    uint dwNumMesh,
            //    uint dwMode);

            public const int GRADIENT_FILL_RECT_H = 0x00000000;
            public const int GRADIENT_FILL_RECT_V = 0x00000001;

    }
}
