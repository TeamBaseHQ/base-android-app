package com.example.base.base.handler;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.base.base.team.TeamListActivity;
import com.pusher.client.channel.SubscriptionEventListener;

/**
 * Created by Devam on 20-Nov-17.
 */

public class ChannelMessageHandler implements SubscriptionEventListener{

    private Activity context;

    public ChannelMessageHandler(Activity context)
    {
        this.context = context;
    }
    @Override
    public void onEvent(String channelName, String eventName, final String data) {
        /*context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}

