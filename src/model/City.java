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
public class City {
    private int cityID;
    private String city;
    private Country country;
    
    // Constructors
    public City() {
    }

    public City(int cityID, String city, Country country) {
        this.cityID = cityID;
        this.city = city;
        this.country = country;
    }
    
    // Getters
    public int getCityID() {
        return this.cityID;
    }

    public String getCity() {
        return this.city;
    }

    public Country getCountry() {
        return this.country;
    }
    
    // Setters
    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountryID(Country country) {
        this.country = country;
    }
    
    @Override
    public String toString() {
        return this.city;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass() || obj == null) {
            return false;
        }
        City other = (City) obj;
        return this.getCityID() == other.getCityID();
    }
    
    @Override
    public int hashCode() {
         return this.cityID * 5 * country.getCountryID();
    }
}
