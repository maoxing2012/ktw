using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using OpenNETCF.Windows.Forms;
using System.Runtime.InteropServices;
using Microsoft.WindowsCE.Forms;

namespace Denso
{
    public enum KeyModifiers
    {

        None = 0,

        Alt = 1,

        Control = 2,

        Shift = 4,

        Windows = 8,

        Modkeyup = 0x1000,

    }

    class MsgWindow : MessageWindow
    {
        #region p/invoke



        [DllImport("coredll.dll")]

        public static extern bool RegisterHotKey(IntPtr hWnd,      // Window handle 

            UInt32 id,                                                                       // Hot key identifier 

            int Modifiers,                                                            // Key-modifier options 

            int key);                                                                    // Virtual-key code



        [DllImport("coredll.dll", SetLastError = true)]
        public static extern bool UnregisterHotKey(IntPtr hWnd, UInt32 id);
        [DllImport("coredll.dll", EntryPoint = "GlobalAddAtomW", SetLastError = true)]
        public static extern UInt32 GlobalAddAtom(String lpString);
        [DllImport("coredll.dll", SetLastError = true)]
        public static extern UInt32 GlobalDeleteAtom(UInt32 nAtom);
        //声明aapi

        [DllImport("coredll.dll")]

        private static extern bool UnregisterFunc1(

        int fsModifiers, //组合键的键值

        int vk //热键键值

        );


        #endregion p/invoke
        // Event for client
        public KeyHookEventHandler HotKeyPress;
        private List<UInt32> HotKeyId;

        private const int WM_HOTKEY = 0x0312;

        public void RegHotKey(String lpString, int Modifier, int key)
        {
            // Register to listen for hot key
            uint id = GlobalAddAtom(lpString);

            UnregisterFunc1(Modifier, key);

            bool ret= RegisterHotKey(this.Hwnd, id, Modifier, key);

            HotKeyId.Add(id);
        }

        public MsgWindow()
        {
            HotKeyId = new List<UInt32>();
        }

        ~MsgWindow()
        {
            foreach (uint k in HotKeyId)
            {
                UnregisterHotKey(this.Hwnd, k);
                GlobalDeleteAtom(k);
            }

        }

        /// <summary>
        /// WndProc
        /// </summary>
        /// <param name="msg"></param>
        protected override void WndProc(ref Message msg)
        {
            switch (msg.Msg)
            {
                case WM_HOTKEY:
                    if (HotKeyPress != null)
                    {
                        KeyData keyData = new KeyData
                        {
                            KeyCode = Win32.HIWORD((uint)msg.LParam),
                            ScanCode = Win32.LOWORD((uint)msg.LParam),
                            TimeStamp = 0
                        };
                        HotKeyPress(OpenNETCF.Win32.WM.KEYUP, keyData);
                    }
                    break;
                default:
                    base.WndProc(ref msg);
                    break;
            }
        }
    }
}
