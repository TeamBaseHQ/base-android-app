package com.example.base.base.async.team;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.Http.InputError;
import com.base.Exceptions.TeamNotFound;
import com.base.Models.Team;
import com.example.base.base.singleton.BaseManager;
import com.example.base.base.team.TeamListActivity;

/**
 * Created by Devam on 21-Nov-17.
 */

public class InviteTeamMemberAsync extends AsyncTask<String, Void, Boolean> {

    String teamSlug,description,email;
    ProgressDialog pb;
    Context context;
    private static Base base;

    public InviteTeamMemberAsync(String teamSlug, String email,String description, Context context){
        this.teamSlug = teamSlug;
        this.description = description;
        this.email = email;
        this.context = context;
        InviteTeamMemberAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        //requestHTTP(data);
        try {
            boolean result = InviteTeamMemberAsync.base.invitationService()
                    .sendInvitation(teamSlug, email, description);
            return result;
        } catch (TeamNotFound e) {
            // show errors
            return false;
        } catch (InputError e) {
            // show errors
            // See: Handling Errors
            return false;
        } catch (BaseHttpException e) {
            // Log Error
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
        pb.setMessage("Invitation Sending...");
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setProgress(0);
        pb.setMax(100);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        pb.show();
    }
}


