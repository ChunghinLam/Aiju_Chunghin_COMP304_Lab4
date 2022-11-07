package com.example.aiju_chunghin_comp304_lab4;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.Models.Ticket;

@Database(entities = {Customer.class, Movie.class, Ticket.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "MOVIE_DB";
    private static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration().build();
        }

        return instance;
    }

}
