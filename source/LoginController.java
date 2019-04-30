import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * @author Chris McAuley, Sian Pike.
 * <p>
 * Controller class for the Login Window.  Handles user interaction with the UI.
 */
public class LoginController extends Controller {

    /**
     * The field showing the username.
     */
    @FXML
    private TextField usernameTextField;

    /**
     * Textfield for the password.
     */
    @FXML
    private PasswordField passwordTextfield;

    /**
     * The button for logging in.
     */
    @FXML
    private Button loginButton;


    /**
     * When the login button is clicked, this method retrieves the text from the usernameTextField and checks,
     * whether the user exists.
     */
    @FXML
    private void loginButtonClicked(ActionEvent event) {
        String username = usernameTextField.getText();
        User user = getLibrary().getUserManager().getUserByUsername(username);

        //Checks whether the user exists.
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username " + username + " doesn't exist.",
                    ButtonType.OK);
            alert.show();

        } else if (!Security.checkPassword(passwordTextfield.getText(), user.getSecuritySalting(), user.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password.",
                    ButtonType.OK);
            alert.show();
        } else {
            // set the currently logged in user
            getLibrary().setCurrentUserLoggedIn(user);
            //Checks whether the user is a librarian.
            if (user.hasAdminAccess()) {

                new NewWindow("resources/LibrarianDashboard.fxml", event,
                        "Dashboard - TaweLib", getLibrary());

            } else {

                new NewWindow("resources/UserDashboard.fxml", event,
                        "Dashboard - TaweLib", getLibrary());
            }
        }
    }

    /**
     * Handles clicking on the forgotten password label.
     * @param event Clicking on the label.
     */
    @FXML
    public void forgottenPasswordCliecked(MouseEvent event) {
        System.out.println("test");
    }

    /**
     * Tries to do a login operation when Enter is pressed.
     * @param event Pressing Enter
     */
    @FXML
    public void onKeyPressed(KeyEvent event) {
        // if pressed key is enter
        if(event.getCode().compareTo(KeyCode.ENTER) == 0){
            loginButtonClicked(new ActionEvent(event.getSource(), event.getTarget()));
        }
    }
}

