using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;

namespace Entity
{
    /// <summary>
    /// 用户信息
    /// </summary>
    [Serializable]
    public class UserObj
    {
        public UserObj(long id, string loginName, string userName)
        {
            this.UserId = id;
            this.UserName = userName;
            this.LoginName = loginName;
            
        }

        /// <summary>
        /// 用户ID
        /// </summary>
        public long UserId { get; set; }
        /// <summary>
        /// 用户名
        /// </summary>
        public string UserName { get; set; }
        /// <summary>
        /// 登录名
        /// </summary>
        public string LoginName { get; set; }
        /// <summary>
        /// 登录时间
        /// </summary>
        public DateTime LoginTime { get; set; }
    }
}
