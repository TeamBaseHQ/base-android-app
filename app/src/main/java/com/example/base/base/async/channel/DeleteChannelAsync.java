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
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.singleton.BaseManager;

/**
 * Created by Devam on 23-Nov-17.
 */

public class DeleteChannelAsync extends AsyncTask<String, Void, Boolean> {

    private static Base base;
    private String channelSlugName,teamSlug;
    ProgressDialog pb;
    Context context;

    public DeleteChannelAsync(String teamSlug, String channelSlugName, Context context){
        this.context = context;
        this.channelSlugName = channelSlugName;
        this.teamSlug = teamSlug;
        DeleteChannelAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        //requestHTTP(data);
        try {
            Boolean result = DeleteChannelAsync.base.channelService()
                    .deleteChannel(teamSlug, channelSlugName);
            return result;
        } catch (ChannelNotFound e) {
            // show errors
            // See: Handling Errors
            e.printStackTrace();
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
        pb.setMessage("Deleting Channels...");
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

