package com.example.yixu.fullcircle;

import java.util.Date;

public class User {

    private  int userId;
    private String userName;

    private String userPassword;
    private String lastName;
    private String firstName;
    private Date DOB;
    private String email;

    public User(){

    }

    public User(int userId, String userName, String userPassword){

        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;

    }

    public User(String userName, String userPassword){

        this.userName = userName;
        this.userPassword = userPassword;

    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
