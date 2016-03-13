package com.java.arizz96.recyclerweekpicker.widget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by arizz on 12/03/2016.
 */
public class Utils {

    public static ArrayList<WeekItem> getWeeksForMonth(Calendar monthCalendar) {
        ArrayList<WeekItem> weekItems = new ArrayList<>();

        Calendar firstDay = (Calendar) monthCalendar.clone();

        // set Calendar to first day of month
        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        firstDay.set(Calendar.HOUR_OF_DAY, 0);
        firstDay.set(Calendar.MINUTE, 0);
        firstDay.set(Calendar.SECOND, 0);
        // reset first day to first of week
        firstDay.add(Calendar.DAY_OF_MONTH, -firstDay.getFirstDayOfWeek() + 1);

        for (int i = 0; i < 4; i++) {
            WeekItem item = new WeekItem(firstDay);
            weekItems.add(item);

            firstDay.add(Calendar.WEEK_OF_MONTH, 1);
        }


        return weekItems;
    }

    public static int[] calculateWeekDays(int first_day, int last_day){
        int[] days = new int[7];
        if(last_day > first_day){
            for(int i = 0; i < 7; i++)
                days[i] = first_day + i;
        }
        else{
            int firstOfMonthIndex = 0;
            boolean firstPlaced = false;
            for(int i = 0; i < 7; i++)
                if(!firstPlaced && ((last_day) - i != 1))
                    days[6 - i] = last_day - i;
                else if(!firstPlaced && ((last_day) - i == 1)) {
                    days[6 - i] = 1;
                    firstPlaced = true;
                    firstOfMonthIndex = i;
                }
                else{
                    days[i - firstOfMonthIndex - 1] = first_day + i - firstOfMonthIndex - 1;
                }
        }
        return days;
    }

    public static String getMonthName(Calendar cal) {
        return new SimpleDateFormat("MMMM").format(cal.getTime());
    }

    public static String getDayName(int index, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DAY_OF_WEEK, index);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }
}
