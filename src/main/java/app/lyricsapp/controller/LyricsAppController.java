package app.lyricsapp.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LyricsAppController implements Initializable {

    @FXML private Button favoritesButton, lyricsappButton, playlistButton;
    @FXML private ChoiceBox<String> searchChoiceBox, searchChoiceBoxEng, languageChoiceBox;
    @FXML private TextField titleSearchField, artistSearchField, lyricsSearchField;
    private String[] searchSelection = {"Paroles", "Titre/Artiste"};
    private String[] searchSelectionEng = {"Lyrics", "Title/Artist"};
    private String[] languageSelection = {"Langage : FR", "Language : ENG"};

    //    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        searchChoiceBox.setValue("Titre/Artiste");
        searchChoiceBox.getItems().addAll(searchSelection);
        searchChoiceBox.setOnAction(this::searchTitleArtist);
        searchChoiceBox.setOnAction(this::searchLyrics);
        languageChoiceBox.getItems().addAll(languageSelection);

        languageChoiceBox.setOnAction(event -> {
            String language = languageChoiceBox.getValue();
            String fxmlFile = "/app/lyricsapp/view/lyricsapp.fxml"; // par défaut "Langage : FR"
            if (language.equals("Language : ENG")) {
                fxmlFile = "/app/lyricsapp/view/lyricsappeng.fxml";
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage window = (Stage) languageChoiceBox.getScene().getWindow();
                window.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void searchTitleArtist(ActionEvent event){
        String myChoice = searchChoiceBox.getValue();
        if(myChoice.equals("Titre/Artiste")){
            lyricsSearchField.setVisible(false);
            titleSearchField.setVisible(true);
            artistSearchField.setVisible(true);
        } else {
            lyricsSearchField.setVisible(true);
            titleSearchField.setVisible(false);
            artistSearchField.setVisible(false);
        }
    }

    public void searchLyrics(ActionEvent event){
        String myChoice = searchChoiceBox.getValue();
        if(myChoice.equals("Paroles")){
            lyricsSearchField.setVisible(true);
            titleSearchField.setVisible(false);
            artistSearchField.setVisible(false);
        } else {
            lyricsSearchField.setVisible(false);
            titleSearchField.setVisible(true);
            artistSearchField.setVisible(true);
        }
    }

    public void searchTitleArtistEng(ActionEvent event){
        String myChoice = searchChoiceBox.getValue();
        if(myChoice.equals("Title/Artist")){
            lyricsSearchField.setVisible(false);
            titleSearchField.setVisible(true);
            artistSearchField.setVisible(true);
        } else {
            lyricsSearchField.setVisible(true);
            titleSearchField.setVisible(false);
            artistSearchField.setVisible(false);
        }
    }

    public void searchLyricsEng(ActionEvent event){
        String myChoice = searchChoiceBoxEng.getValue();
        if(myChoice.equals("Lyrics")){
            lyricsSearchField.setVisible(true);
            titleSearchField.setVisible(false);
            artistSearchField.setVisible(false);
        } else {
            lyricsSearchField.setVisible(false);
            titleSearchField.setVisible(true);
            artistSearchField.setVisible(true);
        }
    }

    public void switchToSceneFavorites() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/lyricsapp/view/favoris.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) favoritesButton.getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void switchToSceneLyricsApp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/lyricsapp/view/lyricsapp.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) lyricsappButton.getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToScenePlaylist() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/lyricsapp/view/playlist.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) playlistButton.getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fadeInOutText(MouseEvent event) {
        Button button = (Button) event.getSource();
        FadeTransition ft = new FadeTransition(Duration.millis(300), button);
        ft.setCycleCount(1);
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            ft.setFromValue(0.5);
            ft.setToValue(1.0);
            button.setTextFill(Color.WHITE);
        } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            ft.setFromValue(1.0);
            ft.setToValue(0.5);
            button.setTextFill(Color.GRAY);
        }
        ft.setAutoReverse(true);
        ft.play();
    }
}