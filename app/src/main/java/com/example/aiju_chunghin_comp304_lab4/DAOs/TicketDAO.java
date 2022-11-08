package com.example.aiju_chunghin_comp304_lab4.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;

import java.util.List;

@Dao
public interface TicketDAO {
    @Query("Select * from Ticket")
    List<Customer> getTicketList();

    @Insert
    public void insertTicket();

    @Update
    public void updateTicket();

    @Delete
    public void deleteTicket();
}
