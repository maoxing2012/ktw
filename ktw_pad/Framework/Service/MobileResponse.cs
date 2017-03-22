using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Json;

namespace Framework
{
    /// <summary>
    /// 返回信息
    /// </summary>
    [Serializable]
    public class MobileResponse<T>
    {
        // 消息类型
        // M：消息Message，“处理成功。”
        // M1：重要消息Message，需要弹出框确认。“密码已经更新！”
        // E：报错Error，“超过库存可用量，无法执行！”
        // W：警告消息Warning，“收货完成，有待质检商品，请注意及时处理！”
        // C:确认信息Confirm，“本批次的生产日期早于库存的生产日期，确定要收货？”
        private string severityMsgType = "M";

        // 跳转的画面id
        private string targetPageId = string.Empty;

        // 消息内容
        private string severityMsg = string.Empty;

        // 返回值
        private T results = default(T);

        /// <summary>
        /// 消息类型
        /// </summary>
        public string SeverityMsgType
        {
            get
            {
                return severityMsgType;
            }
            set
            {
                severityMsgType = value;
            }
        }
        /// <summary>
        /// 跳转的画面id
        /// </summary>
        public string TargetPageId
        {
            get
            {
                return targetPageId;
            }
            set
            {
                targetPageId = value;
            }
        }
        /// <summary>
        /// 消息内容
        /// </summary>
        public string SeverityMsg
        {
            get
            {
                return severityMsg;
            }
            set
            {
                severityMsg = value;
            }
        }
        /// <summary>
        /// 返回值
        /// </summary>
        public T Results
        {
            get
            {
                return results;
            }
            set
            {
                results = value;
            }
        }
    }
}
