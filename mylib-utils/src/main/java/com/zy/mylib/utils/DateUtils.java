package com.zy.mylib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 返回格式：2007-08-14
	 * @return
	 */
	public static String getToday(){
		String time = "";
		time = getToday("yyyy-MM-dd");
		return time;
	}
	
	public static String between(Date start,Date end){
		long span = end.getTime() - start.getTime();
		int day = (int) (span / (24 * 60 * 60 * 1000));
		int hh = (int) (span % (24 * 60 * 60 * 1000) / (60 * 60 * 1000));
		int mm = (int) (span % (60 * 60 * 1000) / (60 * 1000));
		int ss = (int) (span % (60 * 1000) / 1000);
		return (day > 0?day+"天":"") + (hh > 0?hh + "小时":"") + (mm > 0?mm+"分":"") + ss + "秒";
	}
	
	/**
	 * 
	 * @param format 根据指定的格式时间类型返回当前时间
	 * @return
	 */
	public static String getToday(String format){
		return getDateStr(Calendar.getInstance().getTime(),format);
	}
	/**
	 * 格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getTimestamp(){
		return getToday("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * @return
	 */
	public static String getNowTime(){
		return getToday("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 日期转字符
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateStr(Date date,String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * @param millis
	 * @return
	 */
	public static Date parseMills(long millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前年的第一天
	 * @return
	 */
	public static String getYearFirst(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar currCalendar=Calendar.getInstance();
		Calendar calendar=Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, currCalendar.get(Calendar.YEAR));
		return df.format(calendar.getTime());
	}
	
	/**
	 * 获取当前月的第一天
	 * @param format
	 * @return
	 */
	public static String getFirstDayMonth(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar calendar=Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);    
	    calendar.set(Calendar.MINUTE, 0);    
	    calendar.set(Calendar.SECOND, 0);
		return df.format(calendar.getTime());
	}
	public static String getCurYear() {
		return getToday("yyyy");
	}
	public static String getCurMonth() {
		return getToday("MM");
	}
	public static String getCurDay() {
		return getToday("dd");
	}
	
	/**
	 * 获取当前周的第一天
	 * @return
	 */
	public static String getFirstDayOfWeek(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar calendar=Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK,calendar.getFirstDayOfWeek());
		calendar.set(Calendar.HOUR_OF_DAY, 0);    
		calendar.set(Calendar.MINUTE, 0);    
		calendar.set(Calendar.SECOND, 0);
		return df.format(calendar.getTime());
	}
	
	/**
	 * 计算两日期相差月份个数
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2){
		int result = 0;
		try {
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(date2));
			int year1 = c.get(Calendar.YEAR);
			int month1 = c.get(Calendar.MONTH);
			c.setTime(sdf.parse(date1));
			int year2 = c.get(Calendar.YEAR);
			int month2 = c.get(Calendar.MONTH);
			if(year1 == year2) {
				result = month1 - month2;
			} else {
				result = 12*(year1 - year2) + month1 - month2;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	  * 获取当前星期几
	  * @param pTime
	  * @return
	  */
	 public static int dayForWeek(String pTime) throws Exception {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar c = Calendar.getInstance();
		 c.setTime(format.parse(pTime));
		 int dayForWeek = 0;
		 if(c.get(Calendar.DAY_OF_WEEK) == 1){
			 dayForWeek = 7;
		 }else{
			 dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		 }
		 return dayForWeek;
	}
	 
	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
	 public static int daysBetween(String smdate,String bdate) throws ParseException{  
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
         Calendar cal = Calendar.getInstance();    
         cal.setTime(sdf.parse(smdate));    
         long time1 = cal.getTimeInMillis();                 
         cal.setTime(sdf.parse(bdate));    
         long time2 = cal.getTimeInMillis();         
         long between_days=(time2-time1)/(1000*3600*24);  
         return Integer.parseInt(String.valueOf(between_days));     
     }  
	 
	 /**
	 * 计算两个日期之间的工作日
	 * @param strStartDate
	 * @param strEndDate
	 * @return
	 */
	 @SuppressWarnings("deprecation")
	 public static int getDutyDays(String strStartDate,String strEndDate) {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 Date startDate=null;
		 Date endDate = null;
		 try {
			 startDate=df.parse(strStartDate);
			 endDate = df.parse(strEndDate);
		 } catch (ParseException e) {
			 System.out.println("非法的日期格式,无法进行转换");
			 e.printStackTrace();
		 }
		 int result = 0;
		 while (startDate.compareTo(endDate) <= 0) {
			 if (startDate.getDay() != 6 && startDate.getDay() != 0){
				 result++;
			 }
			 startDate.setDate(startDate.getDate() + 1);
		 }
		 return result;
 	 }
	 
	 /**
	  * 获取上个月的某一天
	  * @param date
	  * @param day
	  * @return
	  */
	 public static Date getLastDate(String date, int day) {
		Calendar cal = Calendar.getInstance();
	 	try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(date));
			cal.add(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, day);//设置上月几号
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return cal.getTime();
	 }
	 
	 /**
	  * 获取下个月的某一天
	  * @param date
	  * @param day
	  * @return
	  */
	 public static String getNextDate(Date date, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, +1);
		cal.set(Calendar.DAY_OF_MONTH, day);//设置上月几号
        return sdf.format(cal.getTime());
	 }
	 
	 /**
	  * 获取当前月的某一天
	  * @param date
	  * @param day
	  * @return
	  */
	 public static Date getCurrentDate(String date, int day) {
		Calendar cal = Calendar.getInstance();
	 	try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			cal.setTime(sdf.parse(date));
			cal.set(Calendar.DAY_OF_MONTH, day);//设置上月几号
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return cal.getTime();
	 }
	 
	 /**
	  * 获取当前月的某一天
	  * @param date
	  * @param day
	  * @return
	  */
	 public static String getCurrentDate(Date date, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day);//设置上月几号
        return sdf.format(cal.getTime());
	 }
	 
	 /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
     public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
     }
	     
    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
     public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
     }
	 
     public static Date lastYearDate(String date) {
 		Calendar cal = Calendar.getInstance();
 	 	try {
 			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 			cal.setTime(sdf.parse(date));
 		    cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-1);
 		} catch (ParseException e) {
 			e.printStackTrace();
 		}
         return cal.getTime();
 	 }
     
     /**
	  * 获取去年的某一天
	  * @param day
	  * @return
	  */
	 public static String lastYearByDay(int month,int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
        return sdf.format(cal.getTime());
	 }
	 
	 /**
	  * 获取去年的某一天
	  * @param day
	  * @return
	  */
	 public static Date lastYearByDate(int month,int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
        return cal.getTime();
	 }
	 
	/**
	* 获取某月有多少天
	* @param year
	* @param month
	* @return 天数
	*/
	public static Integer getMonthDayCount(int year,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 0);
		String d = DateUtils.getDateStr(calendar.getTime(), "d");
		return Integer.parseInt(d);
	}

	/*
	 *获取某年某月的最后一天的日期
	 * @param year
	 * @param month
     * @return
     */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * 获取某年某月第一天的日期
	 * @param year
	 * @param month
     * @return
     */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}


}
