package com.example.aiju_chunghin_comp304_lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.ViewModels.CustomerViewModel;

public class MyAccountActivity extends AppCompatActivity {
    private CustomerViewModel customerViewModel;
    private TextView id_txtView, pass_txtView, confPass_txtView, fname_txtView, lname_txtView, addr_txtView, city_txtView, postal_txtView;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        id_txtView = findViewById(R.id.accAct_id);
        pass_txtView = findViewById(R.id.accAct_password);
        confPass_txtView = findViewById(R.id.accAct_confPassword);
        fname_txtView = findViewById(R.id.accAct_fname);
        lname_txtView = findViewById(R.id.accAct_lname);
        addr_txtView = findViewById(R.id.accAct_addr);
        city_txtView = findViewById(R.id.accAct_city);
        postal_txtView = findViewById(R.id.accAct_postal);

        // Initialize Customer View Model
        try{
            customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);}
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "model error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Display current information
        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
        customer = new Customer(
                pref.getInt("user_custid", 0),
                pref.getString("user_password", null), pref.getString("user_password", null),
                pref.getString("user_fname", null), pref.getString("user_lname", null),
                pref.getString("user_addr", null), pref.getString("user_city", null),
                pref.getString("user_post", null), pref.getString("user_email", null)
                );

        /* INNOVATION */
        id_txtView.setText(customer.getEmail());
        pass_txtView.setText(customer.getPassword());
        fname_txtView.setText(customer.getFirstname());
        lname_txtView.setText(customer.getLastname());
        addr_txtView.setText(customer.getAddress());
        city_txtView.setText(customer.getCity());
        postal_txtView.setText(customer.getPostalCode());

        // Manage Delete button
        Button deleteBtn = findViewById(R.id.accAct_del_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean filled = isFilled();
                if (filled)
                    if (customer.getPassword().equals(confPass_txtView.getText().toString())) {
                        deleteCustomer(customer);

                        Toast.makeText(getApplicationContext(), R.string.good_bye_msg, Toast.LENGTH_LONG).show();
                        backMain();
                    }
                    else
                        Toast.makeText(getApplicationContext(), R.string.dif_pass_msg, Toast.LENGTH_LONG).show();
            }
        });
        // Manage Confirm button
        Button confirmBtn = findViewById(R.id.accAct_yes_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean filled = isFilled();
                if (filled)
                    if (customer.getPassword().equals(confPass_txtView.getText().toString())) {
                        if (RegistrationActivity.checkValid(id_txtView.getText().toString(),
                                postal_txtView.getText().toString())) {
                            try {
                                updateCustomer();

                                backMain();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), R.string.invalid_input_msg,
                                    Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), R.string.dif_pass_msg, Toast.LENGTH_LONG).show();
            }
        });

        // Manage Clear button
        Button clearBtn = findViewById(R.id.accAct_clear_btn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_txtView.setText(null);
                pass_txtView.setText(null);
                //confPass_txtView.setText(null);
                fname_txtView.setText(null);
                lname_txtView.setText(null);
                addr_txtView.setText(null);
                city_txtView.setText(null);
                postal_txtView.setText(null);
            }
        });
    }

    private void updateCustomer(){
        customer = new Customer(
                (int) customer.getCustId(),
                pass_txtView.getText().toString(), pass_txtView.getText().toString(),
                fname_txtView.getText().toString(), lname_txtView.getText().toString(),
                addr_txtView.getText().toString(), city_txtView.getText().toString(), postal_txtView.getText().toString(),
                id_txtView.getText().toString()
        );
        if (customer != null) {
            try {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        customerViewModel.update(customer);
                    }
                });
                SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = pref.edit();
                prefEditor.putString("user_email", customer.getEmail());
                prefEditor.putString("user_password", customer.getPassword());
                prefEditor.putString("user_fname", customer.getFirstname());
                prefEditor.putString("user_lname", customer.getLastname());
                prefEditor.putString("user_addr", customer.getAddress());
                prefEditor.putString("user_city", customer.getCity());
                prefEditor.putString("user_post", customer.getPostalCode());
                prefEditor.putInt("user_custid", customer.getCustId());
                prefEditor.commit();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "insert error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "customer variable is NULL", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteCustomer(Customer customer){
                AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                customerViewModel.delete(customer);
            }
        });
        SharedPreferences pref = getSharedPreferences("accountPref", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.remove("user_email");
        prefEditor.remove("user_password");
        prefEditor.remove("user_fname");
        prefEditor.remove("user_lname");
        prefEditor.remove("user_addr");
        prefEditor.remove("user_city");
        prefEditor.remove("user_post");
        prefEditor.remove("user_custid");
        prefEditor.commit();
    }

    private void backMain(){
        finish();
        Intent move = new Intent(MyAccountActivity.this, MainActivity.class);
        startActivity(move);
    }

    private boolean isFilled(){
        if (
                (id_txtView.getText().toString().isEmpty()) ||
                (addr_txtView.getText().toString().isEmpty()) ||
                (city_txtView.getText().toString().isEmpty()) ||
                (pass_txtView.getText().toString().isEmpty()) ||
                (confPass_txtView.getText().toString().isEmpty()) ||
                (fname_txtView.getText().toString().isEmpty()) ||
                (lname_txtView.getText().toString().isEmpty()) ||
                (postal_txtView.getText().toString().isEmpty())
        ){
            Toast.makeText(getApplicationContext(), R.string.unfilled_msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }
}