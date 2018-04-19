package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DentistExtra extends AppCompatActivity {

    Button KLsubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentist_extra);

        KLsubmitButton = (Button)findViewById(R.id.SubmitFormButtonD);

        KLsubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DentistExtra.this, ListView.class);
                intent.putExtra("messageFromDentist", "Form at Dentist' Office is submitted");
                Bundle extras = new Bundle();
                extras.putString("statusFromDentist", "Form at Dentist's Office is submitted");
                intent.putExtras(extras);
                startActivityForResult(intent, 50);
            }
        });
    }
}
