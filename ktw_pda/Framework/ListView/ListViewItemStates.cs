using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Microsoft.Controls
{
    // Summary: 
    //     Defines constants that represent the possible states of a System.Windows.Forms.ListViewItem. 
    [Flags]
    public enum ListViewItemStates
    {
        // Summary: 
        //     The item is selected. 
        Selected = 1,
        // 
        // Summary: 
        //     The item is disabled. 
        Grayed = 2,
        // 
        // Summary: 
        //     The item is checked. 
        Checked = 8,
        // 
        // Summary: 
        //     The item has focus. 
        Focused = 16,
        // 
        // Summary: 
        //     The item is in its default state. 
        Default = 32,
        // 
        // Summary: 
        //     The item is currently under the mouse pointer. 
        Hot = 64,
        // 
        // Summary: 
        //     The item is marked. 
        Marked = 128,
        // 
        // Summary: 
        //     The item is in an indeterminate state. 
        Indeterminate = 256,
        // 
        // Summary: 
        //     The item should indicate a keyboard shortcut. 
        ShowKeyboardCues = 512,
    }
}