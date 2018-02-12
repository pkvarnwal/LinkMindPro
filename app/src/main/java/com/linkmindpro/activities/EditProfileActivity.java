package com.linkmindpro.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.dialog.PopUpHelper;
import com.linkmindpro.font.FontHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.editprofile.EditProfileData;
import com.linkmindpro.models.editprofile.EditProfileRequest;
import com.linkmindpro.models.editprofile.EditProfileResponse;
import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.models.patient.PatientResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;
import com.linkmindpro.utils.AppUtils;
import com.linkmindpro.utils.ConnectionDetector;
import com.linkmindpro.utils.FileUtils;
import com.linkmindpro.utils.Gallery;
import com.linkmindpro.utils.ImageHelper;
import com.linkmindpro.utils.PermissionClass;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.view.SnackBarFactory;

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

    @BindView(R.id.text_view_name) TextView textViewName;
    @BindView(R.id.text_view_email) TextView textViewEmail;
    @BindView(R.id.text_view_profession) TextView textViewProfession;
    @BindView(R.id.text_view_phone) TextView textViewPhone;
    @BindView(R.id.text_view_address) TextView textViewAddress;
    @BindView(R.id.text_view_city) TextView textViewCity;
    @BindView(R.id.text_view_state) TextView textViewState;
    @BindView(R.id.text_view_zip) TextView textViewZip;
    @BindView(R.id.text_view_change_image) TextView textViewChangeImage;

    private LoginData loginData;
    private final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int REQUEST_PERMISSION_CODE = 200;
    private static final int GALLERY_REQUEST = 2000;
    private String profilePath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        setFont();
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

    private void confirmPopUp(String message) {
        PopUpHelper.showInfoAlertPopup(this, message, new PopUpHelper.InfoPopupListener() {
            @Override
            public void onConfirm() {
                finish();
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
        if (!TextUtils.isEmpty(profileData.getImage()))
        AppUtils.getInstance().display(this, profileData.getImage(), imageViewProfile, R.drawable.ic_user);
    }

    @OnClick(R.id.button_save)
    void saveTapped() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(EditProfileActivity.this, linearLayoutRoot, getString(R.string.no_internet_message));
            return;
        }
        EditProfileRequest editProfileRequest = new EditProfileRequest();
        String name =  editTextName.getText().toString();
        String email =  editTextEmail.getText().toString();
        String address =  editTextAddress.getText().toString();
        String city =  editTextCity.getText().toString();
        String state =  editTextState.getText().toString();
        String zip =  editTextZip.getText().toString();
        String profession =  editTextProfession.getText().toString();

//        if (!profilePath.equals("")) {
        if (!TextUtils.isEmpty(profilePath)) {
            String base64Image = Base64.encodeToString(ImageHelper.convertImageToByteArray(profilePath, 200, 200), Base64.DEFAULT);
            String imgType ="image/png";
            editProfileRequest.setImage(base64Image);
        }

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
                confirmPopUp("Profile updated successfully");
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(EditProfileActivity.this, linearLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });
    }


    @OnClick(R.id.image_view_profile) void imageTapped(){
        PermissionClass permissionClass = new PermissionClass(this);
        if (permissionClass.checkPermission(permissions)) {
            openGallery(GALLERY_REQUEST);
        } else {
            permissionClass.requestPermission(REQUEST_PERMISSION_CODE, permissions);
        }
    }


    private void openGallery(int requestCode) {
        Gallery gallery = new Gallery(this);
        gallery.openPhoto(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery(GALLERY_REQUEST);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case GALLERY_REQUEST:
                    profilePath = FileUtils.getPath(this, data.getData());
                    setPhoto(profilePath);
                    break;
            }
        }
    }

    private void setPhoto(String profileImagePath) {
        if (ImageHelper.getBitmapFromPath(profileImagePath) != null) {
            imageViewProfile.setImageBitmap(ImageHelper.getBitmapFromPath(profileImagePath));
        }
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_MEDIUM, editTextName, editTextEmail, editTextProfession,
                editTextPhone, editTextAddress, editTextCity, editTextState, editTextZip);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textViewName, textViewEmail, textViewProfession,
                textViewPhone, textViewAddress, textViewCity, textViewState, textViewZip, buttonSave,
                textViewCancel, textViewChangeImage);
    }
}
