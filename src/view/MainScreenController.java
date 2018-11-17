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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Address;
import model.Appointment;
import model.City;
import model.Country;
import model.Customer;
import model.Database;
import model.User;

/**
 * FXML Controller class
 *
 * @author jnsch
 */
public class MainScreenController implements Initializable {

    @FXML
    private AnchorPane AppointmentViewPane;
    @FXML
    private Button ExitButton;
    @FXML
    private Button NewAppointmentButton;
    @FXML
    private Button DeleteAppointmentButton;
    @FXML
    private Button EditAppointmentButton;
    @FXML
    private Button NewCustomerButton;
    @FXML
    private Button DeleteCustomerButton;
    @FXML
    private Button EditCustomerButton;
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
    private Tab ReportsTab;
    @FXML
    private SplitMenuButton ReportChooser;
    @FXML
    private MenuItem AppointmentTypeMenuItem;
    @FXML
    private MenuItem ConsultantScheduleMenuItem;
    @FXML
    private MenuItem CustomersLocationMenuItem;
    @FXML
    private TableView<?> ReportsTable;
    @FXML
    private TableColumn<?, ?> ReportsDateColumn;
    @FXML
    private TableColumn<?, ?> ReportsStartColumn;
    @FXML
    private TableColumn<?, ?> ReportsEndColumn;
    @FXML
    private TableColumn<?, ?> ReportsCustomerColumn;
    @FXML
    private TableColumn<?, ?> ReportsConsultantColumn;
    @FXML
    private TableColumn<?, ?> ReportsDescriptionColumn;
    @FXML
    private TableColumn<?, ?> ReportsLocationColumn;
    
    private ScreenHelper helper;
    private Database database;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helper = new ScreenHelper();
        database = new Database();
        
        AppointmentViewChooser.getItems().addAll("Week View", "Month View");
        AppointmentViewChooser.getSelectionModel().selectFirst();
        AppointmentTypeChooser.getItems().addAll("Show All Types", new Separator());
        AppointmentTypeChooser.getItems().addAll(database.getAppointmentTypes());
        AppointmentTypeChooser.getSelectionModel().selectFirst();
        
        this.populateAppointmentsTable(database.getAppointments());
        this.populateCustomersTable(database.getCustomers());
    }    
    
    @FXML
    private void NewButtonHandler(ActionEvent event) throws IOException {
        if (TabPane.getSelectionModel().getSelectedItem() == AppointmentsTab) {
            Stage stage = (Stage) NewAppointmentButton.getScene().getWindow();
            helper.nextScreenHandler(stage, "Appointment.fxml");
        } else if (TabPane.getSelectionModel().getSelectedItem() == CustomersTab) {
            Stage stage = (Stage) NewCustomerButton.getScene().getWindow();
            helper.nextScreenHandler(stage, "Customer.fxml");
        }
    }
    
    @FXML
    private void EditButtonHandler(ActionEvent event) throws IOException {
        
        if (TabPane.getSelectionModel().getSelectedItem() == AppointmentsTab) {
            if (AppointmentsTable.getSelectionModel().getSelectedItem() != null) {
                Stage stage = (Stage) EditAppointmentButton.getScene().getWindow();
                AppointmentController controller = (AppointmentController) 
                        helper.nextScreenControllerHandler(stage, "Appointment.fxml");
                Appointment appointment = AppointmentsTable.getSelectionModel().getSelectedItem();
                controller.setAppointment(appointment);
            }
        } else if (TabPane.getSelectionModel().getSelectedItem() == CustomersTab) {
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
            // TODO
        } else if (TabPane.getSelectionModel().getSelectedItem() == CustomersTab) {
            // TODO
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

    @FXML
    private void AppointmentTypeChooserHandler(MouseEvent event) {
    }

    @FXML
    private void AppointmentViewChooserHandler(MouseEvent event) {
    }

    @FXML
    private void ReportChooserHandler(ActionEvent event) {
    }      
    
    public void populateAppointmentsTable(ObservableList<Appointment> list) {
        AppointmentsDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        AppointmentsStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        AppointmentsEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        AppointmentsCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        AppointmentsConsultantColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        AppointmentsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
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
}
