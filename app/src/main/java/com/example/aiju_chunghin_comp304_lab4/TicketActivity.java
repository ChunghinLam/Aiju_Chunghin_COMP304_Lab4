package com.example.aiju_chunghin_comp304_lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.Models.Ticket;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.MovieViewModel;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.TicketViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketActivity extends AppCompatActivity {
    final int TICKET_PRICE = 9; // assumption

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        Intent intent = getIntent();
        Integer movieId = intent.getIntExtra("movieId", 0);
        String movieShowDate = intent.getStringExtra("movieShowDate"); // Nov 13

        ImageView imageView = findViewById(R.id.ivMovieAvatar);
        // simplify way to implement
        switch (movieId) {
            case 0:
                imageView.setImageResource(R.mipmap.bullettrain_foreground);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.topgun_foreground);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.minions_foreground);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.pearl_foreground);
                break;
            case 4:
                imageView.setImageResource(R.mipmap.womanking_foreground);
                break;
        }

        // setup ticket price, assume its $9 per ticket
        TextView tvPrice = findViewById(R.id.tvPrice);
        tvPrice.setText(tvPrice.getText() + " " + TICKET_PRICE);

        // setup number picker
        NumberPicker npkTicket = findViewById(R.id.npkTicket);
        npkTicket.setMinValue(0);
        npkTicket.setMaxValue(10);
        npkTicket.setValue(0);
        npkTicket.setEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        TicketViewModel ticketModel = new ViewModelProvider(this).get(TicketViewModel.class);
        MovieViewModel movieModel = new ViewModelProvider(this).get(MovieViewModel.class);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                RadioGroup radioGroup = findViewById(R.id.rgpShowtime);
                int showtimeId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(showtimeId);

                if (showtimeId == -1) {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.msg_must_select_a_showtime), Toast.LENGTH_SHORT).show();
                    return;
                }

                int numberOfTicket = npkTicket.getValue();

                // save to db
                try {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            long millisec = 0;

                            SimpleDateFormat f = new SimpleDateFormat("MMM dd yyyy");
                            try {
                                Date d = f.parse(movieShowDate + " 2022");

                                millisec = d.getTime();
                            } catch (ParseException pe) {
                                pe.printStackTrace();
                            }

                            String[] movieList = getResources().getStringArray(R.array.movieList);
                            Movie theMovie = movieModel.getMovieByName(movieList[movieId]);

                            SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
                            Ticket newTicket = new Ticket(
                                pref.getInt("user_custid", 0),
                                theMovie.getMovieId(),
                                numberOfTicket, millisec, (numberOfTicket * TICKET_PRICE)
                            );

                            ticketModel.insert(newTicket);
                        }
                    });
                }
                catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                // return to main screen
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.msg_purchase_success), Toast.LENGTH_LONG).show();
                moveToMainPage();
            }
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveToMainPage();
            }
        });
    }

    private void moveToMainPage() {
        Intent intent = new Intent(TicketActivity.this, MainActivity.class);
        startActivity(intent);
    }
}