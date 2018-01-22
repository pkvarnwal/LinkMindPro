package com.linkmindpro.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.models.login.LoginRequest;
import com.linkmindpro.models.login.LoginResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;
import com.linkmindpro.utils.ConnectionDetector;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.utils.StringUtils;
import com.linkmindpro.view.SnackBarFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements AppConstant {

    private ProgressDialog progressDialog;

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

//    String urlToCall = "https://www.linkmindpro.com/admin/web_service/register_api.php?name=anu&email=prinsu@gmail.com&password=12345&role=1&address=test12&state=up&city=mrt&zipcode=2323&phone=654665";
//    String urlToCall = "https://www.linkmindpro.com/admin/web_service/login_api.php?email=prinsu@gmail.com&password=12345";
//    String urlToCall = "https://www.linkmindpro.com/admin/web_service/register_api.php ?action=subscribe&ref_id=85&name=anu&email=anu@gmail.com.com&password=12345&address=test12&state=up&city=mrt&zipcode=2323&phone=654665";
//    String urlToCall = "https://www.linkmindpro.com/admin/web_service/register_api.php?action=subscribe&ref_id=85";
    String urlToCall = "https://www.linkmindpro.com/admin/web_service/register_api.php ?action=subscribe&ref_id=85&name=anu&email=anu@gmail.com.com&password=12345&address=test12&state=up&city=mrt&zipcode=2323&phone=654665";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    void loginTapped() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(LoginActivity.this, relativeLayoutRoot, getString(R.string.no_internet_message));
            return;
        }

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (validate(email, password)) {
            doLogin(email, password);
           // new HttpAsyncTaskClass().execute();
//            startActivity(new Intent(this, DoctorListActivity.class));
        }
    }

    @OnClick(R.id.text_view_sign_up)
    void signUpTapped() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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


    private void doLogin(String email, String password){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        ProgressHelper.start(this, getString(R.string.please_wait));
        DataManager.getInstance().login(this, loginRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;
                LoginResponse loginResponse = (LoginResponse) response;

                AppPreference.getAppPreference(LoginActivity.this).putObject(PREF_LOGINDATA, loginResponse.getLoginData());

                Intent intent = new Intent(LoginActivity.this, DoctorListActivity.class);
//                intent.putExtra(ID, loginResponse.getLoginData().getId());
                startActivity(intent);
                finish();

            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(LoginActivity.this, relativeLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });

    }

    public class HttpAsyncTaskClass extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            String responseData = null;
            URL url = null;
            BufferedReader br = null;
            HttpURLConnection conn = null;

            try {
                url = new URL(urlToCall);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setUseCaches(false);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer responseBuff = new StringBuffer();
                while ((responseData = br.readLine()) != null) {
                    responseBuff.append(responseData);
                }
                result = responseBuff.toString();

                Log.e("RESPONSE: ", result);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        if (br != null) {
                            br.close();
                            conn.disconnect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;

        }

        protected void onPostExecute(String result) {
            startActivity(new Intent(LoginActivity.this, DoctorListActivity.class));
            progressDialog.dismiss();
        }
    }
}
