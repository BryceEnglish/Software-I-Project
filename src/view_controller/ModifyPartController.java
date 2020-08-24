/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bryce
 */
public class ModifyPartController implements Initializable {
    
    int partIndex;

    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private RadioButton outSourcedRadio;
    @FXML
    private TextField modPartIdTxt;
    @FXML
    private TextField modPartNameTxt;
    @FXML
    private TextField modPartInvTxt;
    @FXML
    private TextField modPartPriceTxt;
    @FXML
    private TextField modPartMaxTxt;
    @FXML
    private TextField modPartMinTxt;
    @FXML
    private TextField modPartMacTxt;
    @FXML
    private Label companyLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pickInHouse(ActionEvent event) {
        companyLabel.setText("Machine ID");
    }

    @FXML
    private void pickOutsourced(ActionEvent event) {
        companyLabel.setText("Company Name");
    }

    @FXML
    private void saveUpdatePart(ActionEvent event) throws IOException {
        
        int id = Integer.parseInt(modPartIdTxt.getText());
        String name = modPartNameTxt.getText();
        int stock = Integer.parseInt(modPartInvTxt.getText());
        Double price = Double.parseDouble(modPartPriceTxt.getText());
        int max = Integer.parseInt(modPartMaxTxt.getText());
        int min = Integer.parseInt(modPartMinTxt.getText());
        
        if (InHouseRadio.isSelected()) {
            int machineId = Integer.parseInt(modPartMacTxt.getText());
            Inventory.updatePart(partIndex, new Inhouse(id, name, price, stock, min, max, machineId));
        }
        
        if (outSourcedRadio.isSelected()) {
            String companyName = modPartMacTxt.getText();
            Inventory.updatePart(partIndex, new Outsourced(id, name, price, stock, min, max, companyName));
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
    private void cancelUpdatePart(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel the part modification, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view_controller/mainscreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
    }
    
    public void sendPart(Part part, int partIndex) {
        
     this.partIndex = partIndex;
        
        if (part instanceof Inhouse) {
            
            Inhouse part1 = (Inhouse) part;
            String machineId = String.valueOf(part1.getMachineId());
            InHouseRadio.setSelected(true);
            modPartMacTxt.setText(machineId);
            companyLabel.setText("Machine Id");
        }
        
        if (part instanceof Outsourced) {
            
            Outsourced part1 = (Outsourced) part;
            String companyName = (part1.getCompanyName());
            outSourcedRadio.setSelected(true);
            modPartMacTxt.setText(companyName);
            companyLabel.setText("Company Name");
        }

        modPartIdTxt.setText(valueOf(Integer.toString(part.getId())));
        modPartNameTxt.setText(String.valueOf(part.getName()));
        modPartInvTxt.setText(String.valueOf(part.getStock()));
        modPartPriceTxt.setText(String.valueOf(part.getPrice()));
        modPartMaxTxt.setText(String.valueOf(part.getMax()));
        modPartMinTxt.setText(String.valueOf(part.getMin())); 
    }
}
