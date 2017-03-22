//#define Test
using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
//using Hprose.Client;
using System.Text.RegularExpressions;
using System.IO;
using System.Reflection;
using Entity;
using System.Drawing;
//using OpenNETCF.Reflection;
//using com.core.scpwms.server.mobile.bean;
using Json;
using System.Resources;

namespace Framework
{
    public class AppContext
    {
        #region 成员
        /// <summary>
        /// 应用程序上下文对象
        /// </summary>
        private static AppContext _appInstance = new AppContext();
        
        /// <summary>
        /// 共享对象
        /// </summary>
        //private Dictionary<string, HproseHttpClient> _clientDic = new Dictionary<string, HproseHttpClient>();
        private Config _config;
        private Dictionary<string, object> _contextDic = new Dictionary<string, object>();
        private Factory _factory;
        private Dictionary<string, HotKey> _hotkeyDic = new Dictionary<string, HotKey>();

        //private Navigator _navigator = new Navigator();

        /// <summary>
        /// 消息记录
        /// </summary>
        private Dictionary<string, string> _messageDic;
        /// <summary>
        /// 扫描设备
        /// </summary>
        private IScan _scanner;

        #endregion

        #region 构造器

        private AppContext()
        {
        }

        #endregion

        #region 共有方法

        /// <summary>
        /// 清除共享对象
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public bool ClearObject(string key)
        {
            if (this._contextDic.ContainsKey(key))
            {
                if (this._contextDic[key] is IDisposable)
                {
                    ((IDisposable)this._contextDic[key]).Dispose();
                    this._contextDic[key] = null;
                }
                this._contextDic.Remove(key);
                return true;
            }
            return false;
        }
        /// <summary>
        /// 创建对象
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="config"></param>
        /// <returns></returns>
        public T CreateObject<T>(string config)
        {
            if (this._factory == null)
            {
                this._factory = new Factory();
            }
            return this._factory.CreateObject<T>(config);
        }


        /// <summary>
        /// 从服务器获取数据
        /// </summary>
        /// <param name="method"></param>
        /// <param name="req"></param>
        /// <returns></returns>
        public MobileResponse<T> Execute<T>(string method, MobileRequest req)
        {
            Config config = this.GetConfig();
            string url = "http://" + config.GetValue(@"//setting/server", "value") + config.GetValue(@"//setting/server", "web");
#if Test
            object[] results = new object[]{method.GetMessage()};
#else
            MobileServiceManagerEx service = new MobileServiceManagerEx(url);
            object[] results = service.Execute(method, new object[] {
                        Converter.Serialize(req)});
#endif
            //调试语句
#if DEBUG
            System.Diagnostics.Debug.WriteLine("{0}={1}".FormatEx(method, Converter.Serialize(req)));
            System.Diagnostics.Debug.WriteLine("{0}={1}".FormatEx( method, results[0]));
#endif
            return Converter.Deserialize<MobileResponse<T>>((string)results[0]);
        }

        #region 帮助类
        private class MobileServiceManagerEx : MobileServiceManager.MobileServiceManager
        {
            public MobileServiceManagerEx(string url)
                : base()
            {
                this.Url = url;
                //超时3分钟  3 * 60 * 1000
                this.Timeout = 0x2bf20;
            }

            internal object[] Execute(string methodName, object[] parameters)
            {
                try
                {
                    return base.Invoke(methodName, parameters);
                }
                catch
                {
                    //出错Log参数值
                    Log.Print("MethodName:{0} parameter:{1}".FormatEx(methodName, parameters[0]));
                    throw;
                }
            }
        }
        #endregion


        /// <summary>
        /// 获取配置访问类
        /// </summary>
        /// <returns></returns>
        public Config GetConfig()
        {
            if (this._config == null)
            {
                this._config = new Config();
            }
            return this._config;
        }

        /// <summary>
        /// 获取热键设置
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public HotKey GetHotKey(string key)
        {
            if (!this._hotkeyDic.ContainsKey(key))
            {
                HotKey h = new HotKey();
                Dictionary<string, string> setting = this.GetConfig().HotKey(key);
                try
                {
                    h.Value = int.Parse(setting["value"]).ToString();
                }
                catch
                {
                    h.Value="0";
                }
                h.Text = setting["text"];
                this._hotkeyDic.Add(key, h);
            }
            return this._hotkeyDic[key];
        }

