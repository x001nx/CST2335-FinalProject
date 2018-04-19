package com.example.delle6330.assignment1;

/**
 * Created by ansht on 2018-04-17.
 */

public class AnnaQuestionSuperClass {

    private int questType;
    private String question;

    public AnnaQuestionSuperClass(){}


    public AnnaQuestionSuperClass(int questType, String question) {
        this.questType = questType;
        this.question = question;
    }

    public int getQuestionType() {
        return questType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestType (int questType) {
        this.questType = questType;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
