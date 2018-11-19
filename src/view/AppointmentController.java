/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import model.User;

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
    private TableView<User> ConsultantsTable;
    @FXML
    private TableColumn<User, String> ConsultantsNameColumn;
    @FXML
    private TextField CustomerSearch;
    @FXML
    private TextField ConsultantSearch;
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
    private Button CustomerSearchButton;
    @FXML
    private Button ConsultantSearchButton;
    @FXML
    private TextField TitleField;
    @FXML
    private TextArea DescriptionField;
    @FXML
    private TextField LocationField;
    @FXML
    private ChoiceBox<String> TypeChooser;
    @FXML
    private DatePicker DateChooser;
    @FXML
    private ChoiceBox<LocalTime> StartChooser;
    @FXML
    private ChoiceBox<LocalTime> EndChooser;
    @FXML
    private Label AppointmentLabel;
    @FXML
    private Button SaveButton;
    
    private final ObservableList<LocalTime> validStartTimes = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> validEndTimes = FXCollections.observableArrayList();
    private FilteredList<LocalTime> filteredEndTimes;
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
        
        if (database.getAppointmentTypes().isEmpty()) { database.getAppointmentTypesList(); }
        if (database.getUsers().isEmpty()) { database.getUserList(); }
        
        /* User must pick start and end times within business hours from list
           This fulfills first bullet point of REQUIREMENT F
           (Business hours assumed to be 8:00am-5:00pm) */
        this.setTimes();
        // REQUIREMENT G - Lambda expression for efficient sorting
        filteredEndTimes = new FilteredList<>(validEndTimes, s -> true);
        StartChooser.setOnAction(this::StartChooserHandler);
        StartChooser.getItems().addAll(validStartTimes);
        StartChooser.getSelectionModel().select(0);
        
        TypeChooser.getItems().addAll(database.getAppointmentTypes());
        TypeChooser.getSelectionModel().select(0);
        DateChooser.setValue(LocalDate.now());
        
        /* User must pick customer from table populated w/DB data
           This fulfills 3rd bullet point of REQUIREMENT F */
        this.populateCustomersTable(database.getCustomers().sorted());
        this.populateConsultantsTable(database.getUsers().sorted());    
    }    
    
    @FXML
    private void StartChooserHandler(ActionEvent event) {
        // Filters the valid end times so end time must be after chosen start time
        filteredEndTimes.setPredicate(s -> s.isAfter(StartChooser.getValue()));
        EndChooser.getItems().clear();
        EndChooser.getItems().addAll(filteredEndTimes);
        EndChooser.getSelectionModel().select(0);
    }

    @FXML
    private void ExitButtonHandler(ActionEvent event) {
        /* Terminates the application when the exit button is pressed
           Displays confirmation dialog first*/
        if (helper.showConfirmationDialog("Are you sure you want to exit?")){
            // ... user chose OK
            System.exit(0); 
        } 
    }

    @FXML
    private void CancelButtonHandler(ActionEvent event) throws IOException {
        /* Switches to main screen and discards changes when cancelButton pressed
           Displays confirmation dialog first*/
        if (helper.showConfirmationDialog("Are you sure you want to discard changes?")) {
            // ... user chose OK
            Stage stage = (Stage) CancelButton.getScene().getWindow(); 
            helper.nextScreenHandler(stage, "MainScreen.fxml");
        }  
    }

    @FXML
    private void CustomerSearchButtonHandler(ActionEvent event) {
        String searchItem = CustomerSearch.getText();
        ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
        boolean found = false;

            // Displays full list if no search string
            if (searchItem == null || searchItem.isEmpty()) {
                this.populateCustomersTable(database.getCustomers());
                found = true;
            }
            // Searches by Name
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
    private void ConsultantSearchButtonHandler(ActionEvent event) {
        String searchItem = ConsultantSearch.getText();
        ObservableList<User> filteredConsultants = FXCollections.observableArrayList();
        boolean found = false;

            // Displays full list if no search string
            if (searchItem == null || searchItem.isEmpty()) {
                this.populateConsultantsTable(database.getUsers());
                found = true;
            }
            // Searches by Name
            for (User u: database.getUsers()) {
                if (u.getUserName().equals(searchItem)) {
                    found = true;
                    filteredConsultants.add(u);
                    this.populateConsultantsTable(filteredConsultants);
                }
            }
            if (found == false) {
                helper.showAlertDialog("Consultant not found.");
            }            
    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) throws IOException {        
        // These checks make sure all user input is valid before saving
        helper.setValidInput(true);
        Customer customer = CustomersTable.getSelectionModel().getSelectedItem();
        if (customer == null) { helper.setValidInput(helper.IOExceptionHandler("Please select a customer.")); }
        
        User user = ConsultantsTable.getSelectionModel().getSelectedItem();
        if (user == null) { helper.setValidInput(helper.IOExceptionHandler("Please select a consultant.")); }
        
        String title = helper.getString(TitleField.getText(), "Title field");
        
        String description = helper.getString(DescriptionField.getText(), "Description field");
        
        String location = helper.getString(LocationField.getText(), "Location field");
        
        String type = TypeChooser.getSelectionModel().getSelectedItem();
        if (type == null) { helper.setValidInput(helper.IOExceptionHandler("Please select a type.")); }
        
        LocalDate date = DateChooser.getValue();
        if (date == null) { helper.setValidInput(helper.IOExceptionHandler("Please select a date.")); }
        
        LocalTime start = StartChooser.getSelectionModel().getSelectedItem();
        if (start == null) { helper.setValidInput(helper.IOExceptionHandler("Please select a start time.")); }
        
        LocalTime end = EndChooser.getSelectionModel().getSelectedItem();
        if (end == null) { helper.setValidInput(helper.IOExceptionHandler("Please select an end time.")); }
        
        if (!helper.getValidInput()) {
            // Show warnings and do not save
            helper.showAlertDialog(helper.getExceptionString());
        } else { 
            // All data is good...            
            if (appointment == null) {
                /* Add a new appointment
                   appointmentID is temporarily set to 0, allow the database's 
                   auto increment to find and set a real value */
                appointment = new Appointment(0, customer, user, title, 
                        description, location, type, date, start, end);
                database.addAppointment(appointment);
                
            } else {
                // Update an existing appointment
                appointment.setCustomer(customer);
                appointment.setUser(user);
                appointment.setTitle(title);
                appointment.setDescription(description);
                appointment.setLocation(location);
                appointment.setType(type);
                appointment.setDate(date);
                appointment.setStart(start);
                appointment.setEnd(end);
                database.updateAppointment(appointment);
            }
            Stage stage = (Stage) SaveButton.getScene().getWindow();
            helper.nextScreenHandler(stage, "MainScreen.fxml");
        }
        helper.setExceptionString(""); // Clear out exception string for next run
    }
    
    public void populateCustomersTable(ObservableList<Customer> list) {
        CustomersNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomersTable.setItems(list);
    }
    
    public void populateConsultantsTable(ObservableList<User> list) {
        ConsultantsNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        ConsultantsTable.setItems(list);
    }
    
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        
        TitleField.setText(appointment.getTitle());
        DescriptionField.setText(appointment.getDescription());
        LocationField.setText(appointment.getLocation());
        TypeChooser.setValue(appointment.getType());
        DateChooser.setValue(appointment.getDate());
        CustomersTable.getSelectionModel().select(appointment.getCustomer());
        ConsultantsTable.getSelectionModel().select(appointment.getUser());
        StartChooser.setValue(appointment.getStart());
        EndChooser.setValue(appointment.getEnd());
    }
    
    public void setTimes() {
        LocalTime time = LocalTime.of(8, 0);
	do {
            validStartTimes.add(time);
            validEndTimes.add(time);
            time = time.plusMinutes(15);
	} while(!time.equals(LocalTime.of(17, 15)));
	validStartTimes.remove(validStartTimes.size() - 1);
	validEndTimes.remove(0);
    }
    
}
