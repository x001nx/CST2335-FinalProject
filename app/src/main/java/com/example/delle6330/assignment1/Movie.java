package com.example.delle6330.assignment1;

import android.graphics.Bitmap;

/**
 * Created by Svetlana Netchaeva on 2018-04-09.
 * In this class contains the fields that are needed for description of a movie
 */

public class Movie {

    /**
     * Fields that are needed for description of a movie
     */
    private long id;
    private String title;
    private String actors;
    private String length;
    private String description;
    private String rating;
    private String genre;
    private String url;
    private Bitmap picture;

    /**
     * Constructor that initializes the fields
     * @param title - movie title
     * @param actors - movie actors
     * @param length - movie length
     * @param description - movie description
     * @param rating - movie rating
     * @param genre - movie genre
     * @param url - movie url
     * @param id - movie id
     */
    public Movie(String title, String actors, String length,
                 String description,String rating, String genre, String url, long id  ){
        this.title = title;
        this.actors = actors;
        this.length = length;
        this.description = description;
        this.rating = rating;
        this.genre = genre;
        this.url = url;
        this.id = id;
    }

    /**
     * getters that return value of particular field
     * @return
     */
    public String getTitle(){
        return title;
    }
    public String getActors(){
        return actors;
    }
    public String getLength(){
        return length;
    }
    public String getDescription(){
        return description;
    }
    public String getRating(){
        return rating;
    }
    public String getGenre(){
        return genre;
    }
    public String getUrl(){
        return url;
    }
    public long getId(){
        return id;
    }
    public Bitmap getPic() {
        return picture;
    }
}