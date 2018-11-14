/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
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
        return addressLine1;
    }
    
    public String getAddressLine2() {
        return addressLine2;
    }
    
    public City getCity() {
        return city;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
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
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass() || obj == null) {
            return false;
        }
        Address other = (Address) obj;
        return this.getAddressID() == other.getAddressID();
    }
    
    @Override
    public int hashCode() {
         return this.addressID * 2 * this.city.getCityID();
    }
}
