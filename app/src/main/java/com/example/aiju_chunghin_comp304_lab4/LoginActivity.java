package com.example.aiju_chunghin_comp304_lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.Models.Movie;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.CustomerViewModel;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.MovieViewModel;

import java.security.spec.ECField;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private CustomerViewModel customerViewModel;
    private TextView textView;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* DEBUG USE */
        textView = findViewById(R.id.debug_txtView);
        String temp = "";
        try {
            // Observed stored data
            //movieViewModel.getAllMovies();
            for (Customer customer : customerViewModel.getAllCustomers()) {
                temp += customer.getCustId();
            }
        } catch (Exception e){
            temp = e.getMessage();
        }
        textView.setText(temp);


    }

    // Authentication method
    private boolean authentication(){
        //If matches id/password stored in db
        if (true)
            return true;
        else
            return false;

    }
}