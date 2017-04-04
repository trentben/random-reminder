package com.redpill.reminders.screen.reminderlist.list;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.redpill.reminders.R;
import com.redpill.reminders.callback.ObjectCallback;
import com.redpill.reminders.model.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.color_strip) View mColorStrip;
    @BindView(R.id.title) TextView mTitleText;
    @BindView(R.id.last_reminder_text) TextView mLastReminderText;
    @BindView(R.id.options_btn) View mOptionButton;
    @BindColor(R.color.freq_low) int mLowColor;
    @BindColor(R.color.freq_medium) int mMediumColor;
    @BindColor(R.color.freq_high) int mHighColor;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy h:mm aa");

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
        setColorStrip();
        mTitleText.setText(reminder.getTitle());
        mLastReminderText.setText(DATE_FORMAT.format(new Date(reminder.getRemindAt())));
    }

    private void setColorStrip() {
        int freq = mReminder.getFrequency();
        switch (freq) {
            case Reminder.FREQUENCY_HIGH:
                mColorStrip.setBackgroundColor(mHighColor);
                break;
            case Reminder.FREQUENCY_MEDIUM:
                mColorStrip.setBackgroundColor(mMediumColor);
                break;
            case Reminder.FREQUENCY_LOW:
                mColorStrip.setBackgroundColor(mLowColor);
                break;
        }
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
            mOnOptionClickListener.onOptionClick(mReminder, mOptionButton);
        }
    }
}
