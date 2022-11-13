package com.example.aiju_chunghin_comp304_lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MyTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);

        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
        Integer custId = pref.getInt("user_custid", 0);

        // TODO: retrieve ticket information use custId
    }
}