package com.og.Controllers;

import com.og.FXMLHelper;
import com.og.MainClasses.Book;
import com.og.MainClasses.User;
import com.og.MainClasses.WorkWIthXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SellerScreenController {
    public Label fullNameLabel;

    List<Book> books;

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
        books = WorkWIthXML.returnBooksInShop();

        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("amount"));

        booksInShopTable.getItems().setAll(books);
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

        Book selectedBook = books.get(selectedIndex);
        selectedBook.setAmount(selectedBook.getAmount() - 1);
        WorkWIthXML.updater(selectedBook.getTitle());
        booksInShopTable.getItems().setAll(books);
    }
}
