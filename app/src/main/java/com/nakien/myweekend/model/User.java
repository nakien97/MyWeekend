package com.nakien.myweekend.model;

public class User {
    private String email;
    private String name;
    private String password;
    private String phone;

    public User() {
        //Firebase configure
    }

    public User(String email, String name, String password, String phone){
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public User(User user){
        this.email = user.email;
        this.name = user.name;
        this.password = user.password;
        this.phone = user.phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
