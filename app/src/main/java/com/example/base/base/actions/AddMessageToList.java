package com.example.base.base.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.base.Models.Message;
import com.example.base.base.serializ.AccessTokenSerializable;
import com.google.gson.Gson;

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
            Gson gson = new Gson();
            Message message = gson.fromJson(data, Message.class);
        }
    }
}
