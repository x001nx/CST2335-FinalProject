/* File name: AnnaAddNumeric.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to add Numeric question
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

public class AnnaAddNumeric extends AppCompatActivity {
    Button add;
    EditText addQuest;
    EditText correctAnswer;
    EditText lambda;
    MultChoicDBHelper dbHelper;
    SQLiteDatabase db;
    AlertDialog.Builder builder;

    /*
* OnCreate, has onClickListener for button Add, and send all data to DB
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_add_numeric);

        add = findViewById(R.id.addBtnAnna);
        addQuest = findViewById(R.id.addNumericQuestionTextAnna);
        correctAnswer = findViewById(R.id.addCorrectNumericAnswerAnna);
        lambda = findViewById(R.id.lambdaToCheckAnswerAnna);
        dbHelper = new MultChoicDBHelper(this);
        db = dbHelper.getWritableDatabase();
        builder = new AlertDialog.Builder(this);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues cv = new ContentValues();
                cv.put(AnnaNumericContract.QuestionTable.COLUMN_QUESTION_TYPE, 3);
                cv.put(AnnaNumericContract.QuestionTable.COLUMN_QUESTION, String.valueOf(addQuest.getText()));
                cv.put(AnnaNumericContract.QuestionTable.COLUMN_ANSWER, String.valueOf(correctAnswer.getText()));
                cv.put(AnnaNumericContract.QuestionTable.COLUMN_LAMBDA, String.valueOf(lambda.getText()));
                db.insert(AnnaNumericContract.QuestionTable.TABLE_NAME, "nullColumnName", cv);


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
                        correctAnswer.setText("");
                        lambda.setText("");
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }
}
