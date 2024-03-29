package com.example.base.base.async.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.base.Auth.AccessToken;
import com.base.Base;
import com.base.Exceptions.BaseException;
import com.example.base.base.singleton.BaseManager;
import com.example.base.base.team.TeamListActivity;
import com.example.base.base.user.LoginActivity;
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.serializ.AccessTokenSerializable;
import com.google.gson.Gson;

/**
 * Created by Devam on 13-Nov-17.
 */

public class LoginUserAsync extends AsyncTask<String, Void, String> {

    ProgressDialog pb;
    Context context;
    String email, password;
    private static Base base;
    SharedPreferences sharedPreferences;

    public LoginUserAsync(String email, String password, Context context) {
        this.email = email;
        this.password = password;
        this.context = context;

        LoginUserAsync.base = BaseManager.getInstance(context,true);
    }

    @Override
    protected String doInBackground(String... params) {
        //requestHTTP(data);
        AccessToken accessToken = null;
        try {
            Log.d("AccessDone","getting token");
            // Log in user and get access token
            accessToken = LoginUserAsync.base.getUserAccessToken(email, password);
            Log.d("AccessDone","token gotcha");

            // Serialize the access token
            AccessTokenSerializable accessTokenSerializable = new AccessTokenSerializable();
            accessTokenSerializable.setAccessToken(accessToken);

            Log.d("AccessDone","token set");
            // Convert token to JSON
            Gson gson = new Gson();
            String json = gson.toJson(accessTokenSerializable);

            // Store token in sharedPreferences
            sharedPreferences = context.getSharedPreferences("BASE", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("AccessTokenObject", json);
            editor.commit();
            return "Login Successful";

        } catch (BaseException e) {
            e.printStackTrace();
            return "Error :- "+e.getMessage();
        }

    }


        @Override
    protected void onPostExecute(String result) {
        pb.dismiss();
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Checking Credentials...");
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setProgress(0);
        pb.setMax(100);
        /*progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("File downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar  */

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        pb.show();
    }
}

