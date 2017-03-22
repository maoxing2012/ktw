using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using Microsoft.WindowsCE.Forms;
using System.Runtime.InteropServices;

namespace Microsoft.Controls
{
    /// <summary> 
    /// Subclassing control implementation 
    /// </summary> 
    public class MessageControl : IDisposable
    {
        #region fields

        private IntPtr handle;
        private IntPtr defWindowProc;
        private WndProcDelegate windowProc;
        private bool disposed = false;
        private static Dictionary<IntPtr, MessageControl> handleList;
        internal List<IMessage> hostList;

        #endregion

        #region constructors

        static MessageControl()
        {
            handleList = new Dictionary<IntPtr, MessageControl>();
        }

        public MessageControl()
        {
            hostList = new List<IMessage>();
        }

        #endregion

        #region public methods


        /// <summary> 
        /// Subclasses the window 
        /// </summary> 
        /// <param name="messageHost">Instance of the class that implements IMessage</param> 
        /// <param name="handle">Handle of the window to subclass</param> 
        /// <returns>An instance of the MessageControl</returns> 
        public static MessageControl Subclass(IMessage messageHost, IntPtr handle)
        {
            MessageControl msgControl = null;

            if (messageHost is IMessage)
            {
                // Check if we already subclassed this window 
                if (handleList.Keys.Contains(handle))
                {
                    // We already subclassed it. Retrieve the MessageControl 
                    msgControl = handleList[handle];
                    if (messageHost is Control)
                    {
                        ((Control)messageHost).HandleDestroyed += new EventHandler(messageHost_HandleDestroyed);
                    }
                    // Add the message host to array list 
                    msgControl.AddHost(messageHost);
                }
                else // New window 
                {
                    // Create new instance of the MessageControl 
                    msgControl = new MessageControl();
                    // Associate it with the handle 
                    handleList[handle] = msgControl;
                    // Add  
                    msgControl.AddHost(messageHost);
                    msgControl.Subclass(handle);
                }
            }

            return msgControl;
        }


        /// <summary> 
        /// Stop subclassing a particular handle and for message host. 
        /// </summary> 
        /// <param name="messageHost"></param> 
        /// <param name="handle"></param> 
        public static void UnSubclass(IMessage messageHost, IntPtr handle)
        {
            MessageControl messageControl = null;

            if (handleList.TryGetValue(handle, out messageControl))
            {
                messageControl.hostList.Remove(messageHost);
                // Check if there are no hosts registered 
                if (messageControl.hostList.Count == 0)
                {
                    messageControl.UnSubclass(handle);
                }
            }
        }

        /// <summary> 
        /// Subclasses the window 
        /// </summary> 
        /// <param name="handle">Handle of the window to subclass</param> 
        public void Subclass(IntPtr handle)
        {
            this.handle = handle;
            if (this.handle != IntPtr.Zero)
            {
                // Get the original window procedure 
                this.defWindowProc = NativeMethods.GetWindowLong(this.handle, -4);
                // Create an instance of the delegate 
                windowProc = new WndProcDelegate(Callback);
                // Tell the window to use our delegate instead 
                NativeMethods.SetWindowLong(handle, (-4), windowProc);
            }
        }

        /// <summary> 
        /// Calls to the default window procedure 
        /// </summary> 
        /// <param name="m">Message to pass</param> 
        public void DefaultWinProc(ref Message m)
        {
            m.Result = NativeMethods.CallWindowProc(this.defWindowProc, m.HWnd, (uint)m.Msg, m.WParam, m.LParam);
        }


        /// <summary> 
        /// Makes a call to a default win procedure for a particular MessageControl 
        /// </summary> 
        /// <param name="m"></param> 
        public static void DefaultWindowProc(ref Message m)
        {
            MessageControl messageControl = null;

            if (handleList.TryGetValue(m.HWnd, out messageControl))
            {
                messageControl.DefaultWinProc(ref m);
            }
        }

        /// <summary> 
        /// Unsublcasses the window 
        /// </summary> 
        public void UnSubclass()
        {
            NativeMethods.SetWindowLong(handle, -4, (int)this.defWindowProc);
        }

        private void UnSubclass(IntPtr handle)
        {
            NativeMethods.SetWindowLong(handle, -4, (int)this.defWindowProc);
        }

        #endregion

        #region helper methods

        static void messageHost_HandleDestroyed(object sender, EventArgs e)
        {
            MessageControl messageControl = null;
            Control ctl = sender as Control;

            if (handleList.TryGetValue(ctl.Handle, out messageControl))
            {
                messageControl.UnSubclass(ctl.Handle);
            }
        }

        private void AddHost(IMessage messageHost)
        {
            hostList.Add(messageHost);
        }


        // Native callback procedure 
        private IntPtr Callback(IntPtr hWnd, uint msg, IntPtr wparam, IntPtr lparam)
        {
            Message message = Message.Create(hWnd, (int)msg, wparam, lparam);
            try
            {
                // Iterate over message hosts 
                foreach (IMessage imessage in hostList)
                {
                    imessage.WndProc(ref message);
                }
            }
            catch (Exception exception)
            {
                throw;
            }
            if (msg == 130)
            {
                this.ReleaseHandle();
            }
            return message.Result;
        }

        // Clean up 
        private void ReleaseHandle()
        {

            if (this.handle == IntPtr.Zero)
            {
                return;
            }

            this.UnSubclass();

            this.handle = IntPtr.Zero;

            this.defWindowProc = IntPtr.Zero;
            this.windowProc = null;

            this.handle = IntPtr.Zero;

        }

        #endregion

        #region IDisposable Members


        ~MessageControl()
        {
            if (handle != IntPtr.Zero)
            {
                ReleaseHandle();
            }
        }

        private void Dispose(bool disposing)
        {
            if (!this.disposed)
            {

                // Call the appropriate methods to clean up 
                // unmanaged resources here. 
                // If disposing is false, 
                // only the following code is executed. 
                ReleaseHandle();
                handle = IntPtr.Zero;

                // Note disposing has been done. 
                disposed = true;

            }


        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        #endregion
    } 
}
