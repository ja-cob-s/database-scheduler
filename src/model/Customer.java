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
public class Customer {
    private int customerID;
    private String customerName;
    private Address address;
    
    // Constructors
    public Customer() {
    }

    public Customer(int customerID, String customerName, Address address) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
    }

    // Getters
    public int getCustomerID() {
        return this.customerID;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Address getAddress() {
        return this.address;
    }
    
    public City getCity() {
        return this.address.getCity();
    }
    
    public Country getCountry() {
        return this.address.getCity().getCountry();
    }
    
    public String getPostalCode() {
        return this.address.getPostalCode();
    }
    
    public String getPhoneNumber() {
        return this.address.getPhoneNumber();
    }

    // Setters
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return this.customerName;
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
        final Customer other = (Customer) obj;
        if (this.customerID != other.customerID) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
         return this.customerID * 11 * this.address.getAddressID();
    }
}
