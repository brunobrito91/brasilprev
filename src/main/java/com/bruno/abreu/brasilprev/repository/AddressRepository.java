package com.bruno.abreu.brasilprev.repository;

import com.bruno.abreu.brasilprev.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
