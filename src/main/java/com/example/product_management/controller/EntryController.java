package com.example.product_management.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class EntryController implements Controller {

    private Stage stage;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onHover(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if(btn == loginButton) {
            btn.setStyle("-fx-background-color: #009c83; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        } else if (btn == signUpButton) {
            btn.setStyle("-fx-background-color: #246a94; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void onExit(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if(btn == loginButton) {
            btn.setStyle("-fx-background-color:  #00c9a7; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        } else if (btn == signUpButton) {
            btn.setStyle("-fx-background-color:  #3498db; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        }
    }

    @Override
    public String getTitle() {
        return "Product Management : Home";
    }

    @FXML
    protected void onLoginClick() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Login Error");
            e.printStackTrace();
        }
    }
}
