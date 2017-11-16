package com.example.base.base.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.base.base.R;
import com.example.base.base.async.channel.ListChannelAsync;

import com.base.Models.Channel;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;
import com.example.base.base.tabs.ThreadTabFragment;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<ChannelItem> channelsList = new ArrayList<>();
    private RecyclerView rvHomeRecyclerView;
    private ChannelAdapter channelAdapter;
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("My Tasks");

        //references
        rvHomeRecyclerView = (RecyclerView) view.findViewById(R.id.rvFhHomeChannel);
        channelsList.clear();
        channelAdapter = new ChannelAdapter(channelsList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        rvHomeRecyclerView.setLayoutManager(rLayoutManager);
        rvHomeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvHomeRecyclerView.setAdapter(channelAdapter);

        sharedPreferences = getContext().getSharedPreferences("BASE",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("teamSlug"))
        {
            new ListChannelAsync(sharedPreferences.getString("teamSlug",""),getActivity()){
                @Override
                protected void onPostExecute(List<Channel> result) {
                    super.onPostExecute(result);
                    // Do something with result here
                    prepareMyTaskData(result);
                }

            }.execute();
        }
        rvHomeRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvHomeRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ChannelItem channelItem = channelsList.get(position);
                Fragment fragment = ThreadTabFragment.newInstance(channelItem.getChannelSlug(),channelItem.getChannelName());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlContentNavigation,fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }

            @Override
            public void onLongClick(View view, int position) {
                ChannelItem channelItem = channelsList.get(position);
            }
        }));

    }

    private void prepareMyTaskData(List<Channel> channels) {


        ChannelItem channelItem = null;
        try{
            if(!channels.isEmpty()) {
                for (Channel channel : channels) {
                    channelItem = new ChannelItem("#"+channel.getColor(), channel.getName(),channel.getSlug());
                    this. channelsList.add(channelItem);
                }
                channelAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(getActivity(), "Don't have any channel", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e)
        {
            Toast.makeText(getActivity(), "Don't have any channel", Toast.LENGTH_SHORT).show();
        }
    }
}