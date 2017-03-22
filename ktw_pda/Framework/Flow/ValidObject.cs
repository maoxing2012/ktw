using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace Framework
{
    /// <summary>
    /// 检验对象
    /// </summary>
    public class ValidObject
    {
        //待检验对象
        public Control ValidControl { get; set; }
        /// <summary>
        /// 单个校验或者多个校验
        /// </summary>
        public int ValidType { get; set; }
        /// <summary>
        /// 构造函数
        /// </summary>
        /// <param name="control"></param>
        public ValidObject(Control control)
        {
            this.ValidControl = control;
        }

        /// <summary>
        /// 数据校验
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="control"></param>
        /// <returns></returns>
        public int Valid<T>(T control)
        {
            return Valid(control);
        }

        /// <summary>
        /// 检验对象
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        public virtual int Valid(TextEx control)
        {
            //必须输入
            if (control.MustInput)
            {
                if (StringUtil.IsEmpty(control.Text))
                {
                    //控件未输入
                    if (1 == ValidType)
                    {
                        //全局校验必须通过才可以
                        control.SelectAll();
                        control.Focus();
                    }
                    //单个校验仅仅提示消息,全局校验也提示
                    //control.BusinessForm.Notify(string.Format(Messages.MsgMustInput, Messages.ResourceManager.GetString("Field" + control.Field.ToLower())));
                }
            }
            return 0;
        }
    }
}
