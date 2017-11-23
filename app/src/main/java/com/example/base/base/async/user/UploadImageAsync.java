package com.example.base.base.async.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.base.Auth.AccessToken;
import com.base.Base;
import com.base.Exceptions.BaseException;
import com.base.Exceptions.BaseHttpException;
import com.base.Models.Media;
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.serializ.AccessTokenSerializable;
import com.example.base.base.singleton.BaseManager;
import com.example.base.base.team.TeamListActivity;
import com.example.base.base.user.LoginActivity;
import com.google.gson.Gson;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Devam on 23-Nov-17.
 */

public class UploadImageAsync extends AsyncTask<String, Void, String> {

    ProgressDialog pb;
    Context context;
    File file;
    private static Base base;

    public UploadImageAsync(File file, Context context) {
        this.file = file;
        this.context = context;

        UploadImageAsync.base = BaseManager.getInstance(context,true);
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("BASEUPLOAD", file.getAbsolutePath());
        //Paths path = new Paths();
        try {
            Media media = UploadImageAsync.base.userService().uploadProfilePicture(file);
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