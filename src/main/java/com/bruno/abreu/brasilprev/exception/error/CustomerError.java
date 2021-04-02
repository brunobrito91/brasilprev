package com.bruno.abreu.brasilprev.exception.error;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class CustomerError {
    private String errorMessage;
    private Timestamp timestamp;
}
