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

import java.util.List;

/**
 * Created by Devam on 18-Nov-17.
 */

public class ListMessagesAsync extends AsyncTask<String, Void, List<Message>> {

    private static Base base;
    private String teamSlug,channelSlug,threadSlug;
    private int page;
    ProgressDialog pb;
    Context context;
    SharedPreferences sharedPreferences;
    //static List<User> users;

    public ListMessagesAsync(String teamSlug,String channelSlug,String threadSlug,int page,Context context){
        this.context = context;
        this.teamSlug = teamSlug;
        this.channelSlug = channelSlug;
        this.threadSlug = threadSlug;
        this.page = page;
        ListMessagesAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected List<Message> doInBackground(String... params) {
        //requestHTTP(data);
        try {
            List<Message> messages = ListMessagesAsync.base.messageService().getAllMessages(this.teamSlug, this.channelSlug, this.threadSlug,this.page);
            return messages;

        } catch (BaseHttpException e) {
            e.printStackTrace();
            return null;
        } catch (ThreadNotFound threadNotFound) {
            threadNotFound.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Message> result) {
        pb.dismiss();
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Loding...");
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
