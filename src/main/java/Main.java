import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    /* The main method for Java FX application */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/main_view.fxml"));
        primaryStage.setTitle("Music Recommender");
        primaryStage.setScene(new Scene(root, 800, 600));
        String css = Main.class.getResource("css/main_view.css").toExternalForm();
        primaryStage.getScene().getStylesheets().add(css);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String [] args) {
        launch(args);
    }
}
