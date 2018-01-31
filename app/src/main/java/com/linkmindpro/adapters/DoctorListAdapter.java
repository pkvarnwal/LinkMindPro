package com.linkmindpro.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkmindpro.models.patient.PatientData;
import com.linkmindpro.utils.AppConstant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;
import com.linkmindpro.activities.ChatActivity;
import com.linkmindpro.utils.AppUtils;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.DoctorListViewHolder> implements AppConstant {

    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<PatientData> mPatientData;

    public DoctorListAdapter(Activity activity, ArrayList<PatientData> patientData) {
        mActivity = activity;
        mPatientData = patientData;
        mLayoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public DoctorListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.doctor_row_item, parent, false);

        return new DoctorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorListViewHolder holder, int position) {
        holder.bindData(mPatientData.get(position));

    }

    @Override
    public int getItemCount() {
        return mPatientData.size();
    }


    class DoctorListViewHolder extends  RecyclerView.ViewHolder {

        private PatientData patientData;

        @BindView(R.id.image_view_item)
        ImageView imageViewItem;
        @BindView(R.id.text_view_item)
        TextView textViewItem;

        DoctorListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(PatientData patientData) {
            this.patientData = patientData;
            if (patientData == null) return;
            if (patientData.getName() != null)
            textViewItem.setText(patientData.getName());
            AppUtils.getInstance().display(mActivity, patientData.getImage(), imageViewItem, R.drawable.ic_launcher_background);
        }

        @OnClick(R.id.relative_layout_row)
        void itemTapped() {
            Intent intent = new Intent(mActivity, ChatActivity.class);
            intent.putExtra(DATA, patientData);
            mActivity.startActivity(intent);
        }
    }
}
