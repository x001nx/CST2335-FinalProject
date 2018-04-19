package com.example.delle6330.assignment1;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import org.xmlpull.v1.XmlPullParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Svetlana Netchaeva on 2018-04-07.
 * In this class contains ...........
 */
public class MovieActivity  extends AppCompatActivity {


    /**
     * ArrayList that contains the list of movies
     */
    ArrayList<Movie> movies = new ArrayList<Movie>();
    /**
     * fileds that are needed for movie description
     */
    private String title;
    private String actors;
    private String length;
    private String description;
    private String rating;
    private String genre;
    private String url;
    Bitmap picture;

    /**
     * button that changes this activity to AddMovie activity
     */
    Button addButton;
    /**
     * progressbar showing progress
     */
    ProgressBar progressBar;
    /**
     * listView that contains the list
     */
    ListView listView;

    /**
     * movieAdapter provides access to the data items
     */
    MovieAdapter movieAdapter;
    /**
     * database perform common database management tasks
     */
    SQLiteDatabase database;
    /**
     * cursor points to a single row of the result fetched by the query
     */
    Cursor cursor;
    MovieFragment movieFragment;
    private boolean isTablet;
    private boolean fromXml = false;
    /**
     * object that manages database creation
     */
    MovieDatabaseHelper databaseHelper;

    /**
     * fields for rating calculation
     */
    Double highest = 0.0;
    Double lowest = 5.0;
    Double average;

