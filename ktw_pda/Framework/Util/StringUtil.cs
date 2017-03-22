using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;

namespace Framework
{
    /// <summary>
    /// String的修改函数
    /// </summary>
    public class StringUtil
    {
        /// <summary>
        /// SHA1
        /// </summary>
        /// <param name="strIn"></param>
        /// <returns></returns>
        public static string GetSHA1(string strIn)
        {
            SHA1CryptoServiceProvider sha1 = new SHA1CryptoServiceProvider();
            byte[] tmpByte = sha1.ComputeHash(Encoding.UTF8.GetBytes(strIn));
            sha1.Clear();
            StringBuilder strResult = new StringBuilder();
            int len = tmpByte.Length - 1;
            for (int i = 0; i <= len; i++)
            {
                strResult.Append(tmpByte[i].ToString("x2"));
            }
            return strResult.ToString();
        }
        /// <summary>
        /// 判断是否为数字
        /// </summary>
        /// <param name="inputString"></param>
        /// <returns></returns>
        public static bool IsDigital(string inputString)
        {
            foreach (Char eachChar in inputString)
            {
                if (!char.IsDigit(eachChar))
                {
                    return false;
                }
            }
            return true;
        }

        /// <summary>
        /// 判断是否为空
        /// </summary>
        /// <param name="inputString"></param>
        /// <returns></returns>
        public static bool IsEmpty(string inputString)
        {
            return (TrimString(inputString) == string.Empty);
        }
        /// <summary>
        /// TrimString
        /// </summary>
        /// <param name="inputString"></param>
        /// <returns></returns>
        public static string TrimString(object inputString)
        {
            if (inputString == null || DBNull.Value.Equals(inputString))
            {
                return string.Empty;
            }
            return inputString.ToString().Trim();
        }

        /// <summary>
        /// String parse long
        /// </summary>
        /// <param name="inputString"></param>
        /// <returns></returns>
        public static long ParseLong(string inputString)
        {
            if (string.IsNullOrEmpty(TrimString(inputString)))
            {
                return 0;
            }
            return long.Parse(inputString);
        }
        
    }
}
