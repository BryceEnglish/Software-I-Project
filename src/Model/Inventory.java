/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Part;

/**
 *
 * @author Bryce
 */
public class Inventory {
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
   
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    
    public static void addPart(int index, Part selectedPart) {
        allParts.add(index, selectedPart);
    }
    
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partid) {
        return null;
        
    }
    
    public static Product lookupProduct(int productid) {
        return null;
        
    }
    
    public static ObservableList<Part> lookupPart(String partName) {
        return allParts;
        
    }
    
    /*public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts;
        
    }*/
    
    public static void updatePart(int index, Part selectedPart) {
    allParts.set(index, selectedPart);
        
    }
    
    public static void updateProduct(int index, Product selectedProduct) {
    allProducts.set(index, selectedProduct);
        
    }
    
    public static boolean deletePart(Part selectedPart) {
        return false;
        
    }
    
    public static boolean deleteProduct(Product selectedProduct) {
        return false;
        
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    public static ObservableList<Part> getAllFilteredParts() {
        return filteredParts;
    }

    public static ObservableList<Product> getAllFilteredProducts() {
        return filteredProducts;
    }
    
}
