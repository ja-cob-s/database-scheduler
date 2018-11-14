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
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Address getAddress() {
        return address;
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
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass() || obj == null) {
            return false;
        }
        Customer other = (Customer) obj;
        return this.getCustomerID() == other.getCustomerID();
    }
    
    @Override
    public int hashCode() {
         return this.customerID * 11 * address.getAddressID();
    }
}
