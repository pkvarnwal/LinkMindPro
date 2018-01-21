package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linkmindpro.dialog.PopUpHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.models.register.RegisterResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.ConnectionDetector;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.view.SnackBarFactory;

import java.util.logging.ErrorManager;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class RegisterReviewActivity extends AppCompatActivity implements AppConstant {

    private RegisterRequest mRegisterRequest;

    @BindView(R.id.text_view_sign_in)
    TextView textViewSignIn;
    @BindView(R.id.button_save)
    Button buttonSave;
    @BindView(R.id.linear_layout_root)
    LinearLayout linearLayoutRoot;

    @BindString(R.string.please_wait) String stringPleaseWait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_review);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(REGISTER)) {
            mRegisterRequest = (RegisterRequest) getIntent().getSerializableExtra(REGISTER);
        }
    }

    @OnClick(R.id.button_save)
    void saveTapped() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(RegisterReviewActivity.this, linearLayoutRoot, getString(R.string.no_internet_message));
            return;
        }

        register();
    }

    private void register() {

        if(mRegisterRequest == null) return;

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(mRegisterRequest.getName());
        registerRequest.setEmail(mRegisterRequest.getEmail());
        registerRequest.setPassword(mRegisterRequest.getPassword());
        registerRequest.setRole("1");
        registerRequest.setAddress(mRegisterRequest.getAddress());
        registerRequest.setState(mRegisterRequest.getState());
        registerRequest.setCity(mRegisterRequest.getCity());
        registerRequest.setZipcode(mRegisterRequest.getZipcode());
        registerRequest.setPhone(mRegisterRequest.getPhone());

        ProgressHelper.start(this, stringPleaseWait);
    }
}
