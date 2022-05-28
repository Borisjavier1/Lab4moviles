package com.example.models;

public class Login {
    private String user;
    private String password;

    Login(){
      this.user = "";
      this.password = "";

    }
    Login(String user, String password){
      this.user = user;
      this.password = password;

    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