        /// <summary>
        /// 获取消息文本
        /// </summary>
        /// <param name="code"></param>
        /// <returns></returns>
        public string GetMessage(string code)
        {
            if (StringUtil.IsEmpty(code))
            {
                return string.Empty;
            }
            if (this._messageDic == null)
            {
                this._messageDic = new Dictionary<string, string>();
            }
            code = code.Trim();
            if (!this._messageDic.ContainsKey(code))
            {
                //string fileContents;
                string value = code;
                Regex reg = new Regex(".*" + code + "[ ]*=[ ]*(?<value>.+)", RegexOptions.Compiled);
                string filePath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName) + @"\Message.txt";
                using (StreamReader sr = new StreamReader(File.Open(filePath, FileMode.Open), Encoding.GetEncoding("Shift-JIS")))
                {
                    //fileContents = sr.ReadToEnd();
                    while (!sr.EndOfStream)
                    {
                        string line = sr.ReadLine();
                        if (!line.StartsWith("#") && !line.IsEmpty())
                        {
                            //非注释行且非空行
                            int eq = line.IndexOf('=');
                            string codeValue = line.Substring(0, eq);
                            value = line.Substring(eq + 1);
                            if (_messageDic.ContainsKey(codeValue))
                                break;
                            this._messageDic.Add(codeValue, value);
                        }
                    }
                }
                //Match match = reg.Match(fileContents);
                //while (match.Success)
                //{
                //    value = match.Result("${value}");
                //    if (match.Value.Trim().StartsWith("#"))
                //    {
                //        match = match.NextMatch();
                //    }
                //    else
                //    {
                //        break;
                //    }
                //}
                //this._messageDic.Add(code, value);
            }
            return this._messageDic[code];
        }
        /// <summary>
        /// 动态Message
        /// </summary>
        /// <param name="code"></param>
        /// <param name="value"></param>
        /// <returns></returns>
        public bool SetMessage(string code,string value)
        {
            if (string.IsNullOrEmpty(code))
                return true;
            if (this._messageDic == null)
            {
                this._messageDic = new Dictionary<string, string>();
            }
            code = code.Trim();
            if (!this._messageDic.ContainsKey(code))
            {
                _messageDic.Add(code, string.Empty);
            }
            _messageDic[code] = value;
            return true;
        }
        /// <summary>
        /// 获取共享对象
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="key"></param>
        /// <returns></returns>
        public T GetObject<T>(string key)
        {
            if (this._contextDic.ContainsKey(key))
            {
                return (T)this._contextDic[key];
            }
            return default(T);
        }

        /// <summary>
        /// 获取扫描设备通信接口
        /// </summary>
        /// <returns></returns>
        public IScan GetScanner()
        {
            if (this._scanner == null)
            {
                this._scanner = this.CreateObject<IScan>("Scanner");
            }
            return this._scanner;
        }

        /// <summary>
        /// 获取对象
        /// </summary>
        /// <returns></returns>
        public static AppContext Instance()
        {
            return _appInstance;
        }

        /// <summary>
        /// 添加共享对象
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="key"></param>
        /// <param name="obj"></param>
        /// <returns></returns>
        public bool SetObject<T>(string key, T obj)
        {
            if (this._contextDic.ContainsKey(key))
            {
                this._contextDic[key] = obj;
            }
            else
            {
                this._contextDic.Add(key, obj);
            }
            return true;
        }
        /// <summary>
        /// 获取图片共通，缓存图片提升速度
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        public Image GetImage(string key)
        {
            //if (!this._imageDic.ContainsKey(key))
            //{
            //    MemoryStream stream =
            //    (MemoryStream)Assembly2.GetEntryAssembly().GetManifestResourceStream(key);
            //    AlphaImage img = AlphaImage.CreateFromStream(stream);
            //    _imageDic[key] = img;
            //}
            //return _imageDic[key];
            UnmanagedMemoryStream stream =(UnmanagedMemoryStream)Assembly.GetEntryAssembly().GetManifestResourceStream(key);
            return Image.FromStream(stream);
        }

        ///// <summary>
        ///// 获取导航对象
        ///// </summary>
        ///// <returns></returns>
        //public Navigator GetNavigator()
        //{
        //    return this._navigator;
        //}

        /// <summary>
        /// 获取构造函数(共有、私有)
        /// </summary>
        /// <param name="target"></param>
        /// <param name="types"></param>
        /// <returns></returns>
        public ConstructorInfo GetObjectConstructor(Type target, Type[] types)
        {
            StringBuilder key = new StringBuilder(target.FullName);
            key.Append("Constructor");
            foreach (Type t in types)
            {
                key.Append(",");
                key.Append(t.FullName);
            }
            ConstructorInfo ret = GetObject<ConstructorInfo>(key.ToString());
            if (ret == null)
            {
                ret = target.GetConstructor(BindingFlags.Instance | BindingFlags.Public | System.Reflection.BindingFlags.NonPublic, null, types, null);
                if (ret != null)
                {
                    SetObject(key.ToString(), ret);
                }
            }
            return ret;
        }

        #endregion 

        #region 共有属性
        /// <summary>
        /// 登录用户
        /// </summary>
        public UserObj LoginUser
        {
            get;
            set;
        }
        /// <summary>
        /// 货主信息
        /// </summary>
        public OwnerInfo Owner
        {
            get;
            set;
        }
        #endregion
        /// <summary>
        /// 程序版本
        /// </summary>
        public string Version { get; set; }
    }
}
