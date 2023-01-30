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
}
