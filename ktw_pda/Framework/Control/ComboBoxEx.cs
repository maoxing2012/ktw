using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Runtime.CompilerServices;
using System.Diagnostics;

namespace Framework
{
    /// <summary>
    /// 组合框
    /// </summary>
    public partial class ComboBoxEx : OpenNETCF.Windows.Forms.ComboBox2
    {
        
        [AccessedThroughProperty("_text")]
        private TextBox __text;
        private bool _hideTip=true;
        private Control _oldParent;
        private string _tip;

        public ComboBoxEx()
            : base()
        {
            base.GotFocus += new EventHandler(this.PHCombo_GotFocus);
            base.LostFocus += new EventHandler(this.PHCombo_LostFocus);
            base.ParentChanged += new EventHandler(this.PHCombo_ParentChanged);
            if (!this.IsDesignMode())
            {
                WndProcHooker.HookWndProc(this, new WndProcHooker.WndProcCallback(this.WM_Handler), Win32.WM_KEYUP);
                WndProcHooker.HookWndProc(this, new WndProcHooker.WndProcCallback(this.WM_Handler), Win32.WM_KEYDOWN);
            }
        }

        /// <summary>
        /// 数据源变更
        /// </summary>
        /// <param name="e"></param>
        protected override void OnDataSourceChanged(EventArgs e)
        {
            base.OnDataSourceChanged(e);
            if ((string.IsNullOrEmpty(this.Text) && !string.IsNullOrEmpty(this.Tip)) && (this._text != null))
            {
                this._text.Visible = true;
            }
        }
        /// <summary>
        /// 获得焦点
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PHCombo_GotFocus(object sender, EventArgs e)
        {
            try
            {
                if (this._text != null)
                {
                    this._text.Visible = false;
                }
                this.DropDown = true;
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 失去焦点
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PHCombo_LostFocus(object sender, EventArgs e)
        {
            try
            {
                if (((!this.IsDesignMode() && ((this.Items.Count == 0) || string.IsNullOrEmpty(this.Text))) && !string.IsNullOrEmpty(this.Tip)) && (this._text != null))
                {
                    this._text.Visible = true;
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 父控件变更时添加提示控件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PHCombo_ParentChanged(object sender, EventArgs e)
        {
            try
            {
                if (this.Parent == null)
                {
                    if ((this._oldParent != null) && (this._text != null))
                    {
                        this._oldParent.Controls.Remove(this._text);
                    }
                }
                else if (!string.IsNullOrEmpty(this.Tip) && !this.IsDesignMode())
                {
                    this._text = new TextBox();
                    this._text.Visible = false;
                    this._oldParent = this.Parent;
                    this.Parent.Controls.Add(this._text);
                    this.ShowTip();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 设置提示控件相关属性
        /// </summary>
        private void ShowTip()
        {
            this._text.TabStop = false;
            this._text.BorderStyle = BorderStyle.None;
            this._text.Text = this.Tip;
            this._text.ForeColor = Color.Gray;
            this._text.BackColor = this.BackColor;
            Size size = new Size(this.Width - 0x16, this.Height - 4);
            this._text.Size = size;
            Point point3 = new Point(this.Location.X + 4, this.Location.Y + 2);
            this._text.Location = point3;
            this._text.Visible = !this.HideTip;
            this._text.BringToFront();
            this.SendToBack();
        }
        /// <summary>
        /// 提示文本获取焦点时，将焦点转交给控件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Text_GotFocus(object sender, EventArgs e)
        {
            try
            {
                this._text.Visible = false;
                this.Focus();
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 下拉时控制
        /// </summary>
        /// <param name="hwnd"></param>
        /// <param name="msg"></param>
        /// <param name="wparam"></param>
        /// <param name="lparam"></param>
        /// <param name="handled"></param>
        /// <returns></returns>
        private int WM_Handler(IntPtr hwnd, uint msg, uint wparam, int lparam, ref bool handled)
        {
            if (this.DroppedDown)
            {
                //下拉框时
                if(wparam == uint.Parse(AppContext.Instance().GetHotKey("esc").Value))
                {
                    //取消
                     this.DropDown = false;
                     handled = true;
                }

                //if ((msg == Win32.WM_KEYUP || msg == Win32.WM_KEYDOWN) && this.IsDesignMode())
                //{
                //    //KeyUp
                //    if (wparam == (uint)Keys.Up)
                //    {
                //        if (this.SelectedIndex > 0)
                //        {
                //            if (msg == Win32.WM_KEYUP)
                //                this.SelectedIndex -= 1;
                //            handled = true;
                //        }
                //    }
                //    else if (wparam == (uint)Keys.Down)
                //    {
                //        if (this.SelectedIndex < this.Items.Count - 1)
                //        {
                //            if (msg == Win32.WM_KEYUP)
                //                this.SelectedIndex += 1;
                //            handled = true;
                //        }
                //    }
                //}
            }
            return 0;
        }


        // Properties
        private TextBox _text
        {
            [DebuggerNonUserCode]
            get
            {
                return this.__text;
            }
            [MethodImpl(MethodImplOptions.Synchronized), DebuggerNonUserCode]
            set
            {
                EventHandler handler = new EventHandler(this.Text_GotFocus);
                if (this.__text != null)
                {
                    this.__text.GotFocus -= handler;
                }
                this.__text = value;
                if (this.__text != null)
                {
                    this.__text.GotFocus += handler;
                }
            }
        }
        /// <summary>
        /// 隐藏提示
        /// </summary>
        public bool HideTip
        {
            get
            {
                return this._hideTip;
            }
            set
            {
                this._hideTip = value;
                if (this._text != null)
                {
                    this._text.Visible = !this.HideTip;
                }
            }
        }

        /// <summary>
        /// 提示信息
        /// </summary>
        public string Tip
        {
            get
            {
                return this._tip;
            }
            set
            {
                this._tip = value;
            }
        }
    }


}
