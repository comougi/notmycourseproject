package com.og.Controllers;

import com.og.FXMLService;
import com.og.MainClasses.XMLReturn;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StartScreenController {
    public PasswordField passwordField;
    public TextField loginField;

    public void onButtonLogInClick(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.trim().length() == 0 || password.trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("LogIn Failed");
            alert.setHeaderText("Unable to Log In");
            alert.setContentText("Login or Password were empty\nPlease Try Again.");
            alert.showAndWait();
            loginField.clear();
            passwordField.clear();
            return;
        }
        XMLReturn.validation(login, password);
    }


    public void onButtonRegistrationClick(ActionEvent actionEvent) {
        FXMLService.loadScreen("RegistrationScreen");
    }
}
