package com.example.aiju_chunghin_comp304_lab4.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;

import java.util.List;

@Dao
public interface CustomerDAO {
    @Query("Select * from Customer")
    LiveData<List<Customer>> getCustomerList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCustomer(Customer customer);

    @Update
    public void updateCustomer(Customer customer);

    @Delete
    public void deleteCustomer(Customer customer);

    @Query("Select * from Customer where custId == :custId")
    public Customer getCustomerById(Integer custId);
}
