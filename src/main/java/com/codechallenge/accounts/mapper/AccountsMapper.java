package com.codechallenge.accounts.mapper;

import com.codechallenge.accounts.dto.AccountsDto;
import com.codechallenge.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto toAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(String.valueOf(accounts.getAccountNumber()));
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts toAccountsEntity(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(Long.valueOf(accountsDto.getAccountNumber()));
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
