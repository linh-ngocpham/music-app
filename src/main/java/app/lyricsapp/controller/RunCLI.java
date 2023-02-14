package app.lyricsapp.controller;

import app.lyricsapp.model.FavoriteList;
import app.lyricsapp.model.Song;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

import static app.lyricsapp.model.ReadXML.*;

public class RunCLI {
    static FavoriteList favoriteList = new FavoriteList();
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------");
        System.out.println("--           Menu Principal           --");
        System.out.println("----------------------------------------");
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
                    favoriteList.save();
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
        // Affichage Menu des recherches
        System.out.println("------------------------------------------------------------------");
        System.out.println("1 - Recherche avec le nom de l'artiste et le nom de la musique");
        System.out.println("2 - Recherche par paroles");
        System.out.println("3 - retour");
        System.out.println("------------------------------------------------------------------");
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
            System.out.println("------------------------------------------------------------------");
            System.out.println("Commande inconnue");
            System.out.println( "Veuillez réessayer s'il vous plait.");
            searchSong();
        }
    }

    public static void searchSongArtistAndSongName() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        String input1 = null;
        boolean validInput1 = true;
        while (validInput1) {
            System.out.println("Saississez le nom de l'artiste :");
            input1 = scanner.nextLine().toLowerCase();
            String[] strInput1 = input1.split(" ");
            if(!banWord.containsAll(List.of(strInput1))){
                validInput1 = false;
            }
            else{
                System.out.println("Le nom de l'artiste contient que des mots interdits");
            }
        }
        String artistName = replaceAllAPI(input1);
        String input2 = null;
        boolean validInput2 = true;
        while (validInput2) {
            System.out.println("Saississez le nom de la musique :");
            input2 = scanner.nextLine().toLowerCase();
            String[] strInput2 = input2.split(" ");
            if(!banWord.containsAll(List.of(strInput2))){
                validInput2 = false;
            }
            else{
                System.out.println("Le nom de la musique contient que des mots interdits");
            }
        }
        String song = replaceAllAPI(input2);
        readXMLSong(artistName, song, songList); //affiche les musique correspondant aux noms de la musique données
        getSongFromSongList();
        postSearchMenu();
    }

    public static void searchSongLyric() throws ParserConfigurationException,IOException, SAXException{
        System.out.println("------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        String input = null;
        boolean validInput = true;
        while (validInput) {
            System.out.println("Saississez un morceau de paroles de la musique :");
            input = scanner.nextLine().toLowerCase();
            String[] strInput = input.split(" ");
            if(!banWord.containsAll(List.of(strInput))){
                validInput = false;
            }
            else{
                System.out.println("Les paroles entré contient que des mots interdits");
            }
        }
        String lyric = replaceAllAPI(input);
        readXMLSongLyric(lyric, songList); //affiche les musique correspondant aux noms d'artistes données
        getSongFromSongList();
        postSearchMenu();
    }

    public static void getSongFromSongList() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("------------------------------------------------------------------");
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
        System.out.println("------------------------------------------------------------------");
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
        System.out.println("------------------------------------------------------------------");
        int check = 0;
        for(Song favSong : favoriteList.getList()){
            if (Objects.equals(song.getSongName(), favSong.getSongName()) && Objects.equals(song.getArtist(), favSong.getArtist())){
                check = 1;
                break;
            }
        }
        if(check == 1){
            System.out.println();
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
                    if(check == 1) {
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
            System.out.println("------------------------------------------------------------------");
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
        System.out.println("------------------------------------------------------------------");
        if(favoriteList.getList().isEmpty()){
            System.out.println("Votre liste de favoris ne contient aucune musique");
            System.out.println("1 - retourner aux menu principal");
        }
        else{
            System.out.println("1 - Souhaitez-Vous affichez une musique de vos favoris ?");
            System.out.println("2 - retourner aux menu principal");
        }
        
        Scanner choice = new Scanner(System.in);
        String input = choice.nextLine();
        if(favoriteList.getList().isEmpty() && Objects.equals(input, "1")){
            switch (input){
                case "1":
                    runCLI();
                    break;
            }
        }
        else if((Objects.equals(input, "1") || Objects.equals(input, "2")) && !favoriteList.getList().isEmpty()){
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

    public static void editFavoritesSongList(int index, FavoriteList favoritesList) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("------------------------------------------------------------------");
        System.out.println("1 - Suprimmer de vos favoris");
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
        System.out.println("------------------------------------------------------------------");
        System.out.println("Choississez la musique");
        System.out.println("Sinon, taper retour");
        Scanner choice = new Scanner(System.in);
        String input = choice.nextLine();
        if(Objects.equals(input, "retour")){
            selectFavoriteSong(favoriteList);
        }
        int temp = Integer.parseInt(input) - 1;
        if(favoriteList.getList().size() > temp && temp  >= 0){
            if(favoriteList.getList().get(temp).getLyric() == null){
                favoriteList.getList().get(temp).toStringOne();
                getLyricsApi(favoriteList.getList().get(temp));
            }
            else{
                favoriteList.getList().get(temp).toStringTwo();
            }
            editFavoritesSongList(temp,favoriteList);
        } else{
            System.out.println("Commande Incorrecte");
            System.out.println("Retour");
            selectFavoriteSong(favoriteList);
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        favoriteList.recuperate();
        runCLI();
    }
}
