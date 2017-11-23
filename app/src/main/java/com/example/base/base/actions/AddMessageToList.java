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

public class AddMessageToList extends BroadcastReceiver {
    private MessageFragment messageFragment = null;

    public AddMessageToList(MessageFragment messageFragment) {
        this.messageFragment = messageFragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        String eventName = intent.getStringExtra("eventName");
        String channelName = intent.getStringExtra("channelName");


        /*//code for notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.appicon);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");
        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());*/

        if(eventName.equals("message.received")){
            Log.d("LODU", data);
            Gson gson = new Gson();
            Message message = gson.fromJson(data, Message.class);

            if (this.messageFragment == null){
                // Send Notification ;)
            } else {
                this.messageFragment.handleIncomingMessage(message);
            }// try kar. open kar k
            //what if messageFragment null hoga ?
        }
    }
}
