package com.example.aiju_chunghin_comp304_lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.Models.Ticket;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.CustomerViewModel;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.MovieViewModel;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.TicketViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class MyTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);

        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
        Integer custId = pref.getInt("user_custid", 0);

        MovieViewModel movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        TicketViewModel ticketViewModel = new ViewModelProvider(this).get(TicketViewModel.class);

        try {
            ticketViewModel.getTicketsByCustId(custId).observe(this, new Observer<List<Ticket>>() {
                @Override
                public void onChanged(List<Ticket> tickets) {
                    if (tickets != null && !tickets.isEmpty()) {
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    for (Ticket ticket : tickets) {
                                        String movieName = movieViewModel.getMovieNameById(ticket.movieId);

                                        TextView tvMovieName = findViewById(R.id.row2tv1);
                                        TextView tvShowDate = findViewById(R.id.row2tv2);
                                        TextView tvShowTime = findViewById(R.id.row2tv3);
                                        TextView tvNoOfTickets = findViewById(R.id.row2tv4);
                                        TextView tvPrice = findViewById(R.id.row2tv5);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tvMovieName.setText(movieName);
                                                tvShowDate.setText(ticket.getShowDate().toString());
                                                tvShowTime.setText("");
                                                tvNoOfTickets.setText(ticket.getNumberOfTickets() + "");
                                                tvPrice.setText(ticket.getPrice() + "");
                                            }
                                        });
                                    }
                                }
                            });
                        }
                        else {
                            // no tickets info

                        }
                        TextView info = findViewById(R.id.ticket_info_wrap);
                        info.setText("");
                    }
            });
        }
        catch (Exception ex) {
            String s = ex.getMessage();
        }
    }
}