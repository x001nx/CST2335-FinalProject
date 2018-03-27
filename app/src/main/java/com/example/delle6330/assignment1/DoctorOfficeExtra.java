package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoctorOfficeExtra extends AppCompatActivity {

    Button KLbtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_office_extra);

        KLbtnSubmit = (Button)findViewById(R.id.SubmitFormButtonDO);

        KLbtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DoctorOfficeExtra.this, ListView.class);
                intent.putExtra("messageFromDoctor", "Form at Doctor's Office is submitted");
                Bundle extras = new Bundle();
                extras.putString("statusFromDoctor", "Form at Doctor's Office is submitted");
                intent.putExtras(extras);
                startActivityForResult(intent, 50);
            }
        });

    }
}
