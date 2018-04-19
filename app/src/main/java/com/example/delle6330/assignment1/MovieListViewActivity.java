package com.example.delle6330.assignment1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Svetlana Netchaeva on 2018-04-10.
 * Class represents a fragment
 */
public class  MovieListViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_fragment);

        Bundle infoPassed = getIntent().getExtras();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MovieFragment phrasesFragment = new MovieFragment();
        phrasesFragment.setArguments(infoPassed);
        fragmentTransaction.replace(R.id.fragment, phrasesFragment).commit();
    }
}