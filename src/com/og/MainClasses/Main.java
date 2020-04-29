package com.og.MainClasses;

import com.og.FXMLService;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLService.setPrimaryStage(primaryStage);
        FXMLService.loadScreen("StartScreen");
        primaryStage.show();
    }
}