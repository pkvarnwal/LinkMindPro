package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class RegisterThirdActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_already_account)
    TextView textViewAlreadyAccount;
    @BindView(R.id.text_view_sign_in)
    TextView textViewSignIn;
    @BindView(R.id.edit_text_phone)
    EditText editTextPhone;
    @BindView(R.id.edit_text_address)
    EditText editTextAddress;
    @BindView(R.id.edit_text_city)
    EditText editTextCity;
    @BindView(R.id.edit_text_state)
    EditText editTextState;
    @BindView(R.id.edit_text_zip)
    EditText editTextZip;
    @BindView(R.id.button_continue)
    Button buttonContinue;
    @BindView(R.id.text_view_skip)
    TextView textViewSkip;
    private RegisterRequest mRegisterRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_third);
        ButterKnife.bind(this);
        getIntentData();

    }

    private void getIntentData() {
        if (getIntent().hasExtra(REGISTER)) {
            mRegisterRequest = (RegisterRequest) getIntent().getSerializableExtra(REGISTER);
        }
    }

    @OnClick(R.id.button_continue)
    void continueTapped() {
        RegisterRequest registerRequest = getPersonalData();
        Intent intent = new Intent(this, RegisterCardActivity.class);
        intent.putExtra(REGISTER, registerRequest);
        startActivity(intent);
    }

    @OnClick(R.id.text_view_skip)
    void skipTapped() {
        Intent intent = new Intent(this, RegisterCardActivity.class);
        intent.putExtra(REGISTER, mRegisterRequest);
        startActivity(intent);
    }

    private RegisterRequest getPersonalData() {

        String address = editTextAddress.getText().toString();
        String city = editTextCity.getText().toString();
        String state = editTextState.getText().toString();
        String zip = editTextZip.getText().toString();
        String phone = editTextPhone.getText().toString();

        if (!TextUtils.isEmpty(phone)) {
            mRegisterRequest.setPhone(phone);
        }
        if (!TextUtils.isEmpty(address)) {
            mRegisterRequest.setAddress(address);
        }
        if (!TextUtils.isEmpty(city)) {
            mRegisterRequest.setCity(city);
        }
        if (!TextUtils.isEmpty(state)) {
            mRegisterRequest.setState(state);
        }
        if (!TextUtils.isEmpty(zip)) {
            mRegisterRequest.setZipcode(zip);
        }

        return mRegisterRequest;
    }
}
