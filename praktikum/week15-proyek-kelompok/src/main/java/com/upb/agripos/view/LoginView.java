package com.upb.agripos.view;

import com.upb.agripos.styles.StyleConstants;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * View Login untuk autentikasi user.
 */
public class LoginView {
    private Stage stage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Label errorLabel;
    private Runnable onLoginSuccess;

    public LoginView(Stage stage) {
        this.stage = stage;
    }

    /**
     * Membangun UI login dengan desain hijau.
     */
    public Scene buildUI() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setStyle(StyleConstants.gradient(StyleConstants.COLOR_SUCCESS, StyleConstants.COLOR_SECONDARY_MEDIUM));

        // Title
        Label titleLabel = new Label("AGRI-POS");
        titleLabel.setStyle(StyleConstants.combine(
                "-fx-font-size: 32;",
                "-fx-font-weight: bold;",
                "-fx-text-fill: white;"
        ));

        Label subtitleLabel = new Label("Sistem Point of Sale Pertanian");
        subtitleLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #d1fae5;");

        // Login Form Container
        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(30));
        formBox.setStyle(StyleConstants.combine(
                "-fx-background-color: white;",
                "-fx-border-radius: 10;",
                "-fx-background-radius: 10;",
                StyleConstants.shadow(10, 0, 0)
        ));
        formBox.setMaxWidth(400);

        // Username
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle(StyleConstants.LABEL_FORM);
        usernameField = new TextField();
        usernameField.setPromptText("Masukkan username");
        usernameField.setStyle(StyleConstants.TEXTFIELD_DEFAULT);

        // Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle(StyleConstants.LABEL_FORM);
        passwordField = new PasswordField();
        passwordField.setPromptText("Masukkan password");
        passwordField.setStyle(StyleConstants.TEXTFIELD_DEFAULT);

        // Error Label
        errorLabel = new Label();
        errorLabel.setStyle(StyleConstants.LABEL_ERROR);

        // Login Button
        loginButton = new Button("LOGIN");
        loginButton.setPrefWidth(Double.MAX_VALUE);
        loginButton.setStyle(StyleConstants.combine(
                StyleConstants.BUTTON_PRIMARY,
                "-fx-font-size: 14;"
        ));
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
                StyleConstants.combine(
                        "-fx-background-color: #059669;",
                        "-fx-text-fill: white;",
                        "-fx-padding: 12px;",
                        "-fx-font-size: 14px;",
                        "-fx-font-weight: bold;",
                        "-fx-border-radius: 5;",
                        "-fx-background-radius: 5;",
                        "-fx-cursor: hand;"
                )));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
                StyleConstants.combine(
                        StyleConstants.BUTTON_PRIMARY,
                        "-fx-font-size: 14;"
                )));

        formBox.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                errorLabel,
                loginButton
        );

        HBox centerBox = new HBox();
        centerBox.setAlignment(javafx.geometry.Pos.CENTER);
        centerBox.getChildren().add(formBox);

        VBox contentBox = new VBox(30);
        contentBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        contentBox.getChildren().addAll(titleLabel, subtitleLabel, centerBox);

        root.getChildren().add(contentBox);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        return new Scene(root, 800, 600);
    }

    // Getter methods
    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getRole() {
        return ""; // Role akan di-detect otomatis dari database
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setErrorMessage(String message) {
        errorLabel.setText(message);
    }

    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
        errorLabel.setText("");
    }
}
