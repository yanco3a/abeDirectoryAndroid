package com.directory.abe.Models;

import java.io.Serializable;

public class User implements Serializable {
    private String userEmail;
    private int userId;
    private int userLat;
    private int userLng;
    private String userPassword;
    private String userSessionId;
    private String username;

    public User(int userId, String userSessionId, String username, String userEmail, String userPassword, int userLng, int userLat) {
        this.userId = userId;
        this.userSessionId = userSessionId;
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userLng = userLng;
        this.userLat = userLat;
    }

    public User(int userId, String userSessionId, String username) {
        this.userId = userId;
        this.userSessionId = userSessionId;
        this.username = username;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserSessionId() {
        return this.userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserLng() {
        return this.userLng;
    }

    public void setUserLng(int userLng) {
        this.userLng = userLng;
    }

    public int getUserLat() {
        return this.userLat;
    }

    public void setUserLat(int userLat) {
        this.userLat = userLat;
    }
}
