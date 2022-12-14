package com.example.aiju_chunghin_comp304_lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* INNOVATION */
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieViewModel model = new ViewModelProvider(this).get(MovieViewModel.class);

        // movie list handling
        ListView movieListView = findViewById(R.id.lvMovieList);
        model.getAllMovies().observe(this, new Observer<List<Movie>>() {

            @Override
            public void onChanged(@Nullable List<Movie> list) {
                if (list.isEmpty())
                    loadMovies(); // load only when db is empty, i.e. when initialize

                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), new ArrayList<>(list));

                movieListView.setAdapter(adapter);
                movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                        intent.putExtra("movieId", position);
                        String date = getApplicationContext().getString(R.string.showdate_1113);
                        intent.putExtra("movieShowDate", date.toString());

                        startActivity(intent);
                    }
                });
            }
        });

        // if is login, show username, else, show welcome guest
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);

        /* INNOVATION */
        String userName = pref.getString("user_email", "Guest");
        userName = userName.replaceAll("(.*)(@.*)", "$1");
        tvWelcome.setText(tvWelcome.getText()+" "+userName);

        /* INNOVATION */
        // View visibility control
//        LinearLayout loginRegView = findViewById(R.id.main_guest);
//        LinearLayout loggedView = findViewById(R.id.main_loggedin);
//
//        if (pref.contains("user_email")) {
//            loginRegView.setVisibility(View.GONE);
//            loggedView.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);

        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);

        // if is login
        if (pref.contains("user_email")) {
            MenuItem mnuLogin = menu.findItem(R.id.mnuLogin);
            mnuLogin.setVisible(false);

            MenuItem mnuRegister = menu.findItem(R.id.mnuRegister);
            mnuRegister.setVisible(false);
        }
        else {
            MenuItem mnuLogout = menu.findItem(R.id.mnuLogout);
            mnuLogout.setVisible(false);

            MenuItem mnuAccount = menu.findItem(R.id.mnuMyAccount);
            mnuAccount.setVisible(false);

            MenuItem mnuTicket = menu.findItem(R.id.mnuMyTicket);
            mnuTicket.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuLogin:
                doLogin();
                return true;
            case R.id.mnuLogout:
                doLogout();
                return true;
            case R.id.mnuRegister:
                doRegister();
                return true;
            case R.id.mnuMyAccount:
                doMyAccount();
                return true;
            case R.id.mnuMyTicket:
                doMyTicket();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doLogin() {
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        finish();
        startActivity(login);
    }

    private void doLogout() {
        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.remove("user").commit();

        prefEditor.remove("user_email");
        prefEditor.remove("user_password");
        prefEditor.remove("user_fname");
        prefEditor.remove("user_lname");
        prefEditor.remove("user_addr");
        prefEditor.remove("user_city");
        prefEditor.remove("user_post");
        prefEditor.remove("user_custid");
        prefEditor.commit();

        finish();
        startActivity(getIntent());
    }

    private void doRegister() {
        Intent register = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(register);
    }

    private void doMyAccount() {
        Intent account = new Intent(MainActivity.this, MyAccountActivity.class);
        startActivity(account);
    }

    private void doMyTicket() {
        Intent myTicket = new Intent(MainActivity.this, MyTicketActivity.class);
        startActivity(myTicket);
    }

    private void loadMovies() {
        MovieViewModel model = new ViewModelProvider(this).get(MovieViewModel.class);

        // load movies to DB
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(getResources().getString(R.string.movie_bullettrain)));
        movieList.add(new Movie(getResources().getString(R.string.movie_topgun)));
        movieList.add(new Movie(getResources().getString(R.string.movie_minions)));
        movieList.add(new Movie(getResources().getString(R.string.movie_pearl)));
        movieList.add(new Movie(getResources().getString(R.string.movie_womanking)));

        try {

            // clear everything before adding
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    model.deleteAllMovies();

                    for (Movie movie : movieList) {
                        model.insert(movie);
                    }
                }
            });
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

/* REQUIREMENTS *
 * DONE The main activity that handles the navigation to book a movie ticket.
 * * TODO This activity will further add the movie details (movieId and movieName to the Movie Table  (use hardcoded values / without input forms).                           [5 marks]
 * DONE The login activity will allow the customer to login for existing customer and register for new customers using a link. Use email as user name with password.
 * * DONE Login credential check involves matching the user inputs with the login details available in the Customer table.                                                                                                                                                                                                                                   [3 marks]
 * DONE The registration activity will allow the customer to input customer information and this information will be stored in the customer table.                                                                                                                                                                                                       [4 marks]
 * DONE The update customer activity will allow the customer to view / update customer information such password, address, city, postal code and email.                                                                                                                                                                                               [4 marks]
 * TODO The Ticket activity will allow a customer to book a ticket. This information should be stored in the Ticket table.    [4 marks]
 * TODO The ticket view activity will allow the customer to view /print or cancel the ticket.                                      [6 marks]
 * DONE Use Shared Preferences to store user name after successful login and publish this user name as a welcome message in the ticket activity.
 * *  TODO Provide a friendly and easy to navigate UI. Use images and image buttons.                                                  [4 marks]
         Hint: Innovative features
 * TODO Innovations:
         You can cancel the tickets 48 hours before the show date/time (date validation)
         OR
         You can add more movie details to the movie table such as actor, director, release year or language.
         OR
         You can add more fields to Ticket table.
         OR
         any other innovation!
         *  get user name based on email
         *  visible control (not used but once implemented and worked)
         *  initially set current information in input view
         *  Stay in day mode (disabled dark/night mode)
 */
