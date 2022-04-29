/*
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zy.mylib.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author ASUS
 */
object DateUtils {
  var DATE_FORMAT = "yyyy-MM-dd"
  var DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
  var DATE_FORMAT_CHINESE = "yyyy年M月d日"

  /**
   * 日期解析
   * @param date
   * @param format
   * @return
   * @throws ParseException
   */
  @Throws(ParseException::class)
  fun parse(date: String?, format: String?): Date {
    val sdf = SimpleDateFormat(format)
    return sdf.parse(date)
  }

  /**
   * 返回格式：2007-08-14
   *
   * @return
   */
  val today: String
    get() {
      var time = ""
      time = getToday(DATE_FORMAT)
      return time
    }

  fun between(start: Date, end: Date): String {
    val span = end.time - start.time
    val day = (span / (24 * 60 * 60 * 1000)).toInt()
    val hh = (span % (24 * 60 * 60 * 1000) / (60 * 60 * 1000)).toInt()
    val mm = (span % (60 * 60 * 1000) / (60 * 1000)).toInt()
    val ss = (span % (60 * 1000) / 1000).toInt()
    return (if (day > 0) day.toString() + "天" else "") + (if (hh > 0) hh.toString() + "小时" else "") + (if (mm > 0) mm.toString() + "分" else "") + ss + "秒"
  }

  /**
   * @param format 根据指定的格式时间类型返回当前时间
   * @return
   */
  fun getToday(format: String?): String {
    return getDateStr(Calendar.getInstance().time, format)
  }

  /**
   * 格式yyyy-MM-dd HH:mm:ss
   *
   * @return
   */
  val timestamp: String
    get() = getToday(DATE_TIME_FORMAT)

  /**
   * @return
   */
  val nowTime: String
    get() = getToday(DATE_TIME_FORMAT)

  /**
   * 日期转字符
   *
   * @param date
   * @param format
   * @return
   */
  fun getDateStr(date: Date?, format: String?): String {
    val df = SimpleDateFormat(format)
    return df.format(date)
  }

  /**
   * @param millis
   * @return
   */
  fun parseMills(millis: Long): Date {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    return calendar.time
  }

  /**
   * 获取当前年的第一天
   *
   * @return
   */
  fun getYearFirst(format: String?): String {
    val df = SimpleDateFormat(format)
    val currCalendar = Calendar.getInstance()
    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar[Calendar.YEAR] = currCalendar[Calendar.YEAR]
    return df.format(calendar.time)
  }

