package com.example.aiju_chunghin_comp304_lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load movies to DB
        Movie m1 = new Movie(getResources().getString(R.string.movie_bullettrain));
        Movie m2 = new Movie(getResources().getString(R.string.movie_topgun));
        Movie m3 = new Movie(getResources().getString(R.string.movie_minions));
        Movie m4 = new Movie(getResources().getString(R.string.movie_pearl));
        Movie m5 = new Movie(getResources().getString(R.string.movie_womanking));

        try {
            MovieViewModel model = new ViewModelProvider(this).get(MovieViewModel.class);

            // clear everything before adding
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    model.deleteAllMovies();

                    model.insert(m1);
                    model.insert(m2);
                    model.insert(m3);
                    model.insert(m4);
                    model.insert(m5);
                }
            });

            // movie list handling
            ListView movieListView = findViewById(R.id.lvMovieList);
//            LiveData<List<Movie>> movieList = model.getAllMovies();
            model.getAllMovies().observe(this, new Observer<List<Movie>>() {

                @Override
                public void onChanged(@Nullable List<Movie> list) {
                    MovieAdapter adapter = new MovieAdapter(getApplicationContext(), new ArrayList<>(list));

//                    ArrayAdapter<Movie> arr = new ArrayAdapter<>(getApplicationContext(), R.layout.movie_row,
//                            R.id.tvMovieName, new ArrayList<>(list));

//                    ArrayAdapter<Movie> arr = new ArrayAdapter<>(getApplicationContext(),
//                        R.layout.movie_row,
//                        new ArrayList<>(list));

                    movieListView.setAdapter(adapter);
                }
            });
        }
        catch (Exception ex) {
            TextView tvErrorMsg = findViewById(R.id.tvErrorMsg);
            tvErrorMsg.setText(ex.getMessage());
        }

        // if is login, show username, else, show welcome guest
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0);

        tvWelcome.setText(tvWelcome.getText() + " " +
                (pref.getString("username", null) == null ?
                getResources().getString(R.string.str_guest) : pref.getString("username", null)));


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
    // test commit to branch akinoshi
}
