using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Microsoft.WindowsCE.Forms;
using System.Runtime.InteropServices;
using System.Drawing;
using System.Windows.Forms;
using System.Diagnostics;
using System.Globalization;

namespace Microsoft.Controls
{

    public delegate void DrawListViewItemEventHandler(object sender, DrawListViewItemEventArgs e);

    public delegate void DrawListViewSubItemEventHandler(object sender, DrawListViewSubItemEventArgs e);

    public class ListViewOwnerDrawn : ListView, IMessage
    {

        #region fields

        private bool ownerDraw;
        private MessageControl messageControl;
        private BorderStyle borderStyle;

        public event DrawListViewItemEventHandler DrawItem;
        public event DrawListViewSubItemEventHandler DrawSubItem;
        public new event EventHandler Resize;

        private const int LVS_OWNERDRAWFIXED = 0x0400;
        private const int WM_PAINT = 0x000F;
        private const int WS_BORDER = 0x00800000;
        private const int WS_VSCROLL = 0x00200000;
        private const int WM_SIZE = 0x0005;
        private const int WM_WINDOWPOSCHANGED = 0x0047;

        #endregion

        #region constructor

        public ListViewOwnerDrawn()
        {
            this.borderStyle = BorderStyle.FixedSingle;
            this.ParentChanged += new EventHandler(ListViewSubclass_ParentChanged);
        }

        #endregion

        private void OnResize()
        {
            if (this.Resize != null)
            {
                this.Resize(this, EventArgs.Empty);
            }
        }

        #region event handlers

        void ListViewSubclass_ParentChanged(object sender, EventArgs e)
        {
            if (this.Parent != null)
            {
                if (!this.IsDesignTime())
                {
                    this.EnableParentSubclassing();
                }
            }
        }

        #endregion

        public void ScrollDown(int pixels)
        {
            NativeMethods.SendMessage(this.Handle, NativeMethods.LVM_SCROLL, 0, pixels);
        }

        #region properties

        public bool OwnerDraw
        {
            get
            {
                return this.ownerDraw;
            }
            set
            {
                this.ownerDraw = value;
            }
        }

        public BorderStyle BorderStyle
        {
            get
            {
                return this.borderStyle;
            }
            set
            {
                if (this.borderStyle != value)
                {
                    this.borderStyle = value;

                    if (!this.IsDesignTime())
                    {
                        int currentStyle = (int)NativeMethods.GetWindowLong(this.Handle, (-16));

                        if ((value == BorderStyle.FixedSingle) | (value == BorderStyle.Fixed3D))
                        {
                            currentStyle |= WS_BORDER;
                        }
                        else
                        {
                            currentStyle = (int)NativeMethods.GetWindowLong(this.Handle, (-16));
                            currentStyle &= ~WS_BORDER;

                        }
                        NativeMethods.SetWindowLong(this.Handle, (-16), currentStyle);
                        SetWindowPos(this.Handle, IntPtr.Zero, 0, 0, this.Width, this.Height, SWP_NOSIZE | SWP_NOACTIVATE | SWP_NOMOVE | SWP_FRAMECHANGED);
                    }

                }
            }
        }

        public bool ShowBorder
        {
            set
            {
                if (!this.IsDesignTime())
                {
                    int currentStyle = (int)NativeMethods.GetWindowLong(this.Handle, (-16));

                    if (value)
                    {
                        currentStyle |= WS_BORDER;
                    }
                    else
                    {
                        currentStyle = (int)NativeMethods.GetWindowLong(this.Handle, (-16));
                        currentStyle &= ~WS_BORDER;

                    }
                    NativeMethods.SetWindowLong(this.Handle, (-16), currentStyle);
                    SetWindowPos(this.Handle, IntPtr.Zero, 0, 0, this.Width, this.Height, SWP_NOSIZE | SWP_NOACTIVATE | SWP_NOMOVE | SWP_FRAMECHANGED);
                }
            }

        }

        public bool IsScollBarVisibile
        {
            get
            {
                int wndStyle = (int)NativeMethods.GetWindowLong(this.Handle, (-16));
                if ((wndStyle & NativeMethods.WS_VSCROLL) != 0)
                {
                    return true;
                }
                return false;
            }
        }

