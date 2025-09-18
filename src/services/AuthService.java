package services;

import models.User;
import repositories.UserRepository;

import java.util.Optional;

public class AuthService {
    private final UserRepository userRepository;
    private User currentUser = null;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void register(String name, String email, String password, String address){
        if(userRepository.existsByEmail(email)){
            System.out.println("Email Already Exists");
            return;
        }
        if(password == null || password.length() < 6){
            System.out.println("Password too short");
            return;
        }
        if(email == null || !email.contains("@")){
            System.out.println("Invalid Email");
            return;
        }

        User user = new User(name, email, password, address);
        userRepository.save(user);
        System.out.println("User "+user.getName()+" has been registered successfully");
    }

    public boolean login(String email, String password){
        if(this.currentUser != null){
            System.out.println("User is already logged in !");
            return false;
        }
        Optional<User> userOpt =  userRepository.findByEmail(email);
        if(userOpt.isEmpty()){
            System.out.println("Email is invalid");
            return false;
        }
        User user = userOpt.get();
        if(!password.equals(user.getPassword())){
            System.out.println("Password is invalid");
            return false;
        }

        currentUser = user;
        System.out.println("User "+ user.getName() +" logged in successfully");

        return true;
    }

    public void logout(){
        if(currentUser == null){
            System.out.println("User is not logged in");
            return;
        }
        System.out.println("User "+ currentUser.getName() +" logged out");
        currentUser = null;
    }

    public void updateEmail(String newEmail){
        if(currentUser == null){
            System.out.println("User is not logged in");
            return;
        }
        if(!currentUser.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)){
            System.out.println("Email already exists");
            return;
        }
        currentUser.setEmail(newEmail);
        userRepository.save(currentUser);
        System.out.println("User "+currentUser.getName()+" has been updated email successfully");
    }

    public void updateAddress(String newAddress){
        if(currentUser == null){
            System.out.println("User is not logged in");
            return;
        }
        currentUser.setAddress(newAddress);
        userRepository.save(currentUser);
        System.out.println("User "+currentUser.getName()+" has been updated address successfully");
    }

    public void changePassword(String oldPassword, String newPassword){
        if(this.currentUser == null){
            System.out.println("User is not logged in !");
        }

        if(!this.currentUser.getPassword().equals(oldPassword)){
            System.out.println("Old password doesn't match");
            return;
        }
        if(newPassword.length() < 6){
            System.out.println("Password too short");
            return;
        }
        currentUser.setPassword(newPassword);
        userRepository.save(currentUser);
        System.out.println("Password changed successfully");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void showProfile(){
        if(this.currentUser == null){
            System.out.println("User is not logged in");
            return;
        }
        System.out.println("===== Profil Utilisateur =====");
        System.out.println("Name : " + currentUser.getName());
        System.out.println("Email : " + currentUser.getEmail());
        System.out.println("Adresse : " + currentUser.getAddress());
        System.out.println("==============================");
    }


    
}
