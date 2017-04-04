package com.redpill.reminders.screen.reminderlist.dialog;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.redpill.reminders.R;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditReminderDialog extends DialogFragment implements EditReminderView {

    @BindView(R.id.dialog_title) TextView mDialogTitleText;
    @BindView(R.id.title_edit) EditText mTitleEdit;
    @BindView(R.id.frequency_text) TextView mFrequencyText;
    @BindView(R.id.freq_low_button) Button mLowButton;
    @BindView(R.id.freq_medium_button) Button mMediumButton;
    @BindView(R.id.freq_high_button) Button mHighButton;
    @BindView(R.id.time_of_day_spinner) Spinner mTimeOfDaySpinner;
    @BindView(R.id.add_button) Button mAddButton;
    @BindView(R.id.update_button) Button mUpdateButton;
    @BindDrawable(R.drawable.button_frequency_high) Drawable mHighBackground;
    @BindDrawable(R.drawable.button_frequency_medium) Drawable mMediumBackground;
    @BindDrawable(R.drawable.button_frequency_low) Drawable mLowBackground;
    @BindDrawable(R.drawable.button_frequency_high_grey) Drawable mHighBackgroundGrey;
    @BindDrawable(R.drawable.button_frequency_medium_grey) Drawable mMediumBackgroundGrey;
    @BindDrawable(R.drawable.button_frequency_low_grey) Drawable mLowBackgroundGrey;

    private EditReminderPresenter mPresenter;
    private boolean mIsUpdateMode;
    private int mReminderId;
    private int mSelectedFrequency;
    private int mSelectedTimeOfDay;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_reminder, container, false);
        ButterKnife.bind(this, view);

        handleArguments();
        init(mIsUpdateMode);

        mPresenter = new EditReminderPresenter(this, new EditReminderModel(getContext()));
        return view;
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);

        super.onResume();
    }

    @OnClick(R.id.freq_low_button)
    public void onLowFreqClick() {
        setSelectedFrequency(FREQUENCY_LOW);
    }

    @OnClick(R.id.freq_medium_button)
    public void onMediumFreqClick() {
        setSelectedFrequency(FREQUENCY_MEDIUM);

    }

    @OnClick(R.id.freq_high_button)
    public void onHighFreqClick() {
        setSelectedFrequency(FREQUENCY_HIGH);

    }

    @OnClick(R.id.add_button)
    public void onAddClick() {
        mPresenter.onAddReminderClick();
    }

    @OnClick(R.id.update_button)
    public void onUpdateClick() {
        mPresenter.onUpdateReminderClick();
    }

    @OnClick(R.id.cancel_button)
    public void onCancelClick() {
        dismiss();
    }

    //View Methods

    @Override
    public boolean isUpdateMode() {
        return mIsUpdateMode;
    }

    @Override
    public int getReminderIdParam() {
        return mReminderId;
    }

    @Override
    public String getTitleText() {
        return mTitleEdit.getText().toString();
    }

    @Override
    public void setTitleText(String text) {
        mTitleEdit.setText(text);
    }

    @Override
    public int getSelectedFrequency() {
        return mSelectedFrequency;
    }

    @Override
    public void setSelectedFrequency(int freq) {
        mSelectedFrequency = freq;

        mHighButton.setBackground(mHighBackgroundGrey);
        mMediumButton.setBackground(mMediumBackgroundGrey);
        mLowButton.setBackground(mLowBackgroundGrey);

        String freqDuration = "";

        switch (freq) {
            case FREQUENCY_HIGH:
                mHighButton.setBackground(mHighBackground);
                freqDuration = getString(R.string.day);
                break;
            case FREQUENCY_MEDIUM:
                mMediumButton.setBackground(mMediumBackground);
                freqDuration = getString(R.string.week);
                break;
            case FREQUENCY_LOW:
                mLowButton.setBackground(mLowBackground);
                freqDuration = getString(R.string.month);
                break;
        }

        mFrequencyText.setText(String.format(getString(R.string.reminde_me_every), freqDuration));
    }

    @Override
    public int getTimeOfDay() {
        return mTimeOfDaySpinner.getSelectedItemPosition();
    }

    @Override
    public void setTimeOfDay(int timeOfDay) {
        mTimeOfDaySpinner.setSelection(timeOfDay);
    }

    //Dialog Methods

    private void handleArguments() {
        Bundle args = getArguments();

        if (args != null) {
            mIsUpdateMode = args.getBoolean(Constant.FLAG_IS_UPDATE_MODE, false);
            if (mIsUpdateMode) {
                mReminderId = args.getInt(Constant.FLAG_REMINDER_ID, 0);
                if (mReminderId == 0) {
                    mIsUpdateMode = false;
                }
            }
        }
    }

    private void init(boolean mIsUpdateMode) {
        String title = mIsUpdateMode ? getString(R.string.update_reminder) : getString(R.string.setup_reminder);
        mDialogTitleText.setText(title);

        mAddButton.setVisibility(mIsUpdateMode ? View.GONE : View.VISIBLE);
        mUpdateButton.setVisibility(mIsUpdateMode ? View.VISIBLE : View.GONE);

    }



}
