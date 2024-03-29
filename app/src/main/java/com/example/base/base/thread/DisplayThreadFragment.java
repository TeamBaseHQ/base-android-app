package com.example.base.base.thread;

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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.base.Models.Thread;
import com.example.base.base.R;
import com.example.base.base.actions.AddChannelMemberToList;
import com.example.base.base.actions.AddThreadToList;
import com.example.base.base.actions.HandlesAction;
import com.example.base.base.actions.HighlighNewMessage;
import com.example.base.base.actions.RemoveThreadFromList;
import com.example.base.base.async.thread.DeleteThreadAsync;
import com.example.base.base.async.thread.ListThreadAsync;
import com.example.base.base.listener.channel.ChannelMemberWasAdded;
import com.example.base.base.listener.message.MessageWasReceived;
import com.example.base.base.listener.thread.ThreadWasCreated;
import com.example.base.base.listener.thread.ThreadWasDeleted;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;
import com.example.base.base.tabs.TabFragment;
import com.example.base.base.tabs.ThreadMessageTabFragment;

import java.util.ArrayList;
import java.util.List;


public class DisplayThreadFragment extends Fragment implements HandlesAction {

    private List<DisplayThread> displayThreadsList = new ArrayList<>();
    private RecyclerView rvDisplayThreadRecyclerView;
    private DisplayThreadAdapter DisplayThreadAdapter;
    SharedPreferences sharedPreferences;
    String teamSlug,channelSlug;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().registerReceiver(
                new HighlighNewMessage(this),
                new IntentFilter(MessageWasReceived.ACTION)
        );

        getActivity().registerReceiver(
                new AddThreadToList(this),
                new IntentFilter(ThreadWasCreated.ACTION)
        );

        getActivity().registerReceiver(
                new RemoveThreadFromList(this),
                new IntentFilter(ThreadWasDeleted.ACTION)
        );
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

        rvDisplayThreadRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvDisplayThreadRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DisplayThread displayThread = displayThreadsList.get(position);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlContentNavigation,ThreadMessageTabFragment
                        .newInstance(displayThread.getThreadName(),displayThread.getThreadSlug(),
                                displayThread.getChannelSlug()),"DisplayThread");
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {
                final DisplayThread displayThread = displayThreadsList.get(position);

                final Dialog dialog= new Dialog(getActivity());
                dialog.setContentView(R.layout.customdialog_deletechannelmember);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView remove = dialog.findViewById(R.id.tvCdcmHeading);
                remove.setText("Remove "+displayThread.getThreadName()+" ?");

                Button ok = dialog.findViewById(R.id.btnCdcmOk);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedPreferences = getActivity().getSharedPreferences("BASE",Context.MODE_PRIVATE);
                        new DeleteThreadAsync(sharedPreferences.getString("teamSlug","")
                                ,displayThread.getChannelSlug(),displayThread.getThreadSlug(),getActivity()){
                            @Override
                            protected void onPostExecute(Boolean result) {
                                getThreads();
                                dialog.dismiss();
                            }

                        }.execute();
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
        }));

        sharedPreferences = getActivity().getSharedPreferences("BASE", Context.MODE_PRIVATE);

        Intent i = getActivity().getIntent();
        if(i.getExtras().containsKey("channelSlugName"))
        {
            this.channelSlug = i.getExtras().getString("channelSlugName");
            if(sharedPreferences.contains("teamSlug"))
            {
                getThreads();
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
    }

    private void prepareMyTaskData(List<Thread> threads) {

        this.displayThreadsList.clear();
        DisplayThread displayThread = null;
        if(!threads.isEmpty()) {
            for (Thread thread : threads) {
                displayThread = new DisplayThread(this.channelSlug,thread.getSlug(),thread.getSubject(), "2m", "Kunal: Yeah.", R.drawable.devam);
                displayThreadsList.add(displayThread);
            }
            DisplayThreadAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(getActivity(), "No Thread Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void getThreads(){
        this.teamSlug = sharedPreferences.getString("teamSlug","");
        new ListThreadAsync(this.teamSlug,this.channelSlug,getActivity())
        {
            @Override
            protected void onPostExecute(List<Thread> result) {
                super.onPostExecute(result);
                prepareMyTaskData(result);
            }
        }.execute();
    }

    @Override
    public void handle(String eventName, String channelName, String data) {
        //
        if(eventName.equals(MessageWasReceived.ACTION)){

        }else if(eventName.equals(ThreadWasCreated.ACTION)){

        }else if(eventName.equals(ThreadWasDeleted.ACTION)){

        }
    }
}