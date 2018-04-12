package com.example.delle6330.assignment1;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class PavelStartActivity extends AppCompatActivity {
    private Context ctx;
    private ListView lv;
    private EditText et;
    private ArrayList<String[]> stationsArray;
    private OSDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues cv;
    private Cursor cursor;
    private QueryOC qOC;
    private String osQuery;
    private String queryFinal = "";
    private StopsAdapter stopsAdapter;
    private ProgressBar progressBar;
    private Button searchStops;
    private boolean isLandscape;
    private PavelStopsFragment stopsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        osQuery = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=c88f040c&&apiKey=fe500fc8da4e4f5e823b913c388cc2f1&";
        super.onCreate(savedInstanceState);
        final int orientation = getResources().getConfiguration().orientation;
        Log.i("******* ORIENTATION", String.valueOf(orientation));

        if (orientation == 2) {
            isLandscape = true;
        }
        setContentView(R.layout.activity_pavel_start);
        ctx = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.stations_list);
        et = (EditText) findViewById(R.id.station_number);
        searchStops = findViewById(R.id.button_search);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.INVISIBLE);

        stationsArray = new ArrayList<>();
        stopsAdapter = new StopsAdapter(this);
        //DATABASE
        //DatabaseHelper
        dbHelper = new OSDatabaseHelper(this);
        //Get WritableDatabase
        db = dbHelper.getWritableDatabase();
        //ContentView
        cv = new ContentValues();

        //Read from database
        cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME + ";",null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            Log.i("****** Cursor", "Size " + String.valueOf(cursor.getCount()));
