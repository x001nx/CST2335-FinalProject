/* File name: AnnaQuestionsListView.java
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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnnaQuestionsListView extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "Questions_List_View";

    ListView listView;
    QuestionsAdapter questionListAdapter;
    MultipleChoiceQuestion question;
    ArrayList<MultipleChoiceQuestion> multQuestions;
    ArrayList<TrueFalseQuestion> tfQuestions;
    ArrayList<NumericQuestion> numQuestions;
    MultChoicDBHelper MultDBHelper;
    SQLiteDatabase db;
    ContentValues cv;
    Cursor cursor;
    boolean isTablet;
    int clickedPosition;

    String str2;

    FrameLayout frameLayout;
    ArrayList<AnnaQuestionSuperClass> qText;
    AnnaMultChoiceFragment multFrag;
    AnnaTrueFalseFragment tfFrag;
    AnnaNumericFragment numFrag;
    Button deleteButton;

    Bundle infoToPassNum;
    Bundle infoToPassTF;
    Bundle infoToPassMC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_questions_list_view);


        //Check if frame layout is loaded
        frameLayout = findViewById(R.id.tablet_frame);

        if (frameLayout != null) {
            isTablet = true;
            Log.i("****** Frame loaded", "Tablet " + isTablet);
        } else {
            isTablet = false;
            Log.i("****** Frame loaded", "Tablet " + isTablet);

        }



        listView = findViewById(R.id.questionViewAnna);



        MultDBHelper = new MultChoicDBHelper(this);
        db = MultDBHelper.getWritableDatabase();
        multQuestions = MultDBHelper.getAllMultQuestions();
//        Log.i("***MULT quest", multQuestions.get(0).getQuestion());
//        TFDBHelper tfDbHelper = new TFDBHelper(this);
        tfQuestions = MultDBHelper.getAllTFQuestions();
//        Log.i("***TF quest", tfQuestions.get(0).getQuestion());
//        NumericDBHelper numDbHelper = new NumericDBHelper(this);
        numQuestions = MultDBHelper.getAllNumQuestions();
//        Log.i("***NUM quest", numQuestions.get(0).getQuestion());
        qText = getQuestionTexts();
        questionListAdapter = new QuestionsAdapter(this);
        listView.setAdapter(questionListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MultipleChoiceQuestion mq = new MultipleChoiceQuestion();
                TrueFalseQuestion tf = new TrueFalseQuestion();
                NumericQuestion num = new NumericQuestion();

                TextView questType = view.findViewById(R.id.questType);
                TextView questText = view.findViewById(R.id.questText);

                Log.i(">>>Clicked on type",String.valueOf(questType.getText()) );
                Log.i(">>>Clicked on quest",String.valueOf(questText.getText()) );

                switch(String.valueOf(questType.getText())){

                    case "MF":
                        String questMC = String.valueOf(questText.getText());
                        Log.i("@@@String", questMC);
                        mq = MultDBHelper.findMCQuestionByTypeAndText(questMC);
                       infoToPassMC = new Bundle();
                        infoToPassMC.putString("questionTypeBundle", String.valueOf(mq.getQuestionType()));
                        infoToPassMC.putString("questionTextBundle", mq.getQuestion());
                        infoToPassMC.putString("option1Bundle", mq.getOption1());
                        infoToPassMC.putString("option2Bundle", mq.getOption2());
                        infoToPassMC.putString("option3Bundle", mq.getOption3());
                        infoToPassMC.putString("option4Bundle", mq.getOption4());
                        infoToPassMC.putString("answerTextBundle", mq.getAnswerNr());
                        infoToPassMC.putBoolean("isTablet", isTablet);
                        break;

                    case "TF":
                        String questTF = String.valueOf(questText.getText());
                        Log.i("@@@String", questTF);
                        tf = MultDBHelper.findTFQuestionByTypeAndText(questTF);
                        infoToPassTF = new Bundle();
                        infoToPassTF.putString("questionTypeBundle", String.valueOf(tf.getQuestionType()));
                        infoToPassTF.putString("questionTextBundle", tf.getQuestion());
                        infoToPassTF.putString("answerTextBundle", tf.getAnswer());
                        infoToPassTF.putBoolean("isTablet", isTablet);
                        break;

                    case "N":
                        String questNum = String.valueOf(questText.getText());
                        Log.i("@@@String", questNum);
                        num = MultDBHelper.findNumQuestionByTypeAndText(questNum);
                       infoToPassNum = new Bundle();
                        infoToPassNum.putString("questionTypeBundle", String.valueOf(num.getQuestionType()));
                        infoToPassNum.putString("questionTextBundle", num.getQuestion());
                        infoToPassNum.putString("answerTextBundle", num.getAnswer());
                        infoToPassNum.putString("lambdaTextBundle", num.getLambda());
                        infoToPassNum.putBoolean("isTablet", isTablet);
                        break;

                }

                if (isTablet){

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    switch(String.valueOf(questType.getText())) {
                        case ("MF"):
                            multFrag = new AnnaMultChoiceFragment();
                            multFrag.setArguments (infoToPassMC);
                            ft.replace(R.id.tablet_frame, multFrag);
                            ft.commit();
                            break;
                        case ("TF"):
                            tfFrag = new AnnaTrueFalseFragment();
                            tfFrag.setArguments (infoToPassTF);
                            ft.replace(R.id.tablet_frame, tfFrag);
                            ft.commit();
                            break;
                        case ("N"):
                            numFrag = new AnnaNumericFragment();
                            numFrag.setArguments (infoToPassNum);
                            ft.replace(R.id.tablet_frame, numFrag);
                            ft.commit();
                            break;
                    }
                }
                else {
                    Log.i("Intent phone", "phone");
                    Intent phoneQuestView = new Intent (AnnaQuestionsListView.this, AnnaQuestionDetails.class);

                    switch(String.valueOf(questType.getText())) {
                        case ("MF"):
                            phoneQuestView.putExtras(infoToPassMC);
                            break;
                        case ("TF"):
                            phoneQuestView.putExtras(infoToPassTF);
                            break;
                        case ("N"):
                            phoneQuestView.putExtras(infoToPassNum);
                            break;
                    }
                    startActivityForResult(phoneQuestView, 50);
                }
            }

        }  );





//Here should be changed
//        cv = new ContentValues();
//        cursor = db.query(MultChoicDBHelper.DATABASE_NAME, new String[]{ AnnaMultChoiceContract.QuestionTable._ID, AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION, AnnaMultChoiceContract.QuestionTable.COLUMN_ANSWER_NR}, null, null, null, null, null, null);



    }

    /*
    * Method returns ArrayList of objects, uses polymorphic approach, gets all objects of different types that inherit from the same abstract class
     */

    public ArrayList<AnnaQuestionSuperClass> getQuestionTexts() {
        int length_of_array = multQuestions.size()+tfQuestions.size()+numQuestions.size();
        Log.i("****LENGTH OF ARRAY", String.valueOf(length_of_array));
        Log.i("****MULTIPLE SIZE", String.valueOf(multQuestions.size()));
        Log.i("****MULTIPLE+TF SIZE", String.valueOf(multQuestions.size()+tfQuestions.size()));
        int i = 0;

        qText = new ArrayList<>();
        while (i < length_of_array) {
            for (int j = 0; j < multQuestions.size(); j++) {

                AnnaQuestionSuperClass multQuestObj = new AnnaQuestionSuperClass (multQuestions.get(j).getQuestionType(), multQuestions.get(j).getQuestion());


                qText.add(i, multQuestObj);
                Log.i("Array got Mult", qText.get(i).getQuestion());
                i++;
            }
            for (int j = 0; j < tfQuestions.size(); j++) {
                AnnaQuestionSuperClass tfQuestObj = new AnnaQuestionSuperClass (tfQuestions.get(j).getQuestionType(), tfQuestions.get(j).getQuestion());
                qText.add(i, tfQuestObj);
                Log.i("Array got TF", qText.get(i).getQuestion());
                i++;
            }

            for (int j = 0; j < numQuestions.size(); j++) {
                AnnaQuestionSuperClass numQuestObj = new AnnaQuestionSuperClass (numQuestions.get(j).getQuestionType(), numQuestions.get(j).getQuestion());
                qText.add(i, numQuestObj);
                Log.i("Array got NUM", qText.get(i).getQuestion());
                i++;
            }
        }
        return qText;
    }

    /*
    * method to delete question from DB, gets parameters from fragments
     */

    public void deleteQuestion(String t, String s) {

        //DATABASE
        //DatabaseHelper
        MultChoicDBHelper dbHelper = new MultChoicDBHelper(this);
        //Get WritableDatabase
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //ContentView
        ContentValues cv = new ContentValues();
        //Read from database
        Log.i("---DELETE TYPE", t);
        switch (t){
            case "1":
                db.delete(AnnaMultChoiceContract.QuestionTable.TABLE_NAME, AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION + " = ?" , new String[]{s});
                break;
            case "2":
                db.delete(AnnaTrueFalseContract.QuestionTable.TABLE_NAME, AnnaTrueFalseContract.QuestionTable.COLUMN_QUESTION + " = ?" , new String[]{s});
                break;
            case "3":
                db.delete(AnnaNumericContract.QuestionTable.TABLE_NAME, AnnaNumericContract.QuestionTable.COLUMN_QUESTION + " = ?" , new String[]{s});
                break;
        }

        for (int i=0; i<qText.size(); i++){
            if (qText.get(i).getQuestion().equals(s)){
                qText.remove(i);
            }
        }
        questionListAdapter.notifyDataSetChanged();


    }

