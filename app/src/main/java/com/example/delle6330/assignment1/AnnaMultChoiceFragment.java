/* File name: AnnaMultChoiceFragment.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to describe the table
 */

package com.example.delle6330.assignment1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AnnaMultChoiceFragment extends Fragment {

    Button deleteButton;
    TextView textMultQuestion;
    TextView textOption1;
    TextView textOption2;
    TextView textOption3;
    TextView textOption4;
    TextView textAnswer;
    String questText;
    String option1;
    String option2;
    String option3;
    String option4;
    String correctAnswer;
    String questType;

    boolean isTablet;
    int i;
    String qst;
    String answ;
    MultChoicDBHelper multDBHelper;

    SQLiteDatabase db;


    View fragmentView;

    /*
    *onCreate is used to get a Bundle
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_anna_mult_choice_fragment);
    Log.i("----Oncreate", "opened");
        multDBHelper = new MultChoicDBHelper(this.getActivity());
        db = multDBHelper.getReadableDatabase();
        i = 0;
        Bundle infoToPass = getArguments();


        questType = (String) infoToPass.get("questionTypeBundle");
        questText = (String) infoToPass.get("questionTextBundle");
        option1 = (String) infoToPass.get("option1Bundle");
        option2 = (String) infoToPass.get("option2Bundle");
        option3 = (String) infoToPass.get("option3Bundle");
        option4 = (String) infoToPass.get("option4Bundle");
        correctAnswer = (String) infoToPass.get("answerTextBundle");
        isTablet = (Boolean) infoToPass.get("isTablet");

    }

    /*
    * onCreateView - creates view of fragment and sets deleteButton
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("----OncreatView", "opened");
        fragmentView = inflater.inflate(R.layout.activity_anna_mult_choice_fragment, null);
        textMultQuestion = fragmentView.findViewById(R.id.textViewMultQuestionAnna);
        textMultQuestion.setText(questText);

        textOption1 = fragmentView.findViewById(R.id.textViewMultOption1Anna);
        textOption1.setText(option1);

        textOption2 = fragmentView.findViewById(R.id.textViewMultOption2Anna);
        textOption2.setText(option2);

        textOption3 = fragmentView.findViewById(R.id.textViewMultOption3Anna);
        textOption3.setText(option3);

        textOption4 = fragmentView.findViewById(R.id.textViewMultOption4Anna);
        textOption4.setText(option4);

        textAnswer = fragmentView.findViewById(R.id.textViewMultAnswerAnna);
        textAnswer.setText(correctAnswer);


        deleteButton = fragmentView.findViewById(R.id.btnDeleteQuestion);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTablet) {
                    ((AnnaQuestionsListView) getActivity()).deleteQuestion(questType, questText);
                    textMultQuestion.setText("");
                    textOption1.setText("");
                    textOption2.setText("");
                    textOption3.setText("");
                    textOption4.setText("");
                    textAnswer.setText("");
                } else {
                    Intent getBack = new Intent(getActivity(), AnnaQuestionsListView.class);
                    getBack.putExtra("questionTypeBundle", questType);
                    getBack.putExtra("questionTextBundle", questText);
                    getActivity().setResult(Activity.RESULT_OK, getBack);
                    getActivity().finish();
                }
            }
        });

return fragmentView;
    }
}
