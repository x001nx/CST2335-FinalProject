/* File name: AnnaResultActivity.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: raw template of the page with results when real quiz is taken
 */

package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnnaResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_result);

        Button backBtn = findViewById(R.id.button_go_back);
int score = getIntent().getIntExtra("Score", 0);

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent (AnnaResultActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}
