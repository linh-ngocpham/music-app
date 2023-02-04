package app.lyricsapp.model;

import java.util.HashSet;
import java.util.Set;

public class FavoriteList {
    private Set<Song> favoritesSongs;

    public FavoriteList(){
        this.favoritesSongs = new HashSet<>();
    }

    public void add(Song song){
        this.favoritesSongs.add(song);
    }

    public void remove(Song song){
        this.favoritesSongs.remove(song);
    }

    public Set<Song> getList(){
        return favoritesSongs;
    }

    public boolean contains(Song song){
        return favoritesSongs.contains(song);
    }

    public void toStringFavoritesList() {
        for(Song song : this.favoritesSongs){
            System.out.println("TrackId: " + song.getTrackId());
            System.out.println("LyricChecksum: " + song.getLyricChecksum());
            System.out.println("LyricId: " + song.getLyricId());
            System.out.println("SongUrl: " + song.getSongUrl());
            System.out.println("ArtistUrl: " + song.getArtistUrl());
            System.out.println("Artist: " + song.getArtist());
            System.out.println("Song: " + song.getSongName());
            System.out.println("SongRank: " + song.getSongRank());
        }
    }
}
