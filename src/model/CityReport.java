/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class to handle the City Report, part of REQUIREMENT I
 * @author jnsch
 */
public class CityReport {
    private String city;
    private int customers;
    
    public CityReport() {
    }
    
    public CityReport(String city, int customers) {
        this.city = city;
        this.customers = customers;
    }

    public String getCity() {
        return city;
    }

    public int getCustomers() {
        return customers;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCustomers(int customers) {
        this.customers = customers;
    }
}
