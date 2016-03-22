package com.java.arizz96.recyclerweekpicker.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.arizz96.recyclerweekpicker.R;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

/**
 * Created by arizz on 12/03/2016.
 */
public class RecyclerWeekPickerAdapter extends RecyclerViewPager.Adapter<RecyclerWeekPickerAdapter.ViewHolder> {

    private ArrayList<WeekItem> mWeekList;
    private Context mContext;
    private clickInterface mDaySelected;
    private static Calendar lastSelectedDay;
    private TreeMap<Long, Boolean> mDisabledDays;

    protected static interface clickInterface {
        void onClick(Calendar calendar);
    }

    public RecyclerWeekPickerAdapter(Context context, ArrayList<WeekItem> weekItems,
                                     clickInterface dayListener, TreeMap<Long, Boolean> disabledDays) {
        mWeekList = weekItems;
        mContext = context;
        mDaySelected = dayListener;
        mDisabledDays = disabledDays;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_item, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(Calendar.HOUR_OF_DAY, 0);
        currentDay.set(Calendar.MINUTE, 0);
        currentDay.set(Calendar.SECOND, 0);
        currentDay.set(Calendar.MILLISECOND, 0);

        for(int i = 0; i < 7; i++){
            final Calendar selected = mWeekList.get(position).getCalendarByOffset(i);
            holder.mDayView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectedDay(selected);
                }
            });
            holder.mWeekDaysNum[i].setText(mWeekList.get(position).getDayNumAtIndex(i) + "");
            holder.mWeekDays[i].setText(mWeekList.get(position).getDayNameAtIndex(i, "E"));

            // disabled case
            if(getEnabledDayState(mWeekList.get(position).getCalendarByOffset(i))) {
                holder.mWeekDaysNum[i].setTextColor(mContext.getResources().getColor(R.color.day_item_enabled_day_color));
                holder.mWeekDays[i].setTextColor(mContext.getResources().getColor(R.color.day_item_enabled_day_color));
                holder.itemView.setEnabled(true);
            } else {
                holder.mWeekDaysNum[i].setTextColor(mContext.getResources().getColor(R.color.day_item_disabled_day_color));
                holder.mWeekDays[i].setTextColor(mContext.getResources().getColor(R.color.day_item_disabled_day_color));
                holder.itemView.setEnabled(false);

            }

            // selection case
            if(mWeekList.get(position).getSelectedIndex() == i) {
                holder.mWeekDaysNum[i].setBackgroundResource(R.drawable.day_item_num_bg);
                holder.mWeekDays[i].setBackgroundResource(R.drawable.day_item_name_bg);
            } else {
                holder.mWeekDaysNum[i].setBackgroundResource(0);
                holder.mWeekDays[i].setBackgroundResource(0);
            }

            // current day case
            if(mWeekList.get(position).getCalendarByOffset(i).compareTo(currentDay) == 0)
                holder.mCurrentDayView[i].setBackgroundResource(R.drawable.day_item_current_bg);
            else
                holder.mCurrentDayView[i].setBackgroundResource(0);
        }
    }

    public void toggleSelection(int week, int day, Calendar calendar) {
        if(getEnabledDayState(calendar)) {
            if(lastSelectedDay != null) {
                int weekIndex = getWeekIndexForDay(lastSelectedDay);
                mWeekList.get(weekIndex).resetSelectedDay();
                notifyItemChanged(weekIndex);
            }
            mWeekList.get(week).setSelectedIndex(day);
            lastSelectedDay = calendar;
            notifyItemChanged(week);
            mDaySelected.onClick(calendar);
        }
    }

    @Override
    public int getItemCount() {
        return mWeekList.size();
    }

    public void addPreviousWeek() {
        WeekItem week = Utils.getPreviousWeekForDay(mWeekList.get(0).getCalendarByOffset(0));
        mWeekList.add(0, week);
        notifyItemInserted(0);
    }

    public void addNextWeek() {
        WeekItem week = Utils.getPreviousWeekForDay(mWeekList.get(getItemCount() - 1).getCalendarByOffset(6));
        mWeekList.add(week);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addPreviousWeeks(int count) {
        ArrayList<WeekItem> weeks = Utils.getPreviousWeeksForDay(mWeekList.get(0).getCalendarByOffset(0), count);
        for (int i = 0; i < weeks.size(); i++) {
            mWeekList.add(0, weeks.get(weeks.size() - 1 - i));
            notifyItemInserted(0);
        }
    }

    public void addNextWeeks(int count) {
        ArrayList<WeekItem> weeks = Utils.getNextWeeksForDay(mWeekList.get(getItemCount() - 1).getCalendarByOffset(6), count);
        for (int i = 0; i < weeks.size(); i++) {
            mWeekList.add(weeks.get(i));
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public String getMonthNameForWeek(int weekNum) {
        return mWeekList.get(weekNum).getMonthName();
    }

    private boolean getEnabledDayState(long day) {
        if(mDisabledDays.containsKey(day))
            return mDisabledDays.get(day);
        else
            return true;
    }

    private boolean getEnabledDayState(Calendar day) {
        Calendar cal = (Calendar) day.clone();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return getEnabledDayState(cal.getTimeInMillis());
    }

    private int getWeekIndexForDay(Calendar day) {
        Calendar cal = (Calendar) day.clone();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long diff = day.getTimeInMillis() - mWeekList.get(0).getCalendarByOffset(0).getTimeInMillis();
        int weeksDiff = (int)(diff / Utils.SECONDS_IN_WEEK);

        return weeksDiff;
    }

    protected int setSelectedDay(Calendar day) {
        int offset = getWeekIndexForDay(day);
        if(offset < 0) {
            addPreviousWeeks(-offset + 1);
        } else if(offset >= getItemCount()) {
            addNextWeeks(offset - getItemCount() + 1);
        }
        offset = getWeekIndexForDay(day);
        toggleSelection(offset, mWeekList.get(offset).getDayIndexAtNum(day.get(Calendar.DAY_OF_MONTH)), day);
        return offset;
    }

    protected RecyclerWeekPicker.onDayEnableStateChange getEnabledStateChanged() {
        return new RecyclerWeekPicker.onDayEnableStateChange() {
            @Override
            public void enableStateChanged(Calendar day) {
                notifyItemChanged(getWeekIndexForDay(day));
            }
        };
    }

    public static class ViewHolder extends RecyclerViewPager.ViewHolder {

        View mDayView[] = new View[7];
        TextView mWeekDays[] = new TextView[7];
        TextView mWeekDaysNum[] = new TextView[7];
        View mCurrentDayView[] = new View[7];

        public ViewHolder(View itemView) {
            super(itemView);

            int daysIds[] = {
                    R.id.week_item_day_Mo,
                    R.id.week_item_day_Tu,
                    R.id.week_item_day_We,
                    R.id.week_item_day_Th,
                    R.id.week_item_day_Fr,
                    R.id.week_item_day_Sa,
                    R.id.week_item_day_Su
            };

            for(int i = 0; i < 7; i++){
                mDayView[i] = itemView.findViewById(daysIds[i]);
                mWeekDays[i] = (TextView) mDayView[i].findViewById(R.id.item_day_nameTxv);
                mWeekDaysNum[i] = (TextView) mDayView[i].findViewById(R.id.item_day_numTxv);
                mCurrentDayView[i] = mDayView[i].findViewById(R.id.item_day_currentDayView);
            }
        }
    }
}
