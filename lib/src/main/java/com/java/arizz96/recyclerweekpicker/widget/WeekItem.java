package com.java.arizz96.recyclerweekpicker.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;

/**
 * Created by arizz on 11/03/2016.
 */
public class WeekItem {

    private int[] mWeekDays;
    private Calendar mFirstDayCalendar;
    private SparseBooleanArray mDisabledDays;
    private String mMonthName;
    private int mSelectedDay = -1;

    public WeekItem(Calendar firstDay) {
        mFirstDayCalendar = (Calendar) firstDay.clone();
        if(mFirstDayCalendar.get(Calendar.DAY_OF_WEEK) != Utils.FIRST_DAY_OF_WEEK)
            Utils.setCalendarToFirstDayOfWeek(mFirstDayCalendar);
        Calendar lastDay = (Calendar) mFirstDayCalendar.clone();
        lastDay.add(Calendar.DAY_OF_MONTH, 6);
        String monthName = Utils.getMonthName(mFirstDayCalendar);
        monthName += String.format(" %d", mFirstDayCalendar.get(Calendar.YEAR));
        String nextMonthName = Utils.getMonthName(lastDay);
        nextMonthName += String.format(" %d", lastDay.get(Calendar.YEAR));
            monthName = String.format("%s / %s", monthName, nextMonthName);

        mWeekDays = Utils.calculateWeekDays(mFirstDayCalendar.get(Calendar.DAY_OF_MONTH), lastDay.get(Calendar.DAY_OF_MONTH));
        mMonthName = monthName;
        mDisabledDays = new SparseBooleanArray(7);
    }

    public int getDayNumAtIndex(int index) {
        return mWeekDays[index];
    }

    public int getDayIndexAtNum(int num) {
        for (int i = 0; i < 7; i++) {
            if(mWeekDays[i] == num)
                return i;
        }
        return -1;
    }

    public String getDayNameAtIndex(int index, String format) {
        return Utils.getDayName(index, format);
    }

    public int getFirstDay() {
        return mWeekDays[0];
    }

    public int getLastDay() {
        return mWeekDays[6];
    }

    public boolean isEnabledAtDay(int day) {
        return mDisabledDays.get(day, true);
    }

    public boolean isEnabledAtIndex(int index) {
        return isEnabledAtDay(mWeekDays[index]);
    }

    public void setEnabledAtDay(int day, boolean state) {
        mDisabledDays.put(day, state);
    }

    public void setEnabledAtIndex(int index, boolean state) {
        setEnabledAtDay(mWeekDays[index], state);
    }

    public String getMonthName() {
        return mMonthName;
    }

    public Calendar getCalendarByOffset(int offset) {
        Calendar calendar = (Calendar) mFirstDayCalendar.clone();
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return calendar;
    }

    public Calendar getCalendarByDay(int day) {
        int offset = -1;
        for (int i = 0; i < 7 && offset == -1; i++) {
            if(mWeekDays[i] == day)
                offset = i;
        }
        return getCalendarByOffset(offset);
    }

    public void setSelectedDay(int day) {
        mSelectedDay = day;
    }

    public void setSelectedIndex(int day) {
        setSelectedDay(mWeekDays[day]);
    }

    public int getSelectedDay() {
        return mSelectedDay;
    }

    public int getSelectedIndex() {
        for (int i = 0; i < 7; i++) {
            if(mWeekDays[i] == getSelectedDay())
                return i;
        }
        return -1;
    }

    public void resetSelectedDay() {
        mSelectedDay = -1;
    }
}
