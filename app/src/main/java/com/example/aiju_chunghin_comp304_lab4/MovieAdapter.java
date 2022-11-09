package com.example.aiju_chunghin_comp304_lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aiju_chunghin_comp304_lab4.Models.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    private List<Movie> moviesList = new ArrayList<>();

    public MovieAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
        mContext = context;
        moviesList = list;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.movie_row,parent,false);

        Movie currentMovie = moviesList.get(position);

        ImageView image = listItem.findViewById(R.id.ivMovieAvatar);
        String movieName = currentMovie.getMovieName();

        // simply way
        if (movieName.toLowerCase(Locale.ROOT).contains("bullet"))
            image.setImageResource(R.mipmap.bullettrain_foreground);
        else if (movieName.toLowerCase(Locale.ROOT).contains("minions"))
            image.setImageResource(R.mipmap.minions_foreground);
        else if (movieName.toLowerCase(Locale.ROOT).contains("pearl"))
            image.setImageResource(R.mipmap.pearl_foreground);
        else if (movieName.toLowerCase(Locale.ROOT).contains("top"))
            image.setImageResource(R.mipmap.topgun_foreground);
        else if (movieName.toLowerCase(Locale.ROOT).contains("woman"))
            image.setImageResource(R.mipmap.womanking_foreground);

        TextView tvMovieName = listItem.findViewById(R.id.tvMovieName);
        tvMovieName.setText(movieName);

        TextView tvShowDate = (TextView) listItem.findViewById(R.id.tvShowDate);
        TextView tvShowTime = (TextView) listItem.findViewById(R.id.tvShowTime);

        return listItem;
    }
}
