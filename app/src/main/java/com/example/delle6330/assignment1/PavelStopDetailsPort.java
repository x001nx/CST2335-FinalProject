package com.example.delle6330.assignment1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PavelStopDetailsPort extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pavel_stop_details_port);
        //Load fragment to phone:
        Bundle infoToPass = getIntent().getExtras();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        PavelStopsFragment sf  =  new PavelStopsFragment();
        sf.setArguments( infoToPass );
        //replace stops.frame with MessageFragment
        ft.replace( R.id.pj_stops_phone_frame , sf);
        ft.commit();
    }
}
