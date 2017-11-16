package com.example.base.base.async.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ChannelNotFound;
import com.base.Exceptions.Http.InputError;
import com.base.Models.Thread;
import com.example.base.base.singleton.BaseManager;

import java.util.List;

/**
 * Created by Devam on 16-Nov-17.
 */

public class CreateThreadAsync extends AsyncTask<String, Void, String> {

    private static Base base;
    private String channelSlug,teamSlug,threadSubject,threadDesString;
    ProgressDialog pb;
    Context context;
    SharedPreferences sharedPreferences;
    //static List<User> users;

    public CreateThreadAsync(String threadSubject,String threadDescription,String teamSlug,String channelSlug,Context context){
        this.context = context;
        this.channelSlug = channelSlug;
        this.teamSlug = teamSlug;
        this.threadSubject = threadSubject;
        this.threadDesString = threadDescription;
        CreateThreadAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected String doInBackground(String... params) {
        //requestHTTP(data);
        try {
            sharedPreferences = context.getSharedPreferences("BASE",Context.MODE_PRIVATE);

            Thread thread = CreateThreadAsync.base.threadService().addChannelThread(teamSlug, channelSlug, threadSubject, threadDesString);
            return "Thread Created";

        } catch (InputError e) {
            e.printStackTrace();
            return  "Error :- "+e.getMessage();
        }catch (ChannelNotFound channelNotFound) {
            channelNotFound.printStackTrace();
            return  "Error :- "+channelNotFound.getMessage();
        }catch (BaseHttpException e) {
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
