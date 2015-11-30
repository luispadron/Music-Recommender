package models;


import com.squareup.okhttp.*;


import org.json.JSONException;
import org.json.JSONObject;


import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArtistFinal {
    private String name;
    private String href;
    private String url;
    private List<String> genres = new ArrayList<String>();
    private int popularity;

    //Constructer with no fields
    public ArtistFinal() {
    }
    //Overloaded constructer with all fields being set from the start, except for URL
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
                formattedGenres = genre + ", " + genre;
            }
        } else if (genres.size() == 1) { //if we only have one genre
            formattedGenres = genres.get(0);
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
        //Using OkHTTP access the web, with the provided href
        //with the href we can grab json data and grab the URL for the
        //the artists homepage, this will help us give this infromation to the user
        //We grab the JSON asynchronously
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(href).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to connect to internet and grab URL for the artist" +
                ", error: " + e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //get the body
                String jsondata = response.body().string();
                //parse the json data
                try {
                    JSONObject baseData = new JSONObject(jsondata);
                    JSONObject jsonUrl = baseData.getJSONObject("external_urls");
                    url = jsonUrl.getString("spotify");
                    System.out.println(url);
                } catch (JSONException e) {
                    JOptionPane.showMessageDialog(null, "JSON error occurred: " + e);
                }
            }
        });
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
