package com.akbar.moviesapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akbar.moviesapp.R;
import com.akbar.moviesapp.model.TrailerData;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by kamal on 29/11/2016.
 */

public class AdapterTrailer extends RecyclerView.Adapter<AdapterTrailer.TrailerHolder> {

    List<TrailerData.Result> listTrailer;
    Context mContext;

    public class TrailerHolder extends RecyclerView.ViewHolder {
        TextView titleTrailer;
        TextView type;

        String key;

        public TrailerHolder(View itemView) {
            super(itemView);
            titleTrailer = (TextView) itemView.findViewById(R.id.txtName);
            type = (TextView) itemView.findViewById(R.id.txtType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/watch?v=" + key));
                    i.putExtra("judul", titleTrailer.getText().toString());
                    i.putExtra("type", type.getText().toString());
                    view.getContext().startActivity(i);
                }
            });
        }
    }

    public AdapterTrailer (Context context, List<TrailerData.Result> list_trailer){
        this.mContext = context;
        this.listTrailer = list_trailer;
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        TrailerHolder trailerHolder = new TrailerHolder(rowView);
        return trailerHolder;
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {
        holder.titleTrailer.setText(listTrailer.get(position).name);
        holder.type.setText(listTrailer.get(position).type);
        holder.key = listTrailer.get(position).key;
    }

    @Override
    public int getItemCount() {
        return listTrailer.size();
    }
}
