package com.example.base.base.actions;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.base.Models.Message;
import com.example.base.base.R;
import com.example.base.base.message_format.MessageFragment;
import com.example.base.base.serializ.AccessTokenSerializable;
import com.google.gson.Gson;

/**
 * Created by Devam on 23-Nov-17.
 */

public class AddMessageToList extends BaseAction {

    public AddMessageToList(HandlesAction actionHandler) {
        super(actionHandler);
    }

    @Override
    public void handle(String eventName, String channelName, String data) {
        super.handle(eventName, channelName, data);
        // Send Notification
    }
}
