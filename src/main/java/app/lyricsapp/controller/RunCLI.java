package app.lyricsapp.controller;

import app.lyricsapp.EngLanguage;
import app.lyricsapp.FrenchLanguage;
import app.lyricsapp.ItalyLanguage;
import app.lyricsapp.Language;
import app.lyricsapp.model.FavoriteList;
import app.lyricsapp.model.Song;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static app.lyricsapp.model.FavoriteList.deletePlaylist;
import static app.lyricsapp.model.FavoriteList.getMostFavoritesArtist;
import static app.lyricsapp.model.ReadXML.*;

public class  RunCLI {
    public static List<FavoriteList> playlists = new ArrayList<>();
    static FavoriteList favoriteList = new FavoriteList();
    public List<FavoriteList> getPlaylists() {
        return playlists;
    }
    static List<Song> songList = new ArrayList<>();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN ="\u001B[32m";
    public static final String ANSI_YELLOW ="\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static ArrayList<String> banWord = new ArrayList<String>();

    private static Language current_language = null;
    private static final String banWordString = "about,after,all,also,an,and,another," +
                                                "any,are,as,at,be,because,been,before," +
                                                "being,between,both,but,by,came,can,come," +
                                                "could,did,do,does,each,else,for,from,get," +
                                                "got,had,has,have,he,her,here,him,himself," +
                                                "his,how,if,in,into,is,it,its,just,like," +
                                                "make,many,me,might,more,most,much,must," +
                                                "my,never,no,now,of,on,only,or,other,our," +
                                                "out,over,re,said,same,see,should,since," +
                                                "so,some,still,such,take,than,that,the," +
                                                "their,them,then,there,these,they,this," +
                                                "those,through,to,too,under,up,use,very," +
                                                "want,was,way,we,well,were,what,when," +
                                                "where,which,while,who,will,with,would,you,your";
    public static void banWordList(String banWordString){
        ArrayList<String> tempList = new ArrayList<>();
        String temp = "";
        for(int i = 0; i < banWordString.length(); i ++){
            if(banWordString.charAt(i) == ','){
                tempList.add(temp);
                temp = "";
            }
            else {
                temp += banWordString.charAt(i);
            }
        }
        banWord = tempList;
    }

    public static String replaceAllAPI(String inputString){
        String temp = inputString;
        temp = temp.replaceAll(":", "%3A");
        temp = temp.replaceAll("/", "%2F");
        temp = temp.replaceAll("\\?", "%3F");
        temp = temp.replaceAll("#", "%23");
        temp = temp.replaceAll("\\[", "%5B");
        temp = temp.replaceAll("]", "%5D");
        temp = temp.replaceAll("@", "%40");
        temp = temp.replaceAll("!", "%21");
        temp = temp.replaceAll("\\$", "%24");
        temp = temp.replaceAll("&", "%26");
        temp = temp.replaceAll("'", "%27");
        temp = temp.replaceAll("\\(", "%28");
        temp = temp.replaceAll("\\)", "%29");
        temp = temp.replaceAll("\\*", "%2A");
        temp = temp.replaceAll("\\+", "%2B");
        temp = temp.replaceAll(",", "%2C");
        temp = temp.replaceAll(";", "%3B");
        temp = temp.replaceAll("=", "%3D");
        temp = temp.replaceAll("%", "%25");
        temp = temp.replaceAll(" ", "%20");
        return temp;
    }

    public static void run()throws ParserConfigurationException, IOException, SAXException {
        banWordList(banWordString);
        boolean validInput = true;
        Scanner scanner = new Scanner(System.in);
        String input;

        // Language Menu
        System.out.println(ANSI_RED +"----------------------------------------");
        System.out.println("--           Language choice:         --");
        System.out.println("----------------------------------------"+ANSI_RESET);
        while (validInput) {
            System.out.println(ANSI_RED +"1. French");
            System.out.println("2. English");
            System.out.println("3. Italy"+ANSI_RESET);
            input = scanner.nextLine();
            if (Objects.equals(input, "1") || Objects.equals(input, "2")|| Objects.equals(input, "3")) {
                validInput = false;
                switch (input) {
                    case "1":
                        current_language = new FrenchLanguage();
                        break;
                    case "2":
                        current_language = new EngLanguage();
                        break;
                    case "3":
                        current_language = new ItalyLanguage();
                        break;
                }
            } else {
                System.out.println("Invalid Choice.");
            }
        }
        runCLI();
    }

