package com.example.base.base.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.base.base.R;
import com.example.base.base.async.user.CreateUserAsync;

public class RegisterActivity extends AppCompatActivity {

    TextView login;
    Button signup;
    //SharedPreferences sharedPreferences;
    EditText name,email,password;
    String name_value,email_value,password_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        signup = (Button) findViewById(R.id.btnRCreateAccount);
        login = (TextView) findViewById(R.id.tvRLogin);
        name = (EditText) findViewById(R.id.etRName);
        email = (EditText) findViewById(R.id.etREmail);
        password = (EditText) findViewById(R.id.etRPassword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name_value = name.getText().toString();
                email_value = email.getText().toString();
                password_value = password.getText().toString();

                if(name_value.length()!=0 && email_value.length()!=0 && password_value.length()!=0) {
                    /*
                    sharedPreferences = getSharedPreferences("BASE", Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("BaseObject", "");*/
                    //Base base = new Base(sharedPreferences.getString("BaseObject", ""));//gson.fromJson(json, Base.class);

                    new CreateUserAsync(name_value,email_value,password_value,RegisterActivity.this).execute();
                }
                else
                {
                    if(password_value.length()==0)
                    {
                        password.setError("Field is mandatory");
                    }
                    if(email_value.length()==0)
                    {
                        email.setError("Field is mandatory");
                    }
                    if (name_value.length()==0)
                    {
                        name.setError("Field is mandatory");
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
