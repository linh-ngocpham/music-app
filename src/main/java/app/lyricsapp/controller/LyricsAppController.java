package app.lyricsapp.controller;

import app.lyricsapp.model.FavoriteList;
import app.lyricsapp.model.Song;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.text.html.ImageView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static app.lyricsapp.controller.RunCLI.*;
import static app.lyricsapp.model.ReadXML.*;

public class LyricsAppController implements Initializable {

    @FXML private Button favoritesButton, lyricsappButton, playlistButton, displayFavoritesButton, backMenu;
    @FXML private ChoiceBox<String> searchChoiceBox, languageChoiceBox;
    @FXML private TextField titleSearchField, artistSearchField, lyricsSearchField;
    @FXML private AnchorPane presentationTile, infoAnchorPane;
    @FXML private ScrollPane mainScrollPane;
    @FXML private Label labelTest, favoritesLabel, labelFavArtist, playlistLabel, titleArtistLabel;
    @FXML private VBox vbox;
    private String[] searchSelection = {"Paroles", "Titre/Artiste"};
    private String[] searchSelectionEng = {"Lyrics", "Title/Artist"};
    private String[] languageSelection = {"Langage : FR", "Language : ENG"};
    private static List<Song> songList = new ArrayList<>();
    public static FavoriteList favoriteList = new FavoriteList();
    private Map<String, Integer> artistCounts = new HashMap<>();

    //    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String fxmlName = location.toString();
        if (fxmlName.contains("/lyricsappeng.fxml") || fxmlName.contains("/favoriseng.fxml") || fxmlName.contains("/playlisteng.fxml")) {
            // set values for English search
            searchChoiceBox.setValue("Title/Artist");
            searchChoiceBox.getItems().addAll(searchSelectionEng);
        } else {
            // set values for French search
            searchChoiceBox.setValue("Titre/Artiste");
            searchChoiceBox.getItems().addAll(searchSelection);
        }

        if (fxmlName.contains("/favoris.fxml") || fxmlName.contains("/playlist.fxml") || fxmlName.contains("/favoriseng.fxml") || fxmlName.contains("/playlisteng.fxml")) {
            searchChoiceBox.setVisible(false);
            lyricsSearchField.setVisible(false);
            artistSearchField.setVisible(false);
            titleSearchField.setVisible(false);
        }

        searchChoiceBox.setOnAction(this::searchTitleArtist);
        searchChoiceBox.setOnAction(this::searchLyrics);
        languageChoiceBox.getItems().addAll(languageSelection);
        switchLanguage();
        backMenu.setVisible(false);
        favoritesLabel.setVisible(false);
        playlistLabel.setVisible(false);
        titleArtistLabel.setVisible(false);

        displayFavoritesButton.setOnAction(e -> {
            labelFavArtist.setVisible(true);
            presentationTile.setVisible(false);
            displayFavoritesButton.setVisible(false);
            backMenu.setLayoutX(35.0);
            backMenu.setLayoutY(59.0);
            backMenu.setVisible(true);
            searchChoiceBox.setVisible(false);
            titleSearchField.setVisible(false);
            artistSearchField.setVisible(false);
            lyricsSearchField.setVisible(false);
            playlistLabel.setVisible(false);
            favoritesLabel.setVisible(true);
            playlistButton.setVisible(true);
            titleArtistLabel.setVisible(false);
            displayFavoriteList();
        });

        backMenu.setOnAction(event -> {
            labelFavArtist.setText("");
            labelTest.setText("");
            presentationTile.setVisible(true);
            displayFavoritesButton.setVisible(true);
            backMenu.setVisible(false);
            searchChoiceBox.setVisible(true);
            titleSearchField.setVisible(true);
            artistSearchField.setVisible(true);
            lyricsSearchField.setVisible(true);
            favoritesLabel.setVisible(false);
            playlistLabel.setVisible(false);
            titleArtistLabel.setVisible(false);
            vbox.getChildren().clear();
            searchChoiceBox.setValue("Titre/Artiste");
            playlistButton.setVisible(true);
            titleArtistLabel.setLayoutX(310);
            titleArtistLabel.setLayoutY(54);
        });

