package com.og.Controllers;

import com.og.FXMLService;
import com.og.MainClasses.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SellerScreenController {
    public Label fullNameLabel;

    List<Product> productsz;

    @FXML
    private TableView<Product> productsInShopTable;

    @FXML
    private TableColumn<Product, String> modelColumn;

    @FXML
    private TableColumn<Product, String> brandColumn;

    @FXML
    private TableColumn<Product, String> typeColumn;

    @FXML
    private TableColumn<Product, Integer> amountColumn;

    public void showAllProductsInShop() {
        productsz = XMLReturn.returnProductsInShop();

        modelColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));

        productsInShopTable.getItems().setAll(productsz);
    }

    public void onButtonLogOutClick(ActionEvent actionEvent) {
        User.activeUser = null;
        FXMLService.loadScreen("StartScreen");
    }

    public void userInfo() {
        if (User.activeUser != null) {
            fullNameLabel.setText(String.format("Position: %s\nFull name: %s", User.activeUser.getPosition(), User.activeUser.getFullName()));
        }
    }

    public void onButtonSellClick(ActionEvent actionEvent) {
        int selectedIndex = productsInShopTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }

        Product selectedProduct = productsz.get(selectedIndex);
        selectedProduct.setAmount(selectedProduct.getAmount() - 1);
        XMLUpdate.amountUpdater(selectedProduct.getModel(), "Products");
        XMLAdd.addSale(selectedProduct.getModel(), selectedProduct.getAmount());
        List<Product> b = XMLReturn.returnProductsInShop();
        List<Product> b1 = XMLReturn.returnProductsIn("Products", "product");
        for (int i = 0; i < b1.size(); i++) {
            if (b1.get(i).getAmount() == 0) {
                b1.get(i).setInShop(false);
            }
        }
        XMLReturn.cleanUp("Products", "products");
        for (int i = 0; i < b1.size(); i++) {
            XMLAdd.addIn(b1.get(i).getModel(), b1.get(i).getBrand(), b1.get(i).getType(), b1.get(i).getAmount(), b1.get(i).isInShop(), "Products", "product");
        }
        productsInShopTable.getItems().setAll(b);
    }

    public void onButtonNeedItClick(ActionEvent actionEvent) {
        int selectedIndex = productsInShopTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }

        Product selectedProduct = productsz.get(selectedIndex);
        XMLAdd.addIn(selectedProduct.getModel(), selectedProduct.getBrand(), selectedProduct.getType(), 5, false, "NeedIt", "need");
        productsInShopTable.getItems().setAll(productsz);
    }

    public void onButtonFromStockClick(ActionEvent actionEvent) {
        XMLAdd.addFromStockToShop();
        SellerScreenController sellerScreenController = FXMLService.loadScreenReturnController("SellerScreen");
        sellerScreenController.userInfo();
        sellerScreenController.showAllProductsInShop();
        XMLReturn.cleanUp("Delivery", "products");
        productsInShopTable.getItems().setAll(productsz);
    }
}
