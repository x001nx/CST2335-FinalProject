/* File name: TrueFalseQuestion.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to create an object that inherits from SuperClass
 */

package com.example.delle6330.assignment1;

/**
 * Created by ansht on 2018-04-17.
 */

public class TrueFalseQuestion extends AnnaQuestionSuperClass {

    private String answer;

    public TrueFalseQuestion(){}

    public TrueFalseQuestion (int type, String quest, String answer) {
        super.setQuestType(type);
        super.setQuestion(quest);
        setAnswer(answer);
    }

    public String getAnswer() {
        return answer;
    }


    public void setAnswer (String answer) {
        this.answer = answer;
    }



}
