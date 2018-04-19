/* File name: AnnaAddQuestionsMenu.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to add Multiple Choice question
 */
package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnnaAddQuestionsMenu extends AppCompatActivity {

    Button addMultChoice;
    Button addTrueFalse;
    Button addNumeric;

    /*
* OnCreate, has onClickListeners for buttons that send to the form to add specific question
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_add_questions_menu);
        addMultChoice = (Button) findViewById(R.id.multChoiceBtnAnna);
        addTrueFalse = (Button) findViewById(R.id.trueFalseBtnAnna);
        addNumeric = (Button) findViewById(R.id.numericBtnAnna);

        addMultChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (AnnaAddQuestionsMenu.this, AnnaAddMultipleChoice.class);
                startActivity(intent);
            }
        });

        addTrueFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (AnnaAddQuestionsMenu.this, AnnaAddTrueFalse.class);
                startActivity(intent);
            }
        });

        addNumeric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (AnnaAddQuestionsMenu.this, AnnaAddNumeric.class);
                startActivity(intent);
            }
        });
    }
}
