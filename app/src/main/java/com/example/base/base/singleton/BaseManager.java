package com.example.base.base.singleton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.base.Auth.AccessToken;
import com.base.Base;
import com.base.BaseClient;
import com.example.base.base.serializ.AccessTokenSerializable;
import com.example.base.base.user.LoginActivity;
import com.google.gson.Gson;
import com.pusher.client.connection.Connection;
import com.pusher.client.connection.ConnectionState;

/**
 * Created by Devam on 12-Nov-17.
 */

public class BaseManager{

   // private static BaseManager baseManager;
    private static Base base;
    private static SharedPreferences sharedPreferences;

    private BaseManager(){}

    public static  Base getInstance(Context context, Boolean ignoreCheck){
        // No instance created
        if(BaseManager.base == null)
        {
            BaseClient baseClient = new BaseClient();
            baseClient.setClientId("1").setClientSecret("jGjyBcjBZTdTxydacXGtLr6rLRsUq7yEoxNI4psr")
                    .setApiUrl("https://platform.baseapp.in/api");
            BaseManager.base = new Base(baseClient);
        }

        // Get the Access Token from SharedPreferences
        AccessToken accessToken = BaseManager.getAccessTokenFromSharedPreference(context);

        if(!ignoreCheck) {
            // Log user out
            if (accessToken == null) {
                BaseManager.logUserOut(context);
            } else {
                // Set the AccessToken
                base.getClient().setAccessToken(accessToken);
            }
        }

        Log.d("PUSHER", "CONNECTING....");
        Connection pusherConnection = PusherManager.getInstance(context).getConnection();

        Log.d("PUSHER", pusherConnection.getState().toString());
        if(pusherConnection.getState().equals(ConnectionState.CONNECTED)) {
            Log.d("PUSHER", pusherConnection.getSocketId());
            base.getClient().addHeader("X-Socket-ID", pusherConnection.getSocketId());
        }

        // Return the base object
        return base;
    }

    public static Base getInstance(Context context){
        return getInstance(context,false);
    }

    @Nullable
    public static AccessToken getAccessTokenFromSharedPreference(Context context)
    {
        // SharedPreferences not initialized
        if (sharedPreferences == null) {
            // Initialize SharedPreferences
            sharedPreferences = context.getSharedPreferences("BASE", Context.MODE_PRIVATE);
        }

        // Fetch AccessTokenObject
        String json = sharedPreferences.getString("AccessTokenObject", null);

        // No Access Token available
        if (json == null){
            return null;
        }


        // Make and return Access Token
        return BaseManager.makeAccessTokenFromJSON(json);
    }

    private static AccessToken makeAccessTokenFromJSON(String json) {
        Gson gson = new Gson();
        AccessTokenSerializable accessTokenSerializable = gson.fromJson(json, AccessTokenSerializable.class);
        return accessTokenSerializable.getAccessToken();
    }

    private static void logUserOut(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }
}