        playlistButton.setOnAction(event -> {
            labelFavArtist.setVisible(false);
            vbox.getChildren().clear();
            favoritesLabel.setVisible(false);
            titleArtistLabel.setVisible(false);
            labelTest.setText("");
            playlistButton.setVisible(false);
            searchChoiceBox.setVisible(false);
            titleSearchField.setVisible(false);
            artistSearchField.setVisible(false);
            lyricsSearchField.setVisible(false);
            presentationTile.setVisible(false);
            displayFavoritesButton.setVisible(true);
            playlistLabel.setVisible(true);
            backMenu.setLayoutX(35.0);
            backMenu.setLayoutY(120.0);
            backMenu.setVisible(true);
            displayPlaylist();
        });

        favoriteList.recuperateFavorites();
        favoriteList.recuperateAll(playlists);
    }

    @FXML
    public void onEnter(KeyEvent event) throws IOException, ParserConfigurationException, SAXException {
        String searchMode = searchChoiceBox.getValue();
        if (event.getCode() == KeyCode.ENTER) {
            if(searchMode.equals("Titre/Artiste") || searchMode.equals("Title/Artist")) {
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
            titleArtistLabel.setVisible(true);
            titleArtistLabel.setText(songs.getSongName() + ", " + songs.getArtist());
            labelTest.setText(songs.getLyric());

        } catch(IOException | ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCoversApi(Song songs) {
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
                    String CovertURL = songs.setLyricCovertArtUrl(eElement1.getElementsByTagName("LyricCovertArtUrl").item(0).getTextContent());
                    return CovertURL;
                }
            }
        } catch(IOException | ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void displayResults(String result) {
        labelTest.setText("");
        vbox.getChildren().clear();
        String[] lines = result.split("\n");
        presentationTile.setVisible(false);
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            Song song = songList.get(i);
            Button button = new Button();
            button.setPrefWidth(Double.MAX_VALUE);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setId("button"); // Set ID for the button
            button.setStyle("-fx-background-radius: 10;"); // Set rounded corners
            button.setOnMouseEntered(e -> button.setStyle("-fx-text-fill: black; -fx-background-radius: 10; ")); // Add this line to change the text color on hover
            button.setOnMouseExited(e -> button.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; ")); // Add this line to change the text color on hover
            button.setPrefHeight(90);

            // Create a VBox container for the "Favorites" and "Playlist" buttons
            VBox buttonBox = new VBox();
            buttonBox.setAlignment(Pos.BASELINE_LEFT);

            Button favoritesAddButton = new Button("");
            Image img = new Image("file:src/main/java/app/lyricsapp/img/addfavoris.png");
            javafx.scene.image.ImageView imgV = new javafx.scene.image.ImageView(img);
            imgV.setFitWidth(35);
            imgV.setFitHeight(35);
            favoritesAddButton.setGraphic(imgV);
            favoritesAddButton.setOnAction(e -> {
                addToFavorites(song);
                e.consume(); // consume the event so it doesn't propagate further
            });
            favoritesAddButton.setId("button");
            favoritesAddButton.setPrefWidth(50);
            favoritesAddButton.setPrefHeight(20);
            favoritesAddButton.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");

            Button playlistAddButton = new Button("");
            Image imgp = new Image("file:src/main/java/app/lyricsapp/img/addplaylist.png");
            javafx.scene.image.ImageView imgView = new javafx.scene.image.ImageView(imgp);
            playlistAddButton.setGraphic(imgView);
            imgView.setFitWidth(35);
            imgView.setFitHeight(35);
            playlistAddButton.setOnAction(e -> {
                addToPlaylist(song);
                e.consume(); // consume the event so it doesn't propagate further
            });
            playlistAddButton.setId("button");
            playlistAddButton.setPrefWidth(35);
            playlistAddButton.setPrefHeight(35);
            playlistAddButton.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");

            buttonBox.getChildren().addAll(favoritesAddButton, playlistAddButton);
            buttonBox.setMargin(favoritesAddButton, new Insets(-5, 0, 0, 0)); // Set top margin of 10 pixels
            String cover = getCoversApi(song);// assume que cover est une URL valide
            System.out.println(cover);

            if(!cover.contains("ec1.images-amazon.com")){
                cover="file:src/main/java/app/lyricsapp/img/music.png";
            }
            Image image = new Image(cover);
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
            imageView.setFitWidth(96);
            imageView.setFitHeight(96);

            Label title= new Label(line);
            title.setStyle("-fx-text-fill: grey;");

            BorderPane borderPane = new BorderPane();

            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.getChildren().addAll(imageView, title);
            HBox.setMargin(title, new Insets(35, 0, 0, 0));

            borderPane.setLeft(hBox);
            borderPane.setRight(buttonBox);

            BorderPane.setMargin(imageView, new Insets(0, 0, 0, 15));
            BorderPane.setMargin(buttonBox, new Insets(0, 10, 0, 0));
            BorderPane.setMargin(title, new Insets(35, 0, 0, 0));

            button.setGraphic(borderPane);

            button.setOnAction(e -> {
                getLyricsApi(song);
                mainScrollPane.setVvalue(0.0);
                lyricsSearchField.setVisible(false);
                titleSearchField.setVisible(false);
                artistSearchField.setVisible(false);
                searchChoiceBox.setVisible(false);
                vbox.getChildren().clear();
                vbox.setAlignment(Pos.TOP_RIGHT); // Set alignment of VBox to top right
                Button backButton = new Button();
                Image imgb = new Image("file:src/main/java/app/lyricsapp/img/back.png");
                javafx.scene.image.ImageView imgViewb = new javafx.scene.image.ImageView(imgb);
                imgViewb.setFitWidth(35);
                imgViewb.setFitHeight(35);
                backButton.setGraphic(imgViewb);
                backButton.setOnAction(event -> {
                    mainScrollPane.setVvalue(0.0);
                    lyricsSearchField.setVisible(true);
                    titleSearchField.setVisible(true);
                    artistSearchField.setVisible(true);
                    searchChoiceBox.setVisible(true);
                    searchChoiceBox.setValue("Titre/Artiste");
                    titleArtistLabel.setVisible(false);
                    displayResults(result);
                    event.consume();
                });
                vbox.getChildren().addAll(new Label(""), backButton); // Add an empty label before the button to create space
                backButton.setId("backButton");
                VBox.setMargin(backButton, new Insets(-20, 0, 0, 0)); // Set top margin of 10 pixels
            });

            vbox.getChildren().add(button);
           // vbox.getChildren().addAll(imageView, button);
        }
        vbox.setSpacing(10); // Set spacing between buttons
    }

    private void displayFavoriteList() {
        if (favoriteList.isEmpty()) {
            labelTest.setText("Vous n'avez aucune musique favorite");
            vbox.getChildren().clear();
            return; // exit the method if there are no favorite songs
        }
        labelTest.setText("");
        vbox.getChildren().clear();
        Map<String, Integer> artistCounts = new HashMap<>();
        for (Song song : favoriteList.getList()) {
            if (favoriteList.contains(song)) {
                String artist = song.getArtist();
                int count = artistCounts.getOrDefault(artist, 0) + 1;
                artistCounts.put(artist, count);
                Button button = new Button();
                button.setPrefWidth(Double.MAX_VALUE);
                button.setAlignment(Pos.BASELINE_LEFT);
                button.setId("button");
                button.setStyle("-fx-background-radius: 10;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-text-fill: black; -fx-background-radius: 10; "));
                button.setOnMouseExited(e -> button.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; "));
                button.setPrefHeight(90);

                // Create a VBox container for the "Remove from favorites" button
                VBox buttonBox = new VBox();
                buttonBox.setAlignment(Pos.BASELINE_LEFT);

                Button removeButton = new Button();
                removeButton.setOnAction(e -> {
                    removeFromFavorites(song);
                    favoriteList.saveFavorites(favoriteList);
                    displayFavoriteList(); // regenerate the buttons after removing the song from favorites
                    e.consume();
                });
                removeButton.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");
                Image imgr = new Image("file:src/main/java/app/lyricsapp/img/delete.png");
                javafx.scene.image.ImageView imgViewr = new javafx.scene.image.ImageView(imgr);
                imgViewr.setFitWidth(35);
                imgViewr.setFitHeight(35);
                removeButton.setGraphic(imgViewr);
                Button playlistAddButton = new Button();
                playlistAddButton.setOnAction(e -> {
                    addToPlaylist(song);
                    try {
                        favoriteList.saveAll(playlists);
                        favoriteList.saveFavorites(favoriteList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    e.consume(); // consume the event so it doesn't propagate further
                });

                playlistAddButton.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");
                Image imgp = new Image("file:src/main/java/app/lyricsapp/img/addplaylist.png");
                javafx.scene.image.ImageView imgView = new javafx.scene.image.ImageView(imgp);
                imgView.setFitWidth(35);
                imgView.setFitHeight(35);
                playlistAddButton.setGraphic(imgView);

                buttonBox.getChildren().addAll(removeButton, playlistAddButton);
                buttonBox.setMargin(removeButton, new Insets(-10, 0, 0, 0));

                String cover = getCoversApi(song);// assume que cover est une URL valide
                System.out.println(cover);

                if(!cover.contains("ec1.images-amazon.com")){
                    cover="file:src/main/java/app/lyricsapp/img/music.png";
                }
                Image image = new Image(cover);
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
                imageView.setFitWidth(96);
                imageView.setFitHeight(96);

                Label title= new Label(song.getArtist() + " - " + song.getSongName());
                title.setStyle("-fx-text-fill: grey;");

                BorderPane borderPane = new BorderPane();

                HBox hBox = new HBox();
                hBox.setSpacing(10);
                hBox.getChildren().addAll(imageView, title);
                HBox.setMargin(title, new Insets(35, 0, 0, 0));

                borderPane.setLeft(hBox);
                borderPane.setRight(buttonBox);

                BorderPane.setMargin(imageView, new Insets(0, 0, 0, 15));
                BorderPane.setMargin(buttonBox, new Insets(0, 10, 0, 0));
                BorderPane.setMargin(title, new Insets(35, 0, 0, 0));

                button.setGraphic(borderPane);

                button.setOnAction(e -> {
                    getLyricsApi(song);
                    favoritesLabel.setVisible(false);
                    labelFavArtist.setVisible(false);
                    mainScrollPane.setVvalue(0.0);
                    titleArtistLabel.setLayoutX(285);
                    titleArtistLabel.setLayoutY(50);
                    vbox.getChildren().clear();
                    vbox.setAlignment(Pos.TOP_RIGHT); // Set alignment of VBox to top right
                    Button backButton = new Button();
                    Image imgb = new Image("file:src/main/java/app/lyricsapp/img/back.png");
                    javafx.scene.image.ImageView imgViewb = new javafx.scene.image.ImageView(imgb);
                    imgViewb.setFitWidth(35);
                    imgViewb.setFitHeight(35);
                    backButton.setGraphic(imgViewb);
                    backButton.setOnAction(event -> {
                        mainScrollPane.setVvalue(0.0);
                        favoritesLabel.setVisible(true);
                        labelFavArtist.setVisible(true);
                        titleArtistLabel.setVisible(false);
                        displayFavoriteList();
                    });
                    vbox.getChildren().addAll(new Label(""), backButton); // Add an empty label before the button to create space
                    backButton.setId("backButton");
                    VBox.setMargin(backButton, new Insets(-35, 0, 0, 0)); // Set top margin of 10 pixels
                });
                vbox.getChildren().add(button);
            }
        }
        vbox.setSpacing(10);
        // Find the artist with the highest count
        String favArtist = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : artistCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                favArtist = entry.getKey();
                maxCount = entry.getValue();
            } else if (entry.getValue() == maxCount && !favArtist.isEmpty() && entry.getKey().equals(favArtist)) {
                // If the current artist has the same count as the current maxCount and is the same as the favArtist, skip it
                continue;
            } else if (entry.getValue() == maxCount) {
                // If the current artist has the same count as the current maxCount but is not the same as the favArtist, don't display the favArtist
                favArtist = "";
            }
        }
        //
        if (!favArtist.isEmpty()) {
            labelFavArtist.setText("Artiste Favoris: " + favArtist);
        } else {
            labelFavArtist.setText("");
        }
    }


    public void displayPlaylistContent(FavoriteList playlist){
        Button back = new Button();
        Image imgbk = new Image("file:src/main/java/app/lyricsapp/img/back.png");
        javafx.scene.image.ImageView imgViewbk = new javafx.scene.image.ImageView(imgbk);
        imgViewbk.setFitWidth(35);
        imgViewbk.setFitHeight(35);
        back.setGraphic(imgViewbk);
        back.setOnAction(e -> {
            labelTest.setText("");
            displayPlaylist();
        });
        //vbox.getChildren().addAll(new Label(""), back);
        //VBox.setMargin(back, new Insets(-35, 0, 0, 0));

        Button renommer= new Button("Renommer");
        TextField name= new TextField();
        name.setPrefWidth(150);
        name.setPrefHeight(20);
        renommer.setOnAction(event -> {
            FavoriteList.rename(playlist,name.getText());
        });

        HBox hBox = new HBox();
        HBox.setMargin(renommer,new Insets(0,0,0,15));
        HBox.setMargin(name,new Insets(-4,0,0,340));
        Label labelPlay = new Label("Voulez vous renommer votre playlist?");
        hBox.getChildren().addAll(labelPlay,name, renommer);
        renommer.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");
        vbox.getChildren().addAll(new Label(""), back,hBox);
        if (playlist.isEmpty()) {
            labelTest.setText("Vous n'avez aucune musique dans cette playlist");
           // vbox.getChildren().clear();

            return;
        }
        labelTest.setText("");


        vbox.getChildren().clear();
        Map<String, Integer> artistCounts = new HashMap<>();
        for (Song song : playlist.getList()) {
            if (playlist.contains(song)) {
                String artist = song.getArtist();
                int count = artistCounts.getOrDefault(artist, 0) + 1;
                artistCounts.put(artist, count);
                Button button = new Button(song.getArtist() + " - " + song.getSongName());
                button.setPrefWidth(Double.MAX_VALUE);
                button.setAlignment(Pos.BASELINE_LEFT);
                button.setId("button");
                button.setStyle("-fx-background-radius: 10;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-text-fill: black; -fx-background-radius: 10; "));
                button.setOnMouseExited(e -> button.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; "));
                button.setPrefHeight(90);
                //rename

                // Create a VBox container for the "Remove from favorites" button
                VBox buttonBox = new VBox();
                buttonBox.setAlignment(Pos.BASELINE_LEFT);

                Button removeButton = new Button("Remove from this playlist");
                removeButton.setOnAction(e -> {
                    FavoriteList.removeFromPlaylist(playlist,song);
                    try {
                        favoriteList.saveAll(playlists);
                        favoriteList.saveFavorites(favoriteList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    // regenerate the buttons after removing the song from favorites
                    displayPlaylistContent(playlist);
                    e.consume();
                });
                removeButton.setPrefWidth(150);
                removeButton.setPrefHeight(20);
                removeButton.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");

                Button playlistAddButton = new Button("playlist");
                playlistAddButton.setOnAction(e -> {
                    addToPlaylist(song);
                    e.consume(); // consume the event so it doesn't propagate further
                });
                playlistAddButton.setPrefWidth(50);
                playlistAddButton.setPrefHeight(20);
                playlistAddButton.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");

                buttonBox.getChildren().add(removeButton);
                buttonBox.setMargin(removeButton, new Insets(-10, 0, 0, 0));

                button.setGraphic(buttonBox);
                button.setOnAction(e -> {
                    getLyricsApi(song);
                    playlistLabel.setVisible(false);
                    labelFavArtist.setVisible(false);
                    mainScrollPane.setVvalue(0.0);
                    titleArtistLabel.setLayoutX(285);
                    titleArtistLabel.setLayoutY(50);
                    vbox.getChildren().clear();
                    vbox.setAlignment(Pos.TOP_RIGHT); // Set alignment of VBox to top right
                    Button backButton = new Button();
                    Image imgb = new Image("file:src/main/java/app/lyricsapp/img/back.png");
                    javafx.scene.image.ImageView imgViewb = new javafx.scene.image.ImageView(imgb);
                    imgViewb.setFitWidth(35);
                    imgViewb.setFitHeight(35);
                    backButton.setGraphic(imgViewb);
                    backButton.setOnAction(event -> {
                        mainScrollPane.setVvalue(0.0);
                        playlistLabel.setVisible(true);
                        displayPlaylistContent(playlist);
                        titleArtistLabel.setVisible(false);
                    });
                    vbox.getChildren().addAll(new Label(""), backButton); // Add an empty label before the button to create space
                    backButton.setId("backButton");
                    VBox.setMargin(backButton, new Insets(-35, 0, 0, 0)); // Set top margin of 10 pixels
                });
                vbox.getChildren().add(button);
            }
        }
        vbox.setSpacing(10);
        // Find the artist with the highest count
        String favArtist = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : artistCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                favArtist = entry.getKey();
                maxCount = entry.getValue();
            } else if (entry.getValue() == maxCount && !favArtist.isEmpty() && entry.getKey().equals(favArtist)) {
                // If the current artist has the same count as the current maxCount and is the same as the favArtist, skip it
                continue;
            } else if (entry.getValue() == maxCount) {
                // If the current artist has the same count as the current maxCount but is not the same as the favArtist, don't display the favArtist
                favArtist = "";
            }
        }
        if (!favArtist.isEmpty()) {
            labelFavArtist.setText("Artiste Favoris: " + favArtist);
        } else {
            labelFavArtist.setText("");
        }
    }

    private void displayPlaylist(){
        vbox.getChildren().clear();
        // labelTest.setText("Vous n'avez aucune playlist, voulez vous créer une ?");
        Button create = new Button("create");
        TextField playListName= new TextField();
        create.setOnAction(e -> {
            try {
                favoriteList.saveAll(playlists);
                favoriteList.saveFavorites(favoriteList);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String name= playListName.getText();
            FavoriteList list  = new FavoriteList(name);
            playlists.add(list);
            vbox.getChildren().clear();
            displayPlaylist();
           // System.out.println("new Playlist "+list.getPlaylistName());
        });
        create.setPrefWidth(50);
        create.setPrefHeight(20);
        HBox hBox = new HBox();
        HBox.setMargin(create,new Insets(0,0,0,15));
        HBox.setMargin(playListName,new Insets(-4,0,0,340));
        Label labelPlay = new Label("Vous n'avez aucune playlist");
        hBox.getChildren().addAll(labelPlay, playListName, create);
        create.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");
        vbox.getChildren().addAll(hBox);

        for (FavoriteList list: playlists){
            if (!playlists.isEmpty()){
                labelPlay.setText("Vos playlists:");
            }
            System.out.println("Playlist Menu : \n"+list.getPlaylistName());
            Button removeButton = new Button();
            removeButton.setOnAction(e -> {
                FavoriteList.deletePlaylist(list,playlists);
                try {
                    favoriteList.saveAll(playlists);
                    favoriteList.saveFavorites(favoriteList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // regenerate the buttons after removing the song from favorites
                displayPlaylist();
                e.consume();
            });
            Image imgr = new Image("file:src/main/java/app/lyricsapp/img/delete.png");
            javafx.scene.image.ImageView imgViewr = new javafx.scene.image.ImageView(imgr);
            imgViewr.setFitWidth(35);
            imgViewr.setFitHeight(35);
            removeButton.setGraphic(imgViewr);
            removeButton.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; -fx-font-size : 9; ");
            Button button = new Button();
            button.setPrefWidth(Double.MAX_VALUE);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setStyle("-fx-background-radius: 10;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-text-fill: black; -fx-background-radius: 10; "));
            button.setOnMouseExited(e -> button.setStyle("-fx-text-fill: gray; -fx-background-radius: 10; "));
            button.setPrefHeight(90);

            Image image = new Image("file:src/main/java/app/lyricsapp/img/music.png");
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
            imageView.setFitWidth(70);
            imageView.setFitHeight(70);

            Label title= new Label(list.getPlaylistName());
            title.setStyle("-fx-text-fill: grey;");

            BorderPane borderPane = new BorderPane();

            HBox hBox1 = new HBox();
            hBox1.setSpacing(10);
            hBox1.getChildren().addAll(imageView, title);
            HBox.setMargin(title, new Insets(25, 0, 0, 0));

            borderPane.setLeft(hBox1);
            borderPane.setRight(removeButton);

            BorderPane.setMargin(imageView, new Insets(0, 0, 0, 15));
            BorderPane.setMargin(removeButton, new Insets(10, 10, 0, 0));
            BorderPane.setMargin(title, new Insets(25, 0, 0, 0));

            button.setGraphic(borderPane);

            button.setOnAction(event -> {
                System.out.println(list.getList());
                vbox.getChildren().clear();
                displayPlaylistContent(list);
               // displayResults(list.getList().toString());
            });

            vbox.getChildren().add(button);
        }
    }

    public void removeFromFavorites(Song song) {
        favoriteList.remove(song);
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Remove from favorites");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Removed " + song.getArtist() + " - " + song.getSongName() + " from favorites.");
        successAlert.showAndWait();
        updateFavoriteArtistLabel(); // call method to update favorite artist label
    }

    private void updateFavoriteArtistLabel() {
        String favArtist = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : artistCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                favArtist = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        labelFavArtist.setText("Artiste Favoris: " + favArtist);
    }

    public static void addToFavorites(Song song) {
        boolean isAlreadyInFavorites = favoriteList.getList().contains(song);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add to favorites");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to add " + song.getArtist() + " - " + song.getSongName() + " to favorites?");

        ButtonType addButton = new ButtonType("Add");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        if (isAlreadyInFavorites) {
            alert.getButtonTypes().setAll(addButton, cancelButton, new ButtonType("Remove"));
        } else {
            alert.getButtonTypes().setAll(addButton, cancelButton);
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == addButton) {
            if (!isAlreadyInFavorites) {
                favoriteList.add(song);
                favoriteList.saveFavorites(favoriteList);
                try {
                    favoriteList.saveAll(playlists);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Add to favorites");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Added " + song.getArtist() + " - " + song.getSongName() + " to favorites.");
                successAlert.showAndWait();
            }
        } else if (result.get().getText().equals("Remove")) {
            favoriteList.remove(song);
            favoriteList.saveFavorites(favoriteList);
            try {
                favoriteList.saveAll(playlists);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Remove from favorites");
            successAlert.setHeaderText(null);
            favoriteList.saveFavorites(favoriteList);
            successAlert.setContentText("Removed " + song.getArtist() + " - " + song.getSongName() + " from favorites.");
            successAlert.showAndWait();
        }
    }

    private void addToPlaylist(Song song) {
        // Create a new stage for the playlist selection window
        Stage playlistStage = new Stage();
        playlistStage.setTitle("Add to Playlist");

        // Create a label and choice box for selecting the playlist
        Label playlistLabel = new Label("Select Playlist:");
        ChoiceBox<String> playlistChoiceBox = new ChoiceBox<>();

        //playlistChoiceBox.getItems().addAll("Playlist 1", "Playlist 2", "Playlist 3");
        for (FavoriteList playlist:playlists) {
            playlistChoiceBox.getItems().addAll(playlist.getPlaylistName());
        }


        // Create a button for adding the song to the selected playlist
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String selectedPlaylist = playlistChoiceBox.getValue();
            searchInPlaylistByName(selectedPlaylist).add(song);
            // Implement logic for adding the song to the selected playlist
            playlistStage.close(); // Close the playlist selection window
        });

        // Create a button for closing the window without adding the song to a playlist
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            playlistStage.close(); // Close the playlist selection window
        });

        // Add the GUI elements to a VBox container
        VBox playlistBox = new VBox(10, playlistLabel, playlistChoiceBox, addButton);
        playlistBox.setAlignment(Pos.CENTER);
        HBox buttonBox = new HBox(closeButton);
        buttonBox.setAlignment(Pos.TOP_RIGHT);
        VBox mainBox = new VBox(10, playlistBox, buttonBox);
        mainBox.setPadding(new Insets(10));
        playlistStage.setScene(new Scene(mainBox));

        // Display the playlist selection window
        playlistStage.show();
    }
    public FavoriteList searchInPlaylistByName(String name){
        for (FavoriteList element:playlists){
            if(element.getPlaylistName().equals(name)){
                return  element;
            }
        }
        return null;
    }

    public boolean switchLanguage() {
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
        return true;
    }

    private void clearSearchFields() {
        titleSearchField.setText("");
        artistSearchField.setText("");
        lyricsSearchField.setText("");
    }

    public void searchTitleArtist(ActionEvent event){
        String myChoice = searchChoiceBox.getValue();
        if(myChoice.equals("Titre/Artiste") || myChoice.equals("Title/Artist")){
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
        if(myChoice.equals("Paroles") || myChoice.equals("Lyrics")){
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

    public void switchToSceneFavoritesEng() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/lyricsapp/view/favoriseng.fxml"));
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

    public void switchToSceneLyricsAppEng() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/lyricsapp/view/lyricsappeng.fxml"));
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

    public void switchToScenePlaylistEng() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/lyricsapp/view/playlisteng.fxml"));
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
                stringBuilder.append(song.getArtist()).append(" - ").append(song.getSongName()).append("    ").append(song.getSongRank()).append("/10\n");
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
                stringBuilder.append(song.getArtist()).append(" - ").append(song.getSongName()).append("    ").append(song.getSongRank()).append("/10\n");
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