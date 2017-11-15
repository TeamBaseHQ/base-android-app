package com.example.base.base.async.member;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.TeamNotFound;
import com.base.Models.Team;
import com.base.Models.User;
import com.example.base.base.singleton.BaseManager;

import java.util.List;

/**
 * Created by Devam on 15-Nov-17.
 */

public class ListTeamMemberAsync extends AsyncTask<String, Void, List<User>> {

    private static Base base;
    private String slug;
    ProgressDialog pb;
    Context context;
    //static List<User> users;

    public ListTeamMemberAsync(String slug,Context context){
        this.context = context;
        this.slug = slug;
        ListTeamMemberAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected List<User> doInBackground(String... params) {
        //requestHTTP(data);
        try {

            List<User> users = ListTeamMemberAsync.base.teamMemberService().getAllTeamMembers(this.slug);
            return users;

        } catch (BaseHttpException e) {
            e.printStackTrace();
            return null;
        } catch (TeamNotFound teamNotFound) {
            teamNotFound.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<User> result) {
        pb.dismiss();
        if(result == null)
        {
            Toast.makeText(context, "Team Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Fetching Team Member...");
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