        public bool Scrollable
        {
            set
            {
                if (!this.IsDesignTime())
                {
                    //int currentStyle = (int)NativeMethods.GetWindowLong(this.Handle, (-16)); 

                    //if (value) 
                    //{ 
                    //    currentStyle |= NativeMethods.LVS_NOSCROLL; 
                    //} 
                    //else 
                    //{                         
                    //    currentStyle &= ~NativeMethods.LVS_NOSCROLL; 
                    //} 
                    //NativeMethods.SetWindowLong(this.Handle, (-16), currentStyle); 
                    //SetWindowPos(this.Handle, IntPtr.Zero, 0, 0, this.Width, this.Height, SWP_NOSIZE | SWP_NOACTIVATE | SWP_NOMOVE | SWP_FRAMECHANGED | SWP_SHOWWINDOW); 
                    if (!value)
                    {
                        NativeMethods.SCROLLINFO scrollInfo = new NativeMethods.SCROLLINFO();
                        scrollInfo.cbSize = (uint)Marshal.SizeOf(scrollInfo);
                        scrollInfo.fMask = NativeMethods.SIF_RANGE;
                        NativeMethods.SetScrollInfo(this.Handle, 0, ref scrollInfo, 1);
                    }
                }
            }

        }

        #endregion

        #region IMessage Members

        /// <summary> 
        /// Implementation of the WndPoc callback 
        /// </summary> 
        /// <param name="m">Message</param> 
        public void WndProc(ref Microsoft.WindowsCE.Forms.Message m)
        {
            NativeMethods.NMHDR hmndr;

            // Check for WM_NOTIFY messages 
            if (m.Msg == NativeMethods.WM_NOTIFY)
            {
                hmndr = (NativeMethods.NMHDR)Marshal.PtrToStructure(m.LParam, typeof(NativeMethods.NMHDR));
                // Is this sent from our ListView control? 
                if (hmndr.hwndFrom == this.Handle)
                {
                    if (hmndr.code == NativeMethods.NM_CUSTOMDRAW)
                    {
                        this.CustomDraw(ref m);
                    }
                    else
                    {
                        this.DefaultWinProc(ref m);
                    }
                }

            }
            else
            {
                this.DefaultWinProc(ref m);
            }

        }

        #endregion

        #region helper methods

