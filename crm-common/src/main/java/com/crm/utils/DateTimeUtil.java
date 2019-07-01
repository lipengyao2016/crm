package com.crm.utils;

/**
 * 时间\日期工具类
 */
import com.crm.common.MessageException;
import com.crm.common.MessageException;
import com.sun.javafx.binding.StringFormatter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DateTimeUtil {
	/**
	 * 返回当前时间的Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(Date dateDate) {
		if (dateDate == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(Date dateDate) {
		if (dateDate == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间
	 * 
	 * @param strDate
	 *            yyyy-MM-dd格式的字符串
	 * @return
	 */
	public static Date strToDate(String strDate) {
		if (strDate == null)
			return null;
		return strToDate(strDate, "yyyy-MM-dd");
	}

	/**
	 * 转换字符串为日期类型，根据格式进行转换
	 * 
	 * @param strDate
	 *            日期字符串，格式要与定义的format保持一致
	 * @param format
	 *            转换的格式，如yyyyMMdd、yyyyMMddHHmmss、yyyyMMdd HHmmss ....
	 * @return
	 * 
	 * @author yonge
	 * @since 2012-4-25
	 * 
	 */
	public static Date strToDate(String strDate, String format) {
		if (strDate == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 一天的最后时间
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateEnd(String strDate) {
		if (strDate == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strToDate(strDate));
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "0";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
			return "";
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 把给定时间StartDate加上多少个小时之后的时间。
	 * 
	 * @param startDate
	 *            开始时间。(yyyy-MM-dd HH:mm:ss)
	 * @param hours
	 *            增减的小时整数
	 * @return 返回带时分秒格式的时间。(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @author yonge
	 * @since 2011-5-31
	 * 
	 */
	public static Date getNextHours(Date startDate, int hours) {
		try {
			long myTime = startDate.getTime();
			long hoursTime = hours * 60 * 60 * 1000;
			Date result = new Date();
			result.setTime(myTime + hoursTime);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return DateTimeUtil.strToDateLong(format.format(result));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {
		if (!isDate(dat)) {
			throw new MessageException("日期格式不对");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		try {
			cale.setTime(format.parse(dat));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		String lastday = format.format(cale.getTime());

		return lastday;
	}

	/**
	 * 获取指定日期当月第一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getStartDateOfMonth(String dat) {
		if (dat == null || "".equals(dat)) {
			dat = getStringDate();
		}
		if (!isDate(dat)) {
			throw new MessageException("日期格式不对");
		}

		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		try {
			cale.setTime(simple.parse(dat));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		String firstday = simple.format(cale.getTime());
		return firstday;
	}

	/**
	 * 获取指定日期当季第一天
	 * 
	 * @return
	 */
	public static String getStartDateOfSeason(String dat) {
		if (dat == null || "".equals(dat)) {
			dat = getStringDate();
		}
		if (!isDate(dat)) {
			throw new MessageException("日期格式不对");
		}

		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(simple.parse(dat));
			c.set(Calendar.DATE, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int month = c.get(Calendar.MONTH) + 1;

		if (month >= 1 && month <= 3) {
			c.set(Calendar.MONTH, 0);
		} else if (month >= 4 && month <= 6) {
			c.set(Calendar.MONTH, 3);
		} else if (month >= 7 && month <= 9) {
			c.set(Calendar.MONTH, 4);
		} else if (month >= 10 && month <= 12) {
			c.set(Calendar.MONTH, 9);
		}
		String firstday = simple.format(c.getTime());
		return firstday;
	}

	/**
	 * 获取指定日期当年第一天
	 * 
	 * @return
	 */
	public static String getStartDateOfYear(String dat) {
		if (dat == null || "".equals(dat)) {
			dat = getStringDate();
		}
		if (!isDate(dat)) {
			throw new MessageException("日期格式不对");
		}

		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(simple.parse(dat));
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DATE, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String firstday = simple.format(c.getTime());
		return firstday;
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeekFirstDay(String sdate) {
		if (sdate == null || "".equals(sdate)) {
			sdate = getStringDateShort();
		}

		// 再转换为时间
		Date dd = strToDate(sdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dd);
		int d = 0;
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			d = -6;
		} else {
			d = 2 - cal.get(Calendar.DAY_OF_WEEK);
		}
		cal.add(Calendar.DAY_OF_WEEK, d);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的数字字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeekStr(String sdate) {
		String str = getWeek(sdate);
		if ("1".equals(str)) {
			str = "星期日";
		} else if ("2".equals(str)) {
			str = "星期一";
		} else if ("3".equals(str)) {
			str = "星期二";
		} else if ("4".equals(str)) {
			str = "星期三";
		} else if ("5".equals(str)) {
			str = "星期四";
		} else if ("6".equals(str)) {
			str = "星期五";
		} else if ("7".equals(str)) {
			str = "星期六";
		}
		return str;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;

		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (ParseException e) {
			throw new MessageException(StringFormatter.format("转换异常：param:{0},param2:{1}",date1,date2).getValue());
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 是否正确的日期格式
	 * 
	 * @param date
	 */
	public static boolean isDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		;
		if (date == null)
			return false;
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			sdf.parse(date);
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		if (strDate == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 获取一个时间段内的所有日期(日期格式(yyyy-MM-dd))
	 * @param dBegin 开始日期
	 * @param dEnd 结束日期
	 * @return 指定时间段内的日期集合
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd)  
	{  
		List<Date> lDate = new ArrayList<Date>();  
		lDate.add(dBegin);  
		Calendar calBegin = Calendar.getInstance();  
		// 使用给定的 Date 设置此 Calendar 的时间  
		calBegin.setTime(dBegin);  
		Calendar calEnd = Calendar.getInstance();  
		// 使用给定的 Date 设置此 Calendar 的时间  
		calEnd.setTime(dEnd);  
		// 测试此日期是否在指定日期之后  
		while (dEnd.after(calBegin.getTime()))  
		{  
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
			calBegin.add(Calendar.DAY_OF_MONTH, 1);  
			lDate.add(calBegin.getTime());  
		}  
		return lDate;  
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(DateTimeUtil.getEndDateOfMonth("2018-08" + "-01"));
//
	}

	/**
	 * 获取一个时间段内的所有日期
	 * @param dBegin 开始日期
	 * @param dEnd 结束日期
	 * @param field 增加类型(年月日Calendar枚举)
	 * @return 指定时间段内的日期集合
	 * @throws ParseException 
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd,int field){
		List<Date> lDate = new ArrayList<Date>();  
		lDate.add(dBegin);  
		Calendar calBegin = Calendar.getInstance();  
		// 使用给定的 Date 设置此 Calendar 的时间  
		calBegin.setTime(dBegin);  
		Calendar calEnd = Calendar.getInstance();  
		// 使用给定的 Date 设置此 Calendar 的时间  
		calEnd.setTime(dEnd);  
		// 测试此日期是否在指定日期之后  
		while (dEnd.after(calBegin.getTime()))  
		{  
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
			calBegin.add(field, 1);  
			lDate.add(calBegin.getTime());  
		}
//		lDate.remove(lDate.size()-1);
		return lDate;  
	}

	//获取指定月份的当前第一天
	public static Date getMonthStart(Date date) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		int index = calendar.get(Calendar.DAY_OF_MONTH);  
		calendar.add(Calendar.DATE, (1 - index));  
		return calendar.getTime();  
	}  
	//获取指定月份的当前月最后一天
	public static Date getMonthEnd(Date date) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		calendar.add(Calendar.MONTH, 1);  
		int index = calendar.get(Calendar.DAY_OF_MONTH);  
		calendar.add(Calendar.DATE, (-index));  
		return calendar.getTime();  
	}  
}
