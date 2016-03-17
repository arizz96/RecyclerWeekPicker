package com.java.arizz96.recyclerweekpicker.widget;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by arizz on 16/03/2016.
 */
public class WeekItemTest {

    Calendar calendar = Calendar.getInstance();
    WeekItem week = new WeekItem(calendar);

    @Test
    public void testGetDayNumAtIndex() {
        Calendar test = (Calendar) calendar.clone();
        Utils.setCalendarToFirstDayOfWeek(test);
        for (int i = 0; i < 7; i++) {
            assertEquals("days matching", week.getDayNumAtIndex(i), test.get(Calendar.DAY_OF_MONTH));
            test.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void testGetFirstDay() {
        Calendar test = (Calendar) calendar.clone();
        Utils.setCalendarToFirstDayOfWeek(test);
        assertEquals("first day matches", week.getFirstDay(), test.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetLastDay() {
        Calendar test = (Calendar) calendar.clone();
        Utils.setCalendarToFirstDayOfWeek(test);
        test.add(Calendar.DAY_OF_MONTH, 6);
        assertEquals("last day matches", week.getLastDay(), test.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetCalendarByOffset() {
        Calendar test = (Calendar) calendar.clone();
        Utils.setCalendarToFirstDayOfWeek(test);
        for (int i = 0; i < 7; i++) {
            assertEquals("days matching by offset", week.getCalendarByOffset(i), test);
            assertEquals("days matching by day", week.getCalendarByDay(test.get(Calendar.DAY_OF_MONTH)), test);
            test.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void testResetSelectedDay() {
        week.setSelectedDay(week.getFirstDay());
        assertEquals("first day is selected", week.getSelectedDay(), week.getFirstDay());

        week.resetSelectedDay();
        assertEquals("nothing is selected", week.getSelectedDay(), -1);
    }

}
