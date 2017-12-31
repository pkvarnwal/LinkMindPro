package com.linkmindpro.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkmindpro.utils.AppConstant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;
import com.linkmindpro.activities.ChatActivity;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.DoctorListViewHolder> implements AppConstant {

    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mDoctorList;

    public DoctorListAdapter(Activity activity, ArrayList<String> doctorList) {
        mActivity = activity;
        mDoctorList = doctorList;
        mLayoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public DoctorListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.doctor_row_item, parent, false);

        return new DoctorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorListViewHolder holder, int position) {
        holder.bindData(mDoctorList.get(position));

    }

    @Override
    public int getItemCount() {
        return mDoctorList.size();
    }


    class DoctorListViewHolder extends  RecyclerView.ViewHolder {

        private String title;

        @BindView(R.id.image_view_item)
        ImageView imageViewItem;
        @BindView(R.id.text_view_item)
        TextView textViewItem;

        DoctorListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(String s) {
            title = s;
            textViewItem.setText(s);
        }

        @OnClick(R.id.relative_layout_row)
        void itemTapped() {
            Intent intent = new Intent(mActivity, ChatActivity.class);
            intent.putExtra(TITLE, title);
            mActivity.startActivity(intent);
        }
    }
}
