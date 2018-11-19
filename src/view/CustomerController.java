/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Address;
import model.City;
import model.Customer;
import model.Database;

/**
 * FXML Controller class controls the Customer screen which allows the user to
 * add or update customer records
 * @author jnsch
 */
public class CustomerController implements Initializable {

    @FXML
    private TableView<Customer> CustomersTable;
    @FXML
    private TableColumn<Customer, String> CustomersNameColumn;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField NameField;
    @FXML
    private TextArea AddressField;
    @FXML
    private ChoiceBox<City> CityChooser;
    @FXML
    private Label CustomerLabel;
    @FXML
    private Button SaveButton;
    @FXML
    private TextField CountryField;
    @FXML
    private TextField PostalCodeField;
    @FXML
    private TextField PhoneNumberField;
    
    private ScreenHelper helper;
    private Database database;
    private Customer customer;
    private ObservableList<City> cities;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helper = new ScreenHelper();
        database = new Database();
        
        if (database.getCities().isEmpty()) { database.getCitiesList(); }
        cities = database.getCities();
        
        CityChooser.setOnAction(this::CityChooserHandler);
        CityChooser.getItems().addAll(cities.sorted());
        CityChooser.getSelectionModel().selectFirst();
        this.populateCustomersTable(database.getCustomers().sorted());
        
        /* REQUIREMENT G Lambda expression for listener on Customers Table
           populates data in the fields based on table selection
           allows user to switch to a different customer record
           without returning to main screen */
        CustomersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) { this.setCustomer(newSelection); }
        });
        /* REQUIREMENT G Lambda expression does not allow the user to
           type more than 10 characters into the Postal code box, thus
           saving from having to check the input before adding to the database */
        PostalCodeField.setOnKeyTyped(event ->{
            int maxCharacters = 10;
            if(PostalCodeField.getText().length() > maxCharacters) event.consume();
        });
    }
    
    @FXML
    private void CityChooserHandler(ActionEvent event) {
        // Fills in the appropriate country for the city chosen.
        CountryField.setText(CityChooser.getSelectionModel().getSelectedItem().getCountry().toString());
    }
    
    @FXML
    private void ExitButtonHandler(ActionEvent event) {
        /*Terminates the application when the exit button is pressed
          Displays confirmation dialog first*/
        if (helper.showConfirmationDialog("Are you sure you want to exit?")){
            // ... user chose OK
            System.exit(0); 
        } 
    }

    @FXML
    private void CancelButtonHandler(ActionEvent event) throws IOException {
        /*Switches to main screen and discards changes when cancelButton pressed
          Displays confirmation dialog first*/
        if (helper.showConfirmationDialog("Are you sure you want to discard changes?")) {
            // ... user chose OK
            Stage stage = (Stage) CancelButton.getScene().getWindow(); 
            helper.nextScreenHandler(stage, "MainScreen.fxml");
        }  
    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) throws IOException {
        // These checks make sure all user input is valid before saving
        helper.setValidInput(true);
        String customerName = helper.getString(NameField.getText(), "Name field");
        
        String[] addressString = AddressField.getText().split("\n", 2);
        String addressLine1 = addressString[0];
        String addressLine2;
        if (addressString.length > 1) {
            addressLine2 = addressString[1];
        } else {
            addressLine2 = "";
        }
        helper.getString(addressLine1, "Address field");
        
        City city = CityChooser.getSelectionModel().getSelectedItem();
                
        String postalCode = helper.getString(PostalCodeField.getText(), "Postal Code");
        
        /* Validates that phone number is in one of the following formats:
           1234567890, 123-456-7890, (123)456-7890, (123)456789*/
        String phoneNumber = PhoneNumberField.getText();
        if (!phoneNumber.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")) {
            helper.setValidInput(false);
            helper.setExceptionString("Please enter a valid phone number.");
        }
        
        if (!helper.isValidInput()) {
            // Show warnings and do not save
            helper.showAlertDialog(helper.getExceptionString());
        } else { 
            // All data is good...  
            if (this.getCustomer() == null) {
                /* First add the address to the table
                   addressID is temporarily set to 0, allow the database's 
                   auto increment to find and set a real value */
                Address address = new Address(0, addressLine1, addressLine2, 
                        city, postalCode, phoneNumber);
                database.addAddress(address);
                /* Now add a new customer
                   customerID is temporarily set to 0, allow the database's 
                   auto increment to find and set a real value */
                this.setCustomer(new Customer(0, customerName, address));
                database.addCustomer(customer);
            } else {
                // Update an existing customer
                this.getCustomer().setCustomerName(customerName);
                this.getCustomer().getAddress().setAddressLine1(addressLine1);
                this.getCustomer().getAddress().setAddressLine2(addressLine2);
                this.getCustomer().getAddress().setCity(city);
                this.getCustomer().getAddress().getCity().setCountry(city.getCountry());
                this.getCustomer().getAddress().setPostalCode(postalCode);
                this.getCustomer().getAddress().setPhoneNumber(phoneNumber);
                database.updateAddress(this.getCustomer().getAddress());
                database.updateCustomer(this.getCustomer());
            }
            Stage stage = (Stage) SaveButton.getScene().getWindow();
            helper.nextScreenHandler(stage, "MainScreen.fxml");
        }
        
        helper.setExceptionString(""); // Clear out exception string for next run
    }
    
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
        
        NameField.setText(customer.getCustomerName());
        AddressField.setText(customer.getAddress().toString());
        CityChooser.setValue(customer.getAddress().getCity());
        CountryField.setText(customer.getAddress().getCity().getCountry().toString());
        PostalCodeField.setText(customer.getAddress().getPostalCode());
        PhoneNumberField.setText(customer.getAddress().getPhoneNumber());
        
        CustomersTable.getSelectionModel().select(customer);
    }
    
    public void populateCustomersTable(ObservableList<Customer> list) {
        CustomersNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomersTable.setItems(list);
    }
}
