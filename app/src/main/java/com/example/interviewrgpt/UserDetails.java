package com.example.interviewrgpt;

import static com.example.interviewrgpt.getApiKey.apiKey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;

public class UserDetails extends AppCompatActivity {

    EditText userName,jobType;
    Button proceed_btn;
    RadioButton text,audio;
    static int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        userName = findViewById(R.id.user);
        jobType = findViewById(R.id.jobProfile);
        proceed_btn = findViewById(R.id.proceedButon);
        text = findViewById(R.id.textMode);
        audio = findViewById(R.id.audioMode);

        if( ! (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED ))
        {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        new getApiKey(getApplicationContext()); // getting api key


        proceed_btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(jobType.getText()) || (!text.isChecked() && !audio.isChecked()))
                {
                    Toast.makeText(getApplicationContext(),"Please fill data first.........",Toast.LENGTH_SHORT).show();
                }

                else if(apiKey != null){
                    mode  = -1;
                    if(text.isChecked()) mode = 1;
                    else mode = 0;
                    Intent intent = new Intent(UserDetails.this,MainActivity.class);
                    intent.putExtra("user",userName.getText().toString());
                    intent.putExtra("profile",jobType.getText().toString());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Server Error Restart App....",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1)
        {
            if(! (grantResults[0] == PackageManager.PERMISSION_GRANTED) )
            {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},1);
            }
        }
    }
}