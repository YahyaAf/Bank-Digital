package services;

import models.Account;
import repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountService {
    public final AccountRepository accountRepository;
    public final UUID currentUserId;

    public AccountService(AccountRepository accountRepository, UUID currentUserId){
        this.accountRepository=accountRepository;
        this.currentUserId=currentUserId;
    }

    public Account createAccount(){
        Account account = new Account(currentUserId);
        accountRepository.save(account);
        System.out.println("Your account has been created de ID : "+account.getAccountId());
        return account;
    }

    public List<Account> listUserAccounts(){
        return accountRepository.findByUserId(currentUserId);
    }

    public void closeAccount(String accountId){
        Optional<Account> accountOpt = accountRepository.findById(accountId);

        if(accountOpt.isEmpty()){
            System.out.println("Account with ID "+ accountId+" not found");
            return;
        }

        Account account = accountOpt.get();

        if(!account.getOwnerUserId().equals(currentUserId)){
            System.out.println("You are not the owner of this account");
            return;
        }

        if(account.getBalance().compareTo(BigDecimal.ZERO)>0){
            System.out.println("Your account balance is greater than zero");
            return;
        }
        account.deactivate();
        accountRepository.save(account);
        System.out.println("Account with ID "+ accountId+" closed");
    }

    public BigDecimal getBalance(String accountId){
        Optional<Account> accountOpt = accountRepository.findById(accountId);

        if(accountOpt.isEmpty()){
            System.out.println("Account with ID "+accountId+" not found");
            return null;
        }

        Account account = accountOpt.get();

        if(!account.getOwnerUserId().equals(currentUserId)){
            System.out.println("You are not the owner of this account");
            return null;
        }

        return account.getBalance();
    }

}
