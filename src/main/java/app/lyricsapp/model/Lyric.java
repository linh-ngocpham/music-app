package app.lyricsapp.model;

public class Lyric {
    private Song song;

    private String lyric;

    public Lyric(Song song, String lyric){
        this.song = song;
        this.lyric = lyric;
    }
    public Lyric(){}

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

}
