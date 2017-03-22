using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using System.Drawing.Imaging;
using System.Globalization;

namespace Framework
{
    public static class FrameworkEx
    {
        /// <summary>
        /// 取String对应的Message
        /// </summary>
        /// <param name="s"></param>
        public static string GetMessage(this object s)
        {
            return AppContext.Instance().GetMessage(s.TrimEx());
        }
        /// <summary>
        /// TrimString扩展
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static string TrimEx(this object s)
        {
            return StringUtil.TrimString(s);
        }
        /// <summary>
        /// 判断是否为数字
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static bool IsDigital(this string s)
        {
            return StringUtil.IsDigital(s);
        }
        /// <summary>
        /// 判断是否为空
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static bool IsEmpty(this object s)
        {
            return StringUtil.IsEmpty(s.TrimEx());
        }
        /// <summary>
        /// 判断控件是否设计模式
        /// </summary>
        /// <param name="c"></param>
        /// <returns></returns>
        public static bool IsDesignMode(this Control c)
        {
            return FormUtil.DesignMode(c);
        }

        /// <summary>
        /// 判断是否日期
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static bool IsDate(this object value, string format)
        {
            string textValue = value.TrimEx();
            if (textValue.Length != format.Length)
            {
                return false;
            }
            DateTime? d = value.ToDate(format);
            if (d == null)
                return false;
            return true;
        }
        /// <summary>
        /// 找到控件的父窗体
        /// </summary>
        /// <param name="ctl"></param>
        /// <returns></returns>
        public static Form FindForm(this Control ctl)
        {
            Control control = ctl;
            do
            {
                control = control.Parent;
            }
            while (!(control is Form));
            return (Form)control;
        }
        /// <summary>
        /// string.Format扩展
        /// </summary>
        /// <param name="s"></param>
        /// <param name="param"></param>
        /// <returns></returns>
        public static String FormatEx(this string s,params object[] param)
        {
            return string.Format(s, param);
        }
        /// <summary>
        /// 取快捷键消息
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static HotKey HotKey(this string s)
        {
            return AppContext.Instance().GetHotKey(s);
        }
        /// <summary>
        /// 清空控件
        /// </summary>
        /// <param name="f"></param>
        /// <param name="clearItems"></param>
        /// <param name="paras"></param>
        public static void ClearControl(this Form f, bool clearItems, params Control[] paras)
        {
            foreach (Control ctl in paras)
            {
                if (ctl is Label)
                    ctl.Text = String.Empty;
                else if (ctl is TextBox)
                {
                    ctl.Text = String.Empty;
                }
                else if (ctl is ComboBox)
                {
                    if (clearItems)
                    {
                        ComboBox cbo = ctl as ComboBox;
                        if (cbo.DataSource == null)
                        {
                            cbo.Items.Clear();
                        }
                        else
                            cbo.DataSource = null;
                    }
                }
                else if (ctl is DateTimePicker)
                {
                    (ctl as DateTimePicker).Value = DateTime.Now;
                }
            }
        }
        /// <summary>
        /// 转到Double型
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static double ToDouble(this object value)
        {
            string textValue = value.TrimEx();
            if (textValue.IsEmpty())
                return 0;
            try
            {
                return double.Parse(textValue);
            }
            catch
            {
                return 0;
            }
        }

        /// <summary>
        /// 显示浮点数(祛除.0)
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static string TrimDouble(this object s)
        {
            string value = s.TrimEx();
            if (value.Contains('.'))
                return value.TrimEnd('0').TrimEnd('.');
            else
                return value;
        }

        /// <summary>
        /// 转到Long型
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static long ToLong(this object value)
        {
            string textValue = value.TrimEx();
            if (textValue.IsEmpty())
                return 0;
            try
            {
                return long.Parse(textValue);
            }
            catch
            {
                return 0;
            }
        }

        /// <summary>
        /// 转到Long型
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static int ToInt(this object value)
        {
            string textValue = value.TrimEx();
            if (textValue.IsEmpty())
                return 0;
            try
            {
                return int.Parse(textValue);
            }
            catch
            {
                return 0;
            }
        }

        /// <summary>
        /// 转日期
        /// </summary>
        /// <param name="value"></param>
        /// <param name="format"></param>
        /// <returns></returns>
        public static DateTime? ToDate(this object value, string format)
        {
            string textValue = value.TrimEx();
            DateTime? ret = null;
            if (textValue.Length != format.Length)
            {
                return null;
            }
            string week;
            week = textValue.Substring(textValue.Length - 2);
            try
            {
                switch (format)
                {
                    case "yyyy-MM-dd":
                    case "yyyyMMdd":
                    case "yy-MM-dd":
                    case "yyMMdd":
                    case "yy-MM":
                    case "yyMM":
                        ret = DateTime.ParseExact(textValue, format, CultureInfo.InvariantCulture);
                        break;
                    case "yyyy-WW":
                    case "YYYY-WW":
                        ret = DateTime.ParseExact((textValue + "0000").Substring(0, 4), "yyyy", CultureInfo.InvariantCulture);
                        ret = ret.Value.AddDays((week.ToLong() - 1) * 7);
                        if (week.ToLong() > 1)
                        {
                            ret = ret.Value.AddDays((double)(ret.Value.DayOfWeek) * (-1));
                        }
                        break;
                    case "yy-WW":
                    case "YY-WW":
                        ret = DateTime.ParseExact((textValue + "00").Substring(0, 2), "yy", CultureInfo.InvariantCulture);
                        ret = ret.Value.AddDays((week.ToLong() - 1) * 7);
                        if (week.ToLong() > 1)
                        {
                            ret = ret.Value.AddDays((double)(ret.Value.DayOfWeek) * (-1));
                        }
                        break;
                }
            }
            catch
            {
                return null;
            }
            return ret;
        }
    }
}
