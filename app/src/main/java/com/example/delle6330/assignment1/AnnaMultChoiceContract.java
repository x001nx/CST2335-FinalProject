/* File name: AnnaMultChoiceContract.java
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
 * Created by ansht on 2018-04-10.
 */

public class AnnaMultChoiceContract {

    private AnnaMultChoiceContract(){}

    /*
    * Inner class to set columns in the table
     */
    public static class QuestionTable implements BaseColumns{
        public static final String TABLE_NAME = "quiz_mult_questions";
        public static final String COLUMN_QUESTION_TYPE = "questionType";
       public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";

    }

}
