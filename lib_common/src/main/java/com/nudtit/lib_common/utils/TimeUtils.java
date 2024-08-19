package com.nudtit.lib_common.utils;


import android.text.TextUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author : LJY
 * @date : 2021/11/2
 * <p>
 * 获取时间工具类
 */

public class TimeUtils {


    public static String MORNIN = "mornin";
    public static String AFTERNOON = "afternoon";
    public static String DATE_FORMAT_YYYY_MM_DD="yyyy-MM-dd";
    /**
     * 将时间戳转换为星期
     *
     * @return
     */
    public static String getWeek() {

        Date date = new Date();
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int week = cd.get(Calendar.DAY_OF_WEEK);

        String weekString;
        switch (week) {
            case Calendar.SUNDAY:
                weekString = "星期日";
                break;
            case Calendar.MONDAY:
                weekString = "星期一";
                break;
            case Calendar.TUESDAY:
                weekString = "星期二";
                break;
            case Calendar.WEDNESDAY:
                weekString = "星期三";
                break;
            case Calendar.THURSDAY:
                weekString = "星期四";
                break;
            case Calendar.FRIDAY:
                weekString = "星期五";
                break;
            default:
                weekString = "星期六";
                break;

        }

        return weekString;
    }

    /**
     * 时间转换星期
     *
     * @param dateTime
     * @return 星期X
     */
    public static String dateToWeek(String dateTime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = f.parse(dateTime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 指示一个星期中的某天
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 时间转换日期
     *
     * @param date
     * @return yyyy年M月d日
     */
    public static String dateToString(String date) {
        String dateStr;
        String[] spliteStr = date.split("-");
        int year = Integer.parseInt((spliteStr[0]));
        int month = Integer.parseInt((spliteStr[1]));
        int day = Integer.parseInt((spliteStr[2]));
        dateStr = year + "年" + month + "月" + day + "日";
        return dateStr;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取时间 小时
     */
    public static int getTimeHours() {
        String dateString = getTimeShort();
        String[] split = dateString.split(":");
        if (split.length > 0){
            String content = split[0];
            if (StringUtils.isNumeric(content)){
                return Integer.parseInt(content);
            }
            return 0;
        }else {
            return 0;
        }
    }

    /**
     * 获取时间 分钟
     */
    public static int getTimeMin() {
        String dateString = getTimeShort();
        String[] split = dateString.split(":");
        if (split.length > 1){
            String content = split[1];
            if (StringUtils.isNumeric(content)){
                return Integer.parseInt(content);
            }
            return 0;
        }else {
            return 0;
        }
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间格式 yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        return dateString;
    }

    /**
     * 获取当前系统时间
     *
     * @return 返回短时间格式 yyyy-MM-dd HH:mm"
     */
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取当前系统时间
     *
     * @return 返回短时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 判断当前时间是上午还是下午
     *
     * @return MORNIN代表上午  AFTERNOON代表下午
     */
    public static String isMorninOrAfternoon() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour <= 12) {
            return MORNIN;
        }
        return AFTERNOON;
    }


    /***
     * 根据当前时间的秒数来判断 下一分钟还有多少秒
     * @return 距离下一分钟的时间秒S
     */
    public static long getDelayTime() {
        String time = getTimeShort();
        // 10:27:1
        if (!TextUtils.isEmpty(time)) {
            try {
                int lastIndex = time.lastIndexOf(":");
                String secondStr = time.substring(lastIndex + 1);
                int second = Integer.parseInt(secondStr);
                return (60 - second) * 1000;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 3000;
    }

    /***
     * 判断两个时间戳是否在同一天
     * 传入时间格式要为 "yyyy-MM-dd HH:mm:ss"
     * @param time1     第一个时间
     * @param time2     第二个时间
     * @return True是同一天  False不是同一天
     */
    public static boolean isSameDay(String time1, String time2) {
        if (null == time1 || time1.isEmpty() || null == time2 || time2.isEmpty()) {
            return true;
        }
        Date date1 = stringToDate(time1);
        Date date2 = stringToDate(time2);
        if (date1 == null || date2 == null) {
            return true;
        }
        long millis1 = date1.getTime();
        long millis2 = date2.getTime();
        long interval = millis1 - millis2;
        return interval < 86400000 && interval > -86400000 && millis2Days(millis1, TimeZone.getDefault()) == millis2Days(millis2, TimeZone.getDefault());
    }

    private static long millis2Days(long millis, TimeZone timeZone) {
        return (((long) timeZone.getOffset(millis)) + millis) / 86400000;
    }


    /**
     * 字符串日期转date
     * @param time yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date stringToDate(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串日期转date
     * @param time yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date stringToDate(String time,String timeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    /**
     * 字符串日期格式转long
     *
     * @param strDate "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static Long stringToLong(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(strDate);
            long longTime = date.getTime();
            return longTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串日期格式转long 到分钟
     * @param strDate "yyyy-MM-dd HH:mm"
     * @return
     */
    public static Long timeHMToLong(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = dateFormat.parse(strDate);
            long longTime = date.getTime();
            return longTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param startTime 预约时间
     * @param beyondTime 时间范围内
     * @return 0在预约时间范围内 1未到时间点  2超过预约时间
     */
    public static int isCurrentInTimeScope(String startTime, String currentTime, int beyondTime) {
        int result = 0;
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(currentTime)){
            return -1;
        }
        Long startTimeLong = timeHMToLong(startTime);
        Long endLong = startTimeLong + 60000 * beyondTime;
        Long currentLong = timeHMToLong(currentTime);
        if (startTimeLong > currentLong){
            result = 1;
        }else if (startTimeLong <= currentLong && currentLong <= endLong){
            result = 0;
        }else {
            result = 2;
        }
        return result;
    }

    /**
     * date日期转字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        //格式化日期时间格式，只取日期的可以去掉 HH:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //new Date() 获取当前系统时间，或者可以从类中取，如 user.getDate()
        //Date包是 java.util.Date;别导错了
        return dateFormat.format(date);
    }

    /**
     * date日期转字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date,String strFormat) {
        //格式化日期时间格式，只取日期的可以去掉 HH:mm:ss
        DateFormat dateFormat = new SimpleDateFormat(strFormat);
        //new Date() 获取当前系统时间，或者可以从类中取，如 user.getDate()
        //Date包是 java.util.Date;别导错了
        return dateFormat.format(date);
    }


    /**
     * 获取时间
     *
     * @return
     */
    public static String getTime(String timeFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
        String dateString = formatter.format(date);
        return dateString;
    }
}
