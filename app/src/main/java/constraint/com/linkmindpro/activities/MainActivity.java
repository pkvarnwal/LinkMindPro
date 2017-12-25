package constraint.com.linkmindpro.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;
import constraint.com.linkmindpro.utils.ConnectionDetector;
import constraint.com.linkmindpro.utils.StringUtils;
import constraint.com.linkmindpro.view.SnackBarFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.relative_layout_root)
    RelativeLayout relativeLayoutRoot;

    @BindString(R.string.please_enter)
    String stringPleaseEnter;
    @BindString(R.string.enter_emailId)
    String stringEnterEmailId;
    @BindString(R.string.invalid_email)
    String stringInvalidEmail;
    @BindString(R.string.password)
    String stringPassword;
    @BindString(R.string.login_successfully)
    String stringLoginSuccessfully;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    void loginTapped() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(MainActivity.this, relativeLayoutRoot, getString(R.string.no_internet_message));
            return;
        }

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (validate(email, password))
            SnackBarFactory.showNoInternetSnackBar(MainActivity.this, relativeLayoutRoot, stringLoginSuccessfully);
    }

    private boolean validate(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringEnterEmailId);
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringInvalidEmail);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, relativeLayoutRoot, stringPleaseEnter + " " + stringPassword);
            return false;
        }

        return true;
    }
}
