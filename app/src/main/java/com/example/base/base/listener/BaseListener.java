package com.example.base.base.listener;

import android.app.Service;
import android.content.Intent;

import com.pusher.client.channel.ChannelEventListener;

/**
 * Created by Devam on 24-Nov-17.
 */

abstract public class BaseListener implements ChannelEventListener {


    Service service;
    Intent intent;

    public static final String EVENT_TAG = "eventName";
    public static final String CHANNEL_TAG = "channelSlug";
    public static final String DATA_TAG = "eventData";

    public BaseListener(Intent intent, Service service)
    {
        this.service = service;
        this.intent = intent;
    }

    abstract public String getActionName();

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        intent.putExtra(EVENT_TAG, eventName);
        intent.putExtra(CHANNEL_TAG, channelName);
        intent.putExtra(DATA_TAG, data);
        intent.setAction(this.getActionName());
        service.sendBroadcast(intent);
    }


    @Override
    public void onSubscriptionSucceeded(String s) {

    }

}
