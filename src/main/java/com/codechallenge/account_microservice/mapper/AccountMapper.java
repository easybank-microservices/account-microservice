package com.codechallenge.account_microservice.mapper;

import com.codechallenge.account_microservice.dto.AccountDto;
import com.codechallenge.account_microservice.entity.Accounts;

public class AccountMapper {

    public static AccountDto mapToAccountsDto(Accounts accounts, AccountDto accountDto) {
        accountDto.setAccountNumber(String.valueOf(accounts.getAccountNumber()));
        accountDto.setAccountType(accounts.getAccountType());
        accountDto.setBranchAddress(accounts.getBranchAddress());
        return accountDto;
    }

    public static Accounts mapToAccounts(AccountDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(Long.valueOf(accountsDto.getAccountNumber()));
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
