package app.lyricsapp.model;

public class Song {
    private int trackIc;
    private final int lyricId;
    private final String songName;
    private final int songRank;
    private final String artist;

    public Song(int trackId, int lyricId, String songName, int songRank, String artist){
        this.trackIc = trackId;
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

    public int getTrackIc() {
        return trackIc;
    }

    public void setTrackIc(int trackIc) {
        this.trackIc = trackIc;
    }
}
