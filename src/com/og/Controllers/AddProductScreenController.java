package com.og.Controllers;

import com.og.FXMLService;
import com.og.MainClasses.XMLAdd;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class AddProductScreenController {

    public TextField modelField;
    public TextField typeField;
    public TextField brandField;

    public void onBackButtonClick(ActionEvent actionEvent) {
        FXMLService.backScreen();
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        String model = modelField.getText();
        String brand = brandField.getText();
        String type = typeField.getText();

        XMLAdd.addProduct(model, brand, type);
    }
}
