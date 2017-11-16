package com.example.base.base.team;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.base.base.R;
import com.example.base.base.async.team.CreateTeamAsync;

public class CreateTeamActivity extends AppCompatActivity {

    EditText teamName,teamDescription;
    Button createTeam;
    TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        getSupportActionBar().hide();

        cancel = (TextView) findViewById(R.id.tvCtCancel);
        teamName = (EditText) findViewById(R.id.etCtTeamName);
        teamDescription = (EditText) findViewById(R.id.etCtDescription);
        createTeam = (Button) findViewById(R.id.btnCtCreate);

        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamName_value,teamDescription_value;
                teamName_value = teamName.getText().toString();
                teamDescription_value = teamDescription.getText().toString();

                if(teamName_value.length()!=0 && teamDescription_value.length()!=0)
                {
                    new CreateTeamAsync(teamName_value,teamDescription_value,CreateTeamActivity.this).execute();
                }
                else
                {
                    if(teamDescription_value.length()==0)
                    {
                        teamDescription.setError("Field is mandatory");
                    }
                    if(teamName_value.length()==0)
                    {
                        teamName.setError("Field is mandatory");
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateTeamActivity.this,TeamListActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CreateTeamActivity.this,TeamListActivity.class);
        startActivity(i);
    }
}
