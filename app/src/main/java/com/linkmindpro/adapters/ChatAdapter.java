package com.linkmindpro.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkmindpro.models.chat.ChatData;
import com.linkmindpro.utils.AppUtils;
import com.linkmindpro.utils.ImageHelper;
import com.linkmindpro.utils.SimpleDateFormatter;

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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        @BindView(R.id.text_view_user_message) TextView textViewUserMessage;
        @BindView(R.id.text_view_other_emergency) TextView textViewOtherEmergency;
        @BindView(R.id.text_view_user_emergency) TextView textViewUserEmergency;
        @BindView(R.id.relative_layout_current_user) RelativeLayout relativeLayoutCurrentUser;
        @BindView(R.id.image_view_user) ImageView imageViewUser;
        @BindView(R.id.image_view_other) ImageView imageViewOther;

        ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ChatData chatData) {

            if (chatData.getSenderId().equals(mUserId)) {
                relativeLayoutOtherUser.setVisibility(View.GONE);
                relativeLayoutCurrentUser.setVisibility(View.VISIBLE);
                textViewUserDate.setText(SimpleDateFormatter.getCurrentChatTimeStatus(chatData.getDateTime()));
                if (!TextUtils.isEmpty(chatData.getAttachment())) {
                    imageViewUser.setVisibility(View.VISIBLE);
                    AppUtils.getInstance().display(mActivity, chatData.getAttachment(), imageViewUser, null);
                    textViewUserMessage.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(chatData.getMessage())) {
                    textViewUserMessage.setVisibility(View.VISIBLE);
                    textViewUserMessage.setText(chatData.getMessage());
                }
                textViewUserEmergency.setVisibility(chatData.getUrgent() == 0 ? View.GONE : View.VISIBLE);

                return;
            }

            relativeLayoutOtherUser.setVisibility(View.VISIBLE);
            relativeLayoutCurrentUser.setVisibility(View.GONE);
            textViewOtherDate.setText(SimpleDateFormatter.getCurrentChatTimeStatus(chatData.getDateTime()));
            textViewOtherEmergency.setVisibility(chatData.getUrgent() == 0 ? View.GONE : View.VISIBLE);
            if (!TextUtils.isEmpty(chatData.getAttachment())) {
                imageViewOther.setVisibility(View.VISIBLE);
                AppUtils.getInstance().display(mActivity, chatData.getAttachment(), imageViewOther, null);
                textViewOtherMessage.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(chatData.getMessage())) {
                textViewOtherMessage.setVisibility(View.VISIBLE);
                textViewOtherMessage.setText(chatData.getMessage());
            }
        }
    }
}
