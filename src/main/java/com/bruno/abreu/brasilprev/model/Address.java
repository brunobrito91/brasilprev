package com.bruno.abreu.brasilprev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Street must be filled")
    private String street;
    @NotEmpty(message = "Number must be filled")
    private String number;
    @NotEmpty(message = "District must be filled")
    private String district;
    @NotEmpty(message = "City must be filled")
    private String city;
    @NotEmpty(message = "State must be filled")
    private String state;
    @NotEmpty(message = "Country must be filled")
    private String country;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Customer customer;

}
