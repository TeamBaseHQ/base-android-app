package com.example.base.base.async.team;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.Http.InputError;
import com.base.Exceptions.TeamNotFound;
import com.example.base.base.singleton.BaseManager;

/**
 * Created by Devam on 21-Nov-17.
 */

public class DeleteTeamMemberAsync extends AsyncTask<String, Void, Boolean> {

    String teamSlug;
    int memberId;
    ProgressDialog pb;
    Context context;
    private static Base base;

    public DeleteTeamMemberAsync(String teamSlug,int memberId , Context context){
        this.teamSlug = teamSlug;
        this.context = context;
        this.memberId = memberId;
        DeleteTeamMemberAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {

        try {
            Boolean result = DeleteTeamMemberAsync.base.teamMemberService()
                    .deleteTeamMember(teamSlug, String.valueOf(memberId));
            return result;
        } catch (TeamNotFound e) {
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
