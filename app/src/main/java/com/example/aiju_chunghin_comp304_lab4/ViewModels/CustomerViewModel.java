package com.example.aiju_chunghin_comp304_lab4.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aiju_chunghin_comp304_lab4.Models.Customer;
import com.example.aiju_chunghin_comp304_lab4.Repos.CustomerRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {
    private CustomerRepository customerRepository;
    private LiveData<Integer> insertResult;
    private LiveData<List<Customer>> allCustomers;

    public CustomerViewModel(@NotNull Application application) {
        super(application);

        customerRepository = new CustomerRepository(application);
        insertResult = customerRepository.getInsertResult();
        allCustomers = customerRepository.getAllCustomers();
    }

    // Create
    public void insert(Customer customer){
        customerRepository.insert(customer);
    }
    public LiveData<Integer> getInsertResult(){
        return insertResult;
    }
    // Retrieve
    public LiveData<List<Customer>> getAllCustomers(){
        return allCustomers;
    }
    // Update
    public void update(Customer customer){
        customerRepository.update(customer);
    }
    // Delete
    public void delete(Customer customer){
        customerRepository.delete(customer);
    }

    public Customer getCustomerById(Integer custId) { return customerRepository.getCustomerById(custId); }
}
