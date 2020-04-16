package com.og.Controllers;

import com.og.FXMLHelper;
import com.og.MainClasses.XMLAdd;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class AddInStockScreenController {

    public TextField titleField;
    public TextField authorField;
    public TextField genreField;
    public TextField amountField;

    public void onAddButtonClick(ActionEvent actionEvent) {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        int amount = Integer.parseInt(amountField.getText());

        XMLAdd.addIn(title, author, genre, amount, "Stock", "book");

        StoreKeeperScreenController storeKeeperScreenController = FXMLHelper.loadScreenReturnController("StoreKeeperScreen");
        storeKeeperScreenController.userInfo();
        storeKeeperScreenController.showNeedItList();
        storeKeeperScreenController.showBooksInStock();
    }


    public void onBackButtonClick(ActionEvent actionEvent) {
        FXMLHelper.backScreen();
    }
}
