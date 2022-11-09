package com.example.aiju_chunghin_comp304_lab4.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.Repos.MovieRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<Integer> insertResult;
    private LiveData<List<Movie>> allMovies;

    public MovieViewModel(@NotNull Application application) {
        super(application);

        movieRepository = new MovieRepository(application);
        insertResult = movieRepository.getInsertResult();
        allMovies = movieRepository.getAllMovies();
    }

    public void insert(Movie movie) {
        movieRepository.insert(movie);
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }
}
