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
    private Button signUpButton; // SignUp বাটনের জন্য

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onHover(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if(btn == loginButton) {
            btn.setStyle("-fx-background-color: #7a22db; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        } else if (btn == signUpButton) {
            btn.setStyle("-fx-background-color: #27ae60; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void onExit(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if(btn == loginButton) {
            btn.setStyle("-fx-background-color: #6a11cb; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
        } else if (btn == signUpButton) {
            btn.setStyle("-fx-background-color: #00c9a7; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");
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

    @FXML
    protected void onSignUpClick() throws IOException {
        // এখানেও তুমি নতুন scene লোড করতে পারো, যদি সাইন আপ page থাকে
        System.out.println("Sign Up clicked!");
    }
}
