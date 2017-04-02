package com.redpill.reminders.screen.reminderlist.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.redpill.reminders.R;
import com.redpill.reminders.callback.ObjectCallback;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.util.Utility;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReminderDialog extends DialogFragment {

    @BindView(R.id.title_edit) EditText mTitleEdit;

    private ObjectCallback<Reminder> mReminderCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_reminder, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.add_button)
    public void onAddClick() {
        if (mReminderCallback != null) {
            Reminder reminder = new Reminder();
            reminder.setTitle(mTitleEdit.getText().toString());
            reminder.setRemindAt(Utility.getRandomTime());
            mReminderCallback.onCallback(reminder);
        }
        dismiss();
    }

    @OnClick(R.id.cancel_button)
    public void onCancelClick() {
        dismiss();
    }

    public AddReminderDialog setOnReminderCallback(ObjectCallback<Reminder> callback) {
        mReminderCallback = callback;
        return this;
    }


}
