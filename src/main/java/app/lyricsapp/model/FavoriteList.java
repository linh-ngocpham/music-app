package app.lyricsapp.model;

import com.sun.javafx.fxml.BeanAdapter;

import java.io.*;
import java.util.*;

public class FavoriteList {
    private ArrayList<Song> favoritesSongs;
    private String playlistName;

    public FavoriteList(){
        this.favoritesSongs = new ArrayList<>();
        this.playlistName = "Liked";
    }
    public FavoriteList(String playlistName){
        this.favoritesSongs = new ArrayList<>();
        this.playlistName = playlistName;
    }

    public void add(Song song){
        this.favoritesSongs.add(song);
    }

    public void remove(Song song){
        for(Song songFav : this.favoritesSongs){
            if(Objects.equals(song.getArtist(),songFav.getArtist()) && Objects.equals(song.getSongName(),songFav.getSongName())){
                this.favoritesSongs.remove(songFav);
                break;
            }
        }
    }

    public boolean isEmpty(){
        return favoritesSongs.isEmpty();
    }

    public ArrayList<Song> getList(){
        return favoritesSongs;
    }

    public boolean contains(Song song){
        return favoritesSongs.contains(song);
    }
    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
    public void toStringFavoritesList() {
        for(Song song : this.favoritesSongs){
            System.out.println((favoritesSongs.indexOf(song) + 1) + "/    " + song.getArtist() + " - " + song.getSongName() + "    " + song.getSongRank() + "/10");
            //System.out.println("TrackId: " + song.getTrackId());
            //System.out.println("LyricChecksum: " + song.getLyricChecksum());
            //System.out.println("LyricId: " + song.getLyricId());
            //System.out.println("SongUrl: " + song.getSongUrl());
            //System.out.println("ArtistUrl: " + song.getArtistUrl());
            //System.out.println("Artist: " + song.getArtist());
            //System.out.println("Song: " + song.getSongName());
            //System.out.println("SongRank: " + song.getSongRank());
        }
    }
    public void saveAll(List<FavoriteList> playlists) throws IOException {
        int index = 0;
        while(index < playlists.size()){
            if(!new File("src/main/java/app/lyricsapp/model/favorites" + index + ".txt").exists()){
                try{
                    boolean isCreated = new File("src/main/java/app/lyricsapp/model/favorites" + index + ".txt").createNewFile();
                    if(!isCreated){
                        System.out.println("The file was not created correctly");
                        continue;
                    }
                }
                catch (IOException e){
                    System.out.println("An error occurred");
                    continue;
                }
            }
            save("src/main/java/app/lyricsapp/model/favorites" + index + ".txt", playlists.get(index));
            index++;
        }
    }

