package com.example.base.base.thread;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.base.Models.Thread;
import com.example.base.base.R;
import com.example.base.base.async.thread.ListThreadAsync;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.tabs.TabFragment;

import java.util.ArrayList;
import java.util.List;


public class DisplayThreadFragment extends Fragment {

    private List<DisplayThread> displayThreadsList = new ArrayList<>();
    private RecyclerView rvDisplayThreadRecyclerView;
    private DisplayThreadAdapter DisplayThreadAdapter;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_display_thread, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("All Threads");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabFdtAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = TabFragment.newInstance(2);
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.FlContentNavigation, fragment,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        //references
        rvDisplayThreadRecyclerView = (RecyclerView) view.findViewById(R.id.rvFdtDisplayThread);
        displayThreadsList.clear();
        DisplayThreadAdapter = new DisplayThreadAdapter(displayThreadsList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        rvDisplayThreadRecyclerView.setLayoutManager(rLayoutManager);
        rvDisplayThreadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvDisplayThreadRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvDisplayThreadRecyclerView.setAdapter(DisplayThreadAdapter);

        sharedPreferences = getActivity().getSharedPreferences("BASE", Context.MODE_PRIVATE);

        Intent i = getActivity().getIntent();
        if(i.getExtras().containsKey("channelSlugName"))
        {
            if(sharedPreferences.contains("teamSlug"))
            {
                new ListThreadAsync(sharedPreferences.getString("teamSlug",""),i.getExtras().getString("channelSlugName"),getActivity())
                {
                    @Override
                    protected void onPostExecute(List<Thread> result) {
                        super.onPostExecute(result);
                        prepareMyTaskData(result);

                    }
                }.execute();
            }
            else
            {
                Toast.makeText(getActivity(), "Select Team", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getActivity(), "Select Channel", Toast.LENGTH_SHORT).show();
        }
        /*rvDisplayThreadRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvDisplayThreadRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DisplayThread displayThread = displayThreadsList.get(position);
                /*Intent i = getActivity().getIntent();
                i.putExtra("mytasksubject", myTask.getTaskSubject());
                i.putExtra("mytaskmessage", myTask.getMessage());
                i.putExtra("mytaskdeadline", myTask.getDeadline());
                i.putExtra("mytaskstatus", myTask.getStatus());
                i.putExtra("mytaskid", myTask.getId());
                i.putExtra("mytaskbutton", "Edit");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlContentNavigation, ThreadMessageTabFragment.newInstance(displayThread.getThreadName()));
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }

           /* @Override
            public void onLongClick(View view, int position) {
                MyTask myTask = myTasksList.get(position);
            }
        }));*/
    }

    private void prepareMyTaskData(List<Thread> threads) {

        DisplayThread displayThread = null;
        if(!threads.isEmpty()) {
            for (Thread thread : threads) {
                displayThread = new DisplayThread(thread.getSubject(), "2m", "Kunal: Yeah.", R.drawable.devam);
                displayThreadsList.add(displayThread);
            }
            DisplayThreadAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(getActivity(), "No Thread Found", Toast.LENGTH_SHORT).show();
        }
    }
}