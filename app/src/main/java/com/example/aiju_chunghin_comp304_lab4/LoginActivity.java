package com.example.aiju_chunghin_comp304_lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        Button loginBtn = findViewById(R.id.logAct_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication();
            }
        });
    }

    // Authentication method
    private void authentication(){
        // retrieve input
        TextView email = findViewById(R.id.logAct_id);
        TextView password = findViewById(R.id.logAct_pass);

        // check if input matches the data stored in customerDB
        // --> should change to get customer by query
        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            Boolean email_bool;
            Boolean password_bool;
            @Override
            public void onChanged(List<Customer> customers) {
                email_bool = false;
                password_bool = false;
                for (Customer customer : customers){
                    if (customer.getEmail().equals(email.getText().toString()))
                        email_bool = true;
                    if (customer.getPassword().equals(password.getText().toString()))
                        password_bool = true;
                    if (email_bool && password_bool){
                        // Set who's current account in Shared Preference
                        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
                        SharedPreferences.Editor prefEditer = pref.edit();
                        prefEditer.putString("user_email", customer.getEmail());
                        prefEditer.putString("user_password", customer.getPassword());
                        prefEditer.putString("user_fname", customer.getFirstname());
                        prefEditer.putString("user_lname", customer.getLastname());
                        prefEditer.putString("user_addr", customer.getAddress());
                        prefEditer.putString("user_city", customer.getCity());
                        prefEditer.putString("user_post", customer.getPostalCode());
                        prefEditer.putInt("user_custid", customer.getCustId());
                        prefEditer.commit();

                        // Move to Main Activity
                        String login_ok = getApplicationContext().getString(R.string.msg_login_ok);
                        Toast.makeText(getApplicationContext(), String.format(login_ok, customer.getEmail()), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                if (!getSharedPreferences("accountPref", MODE_PRIVATE).contains("user_email"))
                    Toast.makeText(getApplicationContext(), R.string.faital_login, Toast.LENGTH_LONG).show();
            }
        });
    }
}