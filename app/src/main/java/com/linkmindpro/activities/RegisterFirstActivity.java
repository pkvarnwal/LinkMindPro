package com.linkmindpro.activities;

import android.content.Intent;
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
import com.linkmindpro.utils.StringUtils;
import com.linkmindpro.view.SnackBarFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class RegisterFirstActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_already_account)
    TextView textViewAlreadyAccount;
    @BindView(R.id.text_view_sign_in)
    TextView textViewSignIn;
    @BindView(R.id.text_view_hello)
    TextView textViewHello;
    @BindView(R.id.text_view_email_label)
    TextView textViewEmailLabel;
    @BindView(R.id.edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.button_first_continue)
    Button buttonContinue;
    @BindView(R.id.relative_layout_root)
    RelativeLayout relativeLayoutRoot;

    @BindString(R.string.please_enter)
    String stringPleaseEnter;
    @BindString(R.string.enter_emailId)
    String stringEnterEmailId;
    @BindString(R.string.invalid_email)
    String stringInvalidEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_first_continue)
    void continueTapped() {
        String email = editTextEmail.getText().toString();
        if (validate(email)) openNextActivity(email);
    }

    private void openNextActivity(String email) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);

        Intent intent = new Intent(this, RegisterSecondActivity.class);
        intent.putExtra(REGISTER, registerRequest);
        startActivity(intent);
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
