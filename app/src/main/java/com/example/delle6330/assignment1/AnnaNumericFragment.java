/* File name: AnnaNumericFragment.java
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AnnaNumericFragment extends Fragment {

    Button deleteButton;
    TextView textNumQuestion;
    TextView textNumAnswer;
    TextView textLambda;
    String questText;
    String correctAnswer;
    String lambda;
    String questType;

    boolean isTablet;
    int i;

    MultChoicDBHelper multDBHelper;

    SQLiteDatabase db;


    View fragmentView;

    /*
    *onCreate is used to get a Bundle
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_anna_numeric_fragment);

        Log.i("----OncreateNum", "opened");
        multDBHelper = new MultChoicDBHelper(this.getActivity());
        db = multDBHelper.getReadableDatabase();
        Bundle infoToPass = getArguments();

        Log.i("----BundleNum", String.valueOf(infoToPass.get("questionTextBundle")));
        Log.i("----BundleNum", String.valueOf(infoToPass.get("answerTextBundle")));

        questType = (String)infoToPass.get("questionTypeBundle");
        questText = (String)infoToPass.get("questionTextBundle");
        correctAnswer = (String)infoToPass.get("answerTextBundle");
        lambda = (String)infoToPass.get("lambdaTextBundle");
        isTablet = (Boolean)infoToPass.get("isTablet");

    }


    /*
   * onCreateView - creates view of fragment and sets deleteButton
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.activity_anna_numeric_fragment, null);
        textNumQuestion = (TextView) fragmentView.findViewById(R.id.textViewNumQuestionAnna);
        textNumQuestion.setText(questText);
        textNumAnswer = (TextView) fragmentView.findViewById(R.id.textViewNumAnswerAnna);
        textNumAnswer.setText(correctAnswer);
        textLambda = (TextView) fragmentView.findViewById(R.id.textViewNumLambdaAnna);
        textLambda.setText(lambda);

        deleteButton = (Button) fragmentView.findViewById(R.id.btnDeleteMsg);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isTablet){
                    ((AnnaQuestionsListView) getActivity()).deleteQuestion(questType, questText);
                } else {
                    Intent delIntent = new Intent(getActivity(), AnnaQuestionsListView.class);
                    delIntent.putExtra("questionTypeBundle", questType);
                    delIntent.putExtra("questionTextBundle", questText);
                    getActivity().setResult(Activity.RESULT_OK, delIntent);
                    getActivity().finish();
                }
            }
        });

        return fragmentView;
    }

}
