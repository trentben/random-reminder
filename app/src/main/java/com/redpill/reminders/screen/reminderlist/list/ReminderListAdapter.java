package com.redpill.reminders.screen.reminderlist.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redpill.reminders.R;
import com.redpill.reminders.callback.ObjectCallback;
import com.redpill.reminders.model.Reminder;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderHolder> {

    private ObjectCallback<Reminder> mOnClickListener;
    private OnOptionClickListener mOnOptionClickListener;

    private RealmResults<Reminder> mReminders;

    public ReminderListAdapter() {

    }

    public void setReminderList(RealmResults<Reminder> reminders) {
        mReminders = reminders;
        mReminders.addChangeListener(element -> notifyDataSetChanged());
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
        holder.bind(mReminders.get(position));
    }

    @Override
    public int getItemCount() {
        return mReminders.size();
    }

    public void setOnClickListener(ObjectCallback<Reminder> listener) {
        mOnClickListener = listener;
    }

    public void setOnOptionClickListener(OnOptionClickListener onOptionClickListener) {
        mOnOptionClickListener = onOptionClickListener;
    }

    public interface OnOptionClickListener {
        void onOptionClick(Reminder reminder, View view);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mReminders.removeAllChangeListeners();
    }
}
