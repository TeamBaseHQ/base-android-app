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

import com.base.Models.Team;
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.R;
import com.example.base.base.team.TeamList;
import com.example.base.base.team.TeamListActivity;

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

        sharedpreferences = getSharedPreferences("BASE", Context.MODE_PRIVATE);


        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                    Intent i;
                    if(sharedpreferences.contains("AccessTokenObject")) {
                        if(sharedpreferences.contains("teamSlug")) {
                            i = new Intent(SplashActivity.this, NavigationBarActivity.class);
                        }else{
                            i = new Intent(SplashActivity.this, TeamListActivity.class);
                        }
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
