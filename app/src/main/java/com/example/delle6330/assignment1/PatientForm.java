package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PatientForm extends AppCompatActivity {

    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);

        next = (Button)findViewById(R.id.KLnextButton);


        if(getIntent().getIntExtra("Button Doctor's Office was pressed", 0)==1){
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(PatientForm.this, DoctorOfficeExtra.class);
                    startActivityForResult(intent, 50);
                }
            });

        }else if (getIntent().getIntExtra("Dentist was pressed", 0) == 2){
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PatientForm.this, DentistExtra.class);
                    startActivityForResult(intent, 50);
                }
             });

        }else if(getIntent().getIntExtra("Optometrist was pressed", 0)==3){
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PatientForm.this, OptometristExtra.class);
                    startActivityForResult(intent, 50);
                }
            });

        }




    }
}
