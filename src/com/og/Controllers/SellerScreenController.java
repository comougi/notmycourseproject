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
        XMLAdd.addSale(selectedBook.getTitle(), selectedBook.getAmount());
        List<Book> b = XMLReturn.returnBooksInShop();
        List<Book> b1 = XMLReturn.returnBooksIn("Books", "book");
        for (int i = 0; i < b1.size(); i++) {
            if (b1.get(i).getAmount() == 0) {
                b1.get(i).setInShop(false);
            }
        }
        XMLReturn.cleanUp("Books", "books");
        for (int i = 0; i < b1.size(); i++) {
            XMLAdd.addIn(b1.get(i).getTitle(), b1.get(i).getAuthor(), b1.get(i).getGenre(), b1.get(i).getAmount(), b1.get(i).isInShop(), "Books", "book");
        }
        booksInShopTable.getItems().setAll(b);
    }

    public void onButtonNeedItClick(ActionEvent actionEvent) {
        int selectedIndex = booksInShopTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }

        Book selectedBook = booksz.get(selectedIndex);
        XMLAdd.addIn(selectedBook.getTitle(), selectedBook.getAuthor(), selectedBook.getGenre(), 5, false, "NeedIt", "need");
        booksInShopTable.getItems().setAll(booksz);
    }

    public void onButtonFromStockClick(ActionEvent actionEvent) {
        XMLAdd.addFromStockToShop();
        SellerScreenController sellerScreenController = FXMLHelper.loadScreenReturnController("SellerScreen");
        sellerScreenController.userInfo();
        sellerScreenController.showAllBooksInShop();
        XMLReturn.cleanUp("Delivery", "books");
        booksInShopTable.getItems().setAll(booksz);
    }
}
