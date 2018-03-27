package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class DoctorChoice extends AppCompatActivity {

RadioButton KLradioBdoctorOffice;
RadioButton KLradioBDentist;
RadioButton KLradioBOptom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_choice);


        //I tried to follow this instructions to import snackbar:

//        Open [File] -> [Project Structure...]
//
//        Select [app] in the left pad
//
//        Select [Dependencies] in the right tabs
//
//        Click [+] button on the right side
//
//        Select [1 Library dependency]
//
//        Choose [com.android.support:design ~~]
//
//        Click [OK] button and so on

        // now R.id doesn't work



        KLradioBdoctorOffice = (RadioButton)findViewById(R.id.checkBoxDocOffice);
        KLradioBDentist = (RadioButton)findViewById(R.id.checkBoxDent);
        KLradioBOptom = (RadioButton)findViewById(R.id.checkBoxOptom);

        Snackbar snackbar = Snackbar.make(KLradioBdoctorOffice, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);
        snackbar.show();

        KLradioBdoctorOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = 1;

                Intent intent = new Intent(DoctorChoice.this, PatientForm.class);
                intent.putExtra("Button Doctor's Office was pressed", i);
                startActivityForResult(intent, 50);
            }
        });

        KLradioBDentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int i = 2;

                Intent intent = new Intent(DoctorChoice.this, PatientForm.class);
                intent.putExtra("Dentist was pressed", i);
                startActivityForResult(intent, 50);
            }
        });

        KLradioBOptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 3;

                Intent intent = new Intent(DoctorChoice.this, PatientForm.class);
                intent.putExtra("Optometrist was pressed", i);
                startActivityForResult(intent, 50);
            }
        });

        }



}
