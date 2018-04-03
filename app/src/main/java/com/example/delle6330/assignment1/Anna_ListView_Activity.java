package com.example.delle6330.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Anna_ListView_Activity extends AppCompatActivity {

    Button answerAnna ;
    ListView listViewAnna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_list_view);
        answerAnna = (Button) findViewById(R.id.answerButtonAnna);
        answerAnna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(answerAnna, "Saving answer", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
