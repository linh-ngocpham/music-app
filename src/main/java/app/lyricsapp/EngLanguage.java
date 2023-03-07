package app.lyricsapp;

public class EngLanguage extends  Language{
    public EngLanguage(){
        this.type_of_language = "ENGLISH";
        this.mainMenu = "Menu";
        this.findByLyric = "1 - Finding music lyrics";
        this.favoris = "2 - Managing your favorites";
        this.languageChoice = "3 - Change language";
        this.exit = "4 - Quit the program";
        this.choice= "Your choice : ";
        this.invaliedCommande = "Unknown command";
        this.reDo = "Try again, please.";
        this.exitToPreviousMenu = "Return to previous menu";
        this.searchedByMusic = "You have chosen to search for music lyrics";
        this.searchedByArtistMusic = "Search with artist name and music name";
        this.searchedByLyrics= "Search by lyrics ";
        this.searchedByArtist = "You have chosen: Search by artist name";
        this.searchedwithLyrics = "You have chosen: Search with lyrics";
        this.enterArtistName = "Enter the name of the artist:";
        this.errorArtistName = "The name of the artist only contains forbidden words";
        this.noFoundMusic= "No music was found \\n\" + \"Return to previous menu";
        this.enterLyrics= "Enter a piece of lyrics from the music:";
        this.errorLyrics = "The lyrics entered only contain forbidden words";
        this.choseByMusicNumber = "Choose the music number";
        this.isSearchMusicPopular = "Searched Only Popular Music (Min 7/10) : [y/n]";
        this.returnToMain = "Otherwise, return to the main menu: Menu";
        this.returnToPreviousMenu = "Otherwise, return to previous menu : Menu";
        this.newResearch = "1 - Do you want to do a new search?";
        this.choseByMusicNumber2 = "Choose the playlist you want to save in :\n";
        this.exited = "This music is already present in the favorites";
        this.delete = "1 - Delete favorites";
        this.addToFavotis = "1 - Add to favorites";
        this.creatPlaylist = "1 - Create a playlist";
        this.playlistManag1 = "1 - Playlist management";
        this.playlistManag2 = "2 - Playlist management";
        this.playlistChangeName = "1 - rename this playlist";
        this.playlistDelete = "2 - delete playlistt : ";
        this.namePlaylist = "What name do you want to give to the playlist?";
        this.playlistCreated = " has been created.";
        this.playlistNewName = "The playlist is now called : ";
        this.isPlaylistDelete1 = "Are you sure to delete the playlist ";
        this.isPlaylistDelete2 = " ? (y/n) : ";
        this.emptyEntry = "Empty entry";
        this.errorNamePlaylist = "Playlist name too long.";
        this.errorNamePlaylist1 = "The name must not exceed the 50 character limit.";
        this.choosePlaylist = "Choose your playlist:";
        this.emptyFavoris = "Your favorites list contains no music";
        this.emptyPlaylist = "Your Playlist does not contain any music";
        this.displayFavoris = "1 - Would you like to display music from your favorites?";
        this.displayPlaylist = "1 - Would you like to display music from your Playlist?";
        this.removeSuceed = "Music deleted from your favorites";
        this.choosePlaylist2 = "Choose your playlist:";
        this.chooseMusic = "Choose the music";
        this.likedMusic = "Liked :";
        this.changeTaken = "Change taken";
        this.changeNotTaken = "Change Not taken";
    }
}
