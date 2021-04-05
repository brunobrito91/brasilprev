package com.bruno.abreu.brasilprev.service;

import com.bruno.abreu.brasilprev.exception.CustomerNotFoundException;
import com.bruno.abreu.brasilprev.model.Customer;
import com.bruno.abreu.brasilprev.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CrudService<Customer>{

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer read(Long id) {
        return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer update(Customer customer) {
        Customer customerInDb = read(customer.getId());
        customer.getAddress().setId(customerInDb.getAddress().getId());

        return create(customer);
    }

    @Override
    public void delete(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
