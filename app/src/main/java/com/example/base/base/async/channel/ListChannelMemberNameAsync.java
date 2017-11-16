package com.example.base.base.async.channel;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ChannelNotFound;
import com.base.Exceptions.TeamNotFound;
import com.base.Models.User;
import com.example.base.base.singleton.BaseManager;

import java.util.List;

/**
 * Created by Devam on 16-Nov-17.
 */

public class ListChannelMemberNameAsync extends AsyncTask<String, Void, List<User>> {

    private static Base base;
    private String teamSlug,channelSlug;
    ProgressDialog pb;
    Context context;
    //static List<User> users;

    public ListChannelMemberNameAsync(String teamSlug, String channelSlug, Context context){
        this.context = context;
        this.channelSlug = channelSlug;
        this.teamSlug = teamSlug;
        ListChannelMemberNameAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected List<User> doInBackground(String... params) {
        //requestHTTP(data);
        try {

            List<User> users = ListChannelMemberNameAsync.base.channelMemberService().getAllChannelMembers(teamSlug, channelSlug);
            return users;

        } catch (BaseHttpException e) {
            e.printStackTrace();
            return null;
        }catch (ChannelNotFound channelNotFound) {
            channelNotFound.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<User> result) {
        pb.dismiss();
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Fetching Channel Members...");
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
