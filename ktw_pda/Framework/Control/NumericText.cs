using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using System.Text.RegularExpressions;

namespace Framework
{
    /// <summary>
    /// 数字控件
    /// </summary>
    public partial class NumericText : TextEx
    {
        public NumericText()
        {
            InitializeComponent();
            base.KeyPress += new KeyPressEventHandler(this.PHNumericText_KeyPress);
            base.LostFocus += new EventHandler(this.PHNumericText_LostFocus);
            base.GotFocus += new EventHandler(this.PHNumericText_GotFocus);
            this.digitalFormat = string.Empty;
            this.regexKingaku = new Regex(@"^[-?/d*/./d*|\d*]+$");
            this.ErrValue = ErrValueType.NoErr;
            this._canNegative = false;
        }

        private bool _canNegative;
        private string digitalFormat;
        public ErrValueType ErrValue;
        private Regex regexKingaku;


        /// <summary>
        /// 数字判断
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        protected override bool CheckValue(string value)
        {
            if(string.IsNullOrEmpty(value))
                return true;
            
            if (!base.CheckValue(value))
            {
                return false;
            }

            if (!string.IsNullOrEmpty(value))
            {
                if (!this.regexKingaku.IsMatch(value))
                {
                    return false;
                }
                if (!this.CanNegative && (value.IndexOf("-") >= 0))
                {
                    return false;
                }
                if ((value.IndexOf("-") > 0) || (value.LastIndexOf("-") != value.IndexOf("-")))
                {
                    return false;
                }
                if ((value.IndexOf(".") == 0) || (value.LastIndexOf(".") != value.IndexOf(".")))
                {
                    return false;
                }
                if ((value.IndexOf("-") == 0) && (value.IndexOf(".") == 1))
                {
                    return false;
                }
            }
            return true;
        }
        /// <summary>
        /// 格式化输出
        /// </summary>
        private void FormatEdit()
        {
            try
            {
                if (!StringUtil.IsEmpty(this.digitalFormat) && !StringUtil.IsEmpty(this.Text))
                {
                    this.Text = Decimal.Parse(this.Text).ToString(digitalFormat);
                }
            }
            catch
            {
                this.ErrValue = ErrValueType.FormatErr;
            }
        }

        /// <summary>
        /// 获得焦点事件处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PHNumericText_GotFocus(object sender, EventArgs e)
        {
            this.Text = this.Text.Replace(",", "");
            this.SelectAll();
        }

        /// <summary>
        /// 数字按键处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PHNumericText_KeyPress(object sender, KeyPressEventArgs e)
        {
            try
            {
                string inputString = e.KeyChar.ToString();
                if (!char.IsControl(e.KeyChar) && !StringUtil.IsDigital(inputString))
                {
                    string str2 = inputString;
                    if (str2 == "-")
                    {
                        if ((this.SelectionStart != 0) | (this.Text.IndexOf("-") > -1))
                        {
                            e.Handled = true;
                        }
                    }
                    else if (str2 == ".")
                    {
                        if (((this.Text.Trim().IndexOf(".") != -1) | (this.Text.Trim() == string.Empty)) | (this.Text.Trim() == "-"))
                        {
                            e.Handled = true;
                        }
                    }
                    else
                    {
                        e.Handled = true;
                    }
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }
        /// <summary>
        /// 格式化处理
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PHNumericText_LostFocus(object sender, EventArgs e)
        {
            try
            {
                this.Text = this.Text.Trim();
                if (StringUtil.TrimString(this.Text) == "-")
                {
                    this.Text = "";
                }
                if (!StringUtil.IsEmpty(this.Text) && !this.CheckValue(this.Text))
                {
                    this.ErrValue = ErrValueType.CheckErr;
                }
                else
                {
                    this.ErrValue = ErrValueType.NoErr;
                    this.FormatEdit();
                }
            }
            catch (Exception ex)
            {
                ExceptionPolicy.HandleException(ex);
            }
        }

        /// <summary>
        /// 是否可为负数，默认不能
        /// </summary>
        public bool CanNegative
        {
            get
            {
                return this._canNegative;
            }
            set
            {
                this._canNegative = value;
            }
        }
        /// <summary>
        /// 数字格式
        /// </summary>
        public string Format
        {
            get
            {
                return this.digitalFormat;
            }
            set
            {
                this.digitalFormat = value.Trim();
            }
        }
        /// <summary>
        /// 数值值
        /// </summary>
        public string Value
        {
            get
            {
                return this.Text;
            }
            set
            {
                if (!StringUtil.IsEmpty(value) && !this.CheckValue(value))
                {
                    this.ErrValue = ErrValueType.CheckErr;
                    this.Text = value.Trim();
                }
                else
                {
                    this.Text = value.Trim();
                    this.FormatEdit();
                }
            }
        }

        // Nested Types
        public enum ErrValueType
        {
            NoErr,
            FormatErr,
            CheckErr
        }

    }

}
