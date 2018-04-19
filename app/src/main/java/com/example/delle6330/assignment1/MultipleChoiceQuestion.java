/* File name: MultipleChoiceQuestion.java
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
 * Created by ansht on 2018-04-10.
 */

public class MultipleChoiceQuestion extends AnnaQuestionSuperClass {

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answerNr;

    public MultipleChoiceQuestion(){}


    public MultipleChoiceQuestion(int type, String quest, String option1, String option2, String option3, String option4, String answerNr) {
        super.setQuestType(type);
        super.setQuestion(quest);
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNr = answerNr;
    }



    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswerNr() {
        return answerNr;
    }



    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswerNr(String answerNr) {
        this.answerNr = answerNr;
    }


}
