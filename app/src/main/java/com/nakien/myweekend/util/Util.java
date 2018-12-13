package com.nakien.myweekend.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class Util {
    public static String formatDateTime2String(Calendar calendar){
        String hour = calendar.get(Calendar.HOUR_OF_DAY) +"";
        String min = calendar.get(Calendar.MINUTE)+"";
        String date = calendar.get(Calendar.DATE)+"";
        String month = (calendar.get(Calendar.MONTH)+1)+"";
        String year = calendar.get(Calendar.YEAR)+"";
        if(hour.length() < 2){
            hour = "0" + hour;
        }
        if(min.length() < 2){
            min = "0" + min;
        }
        if(date.length() < 2){
            date = "0" + date;
        }
        if(month.length() < 2){
            month = "0" + month;
        }
        return hour + ":" + min + "-" + date+"/"+month+"/"+year;
    }
    public static String formatMoney(long num){
        String money = new String();
        NumberFormat formatter = new DecimalFormat("###,###");
        money = formatter.format(num);
        return money;
    }

    public static boolean isInvalidTime(String schedule){
        Calendar calendar = Calendar.getInstance();
        boolean check = false;
        String []now = formatDateTime2String(calendar).split("-", 2);
        String []myDay = schedule.split("-", 2);

        if(myDay[1].compareTo(now[1]) > 0){
            check =  true;
        }
        else if(myDay[1].equals(now[1])){
            if(myDay[0].compareTo(now[0]) > 0){
                check = true;
            }
        }

        return check;
    }

    public static String minToHour(int min){
        String hour = new String();
        int h, m;
        h = min/60;
        m = min%60;
        hour = h + " giờ " + m + " phút";
        return hour;
    }
}
