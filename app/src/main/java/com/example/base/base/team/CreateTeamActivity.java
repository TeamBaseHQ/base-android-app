package com.example.base.base.team;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
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

import com.base.Models.Team;
import com.example.base.base.R;
import com.example.base.base.async.team.CreateTeamAsync;
import com.example.base.base.async.team.UploadTeamImageAsync;

import java.io.File;

public class CreateTeamActivity extends AppCompatActivity {

    EditText teamName,teamDescription;
    Button createTeam;
    TextView cancel;
    ImageView teamPic;
    private String imagePath;
    private static final int SELECT_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        getSupportActionBar().hide();

        cancel = (TextView) findViewById(R.id.tvCtCancel);
        teamName = (EditText) findViewById(R.id.etCtTeamName);
        teamDescription = (EditText) findViewById(R.id.etCtDescription);
        createTeam = (Button) findViewById(R.id.btnCtCreate);
        teamPic = (ImageView) findViewById(R.id.ivCtUpload);

        teamPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamName_value,teamDescription_value;
                teamName_value = teamName.getText().toString();
                teamDescription_value = teamDescription.getText().toString();

                if(teamName_value.length()!=0 && teamDescription_value.length()!=0)
                {
                    new CreateTeamAsync(teamName_value,teamDescription_value,CreateTeamActivity.this){
                        @Override
                        protected void onPostExecute(Team result) {
                            if(result==null)
                            {
                                Toast.makeText(getApplicationContext(), "Team Not Created", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            new UploadTeamImageAsync(result.getSlug(),new File(imagePath),CreateTeamActivity.this){
                                @Override
                                protected void onPostExecute(String result) {
                                    if(result.contains("Error"))
                                    {
                                        Toast.makeText(CreateTeamActivity.this, "Unable to upload image", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Intent i = new Intent(CreateTeamActivity.this,TeamListActivity.class);
                                    startActivity(i);
                                }
                            }.execute();

                        }
                    }.execute();
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
                    teamPic.setImageURI(selectedImageUri);
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
