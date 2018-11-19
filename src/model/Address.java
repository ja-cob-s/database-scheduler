/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class to handle address records
 * @author jnsch
 */
public class Address {
    private int addressID;
    private String addressLine1;
    private String addressLine2;
    private City city;
    private String postalCode;
    private String phoneNumber;
    
    // Constructors
    public Address() {
    }
    
    public Address(int addressID, String addressLine1, String addressLine2, City city, String postalCode, String phoneNumber) {
        this.addressID = addressID;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }
    
    // Getters
    public int getAddressID() {
        return this.addressID;
    }
    
    public String getAddressLine1() {
        return this.addressLine1;
    }
    
    public String getAddressLine2() {
        return this.addressLine2;
    }
    
    public City getCity() {
        return this.city;
    }
    
    public String getPostalCode() {
        return this.postalCode;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    // Setters
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }
    
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    
    public void setCity(City city) {
        this.city = city;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString() {
        return this.addressLine1 + "\n" + this.addressLine2;
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
        final Address other = (Address) obj;
        if (this.addressID != other.addressID) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
         return this.addressID * 2 * this.city.getCityID();
    }
}
