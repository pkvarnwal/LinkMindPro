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

public class RegisterFourthActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_already_account)
    TextView textViewAlreadyAccount;
    @BindView(R.id.text_view_sign_in)
    TextView textViewSignIn;
    @BindView(R.id.edit_text_pharmacy_name)
    EditText editTextPharmacyName;
    @BindView(R.id.edit_text_address)
    EditText editTextAddress;
    @BindView(R.id.edit_text_city)
    EditText editTextCity;
    @BindView(R.id.edit_text_state)
    EditText editTextState;
    @BindView(R.id.edit_text_zip)
    EditText editTextZip;
    @BindView(R.id.edit_text_phone)
    EditText editTextPhone;
    @BindView(R.id.button_fourth_continue)
    Button buttonFourthContinue;
    @BindView(R.id.text_view_skip)
    TextView textViewSkip;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fourth);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(REGISTER)) {
            registerRequest = (RegisterRequest) getIntent().getSerializableExtra(REGISTER);
        }
    }

    @OnClick(R.id.button_fourth_continue)
    void continueTapped() {
        Intent intent = new Intent(this, RegisterFifthActivity.class);
        intent.putExtra(REGISTER, getPersonalData());
        startActivity(intent);
    }

    @OnClick(R.id.text_view_skip)
    void skipTapped() {
        Intent intent = new Intent(this, RegisterFifthActivity.class);
        intent.putExtra(REGISTER, registerRequest);
        startActivity(intent);
    }

    private RegisterRequest getPersonalData() {
        String pharmacyName = editTextPharmacyName.getText().toString();
        String address = editTextAddress.getText().toString();
        String city = editTextCity.getText().toString();
        String state = editTextState.getText().toString();
        String zip = editTextZip.getText().toString();
        String phone = editTextPhone.getText().toString();

        if (!TextUtils.isEmpty(pharmacyName)) {
            registerRequest.setPharmacyName(pharmacyName);
        }
        if (!TextUtils.isEmpty(address)) {
            registerRequest.setPharmacyAddress(address);
        }
        if (!TextUtils.isEmpty(city)) {
            registerRequest.setPharmacyCity(city);
        }
        if (!TextUtils.isEmpty(state)) {
            registerRequest.setPharmacyState(state);
        }
        if (!TextUtils.isEmpty(zip)) {
            registerRequest.setPharmacyZipcode(zip);
        }
        if (!TextUtils.isEmpty(phone)) {
            registerRequest.setPharmacyPhone(phone);
        }

        return registerRequest;
    }


}
