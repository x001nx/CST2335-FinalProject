package com.example.delle6330.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.List;

/**
 * Created by ansht on 2018-04-10.
 */

public class MultChoicDBHelper extends SQLiteOpenHelper {
    protected static final String ACTIVITY_NAME = "MultChoicDBHelper";
    private static final String DATABASE_NAME = "MultChoiceDB.db";
    private static final int DATABASE_VERSION = 7;
    private String urlString;
    SQLiteDatabase db;


    private int questType;
    private String question;
//    private String multAnsw1;
//    private String multAnsw2;
//    private String multAnsw3;
//    private String multAnsw4;
    private String answer;
    ArrayList<Integer> type;

    public MultChoicDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                MultChoiceContract.QuestionTable.TABLE_NAME + " ( " +
                MultChoiceContract.QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MultChoiceContract.QuestionTable.COLUMN_QUESTION_TYPE + " INTEGER, " +
                MultChoiceContract.QuestionTable.COLUMN_QUESTION + " TEXT, " +
                MultChoiceContract.QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                MultChoiceContract.QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                MultChoiceContract.QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                MultChoiceContract.QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                MultChoiceContract.QuestionTable.COLUMN_ANSWER_NR + " TEXT" +
                ")";


        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    public void importXML()
    {
        QuestionFromXML async = new QuestionFromXML();
        db = getReadableDatabase();
        urlString = "http://torunski.ca/CST2335/QuizInstance.xml";
        async.execute();

    }
    private void fillQuestionsTable() {
        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(1, "A is correct", "A", "B", "C", "D", "1");
        addQuestion(q1);
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion(2, "A is correct", "True", "False", null, null, "True");
        addQuestion(q2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MultChoiceContract.QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    private void addQuestion(MultipleChoiceQuestion question) {
        ContentValues cv = new ContentValues();
        cv.put(MultChoiceContract.QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(MultChoiceContract.QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(MultChoiceContract.QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(MultChoiceContract.QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(MultChoiceContract.QuestionTable.COLUMN_OPTION4, question.getOption4());
        cv.put(MultChoiceContract.QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(MultChoiceContract.QuestionTable.TABLE_NAME, null, cv);
    }

    public ArrayList<MultipleChoiceQuestion> getAllQuestions() {
        ArrayList<MultipleChoiceQuestion> questionList = new ArrayList<>();
        int counter = 0;
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + MultChoiceContract.QuestionTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                MultipleChoiceQuestion question = new MultipleChoiceQuestion();

                    question.setQuestType(c.getInt(c.getColumnIndex(MultChoiceContract.QuestionTable.COLUMN_QUESTION_TYPE)));
                    question.setQuestion(c.getString(c.getColumnIndex(MultChoiceContract.QuestionTable.COLUMN_QUESTION)));
                    question.setOption1(c.getString(c.getColumnIndex(MultChoiceContract.QuestionTable.COLUMN_OPTION1)));
                    question.setOption2(c.getString(c.getColumnIndex(MultChoiceContract.QuestionTable.COLUMN_OPTION2)));
                    question.setOption3(c.getString(c.getColumnIndex(MultChoiceContract.QuestionTable.COLUMN_OPTION3)));
                    question.setOption4(c.getString(c.getColumnIndex(MultChoiceContract.QuestionTable.COLUMN_OPTION4)));
                    question.setAnswerNr(c.getString(c.getColumnIndex(MultChoiceContract.QuestionTable.COLUMN_ANSWER_NR)));
                    questionList.add(question);

            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }



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
                MultipleChoiceQuestion q;

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

                            q = new MultipleChoiceQuestion(questType, question, answers.get(0), answers.get(1), answers.get(2), answers.get(3), answer);
                            addQuestion(q);
                            type.add((answers.size()-1), questType);
                        }

                    }

                    if(parser.getEventType()==XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("NumericQuestion")) {
                        questType = 3;
                            question = parser.getAttributeValue(null, "question");
                            answer = parser.getAttributeValue(null, "answer");
                        }
                    }
                    else if (parser.getEventType() == XmlPullParser.END_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("NumericQuestion")) {

                            q = new MultipleChoiceQuestion(questType, question, null, null, null, null, answer);
                            addQuestion(q);
                            type.add((answers.size()-1), questType);
                        }



                    }

                    if(parser.getEventType()==XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("TrueFalseQuestion")) {
                            questType = 2;
                            question = parser.getAttributeValue(null, "question");
                            answer = parser.getAttributeValue(null, "answer");
                        }
                    }
                    else if (parser.getEventType() == XmlPullParser.END_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("TrueFalseQuestion")) {
                            answers.clear();
                            answers.add(0, "True");
                            answers.add(1, "False");
                            q = new MultipleChoiceQuestion(questType, question, answers.get(0), answers.get(1), null, null, answer);
                            addQuestion(q);
                            type.add((answers.size()-1), questType);
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

}
