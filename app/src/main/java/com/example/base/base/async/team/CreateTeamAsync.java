package com.example.base.base.async.team;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Models.Team;
import com.example.base.base.singleton.BaseManager;
import com.example.base.base.team.TeamListActivity;

/**
 * Created by Devam on 13-Nov-17.
 */

public class CreateTeamAsync extends AsyncTask<String, Void, Team> {

    String teamName,teamDescription;
    ProgressDialog pb;
    Context context;
    private static Base base;

    public CreateTeamAsync(String teamName, String teamDescription, Context context){
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.context = context;
        CreateTeamAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected Team doInBackground(String... params) {
        //requestHTTP(data);
        try {
            Team team = CreateTeamAsync.base.teamService().createTeam(teamName, teamDescription);
            return team;
        } catch (BaseHttpException e) {
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

