package models;

public class User {
    private String name;
    private String email;
    private String address;
    private String password;


    public User(){}

    public User(String name, String email, String password, String address){
        this.name = name;
        this.email= email;
        this.password=password;
        this.address=address;
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
        this.email = email;
    }

    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
