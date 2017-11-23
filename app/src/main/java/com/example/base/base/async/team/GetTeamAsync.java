package com.example.base.base.async.team;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.TeamNotFound;
import com.base.Models.Team;
import com.example.base.base.singleton.BaseManager;

/**
 * Created by Devam on 24-Nov-17.
 */

public class GetTeamAsync extends AsyncTask<String, Void, Team> {

    String teamSlug;
    ProgressDialog pb;
    Context context;
    private static Base base;

    public GetTeamAsync(String teamSlug, Context context){
        this.teamSlug = teamSlug;
        this.context = context;
        GetTeamAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected Team doInBackground(String... params) {
        //requestHTTP(data);
        try {
            Team team = GetTeamAsync.base.teamService().getTeam(teamSlug);
            return team;
        }catch (TeamNotFound teamNotFound) {
            teamNotFound.printStackTrace();
            return null;
        }catch (BaseHttpException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Team result) {
        pb.dismiss();
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Creating Team...");
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