        private void CustomDraw(ref Message m)
        {
            bool itemDrawDefault = false;

            try
            {
                NativeMethods.NMLVCUSTOMDRAW nmcd = (NativeMethods.NMLVCUSTOMDRAW)Marshal.PtrToStructure(m.LParam, typeof(NativeMethods.NMLVCUSTOMDRAW));
                // Find out which stage we're drawing 
                switch (nmcd.nmcd.dwDrawStage)
                {
                    case NativeMethods.CDDS_PREPAINT:
                        if (OwnerDraw)
                        {
                            m.Result = (IntPtr)(NativeMethods.CDRF_NOTIFYITEMDRAW);
                            return;
                        }
                        // We want custom draw for this paint cycle 
                        m.Result = (IntPtr)(NativeMethods.CDRF_NOTIFYSUBITEMDRAW | NativeMethods.CDRF_NEWFONT);
                        return;

                    case NativeMethods.CDDS_ITEMPREPAINT:

                        int itemIndex = (int)nmcd.nmcd.dwItemSpec;
                        Rectangle itemBounds = GetItemRectOrEmpty(itemIndex);

                        if (!ClientRectangle.IntersectsWith(itemBounds))
                        {
                            // we don't need to bother painting this one. 
                            return;
                        }

                        // If OwnerDraw is true, fire the onDrawItem event.  
                        if (OwnerDraw)
                        {

                            Graphics g = Graphics.FromHdc(nmcd.nmcd.hdc);

                            DrawListViewItemEventArgs e = null;
                            try
                            {
                                e = new DrawListViewItemEventArgs(g,
                                       Items[(int)nmcd.nmcd.dwItemSpec],
                                       itemBounds,
                                       (int)nmcd.nmcd.dwItemSpec,
                                       (ListViewItemStates)(nmcd.nmcd.uItemState));

                                OnDrawItem(e);
                            }
                            finally
                            {
                                //g.Dispose(); 
                            }

                            itemDrawDefault = e.DrawDefault;

                            // For the Details view, we send a SKIPDEFAULT when we get a sub-item drawing notification. 
                            // For other view styles, we do it here. 
                            if (this.View == View.Details)
                            {
                                m.Result = (IntPtr)(NativeMethods.CDRF_NOTIFYSUBITEMDRAW);
                            }
                            else
                            {
                                if (!e.DrawDefault)
                                {
                                    m.Result = (IntPtr)(NativeMethods.CDRF_SKIPDEFAULT);
                                }
                            }

                            if (!e.DrawDefault)
                            {
                                return;   // skip our regular drawing code  
                            }
                        }

                        if (this.View == View.Details)
                        {
                            m.Result = (IntPtr)(NativeMethods.CDRF_NOTIFYSUBITEMDRAW | NativeMethods.CDRF_NEWFONT);
                        }

                        goto case (NativeMethods.CDDS_SUBITEM | NativeMethods.CDDS_ITEMPREPAINT);

                    case (NativeMethods.CDDS_SUBITEM | NativeMethods.CDDS_ITEMPREPAINT):

                        itemIndex = (int)nmcd.nmcd.dwItemSpec;
                        itemBounds = GetItemRectOrEmpty(itemIndex);

                        if (!ClientRectangle.IntersectsWith(itemBounds))
                        {
                            // we don't need to bother painting this one. 
                            return;
                        }

                        //if (nmcd.iSubItem == 0) 
                        //{ 
                        //    m.Result = (IntPtr)(NativeMethods.CDRF_SKIPDEFAULT); 
                        //    return; // skip our custom draw code  
                        //} 

                        // If OwnerDraw is true, fire the onDrawSubItem event.  
                        if (OwnerDraw && !itemDrawDefault)
                        {

                            Graphics g = Graphics.FromHdc(nmcd.nmcd.hdc);
                            DrawListViewSubItemEventArgs e = null;

                            // by default, we want to skip the customDrawCode 
                            bool skipCustomDrawCode = true;
                            try
                            {

                                //The ListView will send notifications for every column, even if no 
                                //corresponding subitem exists for a particular item. We shouldn't 
                                //fire events in such cases. 
                                if (nmcd.iSubItem < Items[itemIndex].SubItems.Count)
                                {
                                    Rectangle subItemBounds = GetSubItemRect(itemIndex, nmcd.iSubItem);

                                    // For the first sub-item, the rectangle corresponds to the whole item. 
                                    // We need to handle this case separately.  
                                    if (nmcd.iSubItem == 0 && Items[itemIndex].SubItems.Count > 1)
                                    {
                                        // Use the width for the first column header. 
                                        subItemBounds.Width = this.Columns[0].Width;
                                    }

                                    if (this.ClientRectangle.IntersectsWith(subItemBounds))
                                    {
                                        e = new DrawListViewSubItemEventArgs(g,
                                                  subItemBounds,
                                                  Items[itemIndex],
                                                  Items[itemIndex].SubItems[nmcd.iSubItem],
                                                  itemIndex,
                                                  nmcd.iSubItem,
                                                  this.Columns[nmcd.iSubItem],
                                                  (ListViewItemStates)(nmcd.nmcd.uItemState));
                                        OnDrawSubItem(e);

                                        // the customer still wants to draw the default. 
                                        // Don't skip the custom draw code then 
                                        skipCustomDrawCode = !e.DrawDefault;
                                    }
                                }
                            }
                            finally
                            {
                                g.Dispose();
                            }

                            if (skipCustomDrawCode)
                            {
                                m.Result = (IntPtr)(NativeMethods.CDRF_SKIPDEFAULT);
                                return; // skip our custom draw code  
                            }
                        }

                        // get the node 
                        ListViewItem item = Items[(int)(nmcd.nmcd.dwItemSpec)];

                        int state = nmcd.nmcd.uItemState;
                        int subitem = ((nmcd.nmcd.dwDrawStage & NativeMethods.CDDS_SUBITEM) != 0) ? nmcd.iSubItem : 0;

                        return;

                    default:
                        m.Result = (IntPtr)NativeMethods.CDRF_DODEFAULT;
                        return;
                }
            }
            catch (Exception e)
            {
                Debug.Fail("Exception occurred attempting to setup custom draw. Disabling custom draw for this control", e.ToString());
                m.Result = (IntPtr)NativeMethods.CDRF_DODEFAULT;
            }
        }

        internal int GetItemState(int index)
        {
            return GetItemState(index, NativeMethods.LVIS_FOCUSED | NativeMethods.LVIS_SELECTED | NativeMethods.LVIS_CUT |
                                NativeMethods.LVIS_DROPHILITED | NativeMethods.LVIS_OVERLAYMASK |
                                NativeMethods.LVIS_STATEIMAGEMASK);
        }

        internal int GetItemState(int index, int mask)
        {
            if (index < 0)
            {
                throw new ArgumentOutOfRangeException("index", (index).ToString(CultureInfo.CurrentCulture));
            }


            return (int)SendMessage(this.Handle, NativeMethods.LVM_GETITEMSTATE, index, mask);
        }

        internal Rectangle GetSubItemRect(int itemIndex, int subItemIndex)
        {
            return GetSubItemRect(itemIndex, subItemIndex, 0);
        }

