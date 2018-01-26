package com.linkmindpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.http.DataManager;
import com.linkmindpro.models.editprofile.EditProfileData;
import com.linkmindpro.models.editprofile.EditProfileRequest;
import com.linkmindpro.models.editprofile.EditProfileResponse;
import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.models.patient.PatientResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;
import com.linkmindpro.utils.ProgressHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_view_cancel)
    TextView textViewCancel;
    @BindView(R.id.image_view_profile)
    CircleImageView imageViewProfile;
    @BindView(R.id.edit_text_name)
    EditText editTextName;
    @BindView(R.id.relative_layout_name)
    RelativeLayout relativeLayoutName;
    @BindView(R.id.edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.relative_layout_email)
    RelativeLayout relativeLayoutEmail;
    @BindView(R.id.edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.edit_text_phone)
    EditText editTextPhone;
    @BindView(R.id.edit_text_fax)
    EditText editTextFax;
    @BindView(R.id.edit_text_address)
    EditText editTextAddress;
    @BindView(R.id.edit_text_city)
    EditText editTextCity;
    @BindView(R.id.edit_text_state)
    EditText editTextState;
    @BindView(R.id.edit_text_zip) EditText editTextZip;
    @BindView(R.id.edit_text_profession) EditText editTextProfession;
    @BindView(R.id.button_save)
    Button buttonSave;
    @BindView(R.id.linear_layout_root)
    LinearLayout linearLayoutRoot;
    private LoginData loginData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        getProfileRequest();
    }

    private void getProfileRequest() {
        EditProfileRequest profileRequest = new EditProfileRequest();
        loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);
        profileRequest.setUserId(loginData.getId());
        profileRequest.setAction("view");

        ProgressHelper.start(this, getString(R.string.please_wait));
        DataManager.getInstance().getProfile(this, profileRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;
                EditProfileResponse profileResponse = (EditProfileResponse) response;
                updateView(profileResponse);
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
            }
        });
    }

    private void updateView(EditProfileResponse profileResponse) {
        EditProfileData profileData = profileResponse.getEditProfileData();
        editTextName.setText(profileData.getName());
        editTextEmail.setText(profileData.getEmail());
        editTextAddress.setText(profileData.getAddress());
        editTextCity.setText(profileData.getCity());
        editTextState.setText(profileData.getState());
        editTextZip.setText(profileData.getZipcode());
        editTextProfession.setText(profileData.getProfession());
    }

    @OnClick(R.id.button_save)
    void saveTapped(){
        EditProfileRequest editProfileRequest = new EditProfileRequest();
        String name =  editTextName.getText().toString();
        String email =  editTextEmail.getText().toString();
        String address =  editTextAddress.getText().toString();
        String city =  editTextCity.getText().toString();
        String state =  editTextState.getText().toString();
        String zip =  editTextZip.getText().toString();
        String profession =  editTextProfession.getText().toString();

        editProfileRequest.setUserId(loginData.getId());
        editProfileRequest.setAction("edit");
        editProfileRequest.setName(name);
        editProfileRequest.setAddress(address);
        editProfileRequest.setCity(city);
        editProfileRequest.setState(state);
        editProfileRequest.setZipcode(zip);
        editProfileRequest.setProfession(profession);

        ProgressHelper.start(this, getString(R.string.please_wait));
        DataManager.getInstance().updateProfile(this, editProfileRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;
                EditProfileResponse profileResponse = (EditProfileResponse) response;
                updateView(profileResponse);
            }

            @Override
            public void onError(Object response) {

            }
        });
    }
}
