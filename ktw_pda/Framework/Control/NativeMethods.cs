using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Runtime.InteropServices;

namespace Microsoft.Controls
{
    public delegate IntPtr WndProcDelegate(IntPtr hWnd, uint msg, IntPtr wParam, IntPtr lParam);

    public enum WM : uint
    {
        WM_NULL = 0,

        WM_CREATE = 0x0001,
        WM_PAINT = 0x000F,
        WM_PAINTBACKGROUND = 0x0014,
        WM_LMOUSEDOWN = 0x0201,
        WM_RMOUSEDOWN = 0x0204,
        WM_LMOUSEUP = 0x0202,
        WM_RMOUSEUP = 0x0205,
        WM_MOUSEMOVE = 0x0200,
        WM_CLOSE = 0x0010,
        WM_DESTROY = 0x0002,
        WM_SIZE = 0x0005,
        WM_ENABLE = 0x000A,
        WM_KEYDOWN = 0x0100,
        WM_KEYUP = 0x0101,
        WM_KEYCHAR = 0x0102,
        WM_FOCUS = 0x0007,
        WM_ACTIVATE = 0x0006,
        WM_HELP = 0x0053,
        WM_CTLCOLORSTATIC = 0x0138,
        WM_NCPAINT = 0x0085,
        WM_CTLCOLOREDIT = 0x0133
    }

    public enum WindowStyles : int
    {
        WS_CAPTION = 0x00C00000,     /* WS_BORDER | WS_DLGFRAME  */
        WS_BORDER = 0x00800000,
        WS_DLGFRAME = 0x00400000,
        WS_VSCROLL = 0x00200000,
        WS_HSCROLL = 0x00100000,
        WS_SYSMENU = 0x00080000,
        WS_THICKFRAME = 0x00040000,
        WS_MAXIMIZEBOX = 0x00020000,
        WS_MINIMIZEBOX = 0x00010000,
        WS_SIZEBOX = WS_THICKFRAME,
        WS_CHILD = 0x40000000,
        WS_VISIBLE = 0x10000000,
        WS_DISABLED = 0x08000000
    }

    public static class NativeMethods
    {
        [DllImport("coredll.dll", SetLastError = true)]
        public static extern IntPtr CreateWindowEx(uint dwExStyle, string lpClassName,
            string lpWindowName, uint dwStyle, int x, int y, int nWidth, int nHeight,
            IntPtr hWndParent, IntPtr hMenu, IntPtr hInstance, IntPtr lpParam);

        [DllImport("coredll.dll", SetLastError = true)]
        public static extern int SetWindowLong(IntPtr hWnd, int nIndex, int dwNewLong);

        [DllImport("coredll.dll", SetLastError = true)]
        public static extern int SetWindowLong(IntPtr hWnd, int nIndex, WndProcDelegate newProc);

        [DllImport("coredll.dll", SetLastError = true)]
        public static extern IntPtr GetWindowLong(IntPtr hWnd, int nIndex);

        [DllImport("coredll.dll")]
        public static extern IntPtr CallWindowProc(IntPtr lpPrevWndFunc, IntPtr hWnd, uint Msg, IntPtr wParam, IntPtr lParam);

        [DllImport("coredll.dll", CharSet = CharSet.Auto)]
        public static extern IntPtr DefWindowProc(IntPtr hWnd, int msg, IntPtr wParam, IntPtr lParam);

        [DllImport("coredll.dll", CharSet = CharSet.Auto)]
        public static extern IntPtr GetModuleHandle(string modName);

        [DllImport("coredll.dll", CharSet = CharSet.Auto)]
        public static extern bool DestroyWindow(IntPtr hWnd);

        [DllImport("coredll.dll", CharSet = CharSet.Auto)]
        public static extern int SetScrollInfo(
                        IntPtr hwnd,
                        int fnBar,
                        ref SCROLLINFO lpsi,
                        int fRedraw
                        );

        [DllImport("coredll.dll")]
        public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int X,
           int Y, int cx, int cy, uint uFlags);


        [DllImport("coredll.dll")]
        public static extern int SendMessage(IntPtr hWnd, uint Msg, int wParam, int lParam);

        [DllImport("coredll.dll")]
        public static extern int SendMessage(IntPtr hWnd, uint Msg, int wParam, string lParam);

        public struct SCROLLINFO
        {
            public uint cbSize;
            public uint fMask;
            public int nMin;
            public int nMax;
            public uint nPage;
            public int nPos;
            public int nTrackPos;
        }

