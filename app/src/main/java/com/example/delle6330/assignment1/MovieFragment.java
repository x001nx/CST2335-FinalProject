package com.example.delle6330.assignment1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.Fragment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Svetlana Netchaeva on 2018-04-10.
 * Class represents a behavior of user interface
 */

public class MovieFragment extends Fragment {

    /**
     * fields are needed to describe a movie
     */
    String title; String actors; String length;
    String description; String rating; String genre;
    String url; Bitmap picture;

    EditText titleView;
    EditText actorsView;
    EditText lengthView;
    EditText descriptionView;
    EditText ratingView;
    EditText genreView;
    EditText urlView;

    long id;
    Context context = null;

    /**
     * method deletes movie
     */
   public void onDeleteMessage() {
            MovieActivity activity = (MovieActivity) context;
            activity.deleteMovie(id);
    }

    /**
     * metod called once the fragment is associated with its activity
     * @param c
     */
    public void onAttach(Context c) {
        super.onAttach(c);
        this.context = c;
    }

    public void onCreate(Bundle b) {
        super.onCreate(b);
        Bundle data = getArguments();
        title = data.getString("title");
        actors = data.getString("actors");
        length = data.getString("length");
        description = data.getString("description");
        rating = data.getString("rating");
        genre = data.getString("genre");
        url = data.getString("url");
        id = data.getLong("ID");
    }

    /**
     * access the view hierarchy
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View gui = inflater.inflate(R.layout.activity_movie_list_view, null);

        TextView vTitle = (TextView) gui.findViewById(R.id.editTitle);
        vTitle.setText(title);
        TextView vActors = (TextView) gui.findViewById(R.id.editActors);
        vActors.setText(actors);
        TextView vLength = (TextView) gui.findViewById(R.id.editLength);
        vLength.setText(length);
        TextView vDescription = (TextView) gui.findViewById(R.id.editDescription);
        vDescription.setText(description);
        TextView vRating = (TextView) gui.findViewById(R.id.editRating);
        vRating.setText(rating);
        TextView vGenre = (TextView) gui.findViewById(R.id.editGenre);
        vGenre.setText(genre);
        TextView vUrl = (TextView) gui.findViewById(R.id.editUrl);
        vUrl.setText(url);

        ImageView imageView = gui.findViewById(R.id.image);
        String fileName = url.substring(url.lastIndexOf(':') + 1);
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(fileName + ".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        picture = BitmapFactory.decodeStream(fis);
        imageView.setImageBitmap(picture);

        Button delete = (Button) gui.findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("ID", id);
                getActivity().setResult( 20,intent);
                getActivity().finish();
            }
        });

        titleView = gui.findViewById(R.id.editTitle);
        actorsView = gui.findViewById(R.id.editActors);
        lengthView = gui.findViewById(R.id.editLength);
        descriptionView = gui.findViewById(R.id.editDescription);
        ratingView = gui.findViewById(R.id.editRating);
        genreView = gui.findViewById(R.id.editGenre);
        urlView = gui.findViewById(R.id.editUrl);

        Button update = (Button) gui.findViewById(R.id.updateButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("ID", id);
                intent.putExtra("title", titleView.getText().toString());
                intent.putExtra("actors", actorsView.getText().toString());
                intent.putExtra("length", lengthView.getText().toString());
                intent.putExtra("description", descriptionView.getText().toString());
                intent.putExtra("rating", ratingView.getText().toString());
                intent.putExtra("genre", genreView.getText().toString());
                intent.putExtra("url", urlView.getText().toString());
                getActivity().setResult( 30,intent);
                getActivity().finish();
            }
        });
        return gui;
    }

}
