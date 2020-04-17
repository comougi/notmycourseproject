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

public class SellerScreenController {
    public Label fullNameLabel;

    List<Book> booksz;

    @FXML
    private TableView<Book> booksInShopTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    private TableColumn<Book, Integer> amountColumn;

    public void showAllBooksInShop() {
        booksz = XMLReturn.returnBooksInShop();

        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("amount"));

        booksInShopTable.getItems().setAll(booksz);
    }

    public void onButtonLogOutClick(ActionEvent actionEvent) {
        User.activeUser = null;
        FXMLHelper.loadScreen("StartScreen");
    }

    public void userInfo() {
        if (User.activeUser != null) {
            fullNameLabel.setText(String.format("Position: %s\nFull name: %s", User.activeUser.getPosition(), User.activeUser.getFullName()));
        }
    }

    public void onButtonSellClick(ActionEvent actionEvent) {
        int selectedIndex = booksInShopTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }

        Book selectedBook = booksz.get(selectedIndex);
        selectedBook.setAmount(selectedBook.getAmount() - 1);
        XMLUpdate.amountUpdater(selectedBook.getTitle(), "Books");
        booksInShopTable.getItems().setAll(booksz);
        XMLAdd.addSale(selectedBook.getTitle(), selectedBook.getAmount());
    }

    public void onButtonNeedItClick(ActionEvent actionEvent) {
        int selectedIndex = booksInShopTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }

        Book selectedBook = booksz.get(selectedIndex);
        XMLAdd.addIn(selectedBook.getTitle(), selectedBook.getAuthor(), selectedBook.getGenre(), 5, "NeedIt", "need");
        booksInShopTable.getItems().setAll(booksz);
    }

    public void onButtonFromStockClick(ActionEvent actionEvent) {
        XMLAdd.addFromStockToShop();
        SellerScreenController sellerScreenController = FXMLHelper.loadScreenReturnController("SellerScreen");
        sellerScreenController.userInfo();
        sellerScreenController.showAllBooksInShop();
        booksInShopTable.getItems().setAll(booksz);
    }
}
