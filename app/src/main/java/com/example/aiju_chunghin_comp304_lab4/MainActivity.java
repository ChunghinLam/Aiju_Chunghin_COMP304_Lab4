package com.example.aiju_chunghin_comp304_lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadMovies();

        // if is login, show username, else, show welcome guest
        TextView tvWelcome = findViewById(R.id.tvWelcome);
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0);
        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);

        /* INNOVATION */
        String userName = pref.getString("user_email", "Guest");
//        Toast.makeText(getApplicationContext(), 1+userName, Toast.LENGTH_SHORT).show();
        userName = userName.replaceAll("(.*)(@.*)", "$1");
//        Toast.makeText(getApplicationContext(), 2+userName, Toast.LENGTH_SHORT).show();
        tvWelcome.setText(tvWelcome.getText()+" "+userName);
//        tvWelcome.setText(tvWelcome.getText()+" "+pref.getString("user_email", "Guest"));

        /* INNOVATION */
        // View visibility control
//        LinearLayout loginRegView = findViewById(R.id.main_guest);
//        LinearLayout loggedView = findViewById(R.id.main_loggedin);
//
//        if (pref.contains("user_email")) {
//            loginRegView.setVisibility(View.GONE);
//            loggedView.setVisibility(View.VISIBLE);
//        }

        // login button handling
//        Button btnLogin = findViewById(R.id.btnLogin);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent login = new Intent(MainActivity.this, LoginActivity.class);
////                finish();
////                startActivity(getIntent());
//                startActivity(login);
//            }
//        });

        // register button handling
//        Button btnRegister = findViewById(R.id.btnRegister);
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent register = new Intent(MainActivity.this, RegistrationActivity.class);
//                finish();
//                startActivity(register);
//            }
//        });

        // logout button handling
//        Button btnLogout = findViewById(R.id.btn_logout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
//                SharedPreferences.Editor prefEditor = pref.edit();
//                prefEditor.remove("user_email");
//                prefEditor.remove("user_password");
//                prefEditor.remove("user_fname");
//                prefEditor.remove("user_lname");
//                prefEditor.remove("user_addr");
//                prefEditor.remove("user_city");
//                prefEditor.remove("user_post");
//                prefEditor.remove("user_custid");
//                prefEditor.commit();
//
//                finish();
//                startActivity(getIntent());
//            }
//        });

        // account button handling
//        Button btnAccount = findViewById(R.id.btn_account);
//        btnAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent account = new Intent(MainActivity.this, AccountActivity.class);
//                finish();
//                startActivity(account);
//            }
//        });
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
            model.getAllMovies().observe(this, new Observer<List<Movie>>() {

                @Override
                public void onChanged(@Nullable List<Movie> list) {
                    MovieAdapter adapter = new MovieAdapter(getApplicationContext(), new ArrayList<>(list));

                    movieListView.setAdapter(adapter);
                    movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            String value = (String)parent.getItemAtPosition(position);
                            Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                            intent.putExtra("movieId", position);

                            String date = getApplicationContext().getString(R.string.showdate_1113);
                            intent.putExtra("movieShowDate", date.toString());
                            startActivity(intent);
                        }
                    });
                }
            });
        }
        catch (Exception ex) {
//            TextView tvErrorMsg = findViewById(R.id.tvErrorMsg);
//            tvErrorMsg.setText(ex.getMessage());
        }
    }
}
