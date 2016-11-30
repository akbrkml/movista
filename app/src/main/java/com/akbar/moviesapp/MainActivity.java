package com.akbar.moviesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.akbar.moviesapp.model.MovieData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import com.akbar.moviesapp.Adapter.AdapterMovies;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterMovies adapterMovies;
    Spinner spinner_movie;
    List<String> categories;
    ArrayAdapter<String> dataAdapter;
    private String categoryFilm;
    MovieData movieData;
    private LinearLayoutManager layoutManager;
    private ProgressDialog pDialog;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        spinner_movie = (Spinner) findViewById(R.id.spinner_movie);

        categories = new ArrayList<>();
        categories.add("Now Playing");
        categories.add("Popular");
        categories.add("Top Rated");
        categories.add("Upcoming");

        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_movie.setAdapter(dataAdapter);

        spinner_movie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        requestJsonData(0);
                        break;
                    case 1:
                        requestJsonData(1);
                        break;
                    case 2:
                        requestJsonData(2);
                        break;
                    case 3:
                        requestJsonData(3);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void requestJsonData(int i){

        if (i == 0){
            categoryFilm = "now_playing";
            setTitle("Now Playing");
        }else if (i == 1){
            categoryFilm = "popular";
            setTitle("Popular");
        }else if (i == 2){
            categoryFilm = "top_rated";
            setTitle("Top Rated");
        }else if (i == 3){
            categoryFilm = "upcoming";
            setTitle("Upcoming");
        }

        String URLMovie = AppConstant.movie_url_api + categoryFilm + "?api_key=" + AppConstant.movie_api_key;

        JSONParser(URLMovie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_setting, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplication(), "Searching" ,Toast.LENGTH_LONG).show();
                String url = "";
                url = AppConstant.movie_url_api_search +
                        categoryFilm +
                        "&query=" + query +
                        "&api_key=" + AppConstant.movie_api_key;
                JSONParser(url);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.about){
            Intent aboutIntent = new Intent(this, AboutUs.class);
            startActivity(aboutIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void JSONParser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {;
            @Override
            public void onResponse(String response) {
                Log.d("Main Activity", "Response " + response);
                hidePDialog();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                movieData = gson.fromJson(response, MovieData.class);
                adapterMovies = new AdapterMovies(MainActivity.this, movieData.results);
                recyclerView.setAdapter(adapterMovies);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "Error while connecting to the server.", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplication(), "Please check your connection.", Toast.LENGTH_SHORT).show();
                hidePDialog();
            }
        });

        requestQueue.add(stringRequest);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}

