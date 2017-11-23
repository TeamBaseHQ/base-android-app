package com.example.base.base.team;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.base.base.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Devam on 13-Nov-17.
 */

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.TeamListHolder>  {

    private List<TeamList> teamListList;
    public TeamListAdapter(List<TeamList> teamListList) { this.teamListList = teamListList;}
    @Override
    public TeamListAdapter.TeamListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teamlist_recyclerview, parent, false);

        return new TeamListAdapter.TeamListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeamListAdapter.TeamListHolder holder, int position) {
        TeamList teamList = teamListList.get(position);

        holder.teamName.setText(teamList.getTeamName());
        holder.teamMessage.setText(teamList.getTeamMessage());
        Picasso.with(holder.tempview.getContext())
                .load(teamList.getTeamPic())
                .placeholder(R.drawable.devam)
                .into(holder.teamPic);
    }

    @Override
    public int getItemCount() { return teamListList.size(); }
    public class TeamListHolder extends RecyclerView.ViewHolder {
        public TextView teamName, teamMessage;
        public ImageView teamPic;
        public View tempview;

        public TeamListHolder(View view) {
            super(view);
            teamName = (TextView) view.findViewById(R.id.tvTlrTeamName);
            teamMessage = (TextView) view.findViewById(R.id.tvTlrTeamMessage);
            teamPic = (ImageView) view.findViewById(R.id.ivTlrTeamPic);
            tempview = view;
        }
    }
}