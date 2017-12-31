package com.linkmindpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.linkmindpro.adapters.DoctorListAdapter;
import com.linkmindpro.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class DoctorListActivity extends AppCompatActivity {

    @BindView(R.id.title) TextView textViewTitle;
    @BindView(R.id.text_view_notification) TextView textViewNotification;
    @BindView(R.id.edit_text_search) EditText editTextSearch;
    @BindView(R.id.recycler_view_doctor) RecyclerView recyclerViewDoctor;
    @BindString(R.string.new_message) String stringNewMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        ButterKnife.bind(this);
        updateUi();
        initView();
    }

    private void updateUi() {
        textViewTitle.setText(stringNewMessage);
        textViewNotification.setVisibility(View.VISIBLE);
    }

    private void initView() {
        ArrayList<String> doctorList = new ArrayList<>();
        doctorList.add("Will ResonBloom");
        doctorList.add("Will ResonBloom");
        doctorList.add("Will ResonBloom");
        doctorList.add("William Thompson");
        doctorList.add("William Thompson");
        doctorList.add("William Thompson");

        setRecycleAdapter(doctorList);
    }

    private void setRecycleAdapter(ArrayList<String> doctorList) {
        recyclerViewDoctor.addItemDecoration(new SimpleDividerItemDecoration(this));
        DoctorListAdapter doctorListAdapter = new DoctorListAdapter(this, doctorList);
        recyclerViewDoctor.setAdapter(doctorListAdapter);
        recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(this));
    }
}
