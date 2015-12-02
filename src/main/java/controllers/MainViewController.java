package controllers;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.ArtistSearchRequest;
import com.wrapper.spotify.methods.NewReleasesRequest;
import com.wrapper.spotify.methods.RelatedArtistsRequest;
import com.wrapper.spotify.methods.authentication.ClientCredentialsGrantRequest;
import com.wrapper.spotify.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.AlbumFinal;
import models.ArtistFinal;

import javax.swing.*;
import java.net.URL;
import java.util.*;

public class MainViewController implements Initializable {
    /* Set the fxml objects so we can use them in code */
    @FXML
    Button submitButton;
    @FXML
    TextField inputTextField;
    @FXML
    TextArea outputTextArea;
    /* The API keys for Spotify */
    private final String CLIENT_ID = "ecef8ae0eab64126be50eed12b89cd4a";
    private final String CLIENT_SECRET = "37a14b85174541d68ce3fc6703cfe501";
    /* The Api object, with the client ID and Client secret set */
    private final Api api = Api.builder()
            .clientId(CLIENT_ID)
            .clientSecret(CLIENT_SECRET)
            .build();

    /* This is the implemented method from the interface
     * initialize is run when ever the GUI loads up */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* First thing we want to do before loading anything is make sure to
         * get the authorization code from spotify that allows our program to access
          * their site */
        //create request
        final ClientCredentialsGrantRequest request = api.clientCredentialsGrant().build();
        //make request, asynchronously
        final SettableFuture<ClientCredentials> responseFuture = request.getAsync();
        /* call back for when request finishes */
        Futures.addCallback(responseFuture, new FutureCallback<ClientCredentials>() {
            @Override
            public void onSuccess(ClientCredentials result) {
                //set the the access token into our api object
                api.setAccessToken(result.getAccessToken());
            }

            @Override
            public void onFailure(Throwable t) {
                JOptionPane.showMessageDialog(null, "Something wen't wrong getting access token from " +
                        "Spotify.com.\nPlease try again later.");
            }
        });
    }

    /* Handle the button clicks for the menu bar */
    public void onCloseMenuItem(ActionEvent actionEvent) {
        /* Close application */
        System.exit(0);
    }

    public void onClearMenuItem(ActionEvent actionEvent) {
        /* Clear out all the fields for the TextField and TextArea */
        inputTextField.clear();
        outputTextArea.clear();
    }

    public void onAboutMenuItem(ActionEvent actionEvent) {
        /* Using swing because too lazy to make a whole new view for this */
        JOptionPane.showMessageDialog(null, "This program was created by" +
                " Luis Padron.\n To use this simply select what you would want to do" +
                " and follow the prompts.");
    }

    /* Whenever use selects to look for related artists from the drop down menu bar */
    public void onRelatedArtistsMenuItem(ActionEvent actionEvent) {
        /* Check if submit button is hidden, if so, show it */
        if (!submitButton.isVisible()) {
           submitButton.setVisible(true);
        }
        inputTextField.setPromptText("Enter artist name you would like to match");

        /* Set an on action for the button, the reason this is not its own method like
         * all the other on actions, is because this button can be used for every other
          * option the user might want to do*/
        submitButton.setOnAction((event) -> {
            //In case the user leaves text area blank
            if (inputTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Text field cannot be empty.");
            } else {
                //if text field isn't empty
                String userInput = inputTextField.getText();
                String ARTIST_ID = "";
                outputTextArea.setVisible(true);
            /* First we search for the artist and get the artist ID from Spotify */

                final ArtistSearchRequest request = api.searchArtists(userInput).limit(1).build();
                //we look through Spotify and see if we can find the artists name
                try {
                    final Page<Artist> artistSearchResult = request.get();
                    final List<Artist> artists = artistSearchResult.getItems();

                    if (artists.isEmpty()) {
                        outputTextArea.setText("Unfortunately could not find any artists");
                    } else {
                        //Grab the id for the first artist, this is the most relevant artist
                        //based on user search query
                        ARTIST_ID = artists.get(0).getId();
                        /* Now that we have the artists ID we can grab the related artists */
                        final RelatedArtistsRequest relatedArtistsRequest = api.getArtistRelatedArtists(ARTIST_ID).build();
                        try {
                            //Collect the artists into a list
                            final List<Artist> relatedArtists = relatedArtistsRequest.get();

                            if (relatedArtists.isEmpty()) {
                                outputTextArea.setText("Couldn't find any related artists, bummer.");
                            }else {
                                //Create an array of ArtistFinal objects, which will be used to get information
                                //I made my own class because I wanted to have specific methods to help me get information
                                //easier than the class that was provided by the API
                                ArrayList<ArtistFinal> finalArtists = new ArrayList<>();

                                //set the array
                                for (Artist artist : relatedArtists) {
                                    finalArtists.add(new ArtistFinal(artist.getName(), artist.getHref(),
                                            artist.getGenres(), artist.getPopularity()));
                                }

                                //display the related artists
                                outputTextArea.setText("-------- RELATED ARTISTS --------\n\n");

                                for (ArtistFinal artist : finalArtists) {

                                    //format what we will display to the user for each artist
                                    outputTextArea.setText(outputTextArea.getText()
                                            + "Name: " + artist.getName() +  "\nPopularity: " + artist.getPopularity());
                                    if(!artist.getFormattedGenres().isEmpty()) {
                                        outputTextArea.setText(outputTextArea.getText() + "\nGenre(s): "
                                                + artist.getFormattedGenres());
                                    }
                                    outputTextArea.setText(outputTextArea.getText() + "\nListen: " + artist.getUrl() +
                                            "\n\n--------------------------------------------------------------------\n");
                                }
                            }
                        } catch (Exception e) {
                            outputTextArea.setText("Something went wrong looking for related artists, error: " + e);
                        }
                    }
                } catch (Exception e) {
                    outputTextArea.setText("Something went wrong, error: " + e);
                }
            }
        });
    }
    /* Whenever use selects to look for new releases from the drop down menu bar */
    public void onNewReleasesMenuItem(ActionEvent actionEvent) {
        if (!submitButton.isVisible()) {
            submitButton.setVisible(true);
        }
        inputTextField.setPromptText("Enter the country code, example: US");
        //create onAction for button
        submitButton.setOnAction(event -> {
            //if no input from user
            if (inputTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Text field must be filled");
            } else {
                //if we have input
                //create the request to grab the new releases
                final NewReleasesRequest request = api.getNewReleases()
                        .limit(20)
                        .offset(0)
                        .country(inputTextField.getText())
                        .build();
                //Push the request
                try {
                    NewReleases newReleases = request.get();
                    //get the page of spotify new albums
                    final Page<SimpleAlbum> pageAlbums = newReleases.getAlbums();
                    //create a list of those albums
                    final List<SimpleAlbum> simpleAlbums = pageAlbums.getItems();
                    //Convert and create an array list of AlbumFinals my own class
                    //this class has different way of organizing things than the API provides
                    ArrayList<AlbumFinal> albums = new ArrayList<>();

                    for (SimpleAlbum simpleAlbum : simpleAlbums) {
                        albums.add(new AlbumFinal(simpleAlbum.getName(),
                                simpleAlbum.getAlbumType().toString(), simpleAlbum.getHref()));
                    }

                    //display the albums to the user
                    outputTextArea.setText("--------- NEW RELEASES ---------\n\n");

                    for (AlbumFinal album : albums) {
                        outputTextArea.setText(outputTextArea.getText() +
                                "Artist: " + album.getArtistName() +
                                "\nAlbum name: " + album.getAlbumName() +
                                "\nType: " + album.getAlbumType() +
                                "\nURL: " + album.getUrl());
                        outputTextArea.setText(outputTextArea.getText() +
                                "\n\n--------------------------------------------------------------------\n");
                    }
                    outputTextArea.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Something went wrong while getting new releases.\n" +
                            "Make sure to enter a valid country code and try again.");
                }
            }
        });
    }

    @Override
    public String toString() {
        return "MainViewController{" +
                "submitButton=" + submitButton +
                ", inputTextField=" + inputTextField +
                ", outputTextArea=" + outputTextArea +
                ", CLIENT_ID='" + CLIENT_ID + '\'' +
                ", CLIENT_SECRET='" + CLIENT_SECRET + '\'' +
                ", api=" + api +
                '}';
    }
}
