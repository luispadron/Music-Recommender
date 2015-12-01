package models;


/* This class is mainly used to store the information I get from the SimpleAlbum class inside of the Spotify API
 * I am using. I also have some methods that help me organize the content that is given to me via the API */

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import reusable.UrlParser;

import javax.swing.*;
import java.io.IOException;

public class AlbumFinal {
    private String albumName;
    private String albumType;
    private String artistName;
    private String href;
    private String url;

    public AlbumFinal(String albumName, String albumType, String href) {
        this.albumName = albumName;
        this.albumType = albumType;
        this.href = href;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumType() {
        //Format the string we get from the API
        //the string is given in uppercase and its ugly
        albumType = Character.toUpperCase(albumType.charAt(0)) + albumType.substring(1).toLowerCase();
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public String getArtistName() {
        //Using OkHTTP access the web, with the provided href
        //with the href we can grab json data and grab the URL for the
        //the artists homepage, this will help us give this information to the user
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(href)
                .build();
        try {
            Response response = client.newCall(request).execute();

            try {
                //Get the body of the url contents
                String jsondata = response.body().string();
                //Parse the json data
                JSONObject baseData = new JSONObject(jsondata);
                JSONArray jsonArtists = baseData.getJSONArray("artists");
                System.out.println(jsonArtists.toString());
                JSONObject singleArtist = jsonArtists.getJSONObject(0);
                artistName = singleArtist.getString("name");

            } catch (JSONException e) {
                JOptionPane.showMessageDialog(null, "JSON error occurred: " + e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getUrl() {
        UrlParser urlParser = new UrlParser(href);
        url = urlParser.getParsedUrl();
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