        internal Rectangle GetSubItemRect(int itemIndex, int subItemIndex, ItemBoundsPortion portion)
        {
            // it seems that getting the rectangle for a sub item only works for list view which are in Details view  
            if (this.View != View.Details)
            {
                return Rectangle.Empty;
            }
            if (itemIndex < 0 || itemIndex >= this.Items.Count)
            {
                throw new ArgumentOutOfRangeException("itemIndex", (itemIndex).ToString(CultureInfo.CurrentCulture));
            }
            int subItemCount = Items[itemIndex].SubItems.Count;

            if (subItemIndex < 0 || subItemIndex >= subItemCount)
            {
                throw new ArgumentOutOfRangeException("subItemIndex", (subItemIndex).ToString(CultureInfo.CurrentCulture));
            }

            if (this.Columns.Count == 0)
            {
                return Rectangle.Empty;
            }


            NativeMethods.RECT itemrect = new NativeMethods.RECT();
            itemrect.left = (int)portion;
            itemrect.top = subItemIndex;
            if ((int)SendMessage(this.Handle, NativeMethods.LVM_GETSUBITEMRECT, itemIndex, ref itemrect) == 0)
                throw new ArgumentException("itemIndex",
                                                          (itemIndex).ToString(CultureInfo.CurrentCulture));

            //Rectangle result = new Rectangle(left, itemrect.top, this.Columns[subItemIndex + 1].Width, itemrect.bottom - itemrect.top); 

            Rectangle result = Rectangle.FromLTRB(itemrect.left, itemrect.top, itemrect.right, itemrect.bottom);

            // Rectangle result = this.RectangleToScreen(new Rectangle(itemrect.left, itemrect.top, itemrect.right - itemrect.left, itemrect.bottom - itemrect.top)); 

            return result;
        }

        private Rectangle GetItemRectOrEmpty(int index)
        {
            if (index < 0 || index >= this.Items.Count)
                return Rectangle.Empty;

            if (this.View == View.Details && this.Columns.Count == 0)
            {
                return Rectangle.Empty;
            }


            NativeMethods.RECT itemrect = new NativeMethods.RECT();
            itemrect.left = 0;
            if ((int)SendMessage(this.Handle, NativeMethods.LVM_GETITEMRECT, index, ref itemrect) == 0)
                return Rectangle.Empty;

            Rectangle result = Rectangle.FromLTRB(itemrect.left, itemrect.top, itemrect.right, itemrect.bottom);
            //result.Width = this.Columns[0].Width; 
            return result;
        }

        #endregion

        #region virtual methods

        protected virtual void OnDrawItem(DrawListViewItemEventArgs e)
        {
            if (this.DrawItem != null)
            {
                DrawItem(this, e);
            }
        }


        protected virtual void OnDrawSubItem(DrawListViewSubItemEventArgs e)
        {
            if (DrawSubItem != null)
            {
                DrawSubItem(this, e);
            }
        }

        #endregion

        #region P/Invokes

        [DllImport("coredll")]
        static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);
        [DllImport("coredll")]
        static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, ref NativeMethods.RECT lParam);

        [DllImport("coredll.dll")]
        static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int X,
           int Y, int cx, int cy, uint uFlags);

        const UInt32 SWP_NOSIZE = 0x0001;
        const UInt32 SWP_NOMOVE = 0x0002;
        const UInt32 SWP_NOZORDER = 0x0004;
        const UInt32 SWP_NOREDRAW = 0x0008;
        const UInt32 SWP_NOACTIVATE = 0x0010;
        const UInt32 SWP_FRAMECHANGED = 0x0020;  /* The frame changed: send WM_NCCALCSIZE */
        const UInt32 SWP_SHOWWINDOW = 0x0040;
        const UInt32 SWP_HIDEWINDOW = 0x0080;
        const UInt32 SWP_NOCOPYBITS = 0x0100;
        const UInt32 SWP_NOOWNERZORDER = 0x0200;  /* Don't do owner Z ordering */
        const UInt32 SWP_NOSENDCHANGING = 0x0400;  /* Don't send WM_WINDOWPOSCHANGING */

        #endregion

    }

    #region ItemBoundsPortion

    public enum ItemBoundsPortion
    {
        // Summary: 
        //     The bounding rectangle of the entire item, including the icon, the item text, 
        //     and the subitem text (if displayed), should be retrieved. 
        Entire = 0,
        // 
        // Summary: 
        //     The bounding rectangle of the icon or small icon should be retrieved. 
        Icon = 1,
        // 
        // Summary: 
        //     The bounding rectangle of the item text should be retrieved. 
        Label = 2,
        // 
        // Summary: 
        //     The bounding rectangle of the icon or small icon and the item text should 
        //     be retrieved. In all views except the details view of the System.Windows.Forms.ListView, 
        //     this value specifies the same bounding rectangle as the Entire value. In 
        //     details view, this value specifies the bounding rectangle specified by the 
        //     Entire value without the subitems. If the System.Windows.Forms.ListView.CheckBoxes 
        //     property is set to true, this property does not include the area of the check 
        //     boxes in its bounding rectangle. To include the entire item, including the 
        //     check boxes, use the Entire value when calling the System.Windows.Forms.ListView.GetItemRect(System.Int32) 
        //     method. 
        ItemOnly = 3,
    }

    #endregion

}