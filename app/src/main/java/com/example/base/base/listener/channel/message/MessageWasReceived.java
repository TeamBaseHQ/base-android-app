package com.example.base.base.listener.channel.message;

import android.app.Service;
import android.content.Intent;
import android.util.Log;

import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;

/**
 * Created by Devam on 22-Nov-17.
 */

public class MessageWasReceived implements ChannelEventListener {

    Service service;
    Intent intent;

    public MessageWasReceived(Intent intent, Service service)
    {
        this.service = service;
        this.intent = intent;
    }

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        intent.putExtra("eventName", eventName);
        intent.putExtra("channelName", channelName);
        intent.putExtra("data", data);
        service.sendBroadcast(intent);
    }

//    @Override
//    public void onAuthenticationFailure(String s, Exception e) {
//    }

    @Override
    public void onSubscriptionSucceeded(String s) {

    }
}
