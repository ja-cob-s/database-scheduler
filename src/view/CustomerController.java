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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.City;
import model.Customer;
import model.Database;

/**
 * FXML Controller class
 *
 * @author jnsch
 */
public class CustomerController implements Initializable {

    @FXML
    private AnchorPane CustomerPane;
    @FXML
    private Button ExitButton;
    @FXML
    private TableView<Customer> CustomersTable;
    @FXML
    private TableColumn<Customer, String> CustomersNameColumn;
    @FXML
    private Label NameLabel;
    @FXML
    private Label AddressLabel;
    @FXML
    private Label CityLabel;
    @FXML
    private Label PostalCodeLabel;
    @FXML
    private Label PhoneLabel;
    @FXML
    private Label CountryLabel;
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
        
        CityChooser.getItems().addAll(cities.sorted());
        CityChooser.getSelectionModel().selectFirst();
        this.populateCustomersTable(database.getCustomers().sorted());
        
        CustomersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.setCustomer(newSelection);
            }
        });
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
    private void SaveButtonHandler(ActionEvent event) {
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
