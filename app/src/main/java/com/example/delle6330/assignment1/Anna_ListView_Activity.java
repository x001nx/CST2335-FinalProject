package com.example.delle6330.assignment1;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.delle6330.assignment1.R.id.questionViewAnna;

public class Anna_ListView_Activity extends AppCompatActivity {

    Button answerAnna ;
    ListView listViewAnna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna__list_view_);
        answerAnna = (Button) findViewById(R.id.answerButtonAnna);
//        listViewAnna = (ListView) findViewById(R.id.questionViewAnna);

        answerAnna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(answerAnna, "Saving answer", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
