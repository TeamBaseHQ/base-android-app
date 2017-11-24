package com.example.base.base.listener.thread;

import android.app.Service;
import android.content.Intent;

import com.example.base.base.listener.BaseListener;
import com.pusher.client.channel.SubscriptionEventListener;

/**
 * Created by Devam on 22-Nov-17.
 */

public class ThreadWasCreated extends BaseListener {

    public static final String ACTION = "thread.created";

    public ThreadWasCreated(Intent intent, Service service) {
        super(intent, service);
    }

    @Override
    public String getActionName() {
        return ACTION;
    }
}