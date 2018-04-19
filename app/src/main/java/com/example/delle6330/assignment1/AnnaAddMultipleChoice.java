/* File name: AnnaAddMultipleChoice.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to add Multiple Choice question
 */

package com.example.delle6330.assignment1;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnnaAddMultipleChoice extends AppCompatActivity {

    Button add;
    EditText addQuest;
    EditText answer1;
    EditText answer2;
    EditText answer3;
    EditText answer4;
    EditText correctAnswer;
    MultChoicDBHelper dbHelper;
    SQLiteDatabase db;
    AlertDialog.Builder builder;


/*
* OnCreate, has onClickListener for button Add, and send all data to DB
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_add_multiple_choice);

        add = findViewById(R.id.addBtnAnna);
        addQuest = findViewById(R.id.addQuestionTextAnna);
        answer1 = findViewById(R.id.addAnswerOption1Anna);
        answer2 = findViewById(R.id.addAnswerOption2Anna);
        answer3 = findViewById(R.id.addAnswerOption3Anna);
        answer4 = findViewById(R.id.addAnswerOption4Anna);
        correctAnswer = findViewById(R.id.addCorrectAnswerNumberAnna);
        dbHelper = new MultChoicDBHelper(this);
        db = dbHelper.getWritableDatabase();
        builder = new AlertDialog.Builder(this);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues cv = new ContentValues();
                cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION_TYPE, 1);
                cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION, String.valueOf(addQuest.getText()));
                cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION1, String.valueOf(answer1.getText()));
                cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION2, String.valueOf(answer2.getText()));
                cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION3, String.valueOf(answer3.getText()));
                cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION4, String.valueOf(answer4.getText()));
                db.insert(AnnaMultChoiceContract.QuestionTable.TABLE_NAME, "nullColumnName", cv);
                builder.setTitle(R.string.multiple_dialog);
                //Add the buttons

                builder.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.addMore, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        addQuest.setText("");
                        answer1.setText("");
                        answer2.setText("");
                        answer3.setText("");
                        answer4.setText("");
                        correctAnswer.setText("");
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }
}
