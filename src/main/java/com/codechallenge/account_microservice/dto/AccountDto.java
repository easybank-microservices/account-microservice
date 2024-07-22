package com.codechallenge.account_microservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDto {

    @NotEmpty(message = "{account.accountNumber.notEmpty}")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "{account.accountNumber.pattern}")
    private String accountNumber;

    @NotEmpty(message = "{account.accountType.notEmpty}")
    private String accountType;

    @NotEmpty(message = "{account.branchAddress.notEmpty}")
    private String branchAddress;
}
