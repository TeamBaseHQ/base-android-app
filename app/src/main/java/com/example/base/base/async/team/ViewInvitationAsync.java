package com.example.base.base.async.team;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.Http.InputError;
import com.base.Exceptions.TeamNotFound;
import com.base.Models.Invitation;
import com.example.base.base.singleton.BaseManager;

import java.util.List;

/**
 * Created by Devam on 22-Nov-17.
 */

public class ViewInvitationAsync extends AsyncTask<String, Void, List<Invitation>> {

    String teamSlug;
    ProgressDialog pb;
    Context context;
    private static Base base;

    public ViewInvitationAsync(String teamSlug ,Context context){
        this.teamSlug = teamSlug;
        this.context = context;
        ViewInvitationAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected List<Invitation> doInBackground(String... params) {
        //requestHTTP(data);
        try {
            List<Invitation> invitations = base.invitationService()
                    .getAllInvitations(teamSlug);
            return invitations;
//    List<Invitation> invitations = base.invitationService()
//                                    .getAllInvitations(teamSlug, page);
//    List<Invitation> invitations = base.invitationService()
//                            .getAllInvitations(teamSlug, page, limit);
        } catch (TeamNotFound e) {
            // show errors
            e.printStackTrace();
            return null;
        } catch (BaseHttpException e) {
            // Log Error
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<Invitation> result) {
        pb.dismiss();
    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Fetching Invitations...");
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setProgress(0);
        pb.setMax(100);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        pb.show();
    }
}