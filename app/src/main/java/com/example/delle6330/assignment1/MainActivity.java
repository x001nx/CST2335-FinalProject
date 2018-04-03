package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button pavel;
    Button ksenia;
    Button anna;
    Button svetlana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pavel = findViewById(R.id.pavel);
        ksenia = findViewById(R.id.ksenia);
        anna = findViewById(R.id.anna);
        svetlana = findViewById(R.id.svetlana);

        //Buttons to individual Activities
        anna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        pavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this, PavelStartActivity.class);
                startActivity(intent);
            }
        });

        ksenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DoctorChoice.class);
                startActivity(intent);
            }
        });

        svetlana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MovieActivity.class)) ;
            }
        });
    }
}
