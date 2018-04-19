/**
 * Created by Ksenia on 4/18/2018.
 * 040892102
 * CST2335_010
 */


package com.example.delle6330.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

/**
 * This class represents patient form and list view located under the form.
 * A user can add a patient and see the list of added patient scrolling down the screen
 * A user can choose a doctor patient wants to see, and add additional questions depending on doctor choice
 * When user sees the list of patients, he or she can delete a patient or edit the information about the patient
 * When the user chooses to edit the information about the patient, he is taken to another screen where he or she can do that
 */
public class DoctorChoice extends AppCompatActivity {

    /**
     * Database object
     */
    Mydatabase mydatabase;
    /**
     * ArrayList of patients
     */
    ArrayList<Patient> list = new ArrayList<>();
    /**
     * Customer adapter allowing a user to see patients in the listView
     */
    Custom adapter;

    /**
     * radio group preventing from clicking on all the radio buttons
     */
    RadioGroup radioGroup;
    /**
     * radio button representing user doctor choice
     */
    RadioButton KLradioBdoctorOffice;
    /**
     * radio button representing user doctor choice
     */
    RadioButton KLradioBDentist;
    /**
     * radio button representing user doctor choice
     */
    RadioButton KLradioBOptom;

    /**
     * String representation of doctor choice, that will be added into Patient object
     */
    String strDoctorChoice = "";
    /**
     * String representation of additional question 1, that will be added into Patient object
     */
    String addQestion1 = "";
    /**
     * String representation of additional question 2, that will be added into Patient object
     */
    String addQestion2 = "";

    /**
     * text entered by a user regarding his or her sergeries
     */
    EditText sergeries;
    /**
     * text entered by a user regarding his or her allergies
     */
    EditText allergies;

    /**
     * text entered by a user regarding his or her glasses purchase date
     */
    EditText glassesDate;
    /**
     * text entered by a user regarding his or her store where he or she bought glasses
     */
    EditText glassesStore;

    /**
     * text entered by a user regarding if he or she had braces
     */
    CheckBox yesBraces;

    /**
     * text entered by a user regarding if he or she had not have braces
     */
    CheckBox noBraces;
    /**
     * text entered by a user regarding if he or she has benefits
     */
    CheckBox yesBenefits;
    /**
     * text entered by a user regarding if he or she has not benefits
     */
    CheckBox noBenefits;

    /**
     * creates an activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_choice);

          mydatabase = new Mydatabase(getApplication());

        Toolbar toolbar = findViewById(R.id.bar);
//        setSupportActionBar(toolbar);

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


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
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

                Snackbar snackbar = Snackbar.make(view, "Doctor's Office was chosen", Snackbar.LENGTH_LONG);
                view = snackbar.getView();
                FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                params.gravity = Gravity.TOP;
                view.setLayoutParams(params);
                snackbar.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorChoice.this);
                LayoutInflater inflater = DoctorChoice.this.getLayoutInflater();
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

                Snackbar snackbar = Snackbar.make(view, "Optometrist was chosen", Snackbar.LENGTH_LONG);
                view = snackbar.getView();
                FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                params.gravity = Gravity.TOP;
                view.setLayoutParams(params);
                snackbar.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorChoice.this);
                LayoutInflater inflater = DoctorChoice.this.getLayoutInflater();
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

                Snackbar snackbar = Snackbar.make(view, "Dentist was chosen", Snackbar.LENGTH_LONG);
                view = snackbar.getView();
                FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                params.gravity = Gravity.TOP;
                view.setLayoutParams(params);
                snackbar.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorChoice.this);
                LayoutInflater inflater = DoctorChoice.this.getLayoutInflater();

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

                boolean isInserted = mydatabase.InsertPatient(strDoctorChoice, strName, strAddress,
                        strBday, strPNumber, strHealthCard, strDesc, addQestion1, addQestion2);

                if (isInserted) {
                    Toast.makeText(DoctorChoice.this, "Patient is inserted", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(DoctorChoice.this, "Patient IS NOT inserted", Toast.LENGTH_LONG).show();

                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }
        });
        list = mydatabase.getAllStudent();
        adapter = new Custom(getApplication(), list);
        listView.setAdapter(adapter);
    }
}