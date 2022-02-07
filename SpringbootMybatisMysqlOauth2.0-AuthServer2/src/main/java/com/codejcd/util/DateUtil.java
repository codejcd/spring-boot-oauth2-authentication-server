package com.codejcd.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date Util
 *
 */
public class DateUtil {

	public static final String YYYYMMDDHHmm = "yyyyMMddHHmm";
	
    public static Date getDate(String dateString, String format) throws Exception{
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	ParsePosition pos = new ParsePosition(0);
    	return formatter.parse(dateString, pos);
    }
	
	public static String getNow(String fromFormat) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(fromFormat);
		return formatter.format(today);
	}
	
	public static Date getNowDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
    public static String addMinute(String dateString, String format, int minute){
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        cal.setTime(formatter.parse(dateString, pos));
        cal.add(Calendar.MINUTE,minute);

		return formatter.format(cal.getTime());
    }
	
    public static String addHour(String dateString, String format, int hours){
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        cal.setTime(formatter.parse(dateString, pos));
        cal.add(Calendar.HOUR,hours);

		return formatter.format(cal.getTime());
    }
	
    public static String addDate(String dateString, String format, int days){
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        cal.setTime(formatter.parse(dateString, pos));
        cal.add(Calendar.DATE,days);

		return formatter.format(cal.getTime());
    }
    
    public static boolean isBefore(Date from, Date to) {
		if (from == null || to == null) {
			return false;
		}
		if (to.before(from)) {
			return false;
		}
		return true;
	}

}
