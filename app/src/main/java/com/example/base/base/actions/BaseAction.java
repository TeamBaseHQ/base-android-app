package com.example.base.base.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.base.Models.Message;
import com.example.base.base.listener.BaseListener;
import com.google.gson.Gson;

/**
 * Created by Devam on 24-Nov-17.
 */

abstract public class BaseAction extends BroadcastReceiver {
    private HandlesAction actionHandler = null;

    public BaseAction() {
        //
    }

    public BaseAction(HandlesAction actionHandler) {
        super();
        this.actionHandler = actionHandler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(BaseListener.DATA_TAG);
        String eventName = intent.getStringExtra(BaseListener.EVENT_TAG);
        String channelName = intent.getStringExtra(BaseListener.CHANNEL_TAG);

        if (this.actionHandler != null) {
            this.actionHandler.handle(eventName, channelName, data);
        } else {
            this.handle(eventName, channelName, data);
        }
    }

    public void handle(String eventName, String channelName, String data) {

    }
}

