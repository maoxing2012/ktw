using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Runtime.InteropServices;

namespace Framework
{
    /// <summary>
    /// show the Standard SIP without the menu
    /// with the "SetPosition" method you can set where to display the SIP
    /// </summary>
    public class Sip
    {
        //class WinApi
        //{
        //    //[DllImport("coredll.dll")]
        //    //public static extern int SipShowIM(SIPStatus i);
        //    //[DllImport("coredll.dll")]
        //    //public static extern bool SipSetInfo(ref SIPINFO pSipInfo);
        //    //[DllImport("coredll.dll")]
        //    //public static extern bool SipGetInfo(ref SIPINFO pSipInfo);
        //}
        private static bool pvtVisible = false;
        //private enum SIPStatus
        //{
        //    SIPF_OFF = 0,
        //    SIPF_ON
        //}
        //private struct SIPINFO
        //{
        //    public Int32 cbSize;
        //    public Int32 fdwFlags;
        //    public RECT rcVisibleDesktop;
        //    public RECT rcSipRect;
        //    public Int32 dwImDataSize;
        //    public Int32 pvImData;
        //}

        //private struct RECT
        //{
        //    public Int32 left;
        //    public Int32 top;
        //    public Int32 right;
        //    public Int32 bottom;
        //}


        //private void Show()
        //{
        //    WinApi.SipShowIM(SIPStatus.SIPF_ON);
        //}

        //private void Hide()
        //{
        //    WinApi.SipShowIM(SIPStatus.SIPF_OFF);
        //}

        //public void SetPosition(Int32 top)
        //{
        //    SIPINFO mySi = new SIPINFO();
        //    bool result = true;
        //    mySi.cbSize = System.Runtime.InteropServices.Marshal.SizeOf(typeof(SIPINFO));
        //    result = WinApi.SipGetInfo(ref mySi);
        //    mySi.rcSipRect.top = top;
        //    mySi.rcSipRect.bottom = top + 80;
        //    result = WinApi.SipSetInfo(ref mySi);
        //}

        ////public SIPINFO GetSipInfo()
        ////{
        ////    SIPINFO mySi = new SIPINFO();
        ////    bool result = true;
        ////    mySi.cbSize = System.Runtime.InteropServices.Marshal.SizeOf(typeof(SIPINFO));
        ////    result = WinApi.SipGetInfo(ref mySi);
        ////    return mySi;
        ////}


        /// <summary>
        /// Property setting shows or hides the SIP
        /// </summary>
        public static bool Visible
        {
            get
            {
                return pvtVisible;
            }
            set
            {
                //if (value == true)
                //{
                //    WinApi.SipShowIM(SIPStatus.SIPF_ON);
                //}
                //else
                //{
                //    WinApi.SipShowIM(SIPStatus.SIPF_OFF);
                //}
                pvtVisible = value;
            }
        }
    }

}
