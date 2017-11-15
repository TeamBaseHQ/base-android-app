package com.example.base.base.member;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.base.base.R;

import java.util.List;

/**
 * Created by Devam on 23-Oct-17.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleHolder>  {

    private List<People> peopleList;
    public PeopleAdapter(List<People> peoplesList) { this.peopleList = peoplesList;}
    @Override
    public PeopleAdapter.PeopleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.people_recyclerview, parent, false);

        return new PeopleAdapter.PeopleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PeopleAdapter.PeopleHolder holder, int position) {
        People people = peopleList.get(position);
        /*LayerDrawable shape = (LayerDrawable) ContextCompat.getDrawable(holder.,R.drawable.rounded_corner);
        GradientDrawable gradientDrawable = (GradientDrawable) shape.findDrawableByLayerId(R.id.shape);*/
        holder.name.setText(people.getPeopleName());
        Log.d("Name :- ",people.getPeopleName());
        holder.status.setText(people.getPeopleStatus());
        holder.pic.setImageResource(people.getPeoplePic());
        //holder.message.setImageResource(channel.getChannelMessage());
    }

    @Override
    public int getItemCount() { return peopleList.size(); }
    public class PeopleHolder extends RecyclerView.ViewHolder {
        public TextView status, name;
        public ImageView pic;

        public PeopleHolder(View view) {
            super(view);
            status = (TextView) view.findViewById(R.id.tvPrPeopleStatus);
            name = (TextView) view.findViewById(R.id.tvPrPeopleName);
            pic = (ImageView) view.findViewById(R.id.ivPrPeoplePic);
        }
    }
}
