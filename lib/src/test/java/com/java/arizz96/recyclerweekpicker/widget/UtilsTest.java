package com.java.arizz96.recyclerweekpicker.widget;

import android.test.InstrumentationTestCase;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Calendar;

/**
 * Created by arizz on 15/03/2016.
 */
public class UtilsTest {

    @Test
    public void testGetDayForIndex() {
        assertEquals("0 equals MONDAY", Utils.getDayForIndex(0), Calendar.MONDAY);
        assertEquals("1 equals TUESDAY", Utils.getDayForIndex(1), Calendar.TUESDAY);
        assertEquals("2 equals WEDNESDAY", Utils.getDayForIndex(2), Calendar.WEDNESDAY);
        assertEquals("3 equals THURSDAY", Utils.getDayForIndex(3), Calendar.THURSDAY);
        assertEquals("4 equals FRIDAY", Utils.getDayForIndex(4), Calendar.FRIDAY);
        assertEquals("5 equals SATURDAY", Utils.getDayForIndex(5), Calendar.SATURDAY);
        assertEquals("6 equals SUNDAY", Utils.getDayForIndex(6), Calendar.SUNDAY);
    }

    @Test
    public void testSetCalendarToFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        Utils.setCalendarToFirstDayOfWeek(calendar);
        assertEquals("calendar day is MONDAY", calendar.get(Calendar.DAY_OF_WEEK), Calendar.MONDAY);
    }

    @Test
    public void testCalculateWeekDays() {
        Calendar calendar = Calendar.getInstance();
        Utils.setCalendarToFirstDayOfWeek(calendar);
        int firstDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        int lastDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_WEEK, -6);

        int result[] = Utils.calculateWeekDays(firstDay, lastDay);

        assertTrue(result.length == 7);

        // testing inner values
        for (int i = 0; i < 7; i++) {
            assertEquals(String.format("#%d day match", i + 1), result[i], calendar.get(Calendar.DAY_OF_MONTH));
            assertEquals("day of week match", calendar.get(Calendar.DAY_OF_WEEK), Utils.getDayForIndex(i));
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    @Test
    public void testGetWeekForDay() {
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(Calendar.HOUR_OF_DAY, 0);
        firstDay.set(Calendar.MINUTE, 0);
        firstDay.set(Calendar.SECOND, 0);
        firstDay.set(Calendar.MILLISECOND, 0);
        while (firstDay.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
            firstDay.add(Calendar.DAY_OF_WEEK, -1);
        Calendar lastDay = (Calendar) firstDay.clone();
        lastDay.add(Calendar.DAY_OF_WEEK, 6);

        assertEquals("to match first day", Utils.getWeekForDay(Calendar.getInstance()).getFirstDay(), firstDay.get(Calendar.DAY_OF_MONTH));
        assertEquals("to match last day", Utils.getWeekForDay(Calendar.getInstance()).getLastDay(), lastDay.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetMonthName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 10, 1);
        assertEquals("11 match novembre", Utils.getMonthName(calendar), "novembre");

    }

}
