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
    private String lyricCorrectUrl;
    private String lyric;


    public Song(int trackId, int lyricId, String songName, int songRank, String artist, String lyricChecksum, String artistUrl, String songUrl){
        this.trackId = trackId;
        this.artist = artist;
        this.lyricId = lyricId;
        this.songName = songName;
        this.songRank = songRank;
        this.LyricChecksum = lyricChecksum;
        ArtistUrl = artistUrl;
        SongUrl = songUrl;
        lyricCorrectUrl = null;
        lyric = null;
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

    public String getLyricCorrectUrl() {
        return lyricCorrectUrl;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyricCorrectUrl(String lyricCorrectUrl) {
        this.lyricCorrectUrl = lyricCorrectUrl;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }
}
