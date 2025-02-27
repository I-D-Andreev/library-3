import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main class.
 *
 * @author Sian Pike
 */
public class Main extends Application {

    public static void main(String[] args) {
        // Try to send the not-sent emails every 15 seconds
        Timer timer = new Timer();
        TimerTask task = new SendUnsentEmails();
        timer.schedule(task, 10000, 15000);

        //Launches the application - do not remove.
        launch(args);

        // users - 1, ivan
        // librarian - lib
        // password across all accounts - 1

//        if you want to run the program and there are errors
//        you might need to run this code
//        more in the README file(
//        --------------------------------
//        Library l = new Library();
//        l.save();
        task.cancel();
        timer.purge();
        timer.cancel();
        System.exit(0);
    }

    /**
     * Initialises and shows the login window.
     *
     * @param primaryStage The current window.
     */
    public void start(Stage primaryStage) {
        // window size constraints
        final int windowHeight = 900;
        final int windowWidth = 1000;

        // load the library info
        Library library = new Library();
        Parent root = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/Login.fxml"));
            root = fxmlLoader.load();
            fxmlLoader.<Controller>getController().setLibrary(library);


        } catch (IOException e) {

            System.exit(1);

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(0);
        }
//
        primaryStage.setMinHeight(windowHeight);
        primaryStage.setMaxHeight(windowHeight);
        primaryStage.setMinWidth(windowWidth);
        primaryStage.setMaxWidth(windowWidth);
//        primaryStage.setMaximized(true);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("TaweLib - Login");
        primaryStage.show();
    }
}
