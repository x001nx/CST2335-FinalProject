package com.example.delle6330.assignment1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnaTakeQuizActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "AnnaMultipleChoice";
    public static final String EXTRA_SCORE = "extraScore";
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonAnswer;

    private ColorStateList textColorDefaultRb;

    private List<MultipleChoiceQuestion> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private MultipleChoiceQuestion currentQuestion;

    private int score;
    private boolean answered;

    ListView answers;
    ArrayList<String> answersArray;
private String question = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anna_multiple_choice);

        textViewQuestion = findViewById(R.id.text_view_question_anna);
        textViewScore = findViewById(R.id.text_view_score_anna);
        textViewCountDown = findViewById(R.id.text_view_countdown_anna);
        textViewQuestionCount = findViewById(R.id.text_view_question_count_anna);
        rbGroup = findViewById(R.id.radio_group_anna);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonAnswer = findViewById(R.id.button_answer_anna);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultRb = rb2.getTextColors();
        textColorDefaultRb = rb3.getTextColors();
        textColorDefaultRb = rb4.getTextColors();

        MultChoicDBHelper dbHelper = new MultChoicDBHelper(this);
        questionList = dbHelper.getAllQuestions();

        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();

                    }else {
                        Toast.makeText(AnnaTakeQuizActivity.this, "Please, select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });


    }


private void showNextQuestion(){
        rb1.setTextColor(textColorDefaultRb);
    rb2.setTextColor(textColorDefaultRb);
    rb3.setTextColor(textColorDefaultRb);
    rb4.setTextColor(textColorDefaultRb);
    rbGroup.clearCheck();

    if(questionCounter < questionCountTotal) {
        currentQuestion = questionList.get(questionCounter);

        textViewQuestion.setText(currentQuestion.getQuestion());

        switch(currentQuestion.getQuestionType()){
            case 1:
                rb1.setText(currentQuestion.getOption1());
                rb2.setText(currentQuestion.getOption2());
                rb3.setText(currentQuestion.getOption3());
                rb4.setText(currentQuestion.getOption4());
                break;
            case 2:
                rb1.setText("True");
                rb2.setText("False");
                rb3.setEnabled(false);
                rb4.setEnabled(false);
            default:
                break;
        }


        questionCounter++;
        textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
        answered = false;
        buttonAnswer.setText("Answer");
    }
        else {
        Intent intent = new Intent(AnnaTakeQuizActivity.this, AnnaResultActivity.class);
        startActivity(intent);
    }
    }

    public void checkAnswer() {
    answered = true;

    RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
    String answerNr = String.valueOf(rbGroup.indexOfChild(rbSelected)+1);
    Log.i("*******String.value of ", answerNr);
    if(answerNr == currentQuestion.getAnswerNr()) {
        score++;
        textViewScore.setText("Score: " + score);
    }
    showSolution();
    }

    public void showSolution(){
    rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        if(rb1.getText() == currentQuestion.getAnswerNr())
        {
            rb1.setTextColor(Color.BLUE);
            textViewQuestion.setText ("Answer 1 is correct");
        }
        else if (rb2.getText() == currentQuestion.getAnswerNr())
        {
            rb2.setTextColor(Color.BLUE);
            textViewQuestion.setText ("Answer 2 is correct");
        }
        else if (rb3.getText() == currentQuestion.getAnswerNr())
        {
            rb3.setTextColor(Color.BLUE);
            textViewQuestion.setText ("Answer 3 is correct");
        }
        else if (rb4.getText() == currentQuestion.getAnswerNr())
        {
            rb4.setTextColor(Color.BLUE);
            textViewQuestion.setText ("Answer 4 is correct");
        }

//        switch(currentQuestion.getAnswerNr()) {
//            case "1":
//                rb1.setTextColor(Color.BLUE);
//                textViewQuestion.setText ("Answer 1 is correct");
//                break;
//            case 2:
//                rb2.setTextColor(Color.BLUE);
//                textViewQuestion.setText ("Answer 2 is correct");
//                break;
//            case 3:
//                rb3.setTextColor(Color.BLUE);
//                textViewQuestion.setText ("Answer 3 is correct");
//                        break;
//            case 4:
//                rb4.setTextColor(Color.BLUE);
//                textViewQuestion.setText ("Answer 4 is correct");
//                break;
//        }
        if (questionCounter < questionCountTotal) {
            buttonAnswer.setText("Next");}
            else{
            buttonAnswer.setText("Finish");

        }
    }

    public void finishQuiz(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
    finish();
    }
}




