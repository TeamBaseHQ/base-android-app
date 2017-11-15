package com.example.base.base.thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.base.R;
import com.example.base.base.recyclerview_necessarydata.DividerItemDecoration;
import com.example.base.base.recyclerview_necessarydata.RecyclerTouchListener;
import com.example.base.base.tabs.ThreadMessageTabFragment;
import com.example.base.base.team.ThreadFragment;

import java.util.ArrayList;
import java.util.List;


public class DisplayThreadFragment extends Fragment {

    private List<DisplayThread> displayThreadsList = new ArrayList<>();
    private RecyclerView rvDisplayThreadRecyclerView;
    private DisplayThreadAdapter DisplayThreadAdapter;
    private static final String urlgettask = "https://incsmart.000webhostapp.com/gettask.php";
    ArrayList<Integer> threadMemberpic_arraylist = new ArrayList<>();
    ArrayList<String> threadName_arraylist = new ArrayList<>();
    ArrayList<String> threadTime_arraylist = new ArrayList<>();
    ArrayList<String> threadMessage_arraylist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                fragment = new ThreadFragment();
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
        threadMemberpic_arraylist.clear();
        threadName_arraylist.clear();
        threadTime_arraylist.clear();
        threadMessage_arraylist.clear();
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
                /*Intent i = getActivity().getIntent();
                i.putExtra("mytasksubject", myTask.getTaskSubject());
                i.putExtra("mytaskmessage", myTask.getMessage());
                i.putExtra("mytaskdeadline", myTask.getDeadline());
                i.putExtra("mytaskstatus", myTask.getStatus());
                i.putExtra("mytaskid", myTask.getId());
                i.putExtra("mytaskbutton", "Edit");*/
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlContentNavigation, ThreadMessageTabFragment.newInstance(displayThread.getThreadName()));
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }

           /* @Override
            public void onLongClick(View view, int position) {
                MyTask myTask = myTasksList.get(position);
            }*/
        }));

        /*get emailid
        Intent i = getActivity().getIntent();
        if (i.getExtras() != null) {
            String email_data = i.getStringExtra("email");

            //get report
            getTasks(email_data);
        }*/

        prepareMyTaskData();
    }

    private void prepareMyTaskData() {

        DisplayThread displayThread = new DisplayThread("New Logo Concept","2m","Kunal: Yeah.",R.drawable.devam);
        displayThreadsList.add(displayThread);


        displayThread = new DisplayThread("Website Redesign","just now","Devam: This new concept is s...",R.drawable.devam);
        displayThreadsList.add(displayThread);

        displayThread = new DisplayThread("Better Email Design","10m","Sharvil: The email copy seem...",R.drawable.devam);
        displayThreadsList.add(displayThread);


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

        DisplayThreadAdapter.notifyDataSetChanged();
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