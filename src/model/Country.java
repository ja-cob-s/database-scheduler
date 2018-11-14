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
        return countryID;
    }

    public String getCountry() {
        return country;
    }

    // Setters 
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass() || obj == null) {
            return false;
        }
        Country other = (Country) obj;
        return this.getCountryID() == other.getCountryID();
    }
    
    @Override
    public int hashCode() {
         return this.countryID * 7;
    }
}
