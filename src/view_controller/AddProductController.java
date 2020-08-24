/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import bryceenglish.Bryceenglish;
import bryceenglish.Bryceenglish.prodIdCounter;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
public class AddProductController implements Initializable {
    
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private Button addProductBtn;
    @FXML
    private TextField prodIdTxt;
    @FXML
    private TextField prodNameTxt;
    @FXML
    private TextField prodInvTxt;
    @FXML
    private TextField prodPriceTxt;
    @FXML
    private TextField prodMaxTxt;
    @FXML
    private TextField prodMinTxt;
    @FXML
    private TextField productSearchBox;
    @FXML
    private TableView<Part> partsToPickTable;
    @FXML
    private TableColumn<Part, Integer> partsIdCol;
    @FXML
    private TableColumn<Part, String> partsNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInvCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;
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
        
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        assocPartsTable.setItems(associatedParts);
        
        aPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    void genUID(int idCount) {
        
        if (prodIdCounter.prodIdCount <= 0) {
            prodIdTxt.setText(valueOf(Integer.toString(1)));
            prodIdCounter.prodIdCount = prodIdCounter.prodIdCount + 1;
        }else {
            
            prodIdTxt.setText(valueOf(Integer.toString(prodIdCounter.prodIdCount + 1)));
            prodIdCounter.prodIdCount += 1;
        }
    }

     
    @FXML
    private void saveAddProduct(ActionEvent event) throws IOException {
        
        int id = Integer.parseInt(prodIdTxt.getText());
        String name = prodNameTxt.getText();
        int stock = Integer.parseInt(prodInvTxt.getText());
        Double price = Double.parseDouble(prodPriceTxt.getText());
        int max = Integer.parseInt(prodMaxTxt.getText());
        int min = Integer.parseInt(prodMinTxt.getText());
        
        if (min >= max) {
                
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Minimum value exceeds maximum value");
            alert.showAndWait();
             
        } else {
            Product product = new Product(id, name, price, stock, min, max);
            product.getAllAssociatedParts().addAll(associatedParts);
            Inventory.addProduct(product);
            
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view_controller/mainscreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML
    private void cancelAddProduct(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel the product creation, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view_controller/mainscreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
    }

    @FXML
    private void addProduct(ActionEvent event) {
        
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
}
