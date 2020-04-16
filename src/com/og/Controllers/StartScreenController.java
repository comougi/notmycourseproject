package com.og.Controllers;

import com.og.FXMLHelper;
import com.og.MainClasses.WorkWIthXML;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StartScreenController extends WorkWIthXML {
    public PasswordField passwordField;
    public TextField loginField;

    public void onButtonLogInClick(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        validation(login, password);
    }


    public void onButtonRegistrationClick(ActionEvent actionEvent) {
        FXMLHelper.loadScreen("RegistrationScreen");
    }
}
