/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.scheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;
import util.DBConnection;

/**
 *
 * @author jnsch
 */
public class DatabaseScheduler extends Application {
    
   
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Scheduler");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        DBConnection.connect();  
        Database database = new Database(); 
        database.getAppointmentsList();
        database.getAppointmentTypesList();
        database.getCustomersList();
        launch(args);        
        DBConnection.disconnect();
    }
    
}
