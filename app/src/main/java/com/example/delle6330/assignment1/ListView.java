package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

//here user can download a XML file from an http server to import pre-defined patients into the application

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        displayToast("messageFromDoctor", "statusFromDoctor");
        displayToast("messageFromDentist", "statusFromDentist");
        displayToast("messageFromOptometris", "statusFromOptometrist");

    }


    public void displayToast(String name, String key){

        Intent intent = getIntent();
        String message = intent.getStringExtra(name);
        Bundle bundle = intent.getExtras();
        String status = bundle.getString(key);
        Toast toast = Toast.makeText(this, status, Toast.LENGTH_LONG);
        toast.show();

    }

}
