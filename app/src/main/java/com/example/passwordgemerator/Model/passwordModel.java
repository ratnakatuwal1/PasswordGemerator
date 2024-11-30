package com.example.passwordgemerator.Model;

public class passwordModel {
    private String appName, email, password;

    public passwordModel(){

    }

    public passwordModel(String appName, String email, String password) {
        this.appName = appName;
        this.email = email;
        this.password = password;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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
}
