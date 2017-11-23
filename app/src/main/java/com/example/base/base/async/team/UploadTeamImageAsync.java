package com.example.base.base.async.team;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Models.Media;
import com.example.base.base.async.user.UploadImageAsync;
import com.example.base.base.singleton.BaseManager;

import java.io.File;

/**
 * Created by Devam on 23-Nov-17.
 */

public class UploadTeamImageAsync extends AsyncTask<String, Void, String> {

    ProgressDialog pb;
    Context context;
    File file;
    String teamSlug;
    private static Base base;

    public UploadTeamImageAsync(String teamSlug ,File file, Context context) {
        this.file = file;
        this.context = context;
        this.teamSlug = teamSlug;
        UploadTeamImageAsync.base = BaseManager.getInstance(context,true);
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Media media = UploadTeamImageAsync.base.teamService().uploadTeamPicture(teamSlug,file);
            return "Image Uploaded";
        } catch (BaseHttpException e) {
            e.printStackTrace();
            return "Error :-"+e.getMessage();
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
        pb.setMessage("Image Uploading...");
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setProgress(0);
        pb.setMax(100);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        pb.show();
    }
}



