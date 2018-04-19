/* File name: QuizActivity.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to describe the table
 */

package com.example.delle6330.assignment1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class QuizActivity extends Activity {

    protected static final String ACTIVITY_NAME = "QuizActivity";
    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";

    private TextView textViewHighScore;

    LinearLayout addQuestLL;
    LinearLayout quizLL;
    LinearLayout resultLL;
    LinearLayout questListLL;
    ImageView addQuestIV;
    ImageView quizIV;
    ImageView resultIV;
    ImageView questListIV;
    TextView addQuest;
    TextView quiz;
    TextView result;
    TextView questList;
    Context ctx;
    int highscore;
    ProgressBar progressBar;

    /*
    * onCreate - sets all layouts and clickListeners. Main page of Quiz app
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar tbar = (Toolbar)findViewById(R.id.quiz_toolbar);
        setActionBar(tbar);

        addQuest = findViewById(R.id.addQuestionTV);
        quiz = findViewById(R.id.quizTV);
        result = findViewById(R.id.resultsTV);
        questList = findViewById(R.id.questionListTV);
        addQuestLL = findViewById(R.id.addQuestionLL);
        quizLL = findViewById(R.id.quizLL);
        resultLL = findViewById(R.id.resultsLL);
        questListLL = findViewById(R.id.questionListLL);
        addQuestIV = findViewById(R.id.addQuestionIV);
        quizIV = findViewById(R.id.quizIV);
        resultIV = findViewById(R.id.resultsIV);
        questListIV = findViewById(R.id.questionListIV);
        progressBar = findViewById (R.id.progressBarAnna);
        

        ctx = this;

        addQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddQuestActivity();
            }
        });

        addQuestLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddQuestActivity();
            }
        });

        addQuestIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddQuestActivity();
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTakeQuiz();
            }
        });

        quizLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTakeQuiz();
            }
        });

        quizIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTakeQuiz();
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callResults();
            }
        });

        resultLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callResults();
            }
        });

        resultIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callResults();
            }
        });

        questList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callQuestList();
            }
        });

        questListLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callQuestList();
            }
        });

        questListIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callQuestList();
            }
        });

    }

    private void callAddQuestActivity() {
        Toast.makeText(ctx, "Numeric", Toast.LENGTH_LONG).show();

        Intent intent = new Intent (QuizActivity.this, AnnaAddQuestionsMenu.class);
        startActivity(intent);

        Log.i(ACTIVITY_NAME, "User clicked Numeric");
    }

    private void callTakeQuiz() {
        Snackbar snackbar = Snackbar.make(quiz, "Multiple choice", Snackbar.LENGTH_LONG);
        snackbar.show();

        Intent intent = new Intent(QuizActivity.this, AnnaTakeQuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

        Log.i(ACTIVITY_NAME, "User clicked Multiple Choice");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(AnnaTakeQuizActivity.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighScore(score);
                }
            }
        }
    }

    private void callResults() {

        Intent intent = new Intent(QuizActivity.this, AnnaResultActivity.class);
        intent.putExtra("Score", highscore);
        startActivity(intent);

        Log.i(ACTIVITY_NAME, "User clicked Results");
    }

    private void loadHighScore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighScore.setText("Highscore: " + highscore);
    }

    private void updateHighScore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighScore.setText("Highscore: " + highscore);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }

    private void callQuestList() {
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

        Intent intent = new Intent(QuizActivity.this, AnnaQuestionsListView.class);
        startActivity(intent);

        Log.i(ACTIVITY_NAME, "User clicked True False");
    }



    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.menu_quiz_toolbar, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();

        switch(id){
            case R.id.import_xml:


                MultChoicDBHelper helpMult = new MultChoicDBHelper(QuizActivity.this);


                 if(   helpMult.importXML()) {
                     Log.i("Data uploaded", "helpMult.importXML()");

                     Toast.makeText(getApplicationContext(), "DataBase imported successfully", Toast.LENGTH_LONG).show();

                 }

                 break;

        }

        return true;
    }



}
