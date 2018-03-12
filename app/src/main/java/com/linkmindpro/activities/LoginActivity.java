package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.font.FontHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.login.LoginRequest;
import com.linkmindpro.models.login.LoginResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;
import com.linkmindpro.utils.ConnectionDetector;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.utils.StringUtils;
import com.linkmindpro.view.SnackBarFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class LoginActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.text_view_sign_up)
    TextView textViewSignUp;
    @BindView(R.id.text_view_forgot_password)
    TextView textViewForgotPassword;
    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.relative_layout_root)
    RelativeLayout relativeLayoutRoot;

    @BindString(R.string.please_enter)
    String stringPleaseEnter;
    @BindString(R.string.enter_emailId)
    String stringEnterEmailId;
    @BindString(R.string.invalid_email)
    String stringInvalidEmail;
    @BindString(R.string.password)
    String stringPassword;
    @BindString(R.string.login_successfully)
    String stringLoginSuccessfully;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setFont();
    }

    @OnClick(R.id.button_login)
    void loginTapped() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(LoginActivity.this, relativeLayoutRoot, getString(R.string.no_internet_message));
            return;
        }

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (validate(email, password)) doLogin(email, password);
    }

    @OnClick(R.id.text_view_sign_up)
    void signUpTapped() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.text_view_forgot_password)
    void forgotPasswordTapped() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    private boolean validate(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringEnterEmailId);
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringInvalidEmail);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + stringPassword);
            return false;
        }

        return true;
    }

    private void doLogin(String email, String password){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        ProgressHelper.start(this, getString(R.string.please_wait));
        DataManager.getInstance().login(this, loginRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;
                LoginResponse loginResponse = (LoginResponse) response;

                AppPreference.getAppPreference(LoginActivity.this).putObject(PREF_LOGINDATA, loginResponse.getLoginData());

                if (loginResponse.getLoginData().getRole().equals("Patient")) {
                    Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(IS_PATIENT, true);
                    startActivity(intent);
                    finish();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, DoctorListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(LoginActivity.this, relativeLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editTextEmail, editTextPassword,
                textViewSignUp, textViewForgotPassword);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buttonLogin);
    }
}
