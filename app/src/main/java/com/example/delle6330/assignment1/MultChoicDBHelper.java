/* File name: MultChoicDBHelper.java
 * @Author: Anna Shteyngart,
 * @Student#: 040883547
 * @Course: CST2335
 * @Assignment: FinalProject
 * @Date: 19/04/2018
 * @Professor: Erik Torunski
 * @Class purpose: class to create a fragment for TrueFalse question
 */

package com.example.delle6330.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ansht on 2018-04-10.
 */

public class MultChoicDBHelper extends SQLiteOpenHelper {
    protected static final String ACTIVITY_NAME = "MultChoicDBHelper";
    static final String DATABASE_NAME = "NewQuizDB_2";
    private static final int DATABASE_VERSION = 5;
    private String urlString;
    SQLiteDatabase db;


    private int questType;
    private String question;

    private String answer;
    private String answerNum;
    private String answerTF;
    private String lambda;
    ArrayList<Integer> type;

    public MultChoicDBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;


        final String SQL_CREATE_MULT_QUESTIONS_TABLE = "CREATE TABLE " +
                AnnaMultChoiceContract.QuestionTable.TABLE_NAME + " ( " +
                AnnaMultChoiceContract.QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION_TYPE + " INTEGER, " +
                AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION + " TEXT, " +
                AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                AnnaMultChoiceContract.QuestionTable.COLUMN_ANSWER_NR + " TEXT" +
                ")";
        final String SQL_CREATE_TF_QUESTIONS_TABLE = "CREATE TABLE " +
                AnnaTrueFalseContract.QuestionTable.TABLE_NAME + " ( " +
                AnnaTrueFalseContract.QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AnnaTrueFalseContract.QuestionTable.COLUMN_QUESTION_TYPE + " INTEGER, " +
                AnnaTrueFalseContract.QuestionTable.COLUMN_QUESTION + " TEXT, " +
                AnnaTrueFalseContract.QuestionTable.COLUMN_ANSWER + " TEXT" +
                ")";

        final String SQL_CREATE_NUM_QUESTIONS_TABLE = "CREATE TABLE " +
                AnnaNumericContract.QuestionTable.TABLE_NAME + " ( " +
                AnnaNumericContract.QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AnnaNumericContract.QuestionTable.COLUMN_QUESTION_TYPE + " INTEGER, " +
                AnnaNumericContract.QuestionTable.COLUMN_QUESTION + " TEXT, " +
                AnnaNumericContract.QuestionTable.COLUMN_ANSWER + " TEXT, " +
                AnnaNumericContract.QuestionTable.COLUMN_LAMBDA + " TEXT" +
                ")";



        db.execSQL(SQL_CREATE_MULT_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_TF_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_NUM_QUESTIONS_TABLE);

