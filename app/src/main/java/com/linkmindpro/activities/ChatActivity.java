package com.linkmindpro.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.linkmindpro.utils.AppUtils;
import com.linkmindpro.utils.FileUtils;
import com.linkmindpro.utils.Gallery;
import com.linkmindpro.utils.ImageHelper;
import com.linkmindpro.utils.PermissionClass;

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
    @BindView(R.id.image_view_send)
    ImageView imageViewSend;
    @BindView(R.id.image_view_profile)
    ImageView imageViewProfile;
    @BindView(R.id.image_view_attached)
    ImageView imageViewAttached;
    @BindView(R.id.image_view_imp)
    ImageView imageViewImp;
    @BindView(R.id.edit_text_chat)
    EditText editTextChat;
    private LoginData loginData;
    private PatientData patientData;
    private ArrayList<ChatData> chatDatas = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int REQUEST_PERMISSION_CODE = 200;
    private static final int GALLERY_REQUEST = 2000;
    private String profilePath;
    private String recieverId;
    boolean isMarkAsImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getIntentData();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("Image Updated"));
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUi();
        }
    };

    private void getIntentData() {
        loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);

        if (getIntent().hasExtra(DATA)) {
            patientData = (PatientData) getIntent().getSerializableExtra(DATA);
            updateUi();
            getChat();
        }
        if (getIntent().hasExtra(IS_PATIENT)) {
            updateUi();
            getChat();
        }
    }

    private void getChat() {
        recieverId = loginData.getRole().equals(PATIENT) ? loginData.getReferenceId() : patientData.getId();

        final GetChatRequest getChatRequest = new GetChatRequest();

        getChatRequest.setSenderId(loginData.getId());
        getChatRequest.setRecieverId(recieverId);

        DataManager.getInstance().getChat(this, getChatRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                GetChatResponse chatResponse = (GetChatResponse) response;
                if (chatResponse != null) {
                    chatDatas = chatResponse.getChatData();
                    setRecycleAdapter(chatDatas);
                }
            }

            @Override
            public void onError(Object response) {
                Log.v("err", "error");
            }
        });
    }

    private void updateUi() {
        LoginData loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);
        if (TextUtils.isEmpty(loginData.getRole())) return;
        if (loginData.getRole().equals(DOCTOR)) {
            imageViewImp.setVisibility(View.GONE);
        }
        if (patientData != null) textViewTitle.setText(patientData.getName());

        if (!TextUtils.isEmpty(loginData.getImage()))
            AppUtils.getInstance().display(this, loginData.getImage(), imageViewProfile, R.drawable.ic_user_profile);
    }

    private void setRecycleAdapter(ArrayList<ChatData> chatDatas) {
        chatAdapter = new ChatAdapter(this, chatDatas, loginData.getId());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);
//        recyclerViewChat.scrollToPosition(chatDatas.size());
        recyclerViewChat.setAdapter(chatAdapter);

        recyclerViewChat.scrollToPosition(recyclerViewChat.getAdapter().getItemCount() - 1);
    }


    @OnClick(R.id.image_view_send)
    void sendMesaageTapped() {
        String message = editTextChat.getText().toString();
        if (TextUtils.isEmpty(message) && TextUtils.isEmpty(profilePath)) return;

        SendChatRequest sendChatRequest = new SendChatRequest();
        sendChatRequest.setRecieverId(recieverId);

        sendChatRequest.setSenderId(loginData.getId());
        sendChatRequest.setUrgent(isMarkAsImp ? 1 : 0);
        imageViewAttached.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(profilePath)) {
            String base64Image = Base64.encodeToString(ImageHelper.convertImageToByteArray(profilePath, 50, 50), Base64.DEFAULT);
            sendChatRequest.setAttachment(base64Image);
            sendChatRequest.setMessage(null);
        } else {
            sendChatRequest.setMessage(message);
        }

        DataManager.getInstance().sendChat(this, sendChatRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                GetChatResponse chatResponse = (GetChatResponse) response;
                if (chatResponse == null) return;
                if (chatResponse.getChatData() != null && chatResponse.getChatData().size() > 0) {
                    imageViewAttached.setVisibility(View.GONE);
                    editTextChat.setText("");
                    if (chatAdapter == null) {
                        setRecycleAdapter(chatResponse.getChatData());
                    } else {
                        chatDatas.clear();
                        chatDatas.addAll(chatResponse.getChatData());
                        profilePath = null;
                        chatAdapter.notifyDataSetChanged();
                        recyclerViewChat.scrollToPosition(recyclerViewChat.getAdapter().getItemCount() - 1);
                    }
                }
            }

            @Override
            public void onError(Object response) {

            }
        });
    }

    @OnClick(R.id.image_view_camera)
    void cameraTapped() {
        PermissionClass permissionClass = new PermissionClass(this);
        if (permissionClass.checkPermission(permissions)) {
            openGallery(GALLERY_REQUEST);
        } else {
            permissionClass.requestPermission(REQUEST_PERMISSION_CODE, permissions);
        }
    }

    private void openGallery(int requestCode) {
        Gallery gallery = new Gallery(this);
        gallery.openPhoto(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery(GALLERY_REQUEST);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case GALLERY_REQUEST:
                    profilePath = FileUtils.getPath(this, data.getData());
                    setPhoto(profilePath);
                    break;
            }
        }
    }

    private void setPhoto(String profileImagePath) {
        if (ImageHelper.getBitmapFromPath(profileImagePath) != null) {
            imageViewAttached.setVisibility(View.VISIBLE);
            imageViewAttached.setImageBitmap(ImageHelper.getBitmapFromPath(profileImagePath));
        }
    }

    @OnClick(R.id.image_view_back)
    void backButtonTapped() {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_setting:
                startActivity(new Intent(this, DoctorSettingActivity.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.image_view_imp)
    void markAsImpTapped() {
        if (isMarkAsImp) {
            imageViewImp.setImageResource(R.drawable.ic_exclamation);
            isMarkAsImp = false;
        } else {
            imageViewImp.setImageResource(R.drawable.ic_active_exclamation);
            isMarkAsImp = true;
        }
    }
}

