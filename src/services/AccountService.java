package services;

import models.Account;
import repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountService {
    public final AccountRepository accountRepository;
    private final AuthService authService;

    public AccountService(AccountRepository accountRepository, AuthService authService){
        this.accountRepository=accountRepository;
        this.authService=authService;
    }

    public void createAccount(){
        Account account = new Account(authService.getCurrentUser().getId());
        accountRepository.save(account);
        System.out.println("Your account has been created de ID : "+account.getAccountId());
    }

    public void listUserAccounts(){
        List<Account> accountsList = accountRepository.findByUserId(authService.getCurrentUser().getId());
        if (accountsList.isEmpty()) {
            System.out.println("Account list is empty");
        } else {
            System.out.println("===== Comptes Of User " + authService.getCurrentUser().getId() + " =====");
            for (Account account : accountsList) {
                System.out.println("ID: " + account.getAccountId()
                        + " | Balance: " + account.getBalance()
                        + " | Owner: " + account.getOwnerUserId()
                        + " | activation:  " + account.isActive());
            }
        }
    }

    public void closeAccount(String accountId){
        Optional<Account> accountOpt = accountRepository.findById(accountId);

        if(accountOpt.isEmpty()){
            System.out.println("Account with ID "+ accountId+" not found");
            return;
        }

        Account account = accountOpt.get();

        if(!account.getOwnerUserId().equals(authService.getCurrentUser().getId())){
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

    public void getBalance(String accountId){
        Optional<Account> accountOpt = accountRepository.findById(accountId);

        if(accountOpt.isEmpty()){
            System.out.println("Account with ID "+accountId+" not found");
            return;
        }

        Account account = accountOpt.get();

        if(!account.getOwnerUserId().equals(authService.getCurrentUser().getId())){
            System.out.println("You are not the owner of this account");
            return;
        }

        System.out.println("Voila balance of this account "+account.getBalance());
    }

}
