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
        System.out.println("----------------------------------------");
        System.out.println("--           Language choice:         --");
        System.out.println("----------------------------------------");
        while (validInput) {
            System.out.println("1. French");
            System.out.println("2. English");
            System.out.println("3. Italy");
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
        System.out.println("----------------------------------------");
        System.out.println(current_language.mainMenu);
        System.out.println("----------------------------------------");
        String input = null;
        boolean validInput = true;
        while(validInput){
            System.out.println(current_language.findByLyric);
            System.out.println(current_language.playlistMenu);
            System.out.println(current_language.languageChoice);
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
            System.out.println(current_language.searchedByArtistMusic);
            System.out.println(current_language.searchedByLyrics);
            System.out.println(current_language.returnToMain);
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
            System.out.println(current_language.enterArtistName);
            input1 = scanner.nextLine().toLowerCase();
            String[] strInput1 = input1.split(" ");
            if(!banWord.containsAll(List.of(strInput1)) && (input1.length() <= 75)){
                validInput1 = false;
            }
            else{
                System.out.println(current_language.errorArtistName);
            }
        }
        String artistName = replaceAllAPI(input1);
        String input2 = null;
        boolean validInput2 = true;
        while (validInput2) {
            System.out.println(current_language.enterArtistName);
            input2 = scanner.nextLine().toLowerCase();
            String[] strInput2 = input2.split(" ");
            if(!banWord.containsAll(List.of(strInput2)) && (input2.length() <= 125)){
                validInput2 = false;
            }
            else{
                System.out.println(current_language.errorArtistName);
            }
        }
        boolean validInput3 = IsSearchSongPopular();
        String song = replaceAllAPI(input2);
        readXMLSong(artistName, song, songList, validInput3); //affiche les musiques correspondant aux noms de la musique données
        if(songList.isEmpty()){
            System.out.println(current_language.noFoundMusic);
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
            System.out.println(current_language.isSearchMusicPopular);
            String input = choice.nextLine().toLowerCase();
            if(Objects.equals(input,"y")){
                return true;
            }
            else if (Objects.equals(input, "n")){
                return false;
            }
            else{
                System.out.println("------------------------------------------------------------------");
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
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
            System.out.println(current_language.enterLyrics);
            input = scanner.nextLine().toLowerCase();
            String[] strInput = input.split(" ");
            if(!banWord.containsAll(List.of(strInput)) && (input.length() <= 250)){
                validInput = false;
            }
            else{
                System.out.println(current_language.errorArtistName);
            }
        }
        String lyric = replaceAllAPI(input);
        boolean validInput2 = IsSearchSongPopular();
        readXMLSongLyric(lyric, songList, validInput2);//affiche les musiques correspondant aux paroles données
        if(songList.isEmpty()){
            System.out.println(current_language.noFoundMusic);
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
            System.out.println("------------------------------------------------------------------");
            System.out.println(current_language.choseByMusicNumber);
            System.out.println(current_language.returnToMain);
            String index = choice.nextLine();
            if (Objects.equals(index.toLowerCase(), "menu")) {
                runCLI();
            } else if(index.matches("[0-9]+")) {
                temp = Integer.parseInt(index) - 1;
                if (songList.size() < temp || temp < 0) {
                    System.out.println(current_language.invaliedCommande);
                } else {
                    validInput = false;
                }
            }
            else{
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.exitToPreviousMenu);
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
            System.out.println(current_language.newResearch);
            System.out.println(current_language.returnToMain);
            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input, "1") || Objects.equals(input, "menu")){
                validInput = false;
            }
            else{
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
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
                System.out.println(current_language.exited);
                System.out.println(current_language.delete);
            } else{
                System.out.println(current_language.addToFavotis);
            }
            System.out.println(current_language.returnToPreviousMenu);
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "menu")){
                validInput = false;
            } else {
                System.out.println("------------------------------------------------------------------");
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
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
            System.out.println(current_language.creatPlaylist);
            System.out.println(current_language.showPlaylist);
            System.out.println(current_language.returnToMain);
            System.out.print(current_language.choice);
            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "menu")){
                validInput = false;
            }
            else{
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
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
            System.out.println(current_language.namePlaylist);
            System.out.println(current_language.returnToPreviousMenu);
            input = scanner.nextLine().toLowerCase();
            if(Objects.equals(input,"menu")){
                manageFavorites();
            }
            if(!input.equals("") && input.length() < 50){
                validInput = false;
            }
            else{
                if(input.equals("")){
                    System.out.println(current_language.emptyEntry);
                }
                else{
                    System.out.println(current_language.errorNamePlaylist);
                    System.out.println(current_language.errorNamePlaylist1);
                }
                System.out.println( current_language.reDo);
            }
        }
        File file = new File("src/main/java/app/lyricsapp/model/favorites" + playlists.size() + ".txt");
        file.createNewFile();
        playlists.add(new FavoriteList(input));
        favoriteList.saveAll(playlists);
        System.out.println(playlists.get(playlists.size()-1).getPlaylistName() + current_language.playlistCreated);
        System.out.println(current_language.returnToPreviousMenu);
        manageFavorites();
    }

    //affichage des musiques favorites
    public static void displayPlaylists() throws ParserConfigurationException, IOException, SAXException{
        if(!playlists.isEmpty()) {
            System.out.println(current_language.choosePlaylist);
            for (int i = 0; i < playlists.size() + 1; i++) {
                if(i == 0){
                    System.out.println(i + " - " + favoriteList.getPlaylistName());
                }
                else{
                    System.out.println(i + " - " + playlists.get(i-1).getPlaylistName());
                }
            }
            System.out.println((playlists.size() + 1) + current_language.returnToPreviousMenuInt);
            int input = -1;
            while (input < 0 || input > playlists.size()+1){
                Scanner choice = new Scanner(System.in);
                String textInput = choice.nextLine();
                if(textInput.matches("[0-9]+")){
                    input = Integer.parseInt(textInput);
                }
                else{
                    System.out.println(current_language.invaliedCommande);
                    System.out.println(current_language.reDo);
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
            System.out.println(current_language.likedMusic);
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
            System.out.println(current_language.favoritesArtist + favoritesArtist);
        }
        else{
            System.out.println(current_language.favoritesArtistPlaylist + favoritesArtist);
        }
        favoriteList.toStringFavoritesList();
        while(validInput){
            System.out.println("------------------------------------------------------------------");
            if(favoriteList.getList().isEmpty()){
                if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
                    System.out.println(current_language.emptyFavoris);
                    System.out.println(current_language.returnToPreviousMenu);
                }
                else{
                    System.out.println(current_language.emptyPlaylist);
                    System.out.println(current_language.playlistManag1);
                    System.out.println(current_language.returnToPreviousMenu);
                }
            }
            else{
                if(Objects.equals(favoriteList.getPlaylistName(), "Liked")){
                    System.out.println(current_language.displayFavoris);
                    System.out.println(current_language.returnToPreviousMenu);
                }
                else{
                    System.out.println(current_language.displayPlaylist);
                    System.out.println(current_language.playlistManag2);
                    System.out.println(current_language.returnToPreviousMenu);
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
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
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
        System.out.println(current_language.playlistChangeName);
        System.out.println(current_language.playlistDelete + playlist.getPlaylistName());
        System.out.println(current_language.returnToPreviousMenu);
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
            System.out.println(current_language.invaliedCommande);
            System.out.println(current_language.reDo);
            selectFavoriteSong(playlist);
        }
    }

    //Sous-menu permettant la modification du nom d'une playlist (uniquement)
    public static void editNamePlaylistCLI(FavoriteList playlist) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        System.out.println("------------------------------------------------------------------");
        System.out.print(current_language.namePlaylist + playlist.getPlaylistName());
        System.out.println(current_language.exitToPreviousMenu);
        String input = choice.nextLine().toLowerCase();
        if(Objects.equals(input, "menu") || Objects.equals(input.replace(" ",""), "")){
            System.out.println(current_language.changeNotTaken);
            selectFavoriteSong(playlist);
        }
        else if (input.length() < 50){
            playlist.setPlaylistName(input);
            System.out.println(current_language.changeTaken);
            System.out.println(current_language.playlistNewName + playlist.getPlaylistName());
            selectFavoriteSong(playlist);
        }
        else{
            System.out.println(current_language.changeNotTaken);
            System.out.println(current_language.errorNamePlaylist);
            selectFavoriteSong(playlist);
        }
    }

    //Sous-menu de suppression d'une playlist
    public static void deletePlaylistCLI(FavoriteList playlist) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        System.out.println("------------------------------------------------------------------");
        System.out.print(current_language.isPlaylistDelete1 + playlist.getPlaylistName() + current_language.isPlaylistDelete2);
        String input = choice.nextLine().toLowerCase();
        if(Objects.equals(input, "y") || Objects.equals(input, "n")){
            switch(input){
                case "y":
                    deletePlaylist(playlist, playlists);
                    break;
                case "n":
                    System.out.println(current_language.exitToPreviousMenu);
                    manageFavorites();
                    break;
            }
        }
        else{
            System.out.println(current_language.invaliedCommande);
            System.out.println(current_language.exitToPreviousMenu);
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
            System.out.println(current_language.delete);
            System.out.println(current_language.returnToMain);
            input = choice.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2")){
                validinput = false;
            }
            else {
                System.out.println("------------------------------------------------------------------");
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.reDo);
            }
        }
        switch(input){
            case "1":
                favoritesList.getList().remove(index);
                System.out.println(current_language.removeSuceed);
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
        System.out.println(current_language.chooseMusic);
        System.out.println(current_language.returnToMain);
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
                System.out.println(current_language.invaliedCommande);
                System.out.println(current_language.returnToMain);
                selectFavoriteSong(favoriteList);
            }
        } else{
            System.out.println("------------------------------------------------------------------");
            System.out.println(current_language.invaliedCommande);
            System.out.println(current_language.returnToMain);
            selectFavoriteSong(favoriteList);
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        favoriteList.recuperateFavorites();
        favoriteList.recuperateAll(playlists);
        run();
    }
}