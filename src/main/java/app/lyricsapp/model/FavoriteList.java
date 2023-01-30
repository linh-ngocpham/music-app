package app.lyricsapp.model;

import java.util.HashSet;
import java.util.Set;

public class FavoriteList {
    private Set<Integer> favoritesSongsID;

    public FavoriteList(){
        this.favoritesSongsID = new HashSet<>();
    }

    public void add(Song song){
        this.favoritesSongsID.add(song.getLyricId());
    }

    public void remove(Song song){
        this.favoritesSongsID.remove(song.getLyricId());
    }
}

//print list
//is in list
