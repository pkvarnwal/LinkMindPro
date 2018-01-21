package com.linkmindpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class DoctorPatientInfoActivity extends AppCompatActivity {

    @BindView(R.id.title) TextView title;
    @BindView(R.id.text_view_notification) TextView textViewNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        textViewNotification.setVisibility(View.VISIBLE);
    }


}
