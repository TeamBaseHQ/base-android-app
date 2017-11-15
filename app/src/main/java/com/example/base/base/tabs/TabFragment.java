package com.example.base.base.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.base.HomeFragment;
import com.example.base.base.member.PeopleFragment;
import com.example.base.base.R;
import com.example.base.base.channel.CreateChannelFragment;
import com.example.base.base.thread.PersonalMessageFragment;


public class TabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentTabHost mTabHost;
    private int choice;

    public static TabFragment newInstance (int choice){
        Bundle bundle = new Bundle();
        bundle.putInt("choice", choice);

        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            this.choice = bundle.getInt("choice");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_tab, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Amazon");
        /*Intent i = new Intent(ViewTaskActivity.this,TabActivityViewTask.class);
        startActivity(i);*/

        //For Spinner handling
        readBundle(getArguments());

        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.FlTab);

        if(choice==0) {
            mTabHost.addTab(mTabHost.newTabSpec("Home").setIndicator("", getResources().getDrawable(R.mipmap.home)),
                    HomeFragment.class, null);
        }
        else if(choice==1)
        {
            mTabHost.addTab(mTabHost.newTabSpec("Home").setIndicator("", getResources().getDrawable(R.mipmap.home)),
                    CreateChannelFragment.class, null);
        }
        else
        {
            mTabHost.addTab(mTabHost.newTabSpec("Home").setIndicator("", getResources().getDrawable(R.mipmap.home)),
                    HomeFragment.class, null);
        }
        mTabHost.addTab(mTabHost.newTabSpec("PersonalMessage").setIndicator("",getResources().getDrawable(R.mipmap.personalmessage)),
                PersonalMessageFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("People").setIndicator("",getResources().getDrawable(R.mipmap.people)),
                PeopleFragment.class, null);

        mTabHost.setCurrentTab(0);

        //setTabColor(mTabHost);


    }

    /*public static void setTabColor(TabHost tabhost) {
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#2F314A")); //unselected
        }
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#3F51B5")); // selected
    }*/
}