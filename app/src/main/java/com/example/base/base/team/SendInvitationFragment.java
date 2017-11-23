package com.example.base.base.team;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.example.base.base.async.team.InviteTeamMemberAsync;
import com.example.base.base.tabs.TabFragment;
import com.example.base.base.tabs.ThreadTabFragment;

public class SendInvitationFragment extends Fragment {

    TextView cancel;
    EditText email,description;
    Button invite;
    SharedPreferences sharedPreferences;
    private String teamSlug;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_invitation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("BASE",Context.MODE_PRIVATE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(sharedPreferences.getString("teamName",""));

        //references
        cancel = view.findViewById(R.id.tvSiCancel);
        email = view.findViewById(R.id.etSiEmailId);
        description = view.findViewById(R.id.etSiDescription);
        invite = view.findViewById(R.id.btnSiInvite);

        //onClickListners
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = TabFragment.newInstance(0);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FlContentNavigation, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = getActivity().getSharedPreferences("BASE",Context.MODE_PRIVATE);
                if(!sharedPreferences.contains("teamSlug")) {
                    return;
                }
                teamSlug = sharedPreferences.getString("teamSlug", "");

                String email_value = email.getText().toString();
                final String description_value = description.getText().toString();

                if(email_value.length()!=0&&description_value.length()!=0)
                {
                    new InviteTeamMemberAsync(teamSlug,email_value,description_value,getActivity()){
                        @Override
                        protected void onPostExecute(Boolean result) {
                            if(result)
                            {
                                Toast.makeText(getActivity(), "Invitation sent", Toast.LENGTH_SHORT).show();
                                email.setText("");
                                description.setText("");
                                return;
                            }

                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }.execute();
                }
                else
                {
                    if(description_value.length()==0)
                    {
                        description.setError("Filed is mandatory");
                    }
                    if(email_value.length()==0)
                    {
                        email.setError("Filed is mandatory");
                    }
                }

            }
        });


    }
}
