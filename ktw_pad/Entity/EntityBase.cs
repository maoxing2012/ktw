using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;


namespace Entity
{
    /// <summary>
    /// 实体类基类
    /// </summary>
    public class EntityBase : INotifyPropertyChanged
    {
        // Events
        public event PropertyChangedEventHandler PropertyChanged;

        // Methods
        /// <summary>
        /// 浅拷贝
        /// </summary>
        /// <returns></returns>
        public object Clone()
        {
            return this.MemberwiseClone();
        }

        /// <summary>
        /// 触发属性变更事件
        /// </summary>
        /// <param name="propertyName"></param>
        protected virtual void OnPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }
        /// <summary>
        /// 设置值
        /// </summary>
        /// <typeparam name="T">类型</typeparam>
        /// <param name="field">变量实例</param>
        /// <param name="value">要设置的值</param>
        /// <param name="propertyName">属性名</param>
        /// <returns></returns>
        protected object SetField<T>(ref T field, T value, string propertyName)
        {
            if (EqualityComparer<T>.Default.Equals(field, value))
            {
                return false;
            }
            field = value;
            this.OnPropertyChanged(propertyName);
            return true;
        }

    }

}
