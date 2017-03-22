package com.core.scpwms.server.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import com.core.business.exception.BusinessException;

@SuppressWarnings("all")
public class DateUtil {

    public static final SimpleDateFormat SDF_MBP = new SimpleDateFormat("yyyy-MM-dd");

    /** MM.dd.yyyy */
    public static final String BASIC_DATE_FORMAT = "MM.dd.yyyy";

    /** yyyyMMdd */
    public static final String PURE_DATE_FORMAT = "yyyyMMdd";

    /** yyyy/MM/dd */
    public static final String SLASH_FORMAT = "yyyy/MM/dd";

    /** yyyy-MM-dd */
    public static final String HYPHEN_DATE_FORMAT = "yyyy-MM-dd";
    
    /** yyyy-MM-dd HH:mm:ss */
    public static final String HYPHEN_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /** yy-MM-dd */
    public static final String HYPHEN_SHORT_DATE_FORMAT = "yy-MM-dd";

    /** yyyy/MM/dd HH:mm:ss */
    public static final String LONG_DATE_SLASH_FORMAT = "yyyy/MM/dd HH:mm:ss";

    /** yyyyMMddHHmmss */
    public static final String LONG_DATE_FORMAT = "yyyyMMddHHmmss";

    /**yyyyMMddHHmmssSSS */
    public static final String FULL_DATE_FORMAT = "yyyyMMddHHmmssSSS";

    /** NOW */
    public static final String NOW = "NOW";

    /** NEXT YEAR */
    public static final String NEXTYEAR = "NEXTYEAR";

    /** NEXT MONTH */
    public static final String NEXTMONTH = "NEXTMONTH";

    /** LAST YEAR */
    public static final String LASTYEAR = "LASTYEAR";

    /** LAST MONTH */
    public static final String LASTMONTH = "LASTMONTH";

    /** 一天的毫秒数 */
    public static final long MILLISECONDS_IN_DAY = 60 * 60 * 24 * 1000;

    /** DateFormat池 */
    private static final Map<String, ThreadLocal<DateFormat>> dateFormatPool = new ConcurrentHashMap<String, ThreadLocal<DateFormat>>();


