package com.redpill.reminders.ui.reminderlist.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redpill.reminders.R;
import com.redpill.reminders.model.callback.ObjectCallback;
import com.redpill.reminders.model.data.Reminder;

import io.realm.RealmResults;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderHolder> {

    private ObjectCallback<Reminder> mOnClickListener;
    private OnOptionClickListener mOnOptionClickListener;
    private OnReminderItemInteractionLister mOnEnableSwitchListener;

    private RealmResults<Reminder> mReminders;

    public ReminderListAdapter() {

    }

    public void setReminderList(RealmResults<Reminder> reminders) {
        mReminders = reminders;
        autoUpdateData(true);
    }

    @Override
    public ReminderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reminder_card, parent, false);
        return new ReminderHolder(view);
    }

    @Override
    public void onBindViewHolder(ReminderHolder holder, int position) {
        holder.setOnClickListener(mOnClickListener);
        holder.setOnOptionClickListener(mOnOptionClickListener);
        holder.setOnReminderItemInteractionLister(mOnEnableSwitchListener);
        holder.bind(mReminders.get(position));
    }

    @Override
    public int getItemCount() {
        return mReminders.size();
    }

    public void setOnClickListener(ObjectCallback<Reminder> listener) {
        mOnClickListener = listener;
    }

    public void setOnEnableSwitchListener(OnReminderItemInteractionLister listener) {
        mOnEnableSwitchListener = listener;
    }

    public void setOnOptionClickListener(OnOptionClickListener onOptionClickListener) {
        mOnOptionClickListener = onOptionClickListener;
    }


    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mReminders.removeAllChangeListeners();
    }

    public void autoUpdateData(boolean isEnabled) {
        mReminders.removeAllChangeListeners();

        if (isEnabled) {
            mReminders.addChangeListener(element -> notifyDataSetChanged());
        }
    }

    public interface OnOptionClickListener {
        void onOptionClick(Reminder reminder, View view);
    }

    public interface OnReminderItemInteractionLister {
        void onEnableSwitchChange(Reminder reminder, boolean enable);
    }
}
