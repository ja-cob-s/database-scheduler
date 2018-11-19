/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class to handle User records 
 * @author jnsch
 */
public class User {
    private int userID;
    private String userName;
    
    // Constructors
    public User() {
    }

    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    // Getters
    public int getUserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.userName;
    }
    
    // Setters
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public String toString() {
        return this.userName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.userID != other.userID) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
         return this.userID * 13;
    }
}
