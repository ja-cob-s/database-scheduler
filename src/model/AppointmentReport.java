/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class to handle the Appointment Report, part of REQUIREMENT I
 * @author jnsch
 */
public class AppointmentReport {
    private String month;
    private String type;
    private int amount;

    // Constructors
    public AppointmentReport() {
    }

    public AppointmentReport(String month, String type, int amount) {
        this.month = month;
        this.type = type;
        this.amount = amount;
    }

    // Getters
    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    // Setters
    public void setMonth(String month) {
        this.month = month;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
