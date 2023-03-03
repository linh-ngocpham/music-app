package app.lyricsapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;


public class LyricsApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/app/lyricsapp/view/lyricsapp.fxml"));
        primaryStage.setTitle("Cherchez une musique !");
        primaryStage.sizeToScene();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        Image Image = new Image(new FileInputStream("src/main/java/app/lyricsapp/logo.png"));
        primaryStage.getIcons().add(Image);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