  /**
   * 获取当前月的第一天
   *
   * @param format
   * @return
   */
  fun getFirstDayMonth(format: String?): String {
    val df = SimpleDateFormat(format)
    val calendar = Calendar.getInstance()
    calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH]] = 1
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    return df.format(calendar.time)
  }

  val curYear: String
    get() = getToday("yyyy")
  val curMonth: String
    get() = getToday("MM")
  val curDay: String
    get() = getToday("dd")

  /**
   * 获取当前周的第一天
   *
   * @return
   */
  fun getFirstDayOfWeek(format: String?): String {
    val df = SimpleDateFormat(format)
    val calendar = Calendar.getInstance()
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar[Calendar.DAY_OF_WEEK] = calendar.firstDayOfWeek
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    return df.format(calendar.time)
  }

  /**
   * 计算两日期相差月份个数
   *
   * @param date1
   * @param date2
   * @return
   * @throws ParseException
   */
  fun getMonthSpace(date1: String?, date2: String?): Int {
    var result = 0
    try {
      val sdf = SimpleDateFormat("yyyy-MM-dd")
      val c = Calendar.getInstance()
      c.time = sdf.parse(date2)
      val year1 = c[Calendar.YEAR]
      val month1 = c[Calendar.MONTH]
      c.time = sdf.parse(date1)
      val year2 = c[Calendar.YEAR]
      val month2 = c[Calendar.MONTH]
      result = if (year1 == year2) {
        month1 - month2
      } else {
        12 * (year1 - year2) + month1 - month2
      }
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return result
  }

  /**
   * 获取当前星期几
   *
   * @param pTime
   * @return
   */
  @Throws(Exception::class)
  fun dayForWeek(pTime: String?): Int {
    val format = SimpleDateFormat("yyyy-MM-dd")
    val c = Calendar.getInstance()
    c.time = format.parse(pTime)
    var dayForWeek = 0
    dayForWeek = if (c[Calendar.DAY_OF_WEEK] == 1) {
      7
    } else {
      c[Calendar.DAY_OF_WEEK] - 1
    }
    return dayForWeek
  }

  /**
   * 计算两个日期之间相差的天数
   *
   * @param smdate 较小的时间
   * @param bdate  较大的时间
   * @return 相差天数
   * @throws ParseException
   */
  @Throws(ParseException::class)
  fun daysBetween(smdate: String?, bdate: String?): Int {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val cal = Calendar.getInstance()
    cal.time = sdf.parse(smdate)
    val time1 = cal.timeInMillis
    cal.time = sdf.parse(bdate)
    val time2 = cal.timeInMillis
    val between_days = (time2 - time1) / (1000 * 3600 * 24)
    return between_days.toString().toInt()
  }

  /**
   * 计算两个日期之间的工作日
   *
   * @param strStartDate
   * @param strEndDate
   * @return
   */
  fun getDutyDays(strStartDate: String?, strEndDate: String?): Int {
    val df = SimpleDateFormat("yyyy-MM-dd")
    var startDate: Date? = null
    var endDate: Date? = null
    try {
      startDate = df.parse(strStartDate)
      endDate = df.parse(strEndDate)
    } catch (e: ParseException) {
      println("非法的日期格式,无法进行转换")
      e.printStackTrace()
    }
    var result = 0
    while (startDate!!.compareTo(endDate) <= 0) {
      if (startDate.day != 6 && startDate.day != 0) {
        result++
      }
      startDate.date = startDate.date + 1
    }
    return result
  }

  /**
   * 获取上个月的某一天
   *
   * @param date
   * @param day
   * @return
   */
  fun getLastDate(date: String?, day: Int): Date {
    val cal = Calendar.getInstance()
    try {
      val sdf = SimpleDateFormat("yyyy-MM")
      cal.time = sdf.parse(date)
      cal.add(Calendar.MONTH, -1)
      cal[Calendar.DAY_OF_MONTH] = day //设置上月几号
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return cal.time
  }

  /**
   * 获取下个月的某一天
   *
   * @param date
   * @param day
   * @return
   */
  fun getNextDate(date: Date?, day: Int): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val cal = Calendar.getInstance()
    cal.time = date
    cal.add(Calendar.MONTH, +1)
    cal[Calendar.DAY_OF_MONTH] = day //设置上月几号
    return sdf.format(cal.time)
  }

  /**
   * 获取当前月的某一天
   *
   * @param date
   * @param day
   * @return
   */
  fun getCurrentDate(date: String?, day: Int): Date {
    val cal = Calendar.getInstance()
    try {
      val sdf = SimpleDateFormat("yyyy-MM")
      cal.time = sdf.parse(date)
      cal[Calendar.DAY_OF_MONTH] = day //设置上月几号
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return cal.time
  }

  /**
   * 获取当前月的某一天
   *
   * @param date
   * @param day
   * @return
   */
  fun getCurrentDate(date: Date?, day: Int): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val cal = Calendar.getInstance()
    cal.time = date
    cal[Calendar.DAY_OF_MONTH] = day //设置上月几号
    return sdf.format(cal.time)
  }

  /**
   * 获取某年第一天日期
   *
   * @param year 年份
   * @return Date
   */
  fun getYearFirst(year: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar[Calendar.YEAR] = year
    return calendar.time
  }

  /**
   * 获取某年最后一天日期
   *
   * @param year 年份
   * @return Date
   */
  fun getYearLast(year: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar[Calendar.YEAR] = year
    calendar.roll(Calendar.DAY_OF_YEAR, -1)
    return calendar.time
  }

  fun lastYearDate(date: String?): Date {
    val cal = Calendar.getInstance()
    try {
      val sdf = SimpleDateFormat("yyyy-MM-dd")
      cal.time = sdf.parse(date)
      cal[Calendar.YEAR] = cal[Calendar.YEAR] - 1
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return cal.time
  }

  /**
   * 获取去年的某一天
   *
   * @param day
   * @return
   */
  fun lastYearByDay(month: Int, day: Int): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val cal = Calendar.getInstance()
    cal.add(Calendar.YEAR, -1)
    cal[Calendar.MONTH] = month - 1
    cal[Calendar.DATE] = day
    return sdf.format(cal.time)
  }

  /**
   * 获取去年的某一天
   *
   * @param day
   * @return
   */
  fun lastYearByDate(month: Int, day: Int): Date {
    val cal = Calendar.getInstance()
    cal.add(Calendar.YEAR, -1)
    cal[Calendar.MONTH] = month - 1
    cal[Calendar.DATE] = day
    return cal.time
  }

  /**
   * 获取某月有多少天
   *
   * @param year
   * @param month
   * @return 天数
   */
  fun getMonthDayCount(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar[year, month] = 0
    val d = getDateStr(calendar.time, "d")
    return d.toInt()
  }

  /**
   * 获取某年某月的最后一天的日期
   *
   * @param year
   * @param month
   * @return
   */
  fun getLastDayOfMonth(year: Int, month: Int): String {
    val cal = Calendar.getInstance()
    cal[Calendar.YEAR] = year
    cal[Calendar.MONTH] = month
    cal[Calendar.DAY_OF_MONTH] = cal.getActualMaximum(Calendar.DATE)
    return SimpleDateFormat("yyyy-MM-dd").format(cal.time)
  }

  /**
   * 获取某年某月第一天的日期
   *
   * @param year
   * @param month
   * @return
   */
  fun getFirstDayOfMonth(year: Int, month: Int): String {
    val cal = Calendar.getInstance()
    cal[Calendar.YEAR] = year
    cal[Calendar.MONTH] = month
    cal[Calendar.DAY_OF_MONTH] = cal.getMinimum(Calendar.DATE)
    return SimpleDateFormat("yyyy-MM-dd").format(cal.time)
  }

  /**
   * 将字符串日期转换为日期格式
   *
   * @param datestr
   * @return
   */
  fun stringToDate(datestr: String?): Date? {
    if (datestr == null || datestr == "") {
      return null
    }
    var date = Date()
    val df = SimpleDateFormat(DATE_FORMAT)
    date = try {
      df.parse(datestr)
    } catch (e: ParseException) {
      stringToDate(datestr, "yyyyMMdd")
    }
    return date
  }

  /**
   * 将字符串日期转换为日期格式
   * 自定義格式
   *
   * @param datestr
   * @return
   */
  fun stringToDate(datestr: String?, dateformat: String?): Date {
    var date = Date()
    val df = SimpleDateFormat(dateformat)
    try {
      date = df.parse(datestr)
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return date
  }

  /**
   * 获取日期的DAY值
   *
   * @param date 输入日期
   * @return
   */
  fun getDayOfDate(date: Date?): Int {
    var d = 0
    val cd = Calendar.getInstance()
    cd.time = date
    d = cd[Calendar.DAY_OF_MONTH]
    return d
  }

  /**
   * 获取日期的MONTH值
   *
   * @param date 输入日期
   * @return
   */
  fun getMonthOfDate(date: Date?): Int {
    var m = 0
    val cd = Calendar.getInstance()
    cd.time = date
    m = cd[Calendar.MONTH] + 1
    return m
  }

  /**
   * 获取日期的YEAR值
   *
   * @param date 输入日期
   * @return
   */
  fun getYearOfDate(date: Date?): Int {
    var y = 0
    val cd = Calendar.getInstance()
    cd.time = date
    y = cd[Calendar.YEAR]
    return y
  }

  /**
   * 获取星期几
   *
   * @param date 输入日期
   * @return
   */
  fun getWeekOfDate(date: Date?): Int {
    var wd = 0
    val cd = Calendar.getInstance()
    cd.time = date
    wd = cd[Calendar.DAY_OF_WEEK] - 1
    return wd
  }

  /**
   * 获取输入日期的当月第一天
   *
   * @param date 输入日期
   * @return
   */
  fun getFirstDayOfMonth(date: Date?): Date {
    val cd = Calendar.getInstance()
    cd.time = date
    cd[Calendar.DAY_OF_MONTH] = 1
    return cd.time
  }

  /**
   * 获得输入日期的当月最后一天
   *
   * @param date
   */
  fun getLastDayOfMonth(date: Date?): Date {
    return addDay(getFirstDayOfMonth(addMonth(date, 1)), -1)
  }

  /**
   * 判断是否是闰年
   *
   * @param date 输入日期
   * @return 是true 否false
   */
  fun isLeapYEAR(date: Date?): Boolean {
    val cd = Calendar.getInstance()
    cd.time = date
    val year = cd[Calendar.YEAR]
    return if ((year % 4 == 0 && year % 100 != 0) or (year % 400 == 0)) {
      true
    } else {
      false
    }
  }

  /**
   * 根据整型数表示的年月日，生成日期类型格式
   *
   * @param year  年
   * @param month 月
   * @param day   日
   * @return
   */
  fun getDateByYMD(year: Int, month: Int, day: Int): Date {
    val cd = Calendar.getInstance()
    cd[year, month - 1] = day
    return cd.time
  }

  /**
   * 获取年周期对应日
   *
   * @param date  输入日期
   * @param iyear 年数  負數表示之前
   * @return
   */
  fun getYearCycleOfDate(date: Date?, iyear: Int): Date {
    val cd = Calendar.getInstance()
    cd.time = date
    cd.add(Calendar.YEAR, iyear)
    return cd.time
  }

  /**
   * 获取月周期对应日
   *
   * @param date 输入日期
   * @param i
   * @return
   */
  fun getMonthCycleOfDate(date: Date?, i: Int): Date {
    val cd = Calendar.getInstance()
    cd.time = date
    cd.add(Calendar.MONTH, i)
    return cd.time
  }

  /**
   * 计算 fromDate 到 toDate 相差多少年
   *
   * @param fromDate
   * @param toDate
   * @return 年数
   */
  fun getYearByMinusDate(fromDate: Date?, toDate: Date?): Int {
    val df = Calendar.getInstance()
    df.time = fromDate
    val dt = Calendar.getInstance()
    dt.time = toDate
    return dt[Calendar.YEAR] - df[Calendar.YEAR]
  }

  /**
   * 计算 fromDate 到 toDate 相差多少个月
   *
   * @param fromDate
   * @param toDate
   * @return 月数
   */
  fun getMonthByMinusDate(fromDate: Date?, toDate: Date?): Int {
    val df = Calendar.getInstance()
    df.time = fromDate
    val dt = Calendar.getInstance()
    dt.time = toDate
    return dt[Calendar.YEAR] * 12 + dt[Calendar.MONTH] -
        (df[Calendar.YEAR] * 12 + df[Calendar.MONTH])
  }

  /**
   * 计算 fromDate 到 toDate 相差多少天
   *
   * @param fromDate
   * @param toDate
   * @return 天数
   */
  fun getDayByMinusDate(fromDate: Any?, toDate: Any?): Long {
    val f = chgObject(fromDate)
    val t = chgObject(toDate)
    val fd = f!!.time
    val td = t!!.time
    return (td - fd) / (24L * 60L * 60L * 1000L)
  }

  /**
   * 计算年龄
   *
   * @param birthday 生日日期
   * @param calcDate 要计算的日期点
   * @return
   */
  fun calcAge(birthday: Date?, calcDate: Date?): Int {
    val cYear = getYearOfDate(calcDate)
    val cMonth = getMonthOfDate(calcDate)
    val cDay = getDayOfDate(calcDate)
    val bYear = getYearOfDate(birthday)
    val bMonth = getMonthOfDate(birthday)
    val bDay = getDayOfDate(birthday)
    return if (cMonth > bMonth || cMonth == bMonth && cDay > bDay) {
      cYear - bYear
    } else {
      cYear - 1 - bYear
    }
  }

  /**
   * 从身份证中获取出生日期
   *
   * @param idno 身份证号码
   * @return
   */
  fun getBirthDayFromIDCard(idno: String): String {
    val cd = Calendar.getInstance()
    if (idno.length == 15) {
      cd[Calendar.YEAR] = Integer.valueOf("19" + idno.substring(6, 8))
        .toInt()
      cd[Calendar.MONTH] = Integer.valueOf(idno.substring(8, 10))
        .toInt() - 1
      cd[Calendar.DAY_OF_MONTH] = Integer.valueOf(idno.substring(10, 12)).toInt()
    } else if (idno.length == 18) {
      cd[Calendar.YEAR] = Integer.valueOf(idno.substring(6, 10))
        .toInt()
      cd[Calendar.MONTH] = Integer.valueOf(idno.substring(10, 12))
        .toInt() - 1
      cd[Calendar.DAY_OF_MONTH] = Integer.valueOf(idno.substring(12, 14)).toInt()
    }
    return getDateStr(cd.time, DATE_FORMAT)
  }

  /**
   * 在输入日期上增加（+）或减去（-）天数
   *
   * @param date 输入日期
   * @param iday 要增加或减少的天数
   */
  fun addDay(date: Date?, iday: Int): Date {
    val cd = Calendar.getInstance()
    cd.time = date
    cd.add(Calendar.DAY_OF_MONTH, iday)
    return cd.time
  }

  /**
   * 在输入日期上增加（+）或减去（-）月份
   *
   * @param date   输入日期
   * @param imonth 要增加或减少的月分数
   */
  fun addMonth(date: Date?, imonth: Int): Date {
    val cd = Calendar.getInstance()
    cd.time = date
    cd.add(Calendar.MONTH, imonth)
    return cd.time
  }

  /**
   * 在输入日期上增加（+）或减去（-）年份
   *
   * @param date  输入日期
   * @param iyear 要增加或减少的年数
   */
  fun addYear(date: Date?, iyear: Int): Date {
    val cd = Calendar.getInstance()
    cd.time = date
    cd.add(Calendar.YEAR, iyear)
    return cd.time
  }

  /**
   * 將OBJECT類型轉換為Date
   *
   * @param date
   * @return
   */
  fun chgObject(date: Any?): Date? {
    if (date != null && date is Date) {
      return date
    }
    return if (date != null && date is String) {
      stringToDate(date as String?)
    } else null
  }

  fun getAgeByBirthday(date: String?): Long {
    val birthday = stringToDate(date, "yyyy-MM-dd")
    val sec = System.currentTimeMillis() - birthday.time
    return sec / (1000 * 60 * 60 * 24) / 365
  }
}
