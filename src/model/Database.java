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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    
    private static final String USER_NAME = "admin"; //TODO: Change this to logged in user's name
    private static Connection connection;
    private static ObservableList<Appointment> myAppointments = FXCollections.observableArrayList();
    private static ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<City> cities = FXCollections.observableArrayList();
    
    public Database() {
    }
    
    // These get lists that have already been pulled from the database
    public ObservableList<Appointment> getAppointments() {
        return myAppointments;
    }
    
    public ObservableList<Customer> getCustomers() {
        return customers;
    }
    
    public ObservableList<User> getUsers() {
        return users;
    }
    
    public ObservableList<String> getAppointmentTypes() {
        return appointmentTypes;
    }
    
    public ObservableList<City> getCities() {
        return cities;
    }
    
    /* These next three methods satisfy REQUIREMENT C for 
       adding, updating, and deleting appointment records*/
    public void addAppointment(Appointment appointment) {
        // Convert start and end back to datetime format in UTC
        ZonedDateTime startZDT = LocalDateTime.of(appointment.getDate(), 
                appointment.getStart()).atZone(ZoneId.systemDefault());
        Timestamp startTS = Timestamp.from(startZDT.toInstant());
        
        ZonedDateTime endZDT = LocalDateTime.of(appointment.getDate(), 
                appointment.getEnd()).atZone(ZoneId.systemDefault());
        Timestamp endTS = Timestamp.from(endZDT.toInstant());
        /* Allows the database to auto increment to set the appointmentID, 
           then queries and finds the record from createdTS to set
           the appointmentID of the appointment object */
        Timestamp createdTS = Timestamp.from(Instant.now());
        
        String sql = "INSERT INTO appointment"
                   + "(customerId, userId, title, description, location, contact,"
                   + " type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"
                   + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, appointment.getCustomer().getCustomerID());
            ps.setInt(2, appointment.getUser().getUserID());
            ps.setString(3, appointment.getTitle());
            ps.setString(4, appointment.getDescription());
            ps.setString(5, appointment.getLocation());
            ps.setString(6, appointment.getUser().getUserName());
            ps.setString(7, appointment.getType());
            ps.setString(8, "");
            ps.setTimestamp(9, startTS);
            ps.setTimestamp(10, endTS);
            ps.setTimestamp(11, createdTS);
            ps.setString(12, USER_NAME);
            ps.setTimestamp(13, createdTS);
            ps.setString(14, USER_NAME);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT appointmentId FROM appointment"
                                           + " WHERE createDate = ?;");
            ps.setTimestamp(1, createdTS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointment.setAppointmentID(rs.getInt("appointmentId"));
                myAppointments.add(appointment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAppointment(Appointment appointment) {
        // Convert start and end back to datetime format in UTC
        ZonedDateTime startZDT = LocalDateTime.of(appointment.getDate(), 
                appointment.getStart()).atZone(ZoneId.systemDefault());
        Timestamp startTS = Timestamp.from(startZDT.toInstant());
        
        ZonedDateTime endZDT = LocalDateTime.of(appointment.getDate(),
                appointment.getEnd()).atZone(ZoneId.systemDefault());
        Timestamp endTS = Timestamp.from(endZDT.toInstant());
        
        String sql = "UPDATE appointment"
                   + " SET customerId = ?, userId = ?, title = ?, description = ?, location = ?,"
                   + " type = ?, start = ?, end = ?, lastUpdate = ?, lastUpdateBy = ?"
                   + " WHERE appointmentId = ?;";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, appointment.getCustomer().getCustomerID());
            ps.setInt(2, appointment.getUser().getUserID());
            ps.setString(3, appointment.getTitle());
            ps.setString(4, appointment.getDescription());
            ps.setString(5, appointment.getLocation());
            ps.setString(6, appointment.getType());
            ps.setTimestamp(7, startTS);
            ps.setTimestamp(8, endTS);
            ps.setTimestamp(9, Timestamp.from(Instant.now()));
            ps.setString(10, USER_NAME);
            ps.setInt(11, appointment.getAppointmentID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAppointment(Appointment appointment) {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("DELETE FROM appointment WHERE appointmentId = ?;");
            ps.setString(1, Integer.toString(appointment.getAppointmentID()));
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        myAppointments.remove(appointment);
    }
    
    /* These next five methods satisfy REQUIREMENT B for 
       adding, updating, and deleting customer records*/
    public void addCustomer(Customer customer) {
        /* Allows the database to auto increment to set the customerID, 
           then queries and finds the record from createdTS to set
           the customerID of the appointment object */
        Timestamp createdTS = Timestamp.from(Instant.now());
        
        String sql = "INSERT INTO customer"
                   + "(customerName, addressId, active,"
                   + " createDate, createdBy, lastUpdate, lastUpdateBy)"
                   + " VALUES(?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, customer.getAddress().getAddressID());
            ps.setInt(3, 1);
            ps.setTimestamp(4, createdTS);
            ps.setString(5, USER_NAME);
            ps.setTimestamp(6, createdTS);
            ps.setString(7, USER_NAME);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT customerId FROM customer"
                                           + " WHERE createDate = ?;");
            ps.setTimestamp(1, createdTS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customer.setCustomerID(rs.getInt("customerId"));
                customers.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customer"
                   + " SET customerName = ?, addressId = ?, lastUpdate = ?, lastUpdateBy = ?"
                   + " WHERE customerId = ?;";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, customer.getAddress().getAddressID());
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.setString(4, USER_NAME);
            ps.setInt(5, customer.getCustomerID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteCustomer(Customer customer) {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("DELETE FROM customer WHERE customerId = ?;");
            ps.setString(1, Integer.toString(customer.getCustomerID()));
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        customers.remove(customer);
    }
    
    public void addAddress(Address address) {
        /* Allows the database to auto increment to set the addressID, 
           then queries and finds the record from createdTS to set
           the addressID of the appointment object */
        Timestamp createdTS = Timestamp.from(Instant.now());
        
        String sql = "INSERT INTO address"
                   + "(address, address2, cityId, postalCode, phone"
                   + " createDate, createdBy, lastUpdate, lastUpdateBy)"
                   + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, address.getAddressLine1());
            ps.setString(2, address.getAddressLine2());
            ps.setInt(3, address.getCity().getCityID());
            ps.setString(4, address.getPostalCode());
            ps.setString(5, address.getPhoneNumber());
            ps.setTimestamp(6, createdTS);
            ps.setString(7, USER_NAME);
            ps.setTimestamp(8, createdTS);
            ps.setString(9, USER_NAME);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT addressId FROM address"
                                           + " WHERE createDate = ?;");
            ps.setTimestamp(1, createdTS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                address.setAddressID(rs.getInt("addressId"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAddress(Address address) {
        String sql = "UPDATE address"
                   + " SET address = ?, address2 = ?, cityId = ?, postalCode = ?,"
                   + " phone, lastUpdate = ?, lastUpdateBy = ?"
                   + " WHERE addressId = ?;";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, address.getAddressLine1());
            ps.setString(2, address.getAddressLine2());
            ps.setInt(3, address.getCity().getCityID());
            ps.setString(4, address.getPostalCode());
            ps.setTimestamp(5, Timestamp.from(Instant.now()));
            ps.setString(6, USER_NAME);
            ps.setInt(7, address.getAddressID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // These getters pull data from the SQL server and populate our lists
    public ObservableList<Appointment> getAppointmentsList() {
        connection = DBConnection.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointment;");
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
                String type = rs.getString("type");
                LocalDate date = rs.getTimestamp("start").toLocalDateTime().toLocalDate();
                LocalTime start = rs.getTimestamp("start").toLocalDateTime().toLocalTime();
                LocalTime end = rs.getTimestamp("end").toLocalDateTime().toLocalTime();
                myAppointments.add(new Appointment(appointmentID, customer, user, title, 
                        description, location, type, date, start, end));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myAppointments;
    }
    
    public ObservableList<String> getAppointmentTypesList() {
        connection = DBConnection.getConnection();
        
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT DISTINCT type FROM appointment;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointmentTypes.add(rs.getString("type"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appointmentTypes;
    }
    
    public ObservableList<City> getCitiesList() {
        connection = DBConnection.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM city;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cityID = rs.getInt("cityId");
                String city = rs.getString("city");
                Country country = this.getCountry(rs.getInt("countryId"));
                cities.add(new City(cityID, city, country));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cities;
    }
    
    public ObservableList<Customer> getCustomersList() {
        connection = DBConnection.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer;");
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
    
    public ObservableList<User> getUserList() {
        connection = DBConnection.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userId");
                String userName = rs.getString("userName");
                users.add(new User(userID, userName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
    
    public Customer getCustomer(int customerID) {
        connection = DBConnection.getConnection();
        Customer customer = new Customer();
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT * FROM customer WHERE customerId = ?;");
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
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT * FROM address WHERE addressId = ?;");
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
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT * FROM city WHERE cityId = ?;");
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
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT * FROM country WHERE countryId = ?;");
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
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT * FROM user WHERE userId = ?;");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("userName");
                user = new User(userID, userName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
}
