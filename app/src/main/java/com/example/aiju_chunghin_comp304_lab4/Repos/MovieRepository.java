package com.example.aiju_chunghin_comp304_lab4.Repos;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aiju_chunghin_comp304_lab4.DAOs.CustomerDAO;
import com.example.aiju_chunghin_comp304_lab4.DAOs.MovieDAO;
import com.example.aiju_chunghin_comp304_lab4.DAOs.TicketDAO;
import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.MovieDatabase;

import java.util.List;

public class MovieRepository {
    private final MovieDAO movieDAO;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Movie>> movieList;


    public MovieRepository(Context context) {
        MovieDatabase db = MovieDatabase.getInstance(context);
        movieDAO = db.movieDAO();
        movieList = movieDAO.getMovieList();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return movieList;
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public void insert(Movie movie) {
        movieDAO.insertMovie(movie);
    }
    public void deleteAllMovies() {
        movieDAO.deleteAllMovies();
    }

    public Movie getMovieByName(String name) {
        return movieDAO.getMovieByName(name);
    }
}
