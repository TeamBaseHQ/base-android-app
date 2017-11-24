package com.example.base.base.actions;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.base.Models.Channel;
import com.example.base.base.R;
import com.google.gson.Gson;

/**
 * Created by Devam on 24-Nov-17.
 */

public class AddChannelMemberToList extends BaseAction {

    private Context context;

    public AddChannelMemberToList(HandlesAction actionHandler) {
        super(actionHandler);
    }

    public AddChannelMemberToList(HandlesAction actionHandler,Context context) {
        super(actionHandler);
        this.context = context;
    }

    @Override
    public void handle(String eventName, String channelName, String data) {
        super.handle(eventName, channelName, data);
        //Code for norification
        Channel channel = (new Gson()).fromJson(data,Channel.class);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.context);
        mBuilder.setSmallIcon(R.drawable.appicon);
        mBuilder.setContentTitle("Member Added To "+channel.getName());
        //mBuilder.setContentText(channel.getTeam().get+"");
        NotificationManager mNotificationManager = (NotificationManager)
                this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }
}
