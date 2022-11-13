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


        // TODO: retrieve ticket information use custId
//        CustomerViewModel customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        MovieViewModel movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        TicketViewModel ticketViewModel = new ViewModelProvider(this).get(TicketViewModel.class);

        try {
            ticketViewModel.getTicketsByCustId(custId).observe(this, new Observer<List<Ticket>>() {
                @Override
                public void onChanged(List<Ticket> tickets) {
                    if (tickets != null && !tickets.isEmpty()) {
                        for (Ticket ticket : tickets) {
                            TextView tvMovieName = findViewById(R.id.row2tv1);

                            String movieName = movieViewModel.getMovieNameById(ticket.movieId);
                            tvMovieName.setText(movieName);
                            // TODO: fill the table
                        }

                        TextView info = findViewById(R.id.ticket_info_wrap);
                        info.setText("");
                    }
                    else {
                        // no tickets info

                    }
                }
            });
        }
        catch (Exception ex) {

        }
    }
}