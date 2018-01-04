package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit_profile:
                Intent intent = new Intent(DoctorListActivity.this, EditProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.item_logout:
                Intent loginIntent = new Intent(DoctorListActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
