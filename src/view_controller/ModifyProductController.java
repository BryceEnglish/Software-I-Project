/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static view_controller.AddProductController.associatedParts;

/**
 * FXML Controller class
 *
 * @author Bryce
 */
public class ModifyProductController implements Initializable {
    
    int productIndex;

    @FXML
    private TextField modProdIdTxt;
    @FXML
    private TextField modProdNameTxt;
    @FXML
    private TextField modProdInvTxt;
    @FXML
    private TextField modProdPriceTxt;
    @FXML
    private TextField modProdMaxTxt;
    @FXML
    private TextField modProdMinTxt;
    @FXML
    private TextField productSearchBox;
    @FXML
    private TableView<Part> partsToPickTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Part> assocPartsTable;
    @FXML
    private TableColumn<Part, Integer> aPartIdCol;
    @FXML
    private TableColumn<Part, String> aPartNameCol;
    @FXML
    private TableColumn<Part, Integer> aPartInvCol;
    @FXML
    private TableColumn<Part, Double> aPartPriceCol;
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partsToPickTable.setItems(Inventory.getAllParts());
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        assocPartsTable.setItems(associatedParts);
        
        aPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }

       

    @FXML
    private void saveUpdateProduct(ActionEvent event) throws IOException {
        
        int id = Integer.parseInt(modProdIdTxt.getText());
        String name = modProdNameTxt.getText();
        int stock = Integer.parseInt(modProdInvTxt.getText());
        Double price = Double.parseDouble(modProdPriceTxt.getText());
        int max = Integer.parseInt(modProdMaxTxt.getText());
        int min = Integer.parseInt(modProdMinTxt.getText());
        
        if (min >= max) {
                
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Minimum value exceeds maximum value");
            alert.showAndWait();
             
        } else {
            
            Inventory.updateProduct(productIndex, new Product(id, name, price, stock, min, max));
        
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view_controller/mainscreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML
    private void cancelUpdateProduct(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel the product modification, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view_controller/mainscreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
    }

    @FXML
    private void addUpdateProduct(ActionEvent event) {
        
        Part selectedRows = partsToPickTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedRows);
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
        
        Part selectedRows = assocPartsTable.getSelectionModel().getSelectedItem();
        
        associatedParts.removeAll(selectedRows);
        }
    }

    @FXML
    private void lookupProduct(ActionEvent event) {
        
        String parts = productSearchBox.getText();
        for(Part part:Inventory.getAllParts()) {
            if(part.getName().equals(parts) || Integer.toString(part.getId()).equals(parts))partsToPickTable.getSelectionModel().select(part);
        }
    }
    
    public void sendProduct(Product product, int productIndex) {
        
        this.productIndex = productIndex;
        
        modProdIdTxt.setText(valueOf(Integer.toString(product.getId())));
        modProdNameTxt.setText(String.valueOf(product.getName()));
        modProdInvTxt.setText(String.valueOf(product.getStock()));
        modProdPriceTxt.setText(String.valueOf(product.getPrice()));
        modProdMaxTxt.setText(String.valueOf(product.getMax()));
        modProdMinTxt.setText(String.valueOf(product.getMin()));  
        
    }
}
