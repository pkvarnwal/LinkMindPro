package com.linkmindpro.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.dialog.PopUpHelper;
import com.linkmindpro.font.FontHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.models.register.RegisterResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.FileUtils;
import com.linkmindpro.utils.Gallery;
import com.linkmindpro.utils.ImageHelper;
import com.linkmindpro.utils.PermissionClass;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.view.SnackBarFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterFirstActivity extends AppCompatActivity implements AppConstant {

    private final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int REQUEST_PERMISSION_CODE = 200;
    private static final int GALLERY_REQUEST = 2000;
    @BindView(R.id.image_view_profession)
    CircleImageView imageViewUser;
    private String profilePath;
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
    @BindView(R.id.text_view_privacy_policy)
    TextView textViewPrivacyPolicy;
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
        setFonts();
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buttonSubmit);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editTextProfession, textViewPrivacyPolicy);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(REGISTER)) {
            registerRequest = (RegisterRequest) getIntent().getSerializableExtra(REGISTER);
        }
    }

    @OnClick(R.id.image_view_profession)
    void tappedImage() {
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
            imageViewUser.setImageBitmap(ImageHelper.getBitmapFromPath(profileImagePath));
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

        if (!TextUtils.isEmpty(profilePath)) {
            String base64Image = Base64.encodeToString(ImageHelper.convertImageToByteArray(profilePath, 200, 200), Base64.DEFAULT);
            String imgType = "image/png";
//            registerRequest.setImage(base64Image);
            registerRequest.setImage(base64Image);
        }
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
