package com.example.aiju_chunghin_comp304_lab4.DAOs;

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
    List<Movie> getMovieList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMovie(Movie movie);

    @Update
    public void updateMovie();

    @Delete
    public void deleteMovie();
}
