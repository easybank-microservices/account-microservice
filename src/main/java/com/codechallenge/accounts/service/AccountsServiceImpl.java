package com.codechallenge.accounts.service;

import com.codechallenge.accounts.constants.AccountsConstants;
import com.codechallenge.accounts.dto.AccountsDto;
import com.codechallenge.accounts.dto.CustomerDto;
import com.codechallenge.accounts.entity.Accounts;
import com.codechallenge.accounts.entity.Customer;
import com.codechallenge.accounts.exception.CustomerAlreadyExistsException;
import com.codechallenge.accounts.exception.ResourceNotFoundException;
import com.codechallenge.accounts.mapper.AccountsMapper;
import com.codechallenge.accounts.mapper.CustomerMapper;
import com.codechallenge.accounts.repository.AccountsRepository;
import com.codechallenge.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService{

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = (Customer) customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .map(existingCustomer -> {
                    throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                            + customerDto.getMobileNumber());
                })
                .orElseGet(() -> CustomerMapper.toEntityCustomer(customerDto, new Customer()));

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto getAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.toCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.toAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        return false;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        return false;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
