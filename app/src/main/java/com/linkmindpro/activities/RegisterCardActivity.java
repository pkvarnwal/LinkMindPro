package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linkmindpro.font.FontHelper;
import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class RegisterCardActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_sign_in)
    TextView textViewSignIn;
    @BindView(R.id.edit_text_name_on_card)
    EditText editTextNameOnCard;
    @BindView(R.id.edit_text_card_number)
    EditText editTextCardNumber;
    @BindView(R.id.text_view_date)
    TextView textViewDate;
    @BindView(R.id.text_view_free_trail)
    TextView textViewFreeTrail;
    @BindView(R.id.button_subscribe_now)
    Button buttonSubscribeNow;
    @BindView(R.id.text_view_skip)
    TextView textViewSkip;
    @BindView(R.id.text_view_already_account)
    TextView textViewAlreadyAccount;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_card);
        ButterKnife.bind(this);

        setFonts();
        getIntentData();
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buttonSubscribeNow);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textViewAlreadyAccount, textViewSignIn, textViewSkip, textViewFreeTrail,
                editTextNameOnCard, editTextCardNumber);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(REGISTER)) {
            registerRequest = (RegisterRequest) getIntent().getSerializableExtra(REGISTER);
        }
    }

    @OnClick(R.id.text_view_skip)
    void skipTapped() {
        Intent intent = new Intent(this, RegisterFirstActivity.class);
        intent.putExtra(REGISTER, registerRequest);
        startActivity(intent);
    }
}
