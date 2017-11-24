package com.example.base.base.async.message;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ChannelNotFound;
import com.base.Exceptions.ThreadNotFound;
import com.base.Models.Media;
import com.base.Models.Message;
import com.example.base.base.singleton.BaseManager;

import java.io.File;

/**
 * Created by Devam on 24-Nov-17.
 */

public class UploadFileAsync extends AsyncTask<String, Void, Media[]> {

    private static Base base;
    private String teamSlug,channelSlug,threadSlug,content,type;
    File[] file;
    ProgressDialog pb;
    Context context;
    SharedPreferences sharedPreferences;
    //static List<User> users;

    public UploadFileAsync(String teamSlug, String channelSlug, File[] file, Context context){
        this.context = context;
        this.teamSlug = teamSlug;
        this.channelSlug = channelSlug;
        this.threadSlug = threadSlug;
        this.content = content;
        this.type = type;
        this.file = file;
        UploadFileAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected Media[] doInBackground(String... params) {
        //requestHTTP(data);
        try {
            Media[] media = UploadFileAsync.base.channelService()
                    .uploadMedia(this.teamSlug, this.channelSlug, file);
            return media;

        } catch (ChannelNotFound channelNotFound) {
            channelNotFound.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Media[] result) {
        pb.dismiss();
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("File Uploading...");
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
