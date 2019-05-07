import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class to create a new window.
 *
 * @author Sian Pike, Ivan Andreev
 */

public class NewWindow {

    /**
     * Creates a new window when called.
     *
     * @param fxmlResource The location of the fxml document for the new window.
     * @param event        The current event being executed.
     * @param title        The title of the window.
     * @param library      The library.
     */
    public NewWindow(String fxmlResource, Event event, String title, Library library) {
        final int windowHeight = 900;
        final int windowWidth = 1000;

        Parent root;

        //Attempt to load the fxml file and set the scene.
        try {

            //root = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlResource));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlResource));
            root = fxmlLoader.load();
            fxmlLoader.<Controller>getController().setLibrary(library);

            Stage stage = new Stage();

            stage.setTitle(title);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("resources/form-styles.css");
            stage.setScene(scene);

            stage.setMinHeight(windowHeight);
            stage.setMaxHeight(windowHeight);
            stage.setMinWidth(windowWidth);
            stage.setMaxWidth(windowWidth);
//            stage.setMaximized(true);

            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

            Platform.setImplicitExit(false);
            // save data on stage close
            stage.setOnCloseRequest(eventHandler -> {
                // no unsent messages
                if(MailSender.NOT_SENT_MESSAGES.size() == 0) {
                    library.save();
                } else {
                    eventHandler.consume();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Can't exit the program while there are" +
                            " emails that are not sent. Try logging out!",
                            ButtonType.OK);
                    alert.show();
                }
            });

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(0);

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(1);
        }
    }
}
