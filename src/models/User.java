package models;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String address;
    private String password;


    public User(){}

    public User( String name, String email, String password, String address){
        this.id = UUID.randomUUID(); ;
        this.name = name;
        setEmail(email);
        setPassword(password);
        this.address=address;
    }

    public UUID getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        if(email == null || !email.contains("@")){
            System.out.println("Invalid Email");
            return;
        }
        this.email = email;
    }

    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public void setPassword(String password){
        if(password == null || password.length()<6){
            System.out.println("Invalid Password");
            return;
        }
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}
