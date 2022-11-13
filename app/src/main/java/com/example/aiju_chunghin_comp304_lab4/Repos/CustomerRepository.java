package com.example.aiju_chunghin_comp304_lab4.Repos;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aiju_chunghin_comp304_lab4.DAOs.CustomerDAO;
import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.MovieDatabase;

import java.util.List;

public class CustomerRepository {
    private final CustomerDAO customerDAO;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Customer>> customerList;

    public CustomerRepository(Context context){
        MovieDatabase db = MovieDatabase.getInstance(context);
        customerDAO = db.customerDAO();
        customerList = customerDAO.getCustomerList();
    }

    // Read
    public LiveData<List<Customer>> getAllCustomers(){
        return customerList;
    }
    // Create
    public LiveData<Integer> getInsertResult(){
        return insertResult;
    }
    public void insert(Customer customer){
        customerDAO.insertCustomer(customer);
    }
    // Update
    public void update(Customer customer){
        customerDAO.updateCustomer(customer);
    }
    // Delete
    public void delete(Customer customer){
        customerDAO.deleteCustomer(customer);
    }

    public Customer getCustomerById(Integer custId) { return customerDAO.getCustomerById(custId); }
}
