package com.og.Controllers;

import com.og.FXMLService;
import com.og.MainClasses.Product;
import com.og.MainClasses.User;
import com.og.MainClasses.XMLAdd;
import com.og.MainClasses.XMLReturn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AdminScreenController {


    public Label fullNameLabel;
    List<Product> products;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> modelColumn;

    @FXML
    private TableColumn<Product, String> brandColumn;

    @FXML
    private TableColumn<Product, String> typeColumn;

    public void showAllProducts() {
        products = XMLReturn.returnProductsIn("Products", "product");

        modelColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));

        productsTable.getItems().setAll(products);
    }

    public void userInfo() {
        if (User.activeUser != null) {
            fullNameLabel.setText(String.format("Position: %s\nFull name: %s", User.activeUser.getPosition(), User.activeUser.getFullName()));
        }
    }

    public void addNewProductButtonClick(ActionEvent actionEvent) {
        FXMLService.loadScreen("AddProductScreen");
    }

    public void logOutButton(ActionEvent actionEvent) {
        User.activeUser = null;
        FXMLService.loadScreen("StartScreen");
    }

    public void onButtonBackClick(ActionEvent actionEvent) {
        FXMLService.backScreen();
    }

    public void onButtonDeleteClick(ActionEvent actionEvent) {
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }
        Product selectedProduct = products.get(selectedIndex);
        products.remove(selectedIndex);
        List<Product> b = XMLReturn.returnProductsIn("Products", "product");

        b.remove(selectedIndex);
        XMLReturn.cleanUp("Products", "products");
        for (int i = 0; i < b.size(); i++) {
            XMLAdd.addProduct(b.get(i).getModel(), b.get(i).getBrand(), b.get(i).getType());
        }
        productsTable.getItems().setAll(products);
    }
}
