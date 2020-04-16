package com.og.Controllers;

import com.og.MainClasses.User;
import javafx.scene.control.Label;

public class StoreKeeperScreenController {

    public Label fullNameLabel;

    public void userInfo() {
        if (User.activeUser != null) {
            fullNameLabel.setText(String.format("Position: %s\nFull name: %s", User.activeUser.getPosition(), User.activeUser.getFullName()));
        }
    }


}

