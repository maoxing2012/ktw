using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace Framework
{
    /// <summary>
    /// 扫描设备接口
    /// </summary>
    public interface IScan
    {
        ///// <summary>
        ///// 获取
        ///// </summary>
        //event GetBarCodeEventHandler GetBarCode;

        /// <summary>
        /// 获取
        /// </summary>
        GetBarCodeEventHandler GetBarCode { get; set; }

        ///// <summary>
        ///// 是否发音
        ///// </summary>
        //bool Beep { get; set; }

        /// <summary>
        /// 开始扫描
        /// </summary>
        /// <param name="msgForm"></param>
        void StartScan(Form msgForm);
        /// <summary>
        /// 结束扫描
        /// </summary>
        void StopScan();
    }

    /// <summary>
    /// 获取
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    public delegate void GetBarCodeEventHandler(object sender, ScanArgs e);

    //public interface IGetBarCode
    //{
    //    void GetBarCode(object sender, ScanArgs e);
    //}
}
