package com.example.base.base.async.channel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.TeamNotFound;
import com.base.Models.Channel;
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.singleton.BaseManager;

import java.util.List;

/**
 * Created by Devam on 16-Nov-17.
 */

public class CreateChannelAsync extends AsyncTask<String, Void, String> {

    private static Base base;
    private String channelName,channelDescription,color;
    private Boolean is_private;
    ProgressDialog pb;
    Context context;
    SharedPreferences sharedPreferences;
    //static List<User> users;

    public CreateChannelAsync(String channelName,String channelDescription,String color,Boolean is_private,Context context){
        this.context = context;
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.color = color;
        this.is_private = is_private;
        CreateChannelAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected String doInBackground(String... params) {
        //requestHTTP(data);
        try {
            sharedPreferences = context.getSharedPreferences("BASE",Context.MODE_PRIVATE);
            Channel channel = CreateChannelAsync.base.channelService().createChannel(sharedPreferences.getString("teamSlug",""), this.channelName, this.channelDescription, this.color, this.is_private);
            return "Channel Created";

        } catch (BaseHttpException e) {
            e.printStackTrace();
            return "Error :- "+e.getMessage();
        } catch (TeamNotFound teamNotFound) {
            teamNotFound.printStackTrace();
            return "Error :- "+teamNotFound.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        pb.dismiss();

        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        if(result.equals("Channel Created"))
        {
            Intent i = new Intent(context, NavigationBarActivity.class);
            context.startActivity(i);
        }
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Fetching Channels...");
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
