using System;
namespace Json.Helpers
{
	public static class DateHelper
	{
		private static readonly DateTime _epoc = new DateTime(1970, 1, 1);
		public static int ToUnixTime(DateTime time)
		{
			return (int)time.Subtract(DateHelper._epoc).TotalSeconds;
		}
		public static DateTime FromUnixTime(int seconds)
		{
			return DateHelper._epoc.AddSeconds((double)seconds);
		}
	}
}
