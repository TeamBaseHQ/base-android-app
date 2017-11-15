package com.example.base.base.channel;

import android.widget.ImageView;

/**
 * Created by Devam on 03-Oct-17.
 */

public class ChannelItem {
    private String channelName,channelColor,channelSlug;
    private int channelMessage;

    public ChannelItem(){

    }
     public ChannelItem(String color, String name, int message)
     {
         this.channelColor = color;
         this.channelName = name;
         this.channelMessage = message;
     }

    public ChannelItem(String color, String name, String channelSlug)
    {
        this.channelColor = color;
        this.channelName = name;
        this.channelSlug = channelSlug;
    }

    public int getChannelMessage() {
        return channelMessage;
    }

    public String getChannelColor() {
        return channelColor;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelSlug() {
        return channelSlug;
    }

    public void setChannelColor(String channelColor) {
        this.channelColor = channelColor;
    }

    public void setChannelMessage(int channelMessage) {
        this.channelMessage = channelMessage;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setChannelSlug(String channelSlug) {
        this.channelSlug = channelSlug;
    }
}
