package com.example.delle6330.assignment1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AnnaQuestionDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("QuestionDetails", "Here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_question_details);

        //Load fragment to phone:
        Bundle infoToPass = getIntent().getExtras();
        Log.i("Type", infoToPass.getString("questionTypeBundle" ));
        Log.i("Question" , infoToPass.getString("questionTextBundle"));

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//
//        if (infoToPass.getString("questionTextBundle").equalsIgnoreCase("1")){
//            AnnaMultChoiceFragment multFrag = new AnnaMultChoiceFragment();
//            multFrag.setArguments(infoToPass);
//
//            ft.replace(R.id.phone_frame, multFrag);
//            ft.commit();
//
//        }

        switch (String.valueOf(infoToPass.getString("questionTypeBundle"))) {
            case "1":
                AnnaMultChoiceFragment multFrag = new AnnaMultChoiceFragment();
                multFrag.setArguments(infoToPass);

                ft.replace(R.id.phone_frame, multFrag);
                ft.commit();
                break;
            case "2":
                AnnaTrueFalseFragment tfFrag = new AnnaTrueFalseFragment();
                tfFrag.setArguments(infoToPass);

                ft.replace(R.id.phone_frame, tfFrag);
                ft.commit();
                break;
            case "3":
                AnnaNumericFragment numFrag = new AnnaNumericFragment();
                numFrag.setArguments(infoToPass);

                ft.replace(R.id.phone_frame, numFrag);
                ft.commit();
                break;

        }
    }
}
