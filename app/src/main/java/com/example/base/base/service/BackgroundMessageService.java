package com.example.base.base.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.base.BaseClient;
import com.example.base.base.async.channel.ListChannelAsync;
import com.example.base.base.async.channel.ListChannelMemberNameAsync;
import com.example.base.base.listener.channel.ChannelMemberWasAdded;
import com.example.base.base.listener.channel.ChannelMemberWasRemoved;
import com.example.base.base.listener.channel.ChannelWasCreated;
import com.example.base.base.listener.channel.ChannelWasDeleted;
import com.example.base.base.listener.channel.ChannelWasUpdated;
import com.example.base.base.listener.channel.message.MessageWasReceived;
import com.example.base.base.listener.channel.thread.ThreadWasCreated;
import com.example.base.base.listener.channel.thread.ThreadWasDeleted;
import com.example.base.base.listener.channel.thread.ThreadWasUpdated;
import com.example.base.base.singleton.BaseManager;
import com.example.base.base.singleton.PusherManager;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.util.HttpAuthorizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devam on 22-Nov-17.
 */


public class BackgroundMessageService extends Service{

    public static final String BROADCAST_ACTION = "com.base";
    Intent intent;
    public static Pusher pusher;
    HashMap<String,ChannelEventListener> events;

    @Override
    public void onCreate()
    {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent1, int startId) {

        BackgroundMessageService.pusher = PusherManager.getInstance(getApplicationContext());

        prepareEvent();

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("BASE", Context.MODE_PRIVATE);
            new ListChannelAsync(sharedPreferences.getString("teamSlug", ""), getApplicationContext()) {
                @Override
                protected void onPostExecute(List<com.base.Models.Channel> result) {
                    prepareChannel(result);
                    BackgroundMessageService.pusher.connect();
                }
            }.execute();
        }catch(Exception e){

        }

    }

    public void prepareChannel(List<com.base.Models.Channel> channels)
    {
        if(channels==null || channels.isEmpty())
        {
            return;
        }
        for (com.base.Models.Channel channel: channels) {
            subscribeToChannel(channel.getSlug());
        }
    }

    public void subscribeToChannel(String channelSlug)
    {
        try {
            Channel channel = BackgroundMessageService.pusher.subscribe("channel." + channelSlug);

            for (Map.Entry<String, ChannelEventListener> event : events.entrySet()) {
                channel.bind(event.getKey(), event.getValue());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void prepareEvent()
    {
        events = new HashMap<>();
//        events.put("channel.created",new ChannelWasCreated(intent,this));
//        events.put("channel.updated",new ChannelWasUpdated(intent,this));
//        events.put("channel.deleted",new ChannelWasDeleted(intent,this));
//        events.put("channel.member_added",new ChannelMemberWasAdded(intent,this));
//        events.put("channel.member_removed",new ChannelMemberWasRemoved(intent,this));
//        events.put("thread.created",new ThreadWasCreated(intent,this));
//        events.put("thread.updated",new ThreadWasUpdated(intent,this));
//        events.put("thread.deleted",new ThreadWasDeleted(intent,this));
        events.put("message.received",new MessageWasReceived(intent,this));
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
    }
}
