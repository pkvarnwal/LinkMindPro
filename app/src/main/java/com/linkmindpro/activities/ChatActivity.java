package com.linkmindpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkmindpro.adapters.ChatAdapter;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class ChatActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title) TextView textViewTitle;
    @BindView(R.id.recycler_view_chat) RecyclerView recyclerViewChat;
    @BindView(R.id.image_view_send) ImageView imageViewSend;

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


    private void setRecycleAdapter(ArrayList<String> arrayList) {
        recyclerViewChat.addItemDecoration(new SimpleDividerItemDecoration(this));
        ChatAdapter chatAdapter = new ChatAdapter(this, arrayList);
    }
}
