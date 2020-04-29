package com.og;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class FXMLService {
    private static final String pathToScreens = "\\UI\\";
    private static final Stack<Scene> chronologicalScenes = new Stack<>();
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    private static void createScreen(Parent root) {
        chronologicalScenes.push(primaryStage.getScene());
        Scene scene = new Scene(root);
        setScene(scene);
    }

    public static void loadScreen(String screen) {
        try {
            Parent root = FXMLLoader.load(FXMLService.class.getResource(pathToScreens + screen + ".fxml"));
            createScreen(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T loadScreenReturnController(String screen) {
        T controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMLService.class.getResource(pathToScreens + screen + ".fxml"));
            loader.load();
            Parent root = loader.getRoot();
            controller = loader.getController();
            createScreen(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public static void backScreen() {
        if (chronologicalScenes.size() > 1) {
            Scene pop = chronologicalScenes.pop();
            setScene(pop);
        }
    }

    private static void setScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setHeight(primaryStage.getHeight() + 20);
        primaryStage.setWidth(primaryStage.getWidth() + 20);
    }
}