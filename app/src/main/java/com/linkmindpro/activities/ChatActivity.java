package com.linkmindpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.linkmindpro.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class ChatActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title) TextView textViewTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(TITLE)) {
            updateUi(getIntent().getStringExtra(TITLE));
        }
    }

    private void updateUi(String title) {
        textViewTitle.setText(title);
    }
}