        public const int
           WM_NOTIFY = 0x4E,
           NM_CUSTOMDRAW = (-12),
           WS_HSCROLL = 0x100000,
           WS_VSCROLL = 0x200000,
           SIF_RANGE = 0x0001,
           WS_BORDER = 0x00800000,
           SWP_NOSIZE = 0x0001,
           SWP_NOMOVE = 0x0002,
           SWP_NOZORDER = 0x0004,
           SWP_NOREDRAW = 0x0008,
           SWP_NOACTIVATE = 0x0010,
           SWP_FRAMECHANGED = 0x0020,  /* The frame changed: send WM_NCCALCSIZE */
           SWP_SHOWWINDOW = 0x0040,
           SWP_HIDEWINDOW = 0x0080,
           SWP_NOCOPYBITS = 0x0100,
           SWP_NOOWNERZORDER = 0x0200,  /* Don't do owner Z ordering */
           SWP_NOSENDCHANGING = 0x0400,
           CDRF_NOTIFYPOSTPAINT = 0x00000010,
           CDRF_NOTIFYITEMDRAW = 0x00000020,
           CDRF_NOTIFYSUBITEMDRAW = CDRF_NOTIFYITEMDRAW,
           CDDS_PREPAINT = 0x00000001,
           CDDS_POSTPAINT = 0x00000002,
           CDDS_PREERASE = 0x00000003,
           CDDS_POSTERASE = 0x00000004,
           CDRF_NEWFONT = 0x00000002,
           CDDS_ITEM = 0x00010000,
           CDDS_ITEMPREPAINT = (CDDS_ITEM | CDDS_PREPAINT),
           CDDS_ITEMPOSTPAINT = (CDDS_ITEM | CDDS_POSTPAINT),
           CDDS_ITEMPREERASE = (CDDS_ITEM | CDDS_PREERASE),
           CDDS_ITEMPOSTERASE = (CDDS_ITEM | CDDS_POSTERASE),
           CDRF_SKIPDEFAULT = 0x00000004,
           CDRF_DODEFAULT = 0x00000000,
           CDDS_SUBITEM = 0x00020000,
            CDIS_SELECTED = 0x0001,
           CDIS_GRAYED = 0x0002,
           CDIS_DISABLED = 0x0004,
           CDIS_CHECKED = 0x0008,
           CDIS_FOCUS = 0x0010,
           CDIS_DEFAULT = 0x0020,
           CDIS_HOT = 0x0040,
           CDIS_MARKED = 0x0080,
           CDIS_INDETERMINATE = 0x0100,

           TVM_GETITEMRECT = (TV_FIRST + 4),
           TV_FIRST = 0x1100,
           LVM_FIRST = 0x1000,
           TVM_GETITEM = (TV_FIRST + 62),
           TVIF_TEXT = 0x0001,
           TVIF_IMAGE = 0x0002,
           TVIF_PARAM = 0x0004,
           TVIF_STATE = 0x0008,
           TVIF_HANDLE = 0x0010,
           TVIF_SELECTEDIMAGE = 0x0020,
           TVIF_CHILDREN = 0x0040,
           TVM_SETITEMSPACING = (TV_FIRST + 56),
           WM_SETREDRAW = 0x000B,
           LVS_NOSCROLL = 0x2000,
           LVM_REDRAWITEMS = (0x1000 + 21),
           LVM_GETITEMSTATE = (0x1000 + 44),
           LVIS_SELECTED = 0x0002,
           LVIS_FOCUSED = 0x0001,
           LVIS_CUT = 0x0004,
           LVM_GETITEMRECT = (0x1000 + 14),
           LVM_GETSUBITEMRECT = (0x1000 + 56),
           LVIS_DROPHILITED = 0x0008,
           LVIS_OVERLAYMASK = 0x0F00,
           LVIS_STATEIMAGEMASK = 0xF000,
           LVM_SCROLL = (LVM_FIRST + 20);



        public struct RECT
        {
            public int left;
            public int top;
            public int right;
            public int bottom;
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct NMLVCUSTOMDRAW
        {
            public NMCUSTOMDRAW nmcd;
            public int clrText;
            public int clrTextBk;
            public int iSubItem;
            public int dwItemType;
            // Item Custom Draw 
            public int clrFace;
            public int iIconEffect;
            public int iIconPhase;
            public int iPartId;
            public int iStateId;
            // Group Custom Draw 
            public RECT rcText;
            public uint uAlign;
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct NMCUSTOMDRAW
        {
            public NMHDR nmcd;
            public int dwDrawStage;
            public IntPtr hdc;
            public RECT rc;
            public IntPtr dwItemSpec;
            public int uItemState;
            public IntPtr lItemlParam;
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct NMHDR
        {
            public IntPtr hwndFrom;
            public IntPtr idFrom; //This is declared as UINT_PTR in winuser.h  
            public int code;
        }

        public struct TVITEM
        {
            public uint mask;
            public IntPtr hItem;
            public uint state;
            public uint stateMask;
            public IntPtr pszText;
            public int cchTextMax;
            public int iImage;
            public int iSelectedImage;
            public int cChildren;
            public IntPtr lParam;
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct NMTVCUSTOMDRAW
        {
            public NMCUSTOMDRAWINFO nmcd;
            public int clrText;
            public int clrTextBk;
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct NMCUSTOMDRAWINFO
        {
            public NMHDR hdr;
            public int dwDrawStage;
            public IntPtr hdc;
            public RECT rc;
            public int dwItemSpec;  // this is control specific, but it's how to specify an item.  valid only with CDDS_ITEM bit set 
            public int uItemState;
            public IntPtr lItemlParam;
        }
    }
}