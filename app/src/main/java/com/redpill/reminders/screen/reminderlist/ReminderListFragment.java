package com.redpill.reminders.screen.reminderlist;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redpill.reminders.R;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.screen.reminderlist.dialog.EditReminderDialog;
import com.redpill.reminders.screen.reminderlist.list.ReminderListAdapter;
import com.redpill.reminders.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderListFragment extends Fragment implements ReminderListView{

    @BindView(R.id.reminder_list) RecyclerView mReminderRecyView;

    private Unbinder mUnbinder;
    private ReminderListAdapter mAdapter;
    private ReminderListPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter = new ReminderListPresenter(this, new ReminderListModel(getContext()));
        init();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    //OnClick Methods

    @OnClick(R.id.add_fab)
    public void onAddReminderClick() {
        new EditReminderDialog()
                .show(getFragmentManager(), "addReminder");
    }

    private void onReminderItemClick(Reminder reminder) {
        Bundle args = new Bundle();
        args.putBoolean(Constant.FLAG_IS_UPDATE_MODE, true);
        args.putInt(Constant.FLAG_REMINDER_ID, reminder.getId());

        EditReminderDialog dialog = new EditReminderDialog();
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "updateReminder");
    }

    private void onReminderOptionClick(Reminder reminder, View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.inflate(R.menu.list_item_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (R.id.delete == item.getItemId()) {
                mPresenter.deleteReminder(reminder);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    //View Methods

    @Override
    public void setReminders(RealmResults<Reminder> reminders) {
        mAdapter.setReminderList(reminders);
    }



    //Fragment Methods
    private void init() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mReminderRecyView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mReminderRecyView.setLayoutManager(layoutManager);

        mAdapter = new ReminderListAdapter();
        mAdapter.setOnOptionClickListener(this::onReminderOptionClick);
        mAdapter.setOnClickListener(this::onReminderItemClick);

        mReminderRecyView.setAdapter(mAdapter);

    }


}
