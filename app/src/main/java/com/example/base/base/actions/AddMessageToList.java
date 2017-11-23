package com.example.base.base.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Devam on 23-Nov-17.
 */

public class AddMessageToList extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        String eventName = intent.getStringExtra("eventName");
        String channelName = intent.getStringExtra("channelName");

        if(eventName.equals("message.received")){
            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
        }
    }
}
