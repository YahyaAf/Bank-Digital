import models.Account;
import repositories.AccountRepository;
import repositories.UserRepository;
import repositories.memoires.InMemoryAccountRepository;
import repositories.memoires.InMemoryUserRepository;
import services.AccountService;
import services.AuthService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        UserRepository userRepository = new InMemoryUserRepository();
        AuthService authService = new AuthService(userRepository);
        AccountRepository accountRepository = new InMemoryAccountRepository();
        AccountService accountService = new AccountService(accountRepository,authService);
        int choix;

        do {
            System.out.println("\n==========Acceuil==========");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("0. Exit");
            choix = reader.nextInt();
            switch(choix){
                case 1:
                    System.out.println("Enter name : ");
                    String name =  reader.next();
                    System.out.println("Enter email : ");
                    String email =  reader.next();
                    System.out.println("Enter address : ");
                    String address = reader.next();
                    System.out.println("Enter password :");
                    String password = reader.next();
                    authService.register(name,email,password,address);
                    break;
                case 2:
                    System.out.println("Enter email :");
                    String loginEmail = reader.next();
                    System.out.println("Enter password :");
                    String loginPassword = reader.next();
                    if(authService.login(loginEmail,loginPassword)){
                        int choix2;
                        do {
                            System.out.println("1. Show profile");
                            System.out.println("2. Change password");
                            System.out.println("3. Change email");
                            System.out.println("4. Change address");
                            System.out.println("5. Create account");
                            System.out.println("6. list my accounts");
                            System.out.println("7. Balance of account(By id of account)");
                            System.out.println("8. Close account(By id of account)");
                            System.out.println("20. logout");
                            choix2 = reader.nextInt();
                            switch (choix2){
                                case 1:
                                    authService.showProfile();
                                    break;
                                case 2:
                                    System.out.println("Enter old password : ");
                                    String oldPassword = reader.next();
                                    System.out.println("Enter new password : ");
                                    String newPassword = reader.next();
                                    authService.changePassword(oldPassword,newPassword);
                                    break;
                                case 3:
                                    System.out.println("Enter new email : ");
                                    String newEmail = reader.next();
                                    authService.updateEmail(newEmail);
                                    break;
                                case 4:
                                    System.out.println("Enter new address : ");
                                    String newAddress = reader.next();
                                    authService.updateAddress(newAddress);
                                    break;
                                case 5:
                                    accountService.createAccount();
                                    break;
                                case 6:
                                    accountService.listUserAccounts();
                                    break;
                                case 7:
                                    System.out.println("Please enter your account number");
                                    String accountNumber = reader.next().trim();
                                    accountService.getBalance(accountNumber);
                                    break;
                                case 8:
                                    System.out.println("Please enter your account number");
                                    String accountClose =  reader.next().trim();
                                    accountService.closeAccount(accountClose);
                                    break;
                                default:
                                    System.out.println("Wrong choice");
                            }
                        }while(choix2 != 20);
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