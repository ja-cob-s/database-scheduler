/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * The purpose of this class is to handle tasks common to all screen controllers
 * as to avoid writing redundant code for each screen controller
 * @author jnsch
 */
public class ScreenHelper {
    private boolean validInput; // Flag set to false if any field is invalid
    private String exceptionString;
    
    public ScreenHelper() {
        this.validInput = true;
        this.exceptionString = "";
    }
    
    public boolean getValidInput() {
        return this.validInput;
    }
    
    public String getExceptionString() {
        return this.exceptionString;
    }
    
    public void setValidInput(boolean b) {
        this.validInput = b;
    }
    
    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }
    
    public boolean showConfirmationDialog(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText(s);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }
    
    public void showAlertDialog(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
    
    public void showWarningDialog(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
    
    public boolean IOExceptionHandler(String s) {
        this.setExceptionString(this.getExceptionString() + s + "\n");
        return false;
    }
    
    public void nextScreenHandler(Stage stage, String s) throws IOException {
        // Loads the next screen
        Parent root;   
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public Object nextScreenControllerHandler(Stage stage, String s) throws IOException {
        // Loads the next screen but also passes an object to the next screen
        Parent root;   
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        return loader.getController();
    }
    
    public boolean emptyStringHandler (String s, String field) {
        // Checks if string is empty
        String nullString = null;
        String empty = "";
        
        if (s.equals(nullString) || s.equals(empty)) {
            return this.IOExceptionHandler(field + " cannot be empty.");
        } else {
            return true;
        }
    }
    
    public int getInt(String s, String IOExceptionText) {
        int i = 0;
        try { i = Integer.parseInt(s); }
        catch(Exception e) { this.setValidInput(IOExceptionHandler("Invalid input in: " + IOExceptionText)); }
        return i;
    }
    
    public double getDouble(String s, String IOExceptionText) {
        double d = 0;
        try { d = Double.parseDouble(s); }
        catch(Exception e) { this.setValidInput(IOExceptionHandler("Invalid input in: " + IOExceptionText)); }
        return d;
    }
    
    public String getString(String s, String IOExceptionText) {
        boolean tmp;
        tmp = emptyStringHandler(s, IOExceptionText);
        if (!tmp) { this.setValidInput(false); }
        return s;
    }
}
