package app.lyricsapp;

public class FrenchLanguage extends Language{
    
    public FrenchLanguage() {
        this.type_of_language = "FRENCH";
        this.mainMenu = "Menu";
        this.findByLyric = "1 - Recherche des paroles d'une musique";
        this.favoris = "2 - Gestion de vos favoris";
        this.playlistMenu = "2 - Playlist";
        this.languageChoice = "3 - Changement de langue";
        this.exit = "4 - Quitter le programme";
        this.choice = "Votre choix : ";
        this.invaliedCommande = "Commande inconnue";
        this.reDo = "Veuillez réessayer s'il vous plait.";
        this.favoritesArtist = "Artiste Favoris : ";
        this.favoritesArtistPlaylist = "Artiste Favoris de la playlist : ";
        this.favoritesArtistNotFound = "aucun";
        this.exitToPreviousMenu = "Retour aux menu precedent";
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
        this.isSearchMusicPopular = "Recherché Uniquement les Musique Populaires (Min 7/10) : [y/n]";
        this.returnToMain = "Sinon, retourner aux menu principal : Menu";
        this.returnToPreviousMenu = "Sinon, retourner aux menu precedent : Menu";
        this.returnToPreviousMenuInt = " - retourner aux menu precedent";
        this.newResearch = "1 - Voulez-vous faire une nouvelle recherche ?";
        this.choseByMusicNumber2 = "Choose the playlist you want to save in :\n";
        this.exited = "Cette musique est deja présente dans les favoris";
        this.delete = "1 - Supprimer des favoris";
        this.addToFavotis = "1 - Ajouter aux favoris";
        this.creatPlaylist = "1 - Créer une playlist";
        this.playlistManag1 = "1 - Gestion de la playlist";
        this.playlistManag2 = "2 - Gestion de la playlist";
        this.playlistChangeName = "1 - renommer la playlist";
        this.playlistDelete = "2 - Supprimer la playlist : ";
        this.namePlaylist = "Quelle nom voulez-vous donner à la playlist ?";
        this.playlistCreated = " a été créée.";
        this.playlistNewName = "La playlist s'appelle désormais : ";
        this.isPlaylistDelete1 = "êtes-vous sûr de supprimer la playlist ";
        this.isPlaylistDelete2 = " ? (y/n) : ";
        this.emptyEntry = "Entrée vide";
        this.errorNamePlaylist = "Nom de la playlist trop long.";
        this.errorNamePlaylist1 = "Le nom ne doit pas dépasser la limite de 50 caractères.";
        this.choosePlaylist = "Choisissez votre playlist :";
        this.emptyFavoris = "Votre liste de favoris ne contient aucune musique";
        this.emptyPlaylist = "Votre Playlist ne contient aucune musique";
        this.displayFavoris = "1 - Souhaitez-vous afficher une musique de vos favoris ?";
        this.displayPlaylist = "1 - Souhaitez-vous afficher une musique de votre Playlist?";
        this.removeSuceed = "Musique retirée de vos favoris";
        this.choosePlaylist2 = "Choisissez la playlist:";
        this.chooseMusic = "Choisissez la musique";
        this.likedMusic = "Favoris :";
        this.changeTaken = "Modification pris en compte";
        this.changeNotTaken = "Modification non pris en compte";
    }
}
