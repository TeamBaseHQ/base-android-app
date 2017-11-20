package com.example.base.base.personalmessage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.base.Models.User;
import com.example.base.base.async.channel.AddChannelMemberAsync;
import com.example.base.base.async.channel.ListChannelMemberNameAsync;
import com.example.base.base.async.member.ListTeamMemberAsync;
import com.example.base.base.channel.AddChannelMember;
import com.example.base.base.channel.AddChannelMemberAdapter;
import com.example.base.base.channel.ChannelItem;
import com.example.base.base.message_format.MessageFragment;
import com.example.base.base.R;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;
import com.example.base.base.personalmessage.PersonalMessage;
import com.example.base.base.personalmessage.PersonalMessageAdapter;
import com.example.base.base.tabs.TabFragment;
import com.example.base.base.tabs.ThreadTabFragment;

import java.util.ArrayList;
import java.util.List;


public class PersonalMessageFragment extends Fragment {

    private List<PersonalMessage> personalMessagesList = new ArrayList<>();
    private RecyclerView rvPersonalMessageRecyclerView;
    private PersonalMessageAdapter personalMessageAdapter;
    SharedPreferences sharedPreferences;
    FloatingActionButton floatingActionButton;
    private int choice=0;
    private String channelSlug;
    List<User> teamUser;
    List<User> channelUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                public void onClick(View view) {
                    final Dialog dialog= new Dialog(getActivity());
                    dialog.setContentView(R.layout.customdialog_addchannelmember);

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
                    final String teamSlug_custom = sharedPreferences_custom.getString("teamSlug","");
                    Intent i = getActivity().getIntent();
                    channelSlug = i.getExtras().getString("channelSlugName");

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
                                        setData(teamSlug_custom,addChannelMembers,addChannelMemberAdapter);
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

                    setData(teamSlug_custom,addChannelMembers,addChannelMemberAdapter);
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
                                    AddChannelMember addChannelMember = new AddChannelMember(user.getName(),R.drawable.devam,user.getId());
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
                PersonalMessage personalMessage = personalMessagesList.get(position);
            }
        }));
    }

    private void prepareMyTaskData(List<User> users) {

        this.personalMessagesList.clear();
        PersonalMessage personalMessage;
        try{
            if(!users.isEmpty()) {
                for (User user : users) {
                    personalMessage= new PersonalMessage(R.drawable.devam, user.getName(),"Devam: Hello","2m");
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
}