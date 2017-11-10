package com.example.base.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


public class CreateChannelFragment extends Fragment {

    private CheckBox public_cb,private_cb;
    private TextView cancel;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_create_channel, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Create Channel");

        public_cb = view.findViewById(R.id.cbCcPublic);
        private_cb = view.findViewById(R.id.cbCcPrivate);
        cancel = view.findViewById(R.id.tvCcCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = TabFragment.newInstance(0);
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.FlContentNavigation, fragment,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }

            }
        });
        public_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(private_cb.isChecked())
                {
                    private_cb.setChecked(false);
                }
            }
        });

        private_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(public_cb.isChecked())
                {
                    public_cb.setChecked(false);
                }
            }
        });

    }
}
