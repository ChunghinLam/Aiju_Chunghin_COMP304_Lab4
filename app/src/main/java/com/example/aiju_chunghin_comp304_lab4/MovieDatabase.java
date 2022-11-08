package com.example.aiju_chunghin_comp304_lab4;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.Models.Ticket;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Customer.class, Movie.class, Ticket.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "MOVIE_DB";
    private static final int NUMBER_OF_THREADS = 4;
    private static volatile MovieDatabase instance;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static MovieDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (MovieDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration().build();
                }
            }
        }

        return instance;
    }

}
