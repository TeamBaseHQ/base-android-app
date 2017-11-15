package com.example.base.base.async.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.UserNotFound;
import com.base.Models.User;
import com.example.base.base.singleton.BaseManager;
import com.example.base.base.user.LoginActivity;
import com.example.base.base.NavigationBarActivity;

/**
 * Created by Devam on 13-Nov-17.
 */

public class GetUserAsync extends AsyncTask<String, Void, String> {

    private static Base base;
    ProgressDialog pb;
    NavigationBarActivity context;
    User user;
    SharedPreferences sharedPreferences;

    public GetUserAsync(NavigationBarActivity context){
        this.context = context;
        GetUserAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected String doInBackground(String... params) {
        //requestHTTP(data);
        try {
            user = GetUserAsync.base.userService().getCurrentUser();
            sharedPreferences = context.getSharedPreferences("BASE", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("User_id",user.getId());
            editor.commit();
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            return "Error :- "+userNotFound.getMessage();
        }
        return "User Get Successfully";
    }

    @Override
    protected void onPostExecute(String result) {
        pb.dismiss();

        if(result.contains("Error"))
        {
            Intent i = new Intent(context,LoginActivity.class);
            context.startActivity(i);
        }
        else if(result.equals("User Get Successfully"))
        {
            context.setUserName(user.getName());
        }

    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Getting User Data...");
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