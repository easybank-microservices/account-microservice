package com.codechallenge.accounts.service;

import static org.junit.jupiter.api.Assertions.*;

import com.codechallenge.accounts.dto.AccountsDto;
import com.codechallenge.accounts.dto.CustomerDto;
import com.codechallenge.accounts.entity.Accounts;
import com.codechallenge.accounts.entity.Customer;
import com.codechallenge.accounts.exception.CustomerAlreadyExistsException;
import com.codechallenge.accounts.exception.ResourceNotFoundException;
import com.codechallenge.accounts.repository.AccountsRepository;
import com.codechallenge.accounts.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountsServiceImplTest {

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountsServiceImpl serviceUnderTest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAccount_WhenCustomerDoesNotExist_ShouldCreateNewAccount() {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber("1234567890");

        when(customerRepository.findByMobileNumber(customerDto.getMobileNumber()))
                .thenReturn(Optional.empty());

        Customer customer = new Customer();
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        // Act
        serviceUnderTest.createAccount(customerDto);

        // Assert
        assertNotNull(accountsRepository.findByCustomerId(customer.getCustomerId()));
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(accountsRepository, times(1)).save(any(Accounts.class));
    }

    @Test
    public void createAccount_WhenCustomerAlreadyExists_ShouldThrowException() {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber("1234567890");

        Customer existingCustomer = new Customer();
        when(customerRepository.findByMobileNumber(customerDto.getMobileNumber()))
                .thenReturn(Optional.of(existingCustomer));

        // Act & Assert
        assertThrows(CustomerAlreadyExistsException.class, () -> serviceUnderTest.createAccount(customerDto));
        verify(customerRepository, never()).save(any(Customer.class));
        verify(accountsRepository, never()).save(any(Accounts.class));
    }

    @Test
    public void getAccount_WhenCustomerAndAccountExist_ShouldReturnCustomerDto() {
        // Arrange
        String mobileNumber = "1234567890";

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        when(customerRepository.findByMobileNumber(mobileNumber))
                .thenReturn(Optional.of(customer));

        Accounts account = new Accounts();
        when(accountsRepository.findByCustomerId(customer.getCustomerId()))
                .thenReturn(Optional.of(account));

        CustomerDto expectedCustomerDto = new CustomerDto();
        AccountsDto expectedAccountsDto = new AccountsDto();
        expectedCustomerDto.setAccountsDto(expectedAccountsDto);

        // Act
        CustomerDto actualCustomerDto = serviceUnderTest.getAccount(mobileNumber);

        // Assert
        assertNotNull(actualCustomerDto);
    }

    @Test
    public void getAccount_WhenCustomerDoesNotExist_ShouldThrowException() {
        // Arrange
        String mobileNumber = "1234567890";

        when(customerRepository.findByMobileNumber(mobileNumber))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.getAccount(mobileNumber));
        verify(accountsRepository, never()).findByCustomerId(anyLong());
    }

    @Test
    public void getAccount_WhenAccountDoesNotExist_ShouldThrowException() {
        // Arrange
        String mobileNumber = "1234567890";

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        when(customerRepository.findByMobileNumber(mobileNumber))
                .thenReturn(Optional.of(customer));

        when(accountsRepository.findByCustomerId(customer.getCustomerId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> serviceUnderTest.getAccount(mobileNumber));
    }
}
