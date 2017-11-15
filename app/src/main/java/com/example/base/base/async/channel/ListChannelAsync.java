package com.example.base.base.async.channel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.TeamNotFound;
import com.base.Models.Channel;
import com.base.Models.User;
import com.example.base.base.async.member.ListTeamMemberAsync;
import com.example.base.base.singleton.BaseManager;

import java.util.List;

/**
 * Created by Devam on 15-Nov-17.
 */

public class ListChannelAsync extends AsyncTask<String, Void, List<Channel>> {

    private static Base base;
    private String slug;
    ProgressDialog pb;
    Context context;
    SharedPreferences sharedPreferences;
    //static List<User> users;

    public ListChannelAsync(String slug,Context context){
        this.context = context;
        this.slug = slug;
        ListChannelAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected List<Channel> doInBackground(String... params) {
        //requestHTTP(data);
        try {
            sharedPreferences = context.getSharedPreferences("BASE",Context.MODE_PRIVATE);

            List<Channel> channel = ListChannelAsync.base.channelService().getAllChannels(sharedPreferences.getString("teamSlug",""));
            return channel;

        } catch (BaseHttpException e) {
            e.printStackTrace();
            return null;
        } catch (TeamNotFound teamNotFound) {
            teamNotFound.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Channel> result) {
        pb.dismiss();
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

