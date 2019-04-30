import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Class that controlls ForgottenPassword.fxml
 *
 * @author Ivan Andreev
 */
public class ForgottenPasswordController extends Controller {

    /**
     * The back button.
     */
    @FXML
    private Button backButton;

    /**
     * The textfield for secret answer.
     */
    @FXML
    private PasswordField secretAnswerTextfield;

    /**
     * The textfield for username.
     */
    @FXML
    private TextField usernameTextfield;

    /**
     * The textfield for secret question.
     */
    @FXML
    private TextField secretQuestionTextfield;

    /**
     * The secret question button.
     */
    @FXML
    private Button getSecretQuestionButton;

    /**
     * The button to email a new password.
     */
    @FXML
    private Button emailPasswordButton;

    /**
     * When back button is clicked, goes back to login screen.
     *
     * @param event Back button being clicked.
     */
    @FXML
    public void backButtonClicked(ActionEvent event) {
        new NewWindow("resources/Login.fxml", event,
                "TaweLib - Login", getLibrary());

    }

    @FXML
    public void emailPasswordButtonClicked(ActionEvent event) {

    }

    /**
     * Finds the secret question of the user based on username given.
     *
     * @param event The secret question button being clicked.
     */
    @FXML
    public void getSecretQuestionButtonClicked(ActionEvent event) {
        String username = usernameTextfield.getText();
        User user = getLibrary().getUserManager().getUserByUsername(username);

        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username " + username + " doesn't exist.",
                    ButtonType.OK);
            alert.show();
        } else {
            // user is found
            secretQuestionTextfield.setText(user.getSecretQuestion());
        }
    }

}
