package services;

import models.Account;
import models.Transaction;
import repositories.AccountRepository;
import repositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AuthService authService;

    public TransactionService(TransactionRepository TransactionRepository, AccountRepository accountRepository, AuthService authService) {
        this.transactionRepository = TransactionRepository;
        this.accountRepository = accountRepository;
        this.authService = authService;
    }
    public void deposit(String accountId, BigDecimal amount, String description){
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if(accountOpt.isEmpty()){
            System.out.println("Account not found");
            return;
        }
        Account account = accountOpt.get();
        if(!account.getOwnerUserId().equals(authService.getCurrentUser().getId())){
            System.out.println("You are not the owner of this account");
            return;
        }
        if(!account.isActive()){
            System.out.println("Account not active is closed");
            return;
        }
        if(amount.scale() > 2){
            System.out.println("Max 2 decimal places allowed");
            return;
        }
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        Transaction tx = new Transaction(accountId, Transaction.transactionType.DEPOSIT, amount, null, description);
        transactionRepository.save(tx);

        System.out.println("Deposited "+amount+" to this account" + account.getAccountId());
    }

    public void withdraw(String accountId, BigDecimal amount, String description){
        Optional<Account> accountOpt = accountRepository.findById(accountId);

        if(accountOpt.isEmpty()){
            System.out.println("Account not found");
            return;
        }
        Account account = accountOpt.get();
        if(!account.getOwnerUserId().equals(authService.getCurrentUser().getId())){
            System.out.println("You are not the owner of this account");
            return;
        }

        if(!account.isActive()){
            System.out.println("Account not active is closed");
            return;
        }

        if(account.getBalance().compareTo(amount) < 0){
            System.out.println("Insufficient funds");
            return;
        }
        if(amount.compareTo(BigDecimal.ZERO) <= 0 || amount.scale() > 2){
            System.out.println("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction tx = new Transaction(accountId, Transaction.transactionType.WITHDRAW, amount, null, description);
        transactionRepository.save(tx);
        System.out.println("Withdrawn "+amount+" from this account" + account.getAccountId());

    }

    public void transfer(String fromAccountId, String toAccountId, BigDecimal amount, String description){
        Optional<Account> fromAccountOpt = accountRepository.findById(fromAccountId);
        Optional<Account> toAccountOpt = accountRepository.findById(toAccountId);

        if(fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()){
            System.out.println("Account not found");
            return;
        }

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        if(!fromAccount.getOwnerUserId().equals(authService.getCurrentUser().getId())){
            System.out.println("You are not the owner of this account");
            return;
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0 || amount.scale() > 2){
            System.out.println("Insufficient funds");
            return;
        }
        if(fromAccount.getBalance().compareTo(amount) < 0){
            System.out.println("Insufficient funds");
            return;
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction txout = new Transaction(fromAccountId,Transaction.transactionType.TRANSFEROUT, amount, toAccountId, description);
        Transaction txIn = new Transaction(toAccountId,Transaction.transactionType.TRANSFERIN, amount, fromAccountId, description);

        transactionRepository.save(txout);
        transactionRepository.save(txIn);
        System.out.println("Transfer "+amount+" from this account" + fromAccount.getAccountId()+"to this account"+toAccount.getAccountId());

    }

    public void history(String accountId){
        List<Transaction> history = transactionRepository.findByAccountId(accountId);
        if(history.isEmpty()){
            System.out.println("transactions not found");
            return;
        }
        System.out.println("\n===== Historique du compte " + accountId + " =====");
        for (Transaction tx : history) {
            System.out.println(tx);
        }
    }



}
