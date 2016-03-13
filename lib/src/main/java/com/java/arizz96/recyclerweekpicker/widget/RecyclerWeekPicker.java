package com.java.arizz96.recyclerweekpicker.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.java.arizz96.recyclerweekpicker.R;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by arizz on 11/03/2016.
 */
public class RecyclerWeekPicker extends RelativeLayout {

    private TextView mMonthTxv;
    private RecyclerViewPager mWeekPickerRvp;
    private onDaySelected mDaySelected;

    public static interface onDaySelected {
        void onSelected(Calendar day);
    }

    public RecyclerWeekPicker(Context context) {
        super(context);
        setupView();
    }

    public RecyclerWeekPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public RecyclerWeekPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupView();
    }

    private void inflateView() {
        inflate(getContext(), R.layout.week_picker_item, this);
        mMonthTxv = (TextView) findViewById(R.id.week_picker_item_monthTxv);
        mWeekPickerRvp = (RecyclerViewPager) findViewById(R.id.week_picker_item_weekRvp);
    }

    private void setupView() {
        inflateView();
        Calendar currentDay = Calendar.getInstance();
        ArrayList<WeekItem> weeks = Utils.getWeeksForMonth(currentDay);
        RecyclerWeekPickerAdapter.clickInterface listener = new RecyclerWeekPickerAdapter.clickInterface() {
            @Override
            public void onClick(Calendar calendar) {
                getOnDaySelectedListener().onSelected(calendar);
            }
        };

        RecyclerWeekPickerAdapter adapter = new RecyclerWeekPickerAdapter(getContext(), weeks, listener);
        mWeekPickerRvp.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mWeekPickerRvp.setAdapter(adapter);
        mWeekPickerRvp.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int i, int i1) {
                mMonthTxv.setText(((RecyclerWeekPickerAdapter) mWeekPickerRvp.getAdapter()).getMonthNameForWeek(i1));
            }
        });
    }

    public void setOnDaySelectedListener(onDaySelected listener) {
        mDaySelected = listener;
    }

    public onDaySelected getOnDaySelectedListener() {
        if(mDaySelected == null)
            return  new onDaySelected() {
                @Override
                public void onSelected(Calendar day) {

                }
            };
        else
            return mDaySelected;
    }
}
