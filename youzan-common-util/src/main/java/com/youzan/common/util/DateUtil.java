package com.youzan.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期操作工具类
 * @author zhuchaoyong
 */
public final class DateUtil {
	
	public static final SimpleDateFormat DEFAULT_FORMATTER = new SimpleDateFormat ("yyyy-MM-dd");
	public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat ("yyyyMMdd");
	public static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat YYYYMMDDHHMMSS2 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat YYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat YYYYMMDDHH = new SimpleDateFormat("yyyy-MM-dd HH");
	
	/** 
     * 将日期格式转换成yyyy-MM-dd的字符串格式 
     * 返回值如：2010-10-06
     * @param time 要转换的日期 
     * @return 
     */  
    public static String date2Str(Date time){
        return date2Str(time,DEFAULT_FORMATTER);
    }
    
    public static String date2Str(){
    	return date2Str(getCurDate(),DEFAULT_FORMATTER);
    }
    
    /** 
     * 将日期格式转换成指定格式的字符串格式 
     * @param time 要转换的日期 
     * @param format 要转换的格式
     * @return 
     */  
    public static String date2Str(Date time,SimpleDateFormat format){
    	if(null != format){
    		return format.format(time);
    	}
    	return null;
    }
    
    /** 
     * 将日期格式转换成指定格式的字符串格式 
     * @param time 要转换的日期 
     * @param format 要转换的格式
     * @return 
     */  
    public static String date2Str(Date time,String format){
    	if(StringUtils.isNotEmpty(format)){
    		SimpleDateFormat f = new SimpleDateFormat(format);
    		return f.format(time);
    	}
    	return null;
    }
    
    /** 
     * 将字符串转换成日期 
     * @param date 
     * @return 
     * @throws Exception 
     */  
    public static Date str2Date(String date) throws ParseException {
    	return str2Date(date,DEFAULT_FORMATTER);
    }
    
    /** 
     * 将字符串转换成指定格式日期
     * @param date 
     * @return 
     * @throws ParseException 
     * @throws Exception 
     */  
    public static Date str2Date(String date,SimpleDateFormat format) throws ParseException {
    	if(StringUtils.isNotEmpty(date)){
    		return format.parse(date);
    	}
    	return null;
    }
    
    /** 
     * 将字符串转换成指定格式日期
     * @param date 
     * @return 
     * @throws ParseException 
     * @throws Exception 
     */  
    public static Date str2Date(String date,String format) throws ParseException {
    	if(StringUtils.isNotEmpty(date)){
    		return str2Date(date,new SimpleDateFormat(format));
    	}
    	return null;
    }
    
    /**
	 * 获取日期
	 * @param timeType 时间类型，譬如：Calendar.DAY_OF_YEAR
	 * @param timenum  时间数字，譬如：-1 昨天，0 今天，1 明天
	 * @return 日期
	 */
	public static final Date getDateFromNow(int timeType, int timenum){
		Calendar cld = Calendar.getInstance();
		cld.set(timeType, cld.get(timeType) + timenum);
		return cld.getTime();
	}
	
	/**
	 * 获取日期
	 * @param timeType 时间类型，譬如：Calendar.DAY_OF_YEAR
	 * @param timenum  时间数字，譬如：-1 昨天，0 今天，1 明天
	 * @param format_string 时间格式，譬如："yyyy-MM-dd HH:mm:ss"
	 * @return 字符串
	 */
	public static final String getDateFromNow(int timeType, int timenum, String format_string){
		if (StringUtils.isNotEmpty(format_string)){
			Calendar cld = Calendar.getInstance();
			Date date = null;
			SimpleDateFormat df = new SimpleDateFormat(format_string);
			cld.set(timeType, cld.get(timeType) + timenum);
		    date = cld.getTime();
		    return df.format(date);
		}
		return null;
	}
	
	/**
	  * 计算是否是季度末
	  * @param date
	  * @return
	  */
	public static boolean isSeason(String date){
		boolean sign = false;
		if(StringUtils.isNotEmpty(date)){
			int getMonth = Integer.parseInt(date.substring(5,7));
		    if (getMonth==3)
		      sign = true;
		    if (getMonth==6)
		      sign = true;
		    if (getMonth==9)
		      sign = true;
		    if (getMonth==12)
		      sign = true;
		    return sign;
		}
	    return sign;
	}
	
