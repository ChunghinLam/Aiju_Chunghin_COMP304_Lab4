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

        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        /* DEBUG USE */
        textView = findViewById(R.id.debug_txtView);
        try {
        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                String output = "";
                for (Customer customer : customers) {
                    output += customer.getEmail() + ":" +
                            customer.getPassword() +"\n";
                }
                textView.setText(output);
            }
        });}
        catch (Exception e){
            textView.setText(e.getMessage());
        }


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
// TODO: Add modify/delete account function