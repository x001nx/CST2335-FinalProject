package com.example.delle6330.assignment1;

import android.provider.BaseColumns;

/**
 * Created by ansht on 2018-04-10.
 */

public class MultChoiceContract {

    private MultChoiceContract(){}

    public static class QuestionTable implements BaseColumns{
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION_TYPE = "questionType";
       public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";

    }

}
