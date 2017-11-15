package com.example.base.base.team;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.base.Models.Team;
import com.example.base.base.NavigationBarActivity;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.R;
import com.example.base.base.async.team.ListTeamAsync;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class TeamListActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView rvAllTeamListRecyclerView;
    private TeamListAdapter teamListAdapter;
    private static List<TeamList> allTeamList = new ArrayList<>();
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

            }
            @Override
            public void onLongClick(View view, int position) {
                TeamList teamList = allTeamList.get(position);
            }
        }));

        new ListTeamAsync(TeamListActivity.this).execute();
        //prepareMyTaskData();
    }

    public void prepareMyTaskData(List<Team> teams) {

        TeamList teamList = null;
        try{
            if(!teams.isEmpty()) {
                for (Team team : teams) {
                    teamList = new TeamList(team.getName(), team.getSlug(), "10+ Unread Messages", R.drawable.devam);
                    this.allTeamList.add(teamList);
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
}
