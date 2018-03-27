package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptometristExtra extends AppCompatActivity {

    Button KLsubmitFormO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optometrist_extra);

        KLsubmitFormO = (Button)findViewById(R.id.SubmitFormButtonO);

        KLsubmitFormO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OptometristExtra.this, ListView.class);
                intent.putExtra("messageFromOptometrist", "Form at Optometrist' Office is submitted");
                Bundle extras = new Bundle();
                extras.putString("statusFromOptometrist", "Form at Optometrist's Office is submitted");
                intent.putExtras(extras);
                startActivityForResult(intent, 50);
            }
        });

    }
}
