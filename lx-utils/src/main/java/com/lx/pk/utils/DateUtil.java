package com.lx.pk.utils;
import org.apache.commons.lang.StringUtils;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_DATETIME_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	/**
	 * 获取当前时间串
	 * @param format 日期格式
	 * @return
	 */
	public static String currentTimestamp2String(String format){
		if(StringUtils.isEmpty(format)){
			format = DateUtil.DEFAULT_DATETIME_FORMAT;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * 字符串转日期
	 * @param sourceTime
	 * @return
	 */
	public static Date String2Date(String sourceTime) {
		return string2Date(sourceTime, DateUtil.DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 字符串转日期
	 * @param sourceTime
	 * @param format
	 * @return
	 */
	public static Date string2Date(String sourceTime,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(sourceTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转字符串
	 * @param date
	 * @return
	 */
	public static String date2String(Date date){
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			return sdf.format(date);
		} else {
			return null;
		}
	}

	/**
	 * 日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date,String format){
		if (null == format || StringUtils.isEmpty(format)) {
			format = DateUtil.DEFAULT_DATETIME_FORMAT;
		}

		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return null;
		}
	}

	/**
	 * 获得前几天的时间
	 * @param now
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date now, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.DATE, c.get(Calendar.DATE) - day);
		return c.getTime();
	}

	/**
	 * 获得后几天的时间
	 * @param now
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date now, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + day);
		return c.getTime();
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 * @author bjkandy
	 * @param beginDate
	 * @param endDate
	 * @return List
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

	/**
	 * 获得两个时间的时间差
	 * eg
	 * smdate : 2015-09-24
	 * bdate : 2015-09-25
	 * 返回为1
	 * @throws Exception
	 */
	public static Integer getDateBetween(Date smdate,Date bdate) {
		SimpleDateFormat sdf=new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days=(time2-time1)/(1000*3600*24);
			return Integer.parseInt(String.valueOf(between_days));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;

	}
	/**
	 * 获取当前的日期yyyy-mm-dd hh:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}
	/**
	 * 判断你输入的日期是否在该时间段内
	 */
	public static boolean betweenTimes(String nowDates,String start,String end){
		boolean flag = false;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date before = formatter.parse(start);
			Date after  = formatter.parse(end);
			Date now = formatter.parse(nowDates);
			if(now.before(after)&&now.after(before)){
				flag = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static Date getDateAfter(Date date,Integer day){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,day);
		return calendar.getTime();
	}


//	public static void main(String[] args){
//	    System.out.println(DateUtils.getDateBetween(DateUtils.string2Date("2015-09-24", DateUtils.DEFAULT_DATE_FORMAT), DateUtils.string2Date("2015-09-25", DateUtils.DEFAULT_DATE_FORMAT)));
//	}

}
