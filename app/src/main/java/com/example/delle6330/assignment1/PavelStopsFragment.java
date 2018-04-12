package com.example.delle6330.assignment1;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class PavelStopsFragment extends Fragment {
    Button deleteButton;
    TextView textViewstopNumber;
    TextView textViewstopDescription;
    String stopNumber;
    String stopDescription;
    boolean isLandscape;
    View fragmentView;
    ArrayList<String[]> routeDetails;
    ArrayList<String> routeNumberAL;
    ArrayList<String> routeHeadingAL;
    ArrayList<String> routeDirectionAL;
    ListView listView;
    RoutesAdapter routesAdapter;

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);

        Bundle infoToPass = getArguments();
        stopNumber = (String) infoToPass.get("number");
        stopDescription = (String)infoToPass.get("description");
        isLandscape = (Boolean) infoToPass.get("isLandscape");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.activity_pavel_stop_details, null);
        textViewstopNumber = fragmentView.findViewById(R.id.stopDetailsNumber);
        textViewstopNumber.setText(stopNumber);

        textViewstopDescription = fragmentView.findViewById(R.id.stopDetailsDescription);
        textViewstopDescription.setText(stopDescription);

        deleteButton = fragmentView.findViewById(R.id.pavel_delete_stop);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLandscape) {
                    ((PavelStartActivity) getActivity()).deleteStop(stopNumber);
                }
                else {
                    Intent stops = new Intent(getActivity(), PavelStartActivity.class);
                    stops.putExtra("stopNumber", stopNumber);
                    getActivity().setResult(50, stops);
                    getActivity().finish();
                }
            }
        });

        routeDetails = new ArrayList<>();
        routeNumberAL = new ArrayList<>();
        routeHeadingAL = new ArrayList<>();
        routeDirectionAL = new ArrayList<>();

        QueryOC queryOC = new QueryOC();
        queryOC.execute();

//        for (int i=0; i<routeNumberAL.size(); i++) {
//            routeDetails.add(new String[]{routeNumberAL.get(i), routeHeadingAL.get(i), routeDirectionAL.get(i)});
//            Log.i("****** routeDetails", Arrays.toString(routeDetails.get(i)));
//        }
        routesAdapter = new RoutesAdapter(getActivity());
        listView = (ListView) fragmentView.findViewById(R.id.stopDetailsListView);
        Log.i("*******", String.valueOf(listView));
        listView.setAdapter(routesAdapter);

        routesAdapter.notifyDataSetChanged();
        return fragmentView;
    }

    public class RoutesAdapter extends ArrayAdapter<String> {
        LayoutInflater inflater;

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
            inflater = getActivity().getLayoutInflater();
            Log.i("******RoutesAdater","START");
        }

        /**
         * Lenght of stationsArray
         * @return int
         */
        public int getCount() {
            Log.i("******RoutesAdater","getCount " + routeNumberAL.size());

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
   public class QueryOC extends AsyncTask<String, Integer, String>  {
       String osQuery = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=c88f040c&&apiKey=fe500fc8da4e4f5e823b913c388cc2f1&stopNo=";
       String errorCode;
        String[] route = new String[3];
       @Override
       protected String doInBackground(String... strings){
           URL url;
           HttpURLConnection conn = null;
           InputStream in = null;

           //HTTPUrlConnection - START
           try {
               if (stopNumber.isEmpty()) {
//                   stopNumber="aa";
               }
               url = new URL(osQuery + stopNumber);
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
//                           route[0] = parser.nextText();
                       }
                       else if(parser.getName().equalsIgnoreCase("RouteHeading")){
                           routeHeadingAL.add(parser.nextText());
//                           route[1] = parser.nextText();
                       }
                       else if(parser.getName().equalsIgnoreCase("Direction")){
                           routeDirectionAL.add(parser.nextText());
//                           route[2] = parser.nextText();
                       }
                       if (parser.getName().equalsIgnoreCase("Error")){
                           errorCode = parser.nextText();
                           Log.i("****** Error", errorCode);
                       }
                       routeDetails.add(route);
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
               Toast toast = Toast.makeText(getActivity(), text, duration);//this is the ListActivity
               toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
               toast.show(); //display your message box
               return;
           }
           else {
               routesAdapter.notifyDataSetChanged();
//               Log.i("******RouteNo", routeNumberAL.toString());
//               Log.i("******RouteHeading", routeHeadingAL.toString());
//               Log.i("******RouteDirection", routeDirectionAL.toString());

//               routesAdapter.notifyDataSetChanged();
           }

       }

       protected void onProgressUpdate(Integer... progressValue) {
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(progressValue[0]);

       }
   }
}
