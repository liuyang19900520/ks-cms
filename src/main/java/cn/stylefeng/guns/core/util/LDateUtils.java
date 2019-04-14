package cn.stylefeng.guns.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liuya
 */
public class LDateUtils {

    /**
     * @param str
     * @param format
     * @return
     */
    public static Date stringToDate(String str, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            if (date != null) {
                strDate = dateFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 例：2017/11/12 23:00:23==>2017/11/12 00:00:00
     *
     * @return current time of a day at 0:00
     */
    public static Date getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        return start;
    }

    /**
     * 例：2017/11/12 23:00:23==>2017/11/13 00:00:00
     *
     * @return current time of a day at 23:59
     */
    public static Date getEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date end = calendar.getTime();
        return end;
    }

    public static String nextDate(Integer days, String _date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Calendar cl = Calendar.getInstance();
        Date date = null;

        try {
            date = (Date) sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(date);

        cl.add(Calendar.DAY_OF_YEAR, days);

        date = cl.getTime();
        return sdf.format(date);
    }

    public static String preDate(Integer days, String _date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Calendar cl = Calendar.getInstance();
        Date date = null;

        try {
            date = (Date) sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(date);

        // 时间减一天
        cl.add(Calendar.DAY_OF_MONTH, -days);

        date = cl.getTime();
        return sdf.format(date);
    }

    public static Date nextDate(Integer days, Date _date) {

        Calendar cl = Calendar.getInstance();
        Date date = null;

        date = _date;
        cl.setTime(date);

        // 时间减一天
        cl.add(Calendar.DAY_OF_MONTH, days);

        date = cl.getTime();
        return date;
    }

    public static Date preDate(Integer days, Date _date) {

        Calendar cl = Calendar.getInstance();
        Date date = null;

        date = _date;
        cl.setTime(date);

        // 时间减一天
        cl.add(Calendar.DAY_OF_MONTH, -days);

        date = cl.getTime();
        return date;
    }

    public static int getDiscrepantDays(String dateStart, String dateEnd, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date dateStartD = null;
        Date dateEndD = null;
        try {
            dateStartD = (Date) sdf.parse(dateStart);
            dateEndD = (Date) sdf.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (int) ((dateEndD.getTime() - dateStartD.getTime()) / 1000 / 60 / 60 / 24);
    }
}
