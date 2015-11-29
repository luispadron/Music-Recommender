package models;


public class ArtistFinal {
    private String name;
    private String genre;
    private int popularity;

    //Constructer with no fields
    public ArtistFinal() {
    }
    //Overloaded constructer with all fields being set from the start
    public ArtistFinal(String name, String genre, int popularity) {
        this.name = name;
        this.genre = genre;
        this.popularity = popularity;
    }

    /* Setters & Getters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
