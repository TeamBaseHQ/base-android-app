package com.example.base.base.listener.channel.thread;

import android.app.Service;
import android.content.Intent;

import com.pusher.client.channel.SubscriptionEventListener;

/**
 * Created by Devam on 22-Nov-17.
 */

public class ThreadWasDeleted implements SubscriptionEventListener {

    Service service;
    Intent intent;
    public ThreadWasDeleted(Intent intent, Service service)
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
}
