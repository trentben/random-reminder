package com.redpill.reminders.ui.reminderlist.list;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.redpill.reminders.R;
import com.redpill.reminders.model.callback.ObjectCallback;
import com.redpill.reminders.model.data.Reminder;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderHolder extends RecyclerView.ViewHolder {

    private static final long ONE_HOUR = 3600000;
    private static final long ONE_DAY = ONE_HOUR * 24;
    private static final long ONE_WEEK = ONE_DAY * 7;
    private static final long ONE_MONTH = ONE_DAY * 30;


    @BindView(R.id.color_strip) View mColorStrip;
    @BindView(R.id.title) TextView mTitleText;
    @BindView(R.id.last_reminder_text) TextView mLastReminderText;
    @BindView(R.id.options_btn) View mOptionButton;
    @BindView(R.id.enable_switch) SwitchCompat mEnableSwitch;
    @BindColor(R.color.freq_low) int mLowColor;
    @BindColor(R.color.freq_medium) int mMediumColor;
    @BindColor(R.color.freq_high) int mHighColor;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy h:mm aa");

    private ObjectCallback<Reminder> mOnClickListener;
    private ReminderListAdapter.OnOptionClickListener mOnOptionClickListener;
    private ReminderListAdapter.OnReminderItemInteractionLister mOnEnableSwitchListener;

    private View mItemView;
    private Reminder mReminder;

    public ReminderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemView = itemView;
        mEnableSwitch.setOnCheckedChangeListener(this::onEnableSwitchChange);
    }

    public void bind(Reminder reminder) {
        mReminder = reminder;
        setColorStrip();
        mTitleText.setText(reminder.getTitle());
        mEnableSwitch.setChecked(reminder.isEnabled());

        Date date = new Date();
        date.setTime(mReminder.getRemindAt());
//        mLastReminderText.setText(DATE_FORMAT.format(date));
        mLastReminderText.setText(getLastReminderText());
    }

    private String getLastReminderText() {
        if (mReminder.getReminderHistory().size() == 0) {
            return "Next reminder sometime in the next " + toFrequencyName(mReminder.getFrequency());
        } else {
            long lastAlarm = mReminder.getReminderHistory().get(mReminder.getReminderHistory().size() - 1).getAlarmDate();
            long now = new Date().getTime();

            long difference = now - lastAlarm;

            String lastReminder = "Last reminder was %s";

            if (difference < ONE_HOUR) {
                return String.format(lastReminder, "moments ago");
            } else if(difference < ONE_DAY) {
                return String.format(lastReminder, "hours ago");
            } else if (difference < ONE_WEEK - ONE_DAY) {
                return String.format(lastReminder, "days ago");
            } else if (difference < (ONE_WEEK * 1.5)) {
                return String.format(lastReminder, "about a week ago");
            } else if (difference < ONE_MONTH - (ONE_DAY * 5)) {
                return String.format(lastReminder, "weeks ago");
            } else if (difference < ONE_MONTH + ONE_WEEK) {
                return String.format(lastReminder, "about a month ago");
            } else {
                return String.format(lastReminder, "forever ago!");
            }

        }
    }

    private String toFrequencyName(int frequency) {
        switch(frequency) {
            case Reminder.FREQUENCY_HIGH:
                return "day";
            case Reminder.FREQUENCY_MEDIUM:
                return "week";
            case Reminder.FREQUENCY_LOW:
                return "month";
            default:
                return "n/a";
        }
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

    public void setOnReminderItemInteractionLister(ReminderListAdapter.OnReminderItemInteractionLister listener) {
        mOnEnableSwitchListener = listener;
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

    private void onEnableSwitchChange(CompoundButton compoundButton, boolean isEnabled) {
        if (isEnabled != mReminder.isEnabled()) {
            new Handler().postDelayed(() -> mOnEnableSwitchListener.onEnableSwitchChange(mReminder, isEnabled), 300);
        }
    }

}