//            populateDatabase();
        }

        Log.i("****** Cursor", "Size " + String.valueOf(cursor.getCount()));

        try {
            while(!cursor.isAfterLast()){
                String stationNumber = cursor.getString(cursor.getColumnIndex(OSDatabaseHelper.STATION_NUMBER));
                String stationDescription = cursor.getString(cursor.getColumnIndex(OSDatabaseHelper.STATION_NAME));
                Log.i("****** Read Databse", "Position " + cursor.getPosition() + " Number " + stationNumber);
                Log.i("****** Read Databse", "Position " + cursor.getPosition() + " Number " + stationDescription);
                stationsArray.add(new String[]{stationNumber, stationDescription});

                cursor.moveToNext();
            }
        } catch (Exception e){
            Log.d("Cursor", e.getMessage());
        }

        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchStops();
                    return true;

                }
                return false;
            }
        });

        searchStops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchStops();
            }
        });

        lv.setAdapter(stopsAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                TextView tvStopNumber = view.findViewById(R.id.stopNumber);
                TextView tvStopDescr = view.findViewById(R.id.stopDescription);
//                Log.i("****** OnItemClick", String.valueOf(tvStopNumber.getText()  + " " + tvStopDescr.getText()));
//                Intent intent = new Intent(PavelStartActivity.this, PavelStopDetailsActivity.class);
//                intent.putExtra("number", tvStopNumber.getText());
//                intent.putExtra("description", tvStopDescr.getText());
//                startActivity(intent);


                //Store bundle info
                Bundle infoToPass = new Bundle();
                infoToPass.putString("number", String.valueOf(tvStopNumber.getText()));
                infoToPass.putString("description" , String.valueOf(tvStopDescr.getText()));
                infoToPass.putBoolean("isLandscape", isLandscape);

                if (isLandscape){//for a tablet or landscape
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    stopsFragment  =  new PavelStopsFragment();
                    stopsFragment.setArguments( infoToPass );
                    //Replace item with id = frame with class messageFragment - is already loaded because its a tablet or landscape
                    ft.replace( R.id.pj_stops_land_frame, stopsFragment);
                    ft.commit();

                }
                else {//create new intent with MessageDetails.
                    Intent phoneIntent = new Intent (PavelStartActivity.this, PavelStopDetailsPort.class);
                    phoneIntent.putExtras(infoToPass);
                    startActivityForResult(phoneIntent, 50);
                }
            }
        });
        stopsAdapter.notifyDataSetChanged();

    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbarmenu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        Log.i("****** Lab8 Id", String.valueOf(mi.getItemId()));

        switch (id) {
            case R.id.pjhelp:
                customDialog();
                break;
            default:
                Log.i("****** Final Project", "Something else");
                break;
        }
        return true;
    }

    public void customDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pavel_help_dialog, null);
        builder.setView(view);
        builder.setPositiveButton(R.string.pj_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i("******* Lab8 Dialog", String.valueOf(et.getText()));
                et.getText();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Starts AsyncTask to query OCTranspo database with value from EditText
     */
    public void searchStops(){
        queryFinal+=osQuery+"stopNo="+String.valueOf(et.getText());
        Log.i("****** Search", queryFinal);
        qOC = new QueryOC();
        qOC.execute();
        stopsAdapter.notifyDataSetChanged();
        qOC = null;
        et.setText("");
    }

    public class StopsAdapter extends ArrayAdapter<String>{
        LayoutInflater inflater = PavelStartActivity.this.getLayoutInflater();
        /**
         * New ListView element layout
         */
        View result;

        /**
         * Default constructor
         * @param ctx
         */
        public StopsAdapter(Context ctx) {
            super(ctx, 0);
        }

        /**
         * Lenght of stationsArray
         * @return
         */
        public int getCount() {
            return stationsArray.size();
        }

        /**
         * Get one item from stationsArray
         * @param position
         * @return
         */
        public String getItem(int position) {
            return stationsArray.get(position)[0];
        }

        public String getItemDescription(int position) {
            return stationsArray.get(position)[1];
        }

        /**
         * Create view
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            //CREATE INFLATER
            //SET LAYOUT USING INFLATOR - IF POSSITION INCOMING OUTGOING
            for(String stop[]: stationsArray){
                    result = inflater.inflate(R.layout.stop_layout, null);
            }

            //FIND TEXTVIEW IN RESULT
            TextView stopNumber = (TextView) result.findViewById(R.id.stopNumber);
            TextView stopDescription = result.findViewById(R.id.stopDescription);
            //SET TEXT IN RESULT
            stopNumber.setText(getItem(stationsArray.size()-position-1)); // get the string at position
            stopDescription.setText(getItemDescription(stationsArray.size()-position-1));
            return result;
        }

        public long getId(int position) {
            return position;
        }

    }

    private void populateDatabase(){
        Log.i("****** populateDatabase", "populateDatabase()");
        Integer stationNumber;
        String stationName;
        //readCSV
        try {
            File file = new File("stops.csv");
            Log.i("****** populateDatabase", String.valueOf(file.exists()));
            Scanner sc = new Scanner(file);
            sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] partsOfLine = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                stationNumber = Integer.valueOf(partsOfLine[1]);
                stationName = partsOfLine[2];
                //save to DB
                String query = "INSERT INTO " + dbHelper.TABLE_NAME + " ( " + stationNumber + ", " + stationName+ " );";
                Log.i("populateDatabase", query);
                db.rawQuery(query, null);
            }
        } catch (FileNotFoundException e){
            Log.d("populateDatabase", e.getMessage());
        }
    }

    public class QueryOC extends AsyncTask<String, Integer, String> {
        String stopDescription;
        String stopNumber;
        String errorCode;
        @Override
        protected String doInBackground(String... strings){
            URL url;
            HttpURLConnection conn = null;
            InputStream in = null;

            //HTTPUrlConnection - START
            try {
                url = new URL(queryFinal);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                // Starts the query
                conn.connect();
                //Input Stream from HttpURLConnection
                in = conn.getInputStream();
            } catch (MalformedURLException e) {
            }
            catch (IOException e) {
            }
            //HTTPUrlConnection - END

            //XmlPullParser - START
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                while(parser.next() != XmlPullParser.END_DOCUMENT){
                    if(parser.getEventType() == XmlPullParser.START_TAG){
                        if(parser.getName().equalsIgnoreCase("StopNo")){
                            stopNumber = parser.nextText();
                            Log.i("******StopNo", " StopNo " + stopNumber);
                            publishProgress(50);
                            Thread.sleep(200);

                        }
                        else if (parser.getName().equalsIgnoreCase("StopDescription")){
                            stopDescription = parser.nextText();
                            Log.i("******StopDescription", " StopDescription " + stopDescription);
                            publishProgress(100);
                            Thread.sleep(200);
                        }
                        else if (parser.getName().equalsIgnoreCase("Error")){
                            errorCode = parser.nextText();
                            Log.i("****** Error", errorCode);
                        }
                    }
                }
            } catch (Exception e){
                Log.i("****** Exception XML", e.getMessage());
            }
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            if (errorCode == null){
                Log.i("*****onPostExecute", "null value");
                errorCode = "null value";
            }
            if(!errorCode.isEmpty()) {
                View testView = findViewById(android.R.id.content);
                CharSequence text = "Invalid Stop Number";// "Switch is Off"
                int duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
                Toast toast = Toast.makeText(ctx, text, duration);//this is the ListActivity
                toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                toast.show(); //display your message box
                Snackbar snackbar = Snackbar.make(testView,"Invalid Station Number", Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }
            else {
                for (int i = 0; i < stationsArray.size(); i++) {
                    if (stopNumber.equals(stationsArray.get(i)[0])) {
                        Log.i("****** Equals", stationsArray.get(i)[0] + " = " + stopNumber + " " + "DELETE FROM " + dbHelper.TABLE_NAME + " WHERE STATION_NUMBER = '" + stopNumber + "';");
                        db.execSQL("DELETE FROM " + dbHelper.TABLE_NAME + " WHERE STATION_NUMBER = '" + stopNumber + "';");
                        stationsArray.remove(i);
                    }
                }
                ContentValues cv = new ContentValues();
                cv.put(dbHelper.STATION_NUMBER, stopNumber);
                cv.put(dbHelper.STATION_NAME, stopDescription);
                db.insert(dbHelper.TABLE_NAME, "NullColumnName", cv);
                stationsArray.add(new String[]{stopNumber, stopDescription});
                stopsAdapter.notifyDataSetChanged();
            }

        }

        protected void onProgressUpdate(Integer... progressValue) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progressValue[0]);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50) {
            if (resultCode == 50){
                //update database
                Bundle infoToPass = data.getExtras();
                //*******
                String stopNumber = (String) infoToPass.get("stopNumber");
                deleteStop(stopNumber);
            }
        }
    }

    public void deleteStop(String s) {

        //DATABASE
        //DatabaseHelper
        OSDatabaseHelper dbHelper = new OSDatabaseHelper(ctx);
        //Get WritableDatabase
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //ContentView
        ContentValues cv = new ContentValues();
        //Read from database
        db.delete(dbHelper.TABLE_NAME, dbHelper.STATION_NUMBER + " = " + s, null);
        for (int i=0; i<stationsArray.size(); i++){
            if (stationsArray.get(i)[0].equals(s)){
                stationsArray.remove(i);
            }
        }
        stopsAdapter.notifyDataSetChanged();
        if (isLandscape) {
            getFragmentManager().beginTransaction().remove(stopsFragment);
        }

    }
}
