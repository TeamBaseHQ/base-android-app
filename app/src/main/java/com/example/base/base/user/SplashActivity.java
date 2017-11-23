package com.example.base.base.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.base.Models.Team;
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.R;
import com.example.base.base.async.team.GetTeamAsync;
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
                           /* new GetTeamAsync(sharedpreferences.getString("teamSlug",""),getApplicationContext()){
                                @Override
                                protected void onPostExecute(Team result) {
                                    Intent intent;
                                    if(result==null)
                                    {
                                        Toast.makeText(SplashActivity.this, "result1", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(SplashActivity.this, "You were removed or team is deleted", Toast.LENGTH_SHORT).show();
                                        intent = new Intent(SplashActivity.this, TeamListActivity.class);
                                    }
                                    else
                                    {
                                        Toast.makeText(SplashActivity.this, "result2", Toast.LENGTH_SHORT).show();
                                        intent = new Intent(SplashActivity.this, NavigationBarActivity.class);
                                    }
                                    Toast.makeText(SplashActivity.this, "result3", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                            }.execute();*/
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
