package com.example.base.base.async.team;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Models.Team;
import com.example.base.base.singleton.BaseManager;
import com.example.base.base.team.TeamListActivity;

import java.util.List;

/**
 * Created by Devam on 14-Nov-17.
 */

public class ListTeamAsync extends AsyncTask<String, Void, String> {

    private static Base base;
    ProgressDialog pb;
    TeamListActivity context;
    List<Team> teams;

    public ListTeamAsync(TeamListActivity context){
        this.context = context;
        ListTeamAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected String doInBackground(String... params) {
        //requestHTTP(data);
        try {

            this.teams = ListTeamAsync.base.teamService().getAllTeams();
            return "Team Created";

        } catch (BaseHttpException e) {
            e.printStackTrace();
            return "Error :- "+e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        pb.dismiss();

        if(result.contains("Error"))
        {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
        else if(result.contains("Team Created"))
        {
            context.prepareMyTaskData(teams);
        }
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Getting teams...");
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