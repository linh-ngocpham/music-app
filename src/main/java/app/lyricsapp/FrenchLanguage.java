package app.lyricsapp;

public class FrenchLanguage extends Language{
    
    public FrenchLanguage() {
        this.type_of_language = "FRENCH";
        this.mainMenu = "Menu";
        this.findByLyric = "1 - Recherche des paroles d'une musique";
        this.favoris = "2 - Gestion de vos favoris";
        this.exit = "3 - Quitter le programme";
        this.choice = "Votre choix : ";
        this.invaliedCommande = "Commande inconnue";
        this.reDo = "Veuillez réessayer s'il vous plait.";
        this.searchedByMusic = "Vous avez choisi la recherche des paroles d'une musique";
        this.searchedByArtistMusic = "1 - Recherche avec le nom de l'artiste et le nom de la musique";
        this.searchedByLyrics = "2 - Recherche par paroles ";
        this.searchedByArtist = "Vous avez choisi : Recherche avec le nom de l'artiste";
        this.searchedWithLyrics = "Vous avez choisi : Recherche avec les paroles";
        this.enterArtistName = "Saisissez le nom de l'artiste :";
        this.errorArtistName = "Le nom de l'artiste ne contient que des mots interdits";
        this.noFoundMusic = "Aucune musique n'a été trouvé \\n\" + \"Retour au menu précédent";
        this.enterLyrics = "Saisissez un morceau de paroles de la musique :";
        this.errorLyrics = "Les paroles entrées ne contiennent que des mots interdits";
        this.choseByMusicNumber = "Choisissez le numéro de la musique";
        this.returnToMain = "Sinon, retourner aux menu principal : Menu";
        this.newResearch = "1 - Voulez-vous faire une nouvelle recherche ?";
        this.choseByMusicNumber2 = "Choose the playlist you want to save in :\n";
        this.exited = "Cette musique est déja présente dans les favoris";
        this.delete = "1 - Suprimer des favoris";
        this.addToFavotis = "1 - Ajouter aux favoris";
        this.creatPlaylist = "1 - Créer une playlist";
        this.playlistManag = "2 - Gestion des playlist";
        this.namePlaylist = "Quelle nom voulez-vous donner à la playlist ?";
        this.emptyEntry = "Entrée vide";
        this.errorNamePlaylist = "Nom de la playlist trop long.";
        this.errorNamePlaylist1 = "Le nom ne doit pas dépasser la limite de 50 caractères.";
        this.choosePlaylist = "Choisissez votre playlist :";
        this.emptyFavoris = "Votre liste de favoris ne contient aucune musique";
        this.displayFavoris = "1 - Souhaitez-vous afficher une musique de vos favoris ?";
        this.removeSuceed = "Musique retirée de vos favoris";
        this.choosePlaylist2 = "Choisissez la playlist:";
        this.chooseMusic = "Choisissez la musique";
    }
}
