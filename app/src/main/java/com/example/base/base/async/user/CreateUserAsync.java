package com.example.base.base.async.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.Http.InputError;
import com.base.Models.User;
import com.example.base.base.singleton.BaseManager;
import com.example.base.base.user.LoginActivity;

/**
 * Created by Devam on 13-Nov-17.
 */

public class CreateUserAsync extends AsyncTask<String, Void, String> {

    private static Base base;
    String name,email,password;
    ProgressDialog pb;
    Context context;

    public CreateUserAsync(String name, String email, String password, Context context){
        this.name = name;
        this.email = email;
        this.password = password;
        this.context = context;
        CreateUserAsync.base = BaseManager.getInstance(context);
    }

    @Override
    protected String doInBackground(String... params) {
        //requestHTTP(data);
        try {
            User createdUser = CreateUserAsync.base.userService().createUser(this.name, this.email, this.password);
            return "Register successfully";
        } catch (InputError e) {
            // show errors
            // See: Handling Errors
            e.printStackTrace();
            return "Error :-"+e.getMessage();

        } catch (BaseHttpException e) {
            // Log Error
            //Toast.makeText(RegisterActivity.this, "Error :- "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        else
        {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context,LoginActivity.class);
            i.putExtra("Email",email);
            i.putExtra("Password",password);
            context.startActivity(i);
        }

    }

    @Override
    protected void onPreExecute() {
        pb = new ProgressDialog(this.context);
        pb.setCancelable(false);
        pb.setMessage("Creating Account...");
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
