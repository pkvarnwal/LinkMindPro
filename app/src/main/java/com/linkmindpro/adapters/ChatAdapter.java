package com.linkmindpro.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Activity mActivity;
    private ArrayList<String> mArrayList;
    private LayoutInflater mLayoutInflater;

    public ChatAdapter(Activity activity, ArrayList<String> arrayList) {
        mActivity = activity;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.chat_row_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData() {
        }
    }
}
