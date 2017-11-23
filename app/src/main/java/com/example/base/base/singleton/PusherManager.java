package com.example.base.base.singleton;

import android.content.Context;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.util.HttpAuthorizer;

/**
 * Created by Devam on 23-Nov-17.
 */

public class PusherManager {

    private static Pusher pusher;

    public static Pusher getInstance(Context context)
    {
        if(PusherManager.pusher==null) {
            PusherOptions options = new PusherOptions();
            options.setCluster("ap2");

            PusherManager.pusher = new Pusher("a5fd65ffaebfdc49e7ef", options);
        }
        return PusherManager.pusher;
    }
}
