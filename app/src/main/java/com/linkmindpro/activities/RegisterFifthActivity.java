package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class RegisterFifthActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_sign_in)
    TextView textViewSignIn;
    @BindView(R.id.edit_text_insurance_name)
    EditText editTextInsuranceName;
    @BindView(R.id.edit_text_id_number)
    EditText editTextIdNumber;
    @BindView(R.id.edit_text_group_number)
    EditText editTextGroupNumber;
    @BindView(R.id.edit_text_state)
    EditText editTextState;
    @BindView(R.id.edit_text_phone)
    EditText editTextPhone;
    @BindView(R.id.relative_layout_mail)
    LinearLayout relativeLayoutMail;
    @BindView(R.id.button_fifth_continue)
    Button buttonFifthContinue;
    @BindView(R.id.text_view_skip)
    TextView textViewSkip;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fifth);
        ButterKnife.bind(this);
        getIntentData();
    }

    @OnClick(R.id.button_fifth_continue)
    void continueTapped() {
        Intent intent = new Intent(this, RegisterReviewActivity.class);
        intent.putExtra(REGISTER, getInsuranceData());
        startActivity(intent);
    }

    @OnClick(R.id.text_view_skip)
    void skipTapped() {
        Intent intent = new Intent(this, RegisterReviewActivity.class);
        intent.putExtra(REGISTER, registerRequest);
        startActivity(intent);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(REGISTER)) {
            registerRequest = (RegisterRequest) getIntent().getSerializableExtra(REGISTER);
        }
    }

    RegisterRequest getInsuranceData() {
        String insuranceName = editTextInsuranceName.getText().toString();
        String idNumber = editTextIdNumber.getText().toString();
        String groupNumber = editTextGroupNumber.getText().toString();
        String state = editTextState.getText().toString();
        String phone = editTextPhone.getText().toString();

        if (!TextUtils.isEmpty(insuranceName)) registerRequest.setInsuranceName(insuranceName);
        if (!TextUtils.isEmpty(idNumber)) registerRequest.setInsuranceId(idNumber);
        if (!TextUtils.isEmpty(groupNumber)) registerRequest.setInsuranceGroup(groupNumber);
        if (!TextUtils.isEmpty(state)) registerRequest.setInsuranceState(state);
        if (!TextUtils.isEmpty(phone)) registerRequest.setInsurancePhone(phone);

        return registerRequest;
    }
}
