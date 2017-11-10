package com.example.base.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ThreadTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentTabHost mTabHost;
    private int thread_no,member_no;
    private String channelName;

    public static ThreadTabFragment newInstance (int thread_no,int member_no,String channelName){
        Bundle bundle = new Bundle();
        bundle.putInt("thread_no", thread_no);
        bundle.putInt("member_no", member_no);
        bundle.putString("channelName", channelName);

        ThreadTabFragment fragment = new ThreadTabFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            this.thread_no = bundle.getInt("thread_no");
            this.member_no = bundle.getInt("member_no");
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
        //you can set the title for your toolbar here for different fragments different titles
        /*Intent i = new Intent(ViewTaskActivity.this,TabActivityViewTask.class);
        startActivity(i);*/

        readBundle(getArguments());

        getActivity().setTitle(this.channelName);

        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.FlThreadTab);

        mTabHost.addTab(mTabHost.newTabSpec("Threads").setIndicator(this.thread_no+" Threads"),
                    DisplayThreadFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("Members").setIndicator(this.member_no+" members"),
                PersonalMessageFragment.class, null);

        mTabHost.setCurrentTab(0);

        //setTabColor(mTabHost);


    }

}