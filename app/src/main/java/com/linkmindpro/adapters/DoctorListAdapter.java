package com.linkmindpro.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.activities.ChatActivity;
import com.linkmindpro.font.FontHelper;
import com.linkmindpro.models.patient.PatientData;
import com.linkmindpro.utils.AppConstant;
import com.linkmindpro.utils.AppUtils;
import com.linkmindpro.utils.SimpleDateFormatter;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.DoctorListViewHolder> implements AppConstant {

    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<PatientData> mPatientData;
    private ArrayList<PatientData> mCompleteList = new ArrayList<>();

    public DoctorListAdapter(Activity activity, ArrayList<PatientData> patientData) {
        mActivity = activity;
        mPatientData = patientData;
        mCompleteList.addAll(patientData);
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

    class DoctorListViewHolder extends RecyclerView.ViewHolder {

        private PatientData patientData;

        @BindView(R.id.image_view_item)
        ImageView imageViewItem;
        @BindView(R.id.text_view_item)
        TextView textViewItem;
        @BindView(R.id.text_view_urgent)
        TextView textViewUrgent;
        @BindView(R.id.text_view_last_message) TextView textViewLastMessage;
        @BindView(R.id.text_view_date) TextView textViewDate;
        @BindView(R.id.text_view_count) TextView textViewCount;
        @BindView(R.id.relative_layout_count) RelativeLayout relativeLayoutCount;

        DoctorListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textViewItem, textViewUrgent);
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textViewLastMessage, textViewDate, textViewCount);
        }

        void bindData(PatientData patientData) {
            this.patientData = patientData;
            if (patientData == null) return;
            if (patientData.getName() != null) textViewItem.setText(patientData.getName());
            if (!TextUtils.isEmpty(patientData.getMessage())) {
                textViewLastMessage.setVisibility(View.VISIBLE);
                textViewLastMessage.setText(patientData.getMessage());
            }
            if (patientData.getUrgent().equals("1")){
                textViewUrgent.setVisibility(View.VISIBLE);
            }
            if (patientData.getMessageCount() > 0)  {
                textViewCount.setVisibility(View.VISIBLE);
                textViewCount.setText(patientData.getMessageCount()+"");
            }
            if (!TextUtils.isEmpty(patientData.getDateTime())) {
                textViewDate.setVisibility(View.VISIBLE);
                textViewDate.setText(SimpleDateFormatter.getLastChatTime(patientData.getDateTime()));
            }
            AppUtils.getInstance().display(mActivity, patientData.getImage(), imageViewItem, R.drawable.ic_launcher_background);
        }

        @OnClick(R.id.relative_layout_row)
        void itemTapped() {
            Intent intent = new Intent(mActivity, ChatActivity.class);
            intent.putExtra(DATA, patientData);
            mActivity.startActivity(intent);
        }
    }

    public void getFilteredItem(String name) {
        mPatientData.clear();
        if (!name.equals("")) {
            for (int i = 0; i < mCompleteList.size(); i++) {
                if (mCompleteList.get(i).getName().toLowerCase().contains(name.toLowerCase()))
                    mPatientData.add(mCompleteList.get(i));
            }
        } else mPatientData.addAll(mCompleteList);
        notifyDataSetChanged();
    }
}
