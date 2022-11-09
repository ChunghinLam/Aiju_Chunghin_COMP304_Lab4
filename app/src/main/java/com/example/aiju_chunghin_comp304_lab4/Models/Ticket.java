package com.example.aiju_chunghin_comp304_lab4.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity
public class Ticket {
    @NotNull
    @PrimaryKey(autoGenerate = true)
    public int ticketId;

    public int custId, movieId, numberOfTickets;

    public long showDate;

    public Ticket(int custId, int movieId, int numberOfTickets, long showDate, double price) {
        this.custId = custId;
        this.movieId = movieId;
        this.numberOfTickets = numberOfTickets;
        this.showDate = showDate;
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Date getShowDate() {
        return MyTypeConverters.dateFromTimestamp(showDate);
    }

    public void setShowDate(Date showDate) {
        this.showDate = MyTypeConverters.dateToTimestamp(showDate);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double price;

    public static class MyTypeConverters {
        @TypeConverter
        public static Date dateFromTimestamp(Long value) {
            return value == null ? null : new Date(value); }
        @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime(); }
    }
}
