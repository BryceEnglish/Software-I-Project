/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryceenglish;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Bryce
 */
public class Bryceenglish extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/mainscreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static class partIdCounter {

        public static int partIdCount = 3;

    }
    
    public static class prodIdCounter {

        public static int prodIdCount = 3;

    }
 
    public static void main(String[] args) {
        
        Part part1 = new Inhouse(1, "Part 1", 5.00, 5, 5, 300, 111);
        Part part2 = new Inhouse(2, "Part 2", 10.00, 10, 5, 300, 222);
        Part part3 = new Outsourced(3, "Part 3", 15.00, 12, 5, 300, "Company2");
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        
        Product product1 = new Product(1, "Product 1", 5.00, 5, 5, 300); 
        Product product2 = new Product(2, "Product 2", 10.00, 10, 5, 300);
        Product product3 = new Product(3, "Product 3", 15.00, 12, 5, 300);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        
        launch(args);
    }
}
