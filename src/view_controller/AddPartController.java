/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import bryceenglish.Bryceenglish.partIdCounter;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bryce
 */
public class AddPartController implements Initializable {
    
    int partIndex;

    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private ToggleGroup addPartTg;
    @FXML
    private RadioButton outSourcedRadio;
    @FXML
    private Button savePartBtn;
    @FXML
    private Button cancelPartBtn;
    @FXML
    private TextField partIdTxt;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField partInvTxt;
    @FXML
    private TextField partPriceTxt;
    @FXML
    private TextField partMaxTxt;
    @FXML
    private TextField partMinTxt;
    @FXML
    private TextField partMacTxt;
    @FXML
    private Label companyLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void pickInhouse(ActionEvent event) {
        companyLabel.setText("Machine ID");
    }

    @FXML
    private void pickOutsourced(ActionEvent event) {
        companyLabel.setText("Company Name");
    }
    
    void genUID(int idCount) {
        
        if (partIdCounter.partIdCount <= 0) {
            partIdTxt.setText(valueOf(Integer.toString(1)));
            partIdCounter.partIdCount = partIdCounter.partIdCount + 1;
        }else {
            
            partIdTxt.setText(valueOf(Integer.toString(partIdCounter.partIdCount + 1)));
            partIdCounter.partIdCount += 1;
        }
    }

    @FXML
    private void saveAddPart(ActionEvent event) throws IOException {
        
        
        int id = Integer.parseInt(partIdTxt.getText());
        String name = partNameTxt.getText();
        int stock = Integer.parseInt(partInvTxt.getText());
        Double price = Double.parseDouble(partPriceTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());
        
        if (inHouseRadio.isSelected()) {
            int machineId = Integer.parseInt(partMacTxt.getText());
            Inventory.addPart(partIndex, new Inhouse(id, name, price, stock, min, max, machineId));
        }
        
        if (outSourcedRadio.isSelected()) {
            String companyName = partMacTxt.getText();
            Inventory.addPart(partIndex, new Outsourced(id, name, price, stock, min, max, companyName));
        }
        
        if (min >= max) {
                
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Minimum value exceeds maximum value");
            alert.showAndWait();
             
        } else {
            
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view_controller/mainscreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
        
    }

    @FXML
    private void CancelAddPart(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel the part creation, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view_controller/mainscreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
        
    }
    
}