    public void save(String correctPath, FavoriteList playlist){
        File file = new File(correctPath);
        FavoriteList.clearFile(correctPath);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("" + playlist.getPlaylistName() + "\n");
            System.out.println(playlist.getPlaylistName());
            for(Song song : playlist.getList()){
                writer.write("Song:\n");
                writer.write("TrackId:" + song.getTrackId() + "\n");
                writer.write("LyricChecksum:" + song.getLyricChecksum() + "\n");
                writer.write("LyricId:" + song.getLyricId() + "\n");
                writer.write("SongUrl:" + song.getSongUrl() + "\n");
                writer.write("ArtistUrl:" + song.getArtistUrl() + "\n");
                writer.write("Artist:" + song.getArtist() + "\n");
                writer.write("SongName:" + song.getSongName() + "\n");
                writer.write("SongRank:" + song.getSongRank() + "\n");
                //writer.write("Lyric:" + song.getLyric() + "\n");
            }
            System.out.println("Text saved to file.");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }
    }

    public void saveFavorites(FavoriteList favoriteList){
        File file = new File("src/main/java/app/lyricsapp/model/favorites.txt");
        FavoriteList.clearFile("src/main/java/app/lyricsapp/model/favorites.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("" + favoriteList.getPlaylistName() + "\n");
            for(Song song : favoriteList.getList()){
                writer.write("Song:\n");
                writer.write("TrackId:" + song.getTrackId() + "\n");
                writer.write("LyricChecksum:" + song.getLyricChecksum() + "\n");
                writer.write("LyricId:" + song.getLyricId() + "\n");
                writer.write("SongUrl:" + song.getSongUrl() + "\n");
                writer.write("ArtistUrl:" + song.getArtistUrl() + "\n");
                writer.write("Artist:" + song.getArtist() + "\n");
                writer.write("SongName:" + song.getSongName() + "\n");
                writer.write("SongRank:" + song.getSongRank() + "\n");
                //writer.write("Lyric:" + song.getLyric() + "\n");
            }
            System.out.println("Text saved to file.");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }
    }

    public void recuperateAll(List<FavoriteList> playlists){
        int index = 0;
        while(new File("src/main/java/app/lyricsapp/model/favorites" + index + ".txt").exists()){
            playlists.add(new FavoriteList(""));
            recuperate("src/main/java/app/lyricsapp/model/favorites" + index + ".txt", playlists.get(index));
            index += 1;
        }
    }

    public void recuperate(String correctPath, FavoriteList playlist){
        try {
            FileReader fileReader = new FileReader(correctPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int trackID = 0;
            String lyricChecksum = "";
            int lyricID = 0;
            String songUrl = "";
            String artistUrl = "";
            String artist = "";
            String songName = "";
            int songRank = 0;
            //String lyric = "";

            Song currentSong = null;

            String line;
            String name = bufferedReader.readLine();
            playlist.setPlaylistName(name);
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Song:")) {
                    if (currentSong != null) {
                        currentSong = new Song();
                        currentSong.setTrackId(trackID);
                        currentSong.setLyricId(lyricID);
                        currentSong.setSongName(songName);
                        currentSong.setSongRank(songRank);
                        currentSong.setArtist(artist);
                        currentSong.setLyricChecksum(lyricChecksum);
                        currentSong.setArtistUrl(artistUrl);
                        currentSong.setSongUrl(songUrl);
                        //currentSong.setLyric(lyric);
                        playlist.add(currentSong);
                        trackID = 0;
                        lyricChecksum = "";
                        lyricID = 0;
                        songUrl = "";
                        artistUrl = "";
                        artist = "";
                        songName = "";
                        songRank = 0;
                        //lyric = "";
                    }
                    else{
                        currentSong = new Song();
                    }
                } else if (line.startsWith("TrackId:")) {
                    trackID = Integer.parseInt(line.substring(8));
                } else if (line.startsWith("LyricChecksum:")) {
                    lyricChecksum = line.substring(14);
                } else if (line.startsWith("LyricId:")) {
                    lyricID = Integer.parseInt(line.substring(8));
                } else if (line.startsWith("SongUrl:")) {
                    songUrl = line.substring(8);
                } else if (line.startsWith("ArtistUrl:")) {
                    artistUrl = line.substring(10);
                } else if (line.startsWith("Artist:")) {
                    artist = line.substring(7);
                } else if (line.startsWith("SongName:")) {
                    songName = line.substring(9);
                } else if (line.startsWith("SongRank:")) {
                    songRank = Integer.parseInt(line.substring(9));
                } /*else if (line.startsWith("Lyric:")){
                    lyric = line.substring(6);
                }*/
            }

            if (currentSong != null) {
                currentSong = new Song();
                currentSong.setTrackId(trackID);
                currentSong.setLyricId(lyricID);
                currentSong.setSongName(songName);
                currentSong.setSongRank(songRank);
                currentSong.setArtist(artist);
                currentSong.setLyricChecksum(lyricChecksum);
                currentSong.setArtistUrl(artistUrl);
                currentSong.setSongUrl(songUrl);
                //currentSong.setLyric(lyric);
                playlist.add(currentSong);
            }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void recuperateFavorites(){
        try {
            FileReader fileReader = new FileReader("src/main/java/app/lyricsapp/model/favorites.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int trackID = 0;
            String lyricChecksum = "";
            int lyricID = 0;
            String songUrl = "";
            String artistUrl = "";
            String artist = "";
            String songName = "";
            int songRank = 0;
            //String lyric = "";

            Song currentSong = null;

            String line;
            playlistName = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Song:")) {
                    if (currentSong != null) {
                        currentSong = new Song();
                        currentSong.setTrackId(trackID);
                        currentSong.setLyricId(lyricID);
                        currentSong.setSongName(songName);
                        currentSong.setSongRank(songRank);
                        currentSong.setArtist(artist);
                        currentSong.setLyricChecksum(lyricChecksum);
                        currentSong.setArtistUrl(artistUrl);
                        currentSong.setSongUrl(songUrl);
                        //currentSong.setLyric(lyric);
                        favoritesSongs.add(currentSong);
                        trackID = 0;
                        lyricChecksum = "";
                        lyricID = 0;
                        songUrl = "";
                        artistUrl = "";
                        artist = "";
                        songName = "";
                        songRank = 0;
                        //lyric = "";
                    }
                    else{
                        currentSong = new Song();
                    }
                } else if (line.startsWith("TrackId:")) {
                    trackID = Integer.parseInt(line.substring(8));
                } else if (line.startsWith("LyricChecksum:")) {
                    lyricChecksum = line.substring(14);
                } else if (line.startsWith("LyricId:")) {
                    lyricID = Integer.parseInt(line.substring(8));
                } else if (line.startsWith("SongUrl:")) {
                    songUrl = line.substring(8);
                } else if (line.startsWith("ArtistUrl:")) {
                    artistUrl = line.substring(10);
                } else if (line.startsWith("Artist:")) {
                    artist = line.substring(7);
                } else if (line.startsWith("SongName:")) {
                    songName = line.substring(9);
                } else if (line.startsWith("SongRank:")) {
                    songRank = Integer.parseInt(line.substring(9));
                } /*else if (line.startsWith("Lyric:")){
                    lyric = line.substring(6);
                }*/
            }

            if (currentSong != null) {
                currentSong = new Song();
                currentSong.setTrackId(trackID);
                currentSong.setLyricId(lyricID);
                currentSong.setSongName(songName);
                currentSong.setSongRank(songRank);
                currentSong.setArtist(artist);
                currentSong.setLyricChecksum(lyricChecksum);
                currentSong.setArtistUrl(artistUrl);
                currentSong.setSongUrl(songUrl);
                //currentSong.setLyric(lyric);
                favoritesSongs.add(currentSong);
            }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void clearFile(String fileName){
        try{
            FileWriter fw = new FileWriter(fileName, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
        }catch(Exception exception){
            System.out.println("Exception have been caught");
        }
    }
}
