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

public class DisplayThreadAdapter extends RecyclerView.Adapter<DisplayThreadAdapter.DisplayThreadHolder>  {

    private List<DisplayThread> displayThreadList;
    public DisplayThreadAdapter(List<DisplayThread> displayThreadsList) { this.displayThreadList = displayThreadsList;}
    @Override
    public DisplayThreadAdapter.DisplayThreadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.displaythreads_recyclerview, parent, false);

        return new DisplayThreadAdapter.DisplayThreadHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DisplayThreadAdapter.DisplayThreadHolder holder, int position) {
        DisplayThread displayThread = displayThreadList.get(position);

        holder.threadMessage.setText(displayThread.getThreadMessage());
        holder.threadName.setText(displayThread.getThreadName());
        holder.threadTime.setText(displayThread.getThreadTime());
        holder.threadMemberpic.setImageResource(displayThread.getThreadMemberPic());

        //holder.message.setImageResource(channel.getChannelMessage());
    }

    @Override
    public int getItemCount() { return displayThreadList.size(); }
    public class DisplayThreadHolder extends RecyclerView.ViewHolder {
        public TextView threadName, threadTime, threadMessage;
        public ImageView threadMemberpic;

        public DisplayThreadHolder(View view) {
            super(view);
            threadName = (TextView) view.findViewById(R.id.tvDtrThreadName);
            threadTime = (TextView) view.findViewById(R.id.tvDtrTime);
            threadMessage = (TextView) view.findViewById(R.id.tvDtrMessage);
            threadMemberpic = (ImageView) view.findViewById(R.id.ivDtrMemberPic);

        }
    }
}