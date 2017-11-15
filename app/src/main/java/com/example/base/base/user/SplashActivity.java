package com.example.base.base.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.base.base.NavigationBarActivity;
import com.example.base.base.R;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    ProgressBar pb;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        pb = (ProgressBar) findViewById(R.id.pbS);
        iv = (ImageView) findViewById(R.id.ivSLogo);
        pb.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
        Animation an = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.alphaanim_splash);
        iv.setAnimation(an);

        //Code to add object in sharedpreferences
        sharedpreferences = getSharedPreferences("BASE", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        /*Gson gson = new Gson();
        String json = gson.toJson(base);
        editor.putString("BaseObject", base.toString());
        editor.commit();*/

        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);*/


        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                    Intent i;
                    if(sharedpreferences.contains("AccessTokenObject")) {
                        i = new Intent(SplashActivity.this,NavigationBarActivity.class);
                    }
                    else {
                        i = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    startActivity(i);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}