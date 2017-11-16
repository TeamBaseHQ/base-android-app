package com.example.base.base.personalmessage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.base.Models.User;
import com.example.base.base.async.channel.ListChannelMemberNameAsync;
import com.example.base.base.message_format.MessageFragment;
import com.example.base.base.R;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;
import com.example.base.base.personalmessage.PersonalMessage;
import com.example.base.base.personalmessage.PersonalMessageAdapter;
import com.example.base.base.tabs.ThreadTabFragment;

import java.util.ArrayList;
import java.util.List;


public class PersonalMessageFragment extends Fragment {

    private List<PersonalMessage> personalMessagesList = new ArrayList<>();
    private RecyclerView rvPersonalMessageRecyclerView;
    private PersonalMessageAdapter personalMessageAdapter;
    SharedPreferences sharedPreferences;

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
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("My Tasks");
        //references
        rvPersonalMessageRecyclerView = (RecyclerView) view.findViewById(R.id.rvPmPersonalMessage);

        personalMessagesList.clear();
        personalMessageAdapter = new PersonalMessageAdapter(personalMessagesList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        rvPersonalMessageRecyclerView.setLayoutManager(rLayoutManager);
        rvPersonalMessageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvPersonalMessageRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvPersonalMessageRecyclerView.setAdapter(personalMessageAdapter);

        try {
            Intent i = getActivity().getIntent();
            if (i.getExtras().containsKey("choice") && i.getExtras().containsKey("channelSlugName")) {
                if (i.getExtras().getInt("choice") == 1) {
                    sharedPreferences = getActivity().getSharedPreferences("BASE", Context.MODE_PRIVATE);
                    new ListChannelMemberNameAsync(sharedPreferences.getString("teamSlug", ""), i.getExtras().getString("channelSlugName"), getActivity()) {
                        @Override
                        protected void onPostExecute(List<User> result) {
                            prepareMyTaskData(result);
                        }

                    }.execute();
                }
                else
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
                PersonalMessage personalMessage = personalMessagesList.get(position);
                Intent i = getActivity().getIntent();
                i.putExtra("personalmessageMemberName", personalMessage.getMemberName());
                /*i.putExtra("mytaskmessage", myTask.getMessage());
                i.putExtra("mytaskdeadline", myTask.getDeadline());
                i.putExtra("mytaskstatus", myTask.getStatus());
                i.putExtra("mytaskid", myTask.getId());
                i.putExtra("mytaskbutton", "Edit");*/
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlContentNavigation,new MessageFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {
                PersonalMessage personalMessage = personalMessagesList.get(position);
            }
        }));
    }

    private void prepareMyTaskData(List<User> users) {

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
}