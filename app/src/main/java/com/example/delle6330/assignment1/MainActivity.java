package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

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
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.maintoolbarmenu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        Log.i("****** Lab8 Id", String.valueOf(mi.getItemId()));

        switch (id) {
            case R.id.anna_toolbar:
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
                break;
            case R.id.ksenia_toolbar:
                startActivity(new Intent(MainActivity.this, DoctorChoice.class));
                break;
            case R.id.pavel_toolbar:
                startActivity(new Intent(MainActivity.this, PavelStartActivity.class));
                break;
            case R.id.svetlana_toolbar:
                startActivity(new Intent(MainActivity.this, MovieActivity.class));
                break;
            default:
                break;
        }
        return true;
    }
}
