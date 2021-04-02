package com.bruno.abreu.brasilprev.repository;

import com.bruno.abreu.brasilprev.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