    /**
     * 合法性检查。
     * 
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 日付の判定結果
     */
    public static boolean validate(String year, String month, String day) {
        try {
            if (year.length() != 4 || month.length() > 2 || day.length() > 2)
                return false;
            Calendar calendar = getCalendar(year, month, day);
            calendar.setLenient(false);
            calendar.getTime();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据出生年月日，返回年龄。
     * 
     * @param date 出生年月日
     * @return 年龄
     */
    public static int getAge(Date date) {
        int age;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Calendar cal = new GregorianCalendar();

        int birthYear = calendar.get(Calendar.YEAR);
        int birthMonth = calendar.get(Calendar.MONTH) + 1;
        int birthDay = calendar.get(Calendar.DATE);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);

        if (month > birthMonth) {
            age = year - birthYear;
        } else if (month == birthMonth && day > birthDay) {
            age = year - birthYear;
        } else {
            age = year - birthYear - 1;
        }
        return age;
    }

    /**
     * 根据出生年月日，返回年龄。
     * 
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 年齢
     */
    public static int getAge(String year, String month, String day) {
        return getAge(getDate(year, month, day));
    }

    /**
     * 取得Calendar型的日期。
     * 
     * @param year 年
     * @param month 月
     * @param day 日
     * @return
     */
    public static Calendar getCalendar(String year, String month, String day) {
        int iYear = Integer.parseInt(year);
        int iMonth = Integer.parseInt(month) - 1;
        int iDay = Integer.parseInt(day);

        Calendar calendar = new GregorianCalendar(iYear, iMonth, iDay, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 取得Date型的日期。
     * 
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 引数の変換結果
     */
    public static Date getDate(String year, String month, String day) {
        return getDate(year, month, day, "0", "0", "0");
    }

    /**
     * 取得Date型的时间。
     * 
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 時
     * @param minute 分
     * @return
     */
    public static Date getDate(String year, String month, String day, String hour, String minute) {
        return getDate(year, month, day, hour, minute, "0");
    }

    /**
     * 取得Date型的日期。
     * 
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 時
     * @param minute 分
     * @param second 秒
     * @return
     */
    public static Date getDate(String year, String month, String day, String hour, String minute, String second) {

        int iYear = Integer.parseInt(year);
        int iMonth = Integer.parseInt(month) - 1;
        int iDay = Integer.parseInt(day);
        int iHour = Integer.parseInt(hour);
        int iMinute = Integer.parseInt(minute);
        int iSecond = Integer.parseInt(second);

        Calendar calendar = new GregorianCalendar(iYear, iMonth, iDay, iHour, iMinute, iSecond);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setLenient(false);
        return calendar.getTime();
    }

    /**
     * 取得Date型的时间。
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static Date getDate(String date, String pattern) {
        DateFormat df = getDateFormat(pattern);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * <p>Date转Timestamp</p>
     *
     * @param date
     * @return
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 
     * <p>取得Timstamp型的时间。</p>
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Timestamp getTimestamp(String date, String pattern) {
        DateFormat df = getDateFormat(pattern);
        try {
            return new Timestamp(df.parse(date).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * <p>格式化日期时间</p>
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getStringDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        DateFormat df = getDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 
     * <p>日期格式合法性检查</p>
     *
     * @param date
     * @return
     */
    public static boolean validate(String date) {
        try {
            // スラッシュの位置を取得する
            int slashMarkIndex1 = date.indexOf("/");
            int slashMarkIndex2 = date.indexOf("/", slashMarkIndex1 + 1);

            // 年、月、日をそれぞれ取得する
            String year = date.substring(0, slashMarkIndex1);
            String month = date.substring(slashMarkIndex1 + 1, slashMarkIndex2);
            String day = date.substring(slashMarkIndex2 + 1);

            return validate(year, month, day);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 
     * <p>取得java.sql.Date型日期</p>
     *
     * @param date
     * @return
     */
    public static java.sql.Date getSQLDate(String date) {
        int slashMarkIndex1 = date.indexOf("/");
        int slashMarkIndex2 = date.indexOf("/", slashMarkIndex1 + 1);

        String year = date.substring(0, slashMarkIndex1);
        String month = date.substring(slashMarkIndex1 + 1, slashMarkIndex2);
        String day = date.substring(slashMarkIndex2 + 1);

        return getSQLDate(year, month, day);
    }

    /**
     * 
     * <p>取得java.sql.Date型日期</p>
     *
     * @param yyyy
     * @param mm
     * @param dd
     * @return
     */
    public static java.sql.Date getSQLDate(String yyyy, String mm, String dd) {
        int iYear = Integer.parseInt(yyyy);
        int iMonth = Integer.parseInt(mm) - 1;
        int iDay = Integer.parseInt(dd);

        Date date0 = new GregorianCalendar(iYear, iMonth, iDay).getTime();
        return new java.sql.Date(date0.getTime());
    }

    /**
     * 
     * <p>格式变换</p>
     *
     * @param text
     * @param fromPattern
     * @param toPattern
     * @return
     * @throws ParseException
     */
    public static String convertFormat(String text, String fromPattern, String toPattern) throws ParseException {
        DateFormat formatter = getDateFormat(fromPattern);
        Date date = formatter.parse(text);

        return getStringDate(date, toPattern);

    }

    /**
     * 
     * <p>N年后（前）的日期</p>
     *
     * @param currentTime
     * @param n
     * @return
     */
    public static Date nextNYear(Date currentTime, int n) {
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentTime);
        currentCal.add(Calendar.YEAR, n);

        return currentCal.getTime();
    }

    /**
     * 
     * <p>N月后（前）的日期</p>
     *
     * @param today
     * @param n
     * @return
     */
    public static Date nextNMonth(Date today, int n) {
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(today);
        currentCal.add(Calendar.MONTH, n);

        return currentCal.getTime();
    }

    /**
     * 
     * <p>N天后（前）的日期</p>
     *
     * @param today
     * @param n
     * @return
     */
    public static Date nextNDay(Date today, int n) {
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(today);
        currentCal.add(Calendar.DAY_OF_YEAR, n);

        return currentCal.getTime();
    }

    /**
     * 
     * <p>间隔的日子</p>
     *
     * @param from
     * @param to
     * @return
     */
    public static int getDays(Date from, Date to) {
        return (int) ((to.getTime() - from.getTime()) / MILLISECONDS_IN_DAY);
    }

    /**
     * 
     * <p>天数（28，30，31）</p>
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMaximumDay(String year, String month) {
        int iYear = Integer.parseInt(year);
        int iMonth = Integer.parseInt(month) - 1;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, iYear);
        cal.set(Calendar.MONTH, iMonth);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 
     * <p>取得年，YYYY形式</p>
     *
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = getDateFormat("yyyy");
        return df.format(date);
    }

    /**
     * 
     * <p>取得月，M形式</p>
     *
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = getDateFormat("M");
        return df.format(date);
    }

    /**
     * 
     * <p>取得日，d形式</p>
     *
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = getDateFormat("d");
        return df.format(date);
    }

    /**
     * 
     * <p>格式化</p>
     *
     * @param pattern
     * @return
     */
//    private static DateFormat getDateFormat(String pattern) {
//        DateFormat df = null;
//        if (dateFormatPool.containsKey(pattern)) {
//            df = dateFormatPool.get(pattern);
//        } else {
//            df = new SimpleDateFormat(pattern);
//            df.setTimeZone(TimeZone.getDefault());
//            dateFormatPool.put(pattern, df);
//        }
//        return df;
//    }
	private static DateFormat getDateFormat(final String pattern) {
		ThreadLocal<DateFormat> tl = dateFormatPool.get(pattern);

		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
        	tl = new ThreadLocal<DateFormat>() {
	        	@Override
	            protected SimpleDateFormat initialValue() {
		        		SimpleDateFormat dateFormat =  new SimpleDateFormat(pattern, Locale.JAPAN);
		        		dateFormat.setTimeZone(TimeZone.getDefault());
		        		return dateFormat;
	                }
	            };
	        
	        dateFormatPool.put(pattern, tl);
        }
	
		return tl.get();
	}
    
    /**
     * 
     * <p>获取当前时间，格式yyyy-MM-dd hh:mm:ss</p>
     *
     * @return
     */
    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 
     * <p>获取当前时间，格式自定义</p>
     *
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(day.getTime());
        return date;
    }
    
	/**
	  * Java日期格式转成JDE日期格式
	  * @param date Java日期
	  * @return JDE日期
	  */
	 public static int javaDateToJDEDate(java.util.Date date) {
	  if (date == null)
	   return 0;
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTime(date);
	  int year = calendar.get(Calendar.YEAR) - 1900;
	  int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

	  return year * 1000 + dayOfYear;
	 }

}