        fillQuestionsTable();

    }

    /*
    * method importXML is called in Quiz Main page in toolBar
     */

    public boolean importXML()
    {

        QuestionFromXML async = new QuestionFromXML();
        db = getReadableDatabase();
        urlString = "http://torunski.ca/CST2335/QuizInstance.xml";
        async.execute();

        return true;
    }

    /*
    * dafault questions to check if everything works
     */
    private void fillQuestionsTable() {
        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(1, "Sample Question", "A", "B", "C", "D", "1");
        addMultQuestion(q1);
        TrueFalseQuestion q2 = new TrueFalseQuestion(2, "Sample Question TF", "1");
        addTFQuestion(q2);
        NumericQuestion q3 = new NumericQuestion(3, "Sample Question Num", "1.05", "2");
        addNumQuestion(q3);

    }

    /*
    * onUpgrade drops tables and creates new
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + AnnaMultChoiceContract.QuestionTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AnnaTrueFalseContract.QuestionTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AnnaNumericContract.QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    /*
    * method to ad Multi choice question
     */

    private void addMultQuestion(MultipleChoiceQuestion question) {
        ContentValues cv = new ContentValues();
        cv.put (AnnaNumericContract.QuestionTable.COLUMN_QUESTION_TYPE, question.getQuestionType());
        cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION4, question.getOption4());
        cv.put(AnnaMultChoiceContract.QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(AnnaMultChoiceContract.QuestionTable.TABLE_NAME, null, cv);
    }

     /*
    * method to add True False question
     */

    private void addTFQuestion(TrueFalseQuestion question) {
        ContentValues cv = new ContentValues();
        cv.put (AnnaNumericContract.QuestionTable.COLUMN_QUESTION_TYPE, question.getQuestionType());
        cv.put(AnnaTrueFalseContract.QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(AnnaTrueFalseContract.QuestionTable.COLUMN_ANSWER, question.getAnswer());
        db.insert(AnnaTrueFalseContract.QuestionTable.TABLE_NAME, null, cv);
    }

    /*
    * method to add Numeric question
     */

    private void addNumQuestion(NumericQuestion question) {
        ContentValues cv = new ContentValues();
        cv.put (AnnaNumericContract.QuestionTable.COLUMN_QUESTION_TYPE, question.getQuestionType());
        cv.put(AnnaNumericContract.QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(AnnaNumericContract.QuestionTable.COLUMN_ANSWER, question.getAnswer());
        cv.put(AnnaNumericContract.QuestionTable.COLUMN_LAMBDA, question.getLambda());
        db.insert(AnnaNumericContract.QuestionTable.TABLE_NAME, null, cv);
    }

    /*
    * method to get all Multi Choice questions
     */

    public ArrayList<MultipleChoiceQuestion> getAllMultQuestions() {
        ArrayList<MultipleChoiceQuestion> questionList = new ArrayList<>();
        int counter = 0;
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + AnnaMultChoiceContract.QuestionTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                MultipleChoiceQuestion question = new MultipleChoiceQuestion();

                question.setQuestType(c.getInt(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION_TYPE)));
                question.setQuestion(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_ANSWER_NR)));
                questionList.add(question);

            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    /*
    * method to get all True False questions
     */

    public ArrayList<TrueFalseQuestion> getAllTFQuestions() {
        ArrayList<TrueFalseQuestion> questionList = new ArrayList<>();
        int counter = 0;
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + AnnaTrueFalseContract.QuestionTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                TrueFalseQuestion question = new TrueFalseQuestion();

                question.setQuestType(c.getInt(c.getColumnIndex(AnnaTrueFalseContract.QuestionTable.COLUMN_QUESTION_TYPE)));
                question.setQuestion(c.getString(c.getColumnIndex(AnnaTrueFalseContract.QuestionTable.COLUMN_QUESTION)));
                question.setAnswer(c.getString(c.getColumnIndex(AnnaTrueFalseContract.QuestionTable.COLUMN_ANSWER)));
                questionList.add(question);

            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

     /*
    * method to get all Numeric questions
     */

    public ArrayList<NumericQuestion> getAllNumQuestions() {
        ArrayList<NumericQuestion> questionList = new ArrayList<>();
        int counter = 0;
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + AnnaNumericContract.QuestionTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                NumericQuestion question = new NumericQuestion();

                question.setQuestType(c.getInt(c.getColumnIndex(AnnaNumericContract.QuestionTable.COLUMN_QUESTION_TYPE)));
                question.setQuestion(c.getString(c.getColumnIndex(AnnaNumericContract.QuestionTable.COLUMN_QUESTION)));
                question.setAnswer(c.getString(c.getColumnIndex(AnnaNumericContract.QuestionTable.COLUMN_ANSWER)));
                question.setAnswer(c.getString(c.getColumnIndex(AnnaNumericContract.QuestionTable.COLUMN_LAMBDA)));
                questionList.add(question);

            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }


/*
* inner class to connect to the XML page and parse information into tables
 */

    public class QuestionFromXML extends AsyncTask<String, Integer, String> {
//        The class should have 4 string variables for the wind speed, min, max, and current temperature.
//        There should also be a Bitmap variable to store the picture for the current weather.


        String result = "None";

        protected String doInBackground(String... args) {
            Log.i(ACTIVITY_NAME, "In doInBackground");

            URL url;
            HttpURLConnection conn = null;
            InputStream inputStream = null;

            try {
                url = new URL(urlString);

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                inputStream = conn.getInputStream();
            } catch (MalformedURLException e) {
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);
                ArrayList<String> answers = new ArrayList<>();
                type = new ArrayList<>();
                MultipleChoiceQuestion mq;
                TrueFalseQuestion tfq;
                NumericQuestion nq;

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG) {

                        String tagName = parser.getName();

                        if (tagName.equals("MultipleChoiceQuestion")) {
                            answers.clear();
                            questType = 1;
                            question = parser.getAttributeValue(null, "question");

                            answer = parser.getAttributeValue(null, "correct");
                        } else if (tagName.equals("Answer")) {
                            String ans = parser.nextText();

                            answers.add(ans);

                        }
                    } else if (parser.getEventType() == XmlPullParser.END_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("MultipleChoiceQuestion")) {

                            mq = new MultipleChoiceQuestion(questType, question, answers.get(0), answers.get(1), answers.get(2), answers.get(3), answer);
                            addMultQuestion(mq);
//                            type.add((answers.size()-1), questType);
                        }

                    }

                    if(parser.getEventType()==XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("TrueFalseQuestion")) {
                            questType = 2;
                            question = parser.getAttributeValue(null, "question");
                            answerTF = parser.getAttributeValue(null, "answer");
                        }
                    }
                    else if (parser.getEventType() == XmlPullParser.END_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("TrueFalseQuestion")) {

                            tfq = new TrueFalseQuestion(questType, question, answerTF);
                            addTFQuestion(tfq);
//                            type.add((answers.size()-1), questType);
                        }

                    }

                    if (parser.getEventType() == XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("NumericQuestion")) {
                            questType = 3;
                            question = parser.getAttributeValue(null, "question");
                            answerNum = parser.getAttributeValue(null, "answer");
                            lambda = parser.getAttributeValue(null, "accuracy");
                        }
                    }
                    else if (parser.getEventType() == XmlPullParser.END_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("NumericQuestion")) {

                            nq = new NumericQuestion(questType, question, answerNum, lambda);
                            addNumQuestion(nq);
//                            type.add((answers.size()-1), questType);
                        }



                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
            }

            //temporary return
            return result;

        }
    }

    /*
    * method to search the MC question in the DV by questionText
     */

    public MultipleChoiceQuestion findMCQuestionByTypeAndText(String questTextToFind) {
        MultipleChoiceQuestion multQuestFound = new MultipleChoiceQuestion();
        int counter = 0;
        db = getReadableDatabase();


//        Cursor c = db.rawQuery("SELECT * FROM " + AnnaMultChoiceContract.QuestionTable.TABLE_NAME + " WHERE " + AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION + " = '?' ", new String[] {questTextToFind});

  Cursor c = db.query(false, AnnaMultChoiceContract.QuestionTable.TABLE_NAME, new String[]{"questionType", "question", "option1", "option2", "option3", "option4", "answer_nr"}, "question like ?", new String[]{questTextToFind}, null, null, null, null, null);

            Log.i("Query", c.toString());

            c.moveToFirst();

            multQuestFound.setQuestType(c.getInt(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION_TYPE)));
            multQuestFound.setQuestion(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_QUESTION)));
            multQuestFound.setOption1(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION1)));
            multQuestFound.setOption2(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION2)));
            multQuestFound.setOption3(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION3)));
            multQuestFound.setOption4(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_OPTION4)));
            multQuestFound.setAnswerNr(c.getString(c.getColumnIndex(AnnaMultChoiceContract.QuestionTable.COLUMN_ANSWER_NR)));


            c.close();


        Log.i("QuestionType", String.valueOf(multQuestFound.getQuestionType()));
        Log.i("Question", String.valueOf(multQuestFound.getQuestion()));
        Log.i("Option1", String.valueOf(multQuestFound.getOption1()));
        Log.i("Option1", String.valueOf(multQuestFound.getOption2()));
        Log.i("Option1", String.valueOf(multQuestFound.getOption3()));
        Log.i("Option1", String.valueOf(multQuestFound.getOption4()));
        Log.i("Answer", String.valueOf(multQuestFound.getAnswerNr()));

        return multQuestFound;
    }

     /*
    * method to search the TF question in the DV by questionText
     */

    public TrueFalseQuestion findTFQuestionByTypeAndText(String questTF) {
        TrueFalseQuestion tfQuestFound = new TrueFalseQuestion();
        int counter = 0;
        db = getReadableDatabase();

        Cursor c = db.query(false, AnnaTrueFalseContract.QuestionTable.TABLE_NAME, new String[]{"questionType", "question", "answer_nr"}, "question like ?", new String[]{questTF}, null, null, null, null, null);

        c.moveToFirst();

        tfQuestFound.setQuestType(c.getInt(c.getColumnIndex(AnnaTrueFalseContract.QuestionTable.COLUMN_QUESTION_TYPE)));
        tfQuestFound.setQuestion(c.getString(c.getColumnIndex(AnnaTrueFalseContract.QuestionTable.COLUMN_QUESTION)));
        tfQuestFound.setAnswer(c.getString(c.getColumnIndex(AnnaTrueFalseContract.QuestionTable.COLUMN_ANSWER)));


        c.close();


        Log.i("QuestionType", String.valueOf(tfQuestFound.getQuestionType()));
        Log.i("Question", String.valueOf(tfQuestFound.getQuestion()));
        Log.i("Answer", String.valueOf(tfQuestFound.getAnswer()));

        return tfQuestFound;
    }

    /*
    * method to search the Num question in the DV by questionText
     */

    public NumericQuestion findNumQuestionByTypeAndText(String questNum) {
        NumericQuestion numQuestFound = new NumericQuestion();
        int counter = 0;
        db = getReadableDatabase();

        Cursor c = db.query(false, AnnaNumericContract.QuestionTable.TABLE_NAME, new String[]{"questionType", "question", "answer_nr", "lambda"}, "question like ?", new String[]{questNum}, null, null, null, null, null);

        c.moveToFirst();

        numQuestFound.setQuestType(c.getInt(c.getColumnIndex(AnnaNumericContract.QuestionTable.COLUMN_QUESTION_TYPE)));
        numQuestFound.setQuestion(c.getString(c.getColumnIndex(AnnaNumericContract.QuestionTable.COLUMN_QUESTION)));
        numQuestFound.setAnswer(c.getString(c.getColumnIndex(AnnaNumericContract.QuestionTable.COLUMN_ANSWER)));
        numQuestFound.setLambda(c.getString(c.getColumnIndex(AnnaNumericContract.QuestionTable.COLUMN_LAMBDA)));


        c.close();

        Log.i("QuestionType", String.valueOf(numQuestFound.getQuestionType()));
        Log.i("Question", String.valueOf(numQuestFound.getQuestion()));
        Log.i("Answer", String.valueOf(numQuestFound.getAnswer()));
        Log.i("Lambda", String.valueOf(numQuestFound.getLambda()));

        return numQuestFound;
    }





}
