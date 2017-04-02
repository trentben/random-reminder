package com.redpill.reminders.screen.reminderlist.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.redpill.reminders.R;
import com.redpill.reminders.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditReminderDialog extends DialogFragment implements EditReminderView {

    @BindView(R.id.dialog_title) TextView mDialogTitleText;
    @BindView(R.id.title_edit) EditText mTitleEdit;
    @BindView(R.id.add_button) Button mAddButton;
    @BindView(R.id.update_button) Button mUpdateButton;

    private EditReminderPresenter mPresenter;
    private boolean mIsUpdateMode;
    private int mReminderId;

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
