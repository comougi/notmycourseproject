package com.og.Controllers;

import com.og.FXMLService;
import com.og.MainClasses.XMLAdd;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class AddInStockScreenController {

    public TextField modelField;
    public TextField brandField;
    public TextField typeField;
    public TextField amountField;

    public void onAddButtonClick(ActionEvent actionEvent) {
        String model = modelField.getText();
        String brand = brandField.getText();
        String type = typeField.getText();
        int amount = Integer.parseInt(amountField.getText());

        XMLAdd.addIn(model, brand, type, amount,false, "Stock", "product");

        StoreKeeperScreenController storeKeeperScreenController = FXMLService.loadScreenReturnController("StoreKeeperScreen");
        storeKeeperScreenController.userInfo();
        storeKeeperScreenController.showNeedItList();
        storeKeeperScreenController.showProductsInStock();
    }


    public void onBackButtonClick(ActionEvent actionEvent) {
        FXMLService.backScreen();
    }
}
