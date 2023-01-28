package app.lyricsapp.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadXML {
    public static void main(String[] args) throws ParserConfigurationException, SAXException
    {
        List<Song> songList = new ArrayList<>();
        try {
            File file = new File("src/main/java/app/lyricsapp/model/query1.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            System.out.println("Root Element :" + document.getDocumentElement().getNodeName());
            NodeList nList = document.getElementsByTagName("SearchLyricResult");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength()-1; temp++) {
                Node nNode = nList.item(temp);
                nNode.getNodeName();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int trackId = Integer.parseInt(eElement.getElementsByTagName("TrackId").item(0).getTextContent());
                    String lyricChecksum = eElement.getElementsByTagName("LyricChecksum").item(0).getTextContent();
                    int lyricId = Integer.parseInt(eElement.getElementsByTagName("LyricId").item(0).getTextContent());
                    String songUrl = eElement.getElementsByTagName("SongUrl").item(0).getTextContent();
                    String artistUrl = eElement.getElementsByTagName("ArtistUrl").item(0).getTextContent();
                    String artist = eElement.getElementsByTagName("Artist").item(0).getTextContent();
                    String songName = eElement.getElementsByTagName("Song").item(0).getTextContent();
                    int songRank = Integer.parseInt(eElement.getElementsByTagName("SongRank").item(0).getTextContent());
                    Song song = new Song(trackId, lyricId, songName,songRank,artist, lyricChecksum, artistUrl, songUrl);
                    songList.add(song);
                }
            }
        }
        catch(IOException e) {
            System.out.println(e);
        }
        try {
            File file1 = new File("src/main/java/app/lyricsapp/model/query2.xml");
            DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder db1 = dbf1.newDocumentBuilder();
            Document document = db1.parse(file1);
            document.getDocumentElement().normalize();
            System.out.println("Root Element :" + document.getDocumentElement().getNodeName());
            NodeList nList1 = document.getElementsByTagName("GetLyricResult");
            System.out.println("----------------------------");
            for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
                Node nNode1 = nList1.item(temp1);
                System.out.println("\nCurrent Element :" + nNode1.getNodeName());
                if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement1 = (Element) nNode1;
                    System.out.println("Track ID : " + eElement1.getElementsByTagName("TrackId").item(0).getTextContent());
                    System.out.println("Lyric Check Sum : " + eElement1.getElementsByTagName("LyricChecksum").item(0).getTextContent());
                    System.out.println("Lyric Id : " + eElement1.getElementsByTagName("LyricId").item(0).getTextContent());
                    System.out.println("LyricSong : " + eElement1.getElementsByTagName("LyricSong").item(0).getTextContent());
                    System.out.println("LyricArtist : " + eElement1.getElementsByTagName("LyricArtist").item(0).getTextContent());
                    System.out.println("LyricUrl : " + eElement1.getElementsByTagName("LyricUrl").item(0).getTextContent());
                    System.out.println("LyricRank : " + eElement1.getElementsByTagName("LyricRank").item(0).getTextContent());
                    System.out.println("LyricCorrectUrl : " + eElement1.getElementsByTagName("LyricCorrectUrl").item(0).getTextContent());
                    System.out.println("Lyric : " + eElement1.getElementsByTagName("Lyric").item(0).getTextContent());


                }
            }
        }
        catch(IOException e) {
            System.out.println(e);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choisir un nom de chanson ou un nom d'artiste: ");
        String input = scanner.nextLine();
        for (Song song : songList) {
            if (input.equals(song.getSongName()) || input.equals(song.getArtist())) {
                System.out.println("TrackId: " + song.getTrackId());
                System.out.println("LyricChecksum: " + song.getLyricChecksum());
                System.out.println("LyricId: " + song.getLyricId());
                System.out.println("SongUrl: " + song.getSongUrl());
                System.out.println("ArtistUrl: " + song.getArtistUrl());
                System.out.println("Artist: " + song.getArtist());
                System.out.println("Song: " + song.getSongName());
                System.out.println("SongRank: " + song.getSongRank());
            }
        }
    }
}