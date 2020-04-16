package com.og.Controllers;

import com.og.FXMLHelper;
import com.og.MainClasses.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StoreKeeperScreenController {

    public Label fullNameLabel;

    List<Book> books1;
    List<Book> books2;
    @FXML
    private TableView<Book> inStockTable;
    @FXML
    private TableColumn<Book, String> titleStockColumn;
    @FXML
    private TableColumn<Book, Integer> amountStockColumn;
    @FXML
    private TableView<Book> needItTable;

    @FXML
    private TableColumn<Book, String> titleNeedColumn;

    @FXML
    private TableColumn<Book, Integer> amountNeedColumn;


    public void showBooksInStock() {
        books1 = XMLReturn.returnBooksIn("Stock", "book");

        titleStockColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        amountStockColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("amount"));

        inStockTable.getItems().setAll(books1);
    }

    public void showNeedItList() {
        books2 = XMLReturn.returnBooksIn("NeedIt", "need");

        titleNeedColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        amountNeedColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("amount"));

        needItTable.getItems().setAll(books2);
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
        Book selectedBook = books1.get(selectedIndex);
        XMLAdd.addIn(selectedBook.getTitle(), selectedBook.getAuthor(), selectedBook.getGenre(), 5, "Delivery", "book");
        selectedBook.setAmount(selectedBook.getAmount() - 1);
        inStockTable.getItems().setAll(books1);
    }

    public void onButtonLogOutCLick(ActionEvent actionEvent) {
        User.activeUser = null;
        FXMLHelper.loadScreen("StartScreen");
    }


    public void onButtonAcceptNewBooksClick(ActionEvent actionEvent) {
        FXMLHelper.loadScreen("AddInStockScreen");
    }
}

