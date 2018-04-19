package com.example.delle6330.assignment1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Svetlana Netchaeva on 2018-04-10.
 * This class is responsible for adding movie to the list
 */

public class AddMovie extends Activity {

    /**
     * editText fields that will be send with data
     */
    EditText title;
    EditText actors;
    EditText length;
    EditText description;
    EditText rating;
    EditText genre;
    EditText url;
    Button addButton;

    /**
     * method construct an intent and sends data
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        addButton = (Button)findViewById(R.id.button_add);
        title = findViewById(R.id.editTitle);
        actors = findViewById(R.id.editActors);
        length = findViewById(R.id.editLength);
        description = findViewById(R.id.editDescription);
        rating = findViewById(R.id.editRating);
        genre = findViewById(R.id.editGenre);
        url = findViewById(R.id.editUrl);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Double.parseDouble(rating.getText().toString()) > 4.0) {
                    Toast.makeText(AddMovie.this, "Rating must be out of 4 starts", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("actors", actors.getText().toString());
                    intent.putExtra("length", length.getText().toString());
                    intent.putExtra("description", description.getText().toString());
                    intent.putExtra("rating", rating.getText().toString());
                    intent.putExtra("genre", genre.getText().toString());
                    intent.putExtra("url", url.getText().toString());
                    setResult( 10 ,intent);
                    finish();
                }
            }});
    }
}
