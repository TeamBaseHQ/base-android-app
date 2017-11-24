package com.example.base.base.channel;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.base.base.R;
import com.example.base.base.actions.AddChannelToList;
import com.example.base.base.actions.HandlesAction;
import com.example.base.base.actions.RemoveChannelFromList;
import com.example.base.base.async.channel.DeleteChannelAsync;
import com.example.base.base.async.channel.ListChannelAsync;

import com.base.Models.Channel;
import com.example.base.base.listener.channel.ChannelWasCreated;
import com.example.base.base.listener.channel.ChannelWasDeleted;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;
import com.example.base.base.tabs.ThreadTabFragment;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HandlesAction {

    private List<ChannelItem> channelsList = new ArrayList<>();
    private RecyclerView rvHomeRecyclerView;
    private ChannelAdapter channelAdapter;
    SharedPreferences sharedPreferences;
    TextView starred;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().registerReceiver(
                new AddChannelToList(this),
                new IntentFilter(ChannelWasCreated.ACTION)
        );

        getActivity().registerReceiver(
                new RemoveChannelFromList(this),
                new IntentFilter(ChannelWasDeleted.ACTION)
        );
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
        starred = view.findViewById(R.id.tvFhStarred);
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
            getChannels();
        }

        starred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code for norification
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
                mBuilder.setSmallIcon(R.drawable.appicon);
                mBuilder.setContentTitle("Notification Alert, Click Me!");
                mBuilder.setContentText("Hi, This is Android Notification Detail!");
                NotificationManager mNotificationManager = (NotificationManager)
                        getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                // notificationID allows you to update the notification later on.
                mNotificationManager.notify(1, mBuilder.build());
            }
        });

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
                final ChannelItem channelItem = channelsList.get(position);

                final Dialog dialog= new Dialog(getActivity());
                dialog.setContentView(R.layout.customdialog_deletechannelmember);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView remove = dialog.findViewById(R.id.tvCdcmHeading);
                remove.setText("Remove "+channelItem.getChannelName()+" ?");

                Button ok = dialog.findViewById(R.id.btnCdcmOk);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedPreferences = getActivity().getSharedPreferences("BASE",Context.MODE_PRIVATE);
                        new DeleteChannelAsync(sharedPreferences.getString("teamSlug",""),channelItem.getChannelSlug(),getActivity()){

                            @Override
                            protected void onPostExecute(Boolean result) {
                                if(!result)
                                {
                                    return;
                                }
                                getChannels();
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
    }

    private void prepareMyTaskData(List<Channel> channels) {
        this.channelsList.clear();
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

    public void getChannels()
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

    @Override
    public void handle(String eventName, String channelName, String data) {
        if(eventName.equals(ChannelWasCreated.ACTION)){

        }else if(eventName.equals(ChannelWasDeleted.ACTION)){

        }
    }
}