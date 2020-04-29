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

public class StoreKeeperScreenController {

    public Label fullNameLabel;

    List<Product> products1;
    List<Product> products2;
    @FXML
    private TableView<Product> inStockTable;
    @FXML
    private TableColumn<Product, String> modelStockColumn;
    @FXML
    private TableColumn<Product, Integer> amountStockColumn;
    @FXML
    private TableView<Product> needItTable;

    @FXML
    private TableColumn<Product, String> modelNeedColumn;

    @FXML
    private TableColumn<Product, Integer> amountNeedColumn;


    public void showProductsInStock() {
        products1 = XMLReturn.returnProductsIn("Stock", "product");

        modelStockColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
        amountStockColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));

        inStockTable.getItems().setAll(products1);
    }

    public void showNeedItList() {
        products2 = XMLReturn.returnProductsIn("NeedIt", "need");

        modelNeedColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
        amountNeedColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));

        needItTable.getItems().setAll(products2);
    }

    public void userInfo() {
        if (User.activeUser != null) {
            fullNameLabel.setText(String.format("Position: %s\nFull name: %s", User.activeUser.getPosition(), User.activeUser.getFullName()));
        }
    }


    public void onButtonSendToShopClick(ActionEvent actionEvent) {
        int selectedIndex = inStockTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }
        Product selectedProduct = products1.get(selectedIndex);
        XMLAdd.addIn(selectedProduct.getModel(), selectedProduct.getBrand(), selectedProduct.getType(), 1, false, "Delivery", "book");
        selectedProduct.setAmount(selectedProduct.getAmount() - 1);
        inStockTable.getItems().setAll(products1);
    }

    public void onButtonLogOutCLick(ActionEvent actionEvent) {
        User.activeUser = null;
        FXMLService.loadScreen("StartScreen");
    }


    public void onButtonAcceptNewBooksClick(ActionEvent actionEvent) {
        FXMLService.loadScreen("AddInStockScreen");
    }

    public void onButtonClearClick(ActionEvent actionEvent) {
        int selectedIndex = needItTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }
        Product selectedProduct = products2.get(selectedIndex);
        products2.remove(selectedIndex);
        List<Product> b = XMLReturn.returnProductsIn("NeedIt", "need");

        b.remove(selectedIndex);
        XMLReturn.cleanUp("NeedIt", "needs");
        for (int i = 0; i < b.size(); i++) {
            XMLAdd.addIn(b.get(i).getModel(), b.get(i).getBrand(), b.get(i).getType(), b.get(i).getAmount(), b.get(i).isInShop(), "NeedIt", "need");
        }
        needItTable.getItems().setAll(products2);
    }
}

