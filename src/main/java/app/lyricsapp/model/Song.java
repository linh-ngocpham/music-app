package app.lyricsapp.model;

public class Song {
    private int trackId;
    private final int lyricId;
    private final String songName;
    private final int songRank;
    private final String artist;

    public Song(int trackId, int lyricId, String songName, int songRank, String artist){
        this.trackId = trackId;
        this.artist = artist;
        this.lyricId = lyricId;
        this.songName = songName;
        this.songRank = songRank;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongName() {
        return songName;
    }

    public int getSongRank() {
        return songRank;
    }

    public int getLyricId() {
        return lyricId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }
}
