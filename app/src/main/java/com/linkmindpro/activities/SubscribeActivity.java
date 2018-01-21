package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.linkmindpro.dialog.PopUpHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.models.register.RegisterResponse;
import com.linkmindpro.models.subscribe.SubsribeRequest;
import com.linkmindpro.models.subscribe.SubsribeResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.utils.StringUtils;
import com.linkmindpro.view.SnackBarFactory;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class SubscribeActivity extends AppCompatActivity implements AppConstant {


    @BindString(R.string.confirm_password)
    String stringConfirmPassword;
    @BindString(R.string.password_did_not_match)
    String stringPasswordDidNotMatch;
    @BindString(R.string.password)
    String stringPassword;
    @BindString(R.string.eight_char)
    String stringEightChar;
    @BindString(R.string.please_enter)
    String stringPleaseEnter;
    @BindString(R.string.enter_emailId)
    String stringEnterEmailId;
    @BindString(R.string.invalid_email)
    String stringInvalidEmail;


    @BindString(R.string.please_wait)
    String stringPleaseWait;
    @BindView(R.id.edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.button_subscribe)
    Button buttonSubsribe;
    @BindView(R.id.relative_layout_root)
    LinearLayout relativeLayoutRoot;

    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_subscribe)
    void continueTapped() {
        String email = editTextEmail.getText().toString();

        if (validate(email)) subscribe(email);
    }

    private void subscribe(String email) {
        final SubsribeRequest subsribeRequest = new SubsribeRequest();
        LoginData loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);
        subsribeRequest.setId(loginData.getId());
        subsribeRequest.setEmail(email);

        ProgressHelper.start(this, stringPleaseWait);

        DataManager.getInstance().subscribe(this, subsribeRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                String message = ((SubsribeResponse) response).getSubsribeData().getMessage();
                confirmPopUp(message);
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(SubscribeActivity.this, relativeLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });
    }

    private void confirmPopUp(String message) {
        PopUpHelper.showInfoAlertPopup(this, message, new PopUpHelper.InfoPopupListener() {
            @Override
            public void onConfirm() {
                finish();
            }
        });
    }

    private boolean validate(String email) {

        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringEnterEmailId);
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringInvalidEmail);
            return false;
        }

        return true;
    }
}
