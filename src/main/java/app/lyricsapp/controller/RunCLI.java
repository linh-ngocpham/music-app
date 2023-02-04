package app.lyricsapp.controller;

import app.lyricsapp.model.FavoriteList;
import app.lyricsapp.model.Song;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static app.lyricsapp.model.ReadXML.readXMLArtist;
import static app.lyricsapp.model.ReadXML.readXMLSongName;

public class RunCLI {
    static FavoriteList favoriteList = new FavoriteList();
    static List<Song> songList = new ArrayList<>();

    public static void runCLI() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Menu Principal");
        System.out.println("1 - Recherche des paroles d'une musique");
        System.out.println("2 - Gestion des favoris");
        System.out.println("3 - Quitter le programme");
        System.out.print("Votre choix : ");
        String input = scanner.nextLine();
        if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3")){
            switch (input) {
                case "1":
                    System.out.println("Vous avez choisis la recherche des paroles d'une musique");
                    searchSong();
                    break;
                case "2":
                    displayFavoritesList();
                    break;
                case "3":
                    System.exit(0);
                    break;
            }
        }
        else{
            System.out.println("Commande inconnue");
            System.out.println( "Veuillez réessayer s'il vous plait.");
            runCLI();
        }
    }

    public static void searchSong() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        // Affichage Menu Principal
        System.out.println("1 - Recherche avec le nom de l'artiste");
        System.out.println("2 - Recherche avec le nom de la musique");
        System.out.println("3 - Recherche par paroles");
        System.out.println("4 - retour");
        System.out.print("Votre choix : ");
        String input = scanner.nextLine();
        //Choix de l'utilisateur
        if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3") || Objects.equals(input, "4")){
            switch (input) {
                case "1":
                    System.out.println("Vous avez choisis : Recherche avec le nom de l'artiste");
                    searchSongArtist();
                    break;
                case "2":
                    System.out.println("Vous avez choisis : Recherche avec le nom de la musique");
                    searchSongName();
                    break;
                case "3":
                    System.out.println("Vous avez choisis : Recherche avec les paroles");
                    //searchSongLyric();
                    break;
                case "4":
                    runCLI();
                    break;
            }
        }
        else{
            System.out.println("Commande inconnue");
            System.out.println( "Veuillez réessayer s'il vous plait.");
            searchSong();
        }

    }

    public static void searchSongArtist() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        String artistName = scanner.nextLine();
        Song artist = new Song();
        artist.setArtist(artistName);
        readXMLArtist(artist, songList); //affiche les musique correspondant aux noms de la musique données
        getSongFromSongList();
        postSearchMenu();
    }

    public static void searchSongName() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        String songName = scanner.nextLine();
        Song song = new Song();
        song.setSongName(songName);
        readXMLSongName(song, songList); //affiche les musique correspondant aux noms d'artistes données
        getSongFromSongList();
        postSearchMenu();
    }

    public static void getSongFromSongList() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("Choississez le numéro de la musique");
        System.out.println("Sinon, retourner aux menu principal : Menu");
        Scanner choice = new Scanner(System.in);
        String index = choice.nextLine();
        if(Objects.equals(index, "menu") || Objects.equals(index, "Menu")){
            runCLI();
        }
        int temp = Integer.parseInt(index) - 1;
        if(songList.size() < temp || temp < 0){
            System.out.println("Commande incorrecte");
            getSongFromSongList();
        }
        Song displaySong = songList.get(temp);
        displaySong.toString();
        favorites(displaySong);
    }
    public static void postSearchMenu() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Voulez-vous faire une nouvelle recherche ?");
        System.out.println("2 - Retour au Menu Principal");
        String input = scanner.nextLine();
        if(Objects.equals(input, "1") || Objects.equals(input, "2")){
            switch(input){
                case "1":
                    searchSong();
                    break;
                case "2":
                    runCLI();
                    break;
            }
        }
        else{
            System.out.println("Commande inconnue");
            System.out.println( "Veuillez réessayer s'il vous plait.");
            postSearchMenu();
        }
    }

    public static void favorites(Song song) throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        if(favoriteList.contains(song)){
            System.out.println("Cette musique est déja présente dans les favoris");
            System.out.println("1 - Suprimer des favoris");
        }
        else{
            System.out.println("1 - Ajouter aux favoris");
        }
        System.out.println("2 - Retour");
        String input = scanner.nextLine();
        if(Objects.equals(input, "1") || Objects.equals(input, "2")){
            switch(input) {
                case "1":
                    if(favoriteList.contains(song)) {
                        favoriteList.remove(song);
                    }
                    else{
                        favoriteList.add(song);
                    }
                    break;
                case "2":
                    break;
            }
        }
        else {
            System.out.println("Commande Incorrecte");
            System.out.println("Veuillez réessayer s'il vous plait.");
            favorites(song);
        }
    }

    public static void displayFavoritesList() throws ParserConfigurationException, IOException, SAXException {
        favoriteList.toStringFavoritesList();
        selectFavoriteSong(favoriteList);
    }

    public static void selectFavoriteSong(FavoriteList favoriteList) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("1 - Souhaitez-Vous affichez une musique de vos favoris ?");
        System.out.println("2 - retourner aux menu principal");
        Scanner choice = new Scanner(System.in);
        String input = choice.nextLine();
        if(Objects.equals(input, "1") || Objects.equals(input, "2")){
            switch (input){
                case "1":
                    getSongFromFavoritesSong(favoriteList);
                    break;
                case "2":
                    runCLI();
                    break;
            }
        }
        else{
            System.out.println("Commande Incorrecte");
            System.out.println("Veuillez réessayer s'il vous plait.");
            selectFavoriteSong(favoriteList);
        }
    }

    public static void getSongFromFavoritesSong(FavoriteList favoriteList) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("Choississez le numéro de la musique");
        System.out.println("Sinon, taper retour");
        Scanner choice = new Scanner(System.in);
        String input = choice.nextLine();
        if(Objects.equals(input, "retour")){
            selectFavoriteSong(favoriteList);
        }
        int temp = Integer.parseInt(input) - 1;
        if(songList.size() > temp && temp  >= 0){
            favoriteList.getSongFavorites(temp);
            selectFavoriteSong(favoriteList);
        }
        else{
            System.out.println("Commande Incorrecte");
            System.out.println("Retour");
            selectFavoriteSong(favoriteList);
        }
    }


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        runCLI();
    }
}
