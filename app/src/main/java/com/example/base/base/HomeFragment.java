package com.example.base.base;

import android.animation.IntArrayEvaluator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    private List<Channel> channelsList = new ArrayList<>();
    private RecyclerView rvHomeRecyclerView;
    private ChannelAdapter channelAdapter;
    private static final String urlgettask = "https://incsmart.000webhostapp.com/gettask.php";
    ArrayList<Integer> color_arraylist = new ArrayList<>();
    ArrayList<String> name_arraylist = new ArrayList<>();
    ArrayList<Integer> message_arraylist = new ArrayList<>();

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
        color_arraylist.clear();
        message_arraylist.clear();
        name_arraylist.clear();

        channelsList.clear();
        channelAdapter = new ChannelAdapter(channelsList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        rvHomeRecyclerView.setLayoutManager(rLayoutManager);
        rvHomeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvHomeRecyclerView.setAdapter(channelAdapter);

        /*rvMyTaskRecyclerView.addOnItemTouchListener(new LeaveTouchListener(getActivity(), rvMyTaskRecyclerView, new LeaveTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyTask myTask = myTasksList.get(position);
                Intent i = getActivity().getIntent();
                i.putExtra("mytasksubject", myTask.getTaskSubject());
                i.putExtra("mytaskmessage", myTask.getMessage());
                i.putExtra("mytaskdeadline", myTask.getDeadline());
                i.putExtra("mytaskstatus", myTask.getStatus());
                i.putExtra("mytaskid", myTask.getId());
                i.putExtra("mytaskbutton", "Edit");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlEmployeeHome,new TaskDetailActivity());
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {
                MyTask myTask = myTasksList.get(position);
            }
        }));

        get emailid
        Intent i = getActivity().getIntent();
        if (i.getExtras() != null) {
            String email_data = i.getStringExtra("email");

            //get report
            getTasks(email_data);
        }*/

        prepareMyTaskData();
    }

    private void prepareMyTaskData() {

        Channel channel= new Channel("#63D5FF","design");
        channelsList.add(channel);

        channel= new Channel("#6CC27A","development");
        channelsList.add(channel);

        channel= new Channel("#FFAA57","general");
        channelsList.add(channel);

        channel= new Channel("#EA4040","marketing");
        channelsList.add(channel);

        /*MyTask myTask = null;
        for(int i=0;i<subject_arraylist.size();i++)
        {
            String sta = "In Progress";
            //Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            //String formattedDate = df.format(c.getTime());
            Date strDate = null;
            try {
                strDate = df.parse(deadline_arraylist.get(i));
                if (new Date().after(strDate)) {
                    sta = "Completed";
                }
                myTask = new MyTask(subject_arraylist.get(i),message_arraylist.get(i),sta,deadline_arraylist.get(i),id_arraylist.get(i));
                myTasksList.add(myTask);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/

        channelAdapter.notifyDataSetChanged();
    }

    /*private void getTasks(final String email)
    {
        StringRequest stringrequest = new StringRequest(Request.Method.POST,urlgettask, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                getTasksDetails(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error :- "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<>();
                parameters.put("Emailid",email);
                return parameters;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringrequest);
    }

    private void getTasksDetails(String json){
        try {
            JSONObject jsonobject = new JSONObject(json);
            JSONArray report_jsonarray = jsonobject.getJSONArray("tasks");
            subject_arraylist.clear();
            message_arraylist.clear();
            deadline_arraylist.clear();
            id_arraylist.clear();
            for(int i=0;i<report_jsonarray.length();i++) {
                JSONObject temp = report_jsonarray.getJSONObject(i);
                subject_arraylist.add(temp.getString("subject"));
                message_arraylist.add(temp.getString("message"));
                deadline_arraylist.add(temp.getString("deadline"));
                id_arraylist.add(temp.getString("id"));
            }
            prepareMyTaskData();
        } catch (JSONException e) {
            e.printStackTrace();
            // Toast.makeText(getActivity(), "E :- "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }*/

}