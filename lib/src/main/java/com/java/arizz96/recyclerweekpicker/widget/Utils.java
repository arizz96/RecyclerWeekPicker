package com.java.arizz96.recyclerweekpicker.widget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by arizz on 12/03/2016.
 */
public class Utils {

    public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY;
    public static final long SECONDS_IN_WEEK = 7 * 24 * 60 * 60 * 1000;

    public static ArrayList<WeekItem> getWeeksForMonth(Calendar monthCalendar) {
        ArrayList<WeekItem> weekItems = new ArrayList<>();

        Calendar firstDay = (Calendar) monthCalendar.clone();

        // set Calendar to first day of month
        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        firstDay.set(Calendar.HOUR_OF_DAY, 0);
        firstDay.set(Calendar.MINUTE, 0);
        firstDay.set(Calendar.SECOND, 0);
        firstDay.set(Calendar.MILLISECOND, 0);
        // reset first day to first of week
        firstDay.add(Calendar.DAY_OF_MONTH, -firstDay.getFirstDayOfWeek() + 1);

        for (int i = 0; i < 4; i++) {
            weekItems.add(getWeekForDay(firstDay));
            firstDay.add(Calendar.WEEK_OF_MONTH, 1);
        }


        return weekItems;
    }

    public static WeekItem getWeekForDay(Calendar day) {
        Calendar firstDay = (Calendar) day.clone();
        // set Calendar to first day of month
        firstDay.set(Calendar.HOUR_OF_DAY, 0);
        firstDay.set(Calendar.MINUTE, 0);
        firstDay.set(Calendar.SECOND, 0);
        firstDay.set(Calendar.MILLISECOND, 0);
        // reset first day to first of week
        setCalendarToFirstDayOfWeek(firstDay);

        return new WeekItem(firstDay);
    }

    public static WeekItem getPreviousWeekForDay(Calendar day) {
        Calendar firstDay = (Calendar) day.clone();
        firstDay.add(Calendar.WEEK_OF_YEAR, -1);
        return getWeekForDay(firstDay);
    }

    public static ArrayList<WeekItem> getPreviousWeeksForDay(Calendar day, int count) {
        ArrayList<WeekItem> items = new ArrayList<>(count);
        Calendar firstDay = (Calendar) day.clone();
        firstDay.add(Calendar.WEEK_OF_YEAR, -count);
        for (int i = 0; i < count; i++) {
            items.add(getWeekForDay(firstDay));
            firstDay.add(Calendar.WEEK_OF_YEAR, 1);
        }
        return items;
    }

    public static WeekItem getNextWeekForDay(Calendar day) {
        Calendar firstDay = (Calendar) day.clone();
        firstDay.add(Calendar.WEEK_OF_YEAR, 1);
        return getWeekForDay(firstDay);
    }

    public static ArrayList<WeekItem> getNextWeeksForDay(Calendar day, int count) {
        ArrayList<WeekItem> items = new ArrayList<>(count);
        Calendar firstDay = (Calendar) day.clone();
        for (int i = 0; i < count; i++) {
            firstDay.add(Calendar.WEEK_OF_YEAR, 1);
            items.add(getWeekForDay(firstDay));
        }
        return items;
    }

    public static void setCalendarToFirstDayOfWeek(Calendar calendar) {
        while(calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY_OF_WEEK)
            calendar.add(Calendar.DAY_OF_WEEK, -1);
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

    public static int getDayForIndex(int index) {
        int day = -1;
        switch (index) {
            case 0: day = Calendar.MONDAY; break;
            case 1: day = Calendar.TUESDAY; break;
            case 2: day = Calendar.WEDNESDAY; break;
            case 3: day = Calendar.THURSDAY; break;
            case 4: day = Calendar.FRIDAY; break;
            case 5: day = Calendar.SATURDAY; break;
            case 6: day = Calendar.SUNDAY; break;
        }
        return day;
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
