package com.java.arizz96.recyclerweekpicker.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.widget.LinearLayout;

/**
 * Created by arizz on 11/03/2016.
 */
public class WeekItem extends LinearLayout {

    private int[] mWeekDays;
    private SparseBooleanArray mDisabledDays;

    public WeekItem(Context context) {
        super(context);
    }

    public WeekItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeekItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WeekItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getFirstDay() {
        return mWeekDays[0];
    }

    public int getLastDay() {
        return mWeekDays[6];
    }

    public boolean isEnabledAtDay(int day) {
        return mDisabledDays.get(day);
    }
}
