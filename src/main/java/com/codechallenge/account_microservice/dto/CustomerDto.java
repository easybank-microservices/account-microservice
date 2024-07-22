package com.codechallenge.account_microservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "{customer.name.notEmpty}")
    @Size(min = 5, max = 30, message = "{customer.name.size}")
    private String name;

    @NotEmpty(message = "{customer.email.notEmpty}")
    @Email(message = "{customer.email.valid}")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "{customer.mobileNumber.pattern}")
    private String mobileNumber;

    private AccountDto accountsDto;
}
