package com.example.delle6330.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.os.AsyncTask;


public class MovieActivity extends Activity{

    ListView listView;
    TextView selection;
    EditText editText;
    Button sendButton;
    String [] movies = {"Lady Bird", "Get Out"};
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        //Toast.makeText(getApplicationContext(),"BEST MOVIES", Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(this, "BEST MOVIES",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0,160);
        toast.show();

        selection = (TextView) findViewById(R.id.svEmpty);
        listView = (ListView) findViewById(R.id.svListMovie);
        editText = (EditText) findViewById(R.id.svEditField);
        sendButton = (Button) findViewById(R.id.svAddButton);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, movies);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                // по позиции получаем выбранный элемент
//                String selectedItem = movies[position];
//                // установка текста элемента TextView
//                selection.setText(selectedItem);
                switch(position) {
                    case 0:
                        startActivity(new Intent(MovieActivity.this, MovieListViewActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent( MovieActivity.this, MovieListViewActivity.class  ));
                        break;
                }
            }
        });

    }

    public class QueryMovie extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
          //
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Integer... progressValue) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progressValue[0]);
        }
    }


}

