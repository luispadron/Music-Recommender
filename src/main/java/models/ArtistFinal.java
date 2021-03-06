package models;

import reusable.UrlParser;

import java.util.ArrayList;
import java.util.List;

/* This class is mainly used to store the information I get from the Artist class inside of the Spotify API
 * I am using. I also have some methods that help me organize the content that is given to me via the API */

public class ArtistFinal {
    private String name;
    private String href;
    private String url;
    private List<String> genres = new ArrayList<>();
    private int popularity;

    //Constructer with no fields
    public ArtistFinal() {
    }

    //OVERLOADED constructer with all fields being set from the start, except for URL
    //url can't be set from start simply because we need to grab it using JSON
    public ArtistFinal(String name, String href, List<String> genres, int popularity) {
        this.name = name;
        this.href = href;
        this.genres = genres;
        this.popularity = popularity;
    }

    /* Setters & Getters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<String> getGenres() {
        return genres;
    }

    /* this returns a formatted string for all the genres of an artist */
    public String getFormattedGenres() {
        String formattedGenres = "";

        //if we have more than 1 genre
        if (genres.size() > 1) {
            for (String genre : genres) {
                formattedGenres += genre + ", ";
            }
        } else if (genres.size() == 1) { //if we only have one genre
            formattedGenres = genres.get(0);
        }

        //Remove any trailing commas
        if (formattedGenres.endsWith(", ")) {
            StringBuilder sb = new StringBuilder(formattedGenres);
            formattedGenres = sb.deleteCharAt(formattedGenres.lastIndexOf(",")).toString();
        }

        return formattedGenres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getUrl() {
        UrlParser urlParser = new UrlParser(href);
        url = urlParser.getParsedUrl();
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /* METHOD OVERRIDE FOR equals METHOD */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        //equals method uses polymorphism
        //if were the same class and the artists have the same name
        //then were the same object
        if (obj instanceof ArtistFinal) {
            if (((ArtistFinal) obj).name.equals(this.name)) {
                result = true;
            }
        }

        return result;
    }
    /* METHOD OVERRIDE FOR toString METHOD */
    @Override
    public String toString() {
        return "ArtistFinal{" +
                "name='" + name + '\'' +
                ", href='" + href + '\'' +
                ", url='" + url + '\'' +
                ", genres=" + genres +
                ", popularity=" + popularity +
                '}';
    }
}
