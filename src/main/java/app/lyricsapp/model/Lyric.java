package app.lyricsapp.model;

public class Lyric {
    private int lyricId;

    private String lyricSong;

    private String lyricArtist;

    private String lyric;

    public Lyric(int lyricId, String lyricSong, String lyricArtist, String lyric){
        this.lyricId = lyricId;
        this.lyricSong = lyricSong;
        this.lyricArtist = lyricArtist;
        this.lyric = lyric;
    }

    public int getLyricId() {
        return lyricId;
    }

    public String getLyricSong() {
        return lyricSong;
    }

    public String getLyricArtist() {
        return lyricArtist;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyricId(int lyricId) {
        this.lyricId = lyricId;
    }

    public void setLyricSong(String lyricSong) {
        this.lyricSong = lyricSong;
    }

    public void setLyricArtist(String lyricArtist) {
        this.lyricArtist = lyricArtist;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

}
