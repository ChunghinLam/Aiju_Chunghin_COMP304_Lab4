package com.example.aiju_chunghin_comp304_lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiju_chunghin_comp304_lab4.DAOs.MovieDAO;
import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.MovieViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load movies to DB
        List<Movie> movieList = null;
        try {
            MovieViewModel model = new ViewModelProvider(this).get(MovieViewModel.class);

            // clear the table before adding everytime
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    model.deleteAllMovies();
                }
            });

            Movie m1 = new Movie("movie 1"); // testing
            Movie m2 = new Movie("movie 2"); // testing

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    model.insert(m1);
                    model.insert(m2);
                }
            });

            model.getAllMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movieList) {
                    for (Movie movie : movieList) {
                        Toast.makeText(getApplicationContext(), movie.movieName, Toast.LENGTH_SHORT).show();
//                        movieList.add(movie);
                    }
                }
            });
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // if is login, show username, else, show welcome guest
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0);

        tvWelcome.setText(tvWelcome.getText() + " " +
                (pref.getString("username", null) == null ?
                getResources().getString(R.string.str_guest) : pref.getString("username", null)));

        // movie list handling
        ListView movieListView = findViewById(R.id.lvMovieList);
        ArrayAdapter<Movie> arr = new ArrayAdapter<Movie>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                movieList);

//        movieListView.setAdapter(arr);
//        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                String value = (String)parent.getItemAtPosition(position);
//                Intent intent = new Intent();
//
////                switch (position) {
////                    case 0: // Task 1
////                        intent = new Intent(MainActivity.this, DrawLineActivity.class);
////                        break;
////                    case 1: // Task 2
////                        intent = new Intent(MainActivity.this, FrameActivity.class);
////                        break;
////                    case 2: // Task 3
////                        intent = new Intent(MainActivity.this, AnimationActivity.class);
////                        break;
////                }
//                startActivity(intent);
//            }
//        });

        // login button handling
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        // register button handling
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // do sth
            }
        });
    }

}
