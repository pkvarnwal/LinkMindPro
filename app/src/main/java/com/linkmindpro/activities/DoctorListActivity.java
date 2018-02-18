package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.adapters.DoctorListAdapter;
import com.linkmindpro.dialog.PopUpHelper;
import com.linkmindpro.font.FontHelper;
import com.linkmindpro.http.DataManager;
import com.linkmindpro.http.ErrorManager;
import com.linkmindpro.models.donotdisturb.DoNotDisturbRequest;
import com.linkmindpro.models.donotdisturb.DoNotDisturbResponse;
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
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class DoctorListActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    TextView textViewTitle;
    @BindView(R.id.text_view_notification)
    TextView textViewNotification;
    @BindView(R.id.edit_text_search)
    SearchView searchView;
    @BindView(R.id.recycler_view_doctor)
    RecyclerView recyclerViewDoctor;
    @BindView(R.id.relative_layout_root)
    RelativeLayout relativeLayoutRoot;
    @BindView(R.id.relative_layout_dnd)
    RelativeLayout relativeLayoutDND;
    @BindView(R.id.text_view_dnd)
    TextView textViewDND;
    @BindView(R.id.image_view_setting)
    ImageView imageViewSetting;
    @BindView(R.id.image_view_edit_profile)
    ImageView imageViewEditProfile;

    @BindString(R.string.new_message)
    String stringNewMessage;
    @BindString(R.string.please_wait)
    String stringPleaseWait;
    boolean isVisible;
    private DoctorListAdapter doctorListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        ButterKnife.bind(this);
        setFonts();
        updateUi();
        patientList();
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, searchView, textViewDND);
    }

    private void patientList() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(DoctorListActivity.this, relativeLayoutRoot, getString(R.string.no_internet_message));
            return;
        }
        LoginData loginData = AppPreference.getAppPreference(this).getObject(PREF_LOGINDATA, LoginData.class);
        PatientRequest patientRequest = new PatientRequest();

        patientRequest.setUserId(loginData.getId());

        ProgressHelper.start(this, stringPleaseWait);

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
        imageViewSetting.setVisibility(View.VISIBLE);
        imageViewEditProfile.setVisibility(View.VISIBLE);
        relativeLayoutDND.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(textWatcher);
    }

    private void setRecycleAdapter(ArrayList<PatientData> patientData) {
        recyclerViewDoctor.addItemDecoration(new SimpleDividerItemDecoration(this));
        doctorListAdapter = new DoctorListAdapter(this, patientData);
        recyclerViewDoctor.setAdapter(doctorListAdapter);
        recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.image_view_edit_profile)
    void editProfileTapped() {
        if (isVisible) {
            searchView.setVisibility(View.GONE);
            isVisible = false;
        } else {
            searchView.setVisibility(View.VISIBLE);
            isVisible = true;
        }
    }

    @OnClick(R.id.image_view_setting)
    void settingTapped() {
        Intent intent = new Intent(this, DoctorSettingActivity.class);
        startActivity(intent);
    }

    private void doNotDisturb(boolean active) {
        if (!ConnectionDetector.isNetworkAvailable(this)) {
            SnackBarFactory.showNoInternetSnackBar(DoctorListActivity.this, relativeLayoutRoot, getString(R.string.no_internet_message));
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
                ErrorManager errorManager = new ErrorManager(DoctorListActivity.this, relativeLayoutRoot, response);
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

    SearchView.OnQueryTextListener textWatcher = new  SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            doctorListAdapter.getFilteredItem(newText);
            return false;
        }
    };

}
