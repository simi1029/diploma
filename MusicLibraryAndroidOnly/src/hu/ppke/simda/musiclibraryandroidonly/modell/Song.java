package hu.ppke.simda.musiclibraryandroidonly.modell;

/**
 * Created by David on 2015.03.04..
 */
public class Song {
    private int img;
    private String artist;
    private String title;
    private String length;
    private String genre;
    private String date;
    private String comment;

    public Song(int img, String artist, String title, String length, String genre, String date, String comment) {
        this.img = img;
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.date = date;
        this.comment = comment;
    }

    public Song(int img, String artist, String title, String length) {
        this.img = img;
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.genre = "default";
        this.date = "2015";
        this.comment = "";
    }

    public void modify(String artist, String title, String length, String genre, String date, String comment) {
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.date = date;
        this.comment = comment;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Song{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
