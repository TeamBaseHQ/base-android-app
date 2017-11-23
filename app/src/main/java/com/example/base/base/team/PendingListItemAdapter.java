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
 * Created by Devam on 22-Nov-17.
 */

public class PendingListItemAdapter extends RecyclerView.Adapter<PendingListItemAdapter.PendingListItemHolder>  {

    private List<PendingListItem> pendingListItemList;
    public PendingListItemAdapter(List<PendingListItem> pendingListItemList) { this.pendingListItemList = pendingListItemList;}
    @Override
    public PendingListItemAdapter.PendingListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pendinginvitations_recyclerview, parent, false);

        return new PendingListItemAdapter.PendingListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PendingListItemAdapter.PendingListItemHolder holder, int position) {
        PendingListItem pendingListItem = pendingListItemList.get(position);
        holder.email.setText(pendingListItem.getPendingListEmail());
    }

    @Override
    public int getItemCount() { return pendingListItemList.size(); }
    public class PendingListItemHolder extends RecyclerView.ViewHolder {
        public TextView email;

        public PendingListItemHolder(View view) {
            super(view);
            email = view.findViewById(R.id.tvPiemail);
        }
    }
}
