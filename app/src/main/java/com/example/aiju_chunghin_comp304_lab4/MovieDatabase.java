package com.example.aiju_chunghin_comp304_lab4;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;

import com.example.aiju_chunghin_comp304_lab4.DAOs.CustomerDAO;
import com.example.aiju_chunghin_comp304_lab4.DAOs.MovieDAO;
import com.example.aiju_chunghin_comp304_lab4.DAOs.TicketDAO;
import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.Models.Ticket;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Customer.class, Movie.class, Ticket.class}, version = 1)
@TypeConverters({Ticket.MyTypeConverters.class})
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "MOVIE_DB";
    private static final int NUMBER_OF_THREADS = 4;
    private static volatile MovieDatabase instance;

    public abstract CustomerDAO customerDAO();
    public abstract MovieDAO movieDAO();
    public abstract TicketDAO ticketDAO();

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static MovieDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (MovieDatabase.class) {
                if (instance == null) {
                    // since it is a simple application, we simplify to use main thread for easier db access handling
                    instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DB_NAME)
//                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return instance;
    }
}
