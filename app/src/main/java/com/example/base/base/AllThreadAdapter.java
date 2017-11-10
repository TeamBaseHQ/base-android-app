package com.example.base.base;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Devam on 28-Oct-17.
 */

public class AllThreadAdapter extends RecyclerView.Adapter<AllThreadAdapter.AllThreadHolder>  {

    private List<AllThread> allThreadList;
    public AllThreadAdapter(List<AllThread> allThreadsList) { this.allThreadList = allThreadsList;}
    @Override
    public AllThreadAdapter.AllThreadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allthreads_recyclerview, parent, false);

        return new AllThreadAdapter.AllThreadHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllThreadAdapter.AllThreadHolder holder, int position) {
        AllThread allThread = allThreadList.get(position);

        holder.threadChannelName.setText(allThread.getThreadChannelName());
        holder.threadChannelName.setBackgroundResource(R.drawable.edittext_recyclerview_channelname);
        GradientDrawable drawable = (GradientDrawable) holder.threadChannelName.getBackground();
        drawable.setColor(Color.parseColor(allThread.getThreadChannelColor()));
        holder.threadName.setText(allThread.getThreadName());
        holder.threadTime.setText(allThread.getThreadTime());
        holder.threadMemberpic.setImageResource(allThread.getThreadMemberPic());

        //holder.message.setImageResource(channel.getChannelMessage());
    }

    @Override
    public int getItemCount() { return allThreadList.size(); }
    public class AllThreadHolder extends RecyclerView.ViewHolder {
        public TextView threadName, threadTime;
        public ImageView threadMemberpic;
        public EditText threadChannelName;

        public AllThreadHolder(View view) {
            super(view);
            threadName = (TextView) view.findViewById(R.id.tvAtrThreadName);
            threadTime = (TextView) view.findViewById(R.id.tvAtrTime);
            threadMemberpic = (ImageView) view.findViewById(R.id.ivAtrMemberPic);
            threadChannelName = view.findViewById(R.id.etAtrChannelName);
        }
    }
}