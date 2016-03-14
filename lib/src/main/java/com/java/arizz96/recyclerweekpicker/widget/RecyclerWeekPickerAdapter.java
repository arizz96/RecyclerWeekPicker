package com.java.arizz96.recyclerweekpicker.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.arizz96.recyclerweekpicker.R;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by arizz on 12/03/2016.
 */
public class RecyclerWeekPickerAdapter extends RecyclerViewPager.Adapter<RecyclerWeekPickerAdapter.ViewHolder> {

    private ArrayList<WeekItem> mWeekList;
    private Context mContext;
    private clickInterface mDaySelected;
    private static int lastSelectedDay = -1;
    private static int lastSelectedWeek = -1;

    public static interface clickInterface {
        void onClick(Calendar calendar);
    }

    public RecyclerWeekPickerAdapter(Context context, ArrayList<WeekItem> weekItems, clickInterface dayListener) {
        mWeekList = weekItems;
        mContext = context;
        mDaySelected = dayListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_item, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        for(int i = 0; i < 7; i++){
            final int offset = i;
            holder.mDayView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDaySelected.onClick(mWeekList.get(position).getCalendarByOffset(offset));
                    toggleSelection(position, offset);
                }
            });
            holder.mWeekDaysNum[i].setText(mWeekList.get(position).getDayNumAtIndex(i) + "");
            holder.mWeekDays[i].setText(mWeekList.get(position).getDayNameAtIndex(i, "E"));

            if(mWeekList.get(position).getSelectedIndex() == i) {
                holder.mWeekDaysNum[i].setBackgroundResource(R.drawable.day_item_num_bg);
                holder.mWeekDays[i].setBackgroundResource(R.drawable.day_item_name_bg);
            } else {
                holder.mWeekDaysNum[i].setBackgroundResource(0);
                holder.mWeekDays[i].setBackgroundResource(0);
            }
        }
    }

    public void toggleSelection(int week, int day) {
        if(mWeekList.get(week).isEnabledAtIndex(day)) {
            if(lastSelectedDay != -1 && lastSelectedWeek != -1) {
                mWeekList.get(lastSelectedWeek).resetSelectedDay();
                notifyItemChanged(lastSelectedWeek);
            }
            mWeekList.get(week).setSelectedIndex(day);
            lastSelectedDay = day;
            lastSelectedWeek = week;
            notifyItemChanged(week);
        }
    }

    @Override
    public int getItemCount() {
        return mWeekList.size();
    }

    public String getMonthNameForWeek(int weekNum) {
        return mWeekList.get(weekNum).getMonthName();
    }

    public static class ViewHolder extends RecyclerViewPager.ViewHolder {

        View mDayView[] = new View[7];
        TextView mWeekDays[] = new TextView[7];
        TextView mWeekDaysNum[] = new TextView[7];

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
            }
        }
    }
}
