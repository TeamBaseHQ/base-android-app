package com.example.base.base.tabs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.base.R;
import com.example.base.base.message_format.MessageFragment;
import com.example.base.base.thread.DisplayThreadFragment;
import com.example.base.base.personalmessage.PersonalMessageFragment;


public class ThreadMessageTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentTabHost mTabHost;
    private String threadName,threadSlug,channelSlug;

    public static ThreadMessageTabFragment newInstance (String threadName,String threadSlug,String channelSlug){
        Bundle bundle = new Bundle();
        bundle.putString("threadName", threadName);
        bundle.putString("threadSlug", threadSlug);
        bundle.putString("channelSlug", channelSlug);

        ThreadMessageTabFragment fragment = new ThreadMessageTabFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            this.threadName = bundle.getString("threadName");
            this.threadSlug = bundle.getString("threadSlug");
            this.channelSlug = bundle.getString("channelSlug");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_thread_message_tab, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        /*Intent i = new Intent(ViewTaskActivity.this,TabActivityViewTask.class);
        startActivity(i);*/

        readBundle(getArguments());

        getActivity().setTitle(this.threadName);

        Intent i = getActivity().getIntent();
        i.putExtra("MessageTitleName",threadName);
        i.putExtra("channelSlug",channelSlug);
        i.putExtra("threadSlug",threadSlug);

        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.FlThreadMessageTab);

        mTabHost.addTab(mTabHost.newTabSpec("Messages").setIndicator("Messages"),
                MessageFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("Files").setIndicator("Files"),
                MessageFragment.class, null);

        mTabHost.setCurrentTab(0);

        //setTabColor(mTabHost);


    }

}