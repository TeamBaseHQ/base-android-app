package com.example.base.base.async.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ChannelNotFound;
import com.base.Exceptions.ThreadNotFound;
import com.base.Models.Thread;
import com.example.base.base.singleton.BaseManager;

import java.util.List;

/**
 * Created by Devam on 23-Nov-17.
 */

public class DeleteThreadAsync extends AsyncTask<String, Void, Boolean>
{
        private static Base base;
        private String channelSlug,teamSlug,threadSlug;
        ProgressDialog pb;
        Context context;
        SharedPreferences sharedPreferences;
        //static List<User> users;

        public DeleteThreadAsync(String teamSlug, String channelSlug, String threadSlug, Context context){
            this.context = context;
            this.channelSlug = channelSlug;
            this.teamSlug = teamSlug;
            this.threadSlug = threadSlug;
            DeleteThreadAsync.base = BaseManager.getInstance(context);
        }

        @Override
        protected Boolean doInBackground(String... params) {
        //requestHTTP(data);
            try {
                Boolean result = base.threadService()
                        .deleteChannelThread(teamSlug, channelSlug, threadSlug);
                return result;
            } catch (ThreadNotFound e) {
                // show errors
                // See: Handling Errors
                e.printStackTrace();
                return false;
            } catch (BaseHttpException e) {
                // Log Error
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

