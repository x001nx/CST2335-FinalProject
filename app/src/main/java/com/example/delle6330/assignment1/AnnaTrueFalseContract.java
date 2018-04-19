/* File name: AnnaTrueFalseContract.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to describe the table
 */

package com.example.delle6330.assignment1;

import android.provider.BaseColumns;

/**
 * Created by ansht on 2018-04-17.
 */

public class AnnaTrueFalseContract {

    private AnnaTrueFalseContract(){}

    public static class QuestionTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_TF_questions";
        public static final String COLUMN_QUESTION_TYPE = "questionType";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer_nr";

    }


}
