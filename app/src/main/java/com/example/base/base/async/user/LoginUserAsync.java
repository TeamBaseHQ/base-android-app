package com.example.base.base.async.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
    LoginActivity context;
    String email, password;
    private static Base base;
    SharedPreferences sharedPreferences;

    public LoginUserAsync(String email, String password, LoginActivity context) {
        this.email = email;
        this.password = password;
        this.context = context;

        LoginUserAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected String doInBackground(String... params) {
        //requestHTTP(data);
        AccessToken accessToken = null;
        try {
            // Log in user and get access token
            accessToken = LoginUserAsync.base.getUserAccessToken(email, password);

            // Serialize the access token
            AccessTokenSerializable accessTokenSerializable = new AccessTokenSerializable();
            accessTokenSerializable.setAccessToken(accessToken);

            // Convert token to JSON
            Gson gson = new Gson();
            String json = gson.toJson(accessTokenSerializable);

            // Store token in sharedPreferences
            sharedPreferences = context.getSharedPreferences("BASE", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("AccessTokenObject", json);
            editor.commit();

        } catch (BaseException e) {
            e.printStackTrace();
        }
        return "Login Successful";
    }


        @Override
    protected void onPostExecute(String result) {
        pb.dismiss();

        if(result.contains("Error"))
        {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
        else if(result.contains("Login Successful"))
        {
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
            sharedPreferences = context.getSharedPreferences("BASE", Context.MODE_PRIVATE);
            Intent i;
            if(sharedPreferences.contains("teamSlug")) {
                i = new Intent(context, NavigationBarActivity.class);
            }
            else
            {
                i = new Intent(context, TeamListActivity.class);
            }
            context.startActivity(i);
            context.finish();
        }
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

