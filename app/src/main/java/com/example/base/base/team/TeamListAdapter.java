package com.example.base.base.team;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.base.base.R;

import java.util.List;

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
        holder.teamPic.setImageResource(teamList.getTeamPic());
    }

    @Override
    public int getItemCount() { return teamListList.size(); }
    public class TeamListHolder extends RecyclerView.ViewHolder {
        public TextView teamName, teamMessage;
        public ImageView teamPic;

        public TeamListHolder(View view) {
            super(view);
            teamName = (TextView) view.findViewById(R.id.tvTlrTeamName);
            teamMessage = (TextView) view.findViewById(R.id.tvTlrTeamMessage);
            teamPic = (ImageView) view.findViewById(R.id.ivTlrTeamPic);
        }
    }
}