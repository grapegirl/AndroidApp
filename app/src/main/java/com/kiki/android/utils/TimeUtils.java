package com.kiki.android.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : TimeUtils
 * @Description : 시간 관련 유틸 클래스
 * @since 2015-08-03.
 */
public class TimeUtils {

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String strHH = String.format("%02d", hour);
        String strMM = String.format("%02d", minute);
        String strSS = String.format("%02d", second);

        String date = getDateFormat("yyyymmdd", 0);
        String time = date + "_" + strHH + "" + strMM + "" + strSS + "";
        return time;
    }

    /**
     * 패턴 형식으로 날짜 계산 후 형식에 맞는 날짜 반환하기
     *
     * @param pattern yyyyMMdd
     * @param day
     * @return
     */
    public static String getDateFormat(String pattern, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }

    /**
     * 날짜 출력 포맷에 맞게 반환 메소드
     *
     * @param pattern 출력 패턴
     * @param date    날짜
     * @return 날자 출력 포맷에 맞는 문자열 반환
     */
    public static final String getStringDateFormat(String pattern, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /***
     * 타임존 시간 출력 메소드
     */
    public static void printUserPoneTimezone() {
        TimeZone tz;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z Z)");


        tz = TimeZone.getTimeZone("Asia/Seoul");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("Greenwich");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("America/Los_Angeles");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("America/New_York");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("Pacific/Honolulu");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));


        tz = TimeZone.getTimeZone("Asia/Shanghai");
        df.setTimeZone(tz);
        System.out.println("@@ timezone : " + tz.getDisplayName() + "," + df.format(date));
    }

    /**
     * 현재 사용자에 설정된 시간정보 가져오기
     *
     * @return 시간 정보
     */
    public static String getUserPhoneTimezone() {
        TimeZone timeZone = TimeZone.getDefault();

        timeZone = timeZone.getTimeZone(timeZone.getID());
        Date date = new Date();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(timeZone);

        return df.format(date);
    }
}
