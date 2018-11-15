/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

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
    private LocalDateTime start;
    private LocalDateTime end;
    
    // Constructors
    public Appointment() {
    }

    public Appointment(int appointmentID, Customer customer, User user, String title, String description, 
            String location, String contact, String type, LocalDateTime start, LocalDateTime end, String createdBy) {
        this.appointmentID = appointmentID;
        this.customer = customer;
        this.user = user;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    // Getters
    public int getAppointmentID() {
        return appointmentID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
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

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
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
         return this.appointmentID * 3 * customer.getCustomerID() * user.getUserID();
    }
}
