package com.example.aiju_chunghin_comp304_lab4.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aiju_chunghin_comp304_lab4.Models.Ticket;
import com.example.aiju_chunghin_comp304_lab4.Repos.TicketRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TicketViewModel extends AndroidViewModel {
    private TicketRepository ticketRepository;
    private LiveData<Integer> insertResult;
    private LiveData<List<Ticket>> allTickets;
    private LiveData<List<Ticket>> ticketsByCustomer;

    public TicketViewModel(@NotNull Application application) {
        super(application);

        ticketRepository = new TicketRepository(application);
        insertResult = ticketRepository.getInsertResult();
        allTickets = ticketRepository.getAllTickets();
//        ticketsByCustomer = ticketRepository.getTicketsByCustId();
    }

    public void insert(Ticket ticket) {
        ticketRepository.insert(ticket);
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public LiveData<List<Ticket>> getAllTickets() {
        return allTickets;
    }

    public LiveData<List<Ticket>> getTicketsByCustId(Integer custId) { return ticketsByCustomer; }
}