	/**
	 * 功能描述：返回年份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 功能描述：返回年份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回年份
	 */
	public static int getYear(){
		return getYear(getCurDate());
	}

	/**
	 * 功能描述：返回月份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 功能描述：返回日份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能描述：返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 功能描述：返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 获取当前月份
	 * @return 返回字符串 格式：两位数
	 */
	public static String getCurMonth(){
	    int intMon = getMonth(getCurDate());
		if(intMon < 10){
			return "0" + String.valueOf(intMon);
		}else{
			return String.valueOf(intMon);
		}
	}
	
	/**
	 * 功能描述：返回字符型时间
	 * @param date Date 日期
	 * @return 返回字符型时间 HH:mm:ss 格式
	 */
	public static String getTime(Date date) {
		return date2Str(date, "HH:mm:ss");
	}
	
	/**
	 * 功能描述：返回字符型日期时间
	 * @param date Date 日期
	 * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
	 */
	public static String getDateTime(Date date) {
		return date2Str(date, "yyyy/MM/dd HH:mm:ss");
	}
	
	/**
	 * 功能描述：取得指定月份的第一天
	 * @param strdate String 字符型日期
	 * @return String yyyy-MM-dd 格式
	 * @throws ParseException 
	 */
	public static String getMonthBegin(String strdate) throws ParseException {
		Date date = str2Date(strdate);
		if(null != date){
			String str = date2Str(date, "yyyy-MM");
			if(StringUtils.isNotEmpty(str)){
				return str + "-01";
			}
		}
		return null;
	}
	
	/**
	 * 功能描述：取得指定月份的最后一天
	 * @param strdate String 字符型日期
	 * @return String 日期字符串 yyyy-MM-dd格式
	 * @throws ParseException 
	 */
	public static String getMonthEnd(String strdate) throws ParseException {
		if(StringUtils.isNotEmpty(strdate)){
			Date date = str2Date(getMonthBegin(strdate));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			return date2Str(calendar.getTime());
		}
		return null;
	}
    
    /**
     * 在当前时间的基础上加或减去year年
     * @param year
     * @return
     */
    public static Date year(int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }
    
