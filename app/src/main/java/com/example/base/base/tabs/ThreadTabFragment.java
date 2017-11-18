package com.example.base.base.tabs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.base.base.R;
import com.example.base.base.thread.DisplayThreadFragment;
import com.example.base.base.personalmessage.PersonalMessageFragment;


public class ThreadTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentTabHost mTabHost;
    //private int thread_no = 5 ,member_no = 5;
    private String channelName,channelSlugName;

    public static ThreadTabFragment newInstance (String channelSlugName,String channelName){
        Bundle bundle = new Bundle();
        bundle.putString("channelSlugName", channelSlugName);
        bundle.putString("channelName", channelName);

        ThreadTabFragment fragment = new ThreadTabFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            this.channelSlugName = bundle.getString("channelSlugName");
            this.channelName = bundle.getString("channelName");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_thread_tab, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //reading the bundle
        readBundle(getArguments());

        //setting title of action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(this.channelName);

        Intent i = getActivity().getIntent();
        i.putExtra("channelSlugName",this.channelSlugName);
        i.putExtra("channelName",this.channelName);
        i.putExtra("flag",1);

        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.FlThreadTab);

        mTabHost.addTab(mTabHost.newTabSpec("Threads").setIndicator("Threads"),
                    DisplayThreadFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("Members").setIndicator("Members"),
                PersonalMessageFragment.class, null);

        mTabHost.setCurrentTab(0);

        //setTabColor(mTabHost);


    }

}