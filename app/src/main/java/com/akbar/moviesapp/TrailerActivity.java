package com.akbar.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.VideoView;

import com.akbar.moviesapp.Adapter.AdapterTrailer;
import com.akbar.moviesapp.model.TrailerData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class TrailerActivity extends AppCompatActivity {
    VideoView video;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TrailerData trailerData;
    private AdapterTrailer adapterTrailer;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

//        video = (VideoView) findViewById(R.id.trailer);
//        video.setVideoPath("");
//        video.start();

        setTitle("Trailer");
        recyclerView = (RecyclerView) findViewById(R.id.list_trailer);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        extras = getIntent().getExtras();
        String id_film = extras.getString(AppConstant.movie_id_intent);

        getDataTrailer(id_film);
    }

    private void getDataTrailer(String id){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        String url = AppConstant.movie_url_api + id + "/videos?api_key=" + AppConstant.movie_api_key;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){;

            @Override
            public void onResponse(String response) {
                Log.d("Trailer Activity", "Response " + response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                trailerData = gson.fromJson(response, TrailerData.class);
                adapterTrailer = new AdapterTrailer(TrailerActivity.this, trailerData.results);
                recyclerView.setAdapter(adapterTrailer);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
