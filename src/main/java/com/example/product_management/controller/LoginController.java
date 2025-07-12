package com.example.product_management.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController implements Controller {

    private Stage stage;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @Override
    public String getTitle() {
        return "Product Management : Login";
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setTitle(getTitle());
    }

    private String getUsername() {
        return username.getText();
    }

    private String getPassword() {
        return password.getText();
    }

    private String hashedPassword(String password) {
        String fixedSalt = BCrypt.gensalt();
        return BCrypt.hashpw(password, fixedSalt);
    }

    private void handleLogin() {
        System.out.println("Username: " + getUsername());
        System.out.println("Hashed Password: " + hashedPassword(getPassword()));
    }

    @FXML
    protected void clickLogin() throws Exception {
        handleLogin();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/Homepage.fxml"));
        Parent root = loader.load();

        HomeController home = loader.getController();
        home.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void backHome() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/product_management/EntryPage.fxml"));
        Parent root = loader.load();

        EntryController entryController = loader.getController();
        entryController.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onHover(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #a25eff; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 30;");
    }

    @FXML
    private void onExit(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #a564ff; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 30;");
    }
}
