package com.example.base.base.async.channel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ChannelNotFound;
import com.example.base.base.singleton.BaseManager;

/**
 * Created by Devam on 21-Nov-17.
 */

public class DeleteChannelMemberAsync extends AsyncTask<String, Void, Boolean> {

    private static Base base;
    private String channelSlug,teamSlug;
    private int memberId;
    ProgressDialog pb;
    Context context;
    SharedPreferences sharedPreferences;
    //static List<User> users;

    public DeleteChannelMemberAsync(int memberId,String channelSlug,Context context){
        this.memberId = memberId;
        this.context = context;
        this.channelSlug = channelSlug;
        DeleteChannelMemberAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        //requestHTTP(data);
        try {
            sharedPreferences = context.getSharedPreferences("BASE",Context.MODE_PRIVATE);
            teamSlug = sharedPreferences.getString("teamSlug","");
            Log.d("MemberId",memberId+"");
            Log.d("teamSlug",teamSlug+"");
            Log.d("channelSlug",channelSlug+"");
            boolean result = DeleteChannelMemberAsync.base.channelMemberService()
                    .deleteChannelMember(teamSlug, channelSlug, String.valueOf(memberId));
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
        pb.setMessage("Deleting Member to Channel...");
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


