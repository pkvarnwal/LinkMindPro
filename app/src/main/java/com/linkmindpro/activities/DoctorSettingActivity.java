package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.dialog.PopUpHelper;
import com.linkmindpro.font.FontHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.donotdisturb.DoNotDisturbRequest;
import com.linkmindpro.models.donotdisturb.DoNotDisturbResponse;
import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;
import com.linkmindpro.utils.AppUtils;
import com.linkmindpro.utils.ConnectionDetector;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.view.SnackBarFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorSettingActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_invite_patient)
    TextView textViewInvitePatient;
    @BindView(R.id.linear_layout_invite_patient)
    LinearLayout linearLayoutInvitePatient;
    @BindView(R.id.image_view_doctor)
    CircleImageView imageViewDoctor;
    @BindView(R.id.text_view_name)
    TextView textViewName;
    @BindView(R.id.text_view_profession)
    TextView textViewProfession;
    @BindView(R.id.text_view_count)
    TextView textViewCount;
    @BindView(R.id.image_view_arrow)
    ImageView imageViewArrow;
    @BindView(R.id.relative_layout_count)
    RelativeLayout relativeLayoutCount;
    @BindView(R.id.image_view_dnd)
    ImageView imageViewDnd;
    @BindView(R.id.switch_compat_dnd)
    SwitchCompat switchCompatDnd;
    @BindView(R.id.text_view_dnd_message)
    TextView textViewDndMessage;
    @BindView(R.id.text_view_edit_profile)
    TextView textViewEditProfile;
    @BindView(R.id.text_view_email)
    TextView textViewEmail;
    @BindView(R.id.text_view_address)
    TextView textViewAddress;
    @BindView(R.id.text_view_phone_label)
    TextView textViewPhoneLabel;
    @BindView(R.id.text_view_phone)
    TextView textViewPhone;
    @BindView(R.id.relative_layout_phone)
    RelativeLayout relativeLayoutPhone;
    @BindView(R.id.text_view_fax_label)
    TextView textViewFaxLabel;
    @BindView(R.id.text_view_fax)
    TextView textViewFax;
    @BindView(R.id.relative_layout_fax)
    RelativeLayout relativeLayoutFax;
    @BindView(R.id.relative_layout_edit_profile)
    RelativeLayout relativeLayoutEditProfile;
    @BindView(R.id.text_view_payment_method)
    TextView textViewPaymentMethod;
    @BindView(R.id.text_view_visa_label)
    TextView textViewVisaLabel;
    @BindView(R.id.text_view_visa)
    TextView textViewVisa;
    @BindView(R.id.relative_layout_payment)
    RelativeLayout relativeLayoutPayment;
    @BindView(R.id.text_view_help_and_support)
    TextView textViewHelpAndSupport;
    @BindView(R.id.text_view_reset_password)
    TextView textViewResetPassword;
    @BindView(R.id.text_view_logout)
    TextView textViewLogout;
    @BindView(R.id.linear_layout_root)
    LinearLayout linearLayoutRoot;
    @BindView(R.id.relative_layout_dnd)
    RelativeLayout RelativeLayoutDnd;

    @BindString(R.string.please_wait)
    String stringPleaseWait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_setting);
        ButterKnife.bind(this);
        setFont();
        updateUi();
    }

    private void updateUi() {
        LoginData loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);
        if (TextUtils.isEmpty(loginData.getRole())) return;
        if (loginData.getRole().equals("Patient")) {
            RelativeLayoutDnd.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(loginData.getName())) textViewName.setText(loginData.getName());
        if (!TextUtils.isEmpty(loginData.getImage()))
            AppUtils.getInstance().display(this, loginData.getImage(), imageViewDoctor, R.drawable.ic_user);
        if (!TextUtils.isEmpty(loginData.getProfession()))
            textViewProfession.setText(loginData.getProfession());
        if (!TextUtils.isEmpty(loginData.getEmail()))
            textViewEmail.setText(loginData.getEmail());
    }

    @OnClick(R.id.linear_layout_invite_patient)
    void invitePatientTapped() {
        startActivity(new Intent(this, SubscribeActivity.class));
    }

    @OnClick(R.id.relative_layout_dnd)
    void dndTapped() {
        doNotDisturb(true);
    }

    @OnClick(R.id.relative_layout_edit_profile)
    void editProfileTapped() {
        Intent intent = new Intent(DoctorSettingActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.text_view_reset_password)
    void resetPasswordTapped() {
        Intent resetIntent = new Intent(DoctorSettingActivity.this, ChangePasswordActivity.class);
        startActivity(resetIntent);
    }


    @OnClick(R.id.text_view_logout)
    void logoutTapped() {
        Intent loginIntent = new Intent(DoctorSettingActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        AppPreference.getAppPreference(this).remove(PREF_LOGINDATA);
        startActivity(loginIntent);
        finish();
    }

    private void doNotDisturb(boolean active) {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(DoctorSettingActivity.this, linearLayoutRoot, getString(R.string.no_internet_message));
            return;
        }

        LoginData loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);

        DoNotDisturbRequest doNotDisturbRequest = new DoNotDisturbRequest();
        doNotDisturbRequest.setMessage("I am currently unavailable now.");
        doNotDisturbRequest.setUserId(loginData.getId());
        doNotDisturbRequest.setDnd(active ? "1" : "0");

        ProgressHelper.start(this, stringPleaseWait);

        DataManager.getInstance().doNotDisturb(this, doNotDisturbRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                ProgressHelper.stop();
                String message = ((DoNotDisturbResponse) response).getDoNotDisturbData().getMessage();
                confirmPopUp(message);

            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(DoctorSettingActivity.this, linearLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });
    }

    private void confirmPopUp(String message) {
        PopUpHelper.showInfoAlertPopup(this, message, new PopUpHelper.InfoPopupListener() {
            @Override
            public void onConfirm() {
            }
        });
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_MEDIUM, textViewProfession, textViewAddress,
                textViewDndMessage, textViewInvitePatient, textViewFax, textViewPhone, textViewEmail);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textViewName, textViewEditProfile,
                textViewPaymentMethod, textViewHelpAndSupport, textViewLogout, textViewResetPassword,
                switchCompatDnd);
    }
}
