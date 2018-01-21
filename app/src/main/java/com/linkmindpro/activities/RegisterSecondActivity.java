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

import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.view.SnackBarFactory;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class RegisterSecondActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_already_account)
    TextView textViewAlreadyAccount;
    @BindView(R.id.text_view_sign_in)
    TextView textViewSignIn;
    @BindView(R.id.edit_full_name)
    EditText editFullName;
    @BindView(R.id.edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.edit_text_confirm_password)
    EditText editTextConfirmPassword;
    @BindView(R.id.button_second_continue)
    Button buttonSecondContinue;
    @BindView(R.id.relative_layout_root)
    RelativeLayout relativeLayoutRoot;

    @BindString(R.string.confirm_password) String stringConfirmPassword;
    @BindString(R.string.password_did_not_match) String stringPasswordDidNotMatch;
    @BindString(R.string.password) String stringPassword;
    @BindString(R.string.eight_char) String stringEightChar;
    @BindString(R.string.please_enter) String stringPleaseEnter;
    private RegisterRequest registerRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(REGISTER)) {
            registerRequest = (RegisterRequest) getIntent().getSerializableExtra(REGISTER);
        }
    }

    @OnClick(R.id.button_second_continue)
    void continueTapped() {
        String fullName = editFullName.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (validate(fullName, password, confirmPassword)) {
            openNextActivity(fullName, password);
        }
    }

    private void openNextActivity(String fullName, String password) {
        registerRequest.setName(fullName);
        registerRequest.setPassword(password);
        Intent intent = new Intent(this, RegisterThirdActivity.class);
        intent.putExtra(REGISTER, registerRequest);
        startActivity(intent);
    }

    private boolean validate(String fullName, String password, String confirmPassword) {
        if (TextUtils.isEmpty(fullName)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + "full name");
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
