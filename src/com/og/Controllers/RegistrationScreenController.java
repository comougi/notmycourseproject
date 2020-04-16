package com.og.Controllers;

import com.og.MainClasses.WorkWIthXML;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationScreenController extends WorkWIthXML {

    public TextField loginField;
    public PasswordField passwordField;
    public TextField fullNameField;
    public TextField positionField;


    public void onButtonRegClick(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String position = positionField.getText();

        addUser(login, password, position, fullName);

    }
}