    public static void runCLI() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        // Main menu
        System.out.println(ANSI_GREEN +"----------------------------------------");
        System.out.println(current_language.mainMenu);
        System.out.println("----------------------------------------"+ANSI_RESET);
        String input = null;
        boolean validInput = true;
        while(validInput){
            System.out.println(ANSI_BLUE + current_language.findByLyric + ANSI_RESET);
            System.out.println(ANSI_YELLOW + current_language.playlistMenu + ANSI_RESET);
            System.out.println(ANSI_RED + current_language.languageChoice + ANSI_RESET);
            System.out.println(current_language.exit);
            System.out.print(current_language.choice);
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3") || Objects.equals(input, "4")){
                validInput = false;
            }
            else {
                System.out.println("------------------------------------------------------------------");
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
            }
        }
        switch (input) {
            case "1":
                System.out.println(current_language.searchedByMusic);
                searchSong();
                break;
            case "2":
                manageFavorites();
                break;
            case "3":
                run();
                break;
            case "4":
                favoriteList.saveAll(playlists);
                favoriteList.saveFavorites(favoriteList);
                System.exit(0);
                break;
        }
    }


    public static void searchSong() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        String input = null;
        while(validInput){
            // Affichage Menu des recherches
            System.out.println("------------------------------------------------------------------");
            System.out.println(ANSI_BLUE + current_language.searchedByArtistMusic);
            System.out.println(current_language.searchedByLyrics);
            System.out.println(current_language.returnToMain + ANSI_RESET);
            System.out.println("------------------------------------------------------------------");
            System.out.print(current_language.choice);

            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "menu")) {
                validInput = false;
            }
            else {
                System.out.println("------------------------------------------------------------------");
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
            }
        }
        //Choix de l'utilisateur
        switch (input) {
            case "1":
                System.out.println(current_language.searchedByArtist);
                searchSongArtistAndSongName();
                break;
            case "2":
                System.out.println(current_language.searchedWithLyrics);
                searchSongLyric();
                break;
            case "menu":
                runCLI();
                break;
        }
    }

    // recherche par nom de l'artiste et nom de la musique
    public static void searchSongArtistAndSongName() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        String input1 = null;
        boolean validInput1 = true;
        while (validInput1) {
            System.out.println(ANSI_BLUE + current_language.enterArtistName + ANSI_RESET);
            input1 = scanner.nextLine().toLowerCase();
            if(Objects.equals(input1,"?")){
                System.out.println(current_language.banWordIs);
                System.out.println(banWord.toString());
            }
            else{
                String[] strInput1 = input1.split(" ");
                if(!banWord.containsAll(List.of(strInput1)) && (input1.length() <= 75)){
                    validInput1 = false;
                }
                else if (banWord.containsAll(List.of(strInput1))){
                    System.out.println(ANSI_RED + current_language.errorArtistName + ANSI_RESET);
                    System.out.println(ANSI_RED + current_language.banWordCLI + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + current_language.errorArtistNameTooLong + ANSI_RESET);
                }
            }

        }
        String artistName = replaceAllAPI(input1);
        String input2 = null;
        boolean validInput2 = true;
        while (validInput2) {
            System.out.println(ANSI_BLUE + current_language.enterSongName + ANSI_RESET);
            input2 = scanner.nextLine().toLowerCase();
            if(Objects.equals(input2,"?")){
                System.out.println(current_language.banWordIs);
                System.out.println(banWord.toString());
            }
            else{
                String[] strInput2 = input2.split(" ");
                if(!banWord.containsAll(List.of(strInput2)) && (input2.length() <= 125)){
                    validInput2 = false;
                }
                else if(banWord.containsAll(List.of(strInput2))){
                    System.out.println(ANSI_RED + current_language.errorSongName + ANSI_RESET);
                    System.out.println(ANSI_RED + current_language.banWordCLI + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + current_language.errorSongNameTooLong + ANSI_RESET);
                }
            }

        }
        boolean validInput3 = IsSearchSongPopular();
        String song = replaceAllAPI(input2);
        System.out.println("------------------------------------------------------------------");
        readXMLSong(artistName, song, songList, validInput3); //affiche les musiques correspondant aux noms de la musique données
        if(songList.isEmpty()){
            System.out.println(ANSI_BLUE + current_language.noFoundMusic + ANSI_RESET);
            searchSong();
        }
        else{
            getSongFromSongList();
            postSearchMenu();
        }
    }

    // demande à l'utilisateur si la musique voulue sont ceux populaires
    public static boolean IsSearchSongPopular(){
        Scanner choice = new Scanner(System.in);
        while(true){
            System.out.println(ANSI_GREEN + current_language.isSearchMusicPopular + ANSI_RESET);
            String input = choice.nextLine().toLowerCase();
            if(Objects.equals(input,"y")){
                return true;
            }
            else if (Objects.equals(input, "n")){
                return false;
            }
            else{
                System.out.println("------------------------------------------------------------------");
                System.out.println(ANSI_RED + current_language.invaliedCommande);
                System.out.println(current_language.reDo + ANSI_RESET);
            }
        }
    }

    // recherche par paroles
    public static void searchSongLyric() throws ParserConfigurationException,IOException, SAXException{
        Scanner scanner = new Scanner(System.in);
        String input = null;
        boolean validInput = true;
        while (validInput) {
            System.out.println("------------------------------------------------------------------");
            System.out.println(ANSI_YELLOW + current_language.enterLyrics + ANSI_RESET);
            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input,"?")){
                System.out.println(current_language.banWordIs);
                System.out.println(banWord.toString());
            }
            else{
                String[] strInput = input.split(" ");
                if(!banWord.containsAll(List.of(strInput)) && (input.length() <= 250)){
                    validInput = false;
                }
                else if (banWord.containsAll(List.of(strInput))){
                    System.out.println(ANSI_RED + current_language.errorLyrics + ANSI_RESET);
                    System.out.println(ANSI_RED + current_language.banWordCLI + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + current_language.errorLyricsTooLong + ANSI_RESET);
                }
            }

        }
        String lyric = replaceAllAPI(input);
        boolean validInput2 = IsSearchSongPopular();
        System.out.println("------------------------------------------------------------------");
        readXMLSongLyric(lyric, songList, validInput2);//affiche les musiques correspondant aux paroles données
        if(songList.isEmpty()){
            System.out.println(ANSI_YELLOW + current_language.noFoundMusic + ANSI_RESET);
            searchSong();
        }
        else{
            getSongFromSongList();
            postSearchMenu();
        }

    }

    //Sous-menu permmettant de choisir la musique après la recherche
    public static void getSongFromSongList() throws ParserConfigurationException, IOException, SAXException {
        boolean validInput = true;
        Scanner choice = new Scanner(System.in);
        int temp = 0;
        while(validInput) {
            System.out.println( "------------------------------------------------------------------");
            System.out.println(ANSI_YELLOW + current_language.choseByMusicNumber);
            System.out.println(current_language.returnToMain + ANSI_RESET);
            String index = choice.nextLine();
            if (Objects.equals(index.toLowerCase(), "menu")) {
                runCLI();
            } else if(index.matches("[0-9]+")) {
                temp = Integer.parseInt(index) - 1;
                if (songList.size() < temp || temp < 0) {
                    System.out.println(ANSI_RED + current_language.invaliedCommande + ANSI_RESET);
                } else {
                    validInput = false;
                }
            }
            else{
                System.out.println(ANSI_RED + current_language.invaliedCommande);
                System.out.println(current_language.exitToPreviousMenu + ANSI_RESET);
                searchSong();
            }
        }
        Song displaySong = songList.get(temp);
        getLyricsApi(displaySong);
        choosePlaylistForFavorites(displaySong);
    }

    //Sous-menu pour retourner aux menu principal ou refaire une recherche après la recherche et (supposer) ajout à une playlist de la musique
    public static void postSearchMenu() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        String input = null;
        while(validInput){
            System.out.println("------------------------------------------------------------------");
            System.out.println(ANSI_GREEN + current_language.newResearch);
            System.out.println(current_language.returnToMain + ANSI_RESET);
            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input, "1") || Objects.equals(input, "menu")){
                validInput = false;
            }
            else{
                System.out.println(ANSI_RED + current_language.invaliedCommande);
                System.out.println(current_language.reDo + ANSI_RESET);
            }
        }
        switch(input){
            case "1":
                searchSong();
                break;
            case "menu":
                runCLI();
                break;
        }
    }

    //Sous-menu pour choisir une playlist à laquelle ajouter une musique choisi après la recherche
    public static void choosePlaylistForFavorites(Song song) throws IOException, ParserConfigurationException, SAXException {
        System.out.println(current_language.choseByMusicNumber2);
        System.out.println("0/ " + favoriteList.getPlaylistName());
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i+1) + "/ " + playlists.get(i).getPlaylistName());
        }
        System.out.println((playlists.size() + 1) + "/ Retour aux menu précedent");
        int input = -1;
        while (input < 0 || input > playlists.size() + 1){
            Scanner choice = new Scanner(System.in);
            System.out.print(current_language.choice);
            String text = choice.nextLine();
            if(text.matches("[0-9]+")) {
                input = Integer.parseInt(text);
            }
            else{
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
            }
        }
        if(input == 0){
            favorites(song);
        }
        else if(input  == playlists.size() + 1){
            searchSong();
        }
        else{
            favoritesFromPlaylist(song, playlists.get(input-1));
        }
    }

    //Sous-menu permettant de vérfier si l'utilisateur peut ou non ajouter une musique de sa recherche
    // dans la playlist ou encore de la retire
    public static void favoritesFromPlaylist(Song song, FavoriteList playlist) throws IOException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(System.in);
        int check = 0;
        for(Song favSong : playlist.getList()){
            if (Objects.equals(song.getSongName(), favSong.getSongName()) && Objects.equals(song.getArtist(), favSong.getArtist())){
                check = 1;
                break;
            }
        }
        boolean validInput = true;
        String input = "";
        while(validInput){
            System.out.println("------------------------------------------------------------------");
            if(check == 1){
                System.out.println();
                System.out.println(current_language.exited);
                System.out.println(current_language.delete);
            } else{
                System.out.println(current_language.addToFavotis);
            }
            System.out.println(current_language.returnToPreviousMenu);
            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input, "1") || Objects.equals(input, "menu")){
                validInput = false;
            }
            else{
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
            }
        }
        switch(input) {
            case "1":
                if(check == 1) {
                    playlist.remove(song);
                    favoriteList.saveAll(playlists);
                }
                else{
                    playlist.add(song);
                    favoriteList.saveAll(playlists);
                }
                break;
            case "menu":
                runCLI();
                break;
        }
    }

    //Meme fonctionnement que la fonction favoritesFromPlaylist (fonction précédentes)
    //mais dans le cas de la liste des musiques favorites
    public static void favorites(Song song) throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        String input = null;
        int check = 0;
        for(Song favSong : favoriteList.getList()){
            if (Objects.equals(song.getSongName(), favSong.getSongName()) && Objects.equals(song.getArtist(), favSong.getArtist())){
                check = 1;
                break;
            }
        }
        while(validInput){
            System.out.println("------------------------------------------------------------------");
            if(check == 1){
                System.out.println();
                System.out.println(ANSI_YELLOW + current_language.exited);
                System.out.println(current_language.delete + ANSI_RESET);
            } else{
                System.out.println(ANSI_YELLOW + current_language.addToFavotis + ANSI_RESET);
            }
            System.out.println(ANSI_YELLOW + current_language.returnToPreviousMenu + ANSI_RESET);
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "menu")){
                validInput = false;
            } else {
                System.out.println("------------------------------------------------------------------");
                System.out.println(ANSI_RED + current_language.invaliedCommande);
                System.out.println(current_language.reDo + ANSI_RESET);
            }
        }
        switch(input) {
            case "1":
                if (check == 1) {
                    favoriteList.remove(song);
                    favoriteList.saveFavorites(favoriteList);
                } else {
                    favoriteList.add(song);
                    favoriteList.saveFavorites(favoriteList);
                    System.out.println("La musique : " + song.getArtist() + " - " + song.getSongName() + " a été ajoutée aux favoris.");
                }
                break;
            case "menu":
                runCLI();
                break;
        }
    }

    //Menu principal pour l'affichage et gestion des favoris
    //(renommer, supprimer une musique ou la playlist, créer une playlist)
    public static void manageFavorites() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        String input = "";
        while(validInput){
            System.out.println("----------------------------------------");
            System.out.println(ANSI_YELLOW + current_language.creatPlaylist);
            System.out.println(current_language.showPlaylist);
            System.out.println(current_language.returnToMain + ANSI_RESET);
            System.out.print(current_language.choice);
            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "menu")){
                validInput = false;
            }
            else{
                System.out.println(ANSI_RED + current_language.invaliedCommande);
                System.out.println(current_language.reDo + ANSI_RESET);
            }
        }
        switch (input) {
            case "1":
                createPlaylist();
                break;
            case "2":
                displayPlaylists();
                break;
            case "menu":
                runCLI();
                break;
        }
    }

    //Sous-menu permettant de créer une playlist
    public static void createPlaylist() throws IOException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        String input = "";
        while(validInput){
            System.out.println("----------------------------------------");
            System.out.println(ANSI_GREEN + current_language.namePlaylist);
            System.out.println(current_language.returnToPreviousMenu + ANSI_RESET);
            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input,"menu")){
                manageFavorites();
            }
            if(!input.equals("") && input.length() < 50){
                validInput = false;
            }
            else{
                if(input.equals("")){
                    System.out.println(ANSI_RED + current_language.emptyEntry + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + current_language.errorNamePlaylist);
                    System.out.println(current_language.errorNamePlaylist1 + ANSI_RESET);
                }
                System.out.println( current_language.reDo);
            }
        }
        File file = new File("src/main/java/app/lyricsapp/model/favorites" + playlists.size() + ".txt");
        file.createNewFile();
        playlists.add(new FavoriteList(input));
        favoriteList.saveAll(playlists);
        System.out.println(ANSI_YELLOW + playlists.get(playlists.size()-1).getPlaylistName() + current_language.playlistCreated);
        System.out.println(current_language.returnToPreviousMenu + ANSI_RESET);
        manageFavorites();
    }

    //affichage des musiques favorites
    public static void displayPlaylists() throws ParserConfigurationException, IOException, SAXException{
        if(!playlists.isEmpty()) {
            System.out.println(ANSI_YELLOW + current_language.choosePlaylist + ANSI_RESET);
            for (int i = 0; i < playlists.size() + 1; i++) {
                if(i == 0){
                    System.out.println(ANSI_YELLOW + i + " - " + favoriteList.getPlaylistName() + ANSI_RESET);
                }
                else{
                    System.out.println( ANSI_YELLOW + i + " - " + playlists.get(i-1).getPlaylistName() + ANSI_RESET);
                }
            }
            System.out.println(ANSI_YELLOW + (playlists.size() + 1) + current_language.returnToPreviousMenuInt + ANSI_RESET);
            int input = -1;
            while (input < 0 || input > playlists.size()+1){
                Scanner choice = new Scanner(System.in);
                String textInput = choice.nextLine();
                if(textInput.matches("[0-9]+")){
                    input = Integer.parseInt(textInput);
                }
                else{
                    System.out.println(ANSI_RED + current_language.invaliedCommande);
                    System.out.println(current_language.reDo + ANSI_RESET);
                }
            }
            if(input == 0){
                System.out.println("------------------------------------------------------------------");
                selectFavoriteSong(favoriteList);
            }
            else if(input == playlists.size() + 1) {
                manageFavorites();
            }
            else{
                System.out.println("------------------------------------------------------------------");
                selectFavoriteSong(playlists.get(input-1));
            }
        }
        else {
            System.out.println(ANSI_YELLOW + current_language.likedMusic + ANSI_RESET);
            selectFavoriteSong(favoriteList);
        }
    }

    //Choisir une playlist disponible
    //ou à défaut les musiques favorites
    public static void selectFavoriteSong(FavoriteList favoriteList) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        boolean validInput = true;
        String input = null ;
        String favoritesArtist = getMostFavoritesArtist(favoriteList.getList()) != null ? getMostFavoritesArtist(favoriteList.getList()) : current_language.favoritesArtistNotFound;
        if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
            System.out.println(ANSI_GREEN + current_language.favoritesArtist + favoritesArtist + ANSI_RESET);
        }
        else{
            System.out.println(ANSI_GREEN + current_language.favoritesArtistPlaylist + favoritesArtist + ANSI_RESET);
        }
        favoriteList.toStringFavoritesList();
        while(validInput){
            System.out.println("------------------------------------------------------------------");
            if(favoriteList.getList().isEmpty()){
                if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
                    System.out.println(ANSI_YELLOW + current_language.emptyFavoris);
                    System.out.println(current_language.returnToPreviousMenu + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_YELLOW + current_language.emptyPlaylist);
                    System.out.println(current_language.playlistManag1);
                    System.out.println(current_language.returnToPreviousMenu + ANSI_RESET);
                }
            }
            else{
                if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
                    System.out.println(ANSI_YELLOW + current_language.displayFavoris);
                    System.out.println(current_language.returnToPreviousMenu + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_YELLOW + current_language.displayPlaylist);
                    System.out.println(current_language.playlistManag2);
                    System.out.println(current_language.returnToPreviousMenu + ANSI_RESET);
                }
            }
            input = choice.nextLine().toLowerCase();
            boolean condition = Objects.equals(input, "1") || Objects.equals(input, "menu");
            if(favoriteList.getList().isEmpty()){
                if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
                    if(Objects.equals(input, "menu")){
                        validInput = false;
                    }
                }
                else{
                    if(condition){
                        validInput = false;
                    }
                }
            }
            else if(!favoriteList.getList().isEmpty()){
                if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
                    if(condition){
                        validInput = false;
                    }
                }
                else{
                    if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "menu")){
                        validInput = false;
                    }
                }
            }
            else{
                System.out.println(ANSI_RESET + current_language.invaliedCommande);
                System.out.println(current_language.reDo + ANSI_RESET);
            }
        }
        if(favoriteList.getList().isEmpty()){
            if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
                if(input.equals("menu")){
                    manageFavorites();
                }
            } else{
                switch (input){
                    case "1":
                        editPlaylist(favoriteList);
                        break;
                    case "menu":
                        manageFavorites();
                        break;
                }
            }
        } else{
            if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
                switch (input){
                    case "1":
                        getSongFromFavoritesSong(favoriteList);
                        break;
                    case "menu":
                        manageFavorites();
                        break;
                }
            } else{
                switch (input){
                    case "1":
                        getSongFromFavoritesSong(favoriteList);
                        break;
                    case "2":
                        editPlaylist(favoriteList);
                        break;
                    case "menu":
                        manageFavorites();
                        break;
                }
            }
        }
    }

    //Sous-menu de gestion d'une playlist (uniquement)
    public static void editPlaylist(FavoriteList playlist) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        System.out.println("------------------------------------------------------------------");
        System.out.println(ANSI_RED + current_language.playlistChangeName);
        System.out.println(current_language.playlistDelete + ANSI_RESET + playlist.getPlaylistName());
        System.out.println(ANSI_YELLOW + current_language.returnToPreviousMenu + ANSI_RESET);
        String input = choice.nextLine().toLowerCase();
        if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "menu")){
            switch(input){
                case "1":
                    editNamePlaylistCLI(playlist);
                    break;
                case "2":
                    deletePlaylistCLI(playlist);
                    break;
                case "menu":
                    selectFavoriteSong(playlist);
                    break;
            }
        }
        else{
            System.out.println(ANSI_RED + current_language.invaliedCommande);
            System.out.println(current_language.reDo + ANSI_RESET);
            selectFavoriteSong(playlist);
        }
    }

    //Sous-menu permettant la modification du nom d'une playlist (uniquement)
    public static void editNamePlaylistCLI(FavoriteList playlist) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        System.out.println("------------------------------------------------------------------");
        System.out.print(ANSI_RED + current_language.namePlaylist + ANSI_RESET + playlist.getPlaylistName());
        System.out.println(ANSI_YELLOW + current_language.exitToPreviousMenu + ANSI_RESET);
        String input = choice.nextLine().toLowerCase();
        if(Objects.equals(input, "menu") || Objects.equals(input.replace(" ",""), "")){
            System.out.println(ANSI_YELLOW + current_language.changeNotTaken + ANSI_RESET);
            selectFavoriteSong(playlist);
        }
        else if (input.length() < 50){
            playlist.setPlaylistName(input);
            System.out.println(ANSI_GREEN + current_language.changeTaken + ANSI_RESET);
            System.out.println(ANSI_GREEN + current_language.playlistNewName + ANSI_RESET + playlist.getPlaylistName());
            selectFavoriteSong(playlist);
        }
        else{
            System.out.println(ANSI_RED + current_language.changeNotTaken);
            System.out.println(current_language.errorNamePlaylist + ANSI_RESET);
            selectFavoriteSong(playlist);
        }
    }

    //Sous-menu de suppression d'une playlist
    public static void deletePlaylistCLI(FavoriteList playlist) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        System.out.println("------------------------------------------------------------------");
        System.out.print(ANSI_RED +  current_language.isPlaylistDelete1 + playlist.getPlaylistName() + current_language.isPlaylistDelete2 + ANSI_RESET);
        String input = choice.nextLine().toLowerCase();
        if(Objects.equals(input, "y") || Objects.equals(input, "n")){
            switch(input){
                case "y":
                    deletePlaylist(playlist, playlists);
                    break;
                case "n":
                    System.out.println(ANSI_RED + current_language.exitToPreviousMenu + ANSI_RESET);
                    manageFavorites();
                    break;
            }
        }
        else{
            System.out.println(ANSI_RED + current_language.invaliedCommande);
            System.out.println(current_language.exitToPreviousMenu + ANSI_RESET);
            displayPlaylists();
        }
    }

    //sous-menu pour supprimer une musique d'une playlist/favoris
    public static void editFavoritesSongList(int index, FavoriteList favoritesList) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        boolean validinput = true;
        String input = null;
        while(validinput){
            System.out.println("------------------------------------------------------------------");
            System.out.println(ANSI_YELLOW + current_language.delete);
            System.out.println(current_language.returnToMain + ANSI_RESET);
            input = choice.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2")){
                validinput = false;
            }
            else {
                System.out.println("------------------------------------------------------------------");
                System.out.println(ANSI_RED + current_language.invaliedCommande);
                System.out.println(current_language.reDo + ANSI_RESET);
            }
        }
        switch(input){
            case "1":
                favoritesList.getList().remove(index);
                System.out.println(ANSI_GREEN + current_language.removeSuceed + ANSI_RESET);
                favoriteList.saveAll(playlists);
                favoriteList.saveFavorites(favoriteList);
                favoritesList.toStringFavoritesList();
                selectFavoriteSong(favoritesList);
                break;
            case "2":
                favoritesList.toStringFavoritesList();
                selectFavoriteSong(favoritesList);
                break;
        }
    }

    public static void getSongFromFavoritesSong(FavoriteList favoriteList) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("------------------------------------------------------------------");
        System.out.println(ANSI_YELLOW + current_language.chooseMusic);
        System.out.println(current_language.returnToMain + ANSI_RESET);
        Scanner choice = new Scanner(System.in);
        String input = choice.nextLine().toLowerCase();
        if(Objects.equals(input, "retour")){
            selectFavoriteSong(favoriteList);
        }
        else if (input.matches("[0-9]+")){
            int temp = Integer.parseInt(input) - 1;
            if(favoriteList.getList().size() > temp && temp  >= 0){
                if(favoriteList.getList().get(temp).getLyric() == null){
                    favoriteList.getList().get(temp).toStringOne();
                    getLyricsApi(favoriteList.getList().get(temp));
                } else{
                    favoriteList.getList().get(temp).toStringTwo();
                }
                editFavoritesSongList(temp,favoriteList);
            } else{
                System.out.println("------------------------------------------------------------------");
                System.out.println(ANSI_RED + current_language.invaliedCommande);
                System.out.println(current_language.returnToMain + ANSI_RESET);
                selectFavoriteSong(favoriteList);
            }
        } else{
            System.out.println("------------------------------------------------------------------");
            System.out.println(ANSI_RED + current_language.invaliedCommande);
            System.out.println(current_language.returnToMain + ANSI_RESET);
            selectFavoriteSong(favoriteList);
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        favoriteList.recuperateFavorites();
        favoriteList.recuperateAll(playlists);
        run();
    }
}