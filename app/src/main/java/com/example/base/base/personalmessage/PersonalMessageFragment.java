package com.example.base.base.personalmessage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.base.Models.Channel;
import com.base.Models.User;
import com.example.base.base.actions.AddChannelMemberToList;
import com.example.base.base.actions.HandlesAction;
import com.example.base.base.actions.RemoveChannelMemberFromList;
import com.example.base.base.async.channel.AddChannelMemberAsync;
import com.example.base.base.async.channel.DeleteChannelMemberAsync;
import com.example.base.base.async.channel.ListChannelMemberNameAsync;
import com.example.base.base.async.member.ListTeamMemberAsync;
import com.example.base.base.channel.AddChannelMember;
import com.example.base.base.channel.AddChannelMemberAdapter;
import com.example.base.base.helper.Helper;
import com.example.base.base.listener.channel.ChannelMemberWasAdded;
import com.example.base.base.listener.channel.ChannelMemberWasRemoved;
import com.example.base.base.message_format.MessageFragment;
import com.example.base.base.R;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class PersonalMessageFragment extends Fragment implements HandlesAction {

    private List<PersonalMessage> personalMessagesList = new ArrayList<>();
    private RecyclerView rvPersonalMessageRecyclerView;
    private PersonalMessageAdapter personalMessageAdapter;
    SharedPreferences sharedPreferences;
    FloatingActionButton floatingActionButton;
    private int choice=0;
    private String channelSlug,teamSlug;
    List<User> teamUser;
    List<User> channelUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().registerReceiver(
                new AddChannelMemberToList(this,getActivity()),
                new IntentFilter(ChannelMemberWasAdded.ACTION)
        );

        getActivity().registerReceiver(
                new RemoveChannelMemberFromList(this),
                new IntentFilter(ChannelMemberWasRemoved.ACTION)
        );
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_personal_message, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //references
        rvPersonalMessageRecyclerView = (RecyclerView) view.findViewById(R.id.rvPmPersonalMessage);
        floatingActionButton = view.findViewById(R.id.fabPmAdd);

        Intent i = getActivity().getIntent();
        channelSlug = i.getExtras().getString("channelSlugName");
        try {
            if (i.getExtras().containsKey("flag")) {
                this.choice = i.getExtras().getInt("flag");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(choice == 1)
        {

            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog= new Dialog(getActivity());
                    dialog.setContentView(R.layout.customdialog_addchannelmember);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    Button ok = dialog.findViewById(R.id.btnCacmOk);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getChannelMember(sharedPreferences.getString("teamSlug", ""),channelSlug);
                            dialog.dismiss();
                        }
                    });

                    SharedPreferences sharedPreferences_custom = getActivity()
                            .getSharedPreferences("BASE",Context.MODE_PRIVATE);
                    teamSlug = sharedPreferences_custom.getString("teamSlug","");
                    Intent i = getActivity().getIntent();

                    final List<AddChannelMember> addChannelMembers = new ArrayList<>();
                    final AddChannelMemberAdapter addChannelMemberAdapter = new AddChannelMemberAdapter(addChannelMembers);
                    RecyclerView rvAddChannelMember = dialog.findViewById(R.id.rvCacm);
                    RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
                    rvAddChannelMember.setLayoutManager(rLayoutManager);
                    rvAddChannelMember.setItemAnimator(new DefaultItemAnimator());
                    rvAddChannelMember.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    rvAddChannelMember.setAdapter(addChannelMemberAdapter);
                    rvAddChannelMember.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvAddChannelMember, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            AddChannelMember addChannelMember = addChannelMembers.get(position);
                            new AddChannelMemberAsync(addChannelMember.getMemberId(),channelSlug,getActivity()){
                                @Override
                                protected void onPostExecute(Boolean result) {
                                    if(result){
                                        setData(teamSlug,addChannelMembers,addChannelMemberAdapter);
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), "result :- "+result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }.execute();

                        }
                        @Override
                        public void onLongClick(View view, int position) {
                            AddChannelMember addChannelMember = addChannelMembers.get(position);
                        }
                    }));

                    setData(teamSlug,addChannelMembers,addChannelMemberAdapter);
                    dialog.show();
                }//end onClick

                public void setData(String teamSlug_custom,final List<AddChannelMember> addChannelMembers,final AddChannelMemberAdapter addChannelMemberAdapter){
                    addChannelMembers.clear();
                    new ListTeamMemberAsync(teamSlug_custom,getActivity()){

                        @Override
                        protected void onPostExecute(List<User> result) {
                            if(result == null)
                            {
                                Toast.makeText(getActivity(), "Team Not Found", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            teamUser = result;
                        }
                    }.execute();

                    new ListChannelMemberNameAsync(teamSlug_custom, channelSlug, getActivity()) {
                        @Override
                        protected void onPostExecute(List<User> result) {
                            if(result==null)
                            {
                                Toast.makeText(getActivity(), "Team or Channel Not Found", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            channelUser = result;
                            int flag=0;
                            for (User user:teamUser) {
                                for (User channeluser:channelUser) {
                                    if(channeluser.getId() == user.getId())
                                    {
                                        flag=1;
                                    }
                                }
                                if(flag==0)
                                {
                                    AddChannelMember addChannelMember = new AddChannelMember(user.getName(),Helper.resolveUrl(user.getPicture(),"original"),user.getId());
                                    addChannelMembers.add(addChannelMember);
                                }
                                flag=0;
                            }
                            addChannelMemberAdapter.notifyDataSetChanged();
                        }

                    }.execute();
                }
            });

        }
        else if(choice==2)
        {
            floatingActionButton.setVisibility(View.INVISIBLE);
        }

        personalMessagesList.clear();
        personalMessageAdapter = new PersonalMessageAdapter(personalMessagesList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        rvPersonalMessageRecyclerView.setLayoutManager(rLayoutManager);
        rvPersonalMessageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvPersonalMessageRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvPersonalMessageRecyclerView.setAdapter(personalMessageAdapter);

        try {
            if (i.getExtras().containsKey("flag") && i.getExtras().containsKey("channelSlugName")) {
                if (this.choice == 1) {

                    sharedPreferences = getActivity().getSharedPreferences("BASE", Context.MODE_PRIVATE);
                    /*new ListChannelMemberNameAsync(sharedPreferences.getString("teamSlug", ""), i.getExtras().getString("channelSlugName"), getActivity()) {
                        @Override
                        protected void onPostExecute(List<User> result) {
                            prepareMyTaskData(result);
                        }

                    }.execute();*/
                    getChannelMember(sharedPreferences.getString("teamSlug", ""),i.getExtras().getString("channelSlugName"));
                }
                else if(choice==2)
                {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Personal Message");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        rvPersonalMessageRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvPersonalMessageRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(choice==2) {
                    PersonalMessage personalMessage = personalMessagesList.get(position);
                    Intent i = getActivity().getIntent();
                    i.putExtra("MessageTitleName", personalMessage.getMemberName());
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.FlContentNavigation, new MessageFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                final PersonalMessage personalMessage = personalMessagesList.get(position);
                if(choice==1)
                {
                    final Dialog dialog= new Dialog(getActivity());
                    dialog.setContentView(R.layout.customdialog_deletechannelmember);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    TextView tvHeading = dialog.findViewById(R.id.tvCdcmHeading);
                    tvHeading.setText("Remove "+personalMessage.getMemberName()+" ?");

                    Button ok = dialog.findViewById(R.id.btnCdcmOk);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DeleteChannelMemberAsync(personalMessage.getMemberId(),channelSlug,getActivity()){
                                @Override
                                protected void onPostExecute(Boolean result) {
                                    if(result){
                                        getChannelMember(sharedPreferences.getString("teamSlug", ""),channelSlug);
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), "Unable to delete", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }.execute();
                            dialog.dismiss();
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
            }
        }));
    }

    private void prepareMyTaskData(List<User> users) {

        this.personalMessagesList.clear();
        PersonalMessage personalMessage;
        try{
            if(!users.isEmpty()) {
                for (User user : users) {
                    personalMessage= new PersonalMessage(Helper.resolveUrl(user.getPicture(),"thumb"), user.getName(),"Devam: Hello","2m",user.getId());
                    this. personalMessagesList.add(personalMessage);
                }
                personalMessageAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(getActivity(), "Don't have any channel member", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e)
        {
            Toast.makeText(getActivity(), "Don't have any channel member", Toast.LENGTH_SHORT).show();
        }
    }

    private void getChannelMember(String teamSlug,String channelSlug){
        new ListChannelMemberNameAsync(teamSlug, channelSlug, getActivity()) {
            @Override
            protected void onPostExecute(List<User> result) {
                prepareMyTaskData(result);
            }

        }.execute();
    }

    @Override
    public void handle(String eventName, String channelName, String data) {
        if(eventName.equals(ChannelMemberWasAdded.ACTION)){
            teamSlug = getActivity().getSharedPreferences("BASE",Context.MODE_PRIVATE)
                    .getString("teamSlug","");
            Channel channel = (new Gson()).fromJson(data,Channel.class);
            if(channel.getSlug().equals(channelSlug) && channel.getTeam().getSlug().equals(teamSlug))
            {
                getChannelMember(channel.getTeam().getSlug(),channel.getSlug());
            }

        }else if(eventName.equals(ChannelMemberWasRemoved.ACTION)){
            teamSlug = getActivity().getSharedPreferences("BASE",Context.MODE_PRIVATE)
                    .getString("teamSlug","");
            Channel channel = (new Gson()).fromJson(data,Channel.class);//add member
            if(channel.getSlug().equals(channelSlug) && channel.getTeam().getSlug().equals(teamSlug))
            {
                getChannelMember(channel.getTeam().getSlug(),channel.getSlug());
            }
        }
    }
}