/*
*ListView adapter to inflate list view with question texts from DB
 */

    public class QuestionsAdapter extends ArrayAdapter<String>{
        LayoutInflater inflater = AnnaQuestionsListView.this.getLayoutInflater();

//        ArrayList<AnnaQuestionSuperClass> questList = new ArrayList<AnnaQuestionSuperClass> ();

        /**
         * New ListView element layout
         */
        View result;

        /**
         * Default constructor
         * @param ctx
         */
        public QuestionsAdapter(Context ctx) {
            super(ctx, 0);
        }

        /**
         * Lenght of stationsArray
         * @return
         */
        public int getCount() {
            return qText.size();
        }

        /**
         * Get one item from stationsArray
         * @param position
         * @return
         */
        public int getQuestType(int position) {

            return qText.get(position).getQuestionType();
        }

        public String getQuestText(int position) {
            return qText.get(position).getQuestion();
        }

        /**
         * Create view
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            //CREATE INFLATER
            //SET LAYOUT USING INFLATOR - IF POSSITION INCOMING OUTGOING
            for(AnnaQuestionSuperClass question: qText){
                result = inflater.inflate(R.layout.question_layout, null);
            }

            //FIND TEXTVIEW IN RESULT
            TextView questType = (TextView) result.findViewById(R.id.questType);
            TextView questText = result.findViewById(R.id.questText);
            //SET TEXT IN RESULT
            Log.i("******getView", String.valueOf(getQuestType(position)) + getQuestText(position));
            switch (getQuestType(position)){
                case 1:
                    questType.setText("MF");
                    break;
                case 2:
                    questType.setText("TF");
                    break;
                case 3:
                    questType.setText("N");
                    break;
            }

            questText.setText(getQuestText(position));
            return result;
        }

        public long getId(int position) {
            return position;
        }

    }

    /*
    *onActivityResult is used to delete question when frame is for the phone and the question shown in separate layout
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        final SQLiteDatabase db = MultDBHelper.getWritableDatabase();
        Log.i("onActivityResult","");

        if (requestCode == 50) {
            if (resultCode == Activity.RESULT_OK){
                //update databse
                Bundle bun = data.getExtras();
                String questionText = (String) bun.get("questionTextBundle");
                String questionType = (String) bun.get("questionTypeBundle");
                deleteQuestion(questionType, questionText);
                questionListAdapter.notifyDataSetChanged();
            }
        }
    }


}
