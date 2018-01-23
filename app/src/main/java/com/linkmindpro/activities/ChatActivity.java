package com.linkmindpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkmindpro.adapters.ChatAdapter;
import com.linkmindpro.models.patient.PatientData;
import com.linkmindpro.utils.AppConstant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class ChatActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    TextView textViewTitle;
    @BindView(R.id.recycler_view_chat)
    RecyclerView recyclerViewChat;
    @BindView(R.id.image_view_send)
    ImageView imageViewSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            PatientData patientData = (PatientData) getIntent().getSerializableExtra(DATA);
            updateUi(patientData);
        }
    }

    private void updateUi(PatientData patientData) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (patientData.getName() != null) textViewTitle.setText(patientData.getName());
        setRecycleAdapter(arrayList);
    }

    private void setRecycleAdapter(ArrayList<String> arrayList) {
        ChatAdapter chatAdapter = new ChatAdapter(this, arrayList);
        recyclerViewChat.setAdapter(chatAdapter);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
    }
}
