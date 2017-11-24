package com.example.base.base.listener.message;

import android.app.Service;
import android.content.Intent;
import android.util.Log;

import com.example.base.base.listener.BaseListener;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;

/**
 * Created by Devam on 22-Nov-17.
 */

public class MessageWasReceived extends BaseListener {

    public static final String ACTION = "message.received";

    public MessageWasReceived(Intent intent, Service service) {
        super(intent, service);
    }

    @Override
    public String getActionName() {
        return ACTION;
    }
}
