package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.dialog.PopUpHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.changepassword.ChangeRequest;
import com.linkmindpro.models.changepassword.ChangeResponse;
import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;
import com.linkmindpro.utils.ConnectionDetector;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.view.SnackBarFactory;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class ChangePasswordActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    TextView textViewTitle;
    @BindView(R.id.edit_text_current_password)
    EditText editTextCurrentPassword;
    @BindView(R.id.edit_text_new_password)
    EditText editTextNewPassword;
    @BindView(R.id.edit_text_confirm_new_password)
    EditText editTextConfirmNewPassword;
    @BindView(R.id.button_continue)
    Button buttonContinue;
    @BindView(R.id.relative_layout_root)
    RelativeLayout relativeLayoutRoot;
    @BindString(R.string.confirm_password)
    String stringConfirmPassword;
    @BindString(R.string.password_did_not_match)
    String stringPasswordDidNotMatch;
    @BindString(R.string.new_password)
    String stringNewPassword;
    @BindString(R.string.eight_char)
    String stringEightChar;
    @BindString(R.string.please_enter)
    String stringPleaseEnter;
    @BindString(R.string.please_wait)
    String stringPleaseWait;
    @BindString(R.string.current_password)
    String stringCurrentPassword;
    @BindString(R.string.reset_password)
    String stringResetPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        textViewTitle.setText(stringResetPassword);
    }

    @OnClick(R.id.button_continue)
    void buttonContinueTapped() {

        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmNewPassword.getText().toString();

        if (validate(currentPassword, newPassword, confirmPassword)) {
            changePassword(currentPassword, newPassword);
        }
    }

    private void changePassword(String currentPassword, String newPassword) {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(ChangePasswordActivity.this, relativeLayoutRoot, getString(R.string.no_internet_message));
            return;
        }

        LoginData loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);
        ChangeRequest changeRequest = new ChangeRequest();
        changeRequest.setUserId(loginData.getId());
        changeRequest.setOldpassword(currentPassword);
        changeRequest.setPassword(newPassword);
        changeRequest.setCpassword(newPassword);

        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().changePassword(this, changeRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;
                String message = ((ChangeResponse) response).getChangeData().getMessage();
                confirmPopUp(message);
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(ChangePasswordActivity.this, relativeLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });
    }

    private boolean validate(String currentPassword, String newPassword, String confirmPassword) {
        if (TextUtils.isEmpty(currentPassword)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + stringCurrentPassword);
            return false;
        }
        if (TextUtils.isEmpty(newPassword)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + stringNewPassword);
            return false;
        }
        if (newPassword.length() < 8) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + stringEightChar);
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + stringConfirmPassword);
            return false;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!Objects.equals(newPassword, confirmPassword)) {
                SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPasswordDidNotMatch);
                return false;
            }
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
