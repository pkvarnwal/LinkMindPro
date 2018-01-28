package com.linkmindpro.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.models.chat.ChatData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Activity mActivity;
    private ArrayList<ChatData> mChatDatas;
    private String mUserId;
    private LayoutInflater mLayoutInflater;

    public ChatAdapter(Activity activity, ArrayList<ChatData> chatDatas, String userId) {
        mActivity = activity;
        mChatDatas = chatDatas;
        mUserId = userId;
        mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.chat_row_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.bindData(mChatDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatDatas.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_other_date)
        TextView textViewOtherDate;
        @BindView(R.id.text_view_other_message)
        TextView textViewOtherMessage;
        @BindView(R.id.relative_layout_other_user)
        RelativeLayout relativeLayoutOtherUser;
        @BindView(R.id.text_view_user_date)
        TextView textViewUserDate;
        @BindView(R.id.text_view_user_message)
        TextView textViewUserMessage;
        @BindView(R.id.relative_layout_current_user)
        RelativeLayout relativeLayoutCurrentUser;

        ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ChatData chatData) {

            if (chatData.getSenderId().equals(mUserId)) {
                relativeLayoutOtherUser.setVisibility(View.GONE);
                relativeLayoutCurrentUser.setVisibility(View.VISIBLE);
                textViewUserDate.setText(chatData.getDateTime());
                textViewUserMessage.setText(chatData.getMessage());

                return;
            }

            relativeLayoutOtherUser.setVisibility(View.VISIBLE);
            relativeLayoutCurrentUser.setVisibility(View.GONE);
            textViewOtherDate.setText(chatData.getDateTime());
            textViewOtherMessage.setText(chatData.getMessage());
        }
    }
}
