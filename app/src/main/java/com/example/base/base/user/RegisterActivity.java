package com.example.base.base.user;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.base.base.NavigationBarActivity;
import com.example.base.base.R;
import com.example.base.base.async.user.CreateUserAsync;
import com.example.base.base.async.user.LoginUserAsync;
import com.example.base.base.async.user.UploadImageAsync;
import com.example.base.base.team.TeamListActivity;

import java.io.File;

public class RegisterActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;
    TextView login;
    Button signup;
    EditText name,email,password;
    String name_value,email_value,password_value;
    ImageView uploadImage;
    private String imagePath;
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
                    new CreateUserAsync(name_value,email_value,password_value,RegisterActivity.this){
                        @Override
                        protected void onPostExecute(String result) {
                            if(result.contains("Error"))
                            {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            new LoginUserAsync(email_value,password_value,RegisterActivity.this){
                                @Override
                                protected void onPostExecute(String result) {
                                    if(result.contains("Error"))
                                    {
                                        return;
                                    }
                                    openDialog();
                                }
                            }.execute();
                        }
                    }.execute();
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

    public void openDialog()
    {

        final Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.customdialog_uploadprofilepicture);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        uploadImage = dialog.findViewById(R.id.ivCuppProfilePicture);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        Button upload = dialog.findViewById(R.id.btnCuppUpload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadImageAsync(new File(imagePath),getApplication()){
                    @Override
                    protected void onPostExecute(String result) {
                        if (result.contains("Error")) {
                            Toast.makeText(getApplicationContext(), "Error :- " + result, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this,TeamListActivity.class);
                        startActivity(i);
                    }
                }.execute();
            }
        });

        dialog.show();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    imagePath = getPathFromURI(selectedImageUri);
                    Log.i("Image Path","Image Path : " + imagePath);
                    // Set the image in ImageView
                    uploadImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

}
