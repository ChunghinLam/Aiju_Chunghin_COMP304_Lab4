package com.example.aiju_chunghin_comp304_lab4.Repos;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aiju_chunghin_comp304_lab4.DAOs.TicketDAO;
import com.example.aiju_chunghin_comp304_lab4.Models.Ticket;
import com.example.aiju_chunghin_comp304_lab4.MovieDatabase;

import java.util.List;

public class TicketRepository {
    private final TicketDAO ticketDAO;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Ticket>> ticketList;

    public TicketRepository(Context context) {
        MovieDatabase db = MovieDatabase.getInstance(context);
        ticketDAO = db.ticketDAO();
        ticketList = ticketDAO.getTicketList();
    }

    public LiveData<List<Ticket>> getAllTickets() {
        return ticketList;
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public void insert(Ticket ticket) {
        ticketDAO.insertTicket(ticket);
    }
}
