package com.example.delle6330.assignment1;

import android.content.ContentValues;
import android.content.Intent;
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



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues cv = new ContentValues();
                cv.put(MultChoiceContract.QuestionTable.COLUMN_QUESTION_TYPE, 1);
                cv.put(MultChoiceContract.QuestionTable.COLUMN_QUESTION, String.valueOf(addQuest.getText()));
                cv.put(MultChoiceContract.QuestionTable.COLUMN_OPTION1, String.valueOf(answer1.getText()));
                cv.put(MultChoiceContract.QuestionTable.COLUMN_OPTION2, String.valueOf(answer2.getText()));
                cv.put(MultChoiceContract.QuestionTable.COLUMN_OPTION3, String.valueOf(answer3.getText()));
                cv.put(MultChoiceContract.QuestionTable.COLUMN_OPTION4, String.valueOf(answer4.getText()));
                db.insert(MultChoiceContract.QuestionTable.TABLE_NAME, "nullColumnName", cv);
            }
        });

    }
}
