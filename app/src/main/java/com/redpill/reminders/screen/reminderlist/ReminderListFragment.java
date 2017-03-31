package com.redpill.reminders.screen.reminderlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redpill.reminders.R;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.screen.reminderlist.dialog.AddReminderDialog;
import com.redpill.reminders.screen.reminderlist.list.ReminderListAdapter;

import java.util.ArrayList;
import java.util.List;

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

    private List<Reminder> mReminders;
    private ReminderListAdapter mAdapter;

    private ReminderListPresenter mPresenter;

    public ReminderListFragment() {
        // Required empty public constructor
    }


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
        new AddReminderDialog()
                .setOnReminderCallback(mPresenter::onAddReminder)
                .show(getFragmentManager(), "add reminder");
    }

    private void onReminderItemClick(Reminder reminder) {

    }

    private void onReminderOptionClick(Reminder reminder, View view) {
        removeReminder(reminder);

        PopupMenu popupMenu = new PopupMenu(getContext(), view);
    }

    //View Methods

    @Override
    public void setReminders(RealmResults<Reminder> reminders) {
        mAdapter.setReminderList(reminders);
    }

    @Override
    public void addReminder(Reminder reminder) {
        mReminders.add(reminder);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void removeReminder(Reminder reminder) {
        mReminders.remove(reminder);
        mAdapter.notifyDataSetChanged();
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
        mReminders = new ArrayList<>();
        mAdapter.setOnOptionClickListener(this::onReminderOptionClick);
        mAdapter.setOnClickListener(this::onReminderItemClick);

        mReminderRecyView.setAdapter(mAdapter);

    }


}
