package com.og.Controllers;

import com.og.MainClasses.WorkWIthXML;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StartScreenController extends WorkWIthXML{
    public PasswordField passwordField;
    public TextField loginField;

    public void onButtonLogInClick(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();


        read(login,password);
    }
}
