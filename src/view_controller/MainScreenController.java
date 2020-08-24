/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import bryceenglish.Bryceenglish.partIdCounter;
import bryceenglish.Bryceenglish.prodIdCounter;
import java.io.IOException;
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

public class MainScreenController implements Initializable {
    
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TextField partSearchBox;
    @FXML
    private Button addProductBtn;
    @FXML
    private TextField productSearchBox;
    @FXML
    private Button exitBtn;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private Button deleteProductBtn;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private Button updateProductBtn;
    @FXML
    private Button addPartBtn;
    @FXML
    private Button updatePartBtn;
    @FXML
    private Button partSearchBtn;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private Button productSearchBtn;
    @FXML
    private Button deletePartBtn;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    
    public void initialize(URL url, ResourceBundle rb) {
        
        partsTable.setItems(Inventory.getAllParts());
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsTable.setItems(Inventory.getAllProducts());
        
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    void addPart(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_controller/addpart.fxml"));
        loader.load();
        
        AddPartController ADMController = loader.getController();
        ADMController.genUID(partIdCounter.partIdCount);
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.showAndWait();
    }

    @FXML
    void updatePart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_controller/modifypart.fxml"));
        loader.load();
        
        ModifyPartController ADMController = loader.getController();
        ADMController.sendPart(partsTable.getSelectionModel().getSelectedItem(), partsTable.getSelectionModel().getSelectedIndex());
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.showAndWait();
    }

    @FXML
    void deletePart(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            
        ObservableList<Part> selectedRows, allParts;
        allParts = partsTable.getItems();
        
        selectedRows = partsTable.getSelectionModel().getSelectedItems();
        
        allParts.removeAll(selectedRows);
        }
    }

    @FXML
    void lookupPart(ActionEvent event) {
        String parts = partSearchBox.getText();
        for(Part part:Inventory.getAllParts()) {
            if(part.getName().equals(parts) || Integer.toString(part.getId()).equals(parts))partsTable.getSelectionModel().select(part);
        }
    }

    @FXML
    void addProduct(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_controller/addproduct.fxml"));
        loader.load();
        
        AddProductController ADMController = loader.getController();
        ADMController.genUID(prodIdCounter.prodIdCount);
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.showAndWait();
    }

    @FXML
    void updateProduct(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_controller/modifyproduct.fxml"));
        loader.load();
        
        ModifyProductController ADMController = loader.getController();
        ADMController.sendProduct(productsTable.getSelectionModel().getSelectedItem(), productsTable.getSelectionModel().getSelectedIndex());
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.showAndWait();
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected product, do you want to continue?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK) {
            
        ObservableList<Product> selectedRows, allProducts;
        allProducts = productsTable.getItems();
        
        selectedRows = productsTable.getSelectionModel().getSelectedItems();
        
        allProducts.removeAll(selectedRows);
        }
    }

    @FXML
    void lookupProduct(ActionEvent event) {
        
        String products = productSearchBox.getText();
        for(Product product:Inventory.getAllProducts()) {
            if(product.getName().equals(products) || Integer.toString(product.getId()).equals(products))productsTable.getSelectionModel().select(product);
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        System.exit(0);
    }
}


