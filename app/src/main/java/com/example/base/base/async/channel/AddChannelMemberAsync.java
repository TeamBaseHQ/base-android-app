package com.example.base.base.async.channel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ChannelNotFound;
import com.base.Exceptions.TeamNotFound;
import com.base.Models.Channel;
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.singleton.BaseManager;

/**
 * Created by Devam on 20-Nov-17.
 */

public class AddChannelMemberAsync extends AsyncTask<String, Void, Boolean> {

    private static Base base;
    private String channelSlug,teamSlug;
    private int memberId;
    ProgressDialog pb;
    Context context;
    SharedPreferences sharedPreferences;
    //static List<User> users;

    public AddChannelMemberAsync(int memberId,String channelSlug,Context context){
        this.memberId = memberId;
        this.context = context;
        this.channelSlug = channelSlug;
        AddChannelMemberAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        //requestHTTP(data);
        try {
            sharedPreferences = context.getSharedPreferences("BASE",Context.MODE_PRIVATE);
            teamSlug = sharedPreferences.getString("teamSlug","");
            boolean result = base.channelMemberService()
                    .addChannelMember(teamSlug, channelSlug, String.valueOf(memberId));
            return result;

        } catch (BaseHttpException e) {
            e.printStackTrace();
            return false;
        } catch (ChannelNotFound channelNotFound) {
            channelNotFound.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onPostExecute(Boolean result) {
        pb.dismiss();
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Adding Member to Channel...");
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

