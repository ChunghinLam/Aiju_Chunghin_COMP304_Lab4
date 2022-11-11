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
    private TextView textView;
    Customer customer;

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
    private void authentication(){
        // retrieve input
        TextView email = findViewById(R.id.logAct_id);
        TextView password = findViewById(R.id.logAct_pass);

        // check if input matches the data stored in customerDB
        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            Boolean email_bool;
            Boolean password_bool;
            @Override
            public void onChanged(List<Customer> customers) {
                email_bool = false;
                password_bool = false;
                for (Customer customer : customers){
                    //Boolean text = customer.getEmail().equals(email.getText().toString());//customer.getEmail().equals("aaa");
                    //Toast.makeText(getApplicationContext(), text.toString(), Toast.LENGTH_SHORT).show();
                    if (
                            //true // worked
                            //"aaa" == "aaa" // worked
                            //customer.getEmail() == "aaa" // not work
                            //email.getText().toString()=="aaa" // not work
                            //customer.getEmail() == email.getText().toString()
                            customer.getEmail().equals(email.getText().toString())
                            ){
                        email_bool = true;
                    }
                    if (
                            //true // worked
                            //"aaa" == "aaa" // worked
                            //customer.getPassword() == "aaa"
                            //password.getText().toString() == "aaa"
                            //customer.getPassword() == password.getText().toString())
                            customer.getPassword().equals(password.getText().toString())
                            )
                        password_bool = true;
                    if (email_bool && password_bool){
                        Toast.makeText(getApplicationContext(), "Passed!"+customer.getCustId(), Toast.LENGTH_LONG).show();
                        // Set who's current account in Shared Preference
                        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
                        SharedPreferences.Editor prefEditer = pref.edit();
                        prefEditer.putString("user", customer.getEmail());
                        prefEditer.commit();

                        // Move to Main Activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }else{
                     Toast.makeText(getApplicationContext(), "wrong!", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
        //If matches id/password stored in db
//        if ((email_bool && password_bool))
//            return true;
//        else
//            return false;

    }
}
// TODO: Add modify/delete account function
// TODO: Decline duplicate email account