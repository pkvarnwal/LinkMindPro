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

import com.linkmindpro.dialog.PopUpHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.models.register.RegisterResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.view.SnackBarFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterFirstActivity extends AppCompatActivity implements AppConstant {


    @BindString(R.string.please_enter)
    String stringPleaseEnter;
    @BindString(R.string.enter_emailId)
    String stringEnterEmailId;
    @BindString(R.string.invalid_email)
    String stringInvalidEmail;
    @BindString(R.string.please_wait)
    String stringPleaseWait;
    @BindView(R.id.edit_text_profession)
    EditText editTextProfession;
    @BindView(R.id.button_submit)
    Button buttonSubmit;
    @BindView(R.id.relative_layout_root)
    RelativeLayout relativeLayoutRoot;
    private RegisterRequest registerRequest;

    public String getStringPleaseEnter() {
        return stringPleaseEnter;
    }

    public void setStringPleaseEnter(String stringPleaseEnter) {
        this.stringPleaseEnter = stringPleaseEnter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(REGISTER)) {
            registerRequest = (RegisterRequest) getIntent().getSerializableExtra(REGISTER);
        }
    }

    @OnClick(R.id.button_submit)
    void submitTapped() {
        String profession = editTextProfession.getText().toString();
//        if (validate(profession)) openNextActivity(profession);
        if (validate(profession)) register(profession);
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
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, "Please enter profession");
            return false;
        }

        return true;
    }

    private void register(String profession) {
//        final RegisterRequest registerRequest = new RegisterRequest();

      registerRequest.setProfession(profession);

        ProgressHelper.start(this, stringPleaseWait);

        DataManager.getInstance().register(this, registerRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                String message = ((RegisterResponse) response).getRegisterData().getMessage();
                confirmPopUp(message);
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(RegisterFirstActivity.this, relativeLayoutRoot, response);
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
}
