package com.example.product_management;

import com.example.product_management.controller.EntryController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/EntryPage.fxml"));

        Scene scene = new Scene(loader.load());

        EntryController homeController = loader.getController();
        homeController.setStage(stage);

        stage.setTitle(homeController.getTitle());
        stage.setScene(scene);
        stage.setWidth(1415);
        stage.setHeight(1000);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}