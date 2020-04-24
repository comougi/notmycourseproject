package com.og.Controllers;

import com.og.FXMLHelper;
import com.og.MainClasses.Book;
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
    List<Book> books;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    public void showAllBooks() {
        books = XMLReturn.returnBooksIn("Books", "book");

        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));

        booksTable.getItems().setAll(books);
    }

    public void userInfo() {
        if (User.activeUser != null) {
            fullNameLabel.setText(String.format("Position: %s\nFull name: %s", User.activeUser.getPosition(), User.activeUser.getFullName()));
        }
    }

    public void addNewBookButtonClick(ActionEvent actionEvent) {
        FXMLHelper.loadScreen("AddBookScreen");
    }

    public void logOutButton(ActionEvent actionEvent) {
        User.activeUser = null;
        FXMLHelper.loadScreen("StartScreen");
    }

    public void onButtonBackClick(ActionEvent actionEvent) {
        FXMLHelper.backScreen();
    }

    public void onButtonDeleteClick(ActionEvent actionEvent) {
        int selectedIndex = booksTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }
        Book selectedBook = books.get(selectedIndex);
        books.remove(selectedIndex);
        List<Book> b = XMLReturn.returnBooksIn("Books", "book");

        b.remove(selectedIndex);
        XMLReturn.cleanUp("Books", "books");
        for (int i = 0; i < b.size(); i++) {
            XMLAdd.addBook(b.get(i).getTitle(), b.get(i).getAuthor(), b.get(i).getGenre());
        }
        booksTable.getItems().setAll(books);
    }
}
