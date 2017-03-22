using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace Framework
{
    public class InputMode
    {
        #region "DllImports"

        [DllImport("coredll.dll", EntryPoint = "GetCapture")]
        private static extern IntPtr GetCapture();
        [DllImport("coredll.dll", EntryPoint = "GetWindow")]
        private static extern IntPtr GetWindow(IntPtr hWnd, int uCmd);
        [DllImport("coredll.dll", EntryPoint = "SendMessage")]
        private static extern uint SendMessage(IntPtr hWnd, uint msg, uint
        wParam, uint lParam);

        #endregion

        const int GW_CHILD = 5;
        const uint EM_SETINPUTMODE = 0x00DE;

        /// <summary>
        /// Set Control Input Mode
        /// </summary>
        /// <param name="ctrl">Control</param>
        /// <param name="mode">InputMode</param>
        public static void SetInputMode(Control ctrl, IMEMode mode)
        {
            //ctrl.Capture = true;
            //IntPtr h = GetCapture();
            //ctrl.Capture = false;
            //IntPtr hEditbox = GetWindow(h, GW_CHILD);
            //SendMessage(hEditbox, EM_SETINPUTMODE, 0, (uint)mode);
            SendMessage(ctrl.Handle, 0x287, 0x30, 0x0);
        }
    }
    /// <summary>
    /// InputModes
    /// </summary>
    public enum IMEMode
    {
        Spell = 0,
        T9 = 1,
        Numbers = 2,
        Text = 3
    }
}
