package app.lyricsapp.model;

public class Song {
    private int trackId;
    private int lyricId;
    private String songName;
    private int songRank;
    private String artist;
    private String LyricChecksum;
    private String ArtistUrl;
    private String SongUrl;
    private String lyricCorrectUrl;
    private String lyric;
    private String lyricCovertArtUrl;


    public Song(int trackId, int lyricId, String songName, int songRank, String artist, String lyricChecksum, String artistUrl, String songUrl){
        this.trackId = trackId;
        this.artist = artist;
        this.lyricId = lyricId;
        this.songName = songName;
        this.songRank = songRank;
        this.LyricChecksum = lyricChecksum;
        this.ArtistUrl = artistUrl;
        this.SongUrl = songUrl;
        this.lyricCorrectUrl = null;
        this.lyric = null;
    }

    public Song(){}

    public Song(int trackId, int lyricId, String songName, int songRank, String artist, String lyricChecksum, String artistUrl, String songUrl, String lyricCovertArtUrl){
        this.trackId = trackId;
        this.artist = artist;
        this.lyricId = lyricId;
        this.songName = songName;
        this.songRank = songRank;
        this.LyricChecksum = lyricChecksum;
        this.ArtistUrl = artistUrl;
        this.SongUrl = songUrl;
        this.lyricCovertArtUrl = lyricCovertArtUrl;
        this.lyricCorrectUrl = null;
        this.lyric = null;
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
    public String getLyricCovertArtUrl(){
        return lyricCovertArtUrl;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setLyricId(int lyricId) {
        this.lyricId = lyricId;
    }

    public void setArtistUrl(String artistUrl) {
        ArtistUrl = artistUrl;
    }

    public void setLyricChecksum(String lyricChecksum) {
        LyricChecksum = lyricChecksum;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setSongRank(int songRank) {
        this.songRank = songRank;
    }

    public void setSongUrl(String songUrl) {
        SongUrl = songUrl;
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

    public String setLyricCovertArtUrl(String lyricCovertArtUrl){
        this.lyricCovertArtUrl = lyricCovertArtUrl;
        return lyricCovertArtUrl;
    }
    public void toStringOne() {
        System.out.println( this.getArtist() + " - " + this.getSongName() + "    " + this.getSongRank() + "/10");
    }

    public void toStringTwo() {
        System.out.println( this.getArtist() + " - " + this.getSongName() + "    " + this.getSongRank() + "/10");
        System.out.println(this.getLyric());
    }
}