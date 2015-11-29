package controllers;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.ArtistSearchRequest;
import com.wrapper.spotify.methods.RelatedArtistsRequest;
import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.AuthorizationCodeCredentials;
import com.wrapper.spotify.models.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

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
    /* I will use/set this string to determine what action were going to preform */
    private String searchRequest;



    /* This is the implemented method from the interface
     * initialize is run when ever the GUI loads up */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
                                //display the related artists
                                outputTextArea.setText("------RELATED ARTISTS------\n\n");
                                for (Artist artist : relatedArtists) {
                                    //format what we will display to the user for each artist
                                    outputTextArea.setText(outputTextArea.getText()
                                            + "Name: " + artist.getName() +  "\nPopularity: " + artist.getPopularity());
                                    //check if genre information is available
                                    if (!artist.getGenres().isEmpty()) {
                                        outputTextArea.setText(outputTextArea.getText() + "\nGenre: " + artist.getGenres().get(0));
                                    }
                                    outputTextArea.setText(outputTextArea.getText() +
                                            "\n\n---------------------------------------------------------\n");
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

    }

    /* Whenever use selects to look for featured play-lists from the drop down menu bar */
    public void onFeaturedPlaylistsMenuItem(ActionEvent actionEvent) {
        if (!submitButton.isVisible()) {
            submitButton.setVisible(true);
        }
        inputTextField.setPromptText("Enter the country code, example: US");

    }
}
