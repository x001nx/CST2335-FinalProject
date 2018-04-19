/* File name: AnnaTrueFalseFragment.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to create a fragment for TrueFalse question
 */

package com.example.delle6330.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class AnnaTrueFalseFragment extends Fragment {

    Button deleteButton;
    TextView textTFQuestion;
    TextView textTFAnswer;

    boolean isTablet;
    long ID;
    String qst;
    String answ;
    String type;

    MultChoicDBHelper multDBHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        Log.i("----OncreateTF", "opened");

        multDBHelper = new MultChoicDBHelper(this.getActivity());
        db = multDBHelper.getReadableDatabase();

        Bundle infoToPass = getArguments();

        Log.i("----BundleTF", String.valueOf(infoToPass.get("questionTypeBundle")));
        Log.i("----BundleTF", String.valueOf(infoToPass.get("questionTextBundle")));
        Log.i("----BundleTF", String.valueOf(infoToPass.get("answerTextBundle")));

        type = (String)infoToPass.get("questionTypeBundle");
        qst = (String)infoToPass.get("questionTextBundle");
        answ = (String)infoToPass.get("answerTextBundle");
        isTablet = (Boolean)infoToPass.get("isTablet");
    }

    /*
    *onCreateView
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.activity_anna_true_false_fragment, null);
        textTFQuestion = (TextView) fragmentView.findViewById(R.id.textViewTFQuestionAnna);
        textTFQuestion.setText(qst);
        textTFAnswer = (TextView) fragmentView.findViewById(R.id.textViewTFAnswerAnna);
        textTFAnswer.setText(answ);

        deleteButton = (Button) fragmentView.findViewById(R.id.btnDeleteQst);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isTablet){
                    ((AnnaQuestionsListView) getActivity()).deleteQuestion(type, qst);
                } else {
                    Intent delIntent = new Intent(getActivity(), AnnaQuestionsListView.class);
                    delIntent.putExtra("questionTypeBundle", type);
                    delIntent.putExtra("questionTextBundle", qst);
                    getActivity().setResult(Activity.RESULT_OK, delIntent);
                    getActivity().finish();
                }
            }
        });

        return fragmentView;
    }


}
