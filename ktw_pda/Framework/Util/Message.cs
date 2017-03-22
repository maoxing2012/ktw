using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace Framework
{
    /// <summary>
    /// Msgbox封装
    /// </summary>
    public class Message
    {
        public static DialogResult Ask(string msg)
        {
            Cursor.Current = Cursors.Default;
            return MessageBox.Show(msg, "提示", MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1);
        }
        public static DialogResult Ask(string msg,MessageBoxDefaultButton btn)
        {
            Cursor.Current = Cursors.Default;
            return MessageBox.Show(msg, "提示", MessageBoxButtons.YesNo, MessageBoxIcon.Question, btn);
        }

        public static DialogResult Err(string msg)
        {
            Cursor.Current = Cursors.Default;
            return MessageBox.Show(msg, "提示", MessageBoxButtons.OK, MessageBoxIcon.Hand, MessageBoxDefaultButton.Button1);
        }

        public static DialogResult Warn(string msg)
        {
            Cursor.Current = Cursors.Default;
            return MessageBox.Show(msg, "提示", MessageBoxButtons.OK, MessageBoxIcon.Exclamation, MessageBoxDefaultButton.Button1);
        }

        public static DialogResult Info(string msg)
        {
            Cursor.Current = Cursors.Default;
            return MessageBox.Show(msg, "提示", MessageBoxButtons.OK, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button1);
        }

    }
}
