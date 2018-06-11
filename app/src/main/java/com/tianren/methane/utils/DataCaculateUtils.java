package com.tianren.methane.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

/**
 * @Athor: CaoXiuxia
 * @ClassName: DataCaculateUtils
 * @Description: 进行年月日,星期几的 计算(日历函数)
 * @date 2014-10-29 下午9:06:37
 */
public class DataCaculateUtils {

	private String TAG = "DataCaculateUtils";
	// 当前时间的显 示
	private static Calendar calendar;
	private SimpleDateFormat format;
	private int mYear, mMonth, mDay, month_act;
	// 当前时间是星期几
	private static String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };
	private long longtime;
	private Date data;
	private static Date curDate;
	private DataCaculateUtils caculateUtilsObj;

	public DataCaculateUtils() {
		// TODO Auto-generated constructor stub
	}
	/*public DataCaculateUtils getDataCacUtilsIntance(){
		if (caculateUtilsObj == null) {
			caculateUtilsObj = new DataCaculateUtils();
		}
		return caculateUtilsObj;
	}*/
	/**
	 * @Title: calculateTimeDiffer
	 * @Description: 计算年月日
	 * @param :dayNum ：相差的时间2——8（本天之后的七天）
	 * @param :calendar :Calendar的实例化对象
	 * @param :system_time :System.currentTimeMillis()
	 * @return：void
	 * @throws
	 */
	public void calculateTimeDiffer(int dayNum, Calendar calendar,long system_time) {
		//longtime = System.currentTimeMillis() + dayNum * 24 * 60 * 60 * 1000;
		longtime = system_time + dayNum * 24 * 60 * 60 * 1000;
		calendar.setTimeInMillis(longtime);
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);
		month_act = mMonth + 1;
		Log.d(TAG, "longtime-->" + longtime);
		Log.d(TAG, "---" + mYear + "-----" + month_act + "----" + mDay);
	}

	// http://blog.csdn.net/yudajun/article/details/7939552
	/**
	 * @Title: getFormatTime
	 * @Description: 将当前时间 "之后的几天"，进行格式化
	 * @param :format,SimpleDateFormat format = new
	 *        SimpleDateFormat("yyyy-MM-dd") SimpleDateFormat format=new
	 *        SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * @param :dayNum,本日的后几天
	 * @param :system_time,System.currentTimeMillis()
	 * @return void
	 * @throws
	 */
	public String getFormatTime(int dayNum,SimpleDateFormat format,long system_time) {
		// long now = android.os.SystemClock.uptimeMillis();
		// 时间格式化
		//longtime = System.currentTimeMillis() + dayNum * 24 * 60 * 60 * 1000;
		longtime = system_time + dayNum * 24 * 60 * 60 * 1000;
		data = new Date(longtime);
		String time_format = format.format(data);
		Log.d(TAG, time_format);
		return time_format;
		
	}

	/**
	* @Title: getFormatTime
	* @Description: 获取日期  如：2014-12-04
	* @param @return   
	* @return String  
	* @throws
	*/ 
	@SuppressLint("SimpleDateFormat")
	public String getFormatTime() {
		// long now = android.os.SystemClock.uptimeMillis();
		// 时间格式化
		//longtime = System.currentTimeMillis() + dayNum * 24 * 60 * 60 * 1000;
		longtime = System.currentTimeMillis();;
		format = new SimpleDateFormat("yyyy-MM-dd");
		data = new Date(longtime);
		String data_format = format.format(data);
		Log.d(TAG, data_format);
		return data_format;
	}
	
	/**
	 * @Title: getWeekOfDate
	 * @Description: 计算当前的日期是星期几
	 * @param :Calendar 实例化对象 Calendar calendar = Calendar.getInstance();
	 * @param :system_time ,System.currentTimeMillis();
	 * @return String
	 * @throws
	 */
	public String getWeekOfDate(int dayNum,Calendar cal,long system_time) {
		curDate = new Date(system_time+dayNum*24*60*60*1000);
		cal.setTime(curDate);
		//int w = cal.get(Calendar.DAY_OF_WEEK);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;//原来是减一
		if (w < 0) {
			w = 0;
		}
		Log.d("ChooseTimeActivity", "--->" + weekDays[w]+"-->"+w);
		return weekDays[w];
	}
	/*洗衣机使用*/
	
	
	private int year_washer,month_washer,day_washer,hour_washer,minute_washer;
	/**
	 * TODO:获取年份的前两位
	 * @return
	 */
	public int getYearHigh(){
		int yearHight = 0;
		Calendar c = Calendar.getInstance();
		year_washer = c.get(Calendar.YEAR);
		yearHight= Integer.parseInt(String.valueOf(year_washer).substring(0,2));
		Log.e(TAG,  "year high "+yearHight);
		return yearHight;
	}
	/**
	 * TODO:获取年份的后两位
	 * @return
	 */
	public int getYearLow(){
		int yearLow = 0;
		Calendar c = Calendar.getInstance();
		year_washer = c.get(Calendar.YEAR);
		yearLow  = Integer.parseInt(String.valueOf(year_washer).substring(2));//截取掉s从首字母起长度为begin的字符串，将剩余字符串赋值给s；
		Log.e(TAG, "year low "+ yearLow);
		return yearLow;
	}
	
	public int getLocalMouth(){
		int month_washer = 0;
		Calendar c = Calendar.getInstance();
		month_washer = c.get(Calendar.MONTH)+1;
		Log.e(TAG, month_washer + "月");
		return month_washer;
	}
	public int getLocalDay() {
		int day = 0;
		Calendar c = Calendar.getInstance();
		day = c.get(Calendar.DAY_OF_MONTH);
		Log.e(TAG, day + "日");
		return day;
	}
	
	public int getLocalWeekday() {
		Calendar c = Calendar.getInstance();
		int week = c.get(Calendar.DAY_OF_WEEK)-1;
		Log.e(TAG, "星期 ：" + week);
		return week;
	}
	
	public int getLocalHour(Context mContext){
		int hour= 0;
		Calendar c = Calendar.getInstance();
		ContentResolver cv = mContext.getContentResolver();
		String strTimeFormat = android.provider.Settings.System.getString(cv, 
				android.provider.Settings.System.TIME_12_24);
		if(strTimeFormat!=null&&strTimeFormat.equals("24")){
			Log.i(TAG, "24");
			hour = c.get(Calendar.HOUR_OF_DAY);
		}else {
			Log.i(TAG, "12");
			hour = c.get(Calendar.HOUR_OF_DAY)+12;
		}		
		Log.e(TAG, hour+"时");
		return hour;
	}
	public int getLocalMinite(){
		int minute_washer = 0;
		Calendar c = Calendar.getInstance();
		minute_washer = c.get(Calendar.MINUTE);
		Log.e(TAG, minute_washer+"分");
		return minute_washer;
	}



}
