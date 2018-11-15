/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jnsch
 */
public class MainScreenController implements Initializable {

    @FXML
    private AnchorPane AppointmentViewPane;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button EditButton;
    @FXML
    private TabPane TabPane;
    @FXML
    private Tab AppointmentsTab;
    @FXML
    private ChoiceBox<?> AppointmentTypeChooser;
    @FXML
    private TableView<?> AppointmentsTable;
    @FXML
    private TableColumn<?, ?> AppointmentaDateColumn;
    @FXML
    private TableColumn<?, ?> AppointmentaStartColumn;
    @FXML
    private TableColumn<?, ?> AppointmentaEndColumn;
    @FXML
    private TableColumn<?, ?> AppointmentaCustomerColumn;
    @FXML
    private TableColumn<?, ?> AppointmentaConsultantColumn;
    @FXML
    private TableColumn<?, ?> AppointmentaDescriptionColumn;
    @FXML
    private TableColumn<?, ?> AppointmentaLocationColumn;
    @FXML
    private ChoiceBox<?> AppointmentViewChooser;
    @FXML
    private Tab CustomersTab;
    @FXML
    private TableView<?> CustomersTable;
    @FXML
    private TableColumn<?, ?> CustomersNameColumn;
    @FXML
    private TableColumn<?, ?> CustomersAddressColumn;
    @FXML
    private TableColumn<?, ?> CustomersCityColumn;
    @FXML
    private TableColumn<?, ?> CustomersCountryColumn;
    @FXML
    private TableColumn<?, ?> CustomersPostalCodeColumn;
    @FXML
    private TableColumn<?, ?> CustomersPhoneColumn;
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
    @FXML
    private Button NewButton;
    @FXML
    private Button ExitButton;
    
    private ScreenHelper helper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helper = new ScreenHelper();
    }    

    @FXML
    private void DeleteButtonHandler(ActionEvent event) {
    }

    @FXML
    private void EditButtonHandler(ActionEvent event) {
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

    @FXML
    private void NewButtonHandler(ActionEvent event) {
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
    
}
