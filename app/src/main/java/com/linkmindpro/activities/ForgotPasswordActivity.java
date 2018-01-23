package com.linkmindpro.activities;

import android.content.Intent;
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
import com.linkmindpro.models.forgot.ForgotRequest;
import com.linkmindpro.models.forgot.ForgotResponse;
import com.linkmindpro.utils.ConnectionDetector;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.utils.StringUtils;
import com.linkmindpro.view.SnackBarFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.button_submit) Button buttonSubmit;
    @BindView(R.id.edit_text_email) EditText editTextEmail;
    @BindView(R.id.linear_layout_root) LinearLayout linearLayoutRoot;
    @BindString(R.string.enter_emailId) String stringEnterEmailId;
    @BindString(R.string.invalid_email) String stringInvalidEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_submit)
    void submitTapped() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(ForgotPasswordActivity.this, linearLayoutRoot, getString(R.string.no_internet_message));
            return;
        }

        String email = editTextEmail.getText().toString();
        if (validate(email)) forgotPassword();
    }

    private void forgotPassword() {
        String email = editTextEmail.getText().toString();

        ForgotRequest forgotRequest = new ForgotRequest();
        forgotRequest.setEmail(email);
        ProgressHelper.start(this, getString(R.string.please_wait));

        DataManager.getInstance().forgot(this, forgotRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;
                ForgotResponse forgotResponse = (ForgotResponse) response;
                String message = forgotResponse.getForgotData().getMessage();
                confirmPopUp(message);
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(ForgotPasswordActivity.this, linearLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });
    }

    private boolean validate(String email) {
        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, linearLayoutRoot, stringEnterEmailId);
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, linearLayoutRoot, stringInvalidEmail);
            return false;
        }

        return true;
    }

    private void confirmPopUp(String message) {
        PopUpHelper.showInfoAlertPopup(this, message, new PopUpHelper.InfoPopupListener() {
            @Override
            public void onConfirm() {
                openLoginScreen();
            }
        });
    }

    private void openLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
