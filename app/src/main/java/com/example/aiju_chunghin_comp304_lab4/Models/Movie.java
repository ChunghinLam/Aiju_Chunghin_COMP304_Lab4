package com.example.aiju_chunghin_comp304_lab4.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Movie {
    @NotNull
    @PrimaryKey
    public int movieId;
}
