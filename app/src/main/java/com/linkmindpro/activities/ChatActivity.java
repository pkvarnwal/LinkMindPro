package com.linkmindpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkmindpro.adapters.ChatAdapter;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.models.chat.ChatData;
import com.linkmindpro.models.chat.GetChatRequest;
import com.linkmindpro.models.chat.GetChatResponse;
import com.linkmindpro.models.chat.SendChatRequest;
import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.models.patient.PatientData;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class ChatActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    TextView textViewTitle;
    @BindView(R.id.recycler_view_chat)
    RecyclerView recyclerViewChat;
    @BindView(R.id.image_view_send) ImageView imageViewSend;
    @BindView(R.id.edit_text_chat)
    EditText editTextChat;
    private LoginData loginData;
    private PatientData patientData;
    private ArrayList<ChatData> chatDatas = new ArrayList<>();
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            patientData = (PatientData) getIntent().getSerializableExtra(DATA);
            //updateUi(patientData);

            getChat();
        }
    }

    private void getChat() {
        final GetChatRequest getChatRequest = new GetChatRequest();
        loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);
        getChatRequest.setSenderId(loginData.getId());
        getChatRequest.setRecieverId(patientData.getId());
        DataManager.getInstance().getChat(this, getChatRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                GetChatResponse chatResponse = (GetChatResponse) response;
                if (getChatRequest != null) {
                    chatDatas = chatResponse.getChatData();
                    setRecycleAdapter(chatDatas);
                }
            }

            @Override
            public void onError(Object response) {
                Log.e("err", "error");
            }
        });
    }
//
//    private void updateUi(PatientData patientData) {
//        ArrayList<String> arrayList = new ArrayList<>();
//        if (patientData.getName() != null) textViewTitle.setText(patientData.getName());
//        setRecycleAdapter(arrayList);
//    }

    private void setRecycleAdapter(ArrayList<ChatData> chatDatas) {
        chatAdapter = new ChatAdapter(this, chatDatas, loginData.getId());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);
//        recyclerViewChat.scrollToPosition(chatDatas.size());
        recyclerViewChat.setAdapter(chatAdapter);

        recyclerViewChat.scrollToPosition(recyclerViewChat.getAdapter().getItemCount() - 1);
    }


    @OnClick(R.id.image_view_send) void sendMesaageTapped(){
        String message = editTextChat.getText().toString();
        if (TextUtils.isEmpty(message)) return;

        SendChatRequest sendChatRequest = new SendChatRequest();
        sendChatRequest.setRecieverId(patientData.getId());
        sendChatRequest.setSenderId(loginData.getId());
        sendChatRequest.setMessage(message);
        DataManager.getInstance().sendChat(this, sendChatRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                GetChatResponse chatResponse = (GetChatResponse) response;
                if (chatResponse == null) return;
                if (chatResponse.getChatData() != null && chatResponse.getChatData().size() >0 ){
                    editTextChat.setText("");
                    chatDatas.clear();
                    chatDatas.addAll(chatResponse.getChatData());
                    chatAdapter.notifyDataSetChanged();
                    recyclerViewChat.scrollToPosition(recyclerViewChat.getAdapter().getItemCount() - 1);
                }
            }

            @Override
            public void onError(Object response) {

            }
        });

    }
}
