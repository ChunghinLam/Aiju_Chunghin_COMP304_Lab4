package com.example.aiju_chunghin_comp304_lab4.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aiju_chunghin_comp304_lab4.Models.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("Select * from Movie")
    LiveData<List<Movie>> getMovieList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMovie(Movie movie);

    @Update
    public void updateMovie(Movie movie);

    @Delete
    public void deleteMovie(Movie movie);

    @Query("DELETE FROM Movie")
    public void deleteAllMovies();

    @Query("Select * from Movie where movieName == :name")
    public Movie getMovieByName(String name);
}
