package com.example.base.base.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.base.base.singleton.BaseManager;
import com.example.base.base.R;
import com.example.base.base.async.user.LoginUserAsync;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView createAccount;
    String email_value,password_value;
    //SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //references
        email = (EditText) findViewById(R.id.etLEmail);
        password = (EditText) findViewById(R.id.etLPassword);
        login = (Button) findViewById(R.id.btnLLogin);
        createAccount = (TextView) findViewById(R.id.tvLCreateAccount);

        //code for auto fill email and password after registration
        Intent intent = getIntent();
        if(intent.hasExtra("Email")&&intent.hasExtra("Password"))
        {
            email.setText(intent.getExtras().getString("Email"));
            password.setText(intent.getExtras().getString("Password"));
        }

        //onClick listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_value = email.getText().toString();
                password_value = password.getText().toString();

                if(email_value.length()!=0 && password_value.length()!=0)
                {
                        new LoginUserAsync(email_value,password_value,LoginActivity.this).execute();
                        BaseManager.getInstance(LoginActivity.this);
                }
                else
                {
                    if(password_value.length()!=0)
                    {
                        password.setError("Field is mandatory");
                    }
                    if(email_value.length()==0)
                    {
                        email.setError("Field is mandatory");
                    }
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
