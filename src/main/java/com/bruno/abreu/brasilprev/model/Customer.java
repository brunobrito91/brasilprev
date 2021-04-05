package com.bruno.abreu.brasilprev.model;

import io.swagger.annotations.ApiModelProperty;
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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Customer name")
    @NotEmpty(message = "Name must be filled")
    private String name;

    @ApiModelProperty(value = "Customer CPF")
    @NotEmpty(message = "CPF must be filled")
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Customer address")
    private Address address;
}
