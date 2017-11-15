package com.example.base.base.channel;

import android.widget.ImageView;

/**
 * Created by Devam on 03-Oct-17.
 */

public class Channel {
    private String channelName,channelColor;
    private int channelMessage;

    public Channel(){

    }
     public Channel(String color,String name,int message)
     {
         this.channelColor = color;
         this.channelName = name;
         this.channelMessage = message;
     }

    public Channel(String color,String name)
    {
        this.channelColor = color;
        this.channelName = name;
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

    public void setChannelColor(String channelColor) {
        this.channelColor = channelColor;
    }

    public void setChannelMessage(int channelMessage) {
        this.channelMessage = channelMessage;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
