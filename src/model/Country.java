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
public class Country {
    private int countryID;
    private String country;
    
    // Constructors
    public Country() {
    }

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    // Getters
    public int getCountryID() {
        return this.countryID;
    }

    public String getCountry() {
        return this.country;
    }

    // Setters 
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public String toString() {
        return this.country;
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
        final Country other = (Country) obj;
        if (this.countryID != other.countryID) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
         return this.countryID * 7;
    }
}
