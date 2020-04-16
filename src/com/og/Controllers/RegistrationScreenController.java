package com.og.Controllers;

import com.og.MainClasses.WorkWIthXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
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

        if (login.trim().length() == 0 || password.trim().length() == 0 || fullName.trim().length() == 0 || position.trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("LogIn Failed");
            alert.setHeaderText("Unable to Log In");
            alert.setContentText("Login or Password were empty\nPlease Try Again.");
            alert.showAndWait();
            loginField.clear();
            passwordField.clear();
            fullNameField.clear();
            positionField.clear();
            return;
        }



        addUser(login, password, position, fullName);
    }
}
