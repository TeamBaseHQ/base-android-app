package com.example.base.base.thread;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.base.base.R;
import com.example.base.base.async.thread.CreateThreadAsync;
import com.example.base.base.tabs.ThreadTabFragment;


public class ThreadFragment extends Fragment {


    EditText threadSubject,threadDescription;
    Button createThread;
    SharedPreferences sharedPreferences;
    TextView cancel;
    private String channelName,channelSlugName;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_thread, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Create Thread");

        //references
        threadSubject = view.findViewById(R.id.etFtThreadSubject);
        threadDescription = view.findViewById(R.id.etFtThreadDescription);
        createThread = view.findViewById(R.id.btnFtStart);
        cancel = view.findViewById(R.id.tvFtCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = getActivity().getIntent();
                    if (i.getExtras().containsKey("channelName") && i.getExtras().containsKey("channelSlugName")) {
                        Fragment fragment = ThreadTabFragment.newInstance(i.getExtras().getString("channelSlugName"), i.getExtras().getString("channelName"));
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.FlContentNavigation, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }catch(Exception e){

                }
            }
        });

        createThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String threadSubject_value = threadSubject.getText().toString();
                String threadDescription_value = threadDescription.getText().toString();
                if (threadSubject_value.length() != 0 && threadDescription_value.length() != 0) {
                    try {
                        sharedPreferences = getActivity().getSharedPreferences("BASE", Context.MODE_PRIVATE);
                        Intent i = getActivity().getIntent();
                        if (i.getExtras().containsKey("channelSlugName")) {
                            //get channelName and channelSlugName
                            channelName = i.getExtras().getString("channelName");
                            channelSlugName = i.getExtras().getString("channelSlugName");

                            if (sharedPreferences.contains("teamSlug")) {
                                new CreateThreadAsync(threadSubject_value,threadDescription_value,sharedPreferences.getString("teamSlug",""),i.getExtras().getString("channelSlugName"),getActivity()){
                                    @Override
                                    protected void onPostExecute(String result) {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                        if(result.equals("Thread Created"))
                                        {
                                            Fragment fragment = ThreadTabFragment.newInstance(channelSlugName,channelName);
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                                            transaction.replace(R.id.FlContentNavigation,fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();
                                        }
                                    }
                                }.execute();
                            } else {
                                Toast.makeText(getActivity(), "Select Team", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Select Channel", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                  if(threadDescription_value.length()==0)
                  {
                      threadDescription.setError("Field is mandatory");
                  }
                  if(threadSubject_value.length()==0)
                  {
                      threadSubject.setError("Field is mandatory");
                  }
                }

            }
        });

    }
}
