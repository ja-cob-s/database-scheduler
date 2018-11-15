/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private TableColumn<?, ?> CustomerNameColumn;
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
    private ChoiceBox<?> TypeChooser;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        helper = new ScreenHelper();
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
    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) {
    }
    
}
