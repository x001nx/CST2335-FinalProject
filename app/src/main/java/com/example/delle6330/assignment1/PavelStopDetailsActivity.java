        package com.example.delle6330.assignment1;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.util.Xml;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.xmlpull.v1.XmlPullParser;

        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;

public class PavelStopDetailsActivity extends Activity {
    TextView stopNumber;
    TextView stopDescription;
    ListView listView;
    String number;
    ArrayList<String> routeNumberAL;
    ArrayList<String> routeHeadingAL;
    ArrayList<String> routeDirectionAL;
    Context ctx;
    private RoutesAdapter routesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        routeNumberAL = new ArrayList<>();
        routeHeadingAL = new ArrayList<>();
        routeDirectionAL = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pavel_stop_details);
        ctx = this;
        stopNumber = findViewById(R.id.stopDetailsNumber);
        stopDescription = findViewById(R.id.stopDetailsDescription);
        listView = findViewById(R.id.stopDetailsListView);
        routesAdapter = new RoutesAdapter(this);
        listView.setAdapter(routesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PavelStopDetailsActivity.this);
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Stop clicked") //Add a dialog message to strings.xml

                        .setTitle(stopNumber.getText())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("OK", "OK");
                                finish();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                    Log.i("CANCEL", "CANCEL");
                            }
                        })
                        .show();

            }
        });


        Intent intent = getIntent();
        number = intent.getStringExtra("number");
        stopNumber.setText(number);
        String description = intent.getStringExtra("description");
        stopDescription.setText(description);
        QueryOC queryOC = new QueryOC();
        queryOC.execute();
        routesAdapter.notifyDataSetChanged();

    }

    /**
     * RoutesAdapter
     */
    public class RoutesAdapter extends ArrayAdapter<String> {
        LayoutInflater inflater = PavelStopDetailsActivity.this.getLayoutInflater();
        /**
         * New ListView element layout
         */
        View result;

        /**
         * Default constructor
         * @param ctx Context
         */
        private RoutesAdapter(Context ctx) {
            super(ctx, 0);
            Log.i("****** RoutesAdapter", "");
        }

        /**
         * Lenght of stationsArray
         * @return int
         */
        public int getCount() {
            return routeNumberAL.size();
        }

        /**
         * Get one item from stationsArray
         * @param position int
         * @return string
         */
        private String getRouteNumber(int position) {
            return routeNumberAL.get(position);
        }

        private String getRouteDirection(int position) {
            return routeDirectionAL.get(position);
        }

        private String getRouteHeader(int position) {
            return routeHeadingAL.get(position);
        }
        /**
         * Create view
         * @param position int
         * @param convertView ciew
         * @param parent parent
         * @return view
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            //CREATE INFLATER
            //SET LAYOUT USING INFLATOR - IF POSSITION INCOMING OUTGOING
            for(String s: routeNumberAL){
                Log.i("****** getView", s);
                result = inflater.inflate(R.layout.route_layout, null);
            }
            //FIND TEXTVIEW IN RESULT
            TextView routeNumber = result.findViewById(R.id.routeNumber);
            TextView routeHeader = result.findViewById(R.id.routeHeader);
            TextView routeDirection = result.findViewById(R.id.routeDirection);
            //SET TEXT IN RESULT
            routeNumber.setText(getRouteNumber(position)); // get the string at position
            Log.i("****** getView", getRouteNumber(position));
            routeHeader.setText(getRouteHeader(position));
            Log.i("****** getView", getRouteHeader(position));
            routeDirection.setText(getRouteDirection(position));
            Log.i("****** getView", getRouteDirection(position));
            return result;
        }

    }

    public class QueryOC extends AsyncTask<String, Integer, String> {
        String osQuery = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=c88f040c&&apiKey=fe500fc8da4e4f5e823b913c388cc2f1&stopNo=";
        String errorCode;
        @Override
        protected String doInBackground(String... strings){
            URL url;
            HttpURLConnection conn = null;
            InputStream in = null;

            //HTTPUrlConnection - START
            try {
                url = new URL(osQuery + number);
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
                        if(parser.getName().equalsIgnoreCase("RouteNo")){
                            routeNumberAL.add(parser.nextText());
                        }
                        else if(parser.getName().equalsIgnoreCase("RouteHeading")){
                            routeHeadingAL.add(parser.nextText());
                        }
                        else if(parser.getName().equalsIgnoreCase("Direction")){
                            routeDirectionAL.add(parser.nextText());
                        }
                        if (parser.getName().equalsIgnoreCase("Error")){
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
            if(!errorCode.isEmpty()) {
                CharSequence text = "Invalid Stop Number";// "Switch is Off"
                int duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
                Toast toast = Toast.makeText(ctx, text, duration);//this is the ListActivity
                toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                toast.show(); //display your message box
                return;
            }
            else {
                Log.i("******RouteNo", routeNumberAL.toString());
                Log.i("******RouteHeading", routeHeadingAL.toString());
                Log.i("******RouteDirection", routeDirectionAL.toString());
                routesAdapter.notifyDataSetChanged();
            }

        }

        protected void onProgressUpdate(Integer... progressValue) {
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(progressValue[0]);
        }
    }
}
