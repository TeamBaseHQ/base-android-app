package com.example.base.base;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Devam on 03-Oct-17.
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelHolder>  {

    private List<Channel> channelList;
    public ChannelAdapter(List<Channel> channelsList) { this.channelList = channelsList;}
    @Override
    public ChannelAdapter.ChannelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_recyclerview, parent, false);

        return new ChannelAdapter.ChannelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChannelAdapter.ChannelHolder holder, int position) {
        Channel channel = channelList.get(position);
        /*LayerDrawable shape = (LayerDrawable) ContextCompat.getDrawable(holder.,R.drawable.rounded_corner);
        GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);*/
        holder.color.setBackgroundResource(R.drawable.rounded_corner);
        GradientDrawable drawable = (GradientDrawable) holder.color.getBackground();
        drawable.setColor(Color.parseColor(channel.getChannelColor()));
        holder.Name.setText(channel.getChannelName());
        holder.Name.setTextSize(20);
        //holder.message.setImageResource(channel.getChannelMessage());
    }

    @Override
    public int getItemCount() { return channelList.size(); }
    public class ChannelHolder extends RecyclerView.ViewHolder {
        public TextView color, Name;
        public ImageView message;

        public ChannelHolder(View view) {
            super(view);
            color = (TextView) view.findViewById(R.id.tvCrColor);
            Name = (TextView) view.findViewById(R.id.tvCrChannelName);
            message = (ImageView) view.findViewById(R.id.ivCrNumberOfMessage);
        }
    }
}
