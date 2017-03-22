using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.WindowsCE.Forms;


namespace Microsoft.Controls
{
    public interface IMessage
    {
        void WndProc(ref Message m);
    }
}