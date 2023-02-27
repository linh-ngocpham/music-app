package app.lyricsapp.controller;

import app.lyricsapp.model.FavoriteList;
import app.lyricsapp.model.Song;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static app.lyricsapp.model.ReadXML.*;

public class RunCLI { //TODO : faire en sorte que, pour chaque modification d'une playlist, elle est sauvegardé directement dans les fichiers.
    static List<FavoriteList> playlists = new ArrayList<>();
    static FavoriteList favoriteList = new FavoriteList();
    public List<FavoriteList> getPlaylists() {
        return playlists;
    }
    static List<Song> songList = new ArrayList<>();
    private static ArrayList<String> banWord = new ArrayList<String>();
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

    public static void runCLI() throws ParserConfigurationException, IOException, SAXException {
        banWordList(banWordString);
        boolean validInput = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------");
        System.out.println("--           Menu Principal           --");
        System.out.println("----------------------------------------");
        String input = null;
        while(validInput){
            System.out.println("1 - Recherche des paroles d'une musique");
            System.out.println("2 - Gestion de vos favoris");
            System.out.println("3 - Quitter le programme");
            System.out.print("Votre choix : ");
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3")) {
                validInput = false;
            }
            else {
                System.out.println("------------------------------------------------------------------");
                System.out.println("Commande inconnue");
                System.out.println("Veuillez réessayer s'il vous plait.");
            }
        }
        switch (input) {
            case "1":
                System.out.println("Vous avez choisi la recherche des paroles d'une musique");
                searchSong();
                break;
            case "2":
                manageFavorites();
                break;
            case "3":
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
            System.out.println("1 - Recherche avec le nom de l'artiste et le nom de la musique");
            System.out.println("2 - Recherche par paroles");
            System.out.println("3 - Retour");
            System.out.println("------------------------------------------------------------------");
            System.out.print("Votre choix : ");
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3")) {
                validInput = false;
            }
            else {
                System.out.println("------------------------------------------------------------------");
                System.out.println("Commande inconnue");
                System.out.println("Veuillez réessayer s'il vous plait.");
            }
        }
        //Choix de l'utilisateur
        switch (input) {
            case "1":
                System.out.println("Vous avez choisi : Recherche avec le nom de l'artiste");
                searchSongArtistAndSongName();
                break;
            case "2":
                System.out.println("Vous avez choisi : Recherche avec les paroles");
                searchSongLyric();
                break;
            case "3":
                runCLI();
                break;
        }
    }

    public static void searchSongArtistAndSongName() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        String input1 = null;
        boolean validInput1 = true;
        while (validInput1) {
            System.out.println("Saisissez le nom de l'artiste :");
            input1 = scanner.nextLine().toLowerCase();
            String[] strInput1 = input1.split(" ");
            if(!banWord.containsAll(List.of(strInput1)) && (input1.length() <= 75)){
                validInput1 = false;
            }
            else{
                System.out.println("Le nom de l'artiste ne contient que des mots interdits");
            }
        }
        String artistName = replaceAllAPI(input1);
        String input2 = null;
        boolean validInput2 = true;
        while (validInput2) {
            System.out.println("Saisissez le nom de la musique :");
            input2 = scanner.nextLine().toLowerCase();
            String[] strInput2 = input2.split(" ");
            if(!banWord.containsAll(List.of(strInput2)) && (input2.length() <= 125)){
                validInput2 = false;
            }
            else{
                System.out.println("Le nom de la musique ne contient que des mots interdits");
            }
        }
        String song = replaceAllAPI(input2);
        readXMLSong(artistName, song, songList);    //affiche les musique correspondant aux noms de la musique données
        if(songList.isEmpty()){
            System.out.println("Aucune musique n'a été trouvé \n" + "Retour au menu précédent");
            searchSong();
        }
        else{
            getSongFromSongList();
            postSearchMenu();
        }
    }

    public static void searchSongLyric() throws ParserConfigurationException,IOException, SAXException{
        Scanner scanner = new Scanner(System.in);
        String input = null;
        boolean validInput = true;
        while (validInput) {
            System.out.println("------------------------------------------------------------------");
            System.out.println("Saisissez un morceau de paroles de la musique :");
            input = scanner.nextLine().toLowerCase();
            String[] strInput = input.split(" ");
            if(!banWord.containsAll(List.of(strInput)) && (input.length() <= 250)){
                validInput = false;
            }
            else{
                System.out.println("Les paroles entrées ne contiennent que des mots interdits ou ");
            }
        }
        String lyric = replaceAllAPI(input);
        readXMLSongLyric(lyric, songList); //affiche les musiques correspondantes aux noms d'artistes donnés
        if(songList.isEmpty()){
            System.out.println("Aucune musique n'a été trouvé \n" + "Retour au menu précédent");
            searchSong();
        }
        else{
            getSongFromSongList();
            postSearchMenu();
        }

    }

    public static void getSongFromSongList() throws ParserConfigurationException, IOException, SAXException {
        boolean validInput = true;
        Scanner choice = new Scanner(System.in);
        int temp = 0;
        while(validInput) {
            System.out.println("------------------------------------------------------------------");
            System.out.println("Choisissez le numéro de la musique");
            System.out.println("Sinon, retourner aux menu principal : Menu");
            String index = choice.nextLine();
            if (Objects.equals(index.toLowerCase(), "menu")) {
                runCLI();
            } else if(index.matches("[0-9]+")) {
                temp = Integer.parseInt(index) - 1;
                if (songList.size() < temp || temp < 0) {
                    System.out.println("Commande incorrecte");
                } else {
                    validInput = false;
                }
            }
            else{
                System.out.println("Commande incorrecte");
                System.out.println("Retour");
                searchSong();
            }
        }
        Song displaySong = songList.get(temp);
        getLyricsApi(displaySong);
        choosePlaylistForFavorites(displaySong);
    }

    public static void postSearchMenu() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        String input = null;
        while(validInput){
            System.out.println("------------------------------------------------------------------");
            System.out.println("1 - Voulez-vous faire une nouvelle recherche ?");
            System.out.println("2 - Retour au Menu Principal");
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2")){
                validInput = false;
            }
            else{
                System.out.println("Commande inconnue");
                System.out.println("Veuillez réessayer s'il vous plait.");
            }
        }
        switch(input){
            case "1":
                searchSong();
                break;
            case "2":
                runCLI();
                break;
        }
    }
    public static void choosePlaylistForFavorites(Song song) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("Choose the playlist you want to save in :\n");
        System.out.println("0/ " + favoriteList.getPlaylistName());
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i+1) + "/ " + playlists.get(i).getPlaylistName());
        }
        int input = -1;
        while (input < 0 || input > playlists.size()){
            Scanner choice = new Scanner(System.in);
            String text = choice.nextLine();
            if(text.matches("[0-9]+")) {
                input = Integer.parseInt(text);
            }
            else{
                System.out.println("Entré incorrecte");
                System.out.println("Veuillez réesayer s'il vous plait");
            }
        }
        if(input == 0){
            favorites(song);
        }
        else{
            favoritesFromPlaylist(song, playlists.get(input-1));
        }
    }

    public static void favoritesFromPlaylist(Song song, FavoriteList playlist) throws ParserConfigurationException, IOException, SAXException {
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
                System.out.println("Cette musique est déja présente dans les favoris");
                System.out.println("1 - Suprimer des favoris");
            } else{
                System.out.println("1 - Ajouter aux favoris");
            }
            System.out.println("2 - Retour");
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2")){
                validInput = false;
            }
            else{
                System.out.println("Commande Incorrecte");
                System.out.println("Veuillez réessayer s'il vous plait.");
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
            case "2":
                break;
        }
    }
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
                System.out.println("Cette musique est déjà présente dans les favoris");
                System.out.println("1 - Suprimmer des favoris");
            } else{
                System.out.println("1 - Ajouter aux favoris");
            }
            System.out.println("2 - Retour");
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2")){
                validInput = false;
            } else {
                System.out.println("------------------------------------------------------------------");
                System.out.println("Commande Incorrecte");
                System.out.println("Veuillez réessayer s'il vous plait.");
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
            case "2":
                break;
        }
    }
    public static void manageFavorites() throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        String input = "";
        while(validInput){
            System.out.println("----------------------------------------");
            System.out.println("1 - Créer une playlist");
            System.out.println("2 - Gestion des playlist");
            System.out.println("3 - retour");
            System.out.print("Votre choix : ");
            input = scanner.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3")){
                validInput = false;
            }
            else{
                System.out.println("Commande inconnue");
                System.out.println( "Veuillez réessayer s'il vous plait.");
            }
        }
        switch (input) {
            case "1":
                createPlaylist();
                break;
            case "2":
                displayPlaylists();
                break;
            case "3":
                runCLI();
                break;
        }
    }

    public static void createPlaylist() throws IOException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        String input = "";
        while(validInput){
            System.out.println("----------------------------------------");
            System.out.println("Quelle nom voulez-vous donner à la playlist ?");
            System.out.println("9/ Retour");
            input = scanner.nextLine();
            if(Objects.equals(input,"9")){
                manageFavorites();
            }
            if(input != null && input.length() < 50){
                validInput = false;
            }
            else{
                if(input == null){
                    System.out.println("Entrée vide");
                }
                else{
                    System.out.println("Nom de la playlist trop long.");
                    System.out.println("Le nom ne doit pas dépasser la limite de 50 caractères.");
                }
                System.out.println( "Veuillez réessayer s'il vous plait.");
            }
        }
        File file = new File("src/main/java/app/lyricsapp/model/favorites" + playlists.size() + ".txt");
        file.createNewFile();
        playlists.add(new FavoriteList(input));
        favoriteList.saveAll(playlists);
        System.out.println(playlists.get(playlists.size()-1).getPlaylistName() + " a été créée.");
        System.out.println("Retour aux menu précedent");
        manageFavorites();
    }

    public static void displayPlaylists() throws ParserConfigurationException, IOException, SAXException{
        if(!playlists.isEmpty()) {
            System.out.println("Choisissez votre playlist :");
            for (int i = 0; i < playlists.size() + 1; i++) {
                if(i == 0){
                    System.out.println(i + "/ " + favoriteList.getPlaylistName());
                }
                else{
                    System.out.println(i + "/ " + playlists.get(i-1).getPlaylistName());
                }
            }
            System.out.println((playlists.size() + 1) + "/ Retour");
            int input = -1;
            while (input < 0 || input > playlists.size()+1){
                Scanner choice = new Scanner(System.in);
                String textInput = choice.nextLine();
                if(textInput.matches("[0-9]+")){
                    input = Integer.parseInt(textInput);
                }
                else{
                    System.out.println("Commande inconnue");
                    System.out.println( "Veuillez réessayer s'il vous plait.");
                }
            }
            if(input == 0){
                favoriteList.toStringFavoritesList();
                selectFavoriteSong(favoriteList);
            }
            else if(input == playlists.size() + 1) {
                manageFavorites();
            }
            else{
                playlists.get(input-1).toStringFavoritesList();
                selectFavoriteSong(playlists.get(input-1));
            }
        }
        else {
            System.out.println("Liked :");
            favoriteList.toStringFavoritesList();
            selectFavoriteSong(favoriteList);
        }
    }
    public static void displayFavoritesList() throws ParserConfigurationException, IOException, SAXException {
        favoriteList.toStringFavoritesList();
        selectFavoriteSong(favoriteList);
    }

    public static void selectFavoriteSong(FavoriteList favoriteList) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        boolean validInput = true;
        String input = null;
        while(validInput){
            System.out.println("------------------------------------------------------------------");
            if(favoriteList.getList().isEmpty()){
                System.out.println("Votre liste de favoris ne contient aucune musique");
                System.out.println("1 - Retourner aux menu principal");
            }
            else{
                System.out.println("1 - Souhaitez-vous afficher une musique de vos favoris ?");
                System.out.println("2 - Retourner aux menu principal");
            }
            input = choice.nextLine();
            if(favoriteList.getList().isEmpty() && Objects.equals(input, "1")){
                validInput = false;
            }
            else if((Objects.equals(input, "1") || Objects.equals(input, "2")) && !favoriteList.getList().isEmpty()){
                validInput = false;
            }
            else{
                System.out.println("Commande Incorrecte");
                System.out.println("Veuillez réessayer s'il vous plait.");
            }
        }
        if(favoriteList.getList().isEmpty()){
            if (input.equals("1")) {
                runCLI();
            }
        } else{
            switch (input){
                case "1":
                    getSongFromFavoritesSong(favoriteList);
                    break;
                case "2":
                    runCLI();
                    break;
            }
        }
    }

    public static void editFavoritesSongList(int index, FavoriteList favoritesList) throws ParserConfigurationException, IOException, SAXException {
        Scanner choice = new Scanner(System.in);
        boolean validinput = true;
        String input = null;
        while(validinput){
            System.out.println("------------------------------------------------------------------");
            System.out.println("1 - Suprimmer de vos favoris");
            System.out.println("2 - Retour");
            input = choice.nextLine();
            if(Objects.equals(input, "1") || Objects.equals(input, "2")){
                validinput = false;
            }
            else {
                System.out.println("------------------------------------------------------------------");
                System.out.println("Commande Incorrecte");
                System.out.println("Veuillez réessayer s'il vous plait.");
            }
        }
        switch(input){
            case "1":
                favoritesList.getList().remove(index);
                System.out.println("Musique retirée de vos favoris");
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
        System.out.println("Choisissez la musique");
        System.out.println("Sinon, taper retour");
        Scanner choice = new Scanner(System.in);
        String input = choice.nextLine();
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
                System.out.println("Commande Incorrecte");
                System.out.println("Retour");
                selectFavoriteSong(favoriteList);
            }
        } else{
            System.out.println("------------------------------------------------------------------");
            System.out.println("Commande Incorrecte");
            System.out.println("Retour");
            selectFavoriteSong(favoriteList);
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        favoriteList.recuperateFavorites();
        favoriteList.recuperateAll(playlists);
        runCLI();
    }
}