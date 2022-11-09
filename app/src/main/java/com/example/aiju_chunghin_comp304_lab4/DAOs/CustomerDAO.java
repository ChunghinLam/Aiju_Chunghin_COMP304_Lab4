package com.example.aiju_chunghin_comp304_lab4.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;

import java.util.List;

@Dao
public interface CustomerDAO {
    @Query("Select * from Customer")
    List<Customer> getCustomerList();

    @Insert
    public void insertCustomer(Customer customer);

    @Update
    public void updateCustomer(Customer customer);

    @Delete
    public void deleteCustomer(Customer customer);
}
