using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Framework;
using Json;

namespace Framework
{
    /// <summary>
    /// 请求对象
    /// </summary>
    [Serializable]
    public class MobileRequest
    {
        /// <summary>
        /// 画面id
        /// </summary>
        private string pageId;

        public string PageId
        {
            get { return pageId; }
            set { pageId = value; }
        }
        /// <summary>
        /// 出库代码
        /// </summary>
        private long whId;

        public long WhId
        {
            get { return whId; }
            set { whId = value; }
        }
        /// <summary>
        /// 用户ID
        /// </summary>
        private long userId;

        public long UserId
        {
            get { return userId; }
            set { userId = value; }
        }

        /// <summary>
        /// 货主ID
        /// </summary>
        private long ownerId;

        public long OwnerId
        {
            get { return ownerId; }
            set { ownerId = value; }
        }

        //参数

        private Dictionary<string, object> parameters = new Dictionary<string, object>();

        public Dictionary<string, object> Parameters
        {
            get { return parameters; }
            set { parameters = value; }
        }

    }
}
