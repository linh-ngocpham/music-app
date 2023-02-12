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

import static app.lyricsapp.model.ReadXML.*;

public class RunCLI {
    static FavoriteList favoriteList = new FavoriteList();
    static List<Song> songList = new ArrayList<>();
    public static void runCLI() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Menu Principal");
        System.out.println("1 - Recherche des paroles d'une musique");
        System.out.println("2 - Gestion de vos favoris");
        System.out.println("3 - Quitter le programme");
        System.out.print("Votre choix : ");
        String input = scanner.nextLine();
        if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3")) {
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
        } else{
            System.out.println("Commande inconnue");
            System.out.println( "Veuillez réessayer s'il vous plait.");
            runCLI();
        }
    }

    public static void searchSong() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        // Affichage Menu Principal
        System.out.println("1 - Recherche avec le nom de l'artiste et le nom de la musique");
        System.out.println("2 - Recherche par paroles");
        System.out.println("3 - retour");
        System.out.print("Votre choix : ");
        String input = scanner.nextLine();
        //Choix de l'utilisateur
        if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3") || Objects.equals(input, "4")) {
            switch (input) {
                case "1":
                    System.out.println("Vous avez choisis : Recherche avec le nom de l'artiste");
                    searchSongArtistAndSongName();
                    break;
                case "2":
                    System.out.println("Vous avez choisis : Recherche avec les paroles");
                    searchSongLyric();
                    break;
                case "3":
                    runCLI();
                    break;
            }
        } else{
            System.out.println("Commande inconnue");
            System.out.println( "Veuillez réessayer s'il vous plait.");
            searchSong();
        }
    }

    public static void searchSongArtistAndSongName() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("---------------------------------------");
        System.out.println("Saississez le nom de l'artiste :");
        Scanner scanner = new Scanner(System.in);
        String input1 = scanner.nextLine();
        String artistName = input1.replaceAll(" ", "%20");
        System.out.println("Saississez le nom de la musique :");
        String input2 = scanner.nextLine();
        String song = input2.replaceAll(" ", "%20");
        readXMLSong(artistName,song, songList); //affiche les musique correspondant aux noms de la musique données
        getSongFromSongList();
        postSearchMenu();
    }

    public static void searchSongLyric() throws ParserConfigurationException,IOException, SAXException{
        System.out.println("---------------------------------------");
        System.out.println("Saississez un morceau de paroles de la musique :");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String lyric = input.replaceAll(" ", "%20");
        readXMLSongLyric(lyric, songList); //affiche les musique correspondant aux noms d'artistes données
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
        getLyricsApi(displaySong);
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
        } else{
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
        } else {
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
        } else{
            System.out.println("Commande Incorrecte");
            System.out.println("Veuillez réessayer s'il vous plait.");
            selectFavoriteSong(favoriteList);
        }
    }

    public static void editFavoritesSongList(int index, FavoriteList favoritesList) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("1 - Souhaitez-Vous la suprimmer la musique de vos favoris ?");
        System.out.println("2 - retour");
        Scanner choice = new Scanner(System.in);
        String input = choice.nextLine();
        if(Objects.equals(input, "1") || Objects.equals(input, "2")){
            switch(input){
                case "1":
                    favoritesList.getList().remove(index);
                    System.out.println("Musique retiré de vos favoris");
                    favoritesList.toStringFavoritesList();
                    selectFavoriteSong(favoritesList);
                    break;
                case "2":
                    favoritesList.toStringFavoritesList();
                    selectFavoriteSong(favoritesList);
                    break;
            }
        }
        else{
            System.out.println("Commande Incorrecte");
            System.out.println("Veuillez réessayer s'il vous plait.");
            editFavoritesSongList(index, favoritesList);
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
            favoriteList.getList().get(temp).toString();
            editFavoritesSongList(temp,favoriteList);
        } else{
            System.out.println("Commande Incorrecte");
            System.out.println("Retour");
            selectFavoriteSong(favoriteList);
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        runCLI();
    }
}
