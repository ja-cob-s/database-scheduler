/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Database;
import model.User;

/**
 * FXML Controller class that controls the Login screen
 * Default credentials are username "test" and password "test"
 * @author jnsch
 */
public class LoginController implements Initializable {

    @FXML
    private Button ExitButton;
    @FXML
    private TextField UserNameField;
    @FXML
    private Label ErrorLabel;
    @FXML
    private Button LoginButton;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Label WelcomeMsgLine3;
    @FXML
    private Label WelcomeMsgLine2;
    @FXML
    private Label WelcomeMsgLine1;
    @FXML
    private Label LoginPrompt;
    
    private Database database;
    private ResourceBundle rb;
    private String exitMsg;
    private String exitTitle;
    private String noInput;
    private String invalidInput;

    /**
     * Initializes the controller class for login screen
     * Bilingual in English and German fulfills REQUIREMENT A
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = new Database();
        Locale locale = Locale.getDefault(); // Comment to test other language
        //Locale locale = new Locale("de"); // Uncomment to test other language
        rb = ResourceBundle.getBundle("util/login", locale);
        WelcomeMsgLine1.setText(rb.getString("welcomemsg1"));
        WelcomeMsgLine2.setText(rb.getString("welcomemsg2"));
        WelcomeMsgLine3.setText(rb.getString("welcomemsg3"));
        LoginPrompt.setText(rb.getString("prompt"));
        UserNameField.setPromptText(rb.getString("username"));
        PasswordField.setPromptText(rb.getString("password"));
        LoginButton.setText(rb.getString("login"));
        ExitButton.setText(rb.getString("exit"));
        exitMsg = rb.getString("exitmsg");
        exitTitle = rb.getString("exit");
        noInput = rb.getString("noinput");
        invalidInput = rb.getString("invalidinput");
        
        ErrorLabel.setVisible(false);
    }    

    @FXML
    private void ExitButtonHandler(ActionEvent event) {
        /*Terminates the application when the exit button is pressed
          Displays confirmation dialog first*/
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(exitTitle);
        alert.setHeaderText(null);
        alert.setContentText(exitMsg);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // User chose OK
            System.exit(0); 
        } 
    }

    @FXML
    private void LoginButtonHandler(ActionEvent event) throws IOException {
        String userName = UserNameField.getText();
        String password = PasswordField.getText();
        
        if (userName.isEmpty() || password.isEmpty()) {
            ErrorLabel.setText(noInput);
            ErrorLabel.setVisible(true);
        } else {
            User user = database.validateUser(userName, password);

            if (user == null) {
                ErrorLabel.setText(invalidInput);
                ErrorLabel.setVisible(true);
            } else {
                Database.setCurrentUser(user);
                Stage stage = (Stage) LoginButton.getScene().getWindow(); 
                Parent root;   
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
    
}
