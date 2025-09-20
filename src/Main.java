import helpers.InputHelper;
import models.Account;
import repositories.AccountRepository;
import repositories.TransactionRepository;
import repositories.UserRepository;
import repositories.memoires.InMemoryAccountRepository;
import repositories.memoires.InMemoryTransactionRepository;
import repositories.memoires.InMemoryUserRepository;
import services.AccountService;
import services.AuthService;
import services.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        UserRepository userRepository = new InMemoryUserRepository();
        AuthService authService = new AuthService(userRepository);
        AccountRepository accountRepository = new InMemoryAccountRepository();
        AccountService accountService = new AccountService(accountRepository,authService);
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        TransactionService transactionService = new TransactionService(transactionRepository,accountRepository,authService);
        int choix;

        do {
            System.out.println("\n==========Acceuil==========");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("0. Exit");
            choix = reader.nextInt();
            switch(choix){
                case 1:
                    String name =  InputHelper.readInput("Enter name : ");
                    String email =  InputHelper.readInput("Enter email : ");
                    String address = InputHelper.readInput("Enter address : ");
                    String password = InputHelper.readInput("Enter password : ");
                    authService.register(name,email,password,address);
                    break;
                case 2:
                    String loginEmail = InputHelper.readInput("Enter email : ");
                    String loginPassword = InputHelper.readInput("Enter password : ");
                    if(authService.login(loginEmail,loginPassword)){
                        int choix2;
                        do {
                            System.out.println("1. Profile User");
                            System.out.println("2. Create account");
                            System.out.println("3. list my accounts");
                            System.out.println("4. Balance of account(By id of account)");
                            System.out.println("5. Close account(By id of account)");
                            System.out.println("6. Deposit");
                            System.out.println("7. Withdraw");
                            System.out.println("8. Transfer");
                            System.out.println("9. Historiques");
                            System.out.println("10. logout");
                            choix2 = reader.nextInt();
                            switch (choix2){
                                case 1:
                                    int choix3;
                                    do {
                                        System.out.println("1. Show profile");
                                        System.out.println("2. Change password");
                                        System.out.println("3. Change email");
                                        System.out.println("4. Change address");
                                        System.out.println("0. Exit");
                                        choix3 = reader.nextInt();
                                        switch (choix3){
                                            case 1:
                                                authService.showProfile();
                                                break;
                                            case 2:
                                                String oldPassword = InputHelper.readInput("Enter old password : ");
                                                String newPassword = InputHelper.readInput("Enter new password : ");
                                                authService.changePassword(oldPassword,newPassword);
                                                break;
                                            case 3:
                                                String newEmail = InputHelper.readInput("Enter new email : ");
                                                authService.updateEmail(newEmail);
                                                break;
                                            case 4:
                                                String newAddress = InputHelper.readInput("Enter new address : ");
                                                authService.updateAddress(newAddress);
                                                break;
                                            case 0:
                                                System.out.println("");
                                                break;
                                            default:
                                                System.out.println("Wrong choix");
                                        }
                                    }while (choix3 != 0);
                                    break;
                                case 2:
                                    accountService.createAccount();
                                    break;
                                case 3:
                                    accountService.listUserAccounts();
                                    break;
                                case 4:
                                    String accountNumber = InputHelper.readInput("Please enter your account number");
                                    accountService.getBalance(accountNumber);
                                    break;
                                case 5:
                                    System.out.println("Please enter your account number : ");
                                    String accountClose =  reader.next().trim();
                                    accountService.closeAccount(accountClose);
                                    break;
                                case 6:
                                    String accountDeposit = InputHelper.readInput("Please enter your account number : ");
                                    BigDecimal depositAmount = InputHelper.readBigDecimal("Please enter the amount : ");
                                    String depositDescription = InputHelper.readInput("Please enter description : ");
                                    transactionService.deposit(accountDeposit, depositAmount, depositDescription);
                                    break;
                                case 7:
                                    String accountWithdraw = InputHelper.readInput("Please enter your account number : ");
                                    BigDecimal withdrawAmount = InputHelper.readBigDecimal("Please enter the amount : ");
                                    String withdrawDescription = InputHelper.readInput("Please enter description : ");
                                    transactionService.withdraw(accountWithdraw, withdrawAmount, withdrawDescription);
                                    break;
                                case 8:
                                    String accountFrom = InputHelper.readInput("Please enter the from account number : ");
                                    String accountTo = InputHelper.readInput("Please enter the to account number : ");
                                    BigDecimal accountBalance = InputHelper.readBigDecimal("Please enter the amount : ");
                                    String accountBalanceDescription = InputHelper.readInput("Please enter description : ");
                                    transactionService.transfer(accountFrom, accountTo, accountBalance, accountBalanceDescription);
                                    break;
                                case 9:
                                    String accountHistory = InputHelper.readInput("Please enter your account number : ");
                                    transactionService.history(accountHistory);
                                    break;
                                case 10:
                                    authService.logout();
                                    break;
                                default:
                                    System.out.println("Wrong choice");
                            }
                        }while(choix2 != 10);
                    }
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Wrong choice");
            }

        }while(choix != 0);

    }
}