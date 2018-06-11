package com.tianren.methane.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by lulu on 17/1/4.
 */

public class DateUtil {

    public static final String[] formats = new String[]{"yyyy-MM-dd", "yyyyMM", "yyyyMMdd", "yyyy.M.d", "yyyy.MM.dd", "yyyy-M-d",
            "yyyy/MM/dd", "yyyy/M/d", "*yyyy/M/d", "*yyyy/MM/dd", "yyyy年M月d日", "yyyy年MM月dd日"};

    public static final String[] timeFormats = new String[]{"HH:mm:ss", "HH:mm"};


    public static String[] getFormats() {
        Set<String> list = new HashSet<>();
        for (String date : formats) {
            for (String time : timeFormats) {
                list.add(date + " " + time);
            }
            list.add(date);
        }
        return list.toArray(new String[]{});
    }

    public static String format(String f, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(f);
        return simpleDateFormat.format(date);
    }

    /**
     * 使用多个format匹配，其中一个成功即返回
     *
     * @param str
     * @return
     */
    public static Date getDateValueFromString(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        } else {
            for (int i = 0; i < getFormats().length; i++) {
                String f = getFormats()[i];
                SimpleDateFormat sf = new SimpleDateFormat(f);
                sf.setLenient(false);
                try {
                    return sf.parse(str);
                } catch (ParseException e) {
                    //e.printStackTrace();
                }
            }
            return null;
        }

    }

    /**
     * 获得日期中的day
     *
     * @return
     */
    public static Integer getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得日期中的day
     *
     * @return
     */
    public static Integer getDayOfMonth(long t) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(t);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当前月
     *
     * @return
     */
    public static Integer getMonth() {
        Calendar cal = Calendar.getInstance();
        Integer month = cal.get(Calendar.MONTH) + 1;
        return month;

    }

    /**
     * 当前月
     *
     * @return
     */
    public static Integer getMonth(long t) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(t);
        Integer month = cal.get(Calendar.MONTH) + 1;
        return month;

    }

    /**
     * 年
     *
     * @return
     */
    public static Integer getYear() {
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 星期
     *
     * @return
     */
    public static Integer getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        Integer res = cal.get(Calendar.DAY_OF_WEEK);
        return res;
    }


    public static String getCnDayOfWeek() {
        switch (getDayOfWeek()) {
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            case 1:
                return "日";
            default:
                return "";
        }
    }

    public static String secondToDate(String second) {
        Long timestamp = Long.parseLong(second) * 1000;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(timestamp));
    }

    public static String secondToDate(String second, String format) {
        Long timestamp = Long.parseLong(second) * 1000;
        return new SimpleDateFormat(format, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }

    /**
     * 获取当前日期
     *
     */
    public static String getNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
