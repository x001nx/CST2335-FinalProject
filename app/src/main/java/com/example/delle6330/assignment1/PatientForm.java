package com.example.delle6330.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

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
                    intentWithUserDialog();
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

    public void intentWithUserDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder(PatientForm.this);
        builder.setTitle("")
                .setMessage("The reason for a visit is missing. Are you sure, you want to continue ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //how to pass intent in the parameters
                        Intent intent = new Intent(PatientForm.this, DoctorOfficeExtra.class);
                        startActivityForResult(intent, 50);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog  = builder.create();
        dialog.show();

    }
}
