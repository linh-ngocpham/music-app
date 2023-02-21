package app.lyricsapp.controller;

import app.lyricsapp.model.Song;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static app.lyricsapp.controller.RunCLI.replaceAllAPI;
import static app.lyricsapp.model.ReadXML.*;

public class LyricsAppController implements Initializable {

    @FXML private Button favoritesButton, lyricsappButton, playlistButton;
    @FXML private ChoiceBox<String> searchChoiceBox, searchChoiceBoxEng, languageChoiceBox;
    @FXML private TextField titleSearchField, artistSearchField, lyricsSearchField;
    @FXML private AnchorPane presentationTile, rootElement, infoAnchorPane;
    @FXML private Label labelTest;
    @FXML private ScrollBar scrollBar;
    @FXML private VBox vbox;
    private String[] searchSelection = {"Paroles", "Titre/Artiste"};
    private String[] searchSelectionEng = {"Lyrics", "Title/Artist"};
    private String[] languageSelection = {"Langage : FR", "Language : ENG"};
    static List<Song> songList = new ArrayList<>();

    //    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        searchChoiceBox.setValue("Titre/Artiste");
        searchChoiceBox.getItems().addAll(searchSelection);
        searchChoiceBox.setOnAction(this::searchTitleArtist);
        searchChoiceBox.setOnAction(this::searchLyrics);
        languageChoiceBox.getItems().addAll(languageSelection);
        switchLanguage();
    }

    @FXML
    public void onEnter(KeyEvent event) throws IOException, ParserConfigurationException, SAXException {
        String searchMode = searchChoiceBox.getValue();
        if (event.getCode() == KeyCode.ENTER) {
            if(searchMode.equals("Titre/Artiste")) {
                String input1 = titleSearchField.getText();
                String input2 = artistSearchField.getText();
                String title = replaceAllAPI(input1);
                String artist = replaceAllAPI(input2);
                String result = readXMLSong(artist, title, songList);
                displayResults(result);
            } else {
                String input3 = lyricsSearchField.getText();
                String lyrics = replaceAllAPI(input3);
                String result = readXMLSongLyric(lyrics, songList);
                displayResults(result);
            }
            clearSearchFields();
            presentationTile.setVisible(false);
            infoAnchorPane.setVisible(true);
        }
    }

    public void getLyricsApi(Song songs) {
        try {
            DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder db1 = dbf1.newDocumentBuilder();
            Document document = db1.parse(getsong(songs));
            document.getDocumentElement().normalize();
            NodeList nList1 = document.getElementsByTagName("GetLyricResult");
            for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
                Node nNode1 = nList1.item(temp1);
                if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement1 = (Element) nNode1;
                    songs.setLyric(eElement1.getElementsByTagName("Lyric").item(0).getTextContent());
                }
            }
            labelTest.setText(songs.getLyric());

        } catch(IOException | ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayResults(String result) {
        labelTest.setText("");
        vbox.getChildren().clear();
        String[] lines = result.split("\n");
        for (int i = 0; i < lines.length; i++) {
            Button button = new Button(lines[i]);
            button.setPrefWidth(Double.MAX_VALUE);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setId("button"); // Set ID for the button
            button.setOnAction(e -> {
                Song selectedSong = songList.get(vbox.getChildren().indexOf(button));
                getLyricsApi(selectedSong);
                vbox.getChildren().clear();
            });
            vbox.getChildren().add(button);
        }
    }

    public void switchLanguage() {
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

    private void clearSearchFields() {
        titleSearchField.setText("");
        artistSearchField.setText("");
        lyricsSearchField.setText("");
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
        labelTest.setText("");
        vbox.getChildren().clear();
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
        labelTest.setText("");
        vbox.getChildren().clear();
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
        } catch (NullPointerException e) {
            System.err.println("NullPointerException caught: " + e.getMessage());
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
        } catch (NullPointerException e) {
            System.err.println("NullPointerException caught: " + e.getMessage());
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
        } catch (NullPointerException e) {
            System.err.println("NullPointerException caught: " + e.getMessage());
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


    public static String readXMLSong(String artistName, String songName, List<Song> songList) throws ParserConfigurationException, IOException, SAXException {
        songList.clear();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(search(artistName, songName));
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("SearchLyricResult");
        StringBuilder stringBuilder = new StringBuilder();
        for (int temp = 0; temp < nList.getLength() - 1; temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String title = eElement.getElementsByTagName("Song").item(0).getTextContent();
                String lyricChecksum = "";
                int trackId = Integer.parseInt(eElement.getElementsByTagName("TrackId").item(0).getTextContent());
                if(eElement.getElementsByTagName("LyricChecksum").item(0) != null){
                    lyricChecksum = eElement.getElementsByTagName("LyricChecksum").item(0).getTextContent();
                }
                int lyricId = Integer.parseInt(eElement.getElementsByTagName("LyricId").item(0).getTextContent());
                String songUrl = eElement.getElementsByTagName("SongUrl").item(0).getTextContent();
                String artistUrl = eElement.getElementsByTagName("ArtistUrl").item(0).getTextContent();
                String artist = eElement.getElementsByTagName("Artist").item(0).getTextContent();
                int songRank = Integer.parseInt(eElement.getElementsByTagName("SongRank").item(0).getTextContent());
                Song song = new Song(trackId, lyricId, title, songRank, artist, lyricChecksum, artistUrl, songUrl);
                if(!songList.contains(song)) {
                    songList.add(song);
                }
                stringBuilder.append(songList.indexOf(song) + 1).append("/    ").append(song.getArtist()).append(" - ").append(song.getSongName()).append("    ").append(song.getSongRank()).append("/10\n");
                //stringBuilder.append("NumÃ©ro : ").append(songList.indexOf(song) + 1).append("\n");
                //stringBuilder.append("TrackId: ").append(song.getTrackId()).append("\n");
                //stringBuilder.append("LyricChecksum: ").append(song.getLyricChecksum()).append("\n");
                //stringBuilder.append("LyricId: ").append(song.getLyricId()).append("\n");
                //stringBuilder.append("SongUrl: ").append(song.getSongUrl()).append("\n");
                //stringBuilder.append("ArtistUrl: ").append(song.getArtistUrl()).append("\n");
                //stringBuilder.append("Artist: ").append(song.getArtist()).append("\n");
                //stringBuilder.append("Song: ").append(song.getSongName()).append("\n");
                //stringBuilder.append("SongRank: ").append(song.getSongRank()).append("\n");
            }
        }
        return stringBuilder.toString();
    }


    public static String readXMLSongLyric(String lyric, List<Song> songList) throws ParserConfigurationException, IOException, SAXException {
        songList.clear();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(search(lyric));
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("SearchLyricResult");
        StringBuilder stringBuilder = new StringBuilder();
        for (int temp = 0; temp < nList.getLength() - 1 ; temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String title = eElement.getElementsByTagName("Song").item(0).getTextContent();
                int trackId = Integer.parseInt(eElement.getElementsByTagName("TrackId").item(0).getTextContent());
                int lyricId = Integer.parseInt(eElement.getElementsByTagName("LyricId").item(0).getTextContent());
                String songUrl = eElement.getElementsByTagName("SongUrl").item(0).getTextContent();
                String artistUrl = eElement.getElementsByTagName("ArtistUrl").item(0).getTextContent();
                String artist = eElement.getElementsByTagName("Artist").item(0).getTextContent();
                int songRank = Integer.parseInt(eElement.getElementsByTagName("SongRank").item(0).getTextContent());
                Song song = new Song(trackId, lyricId, title, songRank, artist, "", artistUrl, songUrl);
                if(!songList.contains(song)) {
                    songList.add(song);
                }
                stringBuilder.append(songList.indexOf(song) + 1).append("/    ").append(song.getArtist()).append(" - ").append(song.getSongName()).append("    ").append(song.getSongRank()).append("/10\n");
                //stringBuilder.append("NumÃ©ro : ").append(songList.indexOf(song) + 1).append("\n");
                //stringBuilder.append("TrackId: ").append(song.getTrackId()).append("\n");
                //stringBuilder.append("LyricChecksum: ").append(song.getLyricChecksum()).append("\n");
                //stringBuilder.append("LyricId: ").append(song.getLyricId()).append("\n");
                //stringBuilder.append("SongUrl: ").append(song.getSongUrl()).append("\n");
                //stringBuilder.append("ArtistUrl: ").append(song.getArtistUrl()).append("\n");
                //stringBuilder.append("Artist: ").append(song.getArtist()).append("\n");
                //stringBuilder.append("Song: ").append(song.getSongName()).append("\n");
                //stringBuilder.append("SongRank: ").append(song.getSongRank()).append("\n");
            }
        }
        return stringBuilder.toString();
    }

}