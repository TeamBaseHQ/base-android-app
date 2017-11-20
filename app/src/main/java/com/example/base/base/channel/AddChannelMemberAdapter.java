package com.example.base.base.channel;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.base.base.R;

import java.util.List;

/**
 * Created by Devam on 20-Nov-17.
 */

public class AddChannelMemberAdapter extends RecyclerView.Adapter<AddChannelMemberAdapter.AddChannelMemberHolder>  {

    private List<AddChannelMember> addChannelMemberList;
    public AddChannelMemberAdapter(List<AddChannelMember> addChannelMembersList) {
        this.addChannelMemberList = addChannelMembersList;}
    @Override
    public AddChannelMemberAdapter.AddChannelMemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addchannelmember_recyclerview, parent, false);

        return new AddChannelMemberAdapter.AddChannelMemberHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddChannelMemberAdapter.AddChannelMemberHolder holder, int position) {
        AddChannelMember addChannelMember = addChannelMemberList.get(position);
        holder.memberName.setText(addChannelMember.getMemberName());
        holder.memberPic.setImageResource(addChannelMember.getMemberPic());
    }

    @Override
    public int getItemCount() { return addChannelMemberList.size(); }
    public class AddChannelMemberHolder extends RecyclerView.ViewHolder {
        public TextView memberName;
        public ImageView memberPic;

        public AddChannelMemberHolder(View view) {
            super(view);
            memberName = view.findViewById(R.id.tvAcmMemberName);
            memberPic = view.findViewById(R.id.ivAcmMemberPic);
        }
    }
}