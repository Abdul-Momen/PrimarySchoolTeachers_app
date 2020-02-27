package com.momen.primaryschoolteachers_app.Model;



public class LoginResponse {
    private String message;
    private boolean authentication;
    private UserData userData;

    public UserData getUserData() {
        return userData;
    }

    public String getMessage() {
        return message;
    }

    /*public boolean isAuthentication() {
        return authentication;
    }*/
}
