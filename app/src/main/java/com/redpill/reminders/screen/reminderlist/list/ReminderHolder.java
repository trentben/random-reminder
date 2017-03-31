package com.redpill.reminders.screen.reminderlist.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.redpill.reminders.R;
import com.redpill.reminders.callback.ObjectCallback;
import com.redpill.reminders.model.Reminder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title) TextView mTitleText;

    private ObjectCallback<Reminder> mOnClickListener;
    private ReminderListAdapter.OnOptionClickListener mOnOptionClickListener;

    private View mItemView;
    private Reminder mReminder;

    public ReminderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemView = itemView;
    }

    public void bind(Reminder reminder) {
        mReminder = reminder;
        mTitleText.setText(reminder.getTitle());
    }

    public void setOnClickListener(ObjectCallback<Reminder> listener) {
        mOnClickListener = listener;
    }

    public void setOnOptionClickListener(ReminderListAdapter.OnOptionClickListener onOptionClickListener) {
        mOnOptionClickListener = onOptionClickListener;
    }

    @OnClick(R.id.card)
    public void onClick() {
        if (mOnClickListener != null) {
            mOnClickListener.onCallback(mReminder);
        }
    }

    @OnClick(R.id.options_btn)
    public void onOptionClick() {
        if (mOnOptionClickListener != null) {
            mOnOptionClickListener.onOptionClick(mReminder, mItemView);
        }
    }
}
