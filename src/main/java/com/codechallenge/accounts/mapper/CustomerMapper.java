package com.codechallenge.accounts.mapper;

import com.codechallenge.accounts.dto.CustomerDto;
import com.codechallenge.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerDto toCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer toEntityCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
