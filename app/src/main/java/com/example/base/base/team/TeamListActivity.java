package com.example.base.base.team;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.base.Models.Team;
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.async.team.DeleteTeamAsync;
import com.example.base.base.async.thread.DeleteThreadAsync;
import com.example.base.base.channel.ChannelItem;
import com.example.base.base.handler.ChannelMessageHandler;
import com.example.base.base.helper.Helper;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.R;
import com.example.base.base.async.team.ListTeamAsync;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

public class TeamListActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView rvAllTeamListRecyclerView;
    private TeamListAdapter teamListAdapter;
    private List<TeamList> allTeamList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        this.setTitle("Teams");

        fab = (FloatingActionButton) findViewById(R.id.fabTlAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TeamListActivity.this,CreateTeamActivity.class);
                startActivity(i);
            }
        });

        rvAllTeamListRecyclerView = (RecyclerView) findViewById(R.id.rvTlTeamName);
        allTeamList.clear();
        teamListAdapter = new TeamListAdapter(allTeamList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(TeamListActivity.this);
        rvAllTeamListRecyclerView.setLayoutManager(rLayoutManager);
        rvAllTeamListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvAllTeamListRecyclerView.addItemDecoration(new DividerItemDecoration(TeamListActivity.this, LinearLayoutManager.VERTICAL));
        rvAllTeamListRecyclerView.setAdapter(teamListAdapter);

        rvAllTeamListRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvAllTeamListRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TeamList teamList = allTeamList.get(position);
                sharedPreferences = getApplicationContext().getSharedPreferences("BASE", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("teamSlug", teamList.getTeamSlug());
                editor.putString("teamName", teamList.getTeamName());
                editor.commit();
                Intent i = new Intent(TeamListActivity.this, NavigationBarActivity.class);
                startActivity(i);
                finish();

            }
            @Override
            public void onLongClick(View view, int position) {
                final TeamList teamList = allTeamList.get(position);

                final Dialog dialog= new Dialog(TeamListActivity.this);
                dialog.setContentView(R.layout.customdialog_deletechannelmember);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView remove = dialog.findViewById(R.id.tvCdcmHeading);
                remove.setText("Remove "+teamList.getTeamName()+" ?");

                Button ok = dialog.findViewById(R.id.btnCdcmOk);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DeleteTeamAsync(teamList.getTeamSlug(),TeamListActivity.this){
                            @Override
                            protected void onPostExecute(Boolean result) {
                                if(!result)
                                {
                                    Toast.makeText(TeamListActivity.this, "Unable to delete team", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                getTeam();
                                dialog.dismiss();
                            }

                        }.execute();
                    }
                });

                Button cancel = dialog.findViewById(R.id.btnCdcmCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        }));

        getTeam();
        //prepareMyTaskData();

    }

    public void prepareMyTaskData(List<Team> teams) {
        this.allTeamList.clear();
        TeamList teamList = null;
        try{
            if(!teams.isEmpty()) {
                for (Team team : teams) {
                    teamList = new TeamList(team.getName(), team.getSlug(), "10+ Unread Messages", Helper.resolveUrl(team.getPicture(),"original"));
                    allTeamList.add(teamList);
                }
                teamListAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, "You don't have any team", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e)
        {
            Toast.makeText(this, "You don't have any team", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (sharedPreferences.contains("teamSlug")) {
                Intent i = new Intent(TeamListActivity.this, NavigationBarActivity.class);
                startActivity(i);
                finish();
            } else {
                finish();
            }
        }
        catch(NullPointerException e)
        {
            finish();
        }catch(Exception e){

        }
    }

    public void getTeam()
    {
        new ListTeamAsync(TeamListActivity.this).execute();
    }
}
