package com.example.delle6330.assignment1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.delle6330.assignment1.Custom;
import com.example.delle6330.assignment1.Mydatabase;
import com.example.delle6330.assignment1.Patient;
import com.example.delle6330.assignment1.R;

import java.util.ArrayList;

public class KseniaMActivity extends AppCompatActivity {

    Mydatabase mydatabase;
    ArrayList<Patient> list = new ArrayList<>();
    Custom adapter;


    RadioGroup radioGroup;
    RadioButton KLradioBdoctorOffice;
    RadioButton KLradioBDentist;
    RadioButton KLradioBOptom;

    String strDoctorChoice= "";
    String addQestion1 = "";
    String addQestion2 = "";

    EditText sergeries;
    EditText allergies;

    EditText glassesDate;
    EditText glassesStore;

    CheckBox yesBraces;
    CheckBox noBraces;
    CheckBox yesBenefits;
    CheckBox noBenefits;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydatabase = new Mydatabase(getApplication());

        final EditText edtName = (EditText) findViewById(R.id.edtName);
        final EditText edtAddress = (EditText) findViewById(R.id.edtAddress);
        final EditText edtBirthday = (EditText) findViewById(R.id.edtBirthday);
        final EditText editPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        final EditText edtHealthCard = (EditText) findViewById(R.id.edtHealthCard);
        final EditText edtDesc = (EditText) findViewById(R.id.edtDesc);

        sergeries = (EditText) findViewById(R.id.sergeries);
        allergies = (EditText) findViewById(R.id.allergies);
        glassesDate = (EditText)findViewById(R.id.glassesDate);
        glassesStore = (EditText)findViewById(R.id.glassesStore);
        yesBraces = (CheckBox)findViewById(R.id.checkBoxYesBraces);
        noBraces = (CheckBox)findViewById(R.id.checkBoxNoBraces);
        yesBenefits = (CheckBox)findViewById(R.id.checkBoxYesBenefits);
        noBenefits = (CheckBox)findViewById(R.id.checkBoxNoBenefits);

        KLradioBdoctorOffice = (RadioButton) findViewById(R.id.checkBoxDocOffice);
        KLradioBDentist = (RadioButton) findViewById(R.id.checkBoxDent);
        KLradioBOptom = (RadioButton) findViewById(R.id.checkBoxOptom);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()) {
                    case R.id.checkBoxDocOffice:

                        strDoctorChoice = "Doctor's Office";
                        break;

                    case R.id.checkBoxOptom:
                        strDoctorChoice = "Optometrist";
                        break;

                    case R.id.checkBoxDent:
                        strDoctorChoice = "Dentist";
                        break;
                }
            }
        });

        KLradioBdoctorOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(KseniaMActivity.this);
                LayoutInflater inflater = KseniaMActivity.this.getLayoutInflater();
                View inflated = inflater.inflate(R.layout.form_elements, null);
                allergies = inflated.findViewById(R.id.allergies);
                sergeries = inflated.findViewById(R.id.sergeries);
                builder.setView(inflated)

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                addQestion1 = sergeries.getText().toString();
                                addQestion2 = allergies.getText().toString();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });

        KLradioBOptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(KseniaMActivity.this);
                LayoutInflater inflater = KseniaMActivity.this.getLayoutInflater();
                View inflated = inflater.inflate(R.layout.form_elements_opt, null);
                glassesDate = inflated.findViewById(R.id.glassesDate);
                glassesStore = inflated.findViewById(R.id.glassesStore);

                builder.setView(inflated)

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                addQestion1 = glassesDate.getText().toString();
                                addQestion2 = glassesStore.getText().toString();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });

        KLradioBDentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(KseniaMActivity.this);
                LayoutInflater inflater = KseniaMActivity.this.getLayoutInflater();

                View inflated = inflater.inflate(R.layout.form_elements_dent, null);
                yesBenefits = inflated.findViewById(R.id.checkBoxYesBenefits);
                noBenefits = inflated.findViewById(R.id.checkBoxNoBenefits);
                yesBraces = inflated.findViewById(R.id.checkBoxYesBraces);
                noBraces = inflated.findViewById(R.id.checkBoxNoBraces);


                builder.setView(inflater.inflate(R.layout.form_elements_dent, null))

                        // action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                yesBraces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean switchState) {
                                        Toast.makeText(getBaseContext(), "CheckBox is checked", Toast.LENGTH_SHORT).show();

                                        addQestion1 = "Yes Braces";
                                    }
                                });

                                yesBraces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean switchState) {
                                        addQestion1 = "No Braces";
                                    }
                                });

                                yesBenefits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean switchState) {
                                        addQestion2 = "Yes Benefits";
                                    }
                                });

                                yesBenefits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean switchState) {
                                        addQestion2 = "No Benefits";
                                    }
                                });


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });


        ListView listView = (ListView) findViewById(R.id.listview);
        Button btnThem = (Button) findViewById(R.id.btnThem);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName = edtName.getText().toString();
                String strAddress = edtAddress.getText().toString();
                String strBday = edtBirthday.getText().toString();
                String strPNumber = editPhoneNumber.getText().toString();
                String strHealthCard = edtHealthCard.getText().toString();
                String strDesc = edtDesc.getText().toString();

                boolean isInserted = mydatabase.InsertPatient(strDoctorChoice, strName,strAddress,
                        strBday, strPNumber, strHealthCard, strDesc, addQestion1, addQestion2);

                if(isInserted){
                    Toast.makeText(KseniaMActivity.this, "Patient is inserted", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(KseniaMActivity.this, "Patient IS NOT inserted", Toast.LENGTH_LONG).show();

                finish();
                startActivity(getIntent());
                overridePendingTransition(0,0);

            }
        });
        list = mydatabase.getAllStudent();
        adapter = new Custom(getApplication(),list);
        listView.setAdapter(adapter);
    }
}
