package app.lyricsapp.model;

public class Song {
    private int trackId;
    private final int lyricId;
    private final String songName;
    private final int songRank;
    private final String artist;
    private final String LyricChecksum;
    private final String ArtistUrl;
    private final String SongUrl;

    public Song(int trackId, int lyricId, String songName, int songRank, String artist, String lyricChecksum, String artistUrl, String songUrl){
        this.trackId = trackId;
        this.artist = artist;
        this.lyricId = lyricId;
        this.songName = songName;
        this.songRank = songRank;
        this.LyricChecksum = lyricChecksum;
        ArtistUrl = artistUrl;
        SongUrl = songUrl;
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

    public String getLyricChecksum() {
        return LyricChecksum;
    }

    public String getArtistUrl() {
        return ArtistUrl;
    }

    public String getSongUrl() {
        return SongUrl;
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
