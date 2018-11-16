/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author jnsch
 */
public class Appointment {
    private int appointmentID;
    private Customer customer;
    private User user;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    
    // Constructors
    public Appointment() {
    }

    public Appointment(int appointmentID, Customer customer, User user, String title, String description, 
            String location, String contact, String type, LocalDate date, LocalTime start, LocalTime end) {
        this.appointmentID = appointmentID;
        this.customer = customer;
        this.user = user;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    // Getters
    public int getAppointmentID() {
        return this.appointmentID;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public User getUser() {
        return this.user;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLocation() {
        return this.location;
    }

    public String getContact() {
        return this.contact;
    }

    public String getType() {
        return this.type;
    }
    
    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getStart() {
        return this.start;
    }

    public LocalTime getEnd() {
        return this.end;
    }
    
    // Setters
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass() || obj == null) {
            return false;
        }
        Appointment other = (Appointment) obj;
        return this.getAppointmentID() == other.getAppointmentID();
    }
    
    @Override
    public int hashCode() {
         return this.appointmentID * 3 * this.customer.getCustomerID() * this.user.getUserID();
    }
}
