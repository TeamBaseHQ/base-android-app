package com.example.base.base.member;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.example.base.base.R;
import com.example.base.base.SendInvitationFragment;
import com.example.base.base.async.member.ListTeamMemberAsync;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.tabs.TabFragment;

import java.util.ArrayList;
import java.util.List;


public class PeopleFragment extends Fragment {

    private List<People> peoplesList = new ArrayList<>();
    private RecyclerView rvPeopleRecyclerView;
    private PeopleAdapter peopleAdapter;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_people, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("My Tasks");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Team Members");

        //Call Async method too get members
        sharedPreferences = getContext().getSharedPreferences("BASE", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("teamSlug"))
        {
            try {
                new ListTeamMemberAsync(sharedPreferences.getString("teamSlug",""), getActivity()) {
                    @Override
                    protected void onPostExecute(List<User> result) {
                        super.onPostExecute(result);
                        // Do something with result here
                        prepareMyTaskData(result);
                    }

                }.execute();
            }catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Select Team", Toast.LENGTH_SHORT).show();
            }

        }


        //Floating Button
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabFpAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Fragment fragment = new SendInvitationFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlContentNavigation, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //references
        rvPeopleRecyclerView = (RecyclerView) view.findViewById(R.id.rvFpPeople);
        peoplesList.clear();
        peopleAdapter = new PeopleAdapter(peoplesList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        rvPeopleRecyclerView.setLayoutManager(rLayoutManager);
        rvPeopleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvPeopleRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvPeopleRecyclerView.setAdapter(peopleAdapter);

        /*rvMyTaskRecyclerView.addOnItemTouchListener(new LeaveTouchListener(getActivity(), rvMyTaskRecyclerView, new LeaveTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyTask myTask = myTasksList.get(position);
                Intent i = getActivity().getIntent();
                i.putExtra("mytasksubject", myTask.getTaskSubject());
                i.putExtra("mytaskmessage", myTask.getMessage());
                i.putExtra("mytaskdeadline", myTask.getDeadline());
                i.putExtra("mytaskstatus", myTask.getStatus());
                i.putExtra("mytaskid", myTask.getId());
                i.putExtra("mytaskbutton", "Edit");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlEmployeeHome,new TaskDetailActivity());
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {
                MyTask myTask = myTasksList.get(position);
            }
        }));*/
    }

    private void prepareMyTaskData(List<User> users) {

        People people = null;
        try{
            if(!users.isEmpty()) {
                for (User user : users) {
                    people = new People(user.getName(), "Product Manager", R.drawable.devam);
                    this. peoplesList.add(people);
                }
                peopleAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(getActivity(), "Don't have any team member", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e)
        {
            Toast.makeText(getActivity(), "Don't have any team member", Toast.LENGTH_SHORT).show();
        }
    }

}