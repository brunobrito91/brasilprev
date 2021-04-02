package com.bruno.abreu.brasilprev.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException() {
        super("Customer not found!");
    }
}
