using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using Microsoft.WindowsCE.Forms;
using Microsoft.Controls;
using System.Drawing;
using System.Runtime.InteropServices;

namespace System.Windows.Forms
{
    public static class ControlExtension
    {
        private static System.Reflection.Assembly mscorlibAssembly;

        public static bool IsDesignTime(this Control ctl)
        {

            // Determine if this instance is running against .NET Framework by using the MSCoreLib PublicKeyToken 
            if (mscorlibAssembly == null)
            {
                mscorlibAssembly = typeof(int).Assembly;
            }
            if ((mscorlibAssembly != null))
            {
                if (mscorlibAssembly.FullName.ToUpper().EndsWith("B77A5C561934E089"))
                {
                    return true;
                }
            }
            return false;

        }

        public static void RemoveStyle(this Control control, int style)
        {
            if (!control.IsDesignTime())
            {
                int currentStyle = (int)NativeMethods.GetWindowLong(control.Handle, (-16));

                //if (value) 
                //{ 
                //currentStyle |= style; 
                //} 
                //else 
                //{ 
                //    currentStyle = (int)NativeMethods.GetWindowLong(control.Handle, (-16)); 
                currentStyle &= ~style;

                //} 
                NativeMethods.SetWindowLong(control.Handle, (-16), currentStyle);
                NativeMethods.SetWindowPos(control.Handle, IntPtr.Zero, 0, 0, control.Width, control.Height, NativeMethods.SWP_NOSIZE | NativeMethods.SWP_NOACTIVATE | NativeMethods.SWP_NOMOVE | NativeMethods.SWP_FRAMECHANGED);
            }



        }

        public static void SetStyle(this Control control, int style)
        {
            if (!control.IsDesignTime())
            {
                int currentStyle = (int)NativeMethods.GetWindowLong(control.Handle, (-16));

                //if (value) 
                //{ 
                currentStyle |= style;
                //} 
                //else 
                //{ 
                //    currentStyle = (int)NativeMethods.GetWindowLong(control.Handle, (-16)); 
                //    currentStyle &= ~NativeMethods.WS_BORDER; 

                //} 
                NativeMethods.SetWindowLong(control.Handle, (-16), currentStyle);
                NativeMethods.SetWindowPos(control.Handle, IntPtr.Zero, 0, 0, control.Width, control.Height, NativeMethods.SWP_NOSIZE | NativeMethods.SWP_NOACTIVATE | NativeMethods.SWP_NOMOVE | NativeMethods.SWP_FRAMECHANGED);
            }



        }

        public static void ShowBorder(this Control control, bool value)
        {

            if (!control.IsDesignTime())
            {
                int currentStyle = (int)NativeMethods.GetWindowLong(control.Handle, (-16));

                if (value)
                {
                    currentStyle |= NativeMethods.WS_BORDER;
                }
                else
                {
                    currentStyle = (int)NativeMethods.GetWindowLong(control.Handle, (-16));
                    currentStyle &= ~NativeMethods.WS_BORDER;

                }
                NativeMethods.SetWindowLong(control.Handle, (-16), currentStyle);
                NativeMethods.SetWindowPos(control.Handle, IntPtr.Zero, 0, 0, control.Width, control.Height, NativeMethods.SWP_NOSIZE |
                    NativeMethods.SWP_NOACTIVATE | NativeMethods.SWP_NOMOVE | NativeMethods.SWP_FRAMECHANGED);
            }

        }

        public static bool IsHighResolution(this Form form)
        {
            SizeF currentScreen = form.CurrentAutoScaleDimensions;
            if (currentScreen.Height == 192)
            {
                return true;
            }
            return false;
        }

        public static void DefaultWinProc(this Control control, ref Message m)
        {
            MessageControl.DefaultWindowProc(ref m);
        }

        public static void EnableSubclassing(this Control control)
        {
            IMessage messageHost = control as IMessage;
            if (messageHost != null)
            {
                MessageControl.Subclass(messageHost, control.Handle);
            }
        }

        public static void EnableSubclassing(this Control control, IMessage messageHost)
        {
            if (messageHost != null)
            {
                MessageControl.Subclass(messageHost, control.Handle);
            }
        }


        public static void EnableParentSubclassing(this Control control)
        {
            IMessage messageHost = control as IMessage;
            if (messageHost != null)
            {
                // Check if the parent is not null 
                if (control.Parent != null)
                {
                    MessageControl.Subclass(messageHost, control.Parent.Handle);
                }
                else
                {
                    throw new ArgumentException("Cannot subclass. Parent is null");
                }
            }

        }

        public static void DisableParentSubclassing(this Control control)
        {
            IMessage messageHost = control as IMessage;
            if (messageHost != null)
            {
                if (control.Parent != null)
                {
                    MessageControl.UnSubclass(messageHost, control.Parent.Handle);
                }
            }
        }

        public static void DisableSubclassing(this Control control)
        {
            IMessage messageHost = control as IMessage;
            if (messageHost != null)
            {
                MessageControl.UnSubclass(messageHost, control.Handle);
            }
        }

    }
}