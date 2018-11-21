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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Database;
import model.User;

/**
 * FXML Controller class controls the Appointment screen which allows the user to
 * add, update, or delete appointment records - part of REQUIREMENT C
 * @author jnsch
 */
public class AppointmentController implements Initializable {

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
    private Button CancelButton;
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
    private Button SaveButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button CustomerButton;
    
    private final ObservableList<LocalTime> validStartTimes = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> validEndTimes = FXCollections.observableArrayList();
    private FilteredList<LocalTime> filteredEndTimes;
    private ScreenHelper helper;
    private Database database;
    private Appointment appointment;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helper = new ScreenHelper();
        database = new Database();
        
        if (database.getCustomers().isEmpty()) { database.getCustomersList(); }
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
        
        if (this.getAppointment() == null) {
            ConsultantsTable.getSelectionModel().select(Database.getCurrentUser());
            DeleteButton.setVisible(false);
        } 
    }    
    
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
    private void CustomerButtonHandler(ActionEvent event) throws IOException {
        // Switches to customer record screen for the selected customer
        if (CustomersTable.getSelectionModel().getSelectedItem() != null) {
                Stage stage = (Stage) CustomerButton.getScene().getWindow();
                CustomerController controller = (CustomerController) 
                        helper.nextScreenControllerHandler(stage, "Customer.fxml");
                Customer customer = CustomersTable.getSelectionModel().getSelectedItem();
                controller.setCustomer(customer);
            }
    }

    @FXML
    private void CustomerSearchButtonHandler(ActionEvent event) {
        // Searches the customer table view
        String searchItem = CustomerSearch.getText();
        ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
        boolean found = false;

            // Displays full list if no search string
            if (searchItem == null || searchItem.isEmpty()) {
                this.populateCustomersTable(database.getCustomers().sorted());
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
        // Searches the consultant table view
        String searchItem = ConsultantSearch.getText();
        ObservableList<User> filteredConsultants = FXCollections.observableArrayList();
        boolean found = false;

            // Displays full list if no search string
            if (searchItem == null || searchItem.isEmpty()) {
                this.populateConsultantsTable(database.getUsers().sorted());
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
        // REQUIREMENT C - Saves a new appointment or updates an existing appointment
        
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
        
        LocalDate date = DateChooser.getValue();
        
        LocalTime start = StartChooser.getSelectionModel().getSelectedItem();
        
        LocalTime end = EndChooser.getSelectionModel().getSelectedItem();
        
        // REQUIREMENT F - prevents scheduling overlapping appointments for the same user
        this.overlappingAppointmentChecker(user, date, start, end);
        
        if (!helper.isValidInput()) {
            // Show warnings and do not save
            helper.showAlertDialog(helper.getExceptionString());
        } else { 
            // All data is good...            
            if (this.getAppointment() == null) {
                /* Add a new appointment
                   appointmentID is temporarily set to 0, allow the database's 
                   auto increment to find and set a real value */
                this.setAppointment(new Appointment(0, customer, user, title, 
                        description, location, type, date, start, end));
                database.addAppointment(this.getAppointment());
                
            } else {
                // Update an existing appointment
                this.getAppointment().setCustomer(customer);
                this.getAppointment().setUser(user);
                this.getAppointment().setTitle(title);
                this.getAppointment().setDescription(description);
                this.getAppointment().setLocation(location);
                this.getAppointment().setType(type);
                this.getAppointment().setDate(date);
                this.getAppointment().setStart(start);
                this.getAppointment().setEnd(end);
                database.updateAppointment(this.getAppointment());
            }
            // Return to the main screen
            Stage stage = (Stage) SaveButton.getScene().getWindow();
            helper.nextScreenHandler(stage, "MainScreen.fxml");
        }
        helper.setExceptionString(""); // Clear out exception string for next run
    }
    
    @FXML
    private void DeleteButtonHandler(ActionEvent event) throws IOException {
        // REQUIREMENT C - Deletes the current appointment and returns to the Main Screen
        
        if (helper.showConfirmationDialog("Are you sure you want to delete this appointment?")){
            // User chose OK
            database.deleteAppointment(this.getAppointment());
            Stage stage = (Stage) SaveButton.getScene().getWindow();
            helper.nextScreenHandler(stage, "MainScreen.fxml");
        }
    }
    
    public void populateCustomersTable(ObservableList<Customer> list) {
        CustomersNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomersTable.setItems(list);
    }
    
    public void populateConsultantsTable(ObservableList<User> list) {
        ConsultantsNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        ConsultantsTable.setItems(list);
    }
    
    public Appointment getAppointment() {
        return this.appointment;
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
        
        DeleteButton.setVisible(true);
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
    
    public void overlappingAppointmentChecker(User user, LocalDate date, LocalTime start, LocalTime end) {
        final int appointmentID;
        if (this.getAppointment() != null) {
            appointmentID = this.getAppointment().getAppointmentID();
        } else {
            appointmentID = 0;
        }
        /* REQUIREMENT F - prevent scheduling overlapping appointments and REQUIREMENT G - lamba expression for efficient filtering
           Predicate reads appointmentIDs are not the same AND users are the same AND dates are the same AND EITHER
           (new appt start time is before other appt end time AND new appt start time is after other appt start time) OR
           (new appt end time is after other appt start time AND new appt end time is before other appt end time)*/
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(database.getAppointments(), s -> 
            !(s.getAppointmentID() == appointmentID) && s.getUser().equals(user) && s.getDate().equals(date) &&
            ((start.isBefore(s.getEnd()) && start.isAfter(s.getStart())) || (end.isAfter(s.getStart()) && end.isBefore(s.getEnd()))));
        if (!filteredAppointments.isEmpty()) {
            helper.setValidInput(false);
            helper.setExceptionString("Appointment overlaps with another appointment");
        }
    }
}
