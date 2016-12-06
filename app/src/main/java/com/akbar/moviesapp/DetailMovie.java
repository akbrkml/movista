package com.akbar.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akbar.moviesapp.Adapter.*;
import com.akbar.moviesapp.model.MovieData;
import com.akbar.moviesapp.model.TrailerData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

public class DetailMovie extends AppCompatActivity {

    private Bundle extras;
    private String title;
    private String backdrop;
    private String poster;
    private String rating;
    private String release_date;
    private String overview;

    private TextView txtTitleMovie;
    private ImageView imageBackdrop;
    private ImageView imagePoster;
    private TextView txtRating;
    private TextView txtYear;
    private TextView txtOverview;
    private String id_film;
    FloatingActionMenu actionMenu;
    FloatingActionButton buttonShare, buttonTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // identify interface
        txtTitleMovie = (TextView) findViewById(R.id.movie_title);
        imageBackdrop = (ImageView) findViewById(R.id.img_backdrop);
        imageBackdrop.setScaleType(ImageView.ScaleType.FIT_XY);
        imagePoster = (ImageView) findViewById(R.id.img_poster_detail);
        imagePoster.setScaleType(ImageView.ScaleType.FIT_XY);
        txtRating = (TextView) findViewById(R.id.rating);
        txtYear = (TextView) findViewById(R.id.year);
        txtOverview = (TextView) findViewById(R.id.overview);

        // Floating action
        actionMenu = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        buttonShare = (FloatingActionButton) findViewById(R.id.btnShare);
        buttonTrailer = (FloatingActionButton) findViewById(R.id.btnTrailer);

        buttonTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TrailerActivity.class);
                i.putExtra(AppConstant.movie_id_intent, id_film);
                startActivity(i);
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "#Movista# \n\nMovie title : " + title + "\n\n" +
                        overview + "\n\n" +
                        "Rating : \u2605 " + rating + "\n" +
                        "Release date : " + release_date + "\n";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Movista");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share in your friends"));
            }
        });

        // catch data from main activity
        extras = getIntent().getExtras();
//        if (savedInstanceState != null){
            title = extras.getString(AppConstant.movie_title_intent);
            backdrop = extras.getString(AppConstant.movie_backdrop_intent);
            poster = extras.getString(AppConstant.movie_poster_intent);
            rating = extras.getString(AppConstant.movie_vote_intent);
            release_date = extras.getString(AppConstant.movie_year_intent);
            overview = extras.getString(AppConstant.movie_desc_intent);
            id_film = extras.getString(AppConstant.movie_id_intent);
//        }
//        else {
//            title = (String) savedInstanceState.getSerializable(AppConstant.movie_title_intent);
//            backdrop = (String) savedInstanceState.getSerializable(AppConstant.movie_backdrop_intent);
//            poster = (String) savedInstanceState.getSerializable(AppConstant.movie_poster_intent);
//            rating = (String) savedInstanceState.getSerializable(AppConstant.movie_vote_intent);
//            release_date = (String) savedInstanceState.getSerializable(AppConstant.movie_year_intent);
//            overview = (String) savedInstanceState.getSerializable(AppConstant.movie_desc_intent);
//            id_film = (String) savedInstanceState.getSerializable(AppConstant.movie_id_intent);
//        }


        // bind data to interface
        setTitle(title);
        txtTitleMovie.setText(title);
        Picasso.with(this).load(backdrop)
                .placeholder(R.drawable.backdrop)
                .into(imageBackdrop);
        Picasso.with(this).load(poster)
                .placeholder(R.drawable.backdrop)
                .into(imagePoster);
        txtYear.setText(release_date);
        txtRating.setText(rating + " / 10");
        txtOverview.setText(overview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//
//        outState.putSerializable(AppConstant.movie_title_intent, (String) txtTitleMovie.getText());
//        outState.putSerializable(AppConstant.movie_backdrop_intent, imageBackdrop.toString());
//        outState.putSerializable(AppConstant.movie_poster_intent, imagePoster.toString());
//        outState.putSerializable(AppConstant.movie_vote_intent, (String) txtRating.getText());
//        outState.putSerializable(AppConstant.movie_year_intent, (String) txtYear.getText());
//        outState.putSerializable(AppConstant.movie_desc_intent, (String) txtOverview.getText());
//        outState.putSerializable(AppConstant.movie_id_intent, id_film);
//
//        super.onSaveInstanceState(outState);
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//
//        title = savedInstanceState.getString(AppConstant.movie_title_intent);
//        release_date = savedInstanceState.getString(AppConstant.movie_year_intent);
//        rating = savedInstanceState.getString(AppConstant.movie_vote_intent);
//        overview = savedInstanceState.getString(AppConstant.movie_desc_intent);
//        backdrop = savedInstanceState.getString(AppConstant.movie_backdrop_intent);
//        poster = savedInstanceState.getString(AppConstant.movie_poster_intent);
//        id_film = savedInstanceState.getString(AppConstant.movie_id_intent);
//        super.onRestoreInstanceState(savedInstanceState);
//    }
}
