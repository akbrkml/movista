package com.akbar.moviesapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akbar.moviesapp.AppConstant;
import com.akbar.moviesapp.DetailMovie;
import com.akbar.moviesapp.model.MovieData;
import com.akbar.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kamal on 21/11/2016.
 */

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.AdapterHolder> {

    Context mContext;
    public List<MovieData.Result> listMovie;

    public class AdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView list_movie;
        public ImageView list_image;

        public String title;
        public String backdrop;
        public String poster;
        public String overview;
        public String rating;
        public String trailer;
        public String id_film;
        public String release_date;

        public AdapterHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            list_movie = (TextView) itemView.findViewById(R.id.movie_title);
            list_image = (ImageView) itemView.findViewById(R.id.gambar_poster);
            list_image.setScaleType(ImageView.ScaleType.FIT_XY);
//            txt_rating = (TextView) itemView.findViewById(R.id.txt_rating);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailMovie.class);
            intent.putExtra(AppConstant.movie_title_intent, title);
            intent.putExtra(AppConstant.movie_poster_intent, poster);
            intent.putExtra(AppConstant.movie_backdrop_intent, backdrop);
            intent.putExtra(AppConstant.movie_desc_intent, overview);
            intent.putExtra(AppConstant.movie_vote_intent, rating);
            intent.putExtra(AppConstant.movie_year_intent, release_date);
            intent.putExtra(AppConstant.movie_id_intent, id_film);
            v.getContext().startActivity(intent);
        }
    }

    public AdapterMovies(Context context, List<MovieData.Result> list_movie){
        this.mContext = context;
        this.listMovie = list_movie;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movies, parent, false);
        AdapterHolder adapterHolder = new AdapterHolder(rowView);
        return adapterHolder;
}

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        Picasso.with(mContext).load(AppConstant.movie_poster_link + listMovie.get(position).poster_path)
                .placeholder(R.drawable.backdrop)
                .into(holder.list_image);

//        holder.list_movie.setText(listMovie.get(position).original_title);
//        holder.txt_rating.setText(String.valueOf(listMovie.get(position).vote_average));
        holder.title = listMovie.get(position).original_title;
        holder.overview = listMovie.get(position).overview;
        holder.rating = String.valueOf(listMovie.get(position).vote_average);
        holder.release_date = listMovie.get(position).release_date;
        holder.id_film = String.valueOf(listMovie.get(position).id);
        holder.backdrop = AppConstant.movie_backdrop_link + listMovie.get(position).backdrop_path;
        holder.poster = AppConstant.movie_poster_link + listMovie.get(position).poster_path;
    }

    @Override
    public int getItemCount() {
        return this.listMovie.size();
    }
}
