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
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Address;
import model.Appointment;
import model.AppointmentReport;
import model.City;
import model.CityReport;
import model.Country;
import model.Customer;
import model.Database;
import model.User;

/**
 * FXML Controller class that controls the main screen which shows
 * appointments list, customers list, and reports
 * Part of REQUIREMENTS B, C, D, and I
 * @author jnsch
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button NewAppointmentButton;
    @FXML
    private Button EditAppointmentButton;
    @FXML
    private Button NewCustomerButton;
    @FXML
    private Button EditCustomerButton;
    @FXML
    private Button CustomerButton;
    @FXML
    private TabPane TabPane;
    @FXML
    private Tab AppointmentsTab;
    @FXML
    private ChoiceBox<Object> AppointmentTypeChooser;
    @FXML
    private TableView<Appointment> AppointmentsTable;
    @FXML
    private TableColumn<Appointment, LocalDate> AppointmentsDateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> AppointmentsStartColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> AppointmentsEndColumn;
    @FXML
    private TableColumn<Appointment, Customer> AppointmentsCustomerColumn;
    @FXML
    private TableColumn<Appointment, User> AppointmentsConsultantColumn;
    @FXML
    private TableColumn<Appointment, String> AppointmentsDescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> AppointmentsTypeColumn;
    @FXML
    private TableColumn<Appointment, String> AppointmentsLocationColumn;
    @FXML
    private ChoiceBox<Object> AppointmentViewChooser;
    @FXML
    private Tab CustomersTab;
    @FXML
    private TableView<Customer> CustomersTable;
    @FXML
    private TableColumn<Customer, String> CustomersNameColumn;
    @FXML
    private TableColumn<Customer, Address> CustomersAddressColumn;
    @FXML
    private TableColumn<Customer, City> CustomersCityColumn;
    @FXML
    private TableColumn<Customer, Country> CustomersCountryColumn;
    @FXML
    private TableColumn<Customer, String> CustomersPostalCodeColumn;
    @FXML
    private TableColumn<Customer, String> CustomersPhoneColumn;
    @FXML
    private TableView<AppointmentReport> AppointmentReportTable;
    @FXML
    private TableColumn<AppointmentReport, String> AppointmentReportMonthColumn;
    @FXML
    private TableColumn<AppointmentReport, String> AppointmentReportTypeColumn;
    @FXML
    private TableColumn<AppointmentReport, Integer> AppointmentReportNumberColumn;
    @FXML
    private TableView<User> ConsultantReportTable1;
    @FXML
    private TableColumn<User, String> ConsultantReportConsultantColumn;
    @FXML
    private TableView<Appointment> ConsultantReportTable2;
    @FXML
    private TableColumn<Appointment, LocalDate> ConsultantReportDateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> ConsultantReportStartColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> ConsultantReportEndColumn;
    @FXML
    private TableColumn<Appointment, Customer> ConsultantReportCustomerColumn;
    @FXML
    private TableColumn<Appointment, String> ConsultantReportDescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> ConsultantReportLocationColumn;
    @FXML
    private TableView<CityReport> CustomerReportTable;
    @FXML
    private TableColumn<CityReport, String> CustomerReportCityColumn;
    @FXML
    private TableColumn<CityReport, Integer> CustomerReportCustomerColumn;
    
    private ScreenHelper helper;
    private Database database;
    private ObservableList<Appointment> allAppointments;
    private FilteredList<Appointment> filteredAppointments;

    /**
     * Initializes the controller class
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helper = new ScreenHelper();
        database = new Database();
        if (database.getAppointments().isEmpty()) { database.getAppointmentsList(); }
        allAppointments = database.getAppointments();
        
        // REQUIREMENT G - Lambda expressions for efficient sorting and filtering
        Collections.sort(allAppointments, (Appointment a1, Appointment a2) -> 
                a1.getDate().compareTo(a2.getDate()));
        filteredAppointments = new FilteredList<>(allAppointments, s -> true);
        
        // Ability to view the calendar by type of appointment
        if (database.getAppointmentTypes().isEmpty()) { database.getAppointmentTypesList(); }
        AppointmentTypeChooser.setOnAction(this::FilteredAppointmentsHandler); 
        AppointmentTypeChooser.getItems().addAll("Show All Types", new Separator());
        AppointmentTypeChooser.getItems().addAll(database.getAppointmentTypes());
        AppointmentTypeChooser.getSelectionModel().selectFirst();
        
        // REQUIREMENT D - ability to view the calendar by week and by month
        AppointmentViewChooser.setOnAction(this::FilteredAppointmentsHandler);
        AppointmentViewChooser.getItems().addAll("Week View", "Month View");
        AppointmentViewChooser.getSelectionModel().selectFirst();
        
        this.populateAppointmentsTable(this.getFilteredAppointments());
        
        // REQUIREMENT G - Lambda expression to add listener on Tab Pane
        TabPane.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? 
                extends Number> observable, Number oldValue, Number newValue) -> {
            switch (TabPane.getSelectionModel().getSelectedIndex()) {
                case 0:
                    // Appointments tab
                    break;
                case 1:
                    // Customer tab
                    if (database.getCustomers().isEmpty()) { database.getCustomersList(); }
                    this.populateCustomersTable(database.getCustomers().sorted());
                    break;
                case 2:
                    // Reports tab
                    this.populateReportsTables();
                    break;
                default:
                    break;
            }
        });
    }    
    
    @FXML
    private void NewButtonHandler(ActionEvent event) throws IOException {
        if (TabPane.getSelectionModel().getSelectedItem() == AppointmentsTab) {
            // Takes the user to the screen to add a new appointment
            Stage stage = (Stage) NewAppointmentButton.getScene().getWindow();
            helper.nextScreenHandler(stage, "Appointment.fxml");
        } else if (TabPane.getSelectionModel().getSelectedItem() == CustomersTab) {
            // Takes the user to the screen to add a new customer
            Stage stage = (Stage) NewCustomerButton.getScene().getWindow();
            helper.nextScreenHandler(stage, "Customer.fxml");
        }
    }
    
    @FXML
    private void EditButtonHandler(ActionEvent event) throws IOException {
        
        if (TabPane.getSelectionModel().getSelectedItem() == AppointmentsTab) {
            // Takes the user to the screen to edit an appointment record
            if (AppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                Stage stage = (Stage) EditAppointmentButton.getScene().getWindow();
                AppointmentController controller = (AppointmentController) 
                        helper.nextScreenControllerHandler(stage, "Appointment.fxml");
                Appointment appointment = AppointmentsTable.getSelectionModel().getSelectedItem();
                controller.setAppointment(appointment);
            }
        } else if (TabPane.getSelectionModel().getSelectedItem() == CustomersTab) {
            // Takes the user to the screen to edit a customer record
            if (CustomersTable.getSelectionModel().getSelectedItem() != null) {
                Stage stage = (Stage) EditCustomerButton.getScene().getWindow();
                CustomerController controller = (CustomerController) 
                        helper.nextScreenControllerHandler(stage, "Customer.fxml");
                Customer customer = CustomersTable.getSelectionModel().getSelectedItem();
                controller.setCustomer(customer);
            }
        }
    }
    
    @FXML
    private void DeleteButtonHandler(ActionEvent event) {
        if (TabPane.getSelectionModel().getSelectedItem() == AppointmentsTab) {
            // Deletes the selected appointment. Shows a confirmation dialog first
            if (AppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                if (helper.showConfirmationDialog("Are you sure you want to delete this appointment?")){
                    // User chose OK
                    Appointment appointment = AppointmentsTable.getSelectionModel().getSelectedItem();
                    database.deleteAppointment(appointment);
                }
            }
        } else if (TabPane.getSelectionModel().getSelectedItem() == CustomersTab) {
            // Deletes the selected customer. Shows a confirmation dialog first
            if (CustomersTable.getSelectionModel().getSelectedItem() != null) {
                if (helper.showConfirmationDialog("Are you sure you want to delete this customer?")){
                    // User chose OK
                    Customer customer = CustomersTable.getSelectionModel().getSelectedItem();
                    
                    // Does not allow user to delete a customer has has active appointments
                    boolean hasAppointments = false;
                    for (Appointment a : database.getAppointments()) {
                        if (a.getCustomer().equals(customer)) {
                            hasAppointments = true;
                            break;
                        } 
                    }
                    if (hasAppointments) {
                        helper.showAlertDialog("This customer has active appointments. Please delete the appointments first and try again.");
                    } else {
                        database.deleteCustomer(customer);
                    }
                }
            }
        }
    }  
    
    @FXML
    private void CustomerButtonHandler(ActionEvent event) throws IOException {
        // Switches to customer record screen for the selected customer
        if (AppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                Stage stage = (Stage) CustomerButton.getScene().getWindow();
                CustomerController controller = (CustomerController) 
                        helper.nextScreenControllerHandler(stage, "Customer.fxml");
                Customer customer = AppointmentsTable.getSelectionModel().getSelectedItem().getCustomer();
                controller.setCustomer(customer);
            }
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

    private void FilteredAppointmentsHandler(ActionEvent event) {
        // REQUIREMENT D - ability to view the calendar by week and by month
        Object type = AppointmentTypeChooser.getSelectionModel().getSelectedItem();
        int view = AppointmentViewChooser.getSelectionModel().getSelectedIndex();
        
        // REQUIREMENT G - Lambda expressions for efficient sorting
        if (type.toString().equals("Show All Types")) {
            if (view == 0) {
                // Week view showing all types
                filteredAppointments.setPredicate(s -> 
                        s.getDate().isBefore(LocalDate.now().plusDays(7)) && s.getDate().isAfter(LocalDate.now().minusDays(1)));
            } else {
                // Month view showing all types
                filteredAppointments.setPredicate(s -> 
                        s.getDate().isBefore(LocalDate.now().plusMonths(1)) && s.getDate().isAfter(LocalDate.now().minusDays(1)));
            }
        } else {
            if (view == 0) {
                // Week view only selected type
                filteredAppointments.setPredicate(s -> s.getType().equals(type) && 
                        s.getDate().isBefore(LocalDate.now().plusDays(7)) && s.getDate().isAfter(LocalDate.now().minusDays(1)));
            } else {
                // Month view only selected type
                filteredAppointments.setPredicate(s -> s.getType().equals(type) && 
                        s.getDate().isBefore(LocalDate.now().plusMonths(1)) && s.getDate().isAfter(LocalDate.now().minusDays(1)));
            }
        }
    }

    
    public void populateAppointmentsTable(ObservableList<Appointment> list) {
        AppointmentsDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        AppointmentsStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        AppointmentsEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        AppointmentsCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        AppointmentsConsultantColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        AppointmentsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        AppointmentsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        AppointmentsLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        AppointmentsTable.setItems(list);
    }
    
    public void populateCustomersTable(ObservableList<Customer> list) {
        CustomersNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomersAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustomersCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        CustomersCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        CustomersPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        CustomersPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        
        CustomersTable.setItems(list);
    }
    
    public void populateReportsTables() {
        /* REQUIREMENT I - ability to generate each of the following reports:
           - number of appointment types by month
           - the schedule for each consultant
           - one additional report of your choice (number of customers displayed by city)*/
        
        // Number of appointment types by month
        AppointmentReportMonthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        AppointmentReportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        AppointmentReportNumberColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        AppointmentReportTable.setItems(database.getAppointmentReport());
        
        // Schedule for each consultant
        FilteredList<Appointment> consultantReportList = new FilteredList<>(allAppointments, s -> true);
        if (database.getUsers().isEmpty()) { database.getUserList(); }
        ConsultantReportConsultantColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        ConsultantReportTable1.setItems(database.getUsers().sorted());
        
        // REQUIREMENT G - Lambda expressios for efficient filtering
        ConsultantReportTable1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) { 
                consultantReportList.setPredicate(s -> s.getUser().equals(ConsultantReportTable1.getSelectionModel().getSelectedItem()));
            }
        });
        ConsultantReportTable1.getSelectionModel().select(Database.getCurrentUser());
        
        ConsultantReportDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        ConsultantReportStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        ConsultantReportEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        ConsultantReportCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        ConsultantReportDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        ConsultantReportLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        ConsultantReportTable2.setItems(consultantReportList);
        
        // Number of customers by city
        CustomerReportCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        CustomerReportCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customers"));
        CustomerReportTable.setItems(database.getCityReport());
    }
    
    public FilteredList<Appointment> getFilteredAppointments() {
        return this.filteredAppointments;
    }
    
    public void setFilteredAppointments(FilteredList<Appointment> filteredAppointments) {
        this.filteredAppointments = filteredAppointments;
    }
}
