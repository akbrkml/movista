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

        // catch data from main activity
        extras = getIntent().getExtras();
        title = extras.getString(AppConstant.movie_title_intent);
        backdrop = extras.getString(AppConstant.movie_backdrop_intent);
        poster = extras.getString(AppConstant.movie_poster_intent);
        rating = extras.getString(AppConstant.movie_vote_intent);
        release_date = extras.getString(AppConstant.movie_year_intent);
        overview = extras.getString(AppConstant.movie_desc_intent);
        id_film = extras.getString(AppConstant.movie_id_intent);

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
}