    /**
     * method creates an activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);
        addButton = (Button)findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MovieActivity.this, AddMovie.class), 5);
            }
        });

        movieAdapter = new MovieAdapter(this,  movies);
        databaseHelper = new MovieDatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        cursor = database.query( MovieDatabaseHelper.TABLE_NAME,
                new String[] {MovieDatabaseHelper.KEY_ID,
                        MovieDatabaseHelper.KEY_title,
                        MovieDatabaseHelper.KEY_actors,
                        MovieDatabaseHelper.KEY_length,
                        MovieDatabaseHelper.KEY_description,
                        MovieDatabaseHelper.KEY_rating,
                        MovieDatabaseHelper.KEY_genre,
                        MovieDatabaseHelper.KEY_url },
                null, null,null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast() ) {
            movies.add(new Movie(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_title)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_actors)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_length)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_description)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_rating)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_genre)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_url)),
                    new Long(cursor.getInt(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID)))));
            cursor.moveToNext();
        }

        listView.setAdapter(movieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle infoToPass = new Bundle();
                infoToPass.putString("title", movies.get(position).getTitle());
                infoToPass.putString("actors", movies.get(position).getActors());
                infoToPass.putString("length", movies.get(position).getLength());
                infoToPass.putString("description", movies.get(position).getDescription());
                infoToPass.putString("rating", movies.get(position).getRating());
                infoToPass.putString("genre", movies.get(position).getGenre());
                infoToPass.putString("url", movies.get(position).getUrl());
                infoToPass.putLong("ID", id);

                Intent intent = new Intent(MovieActivity.this, MovieListViewActivity.class);
                intent.putExtras(infoToPass);
                startActivityForResult(intent, 20);
            }
        });
    }

    /**
     * method creates the options menu
     * @param menu
     * @return true
     */
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.movie_toolbar_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch( item.getItemId() ){
            case R.id.svAction_one:
                Intent intent1 = new Intent(MovieActivity.this, MainActivity.class);// Anna's activity
                startActivityForResult(intent1, 50);
                break;
            case R.id.svAction_two:
                Intent intent2 = new Intent(MovieActivity.this, MainActivity.class); //Ksenia's
                startActivityForResult(intent2, 50);
                break;
            case R.id.svAction_three:
                Intent intent3 = new Intent(MovieActivity.this, MainActivity.class); //Pavel's
                startActivityForResult(intent3, 50);
                break;
            case R.id.svAbout:
                showDialog();
                break;
            case R.id.movieXML:
                new MovieQuery().execute();
                break;
            case R.id.summary:
                showStatistics();
                break;
        }
        return true;
    }

    /**
     * method shows the dialog window
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.movieAuthor);
        builder.setMessage(R.string.movieInstructions);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * method shows statistics
     */
    public void showStatistics(){
        int count = 0;
        double average = 0.0;
        for (int i = 0;  i < movies.size(); i++ ) {
            try {
                Double rating = Double.parseDouble(movies.get(i).getRating());
                average += rating;
                if (rating > highest)
                    highest = rating;
                if (rating < lowest)
                    lowest = rating;
                count++;
            } catch (Exception e) {
                continue;
            }
        }
        average = average / ((double)(count));
        String averageFormatted = new DecimalFormat("##.##").format(average);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.movieSummary);
        builder.setMessage(getString(R.string.movieStatistics) + " " + highest +
                getString(R.string.movieLowRating) + " " + lowest + getString(R.string.movieAverage) + " " + averageFormatted)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * method that responsible for getting of operation result
     * @param req
     * @param result
     * @param data
     */
    public void onActivityResult(int req, int result, Intent data) {
        if(result == 10) {
            title = data.getExtras().getString("title");
            actors = data.getExtras().getString("actors");
            length = data.getExtras().getString("length");
            description = data.getExtras().getString("description");
            rating = data.getExtras().getString("rating");
            genre = data.getExtras().getString("genre");
            url = data.getExtras().getString("url");

            ContentValues newData = new ContentValues();
            newData.put(MovieDatabaseHelper.KEY_title, title);
            newData.put(MovieDatabaseHelper.KEY_actors, actors);
            newData.put(MovieDatabaseHelper.KEY_length, length);
            newData.put(MovieDatabaseHelper.KEY_description, description);
            newData.put(MovieDatabaseHelper.KEY_rating, rating);
            newData.put(MovieDatabaseHelper.KEY_genre, genre);
            newData.put(MovieDatabaseHelper.KEY_url, url);

            movies.add(new Movie(title, actors, length, description, rating, genre, url,
                    (database.insert(MovieDatabaseHelper.TABLE_NAME, null, newData))));
            Toast.makeText(MovieActivity.this, R.string.movieAdded, Toast.LENGTH_LONG).show();
        }
        if(result == 20) {
            long id = data.getExtras().getLong("ID");
            deleteMovie(id);
            Toast.makeText(MovieActivity.this, R.string.movieDeleted, Toast.LENGTH_LONG).show();
        }
        if(result == 30){
            updateMovie(data);
            Toast.makeText(MovieActivity.this, R.string.movieUpdated, Toast.LENGTH_LONG).show();
        }
        movieAdapter.notifyDataSetChanged();
    }

    /**
     * method deletes movie from list
     * @param id
     */
    public void deleteMovie(long id) {
        database.delete(MovieDatabaseHelper.TABLE_NAME, "ID is ?", new String[] {Long.toString(id)});
        if(isTablet) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(movieFragment);
            ft.commit();
        }
        cursor = database.query( MovieDatabaseHelper.TABLE_NAME,
                new String[] {MovieDatabaseHelper.KEY_ID,
                        MovieDatabaseHelper.KEY_title,
                        MovieDatabaseHelper.KEY_actors,
                        MovieDatabaseHelper.KEY_length,
                        MovieDatabaseHelper.KEY_description,
                        MovieDatabaseHelper.KEY_rating,
                        MovieDatabaseHelper.KEY_genre,
                        MovieDatabaseHelper.KEY_url},
                null, null,null, null, null, null);
        movies.clear();
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            movies.add(new Movie(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_title)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_actors)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_length)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_description)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_rating)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_genre)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_url)),
                    new Long(cursor.getInt(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID)))));
            cursor.moveToNext();
        }
    }

    /**
     * method updates movie
     * @param data
     */
    public void updateMovie(Intent data) {
        long id = data.getExtras().getLong("ID");
        title = data.getExtras().getString("title");
        actors = data.getExtras().getString("actors");
        length = data.getExtras().getString("length");
        description = data.getExtras().getString("description");
        rating = data.getExtras().getString("rating");
        genre = data.getExtras().getString("genre");
        url = data.getExtras().getString("url");

        ContentValues newData = new ContentValues();
        newData.put(MovieDatabaseHelper.KEY_title, title);
        newData.put(MovieDatabaseHelper.KEY_actors, actors);
        newData.put(MovieDatabaseHelper.KEY_length, length);
        newData.put(MovieDatabaseHelper.KEY_description, description);
        newData.put(MovieDatabaseHelper.KEY_rating, rating);
        newData.put(MovieDatabaseHelper.KEY_genre, genre);
        newData.put(MovieDatabaseHelper.KEY_url, url);

        database.update(MovieDatabaseHelper.TABLE_NAME, newData, "ID="+id, null);
        movies.clear();
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            movies.add(new Movie(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_title)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_actors)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_length)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_description)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_rating)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_genre)),
                    cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_url)),
                    new Long(cursor.getInt(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID)))));
            cursor.moveToNext();
        }
    }

    /**
     * This class provides access to the data items
     */
    private class MovieAdapter extends ArrayAdapter<Movie>{

        /**
         * constructor
         * @param context
         * @param movies
         */
        public MovieAdapter(Activity context, ArrayList<Movie> movies)  {
            super(context, 0, movies);
        }

        public int getCount()
        {
            return movies.size();
        }
        public Movie getItem(int position)
        {
            return movies.get(position);
        }

        /**
         * method returns item id
         * @param position
         * @return
         */
        public long getItemId(int position) {
            long result = getItem(position).getId();
            return result;
        }

        /**
         * method returns the number of types of Views
         * @param position
         * @param convertView
         * @param parent
         * @return list of items
         */
        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            View listItemView = convertView;
            if(listItemView == null){
                listItemView = LayoutInflater.from(getContext()).inflate
                        (R.layout.activity_movie_list, parent, false);
            }

            Movie currentMovie = getItem(position);
            TextView title = listItemView.findViewById(R.id.title);
            title.setText(currentMovie.getTitle());

            ImageView imageView = listItemView.findViewById(R.id.image);
            String url = currentMovie.getUrl();
            String fileName = url.substring(url.lastIndexOf(':') + 1);
            FileInputStream fis = null;
            try {
                fis = openFileInput(fileName + ".png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            picture = BitmapFactory.decodeStream(fis);
            imageView.setImageBitmap(picture);
            return listItemView;
        }
    }

    /**
     * This class is used for reading data
     */
    private class MovieQuery extends AsyncTask<String, Integer, String> {

        /**
         * method downloads database
         * @param args
         * @return
         */
        @Override
        protected String doInBackground(String... args) {
            try {
                boolean isTitle, isActors, isLength, isDescription, isRating, isGenre, isUrl;

                isTitle = isActors = isLength = isDescription = isRating = isGenre = isUrl = false;

                URL url1 = new URL("http://torunski.ca/CST2335/MovieInfo.xml");
                HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                publishProgress(25);
                int nextType = parser.next();
                while ((nextType ) != XmlPullParser.END_DOCUMENT) {
                    switch (nextType) {
                        case XmlPullParser.START_TAG: {
                            String name = parser.getName();
                            if(name == null )continue;
                            if (name.equals("Title")) {
                                isTitle = true;
                            }
                            if (name.equals("Actors")) {
                                isActors = true;
                            }
                            if (name.equals("Length")) {
                                isLength= true;
                            }
                            if (name.equals("Description")) {
                                isDescription = true;
                            }
                            if (name.equals("Rating")) {
                                isRating = true;
                            }
                            if (name.equals("Genre")) {
                                isGenre = true;
                            }
                            if (name.equals("URL")) {
                                url = parser.getAttributeValue(null, "value");
                                String fileName = url.substring(url.lastIndexOf(':') + 1);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName + ".png");
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    picture = BitmapFactory.decodeStream(fis);
                                } else {
                                    picture = getImage(url);
                                    FileOutputStream outputStream = openFileOutput
                                            (fileName + ".png", Context.MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                            }
                        }
                        break;
                        case XmlPullParser.TEXT:
                            String text = parser.getText();
                            if (isTitle)
                                title = text;
                            else if (isActors)
                                actors = text;
                            else if (isLength)
                                length = text;
                            else if (isDescription)
                                description = text;
                            else if (isRating)
                                rating = text;
                            else if (isGenre)
                                genre = text;
                            break;
                        case XmlPullParser.END_TAG: {
                            String name = parser.getName();
                            if (name.equals("Movie")) {
                                ContentValues newData = new ContentValues();
                                newData.put(MovieDatabaseHelper.KEY_title, title);
                                newData.put(MovieDatabaseHelper.KEY_actors, actors);
                                newData.put(MovieDatabaseHelper.KEY_length, length);
                                newData.put(MovieDatabaseHelper.KEY_description, description);
                                newData.put(MovieDatabaseHelper.KEY_rating, rating);
                                newData.put(MovieDatabaseHelper.KEY_genre, genre);
                                newData.put(MovieDatabaseHelper.KEY_url, url);
                                movies.add(new Movie(title, actors, length, description, rating, genre, url,
                                        (database.insert(MovieDatabaseHelper.TABLE_NAME, null, newData))));
                                fromXml = true;
                            }if (name.equals("Title")) {
                                isTitle = false;
                            }if (name.equals("Actors")) {
                                isActors = false;
                            }if (name.equals("Length")) {
                                isLength = false;
                            }if (name.equals("Description")) {
                                isDescription = false;
                            }if (name.equals("Rating")) {
                                isRating = false;
                            }if (name.equals("Genre")) {
                                isGenre = false;
                            }
                        }

                    }
                    publishProgress(50);
                    nextType = parser.next();
                }
                movieAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.d("Excption:", e.getMessage());
            }
            publishProgress(75);
            return "Done";
        }

        /**
         * method shows progressBar
         * @param value
         */
        public void onProgressUpdate(Integer... value) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        /**
         * method runs after onProgressUpdate method
         * @param result
         */
        public void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
        }

        public Bitmap getImage(String urlString) {
            Bitmap downloaded = null;
            try {
                URL url = new URL(urlString);
                publishProgress(100);

                HttpURLConnection connection = null;
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    downloaded = BitmapFactory.decodeStream(connection.getInputStream());
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            } catch (Exception e) {
                return null;
            }
            return downloaded;
        }

        /**
         * method checks the file existance
         * @param fname
         * @return
         */
        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname + ".png");
            if (file.exists())
                Log.i("MovieActivity", "Found the image locally");
            else
                Log.i("MovieActivity", "Need to download the image");
            return file.exists();
        }
    }

}
