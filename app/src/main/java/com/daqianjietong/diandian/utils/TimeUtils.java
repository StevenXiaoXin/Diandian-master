package com.daqianjietong.diandian.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final SimpleDateFormat DATE_FORMAT_DATE2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	public static Date getDateByStr(String dd) {

		Date date;
		try {
			date = DATE_FORMAT_DATE.parse(dd);
		} catch (java.text.ParseException e) {
			date = null;
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateByStr2(String dd) {

		Date date;
		try {
			date = DATE_FORMAT_DATE2.parse(dd);
		} catch (java.text.ParseException e) {
			date = null;
			e.printStackTrace();
		}
		return date;
	}


	public static String getTime(long mill){
		Date nowdata=new Date(mill*1000);
		return DATE_FORMAT_DATE.format(nowdata);
	}

	public static String getCurTime(){
		Date date=new Date(System.currentTimeMillis());
		return DATE_FORMAT_DATE2.format(date);
	}

	public static String getCurNextHourTime(){
		Date date=new Date(System.currentTimeMillis()+1000*60*60);
		return DATE_FORMAT_DATE2.format(date);
	}

	public static String getMaxTime(){
		return DATE_FORMAT_DATE2.format(getDateByStr2("2050-01-01 00:00"));
	}

	public static String getShowTime(String time){
		SimpleDateFormat DATE_FORMAT_DATE2 = new SimpleDateFormat(
				"yyyy\u2000年\u2000MM\u2000月\u2000dd\u2000日\u3000\u2000HH\u2000:\u2000mm");
		return DATE_FORMAT_DATE2.format(getDateByStr2(time));
	}

	public static String getShowTime2(String time){
		SimpleDateFormat DATE_FORMAT_DATE2 = new SimpleDateFormat(
				"yyyy年MM月dd日HH:mm");
		return DATE_FORMAT_DATE2.format(Long.valueOf(time)*1000);
	}

	public static String getShowTime3(String time){
		SimpleDateFormat DATE_FORMAT_DATE2 = new SimpleDateFormat(
				"yyyy.MM.dd HH:mm");
		return DATE_FORMAT_DATE2.format(Long.valueOf(time)*1000);
	}

	public static String getStr(String stopTime,String startTime){
		long cha=Long.valueOf(stopTime)-Long.valueOf(startTime);
		int zofen= (int) (cha/60);
		int fen=zofen%60;
		int hour=((int)(zofen/60))%24;
		int day=((int)(zofen/60)/60)/24;
		return day+"天"+hour+"时"+fen+"分";
	}

	public static long getMillon(String time){
		Date date=getDateByStr2(time);
		return date.getTime()/1000;
	}



}