/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.scheduler.DatabaseScheduler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBConnection;

/**
 *
 * @author jnsch
 */
public class Database {
    
    private static Connection connection;
    private static ObservableList<Appointment> myAppointments = FXCollections.observableArrayList();
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    
    public Database() {
    }
    
    public void addAppointment(Appointment appointment) {
        myAppointments.add(appointment);
    }
    
    public void deleteAppointment(Appointment appointment) {
        myAppointments.remove(appointment);
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }
    
    // These getters pull data from the SQL server
    public ObservableList<Appointment> getAppointments() {
        connection = DBConnection.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement("select * from appointment;");
            //ps.setInt(1, 1);
            //System.out.println("sql query " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("appointmentId");
                Customer customer = this.getCustomer(rs.getInt("customerId"));
                User user = this.getUser(rs.getInt("userId"));
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String contact = rs.getString("contact");
                String type = rs.getString("type");
                LocalDate date = rs.getTimestamp("start").toLocalDateTime().toLocalDate();
                LocalTime start = rs.getTimestamp("start").toLocalDateTime().toLocalTime();
                LocalTime end = rs.getTimestamp("end").toLocalDateTime().toLocalTime();
                myAppointments.add(new Appointment(appointmentID, customer, user, title, description,
                    location, contact, type, date, start, end));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myAppointments;
    }
    
    public ObservableList<Customer> getCustomers() {
        connection = DBConnection.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement("select * from customer;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                Address address = this.getAddress(rs.getInt("addressId"));
                customers.add(new Customer(customerID, customerName, address));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customers;
    }
    
    public Customer getCustomer(int customerID) {
        connection = DBConnection.getConnection();
        Customer customer = new Customer();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from customer where customerId = ?;");
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String customerName = rs.getString("customerName");
                Address address = this.getAddress(rs.getInt("addressId"));
                customer = new Customer(customerID, customerName, address);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }
    
    public Address getAddress(int addressID) {
        connection = DBConnection.getConnection();
        Address address = new Address();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from address where addressId = ?;");
            ps.setInt(1, addressID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String addressLine1 = rs.getString("address");
                String addressLine2 = rs.getString("address2");
                City city = this.getCity(rs.getInt("cityId"));
                String postalCode = rs.getString("postalCode");
                String phoneNumber = rs.getString("phone");
                address = new Address(addressID, addressLine1, addressLine2, city, postalCode, phoneNumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return address;
    }
    
    public City getCity(int cityID) {
        connection = DBConnection.getConnection();
        City city = new City();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from city where cityId = ?;");
            ps.setInt(1, cityID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String cityName = rs.getString("city");
                Country country = this.getCountry(rs.getInt("countryId"));
                city = new City(cityID, cityName, country);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return city;
    }
    
    public Country getCountry(int countryID) {
        connection = DBConnection.getConnection();
        Country country = new Country();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from country where countryId = ?;");
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String countryName = rs.getString("country");
                country = new Country(countryID, countryName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return country;
    }
    
    public User getUser(int userID) {
        connection = DBConnection.getConnection();
        User user = new User();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from user where userId = ?;");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                user = new User(userID, userName, password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
}
