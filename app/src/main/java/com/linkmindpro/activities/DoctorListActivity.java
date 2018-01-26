package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.adapters.DoctorListAdapter;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.login.LoginData;
import com.linkmindpro.models.patient.PatientData;
import com.linkmindpro.models.patient.PatientRequest;
import com.linkmindpro.models.patient.PatientResponse;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppPreference;
import com.linkmindpro.utils.ConnectionDetector;
import com.linkmindpro.utils.ProgressHelper;
import com.linkmindpro.view.SimpleDividerItemDecoration;
import com.linkmindpro.view.SnackBarFactory;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class DoctorListActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title) TextView textViewTitle;
    @BindView(R.id.text_view_notification) TextView textViewNotification;
    @BindView(R.id.edit_text_search) EditText editTextSearch;
    @BindView(R.id.recycler_view_doctor) RecyclerView recyclerViewDoctor;
    @BindView(R.id.relative_layout_root) RelativeLayout relativeLayoutRoot;

    @BindString(R.string.new_message) String stringNewMessage;
    Toolbar toolbar;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        updateUi();
        patientList();
    }

    private void patientList() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(DoctorListActivity.this, relativeLayoutRoot, getString(R.string.no_internet_message));
            return;
        }
        LoginData loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);
        PatientRequest patientRequest = new PatientRequest();

//        patientRequest.setUserId("29");
        patientRequest.setUserId(loginData.getId());

        ProgressHelper.start(this, getString(R.string.please_wait));

        DataManager.getInstance().patientList(this, patientRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;
                PatientResponse patientResponse = (PatientResponse) response;
                if (patientResponse.getPatientData().size() > 0)
                setRecycleAdapter(patientResponse.getPatientData());
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(DoctorListActivity.this, relativeLayoutRoot, response);
                errorManager.handleErrorResponse();
            }
        });
    }

    private void updateUi() {
        textViewTitle.setText(stringNewMessage);
        textViewNotification.setVisibility(View.VISIBLE);
    }

    private void setRecycleAdapter(ArrayList<PatientData> patientData) {
        recyclerViewDoctor.addItemDecoration(new SimpleDividerItemDecoration(this));
        DoctorListAdapter doctorListAdapter = new DoctorListAdapter(this, patientData);
        recyclerViewDoctor.setAdapter(doctorListAdapter);
        recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_edit_profile:
                Intent intent = new Intent(DoctorListActivity.this, EditProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.item_reset_password:
                Intent resetIntent = new Intent(DoctorListActivity.this, ChangePasswordActivity.class);
                startActivity(resetIntent);
                break;

            case R.id.item_send_invite:
                Intent inviteIntent = new Intent(DoctorListActivity.this, SubscribeActivity.class);
                startActivity(inviteIntent);
                break;

            case R.id.item_logout:
                Intent loginIntent = new Intent(DoctorListActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                AppPreference.getAppPreference(this).remove(PREF_LOGINDATA);
                startActivity(loginIntent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
