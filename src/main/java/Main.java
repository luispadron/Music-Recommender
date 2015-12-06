import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
                         /*  INHERITANCE */
public class Main extends Application {


    /* The main method for Java FX application */
    @Override
    public void start(Stage primaryStage) throws Exception {
        /* Grab the FXML file that will display our GUI */
        Parent root = FXMLLoader.load(getClass().getResource("views/main_view.fxml"));
        /* Set the title */
        primaryStage.setTitle("Music Recommender");
        /* Set size of scene */
        primaryStage.setScene(new Scene(root, 800, 600));
        /* Get location of CSS */
        String css = Main.class.getResource("css/main_view.css").toExternalForm();
        /* Set CSS */
        primaryStage.getScene().getStylesheets().add(css);
        /* Don't allow resizing since I suck at making GUI's and this is static */
        primaryStage.setResizable(false);
        /* display the GUI */
        primaryStage.show();
    }

    public static void main(String [] args) {
        /* LAUNCHHHHHHHHHHHHHHHHHHHHHHHHHHHH */
        launch(args);
    }
}
