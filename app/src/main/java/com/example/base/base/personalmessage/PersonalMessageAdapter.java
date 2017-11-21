package com.example.base.base.personalmessage;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.base.base.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Devam on 03-Oct-17.
 */

public class PersonalMessageAdapter extends RecyclerView.Adapter<PersonalMessageAdapter.PersonalMessageHolder>  {

    private List<PersonalMessage> personalMessageList;
    public PersonalMessageAdapter(List<PersonalMessage> personalMessagesList) { this.personalMessageList = personalMessagesList;}
    @Override
    public PersonalMessageAdapter.PersonalMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personalmessage_recyclerview, parent, false);

        return new PersonalMessageAdapter.PersonalMessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonalMessageAdapter.PersonalMessageHolder holder, int position) {
        PersonalMessage personalMessage = personalMessageList.get(position);
        holder.name.setText(personalMessage.getMemberName());
        holder.message.setText(personalMessage.getMemberMessage());
        holder.time.setText(personalMessage.getMemberTime());
        Picasso.with(holder.tempView.getContext())
                .load(personalMessage.getMemberPic())
                .into(holder.pic);

        //holder.message.setImageResource(channel.getChannelMessage());
    }

    @Override
    public int getItemCount() { return personalMessageList.size(); }
    public class PersonalMessageHolder extends RecyclerView.ViewHolder {
        public TextView message,name,time;
        public ImageView pic;
        public View tempView;

        public PersonalMessageHolder(View view) {
            super(view);
            name =  (TextView) view.findViewById(R.id.tvPmrMemberName);
            message = (TextView) view.findViewById(R.id.tvPmrMemberMessage);
            time = (TextView) view.findViewById(R.id.tvPmrMemberTime);
            pic = (ImageView) view.findViewById(R.id.ivPmrMemberPic);
            tempView = view;
        }
    }
}

