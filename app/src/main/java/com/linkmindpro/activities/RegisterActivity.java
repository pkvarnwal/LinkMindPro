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
import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.models.register.RegisterResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.utils.StringUtils;
import com.linkmindpro.view.SnackBarFactory;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class RegisterActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_already_account)
    TextView textViewAlreadyAccount;
    @BindView(R.id.text_view_sign_in)
    TextView textViewSignIn;
    @BindView(R.id.edit_text_name)
    EditText editTextName;
    @BindView(R.id.edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.edit_text_confirm_password)
    EditText editTextConfirmPassword;
    @BindView(R.id.button_continue)
    Button buttonContinue;
    @BindView(R.id.relative_layout_root)
    RelativeLayout relativeLayoutRoot;

    @BindString(R.string.confirm_password)
    String stringConfirmPassword;
    @BindString(R.string.password_did_not_match)
    String stringPasswordDidNotMatch;
    @BindString(R.string.password) String stringPassword;
    @BindString(R.string.eight_char) String stringEightChar;
    @BindString(R.string.please_enter) String stringPleaseEnter;
    @BindString(R.string.enter_emailId) String stringEnterEmailId;
    @BindString(R.string.invalid_email) String stringInvalidEmail;
    @BindString(R.string.please_wait) String stringPleaseWait;

    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.text_view_sign_in)
    void signInTapped() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.button_continue)
    void continueTapped() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (validate(name, email, password, confirmPassword)) register(name, email, password);
    }


    private void register(String name, String email, String password) {
        final RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(name);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);

        ProgressHelper.start(this, stringPleaseWait);

        DataManager.getInstance().register(this, name, email, password, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                String message = ((RegisterResponse) response).getRegisterData().getMessage();
                confirmPopUp(message);
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(RegisterActivity.this, relativeLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });
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

    private boolean validate(String name, String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(name)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + "full name");
            return false;
        }
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
        } else if (password.length() < 8) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + stringEightChar);
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + stringConfirmPassword);
            return false;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!Objects.equals(password, confirmPassword)) {
                SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPasswordDidNotMatch);
                return false;
            }
        }

        return true;
    }
}
