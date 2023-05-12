package com.example.cmis202project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Login extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "pass";
    private static final String TABLE_NAME = "users";
    private Text actiontarget;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Login Page");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        Label password = new Label("Password:");
        grid.add(password, 0, 2);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Button loginButton = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);

        actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        loginButton.setOnAction(e -> {
            String userNameValue = userTextField.getText();
            String passwordValue = passwordField.getText();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String query = "SELECT * FROM " + TABLE_NAME + " WHERE username=? AND password=?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, userNameValue);
                stmt.setString(2, passwordValue);

                ResultSet rs = stmt.executeQuery();

                if (userNameValue.equals(DB_USER) && passwordValue.equals(DB_PASSWORD) ) {
                    actiontarget.setFill(Color.GREEN);
                    actiontarget.setText("Login successful!");
                } else {
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("Login failed. Please try again.");
                }

                rs.close();
                stmt.close();
                conn.close();

            } catch (Exception d) {
                d.printStackTrace();
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
