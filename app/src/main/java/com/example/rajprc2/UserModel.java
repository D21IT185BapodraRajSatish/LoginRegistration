package com.example.rajprc2;

public class UserModel {
    String name;
    String password;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
