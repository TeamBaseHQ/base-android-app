package com.example.base.base;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class PeopleFragment extends Fragment {

    private List<People> peoplesList = new ArrayList<>();
    private RecyclerView rvPeopleRecyclerView;
    private PeopleAdapter peopleAdapter;
    private static final String urlgettask = "https://incsmart.000webhostapp.com/gettask.php";
    ArrayList<Integer> pic_arraylist = new ArrayList<>();
    ArrayList<String> name_arraylist = new ArrayList<>();
    ArrayList<Integer> status_arraylist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_people, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("My Tasks");

        //Floating Button

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabFpAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //references
        rvPeopleRecyclerView = (RecyclerView) view.findViewById(R.id.rvFpPeople);
        pic_arraylist.clear();
        status_arraylist.clear();
        name_arraylist.clear();

        peoplesList.clear();
        peopleAdapter = new PeopleAdapter(peoplesList);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        rvPeopleRecyclerView.setLayoutManager(rLayoutManager);
        rvPeopleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rvPeopleRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvPeopleRecyclerView.setAdapter(peopleAdapter);

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

        People people= new People("Kavisha Shah","Product Manager",R.drawable.devam);
        peoplesList.add(people);

        people= new People("Dhruvil Shah","Design Lead",R.drawable.devam);
        peoplesList.add(people);

        people= new People("Asim","UI Designer",R.drawable.devam);
        peoplesList.add(people);

        people= new People("Kunal Varma","UX Developer",R.drawable.devam);
        peoplesList.add(people);

        people= new People("Sharvil Shah","Head of Engineering",R.drawable.devam);
        peoplesList.add(people);

        people= new People("Temp Shah","Software Engineer",R.drawable.devam);
        peoplesList.add(people);



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

        peopleAdapter.notifyDataSetChanged();
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