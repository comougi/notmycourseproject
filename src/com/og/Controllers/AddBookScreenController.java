package com.og.Controllers;

import com.og.FXMLHelper;
import com.og.MainClasses.WorkWIthXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class AddBookScreenController {

    public TextField titleField;
    public TextField genreField;
    public TextField authorField;

    public void onBackButtonClick(ActionEvent actionEvent) {
        FXMLHelper.backScreen();
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();

        WorkWIthXML.addBook(title, author, genre);
    }
}
