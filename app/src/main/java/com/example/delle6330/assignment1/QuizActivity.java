package com.example.delle6330.assignment1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
    TextView numeric;
    TextView multiple;
    TextView trueFalse;
    TextView results;
    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        numeric = findViewById(R.id.numericAnswerTV);
        multiple = findViewById(R.id.multipleChoiceTV);
        trueFalse = findViewById(R.id.trueFalseTV);
        results = findViewById(R.id.resultTV);
        ctx = this;

        numeric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Numeric", Toast.LENGTH_LONG).show();
            }
        });

        multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(multiple, "Poshel v jopu", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        trueFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.activity_dialog);
                TextView txt = (TextView) dialog.findViewById(R.id.txt);
                txt.setText("Notification will be here");
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton);
                dialogButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, AnnaListView.class));
            }
        });

    }
}
