package com.example.aiju_chunghin_comp304_lab4;

import androidx.room.*;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;

import java.util.List;

@Dao
public interface CustomerDAO {
    @Query("Select * from Customer")
    List<Customer> getCustomerList();

    @Insert
    public void insertCustomer();

    @Update
    public void updateCustomer();

    @Delete
    public void deleteCustomer();
}
