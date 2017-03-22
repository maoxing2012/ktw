using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.IO;
using System.Reflection;

namespace Framework
{
    /// <summary>
    /// 配置访问类
    /// </summary>
    public class Config
    {
        private XmlDocument _xml;

        /// <summary>
        /// 取出Handler列表
        /// </summary>
        /// <param name="policy"></param>
        /// <returns></returns>
        public List<string> ExceptionConfig(string policy)
        {
            List<string> ret = new List<string>();
            XmlNodeList list = this.GetXmlDocument().SelectNodes("//ExceptionPolicy/Policy[name='" + policy + "']/Handler");
            int count = list.Count - 1;
            for (int i = 0; i <= count; i++)
            {
                ret.Add(list.Item(i).Attributes["id"].Value);
            }
            return ret;
        }
        /// <summary>
        /// 获取配置节点属性键值对
        /// </summary>
        /// <param name="node"></param>
        /// <returns></returns>
        private Dictionary<string, string> GetConfig(XmlNode node)
        {
            Dictionary<string, string> value = new Dictionary<string, string>();
            int count = node.Attributes.Count - 1;
            for (int i = 0; i <= count; i++)
            {
                value.Add(node.Attributes[i].Name, node.Attributes[i].Value);
            }
            return value;
        }
        /// <summary>
        /// 获取XML对象
        /// </summary>
        /// <returns></returns>
        private XmlDocument GetXmlDocument()
        {
            if (this._xml == null)
            {
                this._xml = new XmlDocument();
                string configFile = Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName) + @"\config.xml";
                this._xml.Load(configFile);
            }
            return this._xml;
        }

        /// <summary>
        /// 快捷键设置
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public Dictionary<string, string> HotKey(string key)
        {
            return this.GetConfig(this.GetXmlDocument()["configuration"]["hotkeys"][key]);
        }
        /// <summary>
        /// 获取对象配置
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        public Dictionary<string, string> ObjectConfig(string name)
        {
            return this.GetConfig(this.GetXmlDocument()["configuration"]["objects"][name]);
        }

        /// <summary>
        /// 获取URL地址
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        public string ServiceConfig(string name)
        {
            return this.GetXmlDocument()["configuration"]["service"].Attributes[name].Value;
        }
        /// <summary>
        /// 获取节点
        /// </summary>
        /// <param name="path"></param>
        /// <returns></returns>
        public XmlNode GetNode(string path)
        {
            return GetXmlDocument().SelectSingleNode(path);
        }
        /// <summary>
        /// 取属性值
        /// </summary>
        /// <param name="path"></param>
        /// <param name="prop"></param>
        /// <returns></returns>
        public string GetValue(string path, string prop)
        {
            return GetXmlDocument().SelectSingleNode(path) .Attributes[prop].Value;
        }
        /// <summary>
        /// 设属性值
        /// </summary>
        /// <param name="path"></param>
        /// <param name="prop"></param>
        /// <param name="value"></param>
        /// <returns></returns>
        public bool SetValue(string path, string prop, string value)
        {
            GetXmlDocument().SelectSingleNode(path).Attributes[prop].Value = value;
            return true;
        }
        /// <summary>
        /// 保存修改数据
        /// </summary>
        public void Save()
        {
            string configFile = Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName) + @"\config.xml";
            XmlWriterSettings setting=new XmlWriterSettings();
            setting.Encoding = Encoding.Default;
            using (XmlWriter xml = XmlWriter.Create(configFile, setting))
            {
                GetXmlDocument().Save(xml);
            }
        }
    }
}