    /**
     * 在指定的时间上加或减去几年
     * @param date
     * @param year
     * @return
     */
    public static Date year(Date date, int year) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.YEAR, year);
        return Cal.getTime();
    }
    
    /**
     * 在当前时间的基础上加或减去几月
     * @param month
     * @return
     */
    public static Date month(int month) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(Calendar.MONTH, month);
        return Cal.getTime();
    }

    /**
     * 在指定的时间上加或减去几月
     *
     * @param date
     * @param month
     * @return
     */
    public static Date month(Date date, int month) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.MONTH, month);
        return Cal.getTime();
    }
    
    /**
     * 获取前一天日期yyyMMdd
     * @see 经测试，针对闰年02月份或跨年等情况，该代码仍有效。测试代码如下
     * @see calendar.set(Calendar.YEAR, 2013);
     * @see calendar.set(Calendar.MONTH, 0);
     * @see calendar.set(Calendar.DATE, 1);
     * @see 测试时，将其放到<code>calendar.add(Calendar.DATE, -1);</code>前面即可
     * @return 返回的日期格式为yyyyMMdd
     */
    public static Date getYestoday(){
        return setDate(-1);
    }
    
    /**
     * 设置指定日期
     * @param date
     * @return
     */
    public static Date setDate(int date){
    	Calendar ca = Calendar.getInstance();
    	ca.add(Calendar.DATE, date);
        return ca.getTime();
    }
    
    /**
     * 在当前时间的基础上加或减去几天
     * @param day
     * @return
     */
    public static Date day(int day) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(Calendar.DAY_OF_YEAR, day);
        return Cal.getTime();
    }

    /**
     * 在指定的时间上加或减去几天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date day(Date date, int day) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.DAY_OF_YEAR, day);
        return Cal.getTime();
    }
    
    /**
     * 在当前时间的基础上加或减去几小时-支持浮点数
     * @param hour
     * @return
     */
    public static Date hour(float hour) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(java.util.Calendar.MINUTE, (int) (hour * 60));
        return Cal.getTime();
    }

    /**
     * 在制定的时间上加或减去几小时-支持浮点数
     * @param date
     * @param hour
     * @return
     */
    public static Date hour(Date date, float hour) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.MINUTE, (int) (hour * 60));
        return Cal.getTime();
    }

    /**
     * 在当前时间的基础上加或减去几分钟
     * @param minute
     * @return
     */
    public static Date minute(int minute) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(java.util.Calendar.MINUTE, minute);
        return Cal.getTime();
    }

    /**
     * 在制定的时间上加或减去几分钟
     * @param date
     * @param minute
     * @return
     */
    public static Date minute(Date date, int minute) {
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(date);
        Cal.add(java.util.Calendar.MINUTE, minute);
        return Cal.getTime();
    }

    /**
     * 判断字符串是否为日期字符串
     * @param date 日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        try {
        	str2Date(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 获取当前系统日期
     * @return
     */
    public static Date getCurDate(){
    	long c = System.currentTimeMillis();
    	return new Date(c);
    }
    
    /**
     * 获取当前系统日期
     * 
     * @author  Evan.zhu
     * @return [Parameters description]
     * @return String [Return type description]
     * @see [Related classes#Related methods#Related properties]
     */
    public static String getNowDate(){
    	return date2Str(getCurDate());
    }
    
    /**
     * 返回当前的时间戳
     * @return
     */
    public static String getCurrentTimeStamp(){
    	return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    /**
	 *得到格式化后的系统当前日期
	 *@param strScheme 格式模式字符串
	 *@return 格式化后的系统当前时间，如果有异常产生，返回空串""
	 *@see java.util.SimpleDateFormat
	 */
	public static final String getNowDateTime(String strScheme) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
			return sdf.format(getCurDate());
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 返回星期对应数字
	 * @param strWeek
	 * @return
	 */
	@SuppressWarnings("unused")
	public static final int getWeekNum(String strWeek) {
		int returnValue = 0;
		if (strWeek.equals("Mon")) {
			returnValue = 1;
		} else if (strWeek.equals("Tue")) {
			returnValue = 2;
		} else if (strWeek.equals("Wed")) {
			returnValue = 3;
		} else if (strWeek.equals("Thu")) {
			returnValue = 4;
		} else if (strWeek.equals("Fri")) {
			returnValue = 5;
		} else if (strWeek.equals("Sat")) {
			returnValue = 6;
		} else if (strWeek.equals("Sun")) {
			returnValue = 0;
		} else if (strWeek == null) {
			returnValue = 0;
		}
		return returnValue;
	}

	/**
	 * 获取当前日期
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static final String getNowDateTime() {
		try {
			return date2Str(getCurDate(), YYYYMMDDHHMMSS);
		} catch (Exception e) {
			
		}
		return null;
	}
    
    /**
     * 时间date1和date2的时间差-单位秒
     * @param date1
     * @param date2
     * @return 秒
     */
    public static long subtract(Date date1, Date date2) {
        long cha = (date2.getTime() - date1.getTime()) / 1000;
        return cha;
    }
    
    /**
     * 时间date1和date2的时间差-单位秒
     * @param date1
     * @param date2
     * @return 秒
     */
    public static long subtract(String date1, String date2) {
        long rs = 0;
        try {
            Date start = str2Date(date1,YYYYMMDDHHMMSS);
            Date end = str2Date(date2,YYYYMMDDHHMMSS);
            long cha = (end.getTime() - start.getTime()) / 1000;
            rs = cha;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    /**
     * 时间date1和date2的时间差 -单位分钟
     * @param date1
     * @param date2
     * @return 分钟
     */
    public static int subtractMinute(String date1, String date2) {
        return subtractMinute(date1,date2,YYYYMMDDHHMMSS);
    }
    
    /**
     * 时间date1和date2的时间差 -单位分钟
     * @param date1
     * @param date2
     * @return 分钟
     */
    public static int subtractMinute(String date1, String date2,SimpleDateFormat format) {
        int rs = 0;
        try {
            Date start = str2Date(date1,format);
            Date end = str2Date(date2,format);
            long cha = (end.getTime() - start.getTime()) / 1000;
            rs = (int) cha / (60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    /**
     * 时间date1和date2的时间差 -单位分钟
     * @param date1
     * @param date2
     * @return 分钟
     */
    public static int subtractMinute(String date1, String date2,String format) {
        return subtractMinute(date1,date2,new SimpleDateFormat(format));
    }
    
    /**
     * 时间date1和date2的时间差-单位分钟
     * @param date1
     * @param date2
     * @return 分钟
     */
    public static int subtractMinute(Date date1, Date date2) {
        long cha = date2.getTime() - date1.getTime();
        return (int) cha / (1000 * 60);
    }
    
    /**
     * 时间date1和date2的时间差-单位小时
     *
     * @param date1
     * @param date2
     * @return 小时
     */
    public static int subtractHour(Date date1, Date date2) {
        long cha = (date2.getTime() - date1.getTime()) / 1000;
        return (int) cha / (60 * 60);
    }

    /**
     * 时间date1和date2的时间差-单位小时
     *
     * @param date1
     * @param date2
     * @return 小时
     */
    public static int subtractHour(String date1, String date2,SimpleDateFormat format) {
        int rs = 0;
        try {
            Date start = str2Date(date1,format);
            Date end = str2Date(date2,format);
            long cha = (end.getTime() - start.getTime()) / 1000;
            rs = (int) cha / (60 * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    /**
     * 时间date1和date2的时间差-单位小时
     *
     * @param date1
     * @param date2
     * @return 小时
     */
    public static int subtractHour(String date1, String date2,String format) {
        return subtractHour(date1,date2,new SimpleDateFormat(format));
    }
    
    /**
     * 时间date1和date2的时间差-单位小时
     *
     * @param date1
     * @param date2
     * @return 小时
     */
    public static int subtractHour(String date1, String date2) {
        return subtractHour(date1,date2,YYYYMMDDHHMMSS);
    }


    /**
     * 时间date1和date2的时间差-单位天
     *
     * @param date1
     * @param date2
     * @return 天
     */
    public static int subtractDay(String date1, String date2,SimpleDateFormat format) {
        int rs = 0;
        try {
            Date start = str2Date(date1,format);
            Date end = str2Date(date2,format);
            long sss = (end.getTime() - start.getTime()) / 1000;
            rs = (int) sss / (60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    /**
     * 时间date1和date2的时间差-单位天
     * @param date1
     * @param date2
     * @return 天
     */
    public static int subtractDay(String date1, String date2,String format) {
        return subtractDay(date1,date2,new SimpleDateFormat(format));
    }
    
    /**
     * 时间date1和date2的时间差-单位天
     * @param date1
     * @param date2
     * @return 天
     */
    public static int subtractDay(String date1, String date2) {
        int rs = 0;
        try {
            Date start = str2Date(date1, YYYYMMDDHHMMSS);
            Date end = str2Date(date2, YYYYMMDDHHMMSS);
            long sss = (end.getTime() - start.getTime()) / 1000;
            rs = (int) sss / (60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 时间date1和date2的时间差-单位天
     *
     * @param date1
     * @param date2
     * @return 天
     */
    public static int subtractDay(Date date1, Date date2) {
        long cha = date2.getTime() - date1.getTime();
        return (int) cha / (1000 * 60 * 60 * 24);
    }

    /**
     * 时间date1和date2的时间差-单位月
     * @param date1
     * @param date2
     * @return 月
     */
    public static int subtractMonth(String date1, String date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(str2Date(date1));
            c2.setTime(str2Date(date2));
            int year1 = c1.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH);
            int year2 = c2.get(Calendar.YEAR);
            int month2 = c2.get(Calendar.MONTH);
            if (year1 == year2) {
                result = month2 - month1;
            } else {
                result = 12 * (year2 - year1) + month2 - month1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    /**
     * 时间date1和date2的时间差-单位月
     * @param date1
     * @param date2
     * @return 月
     */
    public static int subtractMonth(Date date1, Date date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        if (year1 == year2) {
            result = month2 - month1;
        } else {
            result = 12 * (year2 - year1) + month2 - month1;
        }
        return result;
    }

    /**
     * 时间date1和date2的时间差-单位年
     *
     * @param date1
     * @param date2
     * @return 年
     */
    public static int subtractYear(String date1, String date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(str2Date(date1));
            c2.setTime(str2Date(date2));
            int year1 = c1.get(Calendar.YEAR);
            int year2 = c2.get(Calendar.YEAR);
            result = year2 - year1;
        } catch (ParseException e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    /**
     * 时间date1和date2的时间差-单位年
     *
     * @param date1
     * @param date2
     * @return 年
     */
    public static int subtractYear(Date date1, Date date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        result = year2 - year1;
        return result;
    }
    
    /**
     * 获取俩个时间的查结果用时秒表示
     * @param date1
     * @param date2
     * @return 几小时:几分钟:几秒钟
     * @Summary:此处可以讲计算结果包装成一个结构体返回便于格式化
     */
    public static String subtractTime(String date1, String date2) {
        String result = "";
        try {
            Date start = str2Date(date1,YYYYMMDDHHMMSS);
            Date end = str2Date(date2,YYYYMMDDHHMMSS);
            long sss = (end.getTime() - start.getTime()) / 1000;
            int hh = (int) sss / (60 * 60);
            int mm = (int) (sss - hh * 60 * 60) / (60);
            int ss = (int) (sss - hh * 60 * 60 - mm * 60);
            result = hh + ":" + mm + ":" + ss;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 获取俩个时间的查结果用时秒表示
     * @param date1
     * @param date2
     * @return 几天-几小时:几分钟:几秒钟
     * @Summary:此处可以讲计算结果包装成一个结构体返回便于格式化
     */
    public static String subtractDate(String date1, String date2) {
        String result = "";
        try {
        	Date start = str2Date(date1,YYYYMMDDHHMMSS);
            Date end = str2Date(date2,YYYYMMDDHHMMSS);
            long sss = (end.getTime() - start.getTime()) / 1000;
            int dd = (int) sss / (60 * 60 * 24);
            int hh = (int) (sss - dd * 60 * 60 * 24) / (60 * 60);
            int mm = (int) (sss - dd * 60 * 60 * 24 - hh * 60 * 60) / (60);
            int ss = (int) (sss - dd * 60 * 60 * 24 - hh * 60 * 60 - mm * 60);
            result = dd + "-" + hh + ":" + mm + ":" + ss;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取俩个时间之前的相隔的天数
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public  static int subDay(Date startTime, Date endTime) {
        int days = 0;
        Calendar can1 = Calendar.getInstance();
        can1.setTime(startTime);
        Calendar can2 = Calendar.getInstance();
        can2.setTime(endTime);
        int year1 = can1.get(Calendar.YEAR);
        int year2 = can2.get(Calendar.YEAR);

        Calendar can = null;
        if (can1.before(can2)) {
            days -= can1.get(Calendar.DAY_OF_YEAR);
            days += can2.get(Calendar.DAY_OF_YEAR);
            can = can1;
        } else {
            days -= can2.get(Calendar.DAY_OF_YEAR);
            days += can1.get(Calendar.DAY_OF_YEAR);
            can = can2;
        }
        for (int i = 0; i < Math.abs(year2 - year1); i++) {
            days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
            can.add(Calendar.YEAR, 1);
        }

        return days;
    }

    /**
     * 获取俩个时间之前的相隔的天数
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public  static int subDay(String startTime, String endTime) {
        int days = 0;
        try {
            Date date1 = str2Date(startTime,YYYYMMDDHHMMSS);
            Date date2 = str2Date(endTime,YYYYMMDDHHMMSS);
            Calendar can1 = Calendar.getInstance();
            can1.setTime(date1);
            Calendar can2 = Calendar.getInstance();
            can2.setTime(date2);
            int year1 = can1.get(Calendar.YEAR);
            int year2 = can2.get(Calendar.YEAR);

            Calendar can = null;
            if (can1.before(can2)) {
                days -= can1.get(Calendar.DAY_OF_YEAR);
                days += can2.get(Calendar.DAY_OF_YEAR);
                can = can1;
            } else {
                days -= can2.get(Calendar.DAY_OF_YEAR);
                days += can1.get(Calendar.DAY_OF_YEAR);
                can = can2;
            }
            for (int i = 0; i < Math.abs(year2 - year1); i++) {
                days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
                can.add(Calendar.YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
    
    /**
    * 将时间段转化成用于显示的形式，如3600秒转化成1小时
    * @param time
    * @return
    */
    public static String time2View(Long time){
    	if(null == time){
    		return "";
    	}
    	if (time >= 0 && time < 60){
    		return time + "秒";
    	}else if (time >= 60 && time < 60 * 60){
    		return time / 60 + "分钟";
    	}else if (time >= 60 * 60 && time < 60 * 60 * 24){
    		return time / (60 * 60) + "小时";
    	}else if (time >= 60 * 60 * 24){
    		return time / (60 * 60 * 24) + "天";
        }else{
            return "";
        }
    }
    
    /**
     * 判断是否在某个时间段内
     * @param startTime
     * @param endTime
     * @param date
     * @return
     * @throws Exception 
     */
    public static boolean between(String startTime,String endTime,Date date) throws ParseException {
        return between(str2Date(startTime),str2Date(endTime),date);
    }

    /**
     * 判断在某个时间内
     * @param startTime
     * @param endTime
     * @param date
     * @return
     */
    public static boolean between(Date startTime,Date endTime,Date date){
        return date.after(startTime) && date.before(endTime);
    }
    
    /**
   	 * @param date
   	 * @return 判断日期是否为工作日(周六和周日为非工作日)
   	 */
   	public static boolean isWorkDay(Date date){
   		Calendar calendar = Calendar.getInstance();
   		calendar.setTime(date);
   		int week = calendar.get(Calendar.DAY_OF_WEEK);
   		if(week == Calendar.SUNDAY || week == Calendar.SATURDAY){
   			return false;
   		}else{
   			return true;
   		}
   	}
   	
   	/**			
  	* 比较2个格式为yyyy-MM-dd的日期<br>
  	* 若d1小于d2返回true<br>
  	* d1=2007-10-01,d2=2007-10-15,则返回true
  	* @return 
  	*/  
   public static boolean after(String d1,String d2){
   		try {
   			Date dt1 = str2Date(d1);
   	   		Date dt2 = str2Date(d2);
   	   		return dt1.getTime() < dt2.getTime();	
		} catch (Exception e) {
			return false;
		}
   }
   
   /**
    * 获得当天0点时间
    * @author  Evan.zhu
    * @return [Parameters description]
    * @return Date [Return type description]
    * @see [Related classes#Related methods#Related properties]
    */
	public static Date getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
 
 
	}
	/**
	 * 获得昨天0点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getYesterdaymorning() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getTimesmorning().getTime()-3600*24*1000);
		return cal.getTime();
	}
	
	/**
	 * 获得当天近7天时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getWeekFromNow() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis( getTimesmorning().getTime()-3600*24*1000*7);
		return cal.getTime();
	}
 
	/**
	 * 获得当天24点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
 
	/**
	 * 获得本周一0点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}
 
	/**
	 * 获得本周日24点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}
 
	/**
	 * 获得本月第一天0点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
 
	/**
	 * 获得本月最后一天24点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
	}
 
	/**
	 * 上月初0点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getLastMonthStartMorning() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesMonthmorning());
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}
	
	/**
	 * 获取当前季度
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return int [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static int getCurrentQuarter(){
		Calendar c = Calendar.getInstance();
		int m = c.get(Calendar.MONTH) + 1;
		return (m % 3 == 0) ? (m / 3) : (m / 3 + 1);
	}
 
	/**
	 * 本季度开始点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
 
	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentQuarterStartTime());
		cal.add(Calendar.MONTH, 3);
		return cal.getTime();
	}
 
 
	/**
	 * 本年开始点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getCurrentYearStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
		return cal.getTime();
	}
 
	/**
	 * 本年结束点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getCurrentYearEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentYearStartTime());
		cal.add(Calendar.YEAR, 1);
		return cal.getTime();
	}
 
	/**
	 * 上年开始点时间
	 * @author  Evan.zhu
	 * @return [Parameters description]
	 * @return Date [Return type description]
	 * @see [Related classes#Related methods#Related properties]
	 */
	public static Date getLastYearStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentYearStartTime());
		cal.add(Calendar.YEAR, -1);
		return cal.getTime();
	}
    
    public static void main(String[] args) {
		System.out.println(getYestoday());
	}
}
