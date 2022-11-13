package com.example.aiju_chunghin_comp304_lab4.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aiju_chunghin_comp304_lab4.Models.Ticket;

import java.util.List;

@Dao
public interface TicketDAO {
    @Query("Select * from Ticket")
    LiveData<List<Ticket>> getTicketList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTicket(Ticket ticket);

    @Update
    public void updateTicket(Ticket ticket);

    @Delete
    public void deleteTicket(Ticket ticket);

    @Query("Select * from Ticket where custId = :custId")
    LiveData<List<Ticket>> getTicketsByCustomer(Integer custId);
}
