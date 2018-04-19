/* File name: NumericQuestion.java
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

public class NumericQuestion extends AnnaQuestionSuperClass {

    String answer;
    String lambda;

    public NumericQuestion(){

    }

    public NumericQuestion(int type, String quest, String answer, String lambda){
        super.setQuestType(type);
        super.setQuestion(quest);
        setAnswer(answer);
        setLambda(lambda);
    }



    public String getAnswer() {
        return answer;
    }


    public void setAnswer (String answer) {
        this.answer = answer;
    }

    public String getLambda() {
        return lambda;
    }


    public void setLambda (String lambda) {
        this.lambda = lambda;
    }

}
