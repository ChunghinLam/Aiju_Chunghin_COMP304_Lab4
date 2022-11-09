package com.example.aiju_chunghin_comp304_lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Authentication method
    private boolean authentication(){
        //TODO: How do we check the eligibility? 1. if matches password stored in db, 2. authentication api
        if (true)
            return true;
        else
            return false;

    }
}