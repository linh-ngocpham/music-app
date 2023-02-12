package app.lyricsapp.model;

import com.sun.javafx.fxml.BeanAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavoriteList {
    private ArrayList<Song> favoritesSongs;

    public FavoriteList(){
        this.favoritesSongs = new ArrayList<>();
    }

    public void add(Song song){
        this.favoritesSongs.add(song);
    }

    public void remove(Song song){
        this.favoritesSongs.remove(song);
    }

    public ArrayList<Song> getList(){
        return favoritesSongs;
    }

    public boolean contains(Song song){
        return favoritesSongs.contains(song);
    }

    public void toStringFavoritesList() {
        for(Song song : this.favoritesSongs){
            System.out.println((favoritesSongs.indexOf(song) + 1) + "/    " + song.getArtist() + " - " + song.getSongName() + "    " + song.getSongRank() + "/10");
            //System.out.println("TrackId: " + song.getTrackId());
            //System.out.println("LyricChecksum: " + song.getLyricChecksum());
            //System.out.println("LyricId: " + song.getLyricId());
            //System.out.println("SongUrl: " + song.getSongUrl());
            //System.out.println("ArtistUrl: " + song.getArtistUrl());
            //System.out.println("Artist: " + song.getArtist());
            //System.out.println("Song: " + song.getSongName());
            //System.out.println("SongRank: " + song.getSongRank());
        }
    }
}
