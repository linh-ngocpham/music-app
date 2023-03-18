package app.lyricsapp;

import app.lyricsapp.controller.LyricsAppController;
import app.lyricsapp.controller.RunCLI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;

import static app.lyricsapp.controller.LyricsAppController.favoriteList;
import static app.lyricsapp.controller.RunCLI.playlists;


public class LyricsApp extends Application {
    public CheckBox goodSongCheckbox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/app/lyricsapp/view/lyricsapp.fxml"));
        primaryStage.setTitle("Cherchez une musique !");
        primaryStage.sizeToScene();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        Image Image = new Image(new FileInputStream("src/main/java/app/lyricsapp/logo.png"));
        primaryStage.getIcons().add(Image);
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Window is closing");
            try {
                favoriteList.saveAll(RunCLI.playlists);
                favoriteList.saveFavorites(favoriteList);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


