package com.example.base.base.async.message;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ThreadNotFound;
import com.base.Models.Message;
import com.example.base.base.singleton.BaseManager;

import java.io.File;

/**
 * Created by Devam on 24-Nov-17.
 */

public class SendFileAsync extends AsyncTask<String, Void, String> {

    private static Base base;
    private String teamSlug,channelSlug,threadSlug,content,type;
    int[] files;
    ProgressDialog pb;
    Context context;
    SharedPreferences sharedPreferences;
    //static List<User> users;

    public SendFileAsync(String teamSlug, String channelSlug, String threadSlug, String content, String type, int[] files, Context context){
        this.context = context;
        this.teamSlug = teamSlug;
        this.channelSlug = channelSlug;
        this.threadSlug = threadSlug;
        this.content = content;
        this.type = type;
        this.files = files;
        SendFileAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected String doInBackground(String... params) {
        //requestHTTP(data);
        try {
            Message message = SendFileAsync.base.messageService()
                    .createMessage(this.teamSlug, this.channelSlug, this.threadSlug, this.content, this.type,files);
            return "Message Sent";

        } catch (BaseHttpException e) {
            e.printStackTrace();
            return "Error :- "+e.toString();
        } catch (ThreadNotFound threadNotFound) {
            threadNotFound.printStackTrace();
            return "Error :- "+threadNotFound.getMessage();
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
        pb.setMessage("Sending Message...");
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