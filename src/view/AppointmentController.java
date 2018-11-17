/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Database;

/**
 * FXML Controller class
 *
 * @author jnsch
 */
public class AppointmentController implements Initializable {

    @FXML
    private AnchorPane AppointmentPane;
    @FXML
    private Button ExitButton;
    @FXML
    private TableView<Customer> CustomersTable;
    @FXML
    private TableColumn<Customer, String> CustomersNameColumn;
    @FXML
    private TextField CustomerSearch;
    @FXML
    private Label TitleLabel;
    @FXML
    private Label DescriptionLabel;
    @FXML
    private Label TypeLabel;
    @FXML
    private Label StartLabel;
    @FXML
    private Label EndLabel;
    @FXML
    private Label DateLabel;
    @FXML
    private Button CancelButton;
    @FXML
    private Label CustomerLabel;
    @FXML
    private Button SearchButton;
    @FXML
    private TextField TitleField;
    @FXML
    private TextArea DescriptionField;
    @FXML
    private ChoiceBox<String> TypeChooser;
    @FXML
    private DatePicker DateChooser;
    @FXML
    private ChoiceBox<?> StartChooser;
    @FXML
    private ChoiceBox<?> EndChooser;
    @FXML
    private Label AppointmentLabel;
    @FXML
    private Button SaveButton;
    
    private ScreenHelper helper;
    private Database database;
    private Appointment appointment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helper = new ScreenHelper();
        database = new Database();
        
        TypeChooser.getItems().addAll(database.getAppointmentTypes());
        this.populateCustomersTable(database.getCustomers());
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
    private void SearchButtonHandler(ActionEvent event) {
        String searchItem = CustomerSearch.getText();
        ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
        boolean found = false;

            //Displays full list if no search string
            if (searchItem == null || searchItem.isEmpty()) {
                this.populateCustomersTable(database.getCustomers());
                found = true;
            }
            //Searches by Name
            for (Customer c: database.getCustomers()) {
                if (c.getCustomerName().equals(searchItem)) {
                    found = true;
                    filteredCustomers.add(c);
                    this.populateCustomersTable(filteredCustomers);
                }
            }
            if (found == false) {
                helper.showAlertDialog("Customer not found.");
            }            
    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) {
    }
    
    public void populateCustomersTable(ObservableList<Customer> list) {
        CustomersNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomersTable.setItems(list);
    }
    
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        
        TitleField.setText(appointment.getTitle());
        DescriptionField.setText(appointment.getDescription());
        TypeChooser.setValue(appointment.getType());
        
    }
    
}
