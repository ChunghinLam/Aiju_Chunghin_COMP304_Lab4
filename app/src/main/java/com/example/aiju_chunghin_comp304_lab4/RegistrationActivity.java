package com.example.aiju_chunghin_comp304_lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.CustomerViewModel;

import java.security.spec.ECField;

public class RegistrationActivity extends AppCompatActivity {
    private CustomerViewModel customerViewModel;
    private Button btnRegister;
    private TextView id_txtView, pass_txtView, confPass_txtView, fname_txtView, lname_txtView, addr_txtView, city_txtView, postal_txtView;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        id_txtView = findViewById(R.id.regAct_id);
        pass_txtView = findViewById(R.id.regAct_password);
        confPass_txtView = findViewById(R.id.regAct_confPassword);
        fname_txtView = findViewById(R.id.regAct_fname);
        lname_txtView = findViewById(R.id.regAct_lname);
        addr_txtView = findViewById(R.id.regAct_addr);
        city_txtView = findViewById(R.id.regAct_city);
        postal_txtView = findViewById(R.id.regAct_postal);
        try{
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);}
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "model error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        btnRegister = findViewById(R.id.regAct_register_btn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCustomer();
                //Toast.makeText(getApplicationContext(), "Clicked!", Toast.LENGTH_SHORT).show();

                Intent move_back = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(move_back);
            }
        });
    }

    private void insertCustomer(){
        customer = new Customer(
//                "aaa","aaa","aaa","aaa","aaa","aaa","aaa","aaa"
                pass_txtView.getText().toString(), confPass_txtView.getText().toString(),
                fname_txtView.getText().toString(), lname_txtView.getText().toString(),
                addr_txtView.getText().toString(), city_txtView.getText().toString(), postal_txtView.getText().toString(),
                id_txtView.getText().toString()
        );
        if (customer != null) {
            try {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        customerViewModel.insert(customer);
                    }
                });
                //Toast.makeText(getApplicationContext(), customer.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "insert error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "customer variable is NULL", Toast.LENGTH_LONG).show();
        }
    }
}